package cheifetz.mostwanted;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MostWantedService {
    @GET("/wanted/v1/list")
    Single<MostWantedFeed> getMostWantedFeed(@Query("person_classification") String person_classification);

}