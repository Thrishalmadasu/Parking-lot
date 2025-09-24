package interfaces;

import models.Ticket;

public interface IPricingStrategy {
    double calculatePrice(Ticket ticket);
}
