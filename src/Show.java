// Show.java
// II) The Tariff class has the following attributes: destinationCountry (String), originCountry (String),
// productCategory (String), minimumTariff (double). It is assumed that String entries are recorded as a
// single word (_ is used to combine multiple words).
//
// You are required to write the implementation of the Show class. Besides the usual mutator & accessor
// methods (i.e. getOrginCountry(), setMinimumTarrif(double)) the class must also have the following:
//
//      a) Parameterized constructor accepts four values and initializes destinationCountry, originCountry, productCategory, and minimumTariff to these passed values.
//      b) Copy constructor, which takes in only one parameter, a Tariff object. The newly created object will be assigned all the attributes of the passed object.
//      c) clone() method. This method will create and return a clone of the calling object.
//      d) toString() and an equals() methods. Two Tariffs are equal if they have the same attributes.
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