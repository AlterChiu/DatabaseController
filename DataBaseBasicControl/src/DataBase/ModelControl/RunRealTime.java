package DataBase.ModelControl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.TreeMap;

import DataBase.Connection.JDBCConnectionStatement;
import DataBase.Connection.InsertValue.InsertSetting;
import DataBase.Connection.InsertValue.InsertTableImplement;
import DataBase.Connection.Result.IndividualDataBase;
import DataBase.Connection.Result.ResultSetSection;
import DataBase.Connection.Result.ResultTranslate;
import DataBase.Connection.Result.ReadTable.ReadTableImplement;
import DataBase.Connection.Result.ReadTable.ReadAll.ReadAllDataBase;
import DataBase.Connection.Result.ReadTable.RealTime.ReadRealTime;
import DataBase.Setting.BasicSetting;

public class RunRealTime {
	private ArrayList<String[]> dataResult = new ArrayList<String[]>();

	public RunRealTime(TreeMap<String, String> profile) throws SQLException, ClassNotFoundException, ParseException {

		// get the read table property from profile
		JDBCConnectionStatement readConnectionStatement = new JDBCConnectionStatement();
		readConnectionStatement.setDriver(profile.get("readDriver"));
		readConnectionStatement.setIP(profile.get("readIP"));
		readConnectionStatement.setUser(profile.get("readUser"));
		readConnectionStatement.setPass(profile.get("readPass"));

		// get the insert table property from profile
		JDBCConnectionStatement insertConnectionStatement = new JDBCConnectionStatement();
		insertConnectionStatement.setDriver(profile.get("insertDriver"));
		insertConnectionStatement.setIP(profile.get("insertIP"));
		insertConnectionStatement.setUser(profile.get("insertUser"));
		insertConnectionStatement.setPass(profile.get("insertPass"));

		// setting the readDB needed connection , selectedYear , tableName ,
		// columnName ,
		ReadTableImplement readImplement = new ReadTableImplement();
		readImplement.setConnection(readConnectionStatement.getConnection());
		readImplement.setTableName(profile.get("readTable"));
		readImplement.setSelectedColumnOrder(profile.get("selectedColumnOrder"));

		// get the resultSet and the function which included
		ReadRealTime readDB = new ReadRealTime(readImplement);

		// setting the selectedColumn which setting in profile
		IndividualDataBase individualDataBase = new IndividualDataBase();
		individualDataBase.setSelectedColumnOrder(readImplement.getSelectedColumnOrder());
		individualDataBase.setColumnNameList(readDB.getMetaDataResource().getColumName());

		// setting the insertDB needed connection , selectedColumnName ,
		// selectedInsertValue
		InsertTableImplement insertImplement = new InsertTableImplement();
		insertImplement.setConnection(insertConnectionStatement.getConnection());
		insertImplement.setSelectedColumnName(individualDataBase.getColumnListAdjusted());
		insertImplement.setTableName(profile.get("insertTable"));

		InsertSetting insertSet = new InsertSetting(insertImplement);

		// setting the rowLimit
		int rowLimit = BasicSetting.rowLimit;
		if (!profile.get("translate").split(",")[0].equals("false")) {
			rowLimit = BasicSetting.changableRowLimit;
		}else{
			rowLimit = BasicSetting.rowLimit;
		}

		// rung the section on resultSet which divided by the
		// BasicSetting.rowLimit
		for (int resultSection = 0; resultSection < readDB.getSize() / rowLimit + 1; resultSection++) {
			// get the resultData
			this.dataResult = new ResultSetSection().getSectionResult(resultSection, readDB.getResult());
			// make the resultData into the selected columnOrder
			if (profile.get("translate").split(",")[0].equals("false")) {
				individualDataBase.setResult(this.dataResult);
				this.dataResult = individualDataBase.getAdjustedResult();
				insertSet.insertValueFast(this.dataResult);

			} else {
				String format = profile.get("translate").split(",")[0];
				double delay = Double.parseDouble(profile.get("translate").split(",")[1]);
				int timeStep = Integer.parseInt(profile.get("translate").split(",")[2]);
				this.dataResult = new ResultTranslate(this.dataResult, profile.get("selectedColumnOrder"), timeStep)
						.getResult(format, delay);
				insertSet.insertValueFast(this.dataResult);
			}
		}
		System.out.println("insert complete " + profile.get("insertTable") + "\t" + profile.get("insertIP")+"\r\n");
	}

}
