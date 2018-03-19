package DataBase.Connection.Result.ReadTable.ReadAll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import DataBase.Connection.Result.MetaData.MetaDataResource;
import DataBase.Connection.Result.ReadTable.ReadTableImplement;

public class ReadAllDataBase {
	private ResultSet rs;
	private Connection con;
	private String readTable;

	public ReadAllDataBase(Connection readCon, String readTable) throws SQLException {
		this.con = readCon;
		this.readTable = readTable;
		String sql = "select * from " + readTable;
		this.rs = readCon.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
				.executeQuery();
	}
	
	public ReadAllDataBase( ReadTableImplement implement) throws SQLException{
		this.con = implement.getConnection();
		this.readTable = implement.getTableName();
		String sql = "select * from " + implement.getTableName();
		this.rs = implement.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
				.executeQuery();
	}
	
	public ResultSet getResult(){
		return this.rs;
	}
	
	public int getSize() throws SQLException{
		ResultSet tempSet = this.rs;
		tempSet.last();
		return tempSet.getRow();
	}
	
	public MetaDataResource  getMetaDataResource() throws SQLException {
		return new MetaDataResource(this.con,this.readTable);
	}
}
