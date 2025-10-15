import java.util.*;

class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited ₹" + amount);
            System.out.println("Deposit successful! Current Balance: ₹" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            transactionHistory.add("Withdrew ₹" + amount);
            System.out.println("Withdrawal successful! Remaining Balance: ₹" + balance);
        }
    }

    public void transfer(Account receiver, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid transfer amount.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance for transfer.");
        } else {
            balance -= amount;
            receiver.balance += amount;
            transactionHistory.add("Transferred ₹" + amount + " to " + receiver.userId);
            receiver.transactionHistory.add("Received ₹" + amount + " from " + this.userId);
            System.out.println("Transfer successful! Remaining Balance: ₹" + balance);
        }
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\n--- Transaction History ---");
            for (String record : transactionHistory) {
                System.out.println(record);
            }
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Demo user data
        Account user1 = new Account("user1", "1234", 5000);
        Account user2 = new Account("user2", "5678", 3000);

        Map<String, Account> accounts = new HashMap<>();
        accounts.put(user1.getUserId(), user1);
        accounts.put(user2.getUserId(), user2);

        System.out.println("=== Welcome to the ATM System ===\n");
        System.out.print("Enter User ID: ");
        String enteredId = sc.nextLine();

        if (!accounts.containsKey(enteredId)) {
            System.out.println("User not found. Please try again.");
            return;
        }

        Account currentUser = accounts.get(enteredId);

        System.out.print("Enter PIN: ");
        String enteredPin = sc.nextLine();

        if (!currentUser.validatePin(enteredPin)) {
            System.out.println("Invalid PIN. Access Denied!");
            return;
        }

        System.out.println("\nLogin Successful! Welcome, " + enteredId + "\n");

        while (true) {
            System.out.println("----- ATM Menu -----");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    currentUser.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmt = sc.nextDouble();
                    currentUser.withdraw(withdrawAmt);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmt = sc.nextDouble();
                    currentUser.deposit(depositAmt);
                    break;
                case 4:
                    sc.nextLine(); // clear buffer
                    System.out.print("Enter receiver User ID: ");
                    String receiverId = sc.nextLine();
                    if (!accounts.containsKey(receiverId)) {
                        System.out.println("Receiver not found.");
                        break;
                    }
                    System.out.print("Enter amount to transfer: ");
                    double transferAmt = sc.nextDouble();
                    currentUser.transfer(accounts.get(receiverId), transferAmt);
                    break;
                case 5:
                    System.out.println("Current Balance: ₹" + currentUser.getBalance());
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM! Goodbye 👋");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }
}
