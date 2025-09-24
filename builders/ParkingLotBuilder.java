package builders;

import enums.SpotType;
import interfaces.ISlotAllocationStrategy;
import interfaces.IPricingStrategy;
import models.ParkingFloor;
import models.ParkingSpot;
import java.util.Map;
import java.util.HashMap;

public class ParkingLotBuilder {
    private int totalFloors;
    private Map<SpotType, Integer> spotLayout;
    private ISlotAllocationStrategy howToFindSpots;
    private IPricingStrategy howToCalculatePrice;
    
    public ParkingLotBuilder() {
        this.spotLayout = new HashMap<>();
    }
    
    public ParkingLotBuilder setFloors(int floors) {
        this.totalFloors = floors;
        return this;
    }
    
    public ParkingLotBuilder setSpotsPerFloor(Map<SpotType, Integer> layout) {
        this.spotLayout = layout;
        return this;
    }
    
    public ParkingLotBuilder setAllocationStrategy(ISlotAllocationStrategy strategy) {
        this.howToFindSpots = strategy;
        return this;
    }
    
    public ParkingLotBuilder setPricingStrategy(IPricingStrategy pricing) {
        this.howToCalculatePrice = pricing;
        return this;
    }
    
    public core.ParkingLot build() {
        if (howToFindSpots == null) {
            throw new IllegalStateException("You need to tell me how to find parking spots!");
        }
        
        core.ParkingLot newParkingLot = new core.ParkingLot(howToFindSpots);
        
        for (int floorNumber = 1; floorNumber <= totalFloors; floorNumber++) {
            ParkingFloor currentFloor = new ParkingFloor(floorNumber);
            
            for (Map.Entry<SpotType, Integer> spotConfig : spotLayout.entrySet()) {
                SpotType typeOfSpot = spotConfig.getKey();
                Integer howManySpots = spotConfig.getValue();
                
                for (int spotIndex = 0; spotIndex < howManySpots; spotIndex++) {
                    boolean needsCharging = (typeOfSpot == SpotType.ELECTRIC);
                    ParkingSpot newSpot = new ParkingSpot(typeOfSpot, needsCharging);
                    currentFloor.addSpot(newSpot);
                }
            }
            
            newParkingLot.addFloor(currentFloor);
        }
        
        return newParkingLot;
    }
}
