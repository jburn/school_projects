import java.util.ArrayList;
import java.util.Iterator;

public class InsInfoContainer
{
    // Class variable
    private ArrayList<InsuranceInfo> insuranceList;

    // Constructor parameters:
    // An ArrayList of InsuranceInfo objects
    public InsInfoContainer(ArrayList<InsuranceInfo> insList)
    {
        insuranceList = insList;
    }

    // Method for adding insurance info
    public void addInsInfo(InsuranceInfo newInfo)
    {
        this.insuranceList.add(newInfo);
    }

    // Method for printing the contents of the container
    public void printContents()
    {
        InsuranceInfo tempIns; // Temp variable
        // Creating iterator for the insurance list
        Iterator<InsuranceInfo> i = this.insuranceList.iterator();
        System.out.println("### PRINTING CONTENTS OF CONTAINER ###");
        while(i.hasNext())
        {
            tempIns = (InsuranceInfo) i.next();
            System.out.println("\n### INSURANCE ###");
            System.out.println("Insured property type: " + tempIns.getInsProperty().getType());
            System.out.println("Insured property location: " + tempIns.getInsProperty().getLocation());
            System.out.println("Insurance value: " + tempIns.getInsValue());
        }
    }

    // Method for printing all insurances with value greater than threshold value given as a parameter
    public void printGreaterValues(double thValue)
    {
        InsuranceInfo tempIns; // Temp variable
        // Creating iterator for the insurance list
        Iterator<InsuranceInfo> i = this.insuranceList.iterator();
        System.out.println("\n### PRINTING INSURANCES WITH VALUE OVER " + thValue + " ###");
        while(i.hasNext())
        {
            tempIns = (InsuranceInfo) i.next();
            if(tempIns.getInsValue() > thValue)
            {
                System.out.println("\n### INSURANCE ###");
                System.out.println("Insured property type: " + tempIns.getInsProperty().getType());
                System.out.println("Insured property location: " + tempIns.getInsProperty().getLocation());
                System.out.println("Insurance value: " + tempIns.getInsValue());
            }
        }
    }

    // Method for printing all insurances with value smaller than threshold value given as a parameter
    public void printSmallerValues(double thValue)
    {
        InsuranceInfo tempIns; // Temp variable
        // Creating iterator for the insurance list
        Iterator<InsuranceInfo> i = this.insuranceList.iterator();
        System.out.println("\n### PRINTING INSURANCES WITH VALUE UNDER " + thValue + " ###");
        while(i.hasNext())
        {
            tempIns = (InsuranceInfo) i.next();
            if(tempIns.getInsValue() < thValue)
            {
                System.out.println("\n### INSURANCE ###");
                System.out.println("Insured property type: " + tempIns.getInsProperty().getType());
                System.out.println("Insured property location: " + tempIns.getInsProperty().getLocation());
                System.out.println("Insurance value: " + tempIns.getInsValue());
            }
        }
    }
}