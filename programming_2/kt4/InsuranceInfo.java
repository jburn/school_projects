public class InsuranceInfo
{
    // Class variables
    private Property insProperty;
    private double insValue;

    // Constructor parameters:
    // Insured property as a Property-class object 
    // Insurance value as a double
    public InsuranceInfo(Property insuranceProperty, double insuranceValue)
    {
        insProperty = insuranceProperty;
        insValue = insuranceValue;
    }

    // Setter for property
    public void setInsProperty(Property newProperty)
    {
        this.insProperty = newProperty;
    }

    // Getter for property
    public Property getInsProperty()
    {
        return this.insProperty;
    }

    // Setter for value
    public void setInsValue(double newValue)
    {
        this.insValue = newValue;
    }

    // Getter for value
    public double getInsValue()
    {
        return this.insValue;
    }
}