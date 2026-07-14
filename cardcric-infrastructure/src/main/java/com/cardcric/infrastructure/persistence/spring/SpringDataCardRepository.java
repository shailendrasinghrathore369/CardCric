package com.cardcric.infrastructure.persistence.spring;

import com.cardcric.infrastructure.persistence.entity.CricketCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCardRepository extends JpaRepository<CricketCardEntity, UUID> {
}
