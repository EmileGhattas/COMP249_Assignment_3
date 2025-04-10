import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // (a) Create at least two empty TariffList objects.
        TariffList list1 = new TariffList();
        TariffList list2 = new TariffList();

        // (b) Open Tariff.txt and read its contents line by line.
        // Expected format: DestinationCountry OriginCountry ProductCategory MinimumTariff
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("Tariff.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if(tokens.length != 4)
                    continue;
                String dest = tokens[0];
                String origin = tokens[1];
                String category = tokens[2];
                double minTariff = Double.parseDouble(tokens[3]);
                // Create a Show object representing the tariff record.
                Show tariff = new Show(dest, origin, category, minTariff);
                // Insert into list1 only if no duplicate exists.
                if(!list1.contains(origin, dest, category))
                    list1.addToStart(tariff);
            }
        } catch(IOException e) {
            System.err.println("Error reading Tariff.txt: " + e.getMessage());
        } finally {
            try { if(reader != null) reader.close(); } catch(IOException e) {}
        }

        // (c) Open TradeRequests.txt and create an ArrayList from its contents.
        // Expected format: RequestID OriginCountry DestinationCountry ProductCategory TradeValue ProposedTariff
        ArrayList<String[]> tradeRequests = new ArrayList<>();
        BufferedReader tradeReader = null;
        try {
            tradeReader = new BufferedReader(new FileReader("TradeRequests.txt"));
            String line;
            while((line = tradeReader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if(tokens.length != 6)
                    continue;
                tradeRequests.add(tokens);
            }
        } catch(IOException e) {
            System.err.println("Error reading TradeRequests.txt: " + e.getMessage());
        } finally {
            try { if(tradeReader != null) tradeReader.close(); } catch(IOException e) {}
        }

        // Process each trade request.
        for(String[] request : tradeRequests) {
            String requestId = request[0];
            String origin = request[1];
            String destination = request[2];
            String category = request[3];
            double tradeValue = Double.parseDouble(request[4]);
            double proposedTariff = Double.parseDouble(request[5]);

            // Use the find() method to locate a matching tariff record.
            TariffList.TariffNode node = list1.find(origin, destination, category);
            if(node == null) {
                System.out.println(requestId + " - No matching tariff record found.");
                continue;
            }
            Show foundTariff = node.getTariff();
            double minimumTariff = foundTariff.getMinimumTariff();

            // Evaluate the trade outcome.
            String outcome = list1.evaluateTrade(proposedTariff, minimumTariff);
            System.out.print(requestId + " - " + outcome + ". ");
            if(outcome.equals("Conditionally Accepted")) {
                double surcharge = tradeValue * ((minimumTariff - proposedTariff) / 100);
                System.out.println("A surcharge of $" + surcharge + " is applied.");
            } else {
                System.out.println("Proposed tariff " +
                        (outcome.equals("Accepted") ? "meets or exceeds" : "is more than 20% below") +
                        " the required minimum tariff.");
            }
        }

        // (d) Prompt the user to enter tariff search details.
        System.out.println("Enter tariff search details (type 'exit' at any prompt to finish):");
        while(true) {
            System.out.print("Enter OriginCountry: ");
            String searchOrigin = scanner.nextLine().trim();
            if(searchOrigin.equalsIgnoreCase("exit"))
                break;
            System.out.print("Enter DestinationCountry: ");
            String searchDestination = scanner.nextLine().trim();
            if(searchDestination.equalsIgnoreCase("exit"))
                break;
            System.out.print("Enter ProductCategory: ");
            String searchCategory = scanner.nextLine().trim();
            if(searchCategory.equalsIgnoreCase("exit"))
                break;

            TariffList.TariffNode foundNode = list1.find(searchOrigin, searchDestination, searchCategory);
            if(foundNode != null) {
                System.out.println("Record found: " + foundNode.getTariff().toString());
            } else {
                System.out.println("No matching tariff record found.");
            }
        }

        // (e) Create additional objects to test each constructor/method.
        System.out.println("\nTesting Show (Tariff) and TariffList methods:");
        Show s1 = new Show("USA", "China", "Electronics", 10.0);
        Show s2 = new Show(s1);  // Copy constructor
        Show s3 = s1.clone();    // clone() method
        System.out.println("s1: " + s1.toString());
        System.out.println("s2 (copy): " + s2.toString());
        System.out.println("s3 (clone): " + s3.toString());
        System.out.println("s1 equals s2: " + s1.equals(s2));

        TariffList listCopy = new TariffList(list1);
        System.out.println("TariffList equals its copy: " + list1.equals(listCopy));

        // (f) Write the contents of list1 to an output file.
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileOutputStream("TariffOutput.txt"));
            output.println(list1.toString());
            System.out.println("Tariff list written to TariffOutput.txt");
        } catch(FileNotFoundException e) {
            System.err.println("Error writing TariffOutput.txt: " + e.getMessage());
        } finally {
            if(output != null)
                output.close();
        }

        scanner.close();
    }
}