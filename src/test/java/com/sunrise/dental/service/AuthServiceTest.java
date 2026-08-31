package com.sunrise.dental.service;

import com.sunrise.dental.dao.UserDAO;
import com.sunrise.dental.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void login_withValidCredentials_returnsUser() {
        AuthService service = new AuthService(new FakeUserDAO(new User("admin", "pass123", "ADMIN")));
        User result = service.login("admin", "pass123");
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    void login_withBlankUsername_returnsNull() {
        AuthService service = new AuthService(new FakeUserDAO(null));
        assertNull(service.login("", "pass123"));
    }

    @Test
    void login_withInvalidCredentials_returnsNull() {
        AuthService service = new AuthService(new FakeUserDAO(null));
        assertNull(service.login("wrong", "wrong"));
    }

    static class FakeUserDAO extends UserDAO {
        private final User userToReturn;

        FakeUserDAO(User userToReturn) {
            this.userToReturn = userToReturn;
        }

        @Override
        public User authenticate(String username, String password) {
            return userToReturn;
        }
    }
}