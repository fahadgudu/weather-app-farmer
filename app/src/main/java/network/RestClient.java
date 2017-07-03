package network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private ApiService apiService;

    public RestClient() {
//        OkHttpClient httpClient = new OkHttpClient();
//        httpClient.networkInterceptors().
//                add(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request request = chain.request().newBuilder()
//                                    .addHeader("X-API-AUTHID", AppPreference.getValue(SampleApplication.getAppContext(), AppKeys.KEY_X_API_AUTHID))
//                                    .addHeader("X-API-AUTHKEY", AppPreference.getValue(SampleApplication.getAppContext(), AppKeys.KEY_X_API_AUTHKEY))
//                                    .build();
//                            return chain.proceed(request);

//                            Request request = chain.request();
//
//                            long t1 = System.nanoTime();
//                            String requestLog = String.format("Sending request %s on %s%n%s",
//                                    request.url(), chain.connection(), request.headers());
//                            //YLog.d(String.format("Sending request %s on %s%n%s",
//                            //        request.url(), chain.connection(), request.headers()));
//                            if(request.method().compareToIgnoreCase("post")==0){
//                                requestLog ="\n"+requestLog+"\n"+bodyToString(request);
//                            }
//                            Log.d("TAG","request"+"\n"+requestLog);
//
//                            Response response = chain.proceed(request);
//                            long t2 = System.nanoTime();
//
//                            String responseLog = String.format("Received response for %s in %.1fms%n%s",
//                                    response.request().url(), (t2 - t1) / 1e6d, response.headers());
//
//                            String bodyString = response.body().string();
//
//                            Log.d("TAG","response"+"\n"+responseLog+"\n"+bodyString);
//
//                            return response.newBuilder()
//                                    .body(ResponseBody.create(response.body().contentType(), bodyString))
//                                    .build();
//                        }
//                    }
//
//                );
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

        Gson gson = new GsonBuilder().setDateFormat(
                "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(AppWebServices.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        apiService = restAdapter.create(ApiService.class);
    }

    public RestClient(boolean headers) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

        Gson gson = new GsonBuilder().setDateFormat(
                "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(AppWebServices.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = restAdapter.create(ApiService.class);
    }
    public class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            //YLog.d(String.format("Sending request %s on %s%n%s",
            //        request.url(), chain.connection(), request.headers()));
            if(request.method().compareToIgnoreCase("post")==0){
                requestLog ="\n"+requestLog+"\n"+bodyToString(request);
            }
            Log.d("TAG","request"+"\n"+requestLog);

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG","response"+"\n"+responseLog+"\n"+bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
        }
    }
    public String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
    public ApiService getApiService() {
        return apiService;
    }
}
