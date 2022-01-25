public class Property {
    // Class variables
    private String location, type;

    // Constructor parameters:
    // Property location and type as strings
    public Property(String propertyLocation, String propertyType)
    {
        location = propertyLocation;
        type = propertyType;
    }

    // Setter for location
    public void setLocation(String newLocation)
    {
        this.location = newLocation;
    }

    // Getter for location
    public String getLocation() 
    {
        return this.location;
    }

    // Setter for type
    public void setType(String newType)
    {
        this.type = newType;
    }

    // Getter for type
    public String getType()
    {
        return this.type;
    }

}
