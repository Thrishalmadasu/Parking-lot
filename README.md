# Smart Parking Lot System

A comprehensive implementation of an intelligent parking lot management system using Java with Strategy Pattern, Builder Pattern, and Interface Segregation for flexible parking operations.

## Features

- **Multi-floor Parking Structure**: Support for multiple floors with configurable spots per floor
- **Vehicle Type Support**: Cars, Bikes, Buses, and Electric Bikes with charging capabilities
- **Electric Vehicle Infrastructure**: Dedicated charging spots with automatic allocation
- **Flexible Spot Types**: Small, Medium, Large, and Electric charging spots
- **Smart Gate System**: Separate entry and exit gates with automatic ticket generation
- **Dynamic Pricing**: Hourly-based pricing with charging fees for electric vehicles
- **Builder Pattern**: Flexible parking lot construction with customizable configurations
- **Strategy-based Design**: Pluggable allocation and pricing strategies for easy extensibility

## Architecture Overview

This implementation uses several design patterns:

- **Strategy Pattern**: For spot allocation algorithms and pricing calculations
- **Builder Pattern**: For flexible parking lot configuration and construction
- **Interface Segregation**: Separate interfaces for electric vehicles and core strategies
- **Composition Pattern**: ParkingFloor contains ParkingSpots, ParkingLot contains floors
- **Template Method**: Abstract Vehicle class with concrete implementations

## Class Diagram

```mermaid
classDiagram
    %% Enumerations
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

    %% Core Vehicle Hierarchy
    class Vehicle {
        <<abstract>>
        -vehicleNo String
        +Vehicle(vehicleNo String)
        +getVehicleNo() String
        +getType() VehicleType {abstract}
        +equals(Object) boolean
        +hashCode() int
        +toString() String
    }

    class Car {
        +Car(vehicleNo String)
        +getType() VehicleType
    }

    class Bike {
        +Bike(vehicleNo String)
        +getType() VehicleType
    }

    class Bus {
        +Bus(vehicleNo String)
        +getType() VehicleType
    }

    class ElectricBike {
        -wantsCharging boolean
        +ElectricBike(vehicleNo String)
        +getType() VehicleType
        +wantsCharging() boolean
        +setWantsCharging(boolean) void
    }

    %% Parking Infrastructure
    class ParkingSpot {
        -spotType SpotType
        -isAvailable boolean
        -currentVehicle Vehicle
        -hasChargingPoint boolean
        +ParkingSpot(SpotType, boolean)
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
        +ParkingFloor(floorNo int)
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

    %% Gate System
    class EntryGate {
        -gateId int
        -parkingLot ParkingLot
        +EntryGate(gateId int)
        +setParkingLot(ParkingLot) void
        +generateTicket(Vehicle) Ticket
        +getGateId() int
    }

    class ExitGate {
        -gateId int
        -pricingStrategy IPricingStrategy
        +ExitGate(gateId int, IPricingStrategy)
        +processExit(Ticket) double
        +getGateId() int
    }

    %% Ticket System
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
        +toString() String
    }

    %% Strategy Implementations
    class NearestSlotStrategy {
        +findSpot(ParkingLot, Vehicle) ParkingSpot
        -vehicleCanFitInSpot(ParkingSpot, Vehicle) boolean
    }

    class HourlyPricingStrategy {
        -BIKE_HOURLY_COST double
        -CAR_HOURLY_COST double
        -BUS_HOURLY_COST double
        -CHARGING_HOURLY_COST double
        -MINIMUM_PARKING_FEE double
        +calculatePrice(Ticket) double
        -calculateBaseParkingCost(VehicleType, Duration) double
        -calculateChargingCost(Duration) double
        -getRateForVehicle(VehicleType) double
    }

    %% Builder Pattern
    class ParkingLotBuilder {
        -totalFloors int
        -spotLayout Map~SpotType_Integer~
        -howToFindSpots ISlotAllocationStrategy
        -howToCalculatePrice IPricingStrategy
        +ParkingLotBuilder()
        +setFloors(int) ParkingLotBuilder
        +setSpotsPerFloor(Map) ParkingLotBuilder
        +setAllocationStrategy(ISlotAllocationStrategy) ParkingLotBuilder
        +setPricingStrategy(IPricingStrategy) ParkingLotBuilder
        +build() ParkingLot
    }

    %% Demo Class
    class ParkingLotDemo {
        +main(String[]) void
        -buildSmartParkingLot() ParkingLot
        -addGatesToMall(ParkingLot) void
        -showParkingStatus(ParkingLot) void
        -simulateRealWorldUsage(ParkingLot) void
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

    ParkingLotBuilder --> ParkingLot
    ParkingLotBuilder --> ISlotAllocationStrategy
    ParkingLotBuilder --> IPricingStrategy

    Vehicle --> VehicleType
```

## Usage Example

