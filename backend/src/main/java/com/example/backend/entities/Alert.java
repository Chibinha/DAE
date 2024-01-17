package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
@Table(name = "alert")
@NamedQueries({
        @NamedQuery(
                name = "getAllAlerts",
                query = "SELECT a FROM Alert a ORDER BY a.id"
        )
})
public class Alert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "username")
    @NotNull
    public User user;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    private Timestamp timestamp;

    public Alert() {

    }

    public Alert(User user, String description) {
        this.user = user;
        this.description = description;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
