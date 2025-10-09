package com.example.cadettsplitterssushi.services;

import com.example.cadettsplitterssushi.entities.Room;
import com.example.cadettsplitterssushi.exceptions.EmptyFieldException;
import com.example.cadettsplitterssushi.exceptions.IncorrectFormatException;
import com.example.cadettsplitterssushi.exceptions.ResourceNotFoundException;
import com.example.cadettsplitterssushi.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomService roomService;



    @Test
    void getAllRooms_ShouldReturnAllRooms() {
        List<Room> rooms = Arrays.asList(
                new Room(1L, "First Room", 10L, true),
                new Room(2L, "Second Room", 20L, true),
                new Room(3L, "Third Room", 15L, true)
        );
        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> foundRooms = roomService.getAllRooms();

        assertThat(foundRooms.size()).isEqualTo(3);
        assertThat(foundRooms.get(0).getId()).isEqualTo(1L);
        assertThat(foundRooms.get(1).getId()).isEqualTo(2L);
        assertThat(foundRooms.get(2).getId()).isEqualTo(3L);
        assertThat(foundRooms.get(0).getName()).isEqualTo("First Room");
        assertThat(foundRooms.get(1).getName()).isEqualTo("Second Room");
        assertThat(foundRooms.get(2).getName()).isEqualTo("Third Room");

        verify(roomRepository).findAll();

    }

    @Test
    void addNewRoom_ShouldReturnNewRoom(){
        Room room = new Room(1L, "New Room", 12L, true);
        when(roomRepository.save(room)).thenReturn(room);

        Room savedRoom = roomService.addNewRoom(room);

        assertNotNull(savedRoom);
        assertEquals(1L, savedRoom.getId());
        assertEquals("New Room", savedRoom.getName());
        assertEquals(12L, savedRoom.getGuestCapacity());
        assertEquals(true, savedRoom.getAvailable());

        verify(roomRepository).save(room);
    }

    @Test
    void updateRoom_ShouldReturnUpdatedRoom() {
        Optional<Room> originalRoom = Optional.of(new Room(1L, "New Room", 12L, true));
        Room updatedRoom = new Room(1L, "Updated Room", 10L, false);
        given(roomRepository.findById(1L)).willReturn(originalRoom);
        given(roomRepository.save(ArgumentMatchers.isA(Room.class))).willReturn(updatedRoom);


        Room savedRoom = roomService.updateRoom(updatedRoom);

        assertNotNull(savedRoom);
        assertEquals(1L,savedRoom.getId());
        assertEquals("Updated Room", savedRoom.getName());
        assertEquals(10L,savedRoom.getGuestCapacity());
        assertEquals(false, savedRoom.getAvailable());

        verify(roomRepository).findById(1L);
        verify(roomRepository).save(ArgumentMatchers.isA(Room.class));
    }

    @Test
    void addNewRoom_ShouldThrowIncorrectFormatExceptionForRoomNameWithNullValue() {
        Room room = new Room();

        IncorrectFormatException thrown = assertThrows(IncorrectFormatException.class, () -> roomService.addNewRoom(room));

        assertEquals("Room with name value 'null' is invalid. Please enter the value in the following format: String, ex. Sushi Place.",
                thrown.getMessage());
    }
    @Test
    void addNewRoom_ShouldThrowIncorrectFormatExceptionForRoomNameWithEmptyString() {
        Room room = new Room();
        room.setName("");

        IncorrectFormatException thrown = assertThrows(IncorrectFormatException.class, () -> roomService.addNewRoom(room));

        assertEquals("Room with name value '' is invalid. Please enter the value in the following format: String, ex. Sushi Place.",
                thrown.getMessage());
    }
    @Test
    void addNewRoom_ShouldThrowIncorrectFormatExceptionForGuestCapacityWithNullValue() {
        Room room = new Room();
        room.setName("New Room");

        IncorrectFormatException thrown = assertThrows(IncorrectFormatException.class, () -> roomService.addNewRoom(room));

        assertEquals("Room with guestCapacity value 'null' is invalid. Please enter the value in the following format: Integer, ex. 12. With value higher than 0",
                thrown.getMessage());

    }
    @Test
    void addNewRoom_ShouldThrowIncorrectFormatExceptionForGuestCapacityWith0Value() {
        Room room = new Room();
        room.setName("New Room");
        room.setGuestCapacity(0L);

        IncorrectFormatException thrown = assertThrows(IncorrectFormatException.class, () -> roomService.addNewRoom(room));

        assertEquals("Room with guestCapacity value '0' is invalid. Please enter the value in the following format: Integer, ex. 12. With value higher than 0",
                thrown.getMessage());

    }
    @Test
    void addNewRoom_ShouldFillInAvailableAndReturnNewRoom() {
        Room room = new Room();
        room.setId(1L);
        room.setName("New Room");
        room.setGuestCapacity(12L);
        Room returnedRoom = new Room(1L, "New Room", 12L, true);
        when(roomRepository.save(room)).thenReturn(returnedRoom);

        Room savedRoom = roomService.addNewRoom(room);

        assertNotNull(savedRoom);
        assertEquals(1L,savedRoom.getId());
        assertEquals("New Room",savedRoom.getName());
        assertEquals(12L,savedRoom.getGuestCapacity());
        assertEquals(true, savedRoom.getAvailable());

        verify(roomRepository).save(room);
    }

    @Test
    void updateRoom_ShouldThrowResourceNotFoundExceptionWhenInvalidRoomIdUsed() {
        Room updatedRoom = new Room(1L, "Updated Room", 10L, false);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> roomService.updateRoom(updatedRoom));

        assertEquals("Room with ID '1' not found.", thrown.getMessage());
    }

    @Test
    void updateRoom_ShouldThrowEmptyFieldExceptionWhenUpdatingNameWithEmptyString() {
        Optional<Room> originalRoom = Optional.of(new Room(1L, "New Room", 12L, true));
        Room updatedRoom = new Room(1L, "", 10L, false);
        given(roomRepository.findById(1L)).willReturn(originalRoom);


        EmptyFieldException thrown = assertThrows(EmptyFieldException.class, () -> roomService.updateRoom(updatedRoom));

        assertEquals("Cannot create a Room object with an empty name field.", thrown.getMessage());
    }


    @Test
    void updateRoom_ShouldThrowIncorrectFormatExceptionWhenUpdatingGuestCapacityWith0Value() {
        Optional<Room> originalRoom = Optional.of(new Room(1L, "New Room", 12L, true));
        Room updatedRoom = new Room(1L, "Updated Room", 0L, false);
        given(roomRepository.findById(1L)).willReturn(originalRoom);


        IncorrectFormatException thrown = assertThrows(IncorrectFormatException.class, () -> roomService.updateRoom(updatedRoom));

        assertEquals("Room with guestCapacity value '0' is invalid. Please enter the value in the following format: Integer, ex. 12. With value higher than 0",
                thrown.getMessage());
    }

    @Test
    void updateRoom_ShouldReturnUpdatedRoomWithDataFilledFromDatabase() {
        Optional<Room> originalRoom = Optional.of(new Room(1L, "New Room", 12L, true));
        Room updatedRoom = new Room(1L, "New Room", 12L, true);
        Room roomWithoutData = new Room(1L, null, null, null);
        given(roomRepository.findById(1L)).willReturn(originalRoom);
        given(roomRepository.save(ArgumentMatchers.isA(Room.class))).willReturn(updatedRoom);


        Room savedRoom = roomService.updateRoom(roomWithoutData);

        assertNotNull(savedRoom);
        assertEquals(1L,savedRoom.getId());
        assertEquals("New Room",savedRoom.getName());
        assertEquals(12L,savedRoom.getGuestCapacity());
        assertEquals(true, savedRoom.getAvailable());

        verify(roomRepository).findById(1L);
        verify(roomRepository).save(ArgumentMatchers.isA(Room.class));
    }

}