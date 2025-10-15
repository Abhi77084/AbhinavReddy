import java.util.*;

class User {
    private static final String USER_ID = "admin";
    private static final String PASSWORD = "1234";

    public static boolean login(String id, String password) {
        return USER_ID.equals(id) && PASSWORD.equals(password);
    }
}

class Reservation {
    String name, classType, from, to, dateOfJourney;
    int trainNo;
    String trainName;
    String pnr;

    Reservation(String name, int trainNo, String classType, String from, String to, String dateOfJourney) {
        this.name = name;
        this.trainNo = trainNo;
        this.trainName = getTrainName(trainNo);
        this.classType = classType;
        this.from = from;
        this.to = to;
        this.dateOfJourney = dateOfJourney;
        this.pnr = "PNR" + (int)(Math.random() * 100000);
    }

    private String getTrainName(int trainNo) {
        switch (trainNo) {
            case 101: return "Rajdhani Express";
            case 102: return "Shatabdi Express";
            case 103: return "Duronto Express";
            default: return "Local Passenger";
        }
    }

    public void display() {
        System.out.println("\nReservation Successful!");
        System.out.println("PNR Number: " + pnr);
        System.out.println("Passenger Name: " + name);
        System.out.println("Train No: " + trainNo);
        System.out.println("Train Name: " + trainName);
        System.out.println("Class: " + classType);
        System.out.println("From: " + from + "  To: " + to);
        System.out.println("Date: " + dateOfJourney);
    }
}

class Database {
    private static Map<String, Reservation> reservations = new HashMap<>();

    public static void addReservation(Reservation r) {
        reservations.put(r.pnr, r);
    }

    public static Reservation getReservation(String pnr) {
        return reservations.get(pnr);
    }

    public static void cancelReservation(String pnr) {
        reservations.remove(pnr);
    }
}

class Cancellation {
    public static void cancelTicket(String pnr) {
        Reservation r = Database.getReservation(pnr);
        if (r != null) {
            System.out.println("\nTicket found for PNR: " + pnr);
            r.display();
            Scanner sc = new Scanner(System.in);
            System.out.print("Do you really want to cancel this ticket? (yes/no): ");
            String confirm = sc.next();
            if (confirm.equalsIgnoreCase("yes")) {
                Database.cancelReservation(pnr);
                System.out.println(" Ticket cancelled successfully!");
            } else {
                System.out.println(" Cancellation aborted.");
            }
        } else {
            System.out.println("No reservation found with PNR: " + pnr);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== ONLINE RESERVATION SYSTEM =====");
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (!User.login(userId, password)) {
            System.out.println("Invalid Login! Access Denied.");
            return;
        }

        int choice;
        do {
            System.out.println("\n----- MAIN MENU -----");
            System.out.println("1. Reservation");
            System.out.println("2. Cancellation");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine(); // clear buffer
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter train number (101-103): ");
                    int trainNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter class type (Sleeper/AC/General): ");
                    String classType = sc.nextLine();
                    System.out.print("From: ");
                    String from = sc.nextLine();
                    System.out.print("To: ");
                    String to = sc.nextLine();
                    System.out.print("Enter date of journey (dd/mm/yyyy): ");
                    String date = sc.nextLine();

                    Reservation r = new Reservation(name, trainNo, classType, from, to, date);
                    Database.addReservation(r);
                    r.display();
                    break;

                case 2:
                    System.out.print("Enter your PNR number: ");
                    String pnr = sc.next();
                    Cancellation.cancelTicket(pnr);
                    break;

                case 3:
                    System.out.println("Thank you for using Online Reservation System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }
}
