package strategies;

import core.ParkingLot;
import interfaces.ISlotAllocationStrategy;
import models.ParkingSpot;
import vehicles.Vehicle;
import java.util.List;

public class NearestSlotStrategy implements ISlotAllocationStrategy {
    
    @Override
    public ParkingSpot findSpot(ParkingLot parkingLot, Vehicle vehicle) {
        List<ParkingSpot> emptySpots = parkingLot.getAvailableSpots();
        
        for (ParkingSpot spot : emptySpots) {
            if (vehicleCanFitInSpot(spot, vehicle)) {
                return spot;
            }
        }
        
        return null;
    }
    
    private boolean vehicleCanFitInSpot(ParkingSpot spot, Vehicle vehicle) {
        return spot.isAvailable();
    }
}
