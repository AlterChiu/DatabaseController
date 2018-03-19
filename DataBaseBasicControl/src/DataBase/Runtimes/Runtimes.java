package DataBase.Runtimes;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.TreeMap;

import DataBase.ModelControl.RunAll;
import DataBase.ModelControl.RunRealTime;
import DataBase.ModelControl.RunSelectedYear;
import DataBase.Property.PropertyFile;

public class Runtimes {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException {
		// TODO Auto-generated method stub
		String fileAdd = "C:\\Users\\alter\\Desktop\\dataBase\\propertyFile";
		try{
			fileAdd = args[0];
		}catch(Exception e){
			fileAdd = new File(".").getAbsolutePath();
			fileAdd = fileAdd.substring(0,fileAdd.length()-1)  + "propertyFile";
		}
	
		System.out.println("profileAdd : " +   fileAdd);
		TreeMap<Integer,TreeMap<String,String>> profileArrays = new PropertyFile(fileAdd).getProfileArray();
		int[] orderKey = profileArrays.keySet().stream().mapToInt(i->i).toArray();
		 
		for(int temptOrder : orderKey){
			String modle =  profileArrays.get(temptOrder).get("model").split(",")[0];
	
			if (modle.equals("runSelectedYear"))
				new RunSelectedYear(profileArrays.get(temptOrder));
			else if (modle.equals("runAll"))	
				new RunAll(profileArrays.get(temptOrder));
			else if (modle.equals("runTempt"))	
				new RunRealTime(profileArrays.get(temptOrder));
			}
		}
	}
