package models;

import vehicles.Vehicle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public final class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private final boolean isUsingCharging;
    
    public Ticket(Vehicle vehicle, ParkingSpot spot, boolean isUsingCharging) {
        this.ticketId = generateTicketId();
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.isUsingCharging = isUsingCharging;
    }
    
    public String getTicketId() { 
        return ticketId; 
    }
    
    public Vehicle getVehicle() { 
        return vehicle; 
    }
    
    public ParkingSpot getSpot() { 
        return spot; 
    }
    
    public LocalDateTime getEntryTime() { 
        return entryTime; 
    }
    
    public boolean isUsingCharging() { 
        return isUsingCharging; 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ticket ticket = (Ticket) obj;
        return Objects.equals(ticketId, ticket.ticketId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }
    
    @Override
    public String toString() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        StringBuilder receipt = new StringBuilder();
        receipt.append("==========================================\n");
        receipt.append("           YOUR PARKING RECEIPT\n");
        receipt.append("==========================================\n");
        receipt.append(String.format("Reference      : %s\n", ticketId));
        receipt.append(String.format("Vehicle        : %s (%s)\n", vehicle.getVehicleNo(), vehicle.getType()));
        receipt.append(String.format("Parked at      : %s\n", entryTime.format(timeFormat)));
        receipt.append(String.format("Charging       : %s\n", isUsingCharging ? "Yes, using charging station" : "No charging needed"));
        receipt.append("==========================================\n");
        receipt.append("Keep this safe - you'll need it to leave!\n");
        receipt.append("==========================================");
        return receipt.toString();
    }
    
    private String generateTicketId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "TKT-" + timestamp + "-" + uuid;
    }
}
