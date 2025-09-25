package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.entities.Room;

import java.util.List;

public interface RoomServiceInterface {
    List<Room> getAllRooms();
    Room addNewRoom(Room room);
    Room updateRoom(Room room);
}
