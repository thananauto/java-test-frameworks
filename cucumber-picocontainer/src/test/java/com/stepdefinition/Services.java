package com.stepdefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;

import com.base.BaseUtil;
import com.base.ReusableLibrary;
import com.common.utility.RuntimeValues;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Services extends ReusableLibrary {

	public Services(BaseUtil base) {
		super(base);
	}

	@Given("^User have simulated the (REST Service|rebuttable signal) for '([^\']*)' with '([^\']*)'$")
	public void iniRebuttableSignalService(String dummy, String signalName,
			String facilityId) throws Exception {
		File file = null;
		if (signalName.equals("DPD30"))
			file = comMethod.getFile("testdata/json/dpd30.json");
				else if (signalName.equals("BKR"))
			file = comMethod.getFile("testdata/json/bkr.json");
		else if (signalName.equals("DPD15"))
			file = comMethod.getFile("testdata/json/dpd15.json");
		else if (signalName.equals("BESLAG"))
			file = comMethod.getFile("testdata/json/beslag.json");
		else if (signalName.equals("OVERLIJDEN"))
			file = comMethod.getFile("testdata/json/overlijden.json");
		else if (signalName.equals("cdgBedrijf"))
			file = comMethod.getFile("testdata/json/cdgbedrijf.json");
		else if (signalName.equals("cdgParticulier"))
			file = comMethod.getFile("testdata/json/cdgparticulier.json");
		else if (signalName.equals("Documents"))
			file = comMethod.getFile("testdata/json/" + facilityId);
		else if (signalName.equals("OVERDRAFT"))
			file = comMethod.getFile("testdata/json/overdraft.json");

		String strJson = restService.readJsonToString(file);
		strJson = strJson.replaceAll("#facilityId", facilityId)
				.replaceAll("#LOANARR", facilityId)
				.replaceAll("#BESLAG", facilityId)
				.replaceAll("#OVERLIJDEN", facilityId)
				.replaceAll("#IBAN", facilityId);;
		Assert.assertTrue("No content in json file",
				(strJson != null && !strJson.equals("")));
		restService.setJsonResponse(strJson);
		if (!signalName.equals("Documents"))
			RuntimeValues.hashMap.put(facilityId, "");

	}

	@Given("^Check or add if the cdg data for '([^\']*)' is present in the DTB$")
	public void cdgDataDTB(String facilityId) throws Exception {
		dbConnection.openDTBConnection();

		String strQuery = "Select prob.FACILITYID from DTB_DT_FACLVLFINDEFPROBPERIOD prob, DTB_DT_FACLVLFINDEFAULTPERIOD fac where prob.FACILITYID='"
				+ facilityId
				+ "' and fac.FACILITYID=prob.FACILITYID and prob.FACILITYID=fac.FACILITYID";

		ArrayList<HashMap<String, Object>> lstResults = dbConnection
				.connectDB2DatabaseGetData(strQuery);
		if (lstResults.size() > 0) {
			String currentCaseStatus = lstResults.get(0).get("FACILITYID")
					.toString();
			if (currentCaseStatus.equals(facilityId)) {
				Assert.assertTrue("Facility id is", true);
			}
			dbConnection.closeDTBDBConnection();
		}

		else if (lstResults.size() < 1) {

			String delSubTrigger = "insert into DTB_DT_FACLVLFINDEFAULTPERIOD (PXCREATEDATETIME, FACILITYID, STATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, STARTDATETIME) \r\n"
					+ "VALUES ('09-11-17 12:35:43,620000000', '0400A', '1', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', '0400A!20140601T030000.000 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinancingDefaultPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINANCINGDEFAULTPERIOD 0400A!20140601T030000.000 GMT', '09-11-17', '01-06-14 05:00:00,000000000')";
			String delDefTrigger = "insert into DTB_DT_FACLVLFINDEFPROBPERIOD (PXCREATEDATETIME, FACILITYID, PROBATIONSTATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, MUTATIONDATETIME, DEFAULTSTARTDATETIME) \r\n"
					+ "VALUES ('09-11-17 12:35:43,682000000', '0400A', 'No', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', '0400A!20171109T113543.682 GMT!20140601T030000.000 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinDefProbationPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINDEFPROBATIONPERIOD 0400A!20171109T113543.682 GMT!20140601T030000.000 GMT', '09-11-17', '01-06-14', '01-06-14 05:00:00,000000000')";
			String delCrossTrigger = "insert into DTB_DT_FACLVLFINDEFAULTPERIOD (PXCREATEDATETIME, FACILITYID, STATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, STARTDATETIME) \r\n"
					+ "VALUES ('07-03-18 15:09:21,008000000', '0401A', '1', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', '0401A!20180307T130929.870 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinancingDefaultPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINANCINGDEFAULTPERIOD 0401A!20180307T130929.870 GMT', '07-03-18', '07-03-18 14:09:29,870000000')";
			String faclFin = "insert into DTB_DT_FACLVLFINDEFPROBPERIOD (PXCREATEDATETIME, FACILITYID, PROBATIONSTATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, PROBATIONSTARTDATETIME, PROBATIONDURATION, MUTATIONDATETIME, DEFAULTSTARTDATETIME) \r\n"
					+ "VALUES ('16-05-18 14:36:11,771000000', '0401A', 'Yes', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', '0401A!20180516T123611.771 GMT!20180515T075804.427 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinDefProbationPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINDEFPROBATIONPERIOD 0401A!20180516T123611.771 GMT!20180515T075804.427 GMT', '16-05-18', '16-05-18', '3', '16-05-18', '15-05-18 09:58:04,427000000')";
			String faclPer = "insert into DTB_DT_FACLVLFINDEFAULTPERIOD (PXCREATEDATETIME, FACILITYID, STATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, STARTDATETIME, ENDDATETIME)  \r\n"
					+ "VALUES ('04-04-18 15:27:44,776000000', '0402A', '0', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', '0402A!20180404T124524.103 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinancingDefaultPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINANCINGDEFAULTPERIOD 0402A!20180404T124524.103 GMT', '01-05-18', '04-04-18 14:45:24,103000000', '01-05-18')";
			String probPer = "insert into DTB_DT_FACLVLFINDEFPROBPERIOD (PXCREATEDATETIME, FACILITYID, PROBATIONSTATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, PROBATIONSTARTDATETIME, PROBATIONENDDATETIME, PROBATIONDURATION, MUTATIONDATETIME, DEFAULTSTARTDATETIME, REASON, CALCULATEDENDDATE) \r\n"
					+ "VALUES ('01-05-18 17:10:53,347000000', '0402A', 'Yes', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', '0402A!20180501T151053.347 GMT!20180404T124524.103 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinDefProbationPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINDEFPROBATIONPERIOD 0402A!20180501T151053.347 GMT!20180404T124524.103 GMT', '01-05-18', '01-05-18', '01-05-18', '3', '01-05-18', '04-04-18 14:45:24,103000000', 'Sucessfull', '01-05-18')";

			int delSub = dbConnection.deleteDataDTB(delSubTrigger);
			int delDef = dbConnection.deleteDataDTB(delDefTrigger);
			int delCross = dbConnection.deleteDataDTB(delCrossTrigger);
			int delFin = dbConnection.deleteDataDTB(faclFin);
			int delPer = dbConnection.deleteDataDTB(faclPer);
			int delProb = dbConnection.deleteDataDTB(probPer);

			Assert.assertEquals("Add row...", 1, delSub);
			Assert.assertEquals("Add row...", 1, delDef);
			Assert.assertEquals("Add row...", 1, delCross);
			Assert.assertEquals("Add row...", 1, delFin);
			Assert.assertEquals("Add row...", 1, delPer);
			Assert.assertEquals("Add row...", 1, delProb);
		}
	}

	@Given("^Check or add if the cdg corp data for '([^\']*)' is present in the DTB$")
	public void cdgDataDTBCorp(String facilityId) throws Exception {
		dbConnection.openDTBConnection();

		String strQuery = "Select prob.FACILITYID from DTB_DT_FACLVLFINDEFPROBPERIOD prob, DTB_DT_FACLVLFINDEFAULTPERIOD fac where prob.FACILITYID='"
				+ facilityId
				+ "' and fac.FACILITYID=prob.FACILITYID and prob.FACILITYID=fac.FACILITYID";

		ArrayList<HashMap<String, Object>> lstResults = dbConnection
				.connectDB2DatabaseGetData(strQuery);
		if (lstResults.size() > 0) {
			String currentCaseStatus = lstResults.get(0).get("FACILITYID")
					.toString();
			if (currentCaseStatus.equals(facilityId)) {
				Assert.assertTrue("Facility id is", true);
			}
			dbConnection.closeDTBDBConnection();
		}

		else if (lstResults.size() < 1) {
			dbConnection.openDTBConnection();

			String delSubTrigger = "insert into DTB_DT_FACLVLFINDEFAULTPERIOD (PXCREATEDATETIME, FACILITYID, STATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, STARTDATETIME) \r\n"
					+ "VALUES ('09-11-17 12:35:43,620000000', 'ARTRAMCorpDefault', '1', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', 'ARTRAMCorpDefault!20140601T030000.000 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinancingDefaultPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINANCINGDEFAULTPERIOD ARTRAMCorpDefault!20140601T030000.000 GMT', '09-11-17', '01-06-14 05:00:00,000000000')";
			String delDefTrigger = "insert into DTB_DT_FACLVLFINDEFPROBPERIOD (PXCREATEDATETIME, FACILITYID, PROBATIONSTATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, MUTATIONDATETIME, DEFAULTSTARTDATETIME) \r\n"
					+ "VALUES ('09-11-17 12:35:43,682000000', 'ARTRAMCorpDefault', 'No', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', 'ARTRAMCorpDefault!20171109T113543.682 GMT!20140601T030000.000 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinDefProbationPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINDEFPROBATIONPERIOD ARTRAMCorpDefault!20171109T113543.682 GMT!20140601T030000.000 GMT', '09-11-17', '01-06-14', '01-06-14 05:00:00,000000000')";
			String delCrossTrigger = "insert into DTB_DT_FACLVLFINDEFAULTPERIOD (PXCREATEDATETIME, FACILITYID, STATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, STARTDATETIME) \r\n"
					+ "VALUES ('07-03-18 15:09:21,008000000', 'ARTRAMCorpPRobation', '1', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', 'ARTRAMCorpPRobation!20180307T130929.870 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinancingDefaultPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINANCINGDEFAULTPERIOD ARTRAMCorpPRobation!20180307T130929.870 GMT', '07-03-18', '07-03-18 14:09:29,870000000')";
			String faclFin = "insert into DTB_DT_FACLVLFINDEFPROBPERIOD (PXCREATEDATETIME, FACILITYID, PROBATIONSTATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, PROBATIONSTARTDATETIME, PROBATIONDURATION, MUTATIONDATETIME, DEFAULTSTARTDATETIME) \r\n"
					+ "VALUES ('16-05-18 14:36:11,771000000', 'ARTRAMCorpPRobation', 'Yes', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', 'ARTRAMCorpPRobation!20180516T123611.771 GMT!20180515T075804.427 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinDefProbationPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINDEFPROBATIONPERIOD ARTRAMCorpPRobation!20180516T123611.771 GMT!20180515T075804.427 GMT', '16-05-18', '16-05-18', '3', '16-05-18', '15-05-18 09:58:04,427000000')";
			String faclPer = "insert into DTB_DT_FACLVLFINDEFAULTPERIOD (PXCREATEDATETIME, FACILITYID, STATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, STARTDATETIME, ENDDATETIME)  \r\n"
					+ "VALUES ('04-04-18 15:27:44,776000000', 'ARTRAMCorpEnded', '0', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', 'ARTRAMCorpEnded!20180404T124524.103 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinancingDefaultPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINANCINGDEFAULTPERIOD ARTRAMCorpEnded!20180404T124524.103 GMT', '01-05-18', '04-04-18 14:45:24,103000000', '01-05-18')";
			String probPer = "insert into DTB_DT_FACLVLFINDEFPROBPERIOD (PXCREATEDATETIME, FACILITYID, PROBATIONSTATUSFLAG, PXCREATEOPNAME, PXCREATEOPERATOR, PXCREATESYSTEMID, PXINSNAME, PXOBJCLASS, PZINSKEY, PXCOMMITDATETIME, PROBATIONSTARTDATETIME, PROBATIONENDDATETIME, PROBATIONDURATION, MUTATIONDATETIME, DEFAULTSTARTDATETIME, REASON, CALCULATEDENDDATE) \r\n"
					+ "VALUES ('01-05-18 17:10:53,347000000', 'ARTRAMCorpEnded', 'Yes', 'Agent(RBG-Rabo-DTB-Data-DefaultTrigger.CreateDefaultTriggerCase)', 'System', 'tlw_st_dtb', 'ARTRAMCorpEnded!20180501T151053.347 GMT!20180404T124524.103 GMT', 'RBG-Rabo-DTB-Data-FacilityLevelFinDefProbationPeriod', 'RBG-RABO-DTB-DATA-FACILITYLEVELFINDEFPROBATIONPERIOD ARTRAMCorpEnded!20180501T151053.347 GMT!20180404T124524.103 GMT', '01-05-18', '01-05-18', '01-05-18', '3', '01-05-18', '04-04-18 14:45:24,103000000', 'Sucessfull', '01-05-18')";

			int delSub = dbConnection.deleteDataDTB(delSubTrigger);
			int delDef = dbConnection.deleteDataDTB(delDefTrigger);
			int delCross = dbConnection.deleteDataDTB(delCrossTrigger);
			int delFin = dbConnection.deleteDataDTB(faclFin);
			int delPer = dbConnection.deleteDataDTB(faclPer);
			int delProb = dbConnection.deleteDataDTB(probPer);

			Assert.assertEquals("Add row...", 1, delSub);
			Assert.assertEquals("Add row...", 1, delDef);
			Assert.assertEquals("Add row...", 1, delCross);
			Assert.assertEquals("Add row...", 1, delFin);
			Assert.assertEquals("Add row...", 1, delPer);
			Assert.assertEquals("Add row...", 1, delProb);

			dbConnection.closeDTBDBConnection();
		}
	}

	@When("^I hit the (REST|sendRebuttableSignal) service with '([^\']*)'$")
	public void sendRebuttableSignal(String serviceName, String arg1) {
		String serviceUrl = null;
		if (serviceName.equals("REST")) {
			serviceUrl = properties.getProperty("ram.url")
					+ properties.getProperty("commgmt.doc.service");
		}
		else
			serviceUrl = properties.getProperty("ram.url")
					+ properties.getProperty("ram.rebuttable.service");

		restService.setUrl(serviceUrl);

		String serviceResponse = restService
				.post(restService.getJsonResponse());
		RuntimeValues.serviceResponse = serviceResponse;
	}

	@Given("^Delete any 'PREVRISK' batch id from 'RAM_DT_BATCH' table$")
	public void clearRAMDTBATCHTable() {

		String strQuery = "DELETE from RAM_DT_BATCH where BATCHTYPE ='PREVRISK'";
		int result = dbConnection.deleteDataRAM(strQuery);
		Assert.assertEquals("Delete the row with batch type ='PREVRISK'", 0,
				result);
	}

	@Given("^The (sendLettersToInspire|sendprocessbatchsignal) service is hit for '([^\']*)'$")
	public void sendProcessBatchSignal(String dummy, String batchName)
			throws Exception {
		dbConnection.openRAMConnection();
		String strBatchId, currentCaseStatus = null;
		if (batchName.equals("BKR"))
			strBatchId = "SELECT (MAX(CAST(BATCHID as int)) + 1) BATCHID from RAM_DT_BATCH where BATCHTYPE='BKR'";
		else if (batchName.equals("Documents"))
			strBatchId = "SELECT (MAX(CAST(BATCHID as int)) + 1) BATCHID from COMM_MGMT_INSPIRE_BATCH";
		else
			strBatchId = "SELECT MAX(CAST(RMM_MSR_PRD_CODE as int)) RMM_MSR_PRD_CODE from PREVRISK_DT_DWH";

		ArrayList<HashMap<String, Object>> lstResults = dbConnection
				.connectDB2DatabaseGetRowSet(strBatchId);
		if (lstResults.size() > 0
				&& (batchName.equals("BKR") || batchName.equals("Documents"))) {
			currentCaseStatus = lstResults.get(0).get("BATCHID").toString();
		} else if (lstResults.size() > 0 && batchName.equals("PREVRISK")) {
			currentCaseStatus = lstResults.get(0).get("RMM_MSR_PRD_CODE")
					.toString();
		}

		String ReqName = properties.getProperty("ram.bkrfilter.username");
		String ReqPassword = properties.getProperty("ram.bkrfilter.password");
		String serviceReq = null;
		if (batchName.equals("Documents"))
			serviceReq = properties.getProperty("ram.url")
					+ properties.getProperty("commgmt.doc.batch");
		else
			serviceReq = properties.getProperty("ram.url")
					+ properties.getProperty("ram.bkrfilter.service");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("" + serviceReq + "?UserIdentifier="
				+ ReqName + "&Password=" + ReqPassword + "");

		httpPost.addHeader("batchid", currentCaseStatus);
		if (!batchName.equals("Documents"))
			httpPost.addHeader("batchtype", batchName);

		httpPost.addHeader("batchaction", "START");

		CloseableHttpResponse response = client.execute(httpPost);
		int code = response.getStatusLine().getStatusCode();
		client.close();
		Assert.assertEquals("The status of send process signal batch", "200",
				code + "");

		dbConnection.closeRAMDBConnection();
	}

	@When("^I get the response code as '(\\d+)'$")
	public void getServiceResponseCode(int arg1) {
		// Assert.assertEquals("Response code", arg1,
		// comMethod.serviceResponse);
	}

	@Given("^I wait a few seconds for all agents to be processed$")
	public void waitForArrear() throws InterruptedException {
		Thread.sleep(30000);
	}

	@Then("^Now look the PCP cases in RAM applciation$")
	public void pcpCaseCompletion() throws Throwable {
		// dummy
	}

}
