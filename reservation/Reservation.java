package reservation;

import room.Room;
import food.FoodItem;

public class Reservation {
    private int reservationNumber;
    private String guestName;
    private Room room;
    private String phoneNumber;
    private boolean foodOrdered;  // Corrected variable name
    private FoodItem orderedFood;

    public Reservation(int reservationNumber, String guestName, Room room, String phoneNumber) {
        this.reservationNumber = reservationNumber;
        this.guestName = guestName;
        this.room = room;
        this.phoneNumber = phoneNumber;
        this.foodOrdered = false;  // Corrected variable name
        this.orderedFood = null;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isFoodOrdered() {
        return foodOrdered;
    }

    public FoodItem getOrderedFood() {
        return orderedFood;
    }

    public void orderFood(FoodItem foodItem) {
        this.foodOrdered = true;
        this.orderedFood = foodItem;
    }

    public double getTotalCost() {
        double roomCost = room.getRoomCost();
        double foodCost = 0.0;

        if (foodOrdered && orderedFood != null) {
            foodCost = orderedFood.getCost();
        }

        return roomCost + foodCost;
    }
}
