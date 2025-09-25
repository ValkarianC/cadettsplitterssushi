package com.example.cadettsplitterssushi.repositories;

import com.example.cadettsplitterssushi.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findRoomByName(String name);
}
