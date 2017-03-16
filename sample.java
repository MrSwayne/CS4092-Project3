 public static void addAirport(String airport,String airportCode)
	   {
        	//figure out which input is the airport name and whihc one is the airport code, then send it off to validation
			int notAlreadyExist = 0;
			//pass string airport and 1 to vlaidateAirport method
			boolean valAirportName = validateAirport(airport,1);
			//pass string aircode and 2 to validateAirport method
			boolean valAirportCode = validateAirport(airportCode,2);
			//if both return true loop through array list and check if airport or airportcode already exist
			if(valAirportCode==true && valAirportName==true)
			{
				for(int i=0;i<airportList.size();i++)
				{
				  if(!airportList.get(i).get(1).equals(airportCode)&&(!airportList.get(i).get(1).equals(airport)))
					{
					  notAlreadyExist=1;
					}  
				
				}	 
			}
			if(notAlreadyExist==1)
			{
				ArrayList<String>Entry = new ArrayList<String>();
				Entry.add(airport);
				Entry.add(airportCode);
				airportList.add(Entry);
			}
			//if both are not already in the lists
			//pop to arraylist
        }
