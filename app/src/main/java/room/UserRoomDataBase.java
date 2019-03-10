package room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {UserTableInfo.class, SyncTimeTable.class}, version = 1, exportSchema = false)
public abstract class UserRoomDataBase extends RoomDatabase {

    private static UserRoomDataBase userRoomDataBase = null;

    public static UserRoomDataBase getUserRoomDataBase(final Context context) {
        if (userRoomDataBase == null) {
            synchronized (UserRoomDataBase.class) {
                userRoomDataBase = Room.databaseBuilder(context.getApplicationContext(),
                        UserRoomDataBase.class,
                        "user_database").build();
            }
        }

        return userRoomDataBase;
    }

    public abstract UserDao userDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        Log.e("InvalidationTracker", "issue");
        return null;
    }
}
