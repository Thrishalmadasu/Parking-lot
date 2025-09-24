package models;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {
    private final int floorNo;
    private final List<ParkingSpot> spots;
    
    public ParkingFloor(int floorNo) {
        this.floorNo = floorNo;
        this.spots = new ArrayList<>();
    }
    
    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }
    
    public List<ParkingSpot> getSpots() {
        return new ArrayList<>(spots);
    }
    
    public int getFloorNo() {
        return floorNo;
    }
}
