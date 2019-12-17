package com.stepdefinition;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;

import com.base.BaseUtil;
import com.base.ReusableLibrary;
import com.common.utility.RuntimeValues;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class PrevRisk extends ReusableLibrary{

	public PrevRisk(BaseUtil base) {
		super(base);
	}
	
	

	@Given("^select the facility, where RRR greater than equal to '([^\']*)' and the cure rate less than '([^\']*)' from the table 'PREVRISK_DT_DWH'$")
	public void filterWithRRRAndCureRate(String rRR, String cureRate) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		

		String query="Select FLF_NO, RMM_FLF_CURE_RATE_PCT, RMM_FLF_RRR_CODE from PREVRISK_DT_DWH";
		dbConnection.openRAMConnection();
		 ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(query);
		  
		 Assert.assertTrue("No of records found in the table", lstResults.size()>1);
	//get all eligible facility
		 
		 for(int i=0; i<lstResults.size(); i++){
			 
			 String raboRiskRating=lstResults.get(i).get("RMM_FLF_RRR_CODE").toString();
			 String cureRate1=lstResults.get(i).get("RMM_FLF_CURE_RATE_PCT").toString();
			 
			 int riskValue=Integer.parseInt(raboRiskRating.substring(1, raboRiskRating.length()));

			 double roundOff = (double) Math.round(Double.parseDouble(cureRate1) * 100) / 100;
			 
			 
			 if(riskValue>=17 && roundOff<0.5){
				 RuntimeValues.eligibleFacility.add(lstResults.get(i).get("FLF_NO").toString());
			 }
			 
			 
		 }
		 Assert.assertTrue("No facility is eligible", RuntimeValues.eligibleFacility.size()>0);
	
	
	}


	@Then("^PCP case are created for the '([^\']*)' facilitie$")
	public void prevRiskPCPCases(String facilitiy) throws Throwable {
		

		//open db connection
		dbConnection.openRAMConnection();
		Thread.sleep(10000);
		 String sqlQuery="Select PXINSNAME, FACILITYLVLFNCID from RAM_WORK_CREDITPROBLEM where SIGNALTYPE='PREVRISK' and FACILITYLVLFNCID='"+facilitiy+"' ORDER BY PXCREATEDATETIME DESC";
		 ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(sqlQuery);
		 if(lstResults.size()>0) {
				String currentfacilityID=lstResults.get(0).get("FACILITYLVLFNCID").toString();
				String currentCaseId=lstResults.get(0).get("PXINSNAME").toString();
				System.out.println(currentfacilityID+" -------> "+currentCaseId);
				
					  RuntimeValues.hashMap.put(currentfacilityID, currentCaseId);
		 }
		 else {
			 Assert.assertTrue("No case created for the facility: "+facilitiy, false);
		  }
		 
	
	}
	
	@Given("^close all DB connection$")
	public void closeAllConn() throws Exception{
		dbConnection.closeRAMDBConnection();
		
	}


@Given("^Clean the table 'PREVRISK_DT_DWH','RAM_WORK_CREDITPROBLEM' and 'RAM_WORK_REBUTTABLESIGNAL' for 'PREVRISK' signal type$")
public void cleanTables() throws Throwable {
			//open db connection
			dbConnection.openRAMConnection();
			
			//delete the 'PREVRISK_DT_DWH'n table
			String prevRiskTable="DELETE from PREVRISK_DT_DWH WHERE FLF_NO IS NOT Null";
			Assert.assertTrue("Delete table of PREVRISK_DT_DWH.....",dbConnection.deleteDataRAM(prevRiskTable)>=0);
			
			//delete the PREVRISK cases in RAM_WORK_CREDITPROBLEM
			String ramWrkCreditProb="DELETE from RAM_WORK_CREDITPROBLEM WHERE SIGNALTYPE='PREVRISK'";
			Assert.assertTrue("Delete row from RAM_WORK_CREDITPROBLEM....",  dbConnection.deleteDataRAM(ramWrkCreditProb)>=0);
			
			//delete the PREVRISK cases in RAM_WORK_REBUTTABLESIGNAL
			String ramRebutSignal="DELETE from RAM_WORK_REBUTTABLESIGNAL WHERE SIGNALTYPE='PREVRISK'";
			Assert.assertTrue("Delete row from RAM_WORK_REBUTTABLESIGNAL....",  dbConnection.deleteDataRAM(ramRebutSignal)>=0);
			
			

}

@Given("^Insert the row for facility '([^\']*)' where RRR='([^\']*)' and CRR='([^\']*)'$")
public void updateDWH(String facilityId, String riskValue, String cRR) throws Throwable  {
	//open db connection
	dbConnection.openRAMConnection();
	
	String insertQuery="Insert into OWNER_RAD.PREVRISK_DT_DWH (FLF_NO,CAS_BATCH_ID,RMM_MSR_PRD_CODE,RMM_FLF_RRR_CODE,RMM_FLF_CURE_RATE_PCT,RMM_FLF_PDFT_PCT,RMM_FLF_LGD_PCT,RMM_FLF_OTSND_TOT_AMT) values "
			+ "('"+facilityId+"','4ED21733-644D-475F-AADC-182EA84A3E28',201805,'"+riskValue+"',"+cRR+",0.00328727409480229,0.0787036067154601,350396.069262247)";
	
	//delete the 'PREVRISK_DT_DWH' table
	Assert.assertTrue("Insert the data in PREVRISK_DT_DWH.....",dbConnection.deleteDataRAM(insertQuery)>=0);
	
	
	
}

@Given("^Check the table 'RAM_WORK_CREDITPROBLEM' for the '([^\']*)' facility and status as '([^\']*)'$")
public void checkStatus(String facility, String status) throws Throwable {
	
	//open db connection
			dbConnection.openRAMConnection();
			String sqlQuery="Select PYSTATUSWORK, FACILITYLVLFNCID from RAM_WORK_CREDITPROBLEM where PYID='"+RuntimeValues.hashMap.get(facility)+"'";
			// String sqlQuery="Select PXINSNAME, FACILITYLVLFNCID from RAM_WORK_CREDITPROBLEM where SIGNALTYPE='PREVRISK' and FACILITYLVLFNCID='"+facility+"' ORDER BY PXCREATEDATETIME DESC";
			 ArrayList<HashMap<String, Object>> lstResults= dbConnection.connectDB2DatabaseGetRowSet(sqlQuery);
			 if(lstResults.size()>0) {
					String currentfacilityID=lstResults.get(0).get("FACILITYLVLFNCID").toString();
					String caseStatus=lstResults.get(0).get("PYSTATUSWORK").toString();
					System.out.println(currentfacilityID+" -------> "+caseStatus);
					
						  Assert.assertTrue("The status is not same ...",caseStatus.equals(status));
			 }
			 else {
				 Assert.assertTrue("No case created for the facility: "+facility, false);
			  }
			 
	
	
}

@Then("^Status of the facility is 'Resolved-NotRebutted'$")
public void status_of_the_facility_is_Resolved_NotRebutted()  {
    // Write code here that turns the phrase above into concrete actions
}

@Then("^Status of the facility is 'Resolved-Rebutted'$")
public void status_of_the_facility_is_Resolved_Rebutted() {
    // Write code here that turns the phrase above into concrete actions
}



@Then("^Check the column of 'SAMMANAGED'$")
public void check_the_column_of_SAMMANAGED() {
    // Write code here that turns the phrase above into concrete actions
}





}
