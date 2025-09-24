package models;

import interfaces.IPricingStrategy;

public class ExitGate {
    private final int gateId;
    private final IPricingStrategy pricingStrategy;
    
    public ExitGate(int gateId, IPricingStrategy pricingStrategy) {
        this.gateId = gateId;
        this.pricingStrategy = pricingStrategy;
    }
    
    public double processExit(Ticket ticket) {
        return pricingStrategy.calculatePrice(ticket);
    }
    
    public int getGateId() {
        return gateId;
    }
}
