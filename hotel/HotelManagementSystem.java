package hotel;

import room.RoomManager;
import reservation.ReservationManager;

import java.util.Scanner;
public class HotelManagementSystem {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();
        ReservationManager reservationManager = new ReservationManager(roomManager);

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("Hotel Management System Menu:");
            System.out.println("1. Display Available Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. Check-Out");
            System.out.println("4. Order Food");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    roomManager.displayAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.next();
		    System.out.print	("Enter your Phone Number: ");
		    String phoneNumber = scanner.next();
                    System.out.print("Enter room type: ");
                    int roomTypeChoice = scanner.nextInt();
                    reservationManager.makeReservation(guestName, roomTypeChoice, phoneNumber);
                    break;
                case 3:
                    System.out.print("Enter room number to check-out: ");
                    int roomNumber = scanner.nextInt();
                    reservationManager.checkOut(roomNumber);
                    break;
                case 4:
                    reservationManager.orderFood(scanner, roomManager);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
