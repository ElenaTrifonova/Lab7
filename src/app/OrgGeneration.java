package app;

import utils.Validation;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class that generates organization
 */
public class OrgGeneration {
    /**
     * Method creates Organization
     *
     * @return Organization
     */
    public static Organization generate() {
        Organization organization = null;
        String nextLine;
        try {
            Scanner scanner = new Scanner(System.in);
            String name;
            do {
                System.out.println("Enter the name of the organization (more  than 3 signs):");
                name = scanner.nextLine();
            } while (!Validation.checkInterval((long) name.length(), 3, false, "Invalid name of organization, try again."));

            float x;
            do {
                System.out.println("Enter the coordinate X:");
                nextLine = scanner.nextLine();
                x = Float.parseFloat(nextLine);

            } while (!Validation.checkStringToFloat(nextLine, "Error of entering, coordinate X is float, try again.")
                    || !Validation.checkInterval((long) x, -164, false, "Invalid coordinate X, it needs to be more than -164."));

            Integer y;
            do {
                System.out.println("Enter the coordinate Y(can't be null):");
                nextLine = scanner.nextLine();
                y = Integer.valueOf(nextLine);

            } while ((!Validation.checkStringToInteger(nextLine, "Error of entering, coordinate Y is integer, try again.")
                    || (nextLine.equals(""))));

            Double annualTurnover;

            System.out.println("Enter the annual turnover:");

            String annualTurnover_str = scanner.nextLine();
            if (annualTurnover_str.length() == 0) {
                annualTurnover = null;
                System.out.println("Nothing was found in the string. Annual turnover will be null.");
            } else {
                do {
                    annualTurnover = Double.valueOf(annualTurnover_str);
                } while (!Validation.checkIntervalDouble(annualTurnover, 0, true, "Invalid annual turnover, it can't be less than 0.")
                        || !Validation.checkStringToDouble(annualTurnover_str, "Error of entering, annual turnover is double, try again."));
            }


            long employeesCount;
            do {
                System.out.println("Enter the employees count:");
                nextLine = scanner.nextLine();
                employeesCount = Long.parseLong(nextLine);

            } while (!Validation.checkStringToLong(nextLine, "Error of entering, employees count is long, try again.")
                    || !Validation.checkInterval(employeesCount, 0, false, "Invalid employees count, it can't be less than 0."));

            String zipCode;
            do {
                System.out.println("Enter the zip code:");
                zipCode = scanner.nextLine();

            } while (!Validation.checkInterval((long) zipCode.length(), 3, false, "Invalid zip code, length need to be not less than 3."));

            OrganizationType type;
            do {
                System.out.println("Enter the type of organization: 1 - Public , 2 - Government , 3 - Trust, 4 - Private limited company, 5 - Open joint stock company");
                nextLine = scanner.nextLine();
                type = OrganizationType.getValue(nextLine, "");

            } while (OrganizationType.getValue(nextLine, "Can't find. Try again.") == null);

            String owner;
            do{
                System.out.println("Enter the owner:");
                owner = scanner.nextLine();
            }while (!Validation.checkChars(owner, true, true));

            java.time.LocalDate creationDate;
            creationDate = java.time.LocalDate.now();
            System.out.println("Creation date:");
            System.out.println(creationDate);

            Long id;
            id = (long) (Math.random() * 111 + 23);
            System.out.println("Unique ID:");
            System.out.println(id);

            String fullName;
            fullName = getAlphaNumericString(6);
            System.out.println("Unique full name:");
            System.out.println(fullName);

            Coordinates coordinates = new Coordinates(x, y);
            Address postalAddress = new Address(zipCode);
            organization = new Organization(id, name, coordinates, creationDate, annualTurnover, fullName, employeesCount, type, postalAddress, owner);


        } catch (NoSuchElementException e) {
            System.out.println("Entering was finished");
        }
        return organization;
    }

    /**
     * Method creates unique full name
     *
     * @param n
     * @return String
     */
    static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
