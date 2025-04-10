// Product.java
// This class represents a product entry read from TradeData.txt.
// Each line in the file is formatted as: ProductName,Country,Category,InitialPrice
// The class stores the product name, country, category, initial price, and the adjusted price after applying tariffs.
public class Product implements Comparable<Product> {
    private String productName;
    private String country;
    private String category;
    private double initialPrice;
    private double adjustedPrice;  // After tariff adjustment

    // Parameterized constructor
    public Product(String productName, String country, String category, double initialPrice) {
        this.productName = productName;
        this.country = country;
        this.category = category;
        this.initialPrice = initialPrice;
        this.adjustedPrice = initialPrice; // Default (before adjustment)
    }

    // Getters
    public String getProductName() {
        return productName;
    }
    public String getCountry() {
        return country;
    }
    public String getCategory() {
        return category;
    }
    public double getInitialPrice() {
        return initialPrice;
    }
    public double getAdjustedPrice() {
        return adjustedPrice;
    }

    // Apply Tariff Adjustment Rules:
    // For each product, adjust the initial price based on the country and (if applicable) category.
    // Rules:
    //   China:         +25%           (All products)
    //   USA:           +10%           (Electronics)
    //   Japan:         +15%           (Automobiles)
    //   India:         +5%            (Agriculture)
    //   South Korea:   +8%            (Electronics)
    //   Saudi Arabia:  +12%           (Energy)
    //   Germany:       +6%            (Manufacturing)
    //   Bangladesh:    +4%            (Textile)
    //   Brazil:        +9%            (Agriculture)
    public void applyTariff() {
        double rate = 0.0;
        if (country.equalsIgnoreCase("China")) {
            rate = 0.25;
        } else if (country.equalsIgnoreCase("USA") && category.equalsIgnoreCase("Electronics")) {
            rate = 0.10;
        } else if (country.equalsIgnoreCase("Japan") && category.equalsIgnoreCase("Automobiles")) {
            rate = 0.15;
        } else if (country.equalsIgnoreCase("India") && category.equalsIgnoreCase("Agriculture")) {
            rate = 0.05;
        } else if (country.equalsIgnoreCase("South Korea") && category.equalsIgnoreCase("Electronics")) {
            rate = 0.08;
        } else if (country.equalsIgnoreCase("Saudi Arabia") && category.equalsIgnoreCase("Energy")) {
            rate = 0.12;
        } else if (country.equalsIgnoreCase("Germany") && category.equalsIgnoreCase("Manufacturing")) {
            rate = 0.06;
        } else if (country.equalsIgnoreCase("Bangladesh") && category.equalsIgnoreCase("Textile")) {
            rate = 0.04;
        } else if (country.equalsIgnoreCase("Brazil") && category.equalsIgnoreCase("Agriculture")) {
            rate = 0.09;
        }
        adjustedPrice = initialPrice * (1 + rate);
    }

    // Products are compared alphabetically by productName.
    @Override
    public int compareTo(Product other) {
        return productName.compareToIgnoreCase(other.productName);
    }

    // toString() outputs the product in the format:
    // ProductName,Country,Category,AdjustedPrice
    @Override
    public String toString() {
        return productName + "," + country + "," + category + "," + adjustedPrice;
    }
}