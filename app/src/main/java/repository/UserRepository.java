package repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import network.NetworkInterface;
import network.RetrofitHelper;
import network.UserInformation;
import network.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import room.SyncTimeTable;
import room.UserDao;
import room.UserRoomDataBase;
import room.UserTableInfo;

public class UserRepository {
    private final UserDao userDao;

    interface onCallBack {
        void getTime(String time);
    }

    public UserRepository(Application application) {
        UserRoomDataBase userRoomDataBase = UserRoomDataBase.getUserRoomDataBase(application);
        userDao = userRoomDataBase.userDao();
    }

    public LiveData<List<UserTableInfo>> getUserDetails() {

        final MutableLiveData<List<UserTableInfo>> userListLiveData = new MutableLiveData<>();
        GetSyncTime getSyncTime = new GetSyncTime(userDao, new onCallBack() {
            @Override
            public void getTime(String syncDate) {
                if (syncDate == null) {
                    getUserDetailsFromNetwork(userListLiveData);
                } else {
                    try {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat
                                ("dd/MM/yyyy", Locale.US);
                        String currentDate = simpleDateFormat.format(calendar.getTime());
                        Date currentTImeDate = simpleDateFormat.parse(currentDate);
                        Date syncDateTime = simpleDateFormat.parse(syncDate);
                        if (currentTImeDate.before(syncDateTime)) {
                            new GetDetailsDAO(userListLiveData, userDao).execute();
                        } else {
                            getUserDetailsFromNetwork(userListLiveData);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getSyncTime.execute();

        return userListLiveData;
    }

    private void getUserDetailsFromNetwork(final MutableLiveData<List<UserTableInfo>>
                                                   userListLiveData) {

        NetworkInterface networkInterface = RetrofitHelper.getInstance().getClient().
                create(NetworkInterface.class);
        networkInterface.getUserDetails().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                UserResponse userResponse = response.body();
                if (userResponse == null) {
                    Log.e("error", "something wrong");
                    return;
                }
                InsertDAO insertDAO = new InsertDAO(userResponse, userDao, userListLiveData);
                insertDAO.execute();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                Log.e("error", "something wrong");
                userListLiveData.setValue(null);
            }
        });

    }

    private static class InsertDAO extends AsyncTask<Void, Void, Void> {

        final UserDao userDao;
        final UserResponse userResponseLiveData;
        final MutableLiveData<List<UserTableInfo>> userListLiveData;

        InsertDAO(UserResponse userResponseLiveData, UserDao userDao,
                  MutableLiveData<List<UserTableInfo>> userListLiveData) {
            this.userResponseLiveData = userResponseLiveData;
            this.userDao = userDao;
            this.userListLiveData = userListLiveData;
        }

        @Override
        protected Void doInBackground(Void... voids) {


            List<UserInformation> userInformations =
                    userResponseLiveData.getUserInformation();
            List<UserTableInfo> userTableInfos = new ArrayList<>();

            for (int i = 0; i < userInformations.size(); i++) {

                UserTableInfo userTableInfo = new UserTableInfo();
                userTableInfo.setEmailAddress(userInformations.get(i).getEmaildId());
                userTableInfo.setFirstName(userInformations.get(i).getFirstName());
                userTableInfo.setId(userInformations.get(i).getUserId());
                userTableInfo.setLastName(userInformations.get(i).getLastName());
                userTableInfo.setMobileNumber(userInformations.get(i).getPhoneNUmber());
                userTableInfos.add(userTableInfo);
                userDao.insertUserData(userTableInfo);
            }

            SyncTimeTable syncTimeTable = new SyncTimeTable();
            syncTimeTable.setSyncTime(userResponseLiveData.getmSyncTime());
            userDao.insertSyncData(syncTimeTable);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetDetailsDAO(userListLiveData, userDao).execute();
        }
    }


    private static class GetDetailsDAO extends AsyncTask<Void, Void, List<UserTableInfo>> {

        final UserDao userDao;
        final MutableLiveData<List<UserTableInfo>> userListLiveData;

        GetDetailsDAO(MutableLiveData<List<UserTableInfo>> userListLiveData, UserDao userDao) {
            this.userListLiveData = userListLiveData;
            this.userDao = userDao;
        }

        @Override
        protected List<UserTableInfo> doInBackground(Void... voids) {
            return userDao.getUserData();

        }

        @Override
        protected void onPostExecute(List<UserTableInfo> userTableInfos) {
            super.onPostExecute(userTableInfos);
            userListLiveData.setValue(userTableInfos);
        }
    }

    private static class GetSyncTime extends AsyncTask<Void, Void, String> {

        final UserDao userDao;
        final onCallBack onCallBack;

        GetSyncTime(UserDao userDao, onCallBack onCallBack) {
            this.userDao = userDao;
            this.onCallBack = onCallBack;
        }

        @Override
        protected String doInBackground(Void... voids) {
            SyncTimeTable syncTimeTable = userDao.getSyncData();
            if (syncTimeTable != null) {
                return userDao.getSyncData().getSyncTime();
            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String userTableInfos) {
            super.onPostExecute(userTableInfos);
            onCallBack.getTime(userTableInfos);

        }
    }

}