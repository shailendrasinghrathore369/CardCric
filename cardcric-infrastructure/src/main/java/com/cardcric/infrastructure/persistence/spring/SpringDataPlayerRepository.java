package com.cardcric.infrastructure.persistence.spring;

import com.cardcric.infrastructure.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataPlayerRepository extends JpaRepository<PlayerEntity, UUID> {
    Optional<PlayerEntity> findByUsername(String username);
}
