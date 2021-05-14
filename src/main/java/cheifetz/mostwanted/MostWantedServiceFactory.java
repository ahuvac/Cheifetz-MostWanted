package cheifetz.mostwanted;
 import  retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostWantedServiceFactory {

    public cheifetz.mostwanted.MostWantedService newInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.fbi.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        cheifetz.mostwanted.MostWantedService service = retrofit.create(cheifetz.mostwanted.MostWantedService.class);

        return service;
    }
}
