package room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "UserDetails")
public class UserTableInfo {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "userId")
    private String id;

    @ColumnInfo(name = "FirstName")
    private String firstName;

    @ColumnInfo(name = "LastName")
    private String lastName;

    @ColumnInfo(name = "EmailAddress")
    private String emailAddress;

    @ColumnInfo(name = "MobileNumber")
    private int mobileNumber;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
