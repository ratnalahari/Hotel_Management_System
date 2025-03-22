package room;

import java.util.Scanner;

public class RoomManager {
    private Room[] rooms;
    private int reservationNumber = 1;

    public RoomManager() {
        rooms = new Room[20];
        for (int i = 0; i < 5; i++) {
            rooms[i] = new SingleRoom(i + 1);
            rooms[i + 5] = new DoubleRoom(i + 6);
            rooms[i + 10] = new DeluxeRoom(i + 11);
            rooms[i + 15] = new SuiteRoom(i + 16);

            rooms[i].setRoomReservationNumber(1);
            rooms[i + 5].setRoomReservationNumber(1);
            rooms[i + 10].setRoomReservationNumber(1);
            rooms[i + 15].setRoomReservationNumber(1);

	    reservationNumber++;
        }
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Room getAvailableRoom(int roomTypeChoice) {
        for (Room room : rooms) {
            if (!room.isOccupied() && room.getRoomType() == roomTypeChoice) {
                return room;
            }
        }
        return null;
    }

    public void displayAvailableRooms() {
        System.out.println("Choose the type of Room: ");
        System.out.println("1. Single Room (1-5)");
        System.out.println("2. Double Room (6-10)");
        System.out.println("3. Deluxe Room (11-15)");
        System.out.println("4. Suite Room (16-20)");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayRoomAvailability(0, 5);
                break;
            case 2:
                displayRoomAvailability(5, 10);
                break;
            case 3:
                displayRoomAvailability(10, 15);
                break;
            case 4:
                displayRoomAvailability(15, 20);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayRoomAvailability(int start, int end) {
        System.out.println("Available Rooms:");
        for (int i = start; i < end; i++) {
            if (!rooms[i].isOccupied()) {
                System.out.println("Room " + rooms[i].getRoomNumber());
            }
        }
    }

    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}
