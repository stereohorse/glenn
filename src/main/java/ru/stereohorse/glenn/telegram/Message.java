package ru.stereohorse.glenn.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Message {

    @JsonProperty("photo")
    private List<Photo> photos;

    private Chat chat;
}
