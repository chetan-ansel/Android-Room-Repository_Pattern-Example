package viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import repository.UserRepository;
import room.UserTableInfo;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<UserTableInfo>> getUserDetails() {
        return userRepository.getUserDetails();
    }

}
