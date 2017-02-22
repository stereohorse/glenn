package ru.stereohorse.glenn.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {

    @JsonProperty("file_id")
    private String id;

    @JsonProperty("file_size")
    private Integer size;
}
