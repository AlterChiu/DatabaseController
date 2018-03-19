package DataBase.Connection.Result.ReadTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import DataBase.Connection.Result.MetaData.MetaDataResource;

public class ReadTableImplement {
	private String tableName;
	private Connection con;
	private String selectedYear;
	private int[] selectedColumn;

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setSelectYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public void setSelectedColumnOrder(String selectedColumn) {
		String[] tempArray = selectedColumn.split(",");
		this.selectedColumn = Arrays.asList(tempArray).stream().mapToInt(Integer::parseInt).toArray();
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

	public String getSelectedYear() {
		return this.selectedYear;
	}

	public MetaDataResource getMetaData() throws SQLException {
		return new MetaDataResource(this.con, this.tableName);
	}

	public String[] getColumnNameList() throws SQLException {
		return this.getMetaData().getColumName();
	}

	public int[] getSelectedColumnOrder() {
		return this.selectedColumn;
	}

}
