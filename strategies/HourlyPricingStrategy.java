package strategies;

import interfaces.IPricingStrategy;
import models.Ticket;
import enums.VehicleType;
import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyPricingStrategy implements IPricingStrategy {
    private static final double BIKE_HOURLY_COST = 2.0;
    private static final double CAR_HOURLY_COST = 5.0;
    private static final double BUS_HOURLY_COST = 10.0;
    private static final double CHARGING_HOURLY_COST = 3.0;
    private static final double MINIMUM_PARKING_FEE = 1.0;
    
    @Override
    public double calculatePrice(Ticket ticket) {
        Duration timeParked = Duration.between(ticket.getEntryTime(), LocalDateTime.now());
        
        double parkingCost = calculateBaseParkingCost(ticket.getVehicle().getType(), timeParked);
        double chargingCost = ticket.isUsingCharging() ? calculateChargingCost(timeParked) : 0.0;
        
        return parkingCost + chargingCost;
    }
    
    private double calculateBaseParkingCost(VehicleType vehicleType, Duration timeParked) {
        double ratePerHour = getRateForVehicle(vehicleType);
        long totalMinutes = timeParked.toMinutes();
        long hoursToCharge = Math.max(1, (totalMinutes + 59) / 60);
        double totalCost = ratePerHour * hoursToCharge;
        return Math.max(totalCost, MINIMUM_PARKING_FEE);
    }
    
    private double calculateChargingCost(Duration timeParked) {
        long totalMinutes = timeParked.toMinutes();
        long hoursOfCharging = Math.max(1, (totalMinutes + 59) / 60);
        return CHARGING_HOURLY_COST * hoursOfCharging;
    }
    
    private double getRateForVehicle(VehicleType vehicleType) {
        switch (vehicleType) {
            case BIKE:
            case ELECTRIC_BIKE:
                return BIKE_HOURLY_COST;
            case CAR:
                return CAR_HOURLY_COST;
            case BUS:
                return BUS_HOURLY_COST;
            default:
                throw new IllegalArgumentException("I don't know how to price this vehicle: " + vehicleType);
        }
    }
}
