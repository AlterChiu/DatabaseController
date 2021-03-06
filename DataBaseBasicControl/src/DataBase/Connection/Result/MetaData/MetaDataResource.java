package DataBase.Connection.Result.MetaData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

public class MetaDataResource {

	private String dateColumnName;
	ResultSetMetaData tempRsm = null;

	// get MetaDataResult by select*table

	public MetaDataResource(Connection con, String table) throws SQLException {
		// check for the mssql or the mysql
		try {
			String sql = "Select top 2  * from " + table;
			ResultSet rs = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery();
			this.tempRsm = rs.getMetaData();
		} catch (Exception e) {

			// SQL for mysql
			String sql = "Select  * from " + table + " LIMIT 2";
			ResultSet rs = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery();
			this.tempRsm = rs.getMetaData();
		}

		// get the date column naem
		for (int i = 1; i <= tempRsm.getColumnCount(); i++) {
			if (tempRsm.getColumnTypeName(i).equals("DATETIME") || tempRsm.getColumnTypeName(i).equals("datetime")
					|| tempRsm.getColumnTypeName(i).equals("smalldatetime")) {
				dateColumnName = tempRsm.getColumnName(i);
				i = tempRsm.getColumnCount() + 1;
				break;
			}
		}
	}

	public String getDateColumnName() throws SQLException {
		return dateColumnName;
	}

	public String[] getColumName() throws SQLException {
		ArrayList<String> columnNameList = new ArrayList<String>();
		for (int i = 0; i < tempRsm.getColumnCount(); i++) {
			columnNameList.add(tempRsm.getColumnName(i + 1));
		}
		return columnNameList.parallelStream().toArray(String[]::new);
	}

}
