package DataBase.Connection.Result;

import java.text.ParseException;
import java.util.ArrayList;

import usualTool.TimeTranslate;

public class ResultTranslate {
	ArrayList<String[]> outArrays = new ArrayList<String[]>();
	ArrayList<String[]> result = new ArrayList<String[]>();
	TimeTranslate tt = new TimeTranslate();
	int timeStep;
	int recdate = 1;
	int stno = 0;

	public ResultTranslate(ArrayList<String[]> result, String column , int timeStep) {
		this.result = result;
		this.timeStep = timeStep;
		try {
			if (Integer.parseInt(column.split(",")[0]) == 1) {
				recdate = 0;
				stno = 1;
			}
		} catch (Exception e) {

		}
	}

	public ArrayList<String[]> getResult(String format, double milli) throws ParseException {
		String[][] content = result.parallelStream().toArray(String[][]::new);

		for (int line = 0; line < content.length; line++) {
			for (int column = 2; column < timeStep; column++) {
				String[] tempt = new String[3];
				tempt[stno] = "\'"+content[line][0]+"\'";
				tempt[recdate] ="\'"+ tt.milliToDate((tt.StringToLong(content[line][1], format) + (column - 2) * (long) milli),
						format) + "\'";
				tempt[2] = content[line][column];
				outArrays.add(tempt);

			}
		}
		return this.outArrays;
	}

}
