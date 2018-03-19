package DataBase.Connection.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import DataBase.Setting.BasicSetting;

/**
 * return the value of the table by tree
 * 
 * @author alter
 *
 */

public class ResultSetSection {
	private ArrayList<String[]> tr = new ArrayList<String[]>();

	/**
	 * 
	 * @param the
	 *            index of resultSet section
	 * @param resultSet
	 * @return TreeMap<Integer,String[]> the value of table of this section
	 * @throws SQLException
	 */

	public ArrayList<String[]> getSectionResult(int index, ResultSet rs) throws SQLException {
		// set result set to the point location
		if (index != 0) {
			rs.absolute(index * BasicSetting.rowLimit);
		} else {
			rs.first();
		}

		try {
			for (int i = 0; i < BasicSetting.rowLimit + 1; i++) {
				String[] tempt = new String[rs.getMetaData().getColumnCount()];
				for (int column = 0; column < tempt.length; column++) {
					try {
						tempt[column] = rs.getString(column + 1);
					} catch (Exception e) {
						tempt[column] = "";
					}
				}
				tr.add(tempt);
				
				// if the last section is done break the loop
//========================================
				if(!rs.isLast()){
					rs.next();
				}else{
					break;
				}
//========================================
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return this.tr;
	}

	public ArrayList<String[]> getSectionResult(ResultSet rs) throws SQLException {
		try {
			while (rs.next()) {
				String[] tempt = new String[rs.getMetaData().getColumnCount()];
				for (int column = 0; column < tempt.length; column++) {
					try{
						tempt[column] = rs.getString(column + 1);
					}catch(Exception e){
						tempt[column] = "";
					}
				}
				tr.add(tempt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.tr;
	}

}
