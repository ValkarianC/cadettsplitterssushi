package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.entities.Room;
import com.example.cadettsplitterssushi.exceptions.EmptyFieldException;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements RoomServiceInterface {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room addNewRoom(Room room) {
        if (room.getName() == null || room.getName().isBlank()) {
            throw new IncorrectFormatException("Room", "name", room.getName(), "String, ex. Sushi Place.");
        }
        if (room.getGuestCapacity() == null || room.getGuestCapacity() == 0){
            throw new IncorrectFormatException("Room", "guestCapacity", room.getGuestCapacity(), "Integer, ex. 12. With value higher than 0");
        }
        if (room.getAvailable() == null){
            room.setAvailable(true);
        }
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Room room) {
        Optional<Room> roomCheck = roomRepository.findById(room.getId());
        Room roomToUpdate = new Room();
        if (roomCheck.isPresent()){
            roomToUpdate.setId(roomCheck.get().getId());
            if (room.getName() == null){
                roomToUpdate.setName(roomCheck.get().getName());
            } else {
                roomToUpdate.setName(room.getName());
            }
            if (room.getGuestCapacity() == null){
                roomToUpdate.setGuestCapacity(roomCheck.get().getGuestCapacity());
            } else {
                roomToUpdate.setGuestCapacity(room.getGuestCapacity());
            }
            if (room.getAvailable() == null){
                roomToUpdate.setAvailable(roomCheck.get().getAvailable());
            } else {
                roomToUpdate.setAvailable(room.getAvailable());
            }

            if (roomToUpdate.getName().isBlank()) {
                throw new EmptyFieldException("Room", "name");
            }
            if (roomToUpdate.getGuestCapacity() == 0){
                throw new IncorrectFormatException("Room", "guestCapacity", roomToUpdate.getGuestCapacity(), "Integer, ex. 12. With value higher than 0");
            }
        } else {
            throw new ResourceNotFoundException("Room", "ID", room.getId());
        }

        return roomRepository.save(roomToUpdate);
    }
}
