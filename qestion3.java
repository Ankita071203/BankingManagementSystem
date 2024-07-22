package ClassQuestions;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
       import java.util.Scanner;

class account {
    double account_no;
    String name;
    String Type;
    double balance;
    double rate = 3.14;

    account(int account_no) {
        this.account_no = account_no;
        fetchAccountDetails(account_no);
    }

    account(String name, String Type, double balance) {
        this.name = name;
        this.Type = Type;
        this.balance = balance;
        createNewAccount();
    }

    private void fetchAccountDetails(int account_no) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM Accounts WHERE account_no = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, account_no);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    this.name = resultSet.getString("name");
                    this.Type = resultSet.getString("Type");
                    this.balance = resultSet.getDouble("balance");
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void readDet() {
        System.out.println("Account details:");
        System.out.println("Account No: " + account_no);
        System.out.println("Name: " + name);
        System.out.println("Type: " + Type);
        System.out.println("Balance: " + balance);
    }

    void printDet() {
        System.out.println("Account No: " + account_no);
        System.out.println("Name: " + name);
        System.out.println("Type: " + Type);
        System.out.println("Balance: " + balance);
        System.out.println("Rate: " + rate);
    }

    double check_balance() {
        System.out.println("Checking balance...");
        if (Type.equals("saving")) {
            balance = balance + (balance * rate);
        } else if (Type.equals("current")) {
            balance = balance + (balance * (rate / 2));
        } else {
            System.out.println("Invalid Account Type");
        }
        System.out.println("Balance: " + balance);
        return balance;
    }

    double withdraw() {
        System.out.println("Enter amount to withdraw:");
        Scanner sc = new Scanner(System.in);
        double amount = sc.nextDouble();
        if (amount <= balance) {
            balance = balance - amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
            updateBalance();
        } else {
            System.out.println("Insufficient balance. Current balance: " + balance);
        }
        return balance;
    }

    double deposit() {
        System.out.println("Enter amount to deposit:");
        Scanner sc = new Scanner(System.in);
        double amount = sc.nextDouble();
        balance = balance + amount;
        System.out.println("Deposit successful. New balance: " + balance);
        updateBalance();
        return balance;
    }

    void deleteAccount() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "DELETE FROM Accounts WHERE account_no = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, (int) account_no);
                statement.executeUpdate();
                connection.close();
                System.out.println("Account deleted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createNewAccount() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO Accounts (name, Type, balance) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, Type);
                statement.setDouble(3, balance);
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.account_no = generatedKeys.getInt(1);
                }
                connection.close();
                System.out.println("New account created successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateBalance() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "UPDATE Accounts SET balance = ? WHERE account_no = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setDouble(1, balance);
                statement.setInt(2, (int) account_no);
                statement.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void viewAllAccounts() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM Accounts";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Account No: " + resultSet.getInt("account_no"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Type: " + resultSet.getString("Type"));
                    System.out.println("Balance: " + resultSet.getDouble("balance"));
                    System.out.println("--------------------------");
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void searchAccountByName(String name) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM Accounts WHERE name LIKE ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "%" + name + "%");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Account No: " + resultSet.getInt("account_no"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Type: " + resultSet.getString("Type"));
                    System.out.println("Balance: " + resultSet.getDouble("balance"));
                    System.out.println("--------------------------");
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void searchAccountByNumber(int account_no) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM Accounts WHERE account_no = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, account_no);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Account No: " + resultSet.getInt("account_no"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Type: " + resultSet.getString("Type"));
                    System.out.println("Balance: " + resultSet.getDouble("balance"));
                } else {
                    System.out.println("No account found with account number: " + account_no);
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
public class qestion3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter mode (admin/user): ");
        String mode = sc.nextLine();

        if (mode.equalsIgnoreCase("admin")) {
            adminMode(sc);
        } else if (mode.equalsIgnoreCase("user")) {
            userMode(sc);
        } else {
            System.out.println("Invalid mode. Exiting...");
        }
    }

    private static void adminMode(Scanner sc) {
        while (true) {
            System.out.println("Admin Options:");
            System.out.println("1) View all accounts");
            System.out.println("2) Search account by name");
            System.out.println("3) Search account by number");
            System.out.println("4) Create new account");
            System.out.println("5) Exit");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline left by nextInt()

            switch (option) {
                case 1:
                    account.viewAllAccounts();
                    break;
                case 2:
                    System.out.println("Enter name to search:");
                    String name = sc.nextLine();
                    account.searchAccountByName(name);
                    break;
                case 3:
                    System.out.println("Enter account number to search:");
                    int accountNo = sc.nextInt();
                    account.searchAccountByNumber(accountNo);
                    break;
                case 4:
                    createAccount(sc);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void userMode(Scanner sc) {
        System.out.print("Do you have an existing account? (yes/no): ");
        String response = sc.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Enter user account number: ");
            int user = sc.nextInt();
            account userAccount = new account(user);

            while (true) {
                System.out.println("Select the option you want to opt:");
                System.out.println("1) readDet");
                System.out.println("2) printDet");
                System.out.println("3) check balance");
                System.out.println("4) withdraw");
                System.out.println("5) deposit");
                System.out.println("6) delete account");
                System.out.println("7) exit");
                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        if (userAccount != null) {
                            userAccount.readDet();
                        } else {
                            System.out.println("No account selected.");
                        }
                        break;
                    case 2:
                        if (userAccount != null) {
                            userAccount.printDet();
                        } else {
                            System.out.println("No account selected.");
                        }
                        break;
                    case 3:
                        if (userAccount != null) {
                            userAccount.check_balance();
                        } else {
                            System.out.println("No account selected.");
                        }
                        break;
                    case 4:
                        if (userAccount != null) {
                            userAccount.withdraw();
                        } else {
                            System.out.println("No account selected.");
                        }
                        break;
                    case 5:
                        if (userAccount != null) {
                            userAccount.deposit();
                        } else {
                            System.out.println("No account selected.");
                        }
                        break;
                    case 6:
                        if (userAccount != null) {
                            userAccount.deleteAccount();
                        } else {
                            System.out.println("No account selected.");
                        }
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        } else {
            createAccount(sc);
        }
    }

    private static void createAccount(Scanner sc) {
        System.out.println("Enter name:");
        String newName = sc.nextLine();
        System.out.println("Enter Type (saving/current):");
        String newType = sc.nextLine();
        System.out.println("Enter initial balance:");
        double newBalance = sc.nextDouble();
        account newAccount = new account(newName, newType, newBalance);
    }
}

