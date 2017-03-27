import javax.swing.*;
import java.io.*;
import java.util.*;

public class FlightManager
{
        public static ArrayList<ArrayList<String>> airportList = new ArrayList<ArrayList<String>>();
        public static ArrayList<ArrayList<String>> flightList = new ArrayList<ArrayList<String>>();
	/*
	TODO List:
	validateFlight()  only case 4 validation left
	addAirport()      simple (Ibrahim)
	editAirport()     simple (Ibrahim)
	editFlight()      medium difficulty (Matthew)
	deleteFlight()    medium difficulty (Matthew)
	searchDate()      hard difficulty (Adam)
	FYI: the validation methods do not display errors if validation is false,
	it only return true or false, please handle and output all error messeges 
	within your function on a case by case basis
	*/
	
	
        /*
	@authors Ian McKay & Adam Swayne
	Input: 
        Output:
	*/
        
        public static void main(String[] args) 
	{
                boolean isFiles = validateFiles();
		if(isFiles)
		{
			displayInstructions();
			readInFiles();
			programLauncher(args);
		}		
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
			String data[];
			int counter = 0;
			
			while(in.hasNext())
			{
				airportList.add(new ArrayList<String>());
				data=in.nextLine().split(",");
				for(int i=0;i<data.length;i++){
					airportList.get(counter).add(data[i]);
				}
				counter++;
			}
			
			in = new Scanner(flightFile);
			counter=0;
			
			while(in.hasNext())
			{
				flightList.add(new ArrayList<String>());
				data=in.nextLine().split(",");
				for(int i=0;i<data.length;i++){
					flightList.get(counter).add(data[i]);
				}
				counter++;
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
	@authors Ian McKay & Adam Swayne
	Input: 
        Output:
	*/
        
        public static boolean validateFiles()
	{
		try
		{
		File airportFile = new File("Airports.txt");
		File flightFile = new File("Flights.txt");
		if(airportFile.exists() && flightFile.exists())			 									return true;
		else airportFile.createNewFile();	flightFile.createNewFile();		
		}
		catch(IOException e)
		{
			System.out.print("Error, " + e.getMessage());
		}
		return true;
	}
        
         /*
	@authors Ian McKay & Adam Swayne 
	Input: 
        Output:
	*/
        
	public static boolean validateAirport(String input, int num)
	{
		String validationOf;
		boolean isValid=false;
		switch(num)
		{
			case 1:       
				validationOf = "airportName";
					
				String pattern  = "[a-zA-Z\\s]*";
						
				if(input != null && input.length() != 0)
				{
					if(input.matches(pattern))
					{
						isValid = true;
					}
				}
				if(validateAirport(input,2))
				{
					isValid=false;
				}
				
				break;
					
			case 2:       
				validationOf = "airportCode";
				isValid=true;
					
				for (int i=0; i<input.length(); i++){
					if (!Character.isUpperCase(input.charAt(i))) isValid= false;
				}
				if (input.length()!=3) isValid= false;
				
				break;
					
			default:      errorMessage(3);
		}
		return isValid;
	}
	
        
         /*
	@authors Ian McKay
	Input: 
        Output:
	*/
        
	
	public static boolean validateFlight(String input, int num)
	{
		String pattern;
		String validateOf;
		boolean isValid = false;
		
		switch(num)
		{
			case 1:       
				validateOf = "flightNum";
				pattern="[A-Z][A-Z]\\d\\d\\d\\d";
				if(input != null && input.length() != 0)
				{
					if(input.matches(pattern))
					{
						isValid = true;
					}
				}
				break;
			case 2:       
				validateOf = "DepartureArrivalTime";
				pattern="\\d\\d\\d\\d";
				if(input != null && input.length() != 0)
				{
					if(input.matches(pattern))
					{
						isValid = true;
					}
				}
				int inputNum=Integer.parseInt(input);
				if(inputNum>=2400 ||inputNum%100>=60)
				{
					isValid = false;
				}
				break;
			case 3:       
				validateOf = "DaysRunning";
				String days = "-MTWTFSS-";
				int countInOrder=0;
				int matchCount=0;
				boolean matchThisPos=false;
				
				for(int i = 0; i < input.length(); i++)
				{
					for(int j = countInOrder; j < days.length(); j++)
					{
						if(input.charAt(i)==days.charAt(j) && !matchThisPos)
						{
							matchCount++;
							countInOrder= j+1;
							matchThisPos=true;
						}
					}
					matchThisPos=false;
				}
				if(matchCount==input.length())
				{
					isValid=true;
				}
					break;
			case 4:       
				validateOf = "StartEndDate";
				String dateElements[];
				int ddInt, mmInt, yyInt;
				int[] daysArray = {31,28,31,30,31,30,31,31,30,31,30,31};
				boolean dateIsValid = true;
				dateElements = input.split("/");
				ddInt= Integer.parseInt(dateElements[0]);
				mmInt= Integer.parseInt(dateElements[1]);
				yyInt= Integer.parseInt(dateElements[2]);
				if(ddInt == 0 || mmInt == 0 || yyInt == 0)dateIsValid =false;
				else if(mmInt > 12)dateIsValid =false;
				else if(ddInt == 29 && mmInt == 2 && ((yyInt % 4 == 0 && yyInt % 100 != 0) || (yyInt % 400 == 0)))dateIsValid =true;
				else if(ddInt > daysArray[mmInt -1])dateIsValid =false;
				break;
				
				default:      errorMessage(3);
			}
		return isValid;
        
	}
					
	public static void errorMessage(int errorNum)
	{
		switch(errorNum)
		{
			case 1:System.out.println("Invalid number of command-line arguments."); break;
			case 2:System.out.println("Invalid first command-line argument."); break;
			case 3: System.out.println("Airport code must be three alphabetic characters in length."); break;
			case 4:System.out.println("File named Airports.txt does not exist."); break;
			case 5:System.out.println("Airport already exists."); break;
			case 6:System.out.println("Airport details added"); break;
			case 7:System.out.println("Validation Incomplete"); break;
			case 8:System.out.println("Airport code not found"); break;
			case 9:System.out.println("Departing Airport not found"); break;
			case 10:System.out.println("Arrival Airport not found"); break;
			case 12:System.out.println("Invalid airport name."); break;
			case 13:System.out.println("Invalid days"); break;
			case 14:System.out.println("Invalid start date."); break;
			case 15:System.out.println("Invalid end date."); break;
			default: System.out.println("error not caught"); break;
		} 
	}
	public static void displayInstructions() 
	{ 
		System.out.println(""); 
		System.out.println("************************ Assistance *****************************************************");
		System.out.println("Add new airport               e.g. java FlightManager AA Lisbon LIS"); 
		System.out.println("Edit airport                  e.g. java FlightManager EA BHD Belfast");
		System.out.println("Delete airport                e.g. java FlightManager DA SNN"); 
		System.out.println("Edit flight                   e.g. java FlightManager EF E13240 -TWTF-1/5/2017 31/10/2017"); 
		System.out.println("Delete flight                 e.g. java FlightManager DF E13240"); 
		System.out.println("Search for flights            e.g. java FlightManager SF Dublin Shannon"); 
		System.out.println("Search for flights on date    e.g. java FlightManager SD Dublin Shannon 1/12/2017"); 
		System.out.println("*****************************************************************************************");
	}
						
						
						
        
         /*
	@authors Ian McKay &
	Input: 
        Output:
	*/
        
	public static void programLauncher(String[] input)
	{
		
		try 
		{
			switch(input[0])
			{
				case "AA":       addAirport(input[1],input[2]); break;
				case "EA":       editAirport(input[1],input[2]); break;
				case "DA":       deleteAirport(input[1]); break;
				case "EF":       editFlight(input[1],input[2],input[3],input[4]); break;
				case "DF":       deleteFlight(input[1]); break;
				case "SF":       searchFlight(input[1],input[2]); break;
				case "SD":       searchDate(input[1],input[2],input[3]); break;
				case "help":	 displayInstructions();
				default:         errorMessage(2);
			}	
		}
		catch(ArrayIndexOutOfBoundsException exception)
		{
			errorMessage(1);
		}

		sortFiles();
		
    }
        
            /*
	@authors Ibrahim Alaydi
	Input: command-line arguments supplied
    Output:validating airportname and code and checking of it exists, also allowing user to add a new airport
	*/
        
public static void addAirport(String airport,String airportCode)
{	
		   

		boolean alreadyExists=false;
		//if both return true loop through array list and check if airport or airportcode already exist
		if(validateAirport(airport,1) && validateAirport(airportCode,2))
		{
			for(int i=0;i<airportList.size();i++) //looping through the airport list 
			{
			  if(airportList.get(i).get(0).equals(airport))//&&(airportList.get(1).get(i).equals(airportCode))) 
				  //checking if airport code and name exist file
				{
				  alreadyExists=true;
				}  
			
			}
				if(!alreadyExists)
				{//if they don't exist we need to create a new airport
					ArrayList<String>Entry = new ArrayList<String>();
					Entry.add(airport);
					Entry.add(airportCode);
					airportList.add(Entry);
					System.out.println("The airport is added successfully");
				}
			else errorMessage(5);
		}
		else if(!validateAirport(airport,1))errorMessage(3);	
		else if(!validateAirport(airportCode,2))errorMessage(12);
 }
        
        /*
	@authors Ibrahim Alaydi
	Input: command-line arguments
    Output: error messages or new airport is written to the file
	*/
        
public static void editAirport(String airportCode,String airport)
{
	boolean isValid=false; //create a boolean
	String temp; //intialize temp for sorting airport and code
	if(validateAirport(airport,1) && validateAirport(airportCode,2))
	isValid=true;// if in the correct order isValid is set to true
	else if(validateAirport(airport,2) && validateAirport(airportCode,1))
	{
		temp=airport;
		airport=airportCode;
		airportCode=temp;
		isValid=true; //if not, then we sort it.
	}
		else
		{ 
		errorMessage(8);
		isValid=false; //else we deplay an error
		}
				 
		if(isValid)// once it is valid we can enter the loop to edit airport list
		{
			System.out.println("*"+airport + "*" + airportCode+"*");
			boolean isFound = false;
			for(int i=0;i<airportList.size() && !isFound;i++) //loops through airportList
			{
				if(airportList.get(i).get(1).equals(airportCode))//checks if they match once again after sorting
				{
					 
					isFound = true;
					airportList.get(i).set(0,airport);	
				}
					
			}
		if(!isFound){
		errorMessage(5);
		}
	}
}
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
     public static void deleteAirport(String airportCode)
	{
		if(validateAirport(airportCode, 2))
		{
			boolean isFound =false;
			boolean flightToDelete=false;
			int rowFound=0;
			for (int i = 0; i <airportList.size(); i++)
			{
				if(airportList.get(i).get(1).equals(airportCode))
				{
					isFound=true;
					rowFound=i;
				}
			}
			if(isFound)
			{
				airportList.remove(rowFound);
				for (int i = 0; i <flightList.size(); i++)
				{
					for(int j = 0; j<flightList.get(i).size();j++)
					{
						if(flightList.get(i).get(j).equals(airportCode))
						{
							rowFound=i;
							flightToDelete=true;
							System.out.println(i);
						}
					}
					if(flightToDelete)
					{
						flightList.remove(rowFound);
						flightToDelete=false;
						i--;
					}
				}
			}
			else
			{
				errorMessage(8);
			}
		}
		else
		{
			errorMessage(3);
		}	
    }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
public static void editFlight(String flightNum,String flightDays,String startDateFlight,String endDateFlight)
{
  boolean isValid = false; //create a boolean
  if(validateFlight(flightDays,3) && validateFlight(startDateFlight,4) && validateFlight(endDateFlight,4))
  {
	isValid = true;
  }
  else
  {
    if(validateFlight(flightDays,3))errorMessage(13);
    if(validateFlight(startDateFlight,4))errorMessage(14);
    if(validateFlight(endDateFlight,4))errorMessage(15);;
    isValid = false;
  }
  if(isValid)
  {
    for(int i =0; i < flightList.size(); i++)
    {
      if(flightList.get(i).equals(flightNum))
      {
        errorMessage(5);
      }
      else
      {
        flightList.get(i).set(0,flightNum);
      }
    }
  }
}
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
   public static void deleteFlight(String flightNum)
{
  if(validateFlight(flightNum,1))
  {
    boolean isFound = false;
    boolean flightToDelete = false;
    int rowFound = 0;
    for (int i = 0; i < flightList.size(); i++)
    {
      if(flightList.get(i).get(1).equals(flightNum))
      {
        isFound = true;
      }
    }
    if(isFound)
    {
      flightList.remove(rowFound);
      for (int i = 0; i < flightList.size(); i++)
      {
        for(int j = 0; j < flightList.get(i).size();j++)
        {
          if(flightList.get(i).get(j).equals(flightNum))
          {
            rowFound = i;
            flightToDelete = true;
          }
        }
        if(flightToDelete)
        {
          flightList.remove(rowFound);
          flightToDelete = false;
          i--;
        }
      }
    }
    else
    {
      errorMessage(8);
    }
  }
  else
  {
    errorMessage(3);
  }
}
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void searchFlight(String airportDepart,String airportArrive)
	{
		if(validateAirport(airportDepart, 1)&& validateAirport(airportArrive,1))
		{
			boolean departFound =false;
			boolean arriveFound =false;
			String result = "";
			String departCode = "";
			String arriveCode = "";

			
			for (int i = 0; i <airportList.size(); i++)
			{
				if(airportList.get(i).get(0).equals(airportDepart))
				{
					departCode= airportList.get(i).get(1);
					departFound=true;
				}
				if(airportList.get(i).get(0).equals(airportArrive))
				{
					arriveCode= airportList.get(i).get(1);
					arriveFound=true;
				}
			}            
			if(departFound && arriveFound)
			{
				System.out.println("The Following Flight(s) depart from " + airportDepart + " and arrive in " + airportArrive + ".");
				for (int i = 0; i <flightList.size(); i++)
				{
					if(flightList.get(i).get(2).equals(arriveCode)&& flightList.get(i).get(1).equals(departCode))
					{
						System.out.println(flightList.get(i));
					}
				}
			}
			else
			{
				if(!departFound)errorMessage(9);
				if(!arriveFound)errorMessage(10);
			}
		}
		else 
		{
			errorMessage(11);
		}

        }
        
		/*
			@author Adam Swayne
			Input: 
			Output:
		*/
        
        public static void searchDate(String airportDepart,String airportArrive, String date)
		{
			boolean stop = false;
			int[] numbers = new int[flightList.size()];
			String airportDepartCode = "", airportArriveCode = "";
			int counter = 0;
			
			//Finds the corresponding airport code to the airports supplied
			for(int i = 0;i < airportList.size() && !stop; i++)
			{
				if (airportDepart.equalsIgnoreCase(airportList.get(i).get(0)))		airportDepartCode += airportList.get(i).get(1);
				if(airportArrive.equalsIgnoreCase(airportList.get(i).get(0)))			airportArriveCode += airportList.get(i).get(1);	
				
				//once both airports have been found, it should break out of the loop
				if ((!(airportDepartCode.equals(""))) && (!(airportArriveCode.equals(""))))		stop = true;
			}
				//if an airport isnt found in the textfile, then an error message is outputted to the user
			if(airportDepartCode.equals(""))				System.out.println("Error, " + airportDepart + " airport not found in Airports.txt");
			else if(airportArriveCode.equals(""))		System.out.println("Error, " + airportArrive + " airport not found in Airports.txt");
			
			else
			{
				for(int i = 0;i < flightList.size();i++)
				{
					//go through all of the arrayList and check if the arrival/departing airport and the date matches what's on file, if it does, then the index of that is put into an array so I can call it again later on
					if((flightList.get(i).get(1).equalsIgnoreCase(airportDepartCode)) && (flightList.get(i).get(2).equalsIgnoreCase(airportArriveCode)) && (flightList.get(i).get(6).equalsIgnoreCase(date)))
					{
						numbers[counter] = i;
						counter++;
					}
					
				}
				//Print out how many flights were found corresponding to search criteria
				if(counter == 1)	System.out.println("There was only 1 flight found matching that criteria.");
				else 						System.out.println("There were " + (counter) + " flights found matching that criteria.");
				
				for(int i = 0; i < counter; i++)
				{
					//Output the textfile data to the user
					System.out.println((i + 1) + ".\nFlight Number   :\t"  + flightList.get(numbers[i]).get(0) + 
																	"\nDeparting from  :\t"   + airportDepart + "\t\tAt : " + flightList.get(numbers[i]).get(3) + "\tOn : " + flightList.get(numbers[i]).get(6) + 
																	"\nArriving to     :\t"        + airportArrive   + "\t\tAt : " + flightList.get(numbers[i]).get(4) + "\tOn : " + flightList.get(numbers[i]).get(7));
				}	
			}
		}
	
	/*
	@authors Adam Swayne
	Input: 
        Output:
	*/
        
        public static void sortFiles()
	{	

		String[] temp;
		for(int i = 0;i < airportList.size();i++)
		{
			for(int j = i + 1;j < airportList.size();j++)
			{
				if(airportList.get(i).get(0).compareToIgnoreCase(airportList.get(j).get(0)) > 0)
				{
					temp = new String[airportList.get(i).size()];
					for(int x = 0; x < airportList.get(i).size();x++ )
					{
						temp[x] = airportList.get(i).get(x);
						
						airportList.get(i).set(x,airportList.get(j).get(x));
						
						airportList.get(j).set(x, temp[x]);					
					}
				}
			}
		}	
	
		for(int i = 0;i < flightList.size();i++)
		{
			for(int j = i + 1;j < flightList.size();j++)
			{
				if(flightList.get(i).get(0).compareToIgnoreCase(flightList.get(j).get(0)) > 0)
				{
					temp = new String[flightList.get(i).size()];
					for(int x = 0; x < flightList.get(i).size();x++ )
					{
						temp[x] = flightList.get(i).get(x);
						
						flightList.get(i).set(x,flightList.get(j).get(x));
						
						flightList.get(j).set(x, temp[x]);					
					}
				}
			}
		}
		
		
		writeToAirports();
		writeToFlights();
       }
        
        /*
	@authors Ian McKay
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
			       currentLine = airportList.get(i).get(0);
			       for(int j = 1; j<airportList.get(i).size();j++)
			       {
				       currentLine = currentLine + "," + airportList.get(i).get(j);
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
	@authors Ian McKay
	Input: 
        Output:
	*/
        
        public static void writeToFlights()
	{
		try
        	{
		       PrintWriter outFile = new PrintWriter("Flights.txt");
		       String currentLine = "";
		       for (int i = 0; i <flightList.size(); i++)
		       {
			       currentLine = flightList.get(i).get(0);
			       for(int j = 1; j<flightList.get(i).size();j++)
			       {
				       currentLine = currentLine + "," + flightList.get(i).get(j);
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
}
