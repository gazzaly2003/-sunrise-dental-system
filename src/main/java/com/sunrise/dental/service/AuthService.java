package com.sunrise.dental.service;

import com.sunrise.dental.dao.UserDAO;
import com.sunrise.dental.model.User;

public class AuthService {

    private final UserDAO userDAO;

    // Default constructor for the actual application
    public AuthService() {
        this.userDAO = new UserDAO();
    }

    // Constructor for unit testing
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String username, String password) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }

        return userDAO.authenticate(username, password);
    }
}