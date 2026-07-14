package com.cardcric.application.port.output;

import com.cardcric.application.dto.UserData;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<UserData> findByUsername(String username);
    Optional<UserData> findByEmail(String email);
    Optional<UserData> findById(UUID id);
    Optional<UserData> findByRefreshToken(String refreshToken);
    UserData save(UserData userData);
}
