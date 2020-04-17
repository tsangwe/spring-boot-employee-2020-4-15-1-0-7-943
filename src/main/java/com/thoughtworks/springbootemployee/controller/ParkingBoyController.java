package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-boy")
public class ParkingBoyController {
    @Autowired
    private ParkingBoyService parkingBoyService;

    @GetMapping(path = "/parking-boys")
    public List<ParkingBoy> getParkingBoys() {
        return parkingBoyService.getAllParkingBoys();
    }

    @PostMapping(path = "/parking-boys")
    public ParkingBoy addParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        return parkingBoyService.addParkingBoy(parkingBoy);
    }

    @DeleteMapping(path = "/parking-boys/{parkingBoyId}")
    public void deleteParkingBoy(@PathVariable int parkingBoyId) {
        parkingBoyService.deleteParkingBoy(parkingBoyId);
    }
}
