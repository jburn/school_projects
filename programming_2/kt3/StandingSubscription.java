public class StandingSubscription extends Subscription
{
    // Class variables
    private int discountPercentage;

    // Constructor parameters:
    // Subscription superclass' parameters
    // Discount percentage as a positive integer
    public StandingSubscription(String magName, String subName, String delivAddr, double monthPrice, int discount)
    {
        super(magName, subName, delivAddr, monthPrice);
        if(discount < 0)
        {
            System.out.println("Discount percentage cannot be negative!");
        }
        else
        {
            discountPercentage = discount;
        }   
    }

    // Setter method for discount percentage
    public void setDiscount(int newPercentage)
    {
        discountPercentage = newPercentage;
    }

    // Getter method for discount percentage
    public int getDiscount()
    {
        return discountPercentage;
    }

    // Function for calculating the price and printing invoice information
    public void printInvoice()
    {
        String subType = "Standing subscription";
        int monthCount = 12;
        double price = monthCount * super.getMonthPrice() * ((100-this.getDiscount()) * 0.01);

        System.out.println("Subscription type: " + subType);
        System.out.println("Magazine name: " + super.getMagName());
        System.out.println("Subscriber name: " + super.getSubName());
        System.out.println("Delivery address: " + super.getDelivAddr());
        System.out.println("Subscribed months: " + monthCount);
        System.out.println("Total price: " + price);
    }
}
