package network;

import com.google.gson.annotations.SerializedName;

public class UserInformation {

    @SerializedName("userId")
    private String mUserId;
    @SerializedName("emaildId")
    private String mEmaildId;
    @SerializedName("phoneNUmber")
    private int mPhoneNUmber;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("firstName")
    private String mFirstName;

    public String getUserId() {
        return mUserId;
    }

    public String getEmaildId() {
        return mEmaildId;
    }

    public int getPhoneNUmber() {
        return mPhoneNUmber;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "mUserId='" + mUserId + '\'' +
                ", mEmaildId='" + mEmaildId + '\'' +
                ", mPhoneNUmber='" + mPhoneNUmber + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                '}';
    }
}
