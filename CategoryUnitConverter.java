import java.util.Scanner;

public class CategoryUnitConverter {

    // Length Conversion Methods
    public static double cmToInches(double cm) {
        return cm * 0.393701;
    }

    public static double inchesToCm(double inches) {
        return inches / 0.393701;
    }

    public static double metersToFeet(double meters) {
        return meters * 3.28084;
    }

    public static double feetToMeters(double feet) {
        return feet / 3.28084;
    }

    public static double kmToMiles(double km) {
        return km * 0.621371;
    }

    public static double milesToKm(double miles) {
        return miles / 0.621371;
    }

    // Volume Conversion Methods
    public static double litersToGallons(double liters) {
        return liters * 0.264172;
    }

    public static double gallonsToLiters(double gallons) {
        return gallons / 0.264172;
    }

    public static double mlToOunces(double ml) {
        return ml * 0.033814;
    }

    public static double ouncesToMl(double ounces) {
        return ounces / 0.033814;
    }

    // Area Conversion Methods
    public static double squareMetersToSquareFeet(double squareMeters) {
        return squareMeters * 10.7639;
    }

    public static double squareFeetToSquareMeters(double squareFeet) {
        return squareFeet / 10.7639;
    }

    public static double squareKmToSquareMiles(double squareKm) {
        return squareKm * 0.386102;
    }

    public static double squareMilesToSquareKm(double squareMiles) {
        return squareMiles / 0.386102;
    }

    // Weight Conversion Methods
    public static double kgToPounds(double kg) {
        return kg * 2.20462;
    }

    public static double poundsToKg(double pounds) {
        return pounds / 2.20462;
    }

    public static double gramsToOunces(double grams) {
        return grams * 0.035274;
    }

    public static double ouncesToGrams(double ounces) {
        return ounces / 0.035274;
    }

    // Temperature Conversion Methods
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueConversion = true;

