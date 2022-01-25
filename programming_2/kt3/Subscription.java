public class Subscription 
{
    // Class variables
    private String magazineName, subscriberName, deliveryAddress;
    private double monthlyPrice;

    // Constructor parameters:
    // Magazine name, subscriber name and delivery address as strings
    // Monthly price as a double
    public Subscription(String magName, String subName, String delivAddr, double monthPrice)
    {
        magazineName = magName;
        subscriberName = subName;
        deliveryAddress = delivAddr;
        if(monthPrice < 0)
        {
            System.out.println("Monthly price cannot be negative!");
        }
        else
        {
            monthlyPrice = monthPrice;
        }
    }

    // Setter method for magazine name
    public void setMagName(String newName)
    {
        magazineName = newName;
    }

    // Getter method for magazine name
    public String getMagName()
    {
        return magazineName;
    }

    // Setter method for subscriber name
    public void setSubName(String newName)
    {
        subscriberName = newName;
    }

    // Getter method for subscriber name
    public String getSubName()
    {
        return subscriberName;
    }

    // Setter method for delivery address
    public void setDelivAddr(String newAddr)
    {
        deliveryAddress = newAddr;
    }

    // Getter method for delivery address
    public String getDelivAddr()
    {
        return deliveryAddress;
    }

    // Setter method for monthly price
    public void setMonthPrice(double newPrice)
    {
        if(newPrice < 0)
        {
            System.out.println("Price cannot be negative!");
        }
        else
        {
            monthlyPrice = newPrice;
        }
    }

    // Getter method for monthly price
    public double getMonthPrice()
    {
        return monthlyPrice;
    }
}