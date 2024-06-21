package com.hotel;

import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel";
    private static final String USER = "root";
    private static final String PASS = "Root";
    private static Connection conn = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            boolean exit = false;
            while (!exit) {
                System.out.println("Hotel Reservation System");
                System.out.println("1. Manage Hotels");
                System.out.println("2. Manage Rooms");
                System.out.println("3. Manage Customers");
                System.out.println("4. Manage Reservations");
                System.out.println("5. Manage Payments");
                System.out.println("6. Exit");
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        manageHotels();
                        break;
                    case 2:
                        manageRooms();
                        break;
                    case 3:
                        manageCustomers();
                        break;
                    case 4:
                        manageReservations();
                        break;
                    case 5:
                        managePayments();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageHotels() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Manage Hotels");
            System.out.println("1. Add Hotel");
            System.out.println("2. View Hotels");
            System.out.println("3. Update Hotel");
            System.out.println("4. Delete Hotel");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addHotel();
                    break;
                case 2:
                    viewHotels();
                    break;
                case 3:
                    updateHotel();
                    break;
                case 4:
                    deleteHotel();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addHotel() throws SQLException {
        System.out.print("Enter hotel name: ");
        String name = scanner.nextLine();
        System.out.print("Enter hotel location: ");
        String location = scanner.nextLine();
        System.out.print("Enter hotel rating: ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter contact details: ");
        String contactDetails = scanner.nextLine();

        String query = "INSERT INTO Hotel (Name, Location, Rating, Contact_Details) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, location);
        pstmt.setInt(3, rating);
        pstmt.setString(4, contactDetails);
        pstmt.executeUpdate();
        System.out.println("Hotel added successfully.");
    }

    private static void viewHotels() throws SQLException {
        String query = "SELECT * FROM Hotel";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Hotels List:");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("Hotel_ID") + ", Name: " + rs.getString("Name") +
                    ", Location: " + rs.getString("Location") + ", Rating: " + rs.getInt("Rating") +
                    ", Contact Details: " + rs.getString("Contact_Details"));
        }
    }

    private static void updateHotel() throws SQLException {
        System.out.print("Enter hotel ID to update: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new hotel name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new hotel location: ");
        String location = scanner.nextLine();
        System.out.print("Enter new hotel rating: ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new contact details: ");
        String contactDetails = scanner.nextLine();

        String query = "UPDATE Hotel SET Name = ?, Location = ?, Rating = ?, Contact_Details = ? WHERE Hotel_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, location);
        pstmt.setInt(3, rating);
        pstmt.setString(4, contactDetails);
        pstmt.setInt(5, hotelId);
        pstmt.executeUpdate();
        System.out.println("Hotel updated successfully.");
    }

    private static void deleteHotel() throws SQLException {
        System.out.print("Enter hotel ID to delete: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Hotel WHERE Hotel_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, hotelId);
        pstmt.executeUpdate();
        System.out.println("Hotel deleted successfully.");
    }

    private static void manageRooms() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Manage Rooms");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addRoom() throws SQLException {
        System.out.print("Enter hotel ID: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter room type: ");
        String roomType = scanner.nextLine();
        System.out.print("Enter room status: ");
        String status = scanner.nextLine();
        System.out.print("Enter room price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        String query = "INSERT INTO Room (Hotel_ID, Room_Type, Status, Price) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, hotelId);
        pstmt.setString(2, roomType);
        pstmt.setString(3, status);
        pstmt.setDouble(4, price);
        pstmt.executeUpdate();
        System.out.println("Room added successfully.");
    }

    private static void viewRooms() throws SQLException {
        String query = "SELECT * FROM Room";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Rooms List:");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("Room_ID") + ", Hotel ID: " + rs.getInt("Hotel_ID") +
                    ", Type: " + rs.getString("Room_Type") + ", Status: " + rs.getString("Status") +
                    ", Price: " + rs.getDouble("Price"));
        }
    }

    private static void updateRoom() throws SQLException {
        System.out.print("Enter room ID to update: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new hotel ID: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new room type: ");
        String roomType = scanner.nextLine();
        System.out.print("Enter new room status: ");
        String status = scanner.nextLine();
        System.out.print("Enter new room price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        String query = "UPDATE Room SET Hotel_ID = ?, Room_Type = ?, Status = ?, Price = ? WHERE Room_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, hotelId);
        pstmt.setString(2, roomType);
        pstmt.setString(3, status);
        pstmt.setDouble(4, price);
        pstmt.setInt(5, roomId);
        pstmt.executeUpdate();
        System.out.println("Room updated successfully.");
    }

    private static void deleteRoom() throws SQLException {
        System.out.print("Enter room ID to delete: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Room WHERE Room_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, roomId);
        pstmt.executeUpdate();
        System.out.println("Room deleted successfully.");
    }

    private static void manageCustomers() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Manage Customers");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewCustomers();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCustomer() throws SQLException {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        String query = "INSERT INTO Customer (Name, Contact_Number, Email, Cust_Address) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, contactNumber);
        pstmt.setString(3, email);
        pstmt.setString(4, address);
        pstmt.executeUpdate();
        System.out.println("Customer added successfully.");
    }

    private static void viewCustomers() throws SQLException {
        String query = "SELECT * FROM Customer";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Customers List:");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("Customer_ID") + ", Name: " + rs.getString("Name") +
                    ", Contact Number: " + rs.getString("Contact_Number") + ", Email: " + rs.getString("Email") +
                    ", Address: " + rs.getString("Cust_Address"));
        }
    }

    private static void updateCustomer() throws SQLException {
        System.out.print("Enter customer ID to update: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new address: ");
        String address = scanner.nextLine();

        String query = "UPDATE Customer SET Name = ?, Contact_Number = ?, Email = ?, Cust_Address = ? WHERE Customer_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, contactNumber);
        pstmt.setString(3, email);
        pstmt.setString(4, address);
        pstmt.setInt(5, customerId);
        pstmt.executeUpdate();
        System.out.println("Customer updated successfully.");
    }

    private static void deleteCustomer() throws SQLException {
        System.out.print("Enter customer ID to delete: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Customer WHERE Customer_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, customerId);
        pstmt.executeUpdate();
        System.out.println("Customer deleted successfully.");
    }

    private static void manageReservations() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Manage Reservations");
            System.out.println("1. Add Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Update Reservation");
            System.out.println("4. Delete Reservation");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addReservation();
                    break;
                case 2:
                    viewReservations();
                    break;
                case 3:
                    updateReservation();
                    break;
                case 4:
                    deleteReservation();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addReservation() throws SQLException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();
        System.out.print("Enter total amount: ");
        double totalAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        String query = "INSERT INTO Reservation (Customer_ID, Room_ID, Check_In_Date, Check_Out_Date, Total_Amount) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, customerId);
        pstmt.setInt(2, roomId);
        pstmt.setString(3, checkInDate);
        pstmt.setString(4, checkOutDate);
        pstmt.setDouble(5, totalAmount);
        pstmt.executeUpdate();
        System.out.println("Reservation added successfully.");
    }

    private static void viewReservations() throws SQLException {
        String query = "SELECT Reservation.Reservation_ID, Customer.Name AS Customer_Name, Room.Room_Type, " +
                "Reservation.Check_In_Date, Reservation.Check_Out_Date, Reservation.Total_Amount " +
                "FROM Reservation " +
                "JOIN Customer ON Reservation.Customer_ID = Customer.Customer_ID " +
                "JOIN Room ON Reservation.Room_ID = Room.Room_ID";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Reservations List:");
        while (rs.next()) {
            System.out.println("Reservation ID: " + rs.getInt("Reservation_ID") + ", Customer: " + rs.getString("Customer_Name") +
                    ", Room Type: " + rs.getString("Room_Type") + ", Check-In Date: " + rs.getString("Check_In_Date") +
                    ", Check-Out Date: " + rs.getString("Check_Out_Date") + ", Total Amount: " + rs.getDouble("Total_Amount"));
        }
    }

    private static void updateReservation() throws SQLException {
        System.out.print("Enter reservation ID to update: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new check-in date (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Enter new check-out date (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();
        System.out.print("Enter new total amount: ");
        double totalAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        String query = "UPDATE Reservation SET Customer_ID = ?, Room_ID = ?, Check_In_Date = ?, Check_Out_Date = ?, Total_Amount = ? WHERE Reservation_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, customerId);
        pstmt.setInt(2, roomId);
        pstmt.setString(3, checkInDate);
        pstmt.setString(4, checkOutDate);
        pstmt.setDouble(5, totalAmount);
        pstmt.setInt(6, reservationId);
        pstmt.executeUpdate();
        System.out.println("Reservation updated successfully.");
    }

    private static void deleteReservation() throws SQLException {
        System.out.print("Enter reservation ID to delete: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Reservation WHERE Reservation_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, reservationId);
        pstmt.executeUpdate();
        System.out.println("Reservation deleted successfully.");
    }

    private static void managePayments() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("Manage Payments");
            System.out.println("1. Add Payment");
            System.out.println("2. View Payments");
            System.out.println("3. Update Payment");
            System.out.println("4. Delete Payment");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addPayment();
                    break;
                case 2:
                    viewPayments();
                    break;
                case 3:
                    updatePayment();
                    break;
                case 4:
                    deletePayment();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addPayment() throws SQLException {
        System.out.print("Enter reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter payment method: ");
        String paymentMethod = scanner.nextLine();
        System.out.print("Enter payment date (YYYY-MM-DD): ");
        String paymentDate = scanner.nextLine();

        String query = "INSERT INTO Payment (Reservation_ID, Amount, Payment_Method, Payment_Date) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, reservationId);
        pstmt.setDouble(2, amount);
        pstmt.setString(3, paymentMethod);
        pstmt.setString(4, paymentDate);
        pstmt.executeUpdate();
        System.out.println("Payment added successfully.");
    }

    private static void viewPayments() throws SQLException {
        String query = "SELECT Payment.Payment_ID, Reservation.Reservation_ID, Payment.Amount, Payment.Payment_Method, Payment.Payment_Date " +
                "FROM Payment " +
                "JOIN Reservation ON Payment.Reservation_ID = Reservation.Reservation_ID";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Payments List:");
        while (rs.next()) {
            System.out.println("Payment ID: " + rs.getInt("Payment_ID") + ", Reservation ID: " + rs.getInt("Reservation_ID") +
                    ", Amount: " + rs.getDouble("Amount") + ", Method: " + rs.getString("Payment_Method") +
                    ", Date: " + rs.getString("Payment_Date"));
        }
    }

    private static void updatePayment() throws SQLException {
        System.out.print("Enter payment ID to update: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new payment method: ");
        String paymentMethod = scanner.nextLine();
        System.out.print("Enter new payment date (YYYY-MM-DD): ");
        String paymentDate = scanner.nextLine();

        String query = "UPDATE Payment SET Reservation_ID = ?, Amount = ?, Payment_Method = ?, Payment_Date = ? WHERE Payment_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, reservationId);
        pstmt.setDouble(2, amount);
        pstmt.setString(3, paymentMethod);
        pstmt.setString(4, paymentDate);
        pstmt.setInt(5, paymentId);
        pstmt.executeUpdate();
        System.out.println("Payment updated successfully.");
    }

    private static void deletePayment() throws SQLException {
        System.out.print("Enter payment ID to delete: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Payment WHERE Payment_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, paymentId);
        pstmt.executeUpdate();
        System.out.println("Payment deleted successfully.");
    }
}
