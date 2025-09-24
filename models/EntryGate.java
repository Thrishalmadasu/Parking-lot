package models;

import core.ParkingLot;
import vehicles.Vehicle;
import interfaces.IElectricVehicle;

public class EntryGate {
    private final int gateId;
    private ParkingLot parkingLot;
    
    public EntryGate(int gateId) {
        this.gateId = gateId;
    }
    
    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
    
    public Ticket generateTicket(Vehicle vehicle) {
        if (parkingLot == null) {
            throw new IllegalStateException("This gate isn't connected to a parking lot yet!");
        }
        
        ParkingSpot availableSpot = parkingLot.getAllocationStrategy().findSpot(parkingLot, vehicle);
        if (availableSpot == null) {
            return null;
        }
        
        boolean needsChargingStation = false;
        if (vehicle instanceof IElectricVehicle) {
            IElectricVehicle electricVehicle = (IElectricVehicle) vehicle;
            needsChargingStation = electricVehicle.wantsCharging() && availableSpot.hasChargingPoint();
        }
        
        availableSpot.parkVehicle(vehicle);
        return new Ticket(vehicle, availableSpot, needsChargingStation);
    }
    
    public int getGateId() {
        return gateId;
    }
}
