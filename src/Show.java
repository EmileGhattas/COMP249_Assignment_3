public class Show implements Cloneable {
    private String destinationCountry;
    private String originCountry;
    private String productCategory;
    private double minimumTariff;

    // a) Parameterized constructor
    public Show(String destinationCountry, String originCountry, String productCategory, double minimumTariff) {
        this.destinationCountry = destinationCountry;
        this.originCountry = originCountry;
        this.productCategory = productCategory;
        this.minimumTariff = minimumTariff;
    }

    // b) Copy constructor (takes a Show object)
    public Show(Show other) {
        this.destinationCountry = other.destinationCountry;
        this.originCountry = other.originCountry;
        this.productCategory = other.productCategory;
        this.minimumTariff = other.minimumTariff;
    }

    // Standard mutator and accessor methods.
    public String getOriginCountry() {
        return originCountry;
    }
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
    public double getMinimumTariff() {
        return minimumTariff;
    }
    public void setMinimumTariff(double minimumTariff) {
        this.minimumTariff = minimumTariff;
    }
    public String getDestinationCountry() {
        return destinationCountry;
    }
    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    // c) clone() method – creates and returns a clone (deep copy) of this object.
    @Override
    public Show clone() {
        return new Show(this);
    }

    // d) toString() method – returns a string representation (fields separated by a space).
    @Override
    public String toString() {
        return destinationCountry + " " + originCountry + " " + productCategory + " " + minimumTariff;
    }

    // d) equals() method – two Show objects are equal if they have identical attributes.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Show other = (Show) obj;
        return destinationCountry.equals(other.destinationCountry) &&
                originCountry.equals(other.originCountry) &&
                productCategory.equals(other.productCategory) &&
                Double.compare(minimumTariff, other.minimumTariff) == 0;
    }
}