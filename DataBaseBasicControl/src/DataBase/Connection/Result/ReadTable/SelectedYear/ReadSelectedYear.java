package DataBase.Connection.Result.ReadTable.SelectedYear;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import DataBase.Connection.Result.MetaData.MetaDataResource;
import DataBase.Connection.Result.ReadTable.ReadTableImplement;

public class ReadSelectedYear {
	private ResultSet rs;
	private MetaDataResource metaResource;

	public ReadSelectedYear(Connection readCon, String table, String year) throws SQLException {
		this.metaResource = new MetaDataResource(readCon, table);
		String dateColumnName = metaResource.getDateColumnName();
		// for ms sql
		try {
			String sql = "select * from " + table + " where  DATEPART(yyyy," + dateColumnName + " )=  " + year + ";";
			this.rs = readCon.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery();
		} // for my sql
		catch (Exception e) {
			String sql = "select * from " + table + " where  DATE_FORMAT(" + dateColumnName + ",'%Y' )=  " + year + ";";
			this.rs = readCon.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery();
		}
	}

	public ReadSelectedYear(ReadTableImplement implement) throws SQLException {
		this.metaResource = implement.getMetaData();
		String dateColumnName = metaResource.getDateColumnName();
		// for ms sql
		try {
			String sql = "select * from " + implement.getTableName() + " where  DATEPART(yyyy," + dateColumnName
					+ " )=  " + implement.getSelectedYear() + ";";
			this.rs = implement.getConnection()
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery();
		} // for my sql
		catch (Exception e) {
			String sql = "select * from " + implement.getTableName() + " where  YEAR(" + dateColumnName
					+ " )= \' " + implement.getSelectedYear() + "\';";
			this.rs = implement.getConnection()
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery();
		}
	}

	public ResultSet getResult() {
		return this.rs;
	}

	public int getSize() throws SQLException {
		ResultSet tempSet = this.rs;
		tempSet.last();
		return tempSet.getRow();
	}

	public MetaDataResource getMetaDataResource() throws SQLException {
		return this.metaResource;
	}
}
