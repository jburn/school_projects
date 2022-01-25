public class RegularSubscription extends Subscription
{
    // Class variables
    private int subscriptionLength;

    // Constructor parameters:
    // Subscription superclass' parameters
    // Subcscription length as a positive integer
    public RegularSubscription(String magName, String subName, String delivAddr, double monthPrice, int subLength)
    {
        super(magName, subName, delivAddr, monthPrice);
        if(subLength < 0)
        {
            System.out.println("Subscription length cannot be negative");
        }
        else
        {
            subscriptionLength = subLength;
        }
    }

    // Setter method for subscription length
    public void setSubLength(int subLen)
    {
        subscriptionLength = subLen;
    }

    // Getter method for subscription length
    public int getSubLength()
    {
        return subscriptionLength;
    }

    // Method for calculating price and printing invoice information 
    public void printInvoice()
    {
        String subType = "Regular subscription";
        int monthCount = this.getSubLength();
        double price = monthCount * super.getMonthPrice();

        System.out.println("Subscription type: " + subType);
        System.out.println("Magazine name: " + super.getMagName());
        System.out.println("Subscriber name: " + super.getSubName());
        System.out.println("Delivery address: " + super.getDelivAddr());
        System.out.println("Subscribed months: " + monthCount);
        System.out.println("Total price: " + price);
    }
}
