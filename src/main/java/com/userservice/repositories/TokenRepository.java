package com.userservice.repositories;

import com.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);

    Token findByTokenAndExpiryAtGreaterThan(String token, long expiryAt);
}
