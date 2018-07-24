package DataBase.Property;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import DataBase.Connection.JDBCConnectionStatement;
import usualTool.AtFileReader;

public class PropertyFile {
	private final int dataBaseProfile = 14;

	private TreeMap<Integer, TreeMap<String, String>> profile = new TreeMap<Integer, TreeMap<String, String>>();

	// Setting PropertyFile
	public PropertyFile(String fileAdd) throws IOException {
		int index = 0;
		String content[] = new AtFileReader(fileAdd).getContain();
		for (int i = 3; i < content.length; i++) {
			if (content[i].contains("Section")) {
				TreeMap<String, String> temp = new TreeMap<String, String>();
				for (int j = i; j < i + dataBaseProfile; j++) {

					if (content[j].contains("readUser")) {
						temp.put("readUser", content[j].split("::")[1].trim());

					} else if (content[j].contains("readPass")) {
						temp.put("readPass", content[j].split("::")[1].trim());

					} else if (content[j].contains("readUrl")) {
						temp.put("readIP", content[j].split("::")[1].trim());

					} else if (content[j].contains("readTable")) {
						temp.put("readTable", content[j].split("::")[1].trim());

					} else if (content[j].contains("readDriver")) {
						temp.put("readDriver", content[j].split("::")[1].trim());

					} else if (content[j].contains("insertUser")) {
						temp.put("insertUser", content[j].split("::")[1].trim());

					} else if (content[j].contains("insertPass")) {
						temp.put("insertPass", content[j].split("::")[1].trim());

					} else if (content[j].contains("insertUrl")) {
						temp.put("insertIP", content[j].split("::")[1].trim());

					} else if (content[j].contains("insertTable")) {
						temp.put("insertTable", content[j].split("::")[1].trim());

					} else if (content[j].contains("insertDriver")) {
						temp.put("insertDriver", content[j].split("::")[1].trim());

					} else if (content[j].contains("column")) {
						temp.put("selectedColumnOrder", content[j].split("::")[1].trim());

					} else if (content[j].contains("model")) {
						temp.put("model", content[j].split("::")[1].trim());

					} else if (content[j].contains("translate")) {
						temp.put("translate", content[j].split("::")[1].trim());

					}
				}
				this.profile.put(index, temp);
				index++;
			}
		}
	}

	public TreeMap<String, String> getProfile(int order) {
		return profile.get(order);
	}

	public TreeMap<Integer, TreeMap<String, String>> getProfileArray() {
		return profile;
	}

	public int getSize() {
		return profile.size();
	}

}
