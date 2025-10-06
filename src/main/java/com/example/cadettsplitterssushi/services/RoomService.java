package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.entities.Room;
import com.example.cadettsplitterssushi.exceptions.EmptyFieldException;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements RoomServiceInterface {

    private final RoomRepository roomRepository;

    private static final Logger logger = LoggerFactory.getLogger("LogFile");

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
        Room roomToSave = roomRepository.save(room);
        logger.info("User added a new room - [{}] to the database. Room ID: {}", roomToSave.getName(), roomToSave.getId());
        return roomToSave;
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
        Room updatedRoom = roomRepository.save(roomToUpdate);
        logger.info("User updated information in database regarding room [{}]. Room ID: {}", updatedRoom.getName(),updatedRoom.getId());
        return updatedRoom;
    }
}
