package org.example;

public class Ship{
    int cargo;
    String location;
    boolean inMaintainence;
    int trips;

    public Ship(String location) {
        this.cargo=0;
        this.trips = 0;
        this.inMaintainence = false;
        this.location = location;
    }
}
