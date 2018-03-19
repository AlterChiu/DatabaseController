package DataBase.Connection.Result.ReadTable.RealTime;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import DataBase.Connection.Result.MetaData.MetaDataResource;
import DataBase.Connection.Result.ReadTable.ReadTableImplement;
import usualTool.TimeTranslate;



public class ReadRealTime {
	private ResultSet rs;
	private MetaDataResource metaResource ;

	public ReadRealTime(Connection readCon, String table) throws SQLException {
		String date = new TimeTranslate().milliToDate(System.currentTimeMillis() - 2 * 3600 * 1000,
				"yyyy-MM-dd HH:mm:ss");
		this.metaResource = new MetaDataResource(readCon,table);
		String dateColumnName = metaResource.getDateColumnName();
		String sql = "select * from " + table + "where "+dateColumnName+" > " + date;
		this.rs = readCon.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
				.executeQuery();
	}

	public ReadRealTime(ReadTableImplement implement) throws SQLException {
		String date = new TimeTranslate().milliToDate(System.currentTimeMillis() - 2 * 3600 * 1000,
				"yyyy-MM-dd HH:mm:ss");
		this.metaResource = implement.getMetaData();
		String dateColumnName = metaResource.getDateColumnName();
		String sql = "select * from " + implement.getTableName() + "where "+dateColumnName+" > " + date;
		this.rs = implement.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
				.executeQuery();
	}
	

	public ResultSet getResult() {
		return this.rs;
	}
	public int getSize() throws SQLException{
		ResultSet tempSet = this.rs;
		tempSet.last();
		return tempSet.getRow();
	}
	
	public MetaDataResource  getMetaDataResource() throws SQLException {
		return this.metaResource;
	}

}
