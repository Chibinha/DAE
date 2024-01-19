package com.example.backend.dtos;

import java.sql.Timestamp;

public class AlertDTO {
    public String username;

    private String description;

    private Timestamp timestamp;

    public AlertDTO() {
    }

    public AlertDTO(String username, String description, Timestamp timestamp) {
        this.username = username;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
