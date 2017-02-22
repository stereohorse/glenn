package ru.stereohorse.glenn.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnotateRequest {

    private Image image;

    private List<Feature> features;

    private ImageContext imageContext;
}
