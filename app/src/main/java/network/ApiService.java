package network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {

    // User Login
    @POST(AppWebServices.API_USER_LOGIN)
    Call<Object> login(@Body RegisteredRequest registeredRequest);

    @POST(AppWebServices.API_WEATHER_UPDATE)
    Call<Object> updateSetting(@Body WeatherRequest registeredRequest);


}
