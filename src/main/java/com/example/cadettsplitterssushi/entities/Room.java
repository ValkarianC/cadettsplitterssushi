package com.example.cadettsplitterssushi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sushi_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, name = "room_name", nullable = false)
    private String name;

    @Column(name = "room_capacity", nullable = false)
    private Long guestCapacity;

    @Column(name = "room_is_available", nullable = false)
    private Boolean isAvailable;

    public Room() {
    }

    public Room(Long id, String name, Long guestCapacity, Boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.guestCapacity = guestCapacity;
        this.isAvailable = isAvailable;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGuestCapacity() {
        return guestCapacity;
    }

    public void setGuestCapacity(Long guestCapacity) {
        this.guestCapacity = guestCapacity;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
