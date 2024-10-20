package com.kkowbel.oneVone.application;

import jakarta.servlet.http.HttpSession;

public interface SessionService {

    void addUsernameToHttpSession(String username, String userId, HttpSession session);
}
