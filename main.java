import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Tool> tools = Arrays.asList(
                new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false),
                new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true),
                new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, true, false),
                new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, true, false));

        try {
            Tool tool = null;
            System.out.println("Welcome to Dome Hepot! Please enter the following information to checkout.");

            while (tool == null) {
                System.out.print("Enter the Tool Code: ");
                String toolCode = scanner.nextLine().toUpperCase().trim();

                tool = tools.stream()
                        .filter(x -> x.getToolCode().equals(toolCode))
                        .findFirst()
                        .orElse(null);

                if (tool == null) {
                    System.out.println("Tool not found. Please try again.");
                    System.out.print("Enter the Tool Code: ");
                }
            }

            System.out.print("Enter the number of days you wish to rent the tool: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                System.out.print("Enter the number of days you wish to rent the tool: ");
                scanner.next();
            }
            int days = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter the discount percent (0-100): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please try again.");
                System.out.print("Enter the discount percent: (0-100): ");
                scanner.next();
            }
            int discountPercent = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter the check out date (mm/dd/yyyy): ");
            String date = scanner.nextLine().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(dateFormat.parse(date));

            // Rental Agreement
            RentalAgreement agreement = Checkout.createRentalAgreement(days, discountPercent, calendarDate,
                    tool);
            System.out.println(agreement.toString());
            scanner.close();

        } catch (IllegalArgumentException e) {
            System.out.println("Error Occurred while processing user input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Internal error occurred: " + e.getMessage());
        }

    }
}
