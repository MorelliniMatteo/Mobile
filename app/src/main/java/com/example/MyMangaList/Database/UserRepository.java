package com.example.MyMangaList.Database;

import android.app.Application;

import com.example.MyMangaList.User;

public class UserRepository {

    private final UserDAO userDAO;

    public UserRepository(Application application) {
        CardItemDatabase db = CardItemDatabase.getDatabase(application);
        userDAO = db.userDAO();
    }

    public void registerUser(User user) {
        CardItemDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insertUser(user);
        });
    }

    public void isUsernameTaken(String username, Callback<Boolean> callback) {
        CardItemDatabase.databaseWriteExecutor.execute(() -> {
            boolean result = userDAO.findByUsername(username) != null;
            callback.onResult(result);
        });
    }

    public void loginUser(String username, String password, Callback<User> callback) {
        CardItemDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDAO.login(username, password);
            callback.onResult(user);
        });
    }

    public void getUserByUsername(String username, Callback<User> callback) {
        CardItemDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDAO.getUserByUsername(username);
            // Passa il risultato al callback
            if (callback != null) {
                callback.onResult(user);
            }
        });
    }



    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public interface Callback<T> {
        void onResult(T result);
    }
}

