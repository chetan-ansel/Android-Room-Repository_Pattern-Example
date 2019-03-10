package network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkInterface {

    @GET("b/5c84b69e2e4731596f1c2852")
    Call<UserResponse> getUserDetails();
}
