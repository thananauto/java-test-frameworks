package com.stepdefinition;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Assert;

import com.base.BaseUtil;
import com.base.ReusableLibrary;
import com.common.utility.RuntimeValues;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class Database extends ReusableLibrary {

	public Database(BaseUtil base) {
		super(base);
	}
	
	@When("^Check whether new '([^\']*)' cases get created in DB for the '([^\']*)'$")
	public void caseCreation(String caseName, String facilityId) throws Exception  {
		//open db connection
		dbConnection.openRAMConnection();
		
		String strQuery=null;
		if(facilityId.length()==5)
			strQuery = "Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='" + caseName + "' and FACILITYLVLFNCID='" + facilityId + "' ORDER BY PXCREATEDATETIME DESC";
		else if (facilityId.length() == 8)
			strQuery = "Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='" + caseName + "' and FACILITYLVLFNCID='" + facilityId + "' ORDER BY PXCREATEDATETIME DESC";
		else if (facilityId.length() == 9)
			strQuery = "Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='" + caseName + "' and FACILITYLVLFNCID='" + facilityId + "' ORDER BY PXCREATEDATETIME DESC";
		else if(facilityId.length()==10)
			 strQuery="Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='"+caseName+"' and PRODUCTARRANGEMENTID='"+facilityId+"' ORDER BY PXCREATEDATETIME DESC";
		else if(facilityId.length()==15)
			strQuery="Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='"+caseName+"' and RELATIONID='"+facilityId+"' ORDER BY PXCREATEDATETIME DESC";
		  ArrayList<HashMap<String, Object>> lstResults =null;
		//sometimes the first row value is come as null
		do{
			lstResults= dbConnection.connectDB2DatabaseGetRowSet(strQuery);
		}while(lstResults.size()>0 && lstResults.get(0).get("CASEID")==null);

		if(lstResults.size()>0){

		  	String currentCaseStatus=lstResults.get(0).get("AGENTSTATUS").toString();
		  String currentCaseId=lstResults.get(0).get("CASEID").toString();
		try {
			currentCaseId = lstResults.get(0).get("CASEID").toString();
		} catch (Exception e) {Thread.sleep(5000);}// wait for 60sec so that agent status can changed to completed

			  if( (caseName.equalsIgnoreCase("OVERDRAFT") || caseName.equalsIgnoreCase("DPD15")) && currentCaseStatus.equals("Completed") && !currentCaseId.equals(RuntimeValues.lastCaseId)){
				 String caseCreationQuery="Select PCPCASEID from RAM_WORK_REBUTTABLESIGNAL  where PYID='"+currentCaseId+"'";
				  lstResults= dbConnection.connectDB2DatabaseGetRowSet(caseCreationQuery);
				  String pyId=lstResults.get(0).get("PCPCASEID").toString();
				  Assert.assertTrue("Cases Id id is "+currentCaseId+" and PY Id is "+pyId, pyId!=null);
				  RuntimeValues.hashMap.put(facilityId, pyId);

			  } else if( currentCaseStatus.equals("Completed") && !currentCaseId.equals(RuntimeValues.lastCaseId)){
			  String caseCreationQuery="Select PYID from RAM_WORK_CREDITPROBLEM  where RSID='"+currentCaseId+"'";
			  lstResults= dbConnection.connectDB2DatabaseGetRowSet(caseCreationQuery);
			  String pyId=lstResults.get(0).get("PYID").toString();
			  Assert.assertTrue("Cases Id id is "+currentCaseId+" and PY Id is "+pyId, pyId!=null);
			  RuntimeValues.hashMap.put(facilityId, pyId);
			  
		  }else{
			  caseCreation(caseName, facilityId);
		  }
		  } else{
		  //close the connection at end
		  dbConnection.closeRAMDBConnection();
		  Assert.assertTrue("No new cases get created", lstResults.size()>0);
		  }
	}
	
	
	@When("^Check whether new dossier '([^\']*)' cases get created in DB for the '([^\']*)'$")
	public void fraudeCreation(String caseName, String facilityId) throws Exception  {
		//open db connection
		dbConnection.openRAMConnection();
		
		String strQuery=null;
		if(facilityId.length()==5)
		 strQuery="Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='"+caseName+"' and FACILITYLVLFNCID='"+facilityId+"' ORDER BY PXCREATEDATETIME DESC";
		
		else if(facilityId.length()==10)
			 strQuery="Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='"+caseName+"' and PRODUCTARRANGEMENTID='"+facilityId+"' ORDER BY PXCREATEDATETIME DESC";
		
		else if(facilityId.length()==15)
			strQuery="Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='"+caseName+"' and RELATIONID='"+facilityId+"' ORDER BY PXCREATEDATETIME DESC";

		  ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(strQuery);
		  if(lstResults.size()>0){
		  String currentCaseStatus=lstResults.get(1).get("AGENTSTATUS").toString();
		  String currentCaseId=lstResults.get(1).get("CASEID").toString();
		try {
			currentCaseId = lstResults.get(0).get("CASEID").toString();
		} catch (Exception e) {Thread.sleep(5000);}// wait for 5sec so that agent status can changed to completed
		  
		  if( currentCaseStatus.equals("Completed") && !currentCaseId.equals(RuntimeValues.lastCaseId)){
			  String caseCreationQuery="Select PYID from RAM_WORK_CREDITPROBLEM  where RSID='"+currentCaseId+"' order by PYID DESC";
			  lstResults= dbConnection.connectDB2DatabaseGetRowSet(caseCreationQuery);
			  String pyId=lstResults.get(0).get("PYID").toString();
			  Assert.assertTrue("Cases Id id is "+currentCaseId+" and PY Id is "+pyId, pyId!=null);
			  RuntimeValues.hashMap.put(facilityId, pyId);
			  
		  }else{
			  caseCreation(caseName, facilityId);
		  }
		  } else{
		  //close the connection at end
		  dbConnection.closeRAMDBConnection();
		  Assert.assertTrue("No new cases get created", lstResults.size()>0);
		  }
	}

	@When("^Check that no duplicate cases get created for '([^\']*)'$")
	public void scheckDup(String relationId) throws Exception {
		//open db connection
		dbConnection.openRAMConnection();

		LocalDate dayToday = LocalDate.now();
		String forParse = dayToday.toString();

		String OLD_FORMAT = "yyyy-MM-dd";
		String NEW_FORMAT = "dd-MM-yy";
		String todayDateString;

		SimpleDateFormat today = new SimpleDateFormat(OLD_FORMAT);
		Date td = today.parse(forParse);
		today.applyPattern(NEW_FORMAT);
		todayDateString = today.format(td);

		String strQuery = null;
		strQuery = "select PXCURRENTSTAGELABEL from RAM_WORK_REBUTTABLESIGNAL where RELATIONID='" + relationId + "' and PXCURRENTSTAGELABEL='Duplicate Case' and to_char(PXCREATEDATETIME,'dd-mm-yy') = '"+todayDateString+"'";

		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(strQuery);
		if (lstResults.size() > 0) {
			String currentCaseStatus = lstResults.get(0).get("PXCURRENTSTAGELABEL").toString();
			try {
				currentCaseStatus = lstResults.get(0).get("PXCURRENTSTAGELABEL").toString();
			} catch (Exception e) {
				Thread.sleep(5000);
			}// wait for 5sec so that agent status can changed to completed

			if (currentCaseStatus.equals("Duplicate Case")) {
				Assert.assertTrue("Cases status is " + currentCaseStatus, true);

			}
			else {
				//close the connection at end
				Assert.assertTrue("No duplicate cases found" + currentCaseStatus, false);
			}
		}
		if(lstResults.size()<1){

				Assert.assertTrue("No results found", false);
			}

				dbConnection.closeRAMDBConnection();

	}

	@When("^Check whether new '([^\']*)' filtering cases get created in DB for the '([^\']*)'$")
	public void caseCreationBkr(String caseName, String facilityId) throws Exception  {
		//open db connection
		dbConnection.openRAMConnection();
		
		 String sqlQuery="Select PXINSNAME, FACILITYLVLFNCID from RAM_WORK_CREDITPROBLEM where SIGNALTYPE='"+caseName+"' and FACILITYLVLFNCID='"+facilityId+"' ORDER BY PXCREATEDATETIME DESC";
		 ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(sqlQuery);
		 if(lstResults.size()>0) {
				String currentfacilityID=lstResults.get(0).get("FACILITYLVLFNCID").toString();
				String currentCaseId=lstResults.get(0).get("PXINSNAME").toString();
				
				if( currentfacilityID.equals(facilityId) && !currentCaseId.equals(RuntimeValues.lastCaseId)) {
					String caseCreationQuery="Select PYID from RAM_WORK_CREDITPROBLEM where PXINSNAME='"+currentCaseId+"'";
					  lstResults= dbConnection.connectDB2DatabaseGetRowSet(caseCreationQuery);
					  String pyId=lstResults.get(0).get("PYID").toString();
					  Assert.assertTrue("Cases Id id is "+currentCaseId+" and PY Id is "+pyId, pyId!=null);
					  RuntimeValues.hashMap.put(facilityId, pyId);
				}
		 }
		 else if(lstResults.size()<1) {
		  Assert.assertTrue("No matching results found", false);
		  }
		  dbConnection.closeRAMDBConnection();
		  
	}
	
	@When("^Check whether new '([^\']*)' cases get created$")
	public void createFraudCase(String caseName) throws Exception  {
		//open db connection
		dbConnection.openRAMConnection();
		
		 String sqlQuery="Select PYID from RAM_WORK_CREDITPROBLEM where SIGNALTYPE='"+caseName+"' ORDER BY PXCREATEDATETIME DESC";
		 ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(sqlQuery);
		 if(lstResults.size()>0) {
				String currentCaseId=lstResults.get(0).get("PYID").toString();
				
				if( !currentCaseId.equals(RuntimeValues.lastCaseId)) {
					String caseCreationQuery="Select PYID from RAM_WORK_CREDITPROBLEM where PXINSNAME='"+currentCaseId+"'";
					  lstResults= dbConnection.connectDB2DatabaseGetRowSet(caseCreationQuery);
					  String pyId=lstResults.get(0).get("PYID").toString();
					  Assert.assertTrue("Cases Id id is "+currentCaseId+" and PY Id is "+pyId, pyId!=null);
				}
		 }
		 else if(lstResults.size()<1) {
		  Assert.assertTrue("No matching results found", false);
		  }
		  dbConnection.closeRAMDBConnection();
		  
	}
	
	@When("^Clean up overlijden cases$")
	public void cleanDeceased() throws Exception  {
		dbConnection.openRAMConnection();
		
		String faclPer="delete from RAM_WORK_CREDITPROBLEM where facilitylvlfncid like '%ART%'";
		//String delReb="delete from RAM_DT_REBUTTABLESIGNAL where relationid like '00000444048%'";

		int delPer=dbConnection.deleteDataRAM(faclPer);
		//int delRebut=dbConnection.deleteDataRAM(delReb);
		
		dbConnection.closeRAMDBConnection();
		  
	}
	
	@Given("^I check the data in table in DB cdg case '([^\']*)' with '([^\']*)' and '([^\']*)'$")
	public void checkCDG(String facilityId, String defstat, String Probation) throws Exception {
	   	
		dbConnection.openDTBConnection();
		
		String sqlQuery="select fac.FACILITYID, fac.STATUSFLAG, prob.* from DTB_DT_FACLVLFINDEFPROBPERIOD prob, DTB_DT_FACLVLFINDEFAULTPERIOD fac where fac.FACILITYID='"+facilityId+"' and fac.STATUSFLAG='"+defstat+"' and prob.FACILITYID=fac.FACILITYID and prob.PROBATIONSTATUSFLAG='"+Probation+"'";
		  
		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetData(sqlQuery);
		if(lstResults.size()>0) {
			String currentfacilityID=lstResults.get(0).get("FACILITYID").toString();
			String currentstatusFlag=lstResults.get(0).get("STATUSFLAG").toString();
			String currentProbation=lstResults.get(0).get("PROBATIONSTATUSFLAG").toString();
			
			if( currentfacilityID.equals(facilityId) && currentstatusFlag.equals(defstat) && currentProbation.equals(Probation)){
				  
				  Assert.assertTrue("Facility id is "+currentfacilityID+" Defaultstatus is "+currentstatusFlag+" Probation is "+currentProbation, true);
				}
				}
			else if(lstResults.size()<1) {  
				Assert.assertTrue("No matching results found", false);
			}
			
			dbConnection.closeDTBDBConnection();		
	}
	@Given("^I check that facility '([^\\']*)' is not in DB$")
	public void checkNotPresent(String facilityId) throws Exception {
	   	
		dbConnection.openDTBConnection();
		
		String sqlQuery="select FACILITYID from DTB_DT_FACLVLFINDEFAULTPERIOD where FACILITYID='"+facilityId+"'";
		  
		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetData(sqlQuery);
		if(lstResults.size()>0) {
			Assert.assertTrue("Facility id is present, while it shouldn't", false);
		}
		else if(lstResults.size()<1) {  
			Assert.assertTrue("No matching results found", true);
		}
		
		dbConnection.closeDTBDBConnection();
	}
	@Given("^Check the last '([^\']*)' cases in RAM_DT_BATCH if status is '([^\']*)'$")
	public void checkBkrBatch(String batchType, String Status) throws Exception {
	   	
		dbConnection.openRAMConnection();
		
		String sqlQuery="select * from RAM_DT_BATCH where BATCHTYPE='"+batchType+"' and not BATCHSTATUS='"+Status+"'";
		  
		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(sqlQuery);
		if(lstResults.size()>0) {
			String updateQuery="select * from RAM_DT_BATCH where BATCHTYPE='"+batchType+"' and not BATCHSTATUS='"+Status+"'";
			
			int defSize=dbConnection.updateRamTable(updateQuery, batchType, Status);
			
			Assert.assertEquals("Update the default table in RAM...", 1, defSize);
			
		}
		else if(lstResults.size()<1) {  
			Assert.assertTrue("Database has no incompleted bkr batches", true);
		}
		
	}
	
	@Given("^I see the data extracts correctly to the bix$")
	public void checkBixRecords() throws Exception {
		
		//open db connection
		LocalDate dayToday = LocalDate.now();
		String forParse = dayToday.toString();
		
		String OLD_FORMAT = "yyyy-MM-dd";
		String NEW_FORMAT = "dd-MM-yy";
		String todayDateString;
		
		SimpleDateFormat today = new SimpleDateFormat(OLD_FORMAT);
		Date td = today.parse(forParse);
		today.applyPattern(NEW_FORMAT);
		todayDateString = today.format(td);
		
		String sqlQuery="select count(*) FROM RAM_WORK_CREDITPROBLEM where to_char(PXCREATEDATETIME,'dd-mm-yy') = '"+todayDateString+"'";
		  
		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(sqlQuery);
		if(lstResults.size()>0) {
			Assert.assertTrue("Database has some records with given date", true);
			
		}
		else if(lstResults.size()<1) {  
			Assert.assertTrue("Database has no records with given date", false);
		}
	
			
		String bixQuery="SELECT count(*) FROM POTENTIAL_CREDIT_PROBLEM where to_char(PCP_CLOSE_DATETIME,'dd-mm-yy') = '"+todayDateString+"'";
		
		ArrayList<HashMap<String, Object>> bixResults= dbConnection.connectBixDatabaseGetRowSet(bixQuery);
		if(bixResults.size()>0) {
			if( lstResults.equals(bixResults))
					{
				 		Assert.assertTrue("Facility id is "+lstResults+" Bixextract is "+bixResults, true);
					}
			
		}
		else if(bixResults.size()<1) {  
			Assert.assertTrue("Bix has no data with given date", false);
		}
	}

	@Given("^I see the correct dates in snapshotdate and actual date$")
	public void checkBixDates() throws Exception {

		//open db connection
		dbConnection.openBIXConnection();
		LocalDate dayToday = LocalDate.now();
		LocalDate yesterday = LocalDate.now().minusDays(1);
		String forParse = dayToday.toString();
		String parseYesterday = yesterday.toString();

		String lastDelID="SELECT (MAX(DELIVERYID)) as DELIVERYID from SOURCEDATADELIVERYDATES";

		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectBixDatabaseGetData(lastDelID);
		if(lstResults.size()>0) {
			String finaldeliveryID=lstResults.get(0).get("DELIVERYID").toString();

			String strQuery="SELECT DATEVALUE FROM SOURCEDATADELIVERYDATES where DELIVERYID='"+finaldeliveryID+"'";
			ArrayList<HashMap<String, Object>> lstResults2= dbConnection.connectBixDatabaseGetData(strQuery);
			if(lstResults2.size()>0) {
				String currentDate=lstResults2.get(0).get("DATEVALUE").toString();
				String dateYesterday=lstResults2.get(1).get("DATEVALUE").toString();

				String OLD_FORMAT = "yyyy-MM-dd";
				String NEW_FORMAT = "dd-MM-yy";
				String newDateString;
				String yesterdayDateString;
				String todayDateString;
				String yesterdayString;

				SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
				Date d = sdf.parse(currentDate);
				sdf.applyPattern(NEW_FORMAT);
				newDateString = sdf.format(d);

				SimpleDateFormat ystdy = new SimpleDateFormat(OLD_FORMAT);
				Date dt = ystdy.parse(dateYesterday);
				ystdy.applyPattern(NEW_FORMAT);
				yesterdayDateString = ystdy.format(dt);

				SimpleDateFormat yststr = new SimpleDateFormat(OLD_FORMAT);
				Date yt = yststr.parse(parseYesterday);
				yststr.applyPattern(NEW_FORMAT);
				yesterdayString = yststr.format(yt);

				SimpleDateFormat today = new SimpleDateFormat(OLD_FORMAT);
				Date td = today.parse(forParse);
				today.applyPattern(NEW_FORMAT);
				todayDateString = today.format(td);

				if( todayDateString.equals(newDateString) &&  yesterdayString.equals(yesterdayDateString)){
					Assert.assertTrue("Date today is "+newDateString+" Date yesterday is "+yesterdayDateString, true);
				}
				else {
					Assert.assertTrue("Date today is "+newDateString+" Date yesterday is "+yesterdayDateString, false);
				}
			}
			else {
				Assert.assertTrue("No results were found in the DB", false);
			}

		}
		else {
			Assert.assertTrue("No results were found in the DB", false);
		}
	}

	@Given("^The last created record contains a unique number for DeliveryID$")
	public void checkBixData() throws Exception {

		//open db connection
		dbConnection.openBIXConnection();
		String strQuery="SELECT DELIVERYID FROM (select deliveryid2.*, rownum rnum from (select * from SOURCEDATADELIVERY ORDER BY DELIVERYID DESC) deliveryid2 where rownum <= 2 ) WHERE rnum >= 2";
		String queryFinal="SELECT (MAX(DELIVERYID)) as DELIVERYID from SOURCEDATADELIVERYDATES";

		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectBixDatabaseGetData(strQuery);
		if(lstResults.size()>0) {
			String currentdeliveryID=lstResults.get(0).get("DELIVERYID").toString();

			ArrayList<HashMap<String, Object>> lstResults2= dbConnection.connectBixDatabaseGetData(queryFinal);
			if(lstResults2.size()>0) {
				String finaldeliveryID=lstResults2.get(0).get("DELIVERYID").toString();
				int currDeliveryID = Integer.parseInt(currentdeliveryID);
				int defcurrDelId = currDeliveryID + 1;
				String strcurrDelId=Integer.toString(defcurrDelId);

				if( strcurrDelId.equals(finaldeliveryID)){
					Assert.assertTrue("DeliveryID is "+finaldeliveryID, true);
				}
				else {
					Assert.assertTrue("Date today is not correct "+finaldeliveryID, false);
				}

			}


			else {
				Assert.assertTrue("No matching results found", false);
			}


			dbConnection.closeBIXDBConnection();

		}
	}

	@Given("^I check if the new DeliveryID exists in all tables$")
	public void checkDelIDAllTables() throws Exception {

		//open db connection
		dbConnection.openBIXConnection();
		String strQuery="SELECT (MAX(DELIVERYID)) as DELIVERYID from SOURCEDATADELIVERYDATES";

		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectBixDatabaseGetData(strQuery);
		if(lstResults.size()>0) {
			String currentdeliveryID=lstResults.get(0).get("DELIVERYID").toString();

			String queryFinal="SELECT dey.DELIVERYID FROM FAC_LVL_FIN_DEFAULT_PERIOD dey, FAC_LVL_FIN_DFT_PROB_PERIOD prob, DEFAULT_TRIGGER_PERIOD def where rownum <= 20 and dey.DELIVERYID='"+currentdeliveryID+"' and dey.DELIVERYID=prob.DELIVERYID and dey.DELIVERYID=def.DELIVERYID";

			ArrayList<HashMap<String, Object>> lstResults2= dbConnection.connectBixDatabaseGetData(queryFinal);
			if(lstResults2.size()>0) {
				String finaldeliveryID=lstResults2.get(0).get("DELIVERYID").toString();

				if( currentdeliveryID.equals(finaldeliveryID)){
					Assert.assertTrue("DeliveryID is "+finaldeliveryID, true);
				}
				else {
					Assert.assertTrue("DeliveryID is not present "+finaldeliveryID, false);
				}

			}


			else {
				Assert.assertTrue("No matching results found", false);
			}


			dbConnection.closeBIXDBConnection();

		}
	}

	@Given("^The old deliveryIDs should still exist$")
	public void checkOldDelIDAllTables() throws Exception {

		//open db connection
		dbConnection.openBIXConnection();
		String strQuery="SELECT (MAX(DELIVERYID)) as DELIVERYID from SOURCEDATADELIVERYDATES";

		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectBixDatabaseGetData(strQuery);
		if(lstResults.size()>0) {
			String currentdeliveryID=lstResults.get(0).get("DELIVERYID").toString();

			String queryFinal="SELECT dey.DELIVERYID FROM FAC_LVL_FIN_DEFAULT_PERIOD dey, FAC_LVL_FIN_DFT_PROB_PERIOD prob, DEFAULT_TRIGGER_PERIOD def where rownum <= 20 and dey.DELIVERYID!='"+currentdeliveryID+"' and dey.DELIVERYID=prob.DELIVERYID and dey.DELIVERYID=def.DELIVERYID";

			ArrayList<HashMap<String, Object>> lstResults2= dbConnection.connectBixDatabaseGetData(queryFinal);
			if(lstResults2.size()>0) {
				String finaldeliveryID=lstResults2.get(0).get("DELIVERYID").toString();

				if( currentdeliveryID.equals(finaldeliveryID)){
					Assert.assertTrue("DeliveryID is the same "+finaldeliveryID, false);
				}
				else {
					Assert.assertTrue("DeliveryID is not the same "+finaldeliveryID, true);
				}

			}


			else {
				Assert.assertTrue("No matching results found", false);
			}


			dbConnection.closeBIXDBConnection();

		}
	}

	@Given("^The row counts are matching$")
	public void matchRowCount() throws Exception {
		
		//open db connection
			dbConnection.openBIXConnection();
		String strQuery="SELECT MAX(to_number(DELIVERYID)) as DELIVERYID from SOURCEDATADELIVERYDATES";

		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectBixDatabaseGetData(strQuery);
		if(lstResults.size()>0) {
			String currentdeliveryID=lstResults.get(0).get("DELIVERYID").toString();
			String rowQuery="Select EXPECTEDVALUE from SOURCEDATADELIVERYCFW where DELIVERYID='"+currentdeliveryID+"'";
			
			ArrayList<HashMap<String, Object>> lstResults3= dbConnection.connectBixDatabaseGetData(rowQuery);
			if(lstResults3.size()>0) {
			String rowQueryVal=lstResults3.get(0).get("EXPECTEDVALUE").toString();
			
			String countDefTrigger="select count(*) from DEFAULT_TRIGGER_PERIOD where DELIVERYID='"+currentdeliveryID+"'";
			
			ArrayList<HashMap<String, Object>> lstResults2= dbConnection.connectBixDatabaseGetData(countDefTrigger);
			if(lstResults2.size()>0) {
			String countDefTrig=lstResults2.get(0).get("COUNT(*)").toString();	
			
				
				if( countDefTrig.equals(rowQueryVal)){
				Assert.assertTrue("Default trigger period is "+countDefTrig, true);
				}
				else {
					Assert.assertTrue("Default trigger period count does not match "+countDefTrig, false);
					}
				
			String countProbPeriod="select count(*) from FAC_LVL_FIN_DFT_PROB_PERIOD where DELIVERYID='"+currentdeliveryID+"'";

			ArrayList<HashMap<String, Object>> lstResults4= dbConnection.connectBixDatabaseGetData(countProbPeriod);
			if(lstResults4.size()>0) {
			String countProbPer=lstResults4.get(0).get("COUNT(*)").toString();
			String rowQueryVal2=lstResults3.get(2).get("EXPECTEDVALUE").toString();
				
				if( countProbPer.equals(rowQueryVal2)){
				Assert.assertTrue("Probation period is "+countProbPer, true);
				}
				else {
					Assert.assertTrue("Probation period count does not match "+countProbPer, false);
					}
				
				String countDefPeriod="select count(*) from FAC_LVL_FIN_DEFAULT_PERIOD where DELIVERYID='"+currentdeliveryID+"'";

				ArrayList<HashMap<String, Object>> lstResults5= dbConnection.connectBixDatabaseGetData(countDefPeriod);
				if(lstResults5.size()>0) {
				String countDefPer=lstResults5.get(0).get("COUNT(*)").toString();
				String rowQueryVal3=lstResults3.get(1).get("EXPECTEDVALUE").toString();
					
					if( countDefPer.equals(rowQueryVal3)){
					Assert.assertTrue("Default period is "+countDefTrig, true);
					}
					else {
						Assert.assertTrue("Default period count does not match "+countDefTrig, false);
						}
			
			dbConnection.closeBIXDBConnection();
			
			}
		}
		}
		}
		}
	}
	
	@Given("^Previous testdata is deleted$")
	public void deleteRowBatchDT() throws Exception {
		String clearNotification="TRUNCATE TABLE RBG_BKR_NOTIFICATION";
		String clearGroupedinfo="TRUNCATE TABLE RBG_BKR_GROUPEDINFO";
		
		int delPrim=dbConnection.deleteDataRAM(clearNotification);
		int delSub=dbConnection.deleteDataRAM(clearGroupedinfo);
		
		Assert.assertEquals("Delete row...", 0, delPrim);
		Assert.assertEquals("Delete row...", 0, delSub);
	}
	@Given("^Clear the BKR_CTR_AMS table before starting$")
	public void clearAMStable() throws Exception {		
		String sqlDeleteBkrRecords = "TRUNCATE TABLE BKR_CTR_AMS";
		int defSize=dbConnection.deleteDataRAM(sqlDeleteBkrRecords);
		
		Assert.assertEquals("Delete row...", 0, defSize);
		
		dbConnection.closeRAMDBConnection();
	}
	@Given("^Add the data to the BKR table$")
	public void addDataBkr() throws Exception {
		dbConnection.openRAMConnection();
		String lastCaseId="SELECT (MAX(CAST(BATCHID as int)) + 1) BATCHID from RAM_DT_BATCH where BATCHTYPE='BKR'";
		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(lastCaseId);
		  if(lstResults.size()>0){
		  String currentCaseStatus=lstResults.get(0).get("BATCHID").toString();
		
				String line1="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444013100','1','"+currentCaseStatus+"',(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'RO',(to_date('11-05-2018', 'DD-MM-YYYY')),'0',(to_date('04-05-2018', 'DD-MM-YYYY')),'N','T',(to_date('11-06-2018', 'DD-MM-YYYY')),'T',add_months(sysdate,-1),'T',(to_date('11-03-2018', 'DD-MM-YYYY')),'T',(to_date('11-02-2018', 'DD-MM-YYYY')),'T',(to_date('11-01-2018', 'DD-MM-YYYY')),'T',(to_date('11-12-2017', 'DD-MM-YYYY')),'T',(to_date('11-11-2017', 'DD-MM-YYYY')))";
				String line2="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444013110','2','"+currentCaseStatus+"',(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('04-12-2017', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'RO',(to_date('11-05-2018', 'DD-MM-YYYY')),'0',(to_date('04-05-2015', 'DD-MM-YYYY')),'N','T',(to_date('11-06-2018', 'DD-MM-YYYY')),'T',add_months(sysdate,-1),'T',(to_date('11-03-2018', 'DD-MM-YYYY')),'T',(to_date('11-02-2018', 'DD-MM-YYYY')),'T',(to_date('11-01-2018', 'DD-MM-YYYY')),'T',(to_date('11-12-2017', 'DD-MM-YYYY')),'T',(to_date('11-11-2017', 'DD-MM-YYYY')))";
				String line3="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444005002','3','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','A',add_months(sysdate,-1),'A',(to_date('13-04-2018', 'DD-MM-YYYY')),'A',(to_date('13-03-2018', 'DD-MM-YYYY')),'A',(to_date('13-02-2018', 'DD-MM-YYYY')),'A',(to_date('13-01-2018', 'DD-MM-YYYY')),'A',(to_date('13-12-2017', 'DD-MM-YYYY')),'A',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line4="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444005001','4','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','T',add_months(sysdate,-1),'T',(to_date('13-04-2018', 'DD-MM-YYYY')),'T',(to_date('13-03-2018', 'DD-MM-YYYY')),'T',(to_date('13-02-2018', 'DD-MM-YYYY')),'T',(to_date('13-01-2018', 'DD-MM-YYYY')),'T',(to_date('13-12-2017', 'DD-MM-YYYY')),'T',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line5="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444007000','5','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','C',add_months(sysdate,-1),'C',(to_date('13-04-2018', 'DD-MM-YYYY')),'C',(to_date('13-03-2018', 'DD-MM-YYYY')),'C',(to_date('13-02-2018', 'DD-MM-YYYY')),'C',(to_date('13-01-2018', 'DD-MM-YYYY')),'C',(to_date('13-12-2017', 'DD-MM-YYYY')),'C',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line6="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444007001','6','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','T',add_months(sysdate,-1),'T',(to_date('13-04-2018', 'DD-MM-YYYY')),'T',(to_date('13-03-2018', 'DD-MM-YYYY')),'T',(to_date('13-02-2018', 'DD-MM-YYYY')),'T',(to_date('13-01-2018', 'DD-MM-YYYY')),'T',(to_date('13-12-2017', 'DD-MM-YYYY')),'T',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line7="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444004000','7','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','T',add_months(sysdate,-1),'T',(to_date('13-04-2018', 'DD-MM-YYYY')),'T',(to_date('13-03-2018', 'DD-MM-YYYY')),'T',(to_date('13-02-2018', 'DD-MM-YYYY')),'T',(to_date('13-01-2018', 'DD-MM-YYYY')),'T',(to_date('13-12-2017', 'DD-MM-YYYY')),'T',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line8="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444004001','8','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','T',add_months(sysdate,-1),'T',(to_date('13-04-2018', 'DD-MM-YYYY')),'T',(to_date('13-03-2018', 'DD-MM-YYYY')),'T',(to_date('13-02-2018', 'DD-MM-YYYY')),'T',(to_date('13-01-2018', 'DD-MM-YYYY')),'T',(to_date('13-12-2017', 'DD-MM-YYYY')),'T',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line9="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444006000','9','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','T',add_months(sysdate,-1),'T',(to_date('13-04-2018', 'DD-MM-YYYY')),'T',(to_date('13-03-2018', 'DD-MM-YYYY')),'T',(to_date('13-02-2018', 'DD-MM-YYYY')),'T',(to_date('13-01-2018', 'DD-MM-YYYY')),'T',(to_date('13-12-2017', 'DD-MM-YYYY')),'T',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line10="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444006001','10','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','T',add_months(sysdate,-1),'T',(to_date('13-04-2018', 'DD-MM-YYYY')),'T',(to_date('13-03-2018', 'DD-MM-YYYY')),'T',(to_date('13-02-2018', 'DD-MM-YYYY')),'T',(to_date('13-01-2018', 'DD-MM-YYYY')),'T',(to_date('13-12-2017', 'DD-MM-YYYY')),'T',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line11="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444003000','11','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','H',add_months(sysdate,-1),'H',(to_date('13-04-2018', 'DD-MM-YYYY')),'H',(to_date('13-03-2018', 'DD-MM-YYYY')),'H',(to_date('13-02-2018', 'DD-MM-YYYY')),'H',(to_date('13-01-2018', 'DD-MM-YYYY')),'H',(to_date('13-12-2017', 'DD-MM-YYYY')),'H',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line12="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444003001','12','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','5',add_months(sysdate,-1),'5',(to_date('13-04-2018', 'DD-MM-YYYY')),'5',(to_date('13-03-2018', 'DD-MM-YYYY')),'5',(to_date('13-02-2018', 'DD-MM-YYYY')),'5',(to_date('13-01-2018', 'DD-MM-YYYY')),'5',(to_date('13-12-2017', 'DD-MM-YYYY')),'5',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line13="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444003002','13','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','9',add_months(sysdate,-1),'9',(to_date('13-04-2018', 'DD-MM-YYYY')),'9',(to_date('13-03-2018', 'DD-MM-YYYY')),'9',(to_date('13-02-2018', 'DD-MM-YYYY')),'9',(to_date('13-01-2018', 'DD-MM-YYYY')),'9',(to_date('13-12-2017', 'DD-MM-YYYY')),'9',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line14="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444008000','14','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','1',add_months(sysdate,-1),'1',(to_date('13-04-2018', 'DD-MM-YYYY')),'1',(to_date('13-03-2018', 'DD-MM-YYYY')),'1',(to_date('13-02-2018', 'DD-MM-YYYY')),'1',(to_date('13-01-2018', 'DD-MM-YYYY')),'1',(to_date('13-12-2017', 'DD-MM-YYYY')),'1',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line15="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444008002','15','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','2',add_months(sysdate,-1),'2',(to_date('13-04-2018', 'DD-MM-YYYY')),'2',(to_date('13-03-2018', 'DD-MM-YYYY')),'2',(to_date('13-02-2018', 'DD-MM-YYYY')),'2',(to_date('13-01-2018', 'DD-MM-YYYY')),'2',(to_date('13-12-2017', 'DD-MM-YYYY')),'2',(to_date('13-11-2017', 'DD-MM-YYYY')))";
				String line16="insert into BKR_CTR_AMS (REL_ID, BKR_CTR_SEQ_NO, DELIVERY_SQN, ACT_DTS, PREV_ACT_DTS, LOAD_DTS, BKR_CHK_DT, BKR_CTR_TP_CODE, BKR_CTR_RGST_DT, BKR_CTR_LOAN_AMT, BKR_CTR_FRST_REPYMT_DT, BKR_CTR_EXPC_FNL_REPYMT_DT, BKR_CTR_ACT_FNL_REPYMT_DT, BKR_CTR_REPYMT_SET_BY_BKR_F, BKR_CTR_PCIRC_CODE_1, BKR_CTR_PCIRC_CODE_1_CRT_DT, BKR_CTR_PCIRC_CODE_2, BKR_CTR_PCIRC_CODE_2_CRT_DT, BKR_CTR_PCIRC_CODE_3, BKR_CTR_PCIRC_CODE_3_CRT_DT, BKR_CTR_PCIRC_CODE_4, BKR_CTR_PCIRC_CODE_4_CRT_DT, BKR_CTR_PCIRC_CODE_5, BKR_CTR_PCIRC_CODE_5_CRT_DT, BKR_CTR_PCIRC_CODE_6, BKR_CTR_PCIRC_CODE_6_CRT_DT, BKR_CTR_PCIRC_CODE_7, BKR_CTR_PCIRC_CODE_7_CRT_DT) \r\n" + 
				"VALUES ('000004444009000','16','"+currentCaseStatus+"',(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('11-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),(to_date('18-05-2018', 'DD-MM-YYYY')),'AK',(to_date('18-05-2017', 'DD-MM-YYYY')),'104171',(to_date('04-12-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),(to_date('04-05-2018', 'DD-MM-YYYY')),'N','2',add_months(sysdate,-1),'2',(to_date('13-03-2018', 'DD-MM-YYYY')),'2',(to_date('13-02-2018', 'DD-MM-YYYY')),'2',(to_date('13-01-2018', 'DD-MM-YYYY')),'2',(to_date('13-12-2017', 'DD-MM-YYYY')),'2',(to_date('13-11-2017', 'DD-MM-YYYY')),'2',(to_date('13-10-2017', 'DD-MM-YYYY')))";
		  
						int delSub=dbConnection.deleteDataRAM(line1);
						int delDef=dbConnection.deleteDataRAM(line2);
						int delCross=dbConnection.deleteDataRAM(line3);
						int delFin=dbConnection.deleteDataRAM(line4);
						int delPer=dbConnection.deleteDataRAM(line5);
						int delProb=dbConnection.deleteDataRAM(line6);
						int delseven=dbConnection.deleteDataRAM(line7);
						int deleight=dbConnection.deleteDataRAM(line8);
						int delnine=dbConnection.deleteDataRAM(line9);
						int delten=dbConnection.deleteDataRAM(line10);
						int deleleven=dbConnection.deleteDataRAM(line11);
						int deltwelve=dbConnection.deleteDataRAM(line12);
						int delthirteen=dbConnection.deleteDataRAM(line13);
						int delfourteen=dbConnection.deleteDataRAM(line14);
						int delfifteen=dbConnection.deleteDataRAM(line15);
						int delsixteen=dbConnection.deleteDataRAM(line16);

						Assert.assertEquals("Add delSub row...", 1, delSub);
						Assert.assertEquals("Add delDef row...", 1, delDef);
						Assert.assertEquals("Add delCross row...", 1, delCross);
						Assert.assertEquals("Add delFin row...", 1, delFin);
						Assert.assertEquals("Add delPer row...", 1, delPer);
						Assert.assertEquals("Add delProb row...", 1, delProb);
						Assert.assertEquals("Add delseven row...", 1, delseven);
						Assert.assertEquals("Add deleight row...", 1, deleight);
						Assert.assertEquals("Add delnine row...", 1, delnine);
						Assert.assertEquals("Add delten row...", 1, delten);
						Assert.assertEquals("Add deleleven row...", 1, deleleven);
						Assert.assertEquals("Add deltwelve row...", 1, deltwelve);
						Assert.assertEquals("Add delthirteen row...", 1, delthirteen);
						Assert.assertEquals("Add delfourteen row...", 1, delfourteen);
						Assert.assertEquals("Add delfifteen row...", 1, delfifteen);
						Assert.assertEquals("Add delsixteen row...", 1, delsixteen);
						
						dbConnection.closeRAMDBConnection();
		  }
	}
	
	@Given("^Check the last '([^\']*)' cases in ([^\']*) table in DB for the '([^\']*)'$")
	public void lastCaseInDB(String caseName, String dummy, String arrOrfacId) throws Exception {
		//open db connection
		dbConnection.openRAMConnection();

		String strQuery=null;
		if(arrOrfacId.length()==5)
			strQuery = "Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='" + caseName + "' and FACILITYLVLFNCID='" + arrOrfacId + "' ORDER BY PXCREATEDATETIME DESC";
		else if (arrOrfacId.length()==8)
			strQuery = "Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='" + caseName + "' and FACILITYLVLFNCID='" + arrOrfacId + "' ORDER BY PXCREATEDATETIME DESC";
		else if (arrOrfacId.length()==9)
			strQuery = "Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='" + caseName + "' and FACILITYLVLFNCID='" + arrOrfacId + "' ORDER BY PXCREATEDATETIME DESC";
		else if(arrOrfacId.length()==10)
			 strQuery="Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='"+caseName+"' and PRODUCTARRANGEMENTID='"+arrOrfacId+"' ORDER BY PXCREATEDATETIME DESC";
		else if(arrOrfacId.length()==15)
			strQuery="Select CASEID, AGENTSTATUS from RAM_DT_REBUTTABLESIGNAL where SIGNALTYPE='"+caseName+"' and RELATIONID='"+arrOrfacId+"' ORDER BY PXCREATEDATETIME DESC";


		ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(strQuery);
	  
	 if(lstResults.size()>0) {
		 RuntimeValues.lastCaseId = lstResults.get(0).get("CASEID").toString();
	 }
	 else
		 RuntimeValues.lastCaseId="No case";
	  //close the connection at end
	  dbConnection.closeRAMDBConnection();
	  Assert.assertTrue("Last case Id"+RuntimeValues.lastCaseId, RuntimeValues.lastCaseId!=null);
	}
	
	
	@Given("^Update the existing DTB table with '([^\']*)' and '([^\']*)' for the '([^\']*)'$")
	public void updateDTBtable(String def, String prob, String facility) throws Exception {
		dbConnection.openDTBConnection();
		
		String defaultQuery="update DTB_DT_FACLVLFINDEFAULTPERIOD a set a.STATUSFLAG=? where FACILITYID=? " 
				   +" and PXCREATEDATETIME = (select max(PXCREATEDATETIME) from DTB_DT_FACLVLFINDEFAULTPERIOD b"
                   +" where a.FACILITYID = b.FACILITYID)";
		
		String probationQuery="update DTB_DT_FACLVLFINDEFPROBPERIOD a set a.PROBATIONSTATUSFLAG=? where FACILITYID=? "
				+ " and PXCREATEDATETIME = (select max(PXCREATEDATETIME) from DTB_DT_FACLVLFINDEFPROBPERIOD b"
                +" where a.FACILITYID = b.FACILITYID)";
		
		
		if(def.equals("Yes"))
			def="1";
		else
			def="0";
		
		int defSize=dbConnection.updateDtbTable(defaultQuery, facility, def);
		int probSize=dbConnection.updateDtbTable(probationQuery, facility, prob);
		
		//default query
		Assert.assertEquals("Update the default table in DTB...", 1, defSize);
		
		//probation query
		Assert.assertEquals("Update the probation table in DTB...", 1, probSize);
		
		dbConnection.closeDTBDBConnection();
	
	}
}
