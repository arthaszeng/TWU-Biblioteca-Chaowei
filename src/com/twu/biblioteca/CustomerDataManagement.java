package com.twu.biblioteca;

import java.util.HashMap;
import java.util.Map;

class CustomerDataManagement {
    private Map<String, String> accountsAndPasswords = new HashMap<>();

    CustomerDataManagement() {
        accountsAndPasswords.put("123-4567", "admin");
    }

    boolean login(String account, String password) {
        return accountsAndPasswords.get(account).equals(password);
    }
}
