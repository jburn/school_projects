import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
    public static void main(String args[])
    {
        Scanner console = new Scanner(System.in); // Scanner object for user input
        // Init temp values
        String temp1, temp2;
        double tempdouble;
        String stringnum;
        ArrayList<InsuranceInfo> insList = new ArrayList<InsuranceInfo>();
        InsInfoContainer tempInfoCont = new InsInfoContainer(insList);
        
        // Loop for input of 5 insurance info objects
        for(int i=0; i<5;i++)
        {   
            stringnum = Integer.toString(i+1); // Show integers starting from 1 instead of 0
            System.out.println("Insurance property num. " + stringnum);
            System.out.print("Insert property type: ");
            temp1 = console.nextLine();
            System.out.print("Insert property name: ");
            temp2 = console.nextLine();
            System.out.print("Insert insurance value: ");
            tempdouble = console.nextDouble();
            tempInfoCont.addInsInfo(new InsuranceInfo(new Property(temp1, temp2), tempdouble));
            console.nextLine(); // Clear input buffer
        }

        tempInfoCont.printContents(); // Print the contents of info container
        
        // Print values smaller than the one input by user
        System.out.println("Print insurance info from insurances with value smaller than: ");
        tempdouble = console.nextDouble();
        tempInfoCont.printSmallerValues(tempdouble);

        // Print values greater than the one input by user
        System.out.println("Print insurance info from insurances with value greater than: ");
        tempdouble = console.nextDouble();
        tempInfoCont.printGreaterValues(tempdouble);

        console.close(); // Close scanner object
    }
}