```java
// Create a smart parking lot with builder pattern
Map<SpotType, Integer> spotConfiguration = new HashMap<>();
spotConfiguration.put(SpotType.SMALL, 5);   // For bikes
spotConfiguration.put(SpotType.MEDIUM, 3);  // For cars
spotConfiguration.put(SpotType.LARGE, 2);   // For buses
spotConfiguration.put(SpotType.ELECTRIC, 2); // For electric vehicles

ParkingLot mall = new ParkingLotBuilder()
    .setFloors(2)
    .setSpotsPerFloor(spotConfiguration)
    .setAllocationStrategy(new NearestSlotStrategy())
    .setPricingStrategy(new HourlyPricingStrategy())
    .build();

// Add gates
EntryGate mainEntrance = new EntryGate(1);
ExitGate paymentCounter = new ExitGate(1, new HourlyPricingStrategy());

mall.addEntryGate(mainEntrance);
mall.addExitGate(paymentCounter);

// Park vehicles
Vehicle myBike = new Bike("KA01AB1234");
ElectricBike eBike = new ElectricBike("KA02CD5678");
eBike.setWantsCharging(true);

// Generate tickets
Ticket bikeTicket = mainEntrance.generateTicket(myBike);
Ticket eBikeTicket = mainEntrance.generateTicket(eBike);

// Process exit
double parkingFee = paymentCounter.processExit(bikeTicket);
bikeTicket.getSpot().vacate();
```

## Spot Type Configuration

### Small Spots
- Designed for bikes and motorcycles
- Compact size for two-wheeler vehicles
- Standard parking rate

### Medium Spots  
- Suitable for cars and standard vehicles
- Most common spot type in parking lots
- Medium parking rate

### Large Spots
- For buses, trucks, and large vehicles
- Higher parking rate due to size
- Limited availability

### Electric Spots
- Equipped with charging stations
- Automatically allocated to electric vehicles requesting charging
- Additional charging fee applied
- Can accommodate any vehicle size with charging capability

## Vehicle Types Supported

### Standard Vehicles
- **Car**: Regular four-wheeler, uses Medium spots
- **Bike**: Two-wheeler, uses Small spots  
- **Bus**: Large vehicle, uses Large spots

### Electric Vehicles
- **ElectricBike**: Implements `IElectricVehicle` interface
- Can request charging station through `setWantsCharging(true)`
- Automatically gets Electric spot when charging is requested
- Falls back to appropriate size spot when charging not needed

## Strategy Implementations

### Allocation Strategies
- **NearestSlotStrategy**: Finds first available spot that can accommodate the vehicle
- Easily extensible for additional strategies (e.g., ClosestToGateStrategy, PreferredFloorStrategy)

### Pricing Strategies  
- **HourlyPricingStrategy**: Time-based pricing with vehicle-specific rates
  - Bikes: $2.00/hour
  - Cars: $5.00/hour  
  - Buses: $10.00/hour
  - Charging: Additional $3.00/hour
- Minimum fee: $1.00
- Easily extensible for flat rate, dynamic pricing, etc.

## Gate System

### Entry Gates
- Generate parking tickets automatically
- Handle electric vehicle charging requests
- Integrate with allocation strategy for spot assignment
- Support multiple entry points

### Exit Gates  
- Process parking fee calculation
- Handle payment processing
- Support multiple exit points
- Integrate with pricing strategy

## Design Patterns Used

1. **Strategy Pattern**: Pluggable allocation and pricing algorithms
2. **Builder Pattern**: Flexible parking lot configuration and construction
3. **Interface Segregation**: Separate concerns with focused interfaces
4. **Composition**: Hierarchical structure (ParkingLot → Floors → Spots)
5. **Template Method**: Abstract Vehicle with concrete implementations
6. **Polymorphism**: Vehicle hierarchy with type-specific behavior

## Project Structure

```
📁 builders/ - ParkingLotBuilder for flexible construction
📁 core/ - ParkingLot main system
📁 demo/ - ParkingLotDemo with usage examples  
📁 enums/ - VehicleType, SpotType enumerations
📁 interfaces/ - IElectricVehicle, ISlotAllocationStrategy, IPricingStrategy
📁 models/ - ParkingSpot, ParkingFloor, Ticket, EntryGate, ExitGate
📁 strategies/ - NearestSlotStrategy, HourlyPricingStrategy implementations  
📁 vehicles/ - Vehicle hierarchy (Car, Bike, Bus, ElectricBike)
```

## Key Features Implemented

✅ **Multi-floor Architecture**: Hierarchical parking structure  
✅ **Electric Vehicle Support**: Dedicated charging infrastructure  
✅ **Flexible Configuration**: Builder pattern for easy setup  
✅ **Smart Allocation**: Strategy-based spot assignment  
✅ **Dynamic Pricing**: Time and vehicle-based fee calculation  
✅ **Gate Management**: Separate entry/exit processing  
✅ **Type Safety**: Strong typing with enums and interfaces  
✅ **Extensibility**: Strategy patterns for easy feature addition

## Future Enhancements

- **Reservation System**: Pre-booking parking spots
- **Payment Integration**: Multiple payment methods
- **Analytics Dashboard**: Usage statistics and reporting  
- **Mobile App Integration**: Real-time availability
- **Dynamic Pricing**: Peak/off-peak hour pricing
- **IoT Integration**: Sensor-based spot detection
- **Multi-tenant Support**: Different parking lot operators

This Smart Parking Lot System demonstrates solid object-oriented design principles with clean architecture, making it maintainable, extensible, and production-ready.
