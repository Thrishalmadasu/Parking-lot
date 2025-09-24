package interfaces;

import core.ParkingLot;
import models.ParkingSpot;
import vehicles.Vehicle;

public interface ISlotAllocationStrategy {
    ParkingSpot findSpot(ParkingLot parkingLot, Vehicle vehicle);
}
