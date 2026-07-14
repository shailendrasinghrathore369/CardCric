package com.cardcric.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String displayName;

    @ElementCollection
    @CollectionTable(name = "player_cards", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "card_id")
    private List<UUID> cardIds = new ArrayList<>();

    protected PlayerEntity() {}

    public PlayerEntity(UUID id, String username, String displayName, List<UUID> cardIds) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.cardIds = new ArrayList<>(cardIds);
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public List<UUID> getCardIds() { return cardIds; }
}
