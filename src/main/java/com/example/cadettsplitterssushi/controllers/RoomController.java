package com.example.cadettsplitterssushi.controllers;

import com.example.cadettsplitterssushi.entities.Room;
import com.example.cadettsplitterssushi.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/wigellsushi")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    //ADMIN & USER ENDPOINTS

    @GetMapping("/rooms")
    public ResponseEntity<Object> getRooms(){
        List<Room> availableRooms = roomService.getAllRooms();
        if (availableRooms.isEmpty()){
            return new ResponseEntity<>("No rooms in database.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(availableRooms, HttpStatus.OK);
        }
    }

    //ADMIN ENDPOINTS

    @PostMapping("/addroom")
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        return new ResponseEntity<>(roomService.addNewRoom(room), HttpStatus.CREATED);
    }
    @PutMapping(("/updateroom"))
    public ResponseEntity<Room> updateRoom(@RequestBody Room room){
        return new ResponseEntity<>(roomService.updateRoom(room), HttpStatus.OK);
    }
}
