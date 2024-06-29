import java.util.Scanner;

// BankAccount class to represent the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }
}

// ATM class to represent the ATM machine
class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void displayMenu() {
        System.out.println("*********************************");
        System.out.println("*       Welcome to the ATM      *");
        System.out.println("*********************************");
        System.out.println("*   1. Check Balance            *");
        System.out.println("*   2. Deposit                  *");
        System.out.println("*   3. Withdraw                 *");
        System.out.println("*   4. Exit                     *");
        System.out.println("*********************************");
    }

    public void withdraw(double amount) {
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            System.out.println("*********************************");
            System.out.println("*    Successfully withdrawn:    *");
            System.out.println("*           $" + amount + "             *");
            System.out.println("*********************************");
        } else {
            System.out.println("*********************************");
            System.out.println("* Insufficient balance for this *");
            System.out.println("*          withdrawal.          *");
            System.out.println("*********************************");
        }
    }

    public void deposit(double amount) {
        account.deposit(amount);
        System.out.println("*********************************");
        System.out.println("*   Successfully deposited:     *");
        System.out.println("*           $" + amount + "             *");
        System.out.println("*********************************");
    }

    public void checkBalance() {
        System.out.println("*********************************");
        System.out.println("*      Current balance:         *");
        System.out.println("*           Rs" + account.getBalance() + "            *");
        System.out.println("*********************************");
    }
}

// Main class to run the ATM program
public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = new BankAccount(1000); // Initial balance
        ATM atm = new ATM(account);

        boolean running = true;
        while (running) {
            atm.displayMenu();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdraw(withdrawAmount);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
        System.out.println("Thank you for using the ATM. Goodbye!");
    }
}

