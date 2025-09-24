package vehicles;

import enums.VehicleType;
import interfaces.IElectricVehicle;

public class ElectricBike extends Vehicle implements IElectricVehicle {
    private boolean wantsCharging;
    
    public ElectricBike(String vehicleNo) {
        super(vehicleNo);
        this.wantsCharging = false;
    }
    
    @Override
    public VehicleType getType() {
        return VehicleType.ELECTRIC_BIKE;
    }
    
    @Override
    public boolean wantsCharging() {
        return wantsCharging;
    }
    
    @Override
    public void setWantsCharging(boolean wantsCharging) {
        this.wantsCharging = wantsCharging;
    }
}
