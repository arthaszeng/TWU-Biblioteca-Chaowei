package com.twu.biblioteca;

import java.util.*;

class CustomerDataManagement {
    private List<User> userList = new ArrayList<>();

    CustomerDataManagement() {
        userList.add(new User("admin", "123-4567", "admin", new ArrayList<>(), new ArrayList<>()));
    }

    User login(String account, String password) {
        for (User user : userList) {
            if (user.getLibraryNumber().equals(account) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
