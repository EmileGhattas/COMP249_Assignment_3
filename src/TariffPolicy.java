// TariffPolicy.java
public interface TariffPolicy {
    // I) Create an interface named TariffPolicy which has a method evaluateTrade that determines the trade
    // request outcome based on tariff policies.
    //         String evaluateTrade(double proposedTariff, double minimumTariff)
    String evaluateTrade(double proposedTariff, double minimumTariff);
}