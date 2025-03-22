@echo off

javac -cp . food/FoodItem.java
javac -cp . food/FoodMenu.java
javac -cp . room/Room.java
javac -cp . room/RoomManager.java
javac -cp . room/SingleRoom.java
javac -cp . room/DoubleRoom.java
javac -cp . room/DeluxeRoom.java
javac -cp . room/SuiteRoom.java
javac -cp . reservation/Reservation.java
javac -cp . reservation/ReservationManager.java
javac -cp . database/DatabaseConnector.java
javac -cp . hotel/HotelManagementSystem.java
java -cp . hotel/HotelManagementSystem.java


pause
