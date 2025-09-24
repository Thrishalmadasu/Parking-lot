package core;

import interfaces.ISlotAllocationStrategy;
import models.*;
import vehicles.Vehicle;
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
                    emptySpots.add(spot);
                }
            }
        }
        return emptySpots;
    }
    
    public List<ParkingFloor> getFloors() {
        return new ArrayList<>(floors);
    }
    
    public List<EntryGate> getEntryGates() {
        return new ArrayList<>(entryGates);
    }
    
    public List<ExitGate> getExitGates() {
        return new ArrayList<>(exitGates);
    }
    
    public ISlotAllocationStrategy getAllocationStrategy() {
        return allocationStrategy;
    }
}
