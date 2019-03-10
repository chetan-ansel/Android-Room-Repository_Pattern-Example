package room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUserData(UserTableInfo userModel);

    @Query("SELECT * FROM userdetails")
    List<UserTableInfo> getUserData();

    @Query("Delete FROM userdetails WHERE userId=:userId")
    void deleteUserData(final int userId);

    @Query("UPDATE userdetails SET EmailAddress = :emailAddress WHERE userId = :userId")
    void updateUserData(final String emailAddress, final int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSyncData(SyncTimeTable syncTimeTable);

    @Query("SELECT * FROM synctimetable")
    SyncTimeTable getSyncData();
}
