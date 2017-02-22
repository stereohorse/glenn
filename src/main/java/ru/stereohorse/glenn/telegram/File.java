package ru.stereohorse.glenn.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static java.util.Optional.ofNullable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class File {

    private File.Result result;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Result {

        @JsonProperty("file_path")
        private String path;
    }

    public String getPath() {
        return ofNullable(result)
            .map(File.Result::getPath)
            .orElse(null);
    }
}
