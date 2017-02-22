package ru.stereohorse.glenn.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Chat {

    private Integer id;
}
