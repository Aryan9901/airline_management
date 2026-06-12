package com.aryan.service;

import com.aryan.dto.UserDTO;
import com.aryan.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String email, String password);
    AuthResponse signup(UserDTO request);
}
