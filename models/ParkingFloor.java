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
    
    public ParkingFloor(ParkingFloor other) {
        this.floorNo = other.floorNo;
        this.spots = new ArrayList<>();
        for (ParkingSpot spot : other.spots) {
            this.spots.add(new ParkingSpot(spot));
        }
    }
    
    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }
    
    public List<ParkingSpot> getSpots() {
        List<ParkingSpot> spotsCopy = new ArrayList<>();
        for (ParkingSpot spot : spots) {
            spotsCopy.add(new ParkingSpot(spot));
        }
        return spotsCopy;
    }
    
    public int getFloorNo() {
        return floorNo;
    }
}
