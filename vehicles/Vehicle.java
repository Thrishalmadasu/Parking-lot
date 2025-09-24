package vehicles;

import enums.VehicleType;

public abstract class Vehicle {
    private final String vehicleNo;
    
    public Vehicle(String vehicleNo) {
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be null or empty");
        }
        this.vehicleNo = vehicleNo.trim().toUpperCase();
    }
    
    public String getVehicleNo() {
        return vehicleNo;
    }
    
    public abstract VehicleType getType();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle vehicle = (Vehicle) obj;
        return vehicleNo.equals(vehicle.vehicleNo);
    }
    
    @Override
    public int hashCode() {
        return vehicleNo.hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s{vehicleNo='%s'}", getType(), vehicleNo);
    }
}
