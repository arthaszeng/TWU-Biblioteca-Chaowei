package com.twu.biblioteca.User;

import java.util.*;

public class UserDataManager {
    private List<User> userList = new ArrayList<>();

    public UserDataManager() {
        userList.add(new User("admin", "email", "phone","123-4567", "admin"));
        userList.add(new User("test", "test", "test", "test", "test"));
    }

    public User login(String account, String password) {
        for (User user : userList) {
            if (user.getLibraryNumber().equals(account) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
