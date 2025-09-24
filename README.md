# Smart Parking Lot System

An implementation of a parking lot management system demonstrating object-oriented design patterns with multi-floor support, electric vehicle charging, and flexible strategies.

## Features

- **Multi-floor Structure**: Configurable floors with different spot types
- **Vehicle Support**: Cars, Bikes, Buses, and Electric Bikes with charging
- **Smart Allocation**: Strategy-based spot assignment algorithms
- **Dynamic Pricing**: Configurable pricing strategies per exit gate
- **Gate Management**: Separate entry and exit processing
- **Builder Pattern**: Flexible parking lot construction

## Class Diagram

```mermaid
classDiagram
    %% Enums
    class VehicleType {
        <<enumeration>>
        CAR
        BIKE
        BUS
        ELECTRIC_BIKE
    }

    class SpotType {
        <<enumeration>>
        SMALL
        MEDIUM
        LARGE
        ELECTRIC
    }

    %% Interfaces
    class IElectricVehicle {
        <<interface>>
        +wantsCharging() boolean
        +setWantsCharging(boolean) void
    }

    class ISlotAllocationStrategy {
        <<interface>>
        +findSpot(ParkingLot, Vehicle) ParkingSpot
    }

    class IPricingStrategy {
        <<interface>>
        +calculatePrice(Ticket) double
    }

    %% Vehicle Hierarchy
    class Vehicle {
        <<abstract>>
        -vehicleNo String
        +Vehicle(String)
        +getVehicleNo() String
        +getType()* VehicleType
        +equals(Object) boolean
        +hashCode() int
        +toString() String
    }

    class Car {
        +getType() VehicleType
    }

    class Bike {
        +getType() VehicleType
    }

    class Bus {
        +getType() VehicleType
    }

    class ElectricBike {
        -wantsCharging boolean
        +getType() VehicleType
        +wantsCharging() boolean
        +setWantsCharging(boolean) void
    }

    %% Core Model Classes
    class ParkingSpot {
        -spotType SpotType
        -hasChargingPoint boolean
        -isAvailable boolean
        -currentVehicle Vehicle
        +ParkingSpot(SpotType, boolean)
        +ParkingSpot(ParkingSpot)
        +parkVehicle(Vehicle) void
        +vacate() void
        +getCurrentVehicle() Vehicle
        +getSpotType() SpotType
        +isAvailable() boolean
        +hasChargingPoint() boolean
    }

    class ParkingFloor {
        -floorNo int
        -spots List~ParkingSpot~
        +ParkingFloor(int)
        +ParkingFloor(ParkingFloor)
        +addSpot(ParkingSpot) void
        +getSpots() List~ParkingSpot~
        +getFloorNo() int
    }

    class ParkingLot {
        -floors List~ParkingFloor~
        -allocationStrategy ISlotAllocationStrategy
        -entryGates List~EntryGate~
        -exitGates List~ExitGate~
        +ParkingLot(ISlotAllocationStrategy)
        +addFloor(ParkingFloor) void
        +addEntryGate(EntryGate) void
        +addExitGate(ExitGate) void
        +getAvailableSpots() List~ParkingSpot~
        +getFloors() List~ParkingFloor~
        +getEntryGates() List~EntryGate~
        +getExitGates() List~ExitGate~
        +getAllocationStrategy() ISlotAllocationStrategy
    }

    class EntryGate {
        -gateId int
        -parkingLot ParkingLot
        +EntryGate(int)
        +EntryGate(EntryGate)
        +setParkingLot(ParkingLot) void
        +generateTicket(Vehicle) Ticket
        +getGateId() int
    }

    class ExitGate {
        -gateId int
        -pricingStrategy IPricingStrategy
        +ExitGate(int, IPricingStrategy)
        +ExitGate(ExitGate)
        +processExit(Ticket) double
        +getGateId() int
    }

    class Ticket {
        -ticketId String
        -vehicle Vehicle
        -spot ParkingSpot
        -entryTime LocalDateTime
        -isUsingCharging boolean
        +Ticket(Vehicle, ParkingSpot, boolean)
        +getTicketId() String
        +getVehicle() Vehicle
        +getSpot() ParkingSpot
        +getEntryTime() LocalDateTime
        +isUsingCharging() boolean
    }

    %% Strategy Implementations
    class NearestSlotStrategy {
        +findSpot(ParkingLot, Vehicle) ParkingSpot
    }

    class HourlyPricingStrategy {
        -BIKE_HOURLY_COST double
        -CAR_HOURLY_COST double
        -BUS_HOURLY_COST double
        -CHARGING_HOURLY_COST double
        -MINIMUM_PARKING_FEE double
        +calculatePrice(Ticket) double
    }

    %% Builder
    class ParkingLotBuilder {
        -totalFloors int
        -spotLayout Map~SpotType_Integer~
        -slotAllocationStrategy ISlotAllocationStrategy
        +setFloors(int) ParkingLotBuilder
        +setSpotsPerFloor(Map) ParkingLotBuilder
        +setAllocationStrategy(ISlotAllocationStrategy) ParkingLotBuilder
        +build() ParkingLot
    }

    %% Relationships
    Vehicle <|-- Car
    Vehicle <|-- Bike
    Vehicle <|-- Bus
    Vehicle <|-- ElectricBike
    ElectricBike ..|> IElectricVehicle

    NearestSlotStrategy ..|> ISlotAllocationStrategy
    HourlyPricingStrategy ..|> IPricingStrategy

    ParkingLot *-- ParkingFloor
    ParkingFloor *-- ParkingSpot
    ParkingLot *-- EntryGate
    ParkingLot *-- ExitGate
    ParkingLot --> ISlotAllocationStrategy

    EntryGate --> Ticket
    ExitGate --> IPricingStrategy
    Ticket --> Vehicle
    Ticket --> ParkingSpot
    ParkingSpot --> Vehicle
    ParkingSpot --> SpotType
    Vehicle --> VehicleType

    ParkingLotBuilder --> ParkingLot
    ParkingLotBuilder --> ISlotAllocationStrategy
```

## Quick Usage

```java
// Build parking lot
Map<SpotType, Integer> layout = Map.of(
    SpotType.SMALL, 5,    SpotType.MEDIUM, 3,
    SpotType.LARGE, 2,    SpotType.ELECTRIC, 2
);

ParkingLot mall = new ParkingLotBuilder()
    .setFloors(2)
    .setSpotsPerFloor(layout)
    .setAllocationStrategy(new NearestSlotStrategy())
    .build();

// Add gates
mall.addEntryGate(new EntryGate(1));
mall.addExitGate(new ExitGate(1, new HourlyPricingStrategy()));

// Park and exit
Vehicle bike = new Bike("KA01AB1234");
Ticket ticket = mall.getEntryGates().get(0).generateTicket(bike);
double fee = mall.getExitGates().get(0).processExit(ticket);
ticket.getSpot().vacate();
```

## Key Design Features

- **Strategy Pattern**: Pluggable allocation and pricing algorithms
- **Builder Pattern**: Flexible parking lot construction  
- **Deep Copying**: All getters return defensive copies
- **Type Safety**: Strong enum-based typing
- **Electric Vehicle Support**: Dedicated charging infrastructure
- **Extensible Architecture**: Easy to add new vehicle types and strategies

Run `ParkingLotDemo.java` to see the complete system in action.