package network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("syncTime")
    private String mSyncTime;
    @SerializedName("users")
    private List<UserInformation> userInformation;

    public String getmSyncTime() {
        return mSyncTime;
    }

    public List<UserInformation> getUserInformation() {
        return userInformation;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "mSyncTime='" + mSyncTime + '\'' +
                ", userInformation=" + userInformation +
                '}';
    }
}
