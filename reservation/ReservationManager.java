package reservation;

import room.Room;
import room.RoomManager;
import food.FoodItem;
import food.FoodMenu;
import java.sql.Statement;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.DriverManager;

import database.DatabaseConnector;

public class ReservationManager {
    private Reservation[] reservations;
    private int nextReservationNumber;
    private Reservation orderingReservation;
    private RoomManager roomManager;

    public ReservationManager(RoomManager roomManager) {
        this.roomManager = roomManager;
        reservations = new Reservation[20];
        nextReservationNumber = 1;
    }

    public Reservation getOrderingReservation() {
        return orderingReservation;
    }

    public void setOrderingReservation(Reservation orderingReservation) {
        this.orderingReservation = orderingReservation;
    }

    public void makeReservation(String guestName, int roomTypeChoice, String phoneNumber) {
        Room availableRoom = roomManager.getAvailableRoom(roomTypeChoice);

        if (!phoneNumber.matches("\\d{10}")) {
            System.out.println("Invalid phone number. Please enter a 10-digit number.");
            return;
        }

        if (availableRoom != null) {
            availableRoom.occupy();
            Reservation reservation = new Reservation(nextReservationNumber, guestName, availableRoom, phoneNumber);

            for (int i = 0; i < reservations.length; i++) {
                if (reservations[i] == null) {
                    reservations[i] = reservation;
                    break;
                }
            }

            setOrderingReservation(reservation);
            System.out.println("Reservation successful! Room " + availableRoom.getRoomNumber() +
                    " is reserved for " + guestName);
        } else {
            System.out.println("No available rooms for reservation.");
        }
    }

    public void checkOut(int roomNumber) {
        Reservation reservation = getOrderingReservation();

        if (reservation != null) {
            saveReservationDetails(reservation);
            Room roomToCheckOut = roomManager.getRoomByNumber(roomNumber);

            if (roomToCheckOut != null && roomToCheckOut.isOccupied()) {
                double totalCost = reservation.getTotalCost();
                roomToCheckOut.vacate();
                System.out.println("Check-out successful for Room " + roomToCheckOut.getRoomNumber() +
                        ". Total cost: $" + totalCost);
            } else {
                System.out.println("Invalid room number or the room is not occupied. Check-out failed.");
            }
        } else {
            System.out.println("No active reservation for check-out.");
        }

    }

    private void saveReservationDetails(Reservation reservation) {
        try {
            String filename = "Bill_for_Room_" + reservation.getRoom().getRoomNumber() + "_Reservation_" +
                    reservation.getReservationNumber() + ".txt";

            try (FileWriter writer = new FileWriter(filename)) {
                writer.write("Reservation Number: " + reservation.getReservationNumber() + "\n");
                writer.write("Guest Name: " + reservation.getGuestName() + "\n");
                writer.write("Room Number: " + reservation.getRoom().getRoomNumber() + "\n");
                writer.write("Total Cost: $" + reservation.getTotalCost() + "\n");
                writer.write("\n");
            }

            nextReservationNumber++;
        } catch (IOException e) {
            System.out.println("Error saving reservation details to file.");
            e.printStackTrace();
        }

        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO hotel(reservationNumber, guestName, roomNumber, totalCost) " +
                 "VALUES (" + reservation.getReservationNumber() + ", '" + reservation.getGuestName() + "', " + reservation.getRoom().getRoomNumber() + ", '" + reservation.getTotalCost() + "')";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println("caught: " + e);
        }
    }

    public void orderFood(Scanner scanner, RoomManager roomManager) {
        Reservation reservation = getOrderingReservation();

        if (reservation != null) {
            displayFoodMenu();
            int foodChoice = getFoodChoice(scanner);

            if (foodChoice != 0) {
                FoodItem selectedFood = getFoodItem(foodChoice);
                if (selectedFood != null) {
                    System.out.print("Enter the room number for ordering food: ");
                    int roomNumber = scanner.nextInt();
                    Room selectedRoom = roomManager.getRoomByNumber(roomNumber);

                    if (selectedRoom != null && selectedRoom.isOccupied() && reservation.getRoom() == selectedRoom) {
                        reservation.orderFood(selectedFood);
                        System.out.println("Order successful! " + selectedFood.getItemName() +
                                " is ordered for Room " + selectedRoom.getRoomNumber() + ".");
                    } else {
                        System.out.println("Invalid room number or the room is not occupied. Order canceled.");
                    }
                } else {
                    System.out.println("Invalid food choice. Order canceled.");
                }
            } else {
                System.out.println("Order canceled.");
            }
        } else {
            System.out.println("No active reservation for ordering food.");
        }
    }

    private void displayFoodMenu() {
        System.out.println("Food Menu:");
        System.out.println("1. Burger - ₹10.99");
        System.out.println("2. Pizza - ₹12.99");
        System.out.println("3. Salad - ₹8.99");
        System.out.println("4. Pasta - ₹11.99");
    }

    private int getFoodChoice(Scanner scanner) {
        System.out.print("Enter the number of the food item to order (0 to cancel): ");
        return scanner.nextInt();
    }

    private FoodItem getFoodItem(int foodChoice) {
        switch (foodChoice) {
            case 1:
                return FoodMenu.BURGER;
            case 2:
                return FoodMenu.PIZZA;
            case 3:
                return FoodMenu.SALAD;
            case 4:
                return FoodMenu.PASTA;
            default:
                return null;
        }
    }
}
