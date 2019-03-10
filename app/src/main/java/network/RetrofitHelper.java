package network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String URL = "https://api.jsonbin.io/";
    private static RetrofitHelper mRetrofitHelper;

    public static RetrofitHelper getInstance() {
        if (mRetrofitHelper == null) {
            mRetrofitHelper = new RetrofitHelper();
        }
        return mRetrofitHelper;
    }

    public Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

}
