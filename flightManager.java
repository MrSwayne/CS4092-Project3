import javax.swing.*;
import java.io.*;
import java.util.*;

public class flightManager
{
        public static ArrayList<String []> airportList = new ArrayList<String[]>();
        public static ArrayList<String []> flightList = new ArrayList<String[]>();
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void main(String[] args) 
	{
                boolean isFiles = validateFiles();
		readInFiles();
		
		
		
        }
	
        /*
	@authors Ian Mckay
	Input: 
        Output:
	*/
        
        public static void readInFiles()
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
                        
                        
                        
		}
		catch(FileNotFoundException e)
		{
			System.out.print("FileNotFoundException : " + e.getMessage());
		}
		catch(IOException e)
		{
			System.out.print("IOException : " + e.getMessage());
		}
	}
	
	/*
	@authors 
	Input: 
        Output:
	*/
        
        public static boolean validateFiles()
	{
		File airportFile = new File("Airports.txt");
		File flightFile = new File("Flights.txt");
		if(airportFile.exists() && flightFile.exists()){
                        return true;
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
		return true;
        }
        
         /*
	@authors 
	Input: 
        Output:
	*/
        
	public static boolean validateFlight(String airportCode, int num)
	{
		return true;
        
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
	@authors Ian McKay and 
	Input: 
        Output:
	*/
        
        public static void writeToAirports()
	{
               try
        	{
		       PrintWriter outFile = new PrintWriter("Airports.txt");
		       String currentLine = "";
		       for (int i = 0; i <airportList.size(); i++)
		       {
			       currentLine = airportList.get(i)[0];
			       for(int j = 1; j<airportList.get(i).length;j++)
			       {
				       currentLine = currentLine + "," + airportList.get(i)[j];
			       }
			       //prints each line to the text file
			       outFile.println(currentLine);
		       }
		       outFile.close();
		}
		catch(IOException e)
        	{
            		System.out.print("IOException : " + e.getMessage());
		}
        }
	
	/*
	@authors 
	Input: 
        Output:
	*/
        
        public static void writeToFlights()
	{
               
        }
}
