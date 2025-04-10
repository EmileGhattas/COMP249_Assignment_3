// ProductManager.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ProductManager {
    // This method processes product data from TradeData.txt,
    // applies tariffs, sorts products, and writes the updated data to UpdatedTradeData.txt.
    public static void processProducts() {
        ArrayList<Product> products = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("TradeData.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                // Expected format: ProductName,Country,Category,InitialPrice
                String[] tokens = line.split(",");
                if (tokens.length != 4) continue;
                String productName = tokens[0].trim();
                String country = tokens[1].trim();
                String category = tokens[2].trim();
                double initialPrice = Double.parseDouble(tokens[3].trim());
                Product p = new Product(productName, country, category, initialPrice);
                p.applyTariff();
                products.add(p);
            }
        } catch (IOException e) {
            System.err.println("Error reading TradeData.txt: " + e.getMessage());
        } finally {
            try { if (reader != null) reader.close(); } catch(IOException e) {}
        }

        // Sort products alphabetically by productName.
        Collections.sort(products);

        // Write updated product data to UpdatedTradeData.txt.
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream("UpdatedTradeData.txt"));
            for (Product p : products) {
                writer.println(p.toString());
            }
            System.out.println("Updated trade data written to UpdatedTradeData.txt");
        } catch (IOException e) {
            System.err.println("Error writing UpdatedTradeData.txt: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }
}