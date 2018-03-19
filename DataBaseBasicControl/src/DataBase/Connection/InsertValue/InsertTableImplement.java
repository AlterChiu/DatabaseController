package DataBase.Connection.InsertValue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import DataBase.Connection.Result.MetaData.MetaDataResource;

public class InsertTableImplement {
	private String tableName;
	private Connection con;
	private String[] columnName;

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setSelectedColumnName(String[] columnName) {
		this.columnName = columnName;
	}


	public void setConnection(Connection con) {
		this.con = con;
	}

	
	public String getTableName() {
		return this.tableName;
	}

	public Connection getConnection() {
		return this.con;
	}

	public String[] getSelectedColumnName() throws SQLException {
		if(this.columnName == null){
		this.columnName = new MetaDataResource(this.con,this.tableName).getColumName();
		}
		return this.columnName;
	}


}
