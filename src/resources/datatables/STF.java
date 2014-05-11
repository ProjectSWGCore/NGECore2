package resources.datatables;

import engine.clientdata.StfTable;

public class STF
{
	/*
	 	Returns a string containing the value of a key in the SWG STF path;
	 	Example: get("@cmd_err:success_prose") returns "The command %TO was a success."	  
	 */
	static public String get(String stfPath)
	{
		stfPath = stfPath.replace("@", "");
		String stfFile = stfPath.split(":")[0];
		String stfKey = stfPath.split(":")[1];
		
		try
		{
			StfTable stf = new StfTable("clientdata/string/en/" + stfFile + ".stf");
			for (int s = 1; s < stf.getRowCount(); s++) 
			{		
				if(stf.getStringById(s).getKey() != null && stf.getStringById(s).getKey().equals(stfKey)) return stf.getStringById(s).getValue();
			}
        } 
		catch (Exception e) { }
		
		return "";
	}
}
