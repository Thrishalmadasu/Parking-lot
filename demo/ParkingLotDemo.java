package demo;

import builders.ParkingLotBuilder;
import core.ParkingLot;
import models.*;
import vehicles.*;
import enums.*;
import strategies.*;
import java.util.HashMap;
import java.util.Map;

public class ParkingLotDemo {
    public static void main(String[] args) {
        
        ParkingLot mall = buildSmartParkingLot();
        addGatesToMall(mall);
        showParkingStatus(mall);
        simulateRealWorldUsage(mall);
    }
    
    private static ParkingLot buildSmartParkingLot() {
        Map<SpotType, Integer> spotConfiguration = new HashMap<>();
        spotConfiguration.put(SpotType.SMALL, 5);
        spotConfiguration.put(SpotType.MEDIUM, 3);
        spotConfiguration.put(SpotType.LARGE, 2);
        spotConfiguration.put(SpotType.ELECTRIC, 2);
        
        return new ParkingLotBuilder()
                .setFloors(2)
                .setSpotsPerFloor(spotConfiguration)
                .setAllocationStrategy(new NearestSlotStrategy())
                .build();
    }
    
    private static void addGatesToMall(ParkingLot mall) {
        EntryGate mainEntrance = new EntryGate(1);
        EntryGate sideEntrance = new EntryGate(2);
        
        ExitGate cashierGate = new ExitGate(1, new HourlyPricingStrategy());
        ExitGate expressGate = new ExitGate(2, new HourlyPricingStrategy());
        
        mall.addEntryGate(mainEntrance);
        mall.addEntryGate(sideEntrance);
        mall.addExitGate(cashierGate);
        mall.addExitGate(expressGate);
    }
    
    private static void showParkingStatus(ParkingLot mall) {
        System.out.println("Mall Parking Status:");
        System.out.println("Entry Points: " + mall.getEntryGates().size());
        System.out.println("Exit Points: " + mall.getExitGates().size());
        System.out.println("Total Floors: " + mall.getFloors().size());
        System.out.println("Empty Spots: " + mall.getAvailableSpots().size());
        System.out.println();
    }
    
    private static void simulateRealWorldUsage(ParkingLot mall) {
        System.out.println("Let's see how vehicles use our parking system:\n");
        
        Vehicle myBike = new Bike("KA01AB1234");
        Vehicle familyCar = new Car("KA05EF9012");        
        ElectricBike ecoFriendlyBike = new ElectricBike("KA02CD5678");
        ecoFriendlyBike.setWantsCharging(true);
        
        EntryGate mainGate = mall.getEntryGates().get(0);
        ExitGate paymentCounter = mall.getExitGates().get(0);
        
        System.out.println("A bike arrives at the mall:");
        Ticket bikeTicket = mainGate.generateTicket(myBike);
        if (bikeTicket != null) {
            System.out.println("Great! Found a " + bikeTicket.getSpot().getSpotType() + " spot for " + bikeTicket.getVehicle().getVehicleNo());
            System.out.println("Charging needed: " + bikeTicket.isUsingCharging() + "\n");
        }
        
        System.out.println("An electric bike needs charging:");
        Ticket eBikeTicket = mainGate.generateTicket(ecoFriendlyBike);
        if (eBikeTicket != null) {
            System.out.println("Perfect! Electric bike " + eBikeTicket.getVehicle().getVehicleNo() + " got a " + eBikeTicket.getSpot().getSpotType() + " spot");
            System.out.println("Charging station: " + eBikeTicket.isUsingCharging() + "\n");
        }
        
        System.out.println("A family car arrives:");
        Ticket carTicket = mainGate.generateTicket(familyCar);
        if (carTicket != null) {
            System.out.println("Car " + carTicket.getVehicle().getVehicleNo() + " parked in " + carTicket.getSpot().getSpotType() + " area\n");
        }
        
        System.out.println("Time to leave - the bike owner comes back:");
        if (bikeTicket != null) {
            try {
                Thread.sleep(1000);
                double parkingFee = paymentCounter.processExit(bikeTicket);
                bikeTicket.getSpot().vacate();
                System.out.println("Bike " + bikeTicket.getVehicle().getVehicleNo() + " left the mall");
                System.out.println("Parking fee: $" + String.format("%.2f", parkingFee) + "\n");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("Available spots now: " + mall.getAvailableSpots().size());
        System.out.println("\nThanks for using our Smart Parking System!");
    }
}
