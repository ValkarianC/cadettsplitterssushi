package com.example.cadettsplitterssushi.controllers;

import com.example.cadettsplitterssushi.entities.Room;
import com.example.cadettsplitterssushi.services.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

    @Mock
    private RoomService roomService;
    @InjectMocks
    private RoomController roomController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getRooms_ShouldReturnAllRooms() throws Exception {
        //Arrange
        List<Room> rooms = Arrays.asList(
                new Room(1L, "First Room", 10L, true),
                new Room(2L, "Second Room", 20L, true),
                new Room(3L, "Third Room", 15L, true)
        );
        when(roomService.getAllRooms()).thenReturn(rooms);
        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellsushi/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("First Room"))
                .andExpect(jsonPath("$[1].name").value("Second Room"))
                .andExpect(jsonPath("$[2].name").value("Third Room"));
        verify(roomService).getAllRooms();
    }


    @Test
    void getRooms_ShouldReturnEmptyDatabaseMessageIfDatabaseIsEmpty() throws Exception {
        //Arrange
        List<Room> rooms = new ArrayList<>();
        when(roomService.getAllRooms()).thenReturn(rooms);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/wigellsushi/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().string("No rooms in database."));
        verify(roomService).getAllRooms();
    }

    @Test
    void addRoom_ShouldReturnCreatedRoom() throws Exception {
        //Arrange
        Room room = new Room(1L,"New Room", 13L, true);
        String json = objectMapper.writeValueAsString(room);
        when(roomService.addNewRoom(ArgumentMatchers.isA(Room.class))).thenReturn(room);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/wigellsushi/addroom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Room"))
                .andExpect(jsonPath("$.guestCapacity").value(13L))
                .andExpect(jsonPath("$.available").value(true));

        verify(roomService).addNewRoom(ArgumentMatchers.isA(Room.class));
    }

    @Test
    void updateRoom_ShouldReturnUpdatedRoom() throws Exception {
        //Arrange
        Room room = new Room(1L,"Updated Room", 13L, false);
        String json = objectMapper.writeValueAsString(room);
        when(roomService.updateRoom(ArgumentMatchers.isA(Room.class))).thenReturn(room);
        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/wigellsushi/updateroom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Room"))
                .andExpect(jsonPath("$.guestCapacity").value(13L))
                .andExpect(jsonPath("$.available").value(false));
        verify(roomService).updateRoom(ArgumentMatchers.isA(Room.class));
    }
}