import java.util.Scanner; // Import scanner for user console input

public class Main
{
    public static void main(String[] args)
    {
        String temp1, temp2, temp3;
        int tempint;
        double tempdouble;
        Scanner konsoli = new Scanner(System.in);
        
        // Getting regular subscription info from user and storing it into a variable: regSub
        System.out.println("### Regular subscription ###");
        System.out.print("Insert magazine name: ");
        temp1 = konsoli.nextLine();
        System.out.print("Insert subscriber name: ");
        temp2 = konsoli.nextLine();
        System.out.print("Insert postal address: ");
        temp3 = konsoli.nextLine();
        System.out.print("Insert monthly price: ");
        tempdouble = konsoli.nextDouble();
        System.out.print("Insert subscription length: ");
        tempint = konsoli.nextInt();
        RegularSubscription regSub = new RegularSubscription(temp1, temp2, temp3, tempdouble, tempint);

        konsoli.nextLine(); // Clear input buffer

        // Getting standing subscription info from user and storing it into a variable: staSub
        System.out.println("### Standing subscription ###");
        System.out.print("Insert magazine name: ");
        temp1 = konsoli.nextLine();
        System.out.print("Insert subscriber name: ");
        temp2 = konsoli.nextLine();
        System.out.print("Insert postal address: ");
        temp3 = konsoli.nextLine();
        System.out.print("Insert monthly price: ");
        tempdouble = konsoli.nextDouble();
        System.out.print("Insert discount percentage: ");
        tempint = konsoli.nextInt();
        StandingSubscription staSub = new StandingSubscription(temp1, temp2, temp3, tempdouble, tempint);

        konsoli.close(); // Close the Scanner object since it is not needed anymore

        // Printing invoices from both subscriptions
        printSubscriptionInvoice(regSub);
        printSubscriptionInvoice(staSub);
    }

    // Method for printing subsctiption invoices
    static void printSubscriptionInvoice(Subscription subs)
    {
        System.out.println("\n###INVOICE###");
        // Different print depending on subscription type
        if(subs instanceof StandingSubscription)
        {
            StandingSubscription sSub = (StandingSubscription) subs;
            sSub.printInvoice();
        }
        else if(subs instanceof RegularSubscription)
        {
            RegularSubscription rSub = (RegularSubscription) subs;
            rSub.printInvoice();
        }
        else
        {
            System.out.println("Invalid subscription!");
        }
    }
}
