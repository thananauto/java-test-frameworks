package com.test.automation.web.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

public class DBconnection {
	public String getDbRAMUrl() {
		return dbRAMUrl;
	}
	public String getDbBIXUrl() {
		return dbBIXUrl;
	}
	public void setDbBIXUrl(String dbBIXUrl) {
		this.dbBIXUrl = dbBIXUrl;
	}
	public String getBixUserName() {
		return bixUserName;
	}
	public void setBixUserName(String bixUserName) {
		this.bixUserName = bixUserName;
	}
	public String getBixPassword() {
		return bixPassword;
	}
	public void setBixPassword(String bixPassword) {
		this.bixPassword = bixPassword;
	}
	public Connection getBixCon() {
		return bixCon;
	}
	public void setBixCon(Connection bixCon) {
		this.bixCon = bixCon;
	}

	public void setDbRAMUrl(String dbRAMUrl) {
		this.dbRAMUrl = dbRAMUrl;
	}

	public String getDbDTBUrl() {
		return dbDTBUrl;
	}

	public void setDbDTBUrl(String dbDTBUrl) {
		this.dbDTBUrl = dbDTBUrl;
	}

	public String getRamUserName() {
		return ramUserName;
	}

	public void setRamUserName(String ramUserName) {
		this.ramUserName = ramUserName;
	}

	public String getDtbUserName() {
		return dtbUserName;
	}

	public void setDtbUserName(String dtbUserName) {
		this.dtbUserName = dtbUserName;
	}

	public String getRamPassWord() {
		return ramPassWord;
	}

	public void setRamPassWord(String ramPassWord) {
		this.ramPassWord = ramPassWord;
	}

	public String getDtbPassword() {
		return dtbPassword;
	}

	public void setDtbPassword(String dtbPassword) {
		this.dtbPassword = dtbPassword;
	}

	public Connection getRamCon() {
		return ramCon;
	}

	public void setRamCon(Connection ramCon) {
		this.ramCon = ramCon;
	}

	public Connection getDtbCon() {
		return dtbCon;
	}

	public void setDtbCon(Connection dtbCon) {
		this.dtbCon = dtbCon;
	}
	
	private String dbBIXUrl;
	private String bixUserName;
	private String bixPassword;
	private Connection bixCon;
	private String dbRAMUrl;
	private String dbDTBUrl;
	private String ramUserName, dtbUserName;
	private String ramPassWord, dtbPassword;
	private Connection ramCon, dtbCon;

	public void openRAMConnection() throws SQLException {

		if (ramCon == null) {
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
			TimeZone.setDefault(timeZone);
			ramCon = DriverManager.getConnection(getDbRAMUrl(),
					getRamUserName(), getRamPassWord());
			if (!ramCon.isClosed())
				System.out
						.println("Successfully connected to oracle DB server--> RAM");
			else
				System.out.println("failed to connect oracle DB server--> RAM");
		}
		// set the connection
		setRamCon(ramCon);
	}

	public void openDTBConnection() throws SQLException {

		if (dtbCon == null) {
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
			TimeZone.setDefault(timeZone);
			dtbCon = DriverManager.getConnection(getDbDTBUrl(),
					getDtbUserName(), getDtbPassword());
			if (!dtbCon.isClosed())
				System.out
						.println("Successfully connected to oracle DB server--> DTB");
			else
				System.out.println("failed to connect oracle DB server--> DTB");
		}
		// set the connection
		setDtbCon(dtbCon);
	}
	
	public void openBIXConnection() throws SQLException {

		if (bixCon == null) {
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
			TimeZone.setDefault(timeZone);
			bixCon = DriverManager.getConnection(getDbBIXUrl(),
					getBixUserName(), getBixPassword());
			if (!bixCon.isClosed())
				System.out
						.println("Successfully connected to oracle DB server--> BIX");
			else
				System.out.println("failed to connect oracle DB server--> BIX");
		}
		// set the connection
		setBixCon(bixCon);
	}
	
	public void closeBIXDBConnection() throws Exception {
		if (!bixCon.isClosed()) {
			bixCon.close();
		}

	}

