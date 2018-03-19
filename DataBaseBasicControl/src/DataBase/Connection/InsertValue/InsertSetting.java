package DataBase.Connection.InsertValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import DataBase.Setting.BasicSetting;

public class InsertSetting {

	private Connection insertCon;
	private String insertTable;
	private String[] columnNameList;

	public InsertSetting(ArrayList<String[]> insertValue, Connection insertCon, String insertTable,
			String[] columnNameList) {
		this.insertCon = insertCon;
		this.insertTable = insertTable;
		
	}

	public InsertSetting(InsertTableImplement implement) throws SQLException {
		this.insertCon = implement.getConnection();
		this.insertTable = implement.getTableName();
		this.columnNameList = implement.getSelectedColumnName();
	}

	/**
	 * insert Value with the ? mark ]
	 * 
	 * @throws SQLException
	 */

	public void insertValue(ArrayList<String[]> insertValue) throws SQLException {
		insertCon.setAutoCommit(false);
		if (insertValue != null) {

			// get the name list of column in insertDB
			String columnName = columnNameList[0];
			String mark = "?";
			for (int i = 1; i < insertValue.get(0).length; i++) {
				columnName = columnNameList[i] + "," + insertValue.get(0)[i];
				mark = mark + ", ?";
			}

			// set the sql insert query by question mark
			String sql = " insert IGNORE into " + insertTable + "(" + columnName + ") values ( " + mark + ");";
			PreparedStatement pre = insertCon.prepareStatement(sql);

			// prepared statement setting
			for (int row = 0; row < insertValue.size(); row++) {
				for (int column = 1; column <= insertValue.get(row).length; column++) {
					pre.setString(column, insertValue.get(row)[column - 1]);
				}
				try {
					pre.executeUpdate();
				} catch (Exception e) {
				}
				
			}
			// insert value
			insertCon.commit();
			System.out.println("Seccess when insert to " + insertTable);
			pre.close();
		} else {
			System.out.println("There is error data in" + insertTable);
		}
	}

	/**
	 * insert the value by no column name
	 * 
	 * @throws SQLException
	 */

	public void insertValueNoName(ArrayList<String[]> insertValue) throws SQLException {
		insertCon.setAutoCommit(false);
		if (insertValue != null) {
			// get the name list of column in insertDB

			String mark = "?";
			for (int i = 1; i < insertValue.get(0).length; i++) {
				mark = mark + ", ?";
			}

			// set the sql insert query by question mark
			String sql = " insert IGNORE  into " + insertTable + " values ( " + mark + ");";
			PreparedStatement pre = insertCon.prepareStatement(sql);
			// prepared statement setting
			for (int row = 0; row < insertValue.size(); row++) {
				for (int column = 1; column <= insertValue.get(row).length; column++) {
					pre.setString(column, insertValue.get(row)[column - 1]);
				}
				try {
					pre.executeUpdate();
				} catch (Exception e) {
				}
			
			}
			// insert value
			insertCon.commit();
			System.out.println("Seccess when insert to " + insertTable);
			pre.close();
		} else {
			System.out.println("There is error data in" + insertTable);
		}
	}
	
	public void insertValueFast(ArrayList<String[]> insertValue) throws SQLException {
		 StringBuilder sqlString = new StringBuilder();
	
		 sqlString.append( " insert  ignore  into " + insertTable + " values ");
		 
		 Statement pre = this.insertCon.createStatement();
		 
		for(int i=0;i<insertValue.size();i++){
			sqlString.append("(" + String.join(",", Arrays.asList(insertValue.get(i))) + ")\r\n,");
		}
		
		if(insertValue.size()<1){
			
		}else{
			String temptString = sqlString.toString();
			temptString = temptString.substring(0,temptString.length()-1) + ";";
			pre.executeUpdate(temptString);
			pre.close();
		}
		
	}
	
}
