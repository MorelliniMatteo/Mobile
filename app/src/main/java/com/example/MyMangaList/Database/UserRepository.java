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

    public User loginUser(String username, String password) {
        return userDAO.login(username, password);
    }

    public boolean isUsernameTaken(String username) {
        return userDAO.findByUsername(username) != null;
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
}