	public ArrayList<HashMap<String, Object>> connectDB2DatabaseGetRowSet(
			String sqlQuery) {
		ArrayList<HashMap<String, Object>> list = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (ramCon.isClosed()) {
				TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
				TimeZone.setDefault(timeZone);
				ramCon = DriverManager.getConnection(getDbRAMUrl(),
						getRamUserName(), getRamPassWord());
			}
			Statement st = ramCon.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			list = new ArrayList<HashMap<String, Object>>(50);
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<String, Object>(
						columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}

				rs.close();
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
		}
		return list;
	}
	
	public ArrayList<HashMap<String, Object>> connectBixDatabaseGetRowSet(
			String sqlQuery) {
		ArrayList<HashMap<String, Object>> list = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (bixCon.isClosed()) {
				TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
				TimeZone.setDefault(timeZone);
				bixCon = DriverManager.getConnection(getDbBIXUrl(),
						getBixUserName(), getBixPassword());
			}
			Statement st = bixCon.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			list = new ArrayList<HashMap<String, Object>>(50);
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<String, Object>(
						columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}

			rs.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<HashMap<String, Object>> connectBixDatabaseGetData(
			String sqlQuery) {
		ArrayList<HashMap<String, Object>> list = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (bixCon.isClosed()) {
				TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
				TimeZone.setDefault(timeZone);
				bixCon = DriverManager.getConnection(getDbBIXUrl(),
						getBixUserName(), getBixPassword());
			}
			Statement st = bixCon.createStatement();
			ResultSet rs = st.executeQuery(sqlQuery);
			
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			list = new ArrayList<HashMap<String, Object>>(50);
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<String, Object>(
						columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}

			rs.close();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}	
	

	public void closeRAMDBConnection() throws Exception {

		if (!ramCon.isClosed()) {
			ramCon.close();
		}

	}

	public void closeDTBDBConnection() throws Exception {
		if (!dtbCon.isClosed()) {
			dtbCon.close();
		}

	}
public int deleteDataDTB(String strQuery) {
				
		int size = 0; 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (dtbCon.isClosed()) {
				TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
				TimeZone.setDefault(timeZone);
				dtbCon = DriverManager.getConnection(getDbDTBUrl(),
						getDtbUserName(), getDtbPassword());
			}
			
			PreparedStatement statement=dtbCon.prepareStatement(strQuery);
			size=statement.executeUpdate();
			statement.close();
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		
		return size;
		
	}
public int deleteDataRAM(String strQuery) {
	
	int size=0; 
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		if (ramCon.isClosed()) {
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
			TimeZone.setDefault(timeZone);
			ramCon = DriverManager.getConnection(getDbDTBUrl(),
					getDtbUserName(), getDtbPassword());
		}
		
		PreparedStatement statement=ramCon.prepareStatement(strQuery);
		size=statement.executeUpdate();
		statement.close();
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	
	return size;
}

public ArrayList<HashMap<String, Object>> connectDB2DatabaseGetData(
		String sqlQuery) {
	ArrayList<HashMap<String, Object>> list = null;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		if (dtbCon.isClosed()) {
			TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
			TimeZone.setDefault(timeZone);
			dtbCon = DriverManager.getConnection(getDbDTBUrl(),
					getDtbUserName(), getDtbPassword());
		}
		Statement st = dtbCon.createStatement();
		ResultSet rs = st.executeQuery(sqlQuery);
		
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		list = new ArrayList<HashMap<String, Object>>(50);
		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>(
					columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}

		rs.close();
		st.close();

	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	return list;
}		
	
	public int readDataDTB(String strQuery) {	
		
		int size=0; 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (dtbCon.isClosed()) {
				TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
				TimeZone.setDefault(timeZone);
				dtbCon = DriverManager.getConnection(getDbDTBUrl(),
						getDtbUserName(), getDtbPassword());
			}
			
			PreparedStatement statement=dtbCon.prepareStatement(strQuery);
			size=statement.executeUpdate();
			statement.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return size;
		
	}


	public int updateDtbTable(String strQuery, String facility, String status) {
		
		
		
		/* strQuery="update DTB_DT_FACLVLFINDEFPROBPERIOD a set a.PROBATIONSTATUSFLAG=? where FACILITYID=? "
				+ " and PXCREATEDATETIME = (select max(PXCREATEDATETIME) from DTB_DT_FACLVLFINDEFPROBPERIOD b"
				                        +" where a.FACILITYID = b.FACILITYID)";
		 
		String strQuery2="update DTB_DT_FACLVLFINDEFAULTPERIOD a set a.STATUSFLAG=? where FACILITYID=? " 
   +" and PXCREATEDATETIME = (select max(PXCREATEDATETIME) from DTB_DT_FACLVLFINDEFAULTPERIOD b"
				                        +" where a.FACILITYID = b.FACILITYID)";*/
		
		int size=0; 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (dtbCon.isClosed()) {
				TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
				TimeZone.setDefault(timeZone);
				dtbCon = DriverManager.getConnection(getDbDTBUrl(),
						getDtbUserName(), getDtbPassword());
			}
			
			PreparedStatement statement=dtbCon.prepareStatement(strQuery);
			statement.setString(1, status);
			statement.setString(2, facility);
			size=statement.executeUpdate();
			statement.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return size;
		
	}
	
public int updateRamTable(String sqlQuery,String caseName, String status) {
		
		int size=0; 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (ramCon.isClosed()) {
				TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
				TimeZone.setDefault(timeZone);
				ramCon = DriverManager.getConnection(getDbDTBUrl(),
						getDtbUserName(), getDtbPassword());
			}
			
			PreparedStatement statement=ramCon.prepareStatement(sqlQuery);
			statement.setString(1, status);
			size=statement.executeUpdate();
			statement.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return size;
		
	}

}
