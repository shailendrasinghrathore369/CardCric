package com.cardcric.infrastructure.persistence.entity;

import com.cardcric.domain.enums.PlayerRole;
import com.cardcric.domain.model.CricketCard;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "cricket_cards")
public class CricketCardEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerRole role;

    private int battingAverage;
    private int bowlingAverage;
    private int strikeRate;
    private int economyRate;
    private int fieldingRating;

    private String imageUrl;

    protected CricketCardEntity() {}

    public CricketCardEntity(UUID id, String name, PlayerRole role, int battingAverage, int bowlingAverage, int strikeRate, int economyRate, int fieldingRating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.battingAverage = battingAverage;
        this.bowlingAverage = bowlingAverage;
        this.strikeRate = strikeRate;
        this.economyRate = economyRate;
        this.fieldingRating = fieldingRating;
        this.imageUrl = imageUrl;
    }

    public static CricketCardEntity from(CricketCard card) {
        return new CricketCardEntity(
            card.id(), card.name(), card.role(),
            card.battingAverage(), card.bowlingAverage(),
            card.strikeRate(), card.economyRate(),
            card.fieldingRating(), card.imageUrl()
        );
    }

    public CricketCard toDomain() {
        return new CricketCard(id, name, role, battingAverage, bowlingAverage, strikeRate, economyRate, fieldingRating, imageUrl);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public PlayerRole getRole() { return role; }
    public int getBattingAverage() { return battingAverage; }
    public int getBowlingAverage() { return bowlingAverage; }
    public int getStrikeRate() { return strikeRate; }
    public int getEconomyRate() { return economyRate; }
    public int getFieldingRating() { return fieldingRating; }
    public String getImageUrl() { return imageUrl; }
}