        while (continueConversion) {
            // Display conversion categories
            System.out.println("Choose a conversion category:");
            System.out.println("1. Length");
            System.out.println("2. Volume");
            System.out.println("3. Area");
            System.out.println("4. Weight");
            System.out.println("5. Temperature");
            System.out.println("6. Exit");

            int categoryChoice = scanner.nextInt();

            switch (categoryChoice) {
                case 1:
                    System.out.println("Choose a length conversion option:");
                    System.out.println("1. cm to inches");
                    System.out.println("2. inches to cm");
                    System.out.println("3. meters to feet");
                    System.out.println("4. feet to meters");
                    System.out.println("5. km to miles");
                    System.out.println("6. miles to km");
                    System.out.println("7. Back to categories");

                    int lengthChoice = scanner.nextInt();
                    switch (lengthChoice) {
                        case 1:
                            System.out.print("Enter value in cm: ");
                            double cm = scanner.nextDouble();
                            System.out.println(cm + " cm is equal to " + cmToInches(cm) + " inches.");
                            break;
                        case 2:
                            System.out.print("Enter value in inches: ");
                            double inches = scanner.nextDouble();
                            System.out.println(inches + " inches is equal to " + inchesToCm(inches) + " cm.");
                            break;
                        case 3:
                            System.out.print("Enter value in meters: ");
                            double meters = scanner.nextDouble();
                            System.out.println(meters + " meters is equal to " + metersToFeet(meters) + " feet.");
                            break;
                        case 4:
                            System.out.print("Enter value in feet: ");
                            double feet = scanner.nextDouble();
                            System.out.println(feet + " feet is equal to " + feetToMeters(feet) + " meters.");
                            break;
                        case 5:
                            System.out.print("Enter value in km: ");
                            double km = scanner.nextDouble();
                            System.out.println(km + " km is equal to " + kmToMiles(km) + " miles.");
                            break;
                        case 6:
                            System.out.print("Enter value in miles: ");
                            double miles = scanner.nextDouble();
                            System.out.println(miles + " miles is equal to " + milesToKm(miles) + " km.");
                            break;
                        case 7:
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                    break;

                case 2:
                    System.out.println("Choose a volume conversion option:");
                    System.out.println("1. Liters to Gallons");
                    System.out.println("2. Gallons to Liters");
                    System.out.println("3. ml to ounces");
                    System.out.println("4. ounces to ml");
                    System.out.println("5. Back to categories");

                    int volumeChoice = scanner.nextInt();
                    switch (volumeChoice) {
                        case 1:
                            System.out.print("Enter value in liters: ");
                            double liters = scanner.nextDouble();
                            System.out.println(liters + " liters is equal to " + litersToGallons(liters) + " gallons.");
                            break;
                        case 2:
                            System.out.print("Enter value in gallons: ");
                            double gallons = scanner.nextDouble();
                            System.out
                                    .println(gallons + " gallons is equal to " + gallonsToLiters(gallons) + " liters.");
                            break;
                        case 3:
                            System.out.print("Enter value in ml: ");
                            double ml = scanner.nextDouble();
                            System.out.println(ml + " ml is equal to " + mlToOunces(ml) + " ounces.");
                            break;
                        case 4:
                            System.out.print("Enter value in ounces: ");
                            double ounces = scanner.nextDouble();
                            System.out.println(ounces + " ounces is equal to " + ouncesToMl(ounces) + " ml.");
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                    break;

                case 3:
                    System.out.println("Choose an area conversion option:");
                    System.out.println("1. Square Meters to Square Feet");
                    System.out.println("2. Square Feet to Square Meters");
                    System.out.println("3. Square Km to Square Miles");
                    System.out.println("4. Square Miles to Square Km");
                    System.out.println("5. Back to categories");

                    int areaChoice = scanner.nextInt();
                    switch (areaChoice) {
                        case 1:
                            System.out.print("Enter value in square meters: ");
                            double squareMeters = scanner.nextDouble();
                            System.out.println(squareMeters + " square meters is equal to "
                                    + squareMetersToSquareFeet(squareMeters) + " square feet.");
                            break;
                        case 2:
                            System.out.print("Enter value in square feet: ");
                            double squareFeet = scanner.nextDouble();
                            System.out.println(squareFeet + " square feet is equal to "
                                    + squareFeetToSquareMeters(squareFeet) + " square meters.");
                            break;
                        case 3:
                            System.out.print("Enter value in square kilometers: ");
                            double squareKm = scanner.nextDouble();
                            System.out.println(squareKm + " square kilometers is equal to "
                                    + squareKmToSquareMiles(squareKm) + " square miles.");
                            break;
                        case 4:
                            System.out.print("Enter value in square miles: ");
                            double squareMiles = scanner.nextDouble();
                            System.out.println(squareMiles + " square miles is equal to "
                                    + squareMilesToSquareKm(squareMiles) + " square kilometers.");
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                    break;

                case 4:
                    System.out.println("Choose an Weight conversion option:");
                    System.out.println("1. Kilograms to Pounds");
                    System.out.println("2. Pounds to Kilograms");
                    System.out.println("3. Grams to Ounces");
                    System.out.println("4. Ounces to Grams");
                    System.out.println("5. Back to categories");

                    int weightChoice = scanner.nextInt();
                    double inputValue, convertedValue;
                    switch (weightChoice) {
                        case 1:
                            System.out.print("Enter weight in Kilograms: ");
                            inputValue = scanner.nextDouble();
                            convertedValue = kgToPounds(inputValue);
                            System.out.println(inputValue + " Kilograms is " + convertedValue + " Pounds");
                            break;
                        case 2:
                            System.out.print("Enter weight in Pounds: ");
                            inputValue = scanner.nextDouble();
                            convertedValue = poundsToKg(inputValue);
                            System.out.println(inputValue + " Pounds is " + convertedValue + " Kilograms");
                            break;
                        case 3:
                            System.out.print("Enter weight in Grams: ");
                            inputValue = scanner.nextDouble();
                            convertedValue = gramsToOunces(inputValue);
                            System.out.println(inputValue + " Grams is " + convertedValue + " Ounces");
                            break;
                        case 4:
                            System.out.print("Enter weight in Ounces: ");
                            inputValue = scanner.nextDouble();
                            convertedValue = ouncesToGrams(inputValue);
                            System.out.println(inputValue + " Ounces is " + convertedValue + " Grams");
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Choose an Temperature conversion option:");
                    System.out.println("1. CelsiusToFahrenheit ");
                    System.out.println("2. FahrenheitToCelsius");
                    System.out.println("3. Back to categories");

                    int TempChoice = scanner.nextInt();
                    switch (TempChoice) {
                        case 1:
                            System.out.print("Enter value in Celsius: ");
                            double celsius = scanner.nextDouble();
                            System.out.println(celsius + " degrees Celsius is equal to " + celsiusToFahrenheit(celsius)
                                    + " degrees Fahrenheit.");
                            break;
                        case 2:
                            System.out.print("Enter value in Fahrenheit: ");
                            double fahrenheit = scanner.nextDouble();
                            System.out.println(
                                    fahrenheit + " degrees Fahrenheit is equal to " + fahrenheitToCelsius(fahrenheit)
                                            + " degrees Celsius.");
                            break;

                        case 3:
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                    break;

                case 6:
                    continueConversion = false;
                    System.out.println("Exit!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

            System.out.println();
            System.out.print("Do you want to perform another conversion? (yes/no): ");
            String userResponse = scanner.next();
            continueConversion = userResponse.equalsIgnoreCase("yes");
        }

        System.out.println("\n\nBy Dilseerat Kaur 2237374\tCEC CGC \tJAVA Project-->Unit Converter");

        scanner.close();
    }
}
