package ru.stereohorse.glenn.google;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Google {

    @POST("/v1/images:annotate")
    Call<AnnotateResponses> annotateImages(@Query("key") String apiKey,
                                           @Body AnnotateRequests annotateRequests);
}
