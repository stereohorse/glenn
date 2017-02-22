package ru.stereohorse.glenn;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.stereohorse.glenn.google.*;
import ru.stereohorse.glenn.telegram.Telegram;
import ru.stereohorse.glenn.telegram.Updates;

import java.io.IOException;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.time.LocalDateTime.now;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;


@Slf4j
public class Glenn {

    private static final int LONG_POLLING_TIMEOUT_S = 30;


    public static void main(String... args) {
        Telegram telegram = createTelegramClient();
        Google google = createGoogleCloudPlatformClient();

        Stream
            .iterate(Updates.empty(), previousUpdates -> {
                try {
                    return telegram.getUpdates(LONG_POLLING_TIMEOUT_S, previousUpdates.getNextOffset())
                        .execute()
                        .body()
                        .setFetchTime(now());
                } catch (IOException e) {
                    throw new RuntimeException("unable to get updates", e);
                }
            })
            .flatMap(response -> response.getUpdates().stream())
            .forEach(update -> update.getPhoto()
                .map(photo -> {
                    try {
                        return telegram.getFile(photo.getId())
                            .execute()
                            .body();
                    } catch (IOException e) {
                        throw new RuntimeException(format("unable to fetch photo: %s", photo), e);
                    }
                })
                .map(filePath -> format("https://api.telegram.org/file/bot%s/%s",
                    getProperty("telegram.token"),
                    filePath.getPath()))
                .map(fileUrl -> {
                    try {
                        return telegram.downloadFile(fileUrl).execute();
                    } catch (IOException e) {
                        throw new RuntimeException(format("unable to download file %s", fileUrl), e);
                    }
                })
                .ifPresent(file -> {
                    AnnotateResponses annotateResponses;
                    try {
                        AnnotateRequests annotateRequests = new AnnotateRequests()
                            .setRequests(singletonList(new AnnotateRequest()
                                .setImage(new Image()
                                    .setContent(file.body().bytes()))
                                .setFeatures(singletonList(new Feature()
                                    .setType("TEXT_DETECTION")))
                                .setImageContext(new ImageContext()
                                    .setLanguageHints(singletonList("ru")))));

                        annotateResponses = google
                            .annotateImages(getProperty("google.apiKey"), annotateRequests)
                            .execute()
                            .body();
                    } catch (IOException e) {
                        throw new RuntimeException("unable to annotate image", e);
                    }

                    update.getChatId()
                        .ifPresent(chatId -> {
                            try {
                                telegram.sendMessage(chatId, ofNullable(annotateResponses)
                                    .map(AnnotateResponses::getResponses)
                                    .filter(responses -> !responses.isEmpty())
                                    .map(responses -> responses.get(0).getTextAnnotations())
                                    .filter(annotations -> !annotations.isEmpty())
                                    .map(a -> a.get(0).getDescription())
                                    .orElse("sorry, I can't read this"))
                                    .execute();
                            } catch (IOException e) {
                                throw new RuntimeException("unable to send message", e);
                            }
                        });
                })
            );
    }

    private static Telegram createTelegramClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::debug);
        loggingInterceptor.setLevel(BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(LONG_POLLING_TIMEOUT_S + 10, SECONDS)
            .build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(format("https://api.telegram.org/bot%s/", getProperty("telegram.token")))
            .client(httpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

        return retrofit.create(Telegram.class);
    }

    private static Google createGoogleCloudPlatformClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::debug);
        loggingInterceptor.setLevel(BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://vision.googleapis.com/")
            .client(httpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

        return retrofit.create(Google.class);
    }
}


