package ru.stereohorse.glenn.telegram;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Telegram {

    @POST("getUpdates")
    Call<Updates> getUpdates(@Query("timeout") Integer timeout,
                             @Query("offset") Integer offset);

    @POST("getFile")
    Call<File> getFile(@Query("file_id") String fileId);

    @POST("sendMessage")
    Call<Void> sendMessage(@Query("chat_id") Integer chatId,
                           @Query("text") String text);

    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
