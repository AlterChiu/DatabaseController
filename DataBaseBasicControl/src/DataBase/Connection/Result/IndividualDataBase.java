package DataBase.Connection.Result;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import DataBase.Setting.TimeTranslate;

public class IndividualDataBase {
	private ArrayList<String[]> tableColumn;
	private String[] columnNameList;
	private int[] selectedColumn;

	// make the column order of the original result to fit the target table

	public void setResult(ArrayList<String[]> tableColumn) {
		this.tableColumn = tableColumn;
	}

	public void setColumnNameList(String[] columnNameList) {
		this.columnNameList = columnNameList;
	}

	public void setSelectedColumnOrder(int[] is) {
		this.selectedColumn = is;
	}

	public String[] getColumnListAdjusted() {
		String[] temp = new String[selectedColumn.length];
		for (int i = 0; i < selectedColumn.length; i++) {
			temp[i] = this.columnNameList[this.selectedColumn[i]];
		}
		return temp;
	}

	public ArrayList<String[]> getAdjustedResult() {
		ArrayList<String[]> tempt = new ArrayList<String[]>();
		// setting new column in treeMap by selectedColumn
		for (int rowNum = 0; rowNum < tableColumn.size(); rowNum++) {
			String value[] = new String[selectedColumn.length];
			for (int i = 0; i < value.length; i++) {
				value[i] = "\'" + tableColumn.get(rowNum)[selectedColumn[i]] + "\'";
			}
			tempt.add(value);
		}
		return tempt;
	}

	// column0: STID , column1:time
	public TreeMap<Integer, String[]> getColumnTranslated(long splitMilliSeconds) throws ParseException {
		TreeMap<Integer, String[]> tempt = new TreeMap<Integer, String[]>();
		int tempTreeControl = 0;
		// get value expect the STID and time
		for (int i = 0; i < this.tableColumn.size(); i++) {
			String[] lineContent = this.tableColumn.get(i);
			ArrayList<String> tempArray = new ArrayList<String>();
			for (int column = 2; column < lineContent.length - 1; column++) {
				tempArray.add(lineContent[column]);
			}
			// get the
			String[] timeString = tempArray.parallelStream().toArray(String[]::new);
			long time = TimeTranslate.StringToLong(this.tableColumn.get(i)[1], TimeTranslate.YMDHMS);

			for (int row = 0; row < timeString.length; row++) {
				String tempInput[] = { this.tableColumn.get(i)[0], time + row * splitMilliSeconds + "",
						timeString[row] };
				tempt.put(tempTreeControl, tempInput);
				tempTreeControl++;
			}
		}
		return tempt;
	}

}
