package room;

import food.FoodItem;

public class Room {
    private int roomNumber;
    private boolean isOccupied;
    protected int roomType;
    private double roomCost;
    private int roomReservationNumber;

    public Room(int roomNumber, double roomCost) {
        this.roomNumber = roomNumber;
        this.isOccupied = false;
        this.roomCost = roomCost;
        this.roomReservationNumber = 1;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getRoomType() {
        return roomType;
    }

    public double getRoomCost() {
        return roomCost;
    }

    public int getRoomReservationNumber() {
        return roomReservationNumber;
    }

    public void setRoomReservationNumber(int reservationNumber) {
        this.roomReservationNumber = reservationNumber;
    }

    public double calculateTotalCost(FoodItem orderedFood) {
        double foodCost = 0.0;
        if (orderedFood != null) {
            foodCost = orderedFood.getCost();
        }
        return roomCost + foodCost;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void vacate() {
        isOccupied = false;
    }
}
