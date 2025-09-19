package com.example.cadettsplitterssushi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sushi_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 30, name = "room_name")
    private String name;

    @Column(name = "room_capacity")
    private long guestCapacity;

    public Room() {
    }

    public Room(long id, String name, long guestCapacity) {
        this.id = id;
        this.name = name;
        this.guestCapacity = guestCapacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGuestCapacity() {
        return guestCapacity;
    }

    public void setGuestCapacity(long guestCapacity) {
        this.guestCapacity = guestCapacity;
    }
}
