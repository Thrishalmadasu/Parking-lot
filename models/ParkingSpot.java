package models;

import enums.SpotType;
import vehicles.Vehicle;

public class ParkingSpot {
    private final SpotType spotType;
    private final boolean hasChargingPoint;
    private boolean isAvailable;
    private Vehicle currentVehicle;
    
    public ParkingSpot(SpotType spotType, boolean hasChargingPoint) {
        this.spotType = spotType;
        this.hasChargingPoint = hasChargingPoint;
        this.isAvailable = true;
        this.currentVehicle = null;
    }
    
    public ParkingSpot(ParkingSpot other) {
        this.spotType = other.spotType;
        this.hasChargingPoint = other.hasChargingPoint;
        this.isAvailable = other.isAvailable;
        this.currentVehicle = other.currentVehicle;
    }
    
    public void parkVehicle(Vehicle vehicle) {
        if (!isAvailable) {
            throw new IllegalStateException("Parking spot is already occupied");
        }
        this.currentVehicle = vehicle;
        this.isAvailable = false;
    }
    
    public void vacate() {
        this.currentVehicle = null;
        this.isAvailable = true;
    }
    
    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
    
    public SpotType getSpotType() {
        return spotType;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public boolean hasChargingPoint() {
        return hasChargingPoint;
    }
}
