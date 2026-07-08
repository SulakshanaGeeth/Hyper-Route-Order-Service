package com.hyperroute.hyperroute.model;

public enum OrderStatus {
    CREATED,
    FINDING_RIDER,
    ASSIGNED,
    PICKED_UP,
    DELIVERED;

    public OrderStatus nextState(OrderStatus requested) {
        return switch (this) {
            case CREATED -> switch (requested) {
                case FINDING_RIDER -> requested;
                default -> throw new IllegalStateException("Invalid transition: Cannot move from CREATED to " + requested);
            };
            case FINDING_RIDER -> switch (requested) {
                case ASSIGNED, CREATED -> requested;
                default -> throw new IllegalStateException("Invalid transition: Cannot move from FINDING_RIDER to " + requested);
            };
            case ASSIGNED -> {
                if (requested == PICKED_UP) yield requested;
                throw new IllegalStateException("Invalid transition: Must pick up the order next.");
            }
            case PICKED_UP -> {
                if (requested == DELIVERED) yield requested;
                throw new IllegalStateException("Invalid transition: Must deliver the order next.");
            }
            case DELIVERED -> throw new IllegalStateException("Invalid transition: Order is already finalized and DELIVERED.");
        };
    }
}
