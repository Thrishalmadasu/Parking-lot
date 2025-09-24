package core;

import interfaces.ISlotAllocationStrategy;
import models.*;
import java.util.*;

public class ParkingLot {
    private final List<ParkingFloor> floors;
    private final ISlotAllocationStrategy allocationStrategy;
    private final List<EntryGate> entryGates;
    private final List<ExitGate> exitGates;
    
    public ParkingLot(ISlotAllocationStrategy allocationStrategy) {
        this.floors = new ArrayList<>();
        this.allocationStrategy = allocationStrategy;
        this.entryGates = new ArrayList<>();
        this.exitGates = new ArrayList<>();
    }
    
    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }
    
    public void addEntryGate(EntryGate gate) {
        gate.setParkingLot(this);
        entryGates.add(gate);
    }
    
    public void addExitGate(ExitGate gate) {
        exitGates.add(gate);
    }
    
    public List<ParkingSpot> getAvailableSpots() {
        List<ParkingSpot> emptySpots = new ArrayList<>();
        for (ParkingFloor floor : floors) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.isAvailable()) {
                    emptySpots.add(new ParkingSpot(spot));
                }
            }
        }
        return emptySpots;
    }
    
    public List<ParkingFloor> getFloors() {
        List<ParkingFloor> floorsCopy = new ArrayList<>();
        for (ParkingFloor floor : floors) {
            floorsCopy.add(new ParkingFloor(floor));
        }
        return floorsCopy;
    }
    
    public List<EntryGate> getEntryGates() {
        List<EntryGate> gatesCopy = new ArrayList<>();
        for (EntryGate gate : entryGates) {
            gatesCopy.add(new EntryGate(gate));
        }
        return gatesCopy;
    }
    
    public List<ExitGate> getExitGates() {
        List<ExitGate> gatesCopy = new ArrayList<>();
        for (ExitGate gate : exitGates) {
            gatesCopy.add(new ExitGate(gate));
        }
        return gatesCopy;
    }
    
    public ISlotAllocationStrategy getAllocationStrategy() {
        return allocationStrategy;
    }
}
