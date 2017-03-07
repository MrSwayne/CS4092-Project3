import javax.swing.*;
import java.io.*;
import java.util.*;

public class flightManager
{
        ArrayList<String []> airportList;
        ArrayList<String []> flightList;
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void main(String[] args) 
	{
        
                airportList = new ArrayList<String[]>();
		flightList = new Arraylist<String[]>();
                boolean isFiles = readInFiles();
                
        
        
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static boolean readInFiles()
	{
                
		try
		{
			FileReader airportFile = new FileReader("Airports.txt");
			FileReader flightFile = new FileReader("Flights.txt");
			Scanner in = new Scanner(airportFile);
			
			while(in.hasNext())
			{
				//splits the data, 
				airportList.add(in.nextLine().split(","));
			}
			
			in = new Scanner(airportFile);
			
			while(in.hasNext())
			{
				//splits the data,
				flightList.add(in.nextLine().split(","));
			}
			
			in.close();
			airportFile.close();
			flightFile.close();
                        
                        
                        if(airportFile.isFile() && flightFile.isFile()){
                        return true;
                        }
		}
		catch(FileNotFoundException e)
		{
			System.out.print("FileNotFoundException : " + e.getMessage());
		}
		catch(IOException e)
		{
			System.out.print("IOException : " + e.getMessage());
		}
                
                return false;

	}
        
         /*
	@authors 
	Input: 
        Output:
	*/
        
	public static boolean validateAirport(String airportCode, int num)
	{
        
        }
        
         /*
	@authors 
	Input: 
        Output:
	*/
        
	public static boolean validateFlight(String airportCode, int num)
	{
        
        }
        
         /*
	@authors 
	Input: 
        Output:
	*/
        
	public static void programLauncher(String[] args)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void addAirport(String airport,String airportCode)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void editAirport(String airport,String airportCode)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void deleteAirport(String airportCode)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void editFlight(String flightNum,String flightDays, String startFlight, String endFlight)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void deleteFlight(String flightNum)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void searchFlight(String airportDepart,String airportArrive)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void searchDate(String airportDepart,String airportArrive, String date)
	{
        
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void writeToFiles()
	{
               try
        {
            
        PrintWriter outFile = new PrintWriter("users.txt");
        for (int i = 0; i <userList.length; i++)
        {
                //prints each line to the text file
                outFile.println((userList[i]));
        }
        outFile.close();
      
       }
        catch(IOException e)
        {
            System.out.print("IOException : " + e.getMessage());
        }
        }
        
        
        
        


}
