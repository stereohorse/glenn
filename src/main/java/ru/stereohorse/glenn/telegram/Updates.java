package ru.stereohorse.glenn.telegram;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Updates {

    @JsonProperty("result")
    private List<Update> updates = emptyList();

    @JsonIgnore
    private LocalDateTime fetchTime;

    public static Updates empty() {
        return new Updates();
    }


    public Integer getNextOffset() {
        return updates.isEmpty()
            ? 0
            : updates.get(updates.size() - 1).getId() + 1;
    }
}
