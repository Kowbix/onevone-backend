package com.kkowbel.oneVone.application;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Override
    public void addUsernameToHttpSession(String username, String userId, HttpSession session) {
        session.setAttribute("username", username);
    }
}
