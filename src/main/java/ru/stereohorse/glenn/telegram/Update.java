package ru.stereohorse.glenn.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Update {

    @JsonProperty("update_id")
    private Integer id;

    private Message message;

    public Optional<Photo> getPhoto() {
        return ofNullable(message)
            .map(Message::getPhotos)
            .flatMap(photos -> photos.stream()
                .sorted(comparing(Photo::getSize).reversed())
                .findFirst());
    }

    public Optional<Integer> getChatId() {
        return ofNullable(message)
            .map(Message::getChat)
            .map(Chat::getId);
    }
}
