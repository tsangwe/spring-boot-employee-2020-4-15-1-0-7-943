package com.thoughtworks.springbootemployee.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "parking_boy")
public class ParkingBoy {
    @Id
    private Integer parkingBoyId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkingBoyId")
    private Employee employee;
    private String nickName;
}
