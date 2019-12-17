package com.stepdefinition;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.base.BaseUtil;
import com.base.ReusableLibrary;
import com.common.utility.RuntimeValues;
import com.page.objects.ObjDashBoard;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.codeborne.selenide.Selenide.$;

public class Dashboard extends ReusableLibrary {

	public Dashboard(BaseUtil base) {
		super(base);
		// TODO Auto-generated constructor stub
	}

	@Given("^Click on the 'Dossier-ID' filter and search with '([^\']*)' case id for the (facility||relation) '([^\']*)'$")
	public void searchInFilter(String caseName, String dummy, String facilityId)
			throws Exception {
		Assert.assertTrue("Click the case Id filter",
				rf.clickButtonLink(ObjDashBoard.caseIdFilter));
		Thread.sleep(2000);
		String caseId =rf.getcaseIDusingFacId(RuntimeValues.hashMap,
				facilityId);
		Assert.assertTrue("Search the case Id" + caseId
				+ " in filter search box",
				rf.typeInEditBox(ObjDashBoard.searchText, caseId));
		Assert.assertTrue("Click Apply",
				rf.clickButtonLink(ObjDashBoard.btnApply));
	}

	@When("^Click the (facility||relation) '([^\']*)' PCP case from dashboard$")
	public void clickPCPCase(String dummy, String facilityId) throws Exception {
		// check whether element page get loaded or not
		Thread.sleep(3000);
		Assert.assertTrue("Dashboard is loaded with line item",
				wait.waitForElementVisible(20, ObjDashBoard.dashboardTable));

		String caseId = rf.getcaseIDusingFacId(RuntimeValues.hashMap,
				facilityId);
		Assert.assertTrue("Click the case " + caseId,
				rf.clickButtonLink(By.linkText(caseId)));

	}

	@When("^I open a random case in PAT$")
	public void openRandomCase(String dummy, String facilityId) throws Exception {
		// check whether element page get loaded or not
		Thread.sleep(3000);
		Assert.assertTrue("Dashboard is loaded with line item",
				wait.waitForElementVisible(20, ObjDashBoard.dashboardTable));

		Assert.assertTrue("Click the rebuttable trigger filter",rf.clickButtonLink(ObjDashBoard.rebutTrigFilter));

		String rebutTrigger = "FRAUDE";

		Assert.assertTrue("Filter to fraud cases",
				rf.typeInEditBox(ObjDashBoard.searchText, rebutTrigger));

		Assert.assertTrue("Click Apply",
				rf.clickButtonLink(ObjDashBoard.btnApply));

		Assert.assertTrue("Click the case " + ObjDashBoard.casePat,
				rf.clickButtonLink(ObjDashBoard.casePat));

	}

	@Then("^I validate that the case is opened correctly$")
	public void siebelButVisible(String amount ) throws Exception {
		$(By.xpath("//a[@class='Button_Link_Blue_Small']")).waitUntil(Condition.visible,5000);
	}
	
	@Then("^I enter the signaltype as '([^\']*)'$")
	public void enterSignalType(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);
		Assert.assertTrue("Enter signaltype" + amount+ " in surname box", rf.typeInEditBox(ObjDashBoard.signalType, amount));
	}
	
	@Then("^I enter the date signal as '([^\']*)'$")
	public void enterDateSignal(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);
		Assert.assertTrue("Enter date signal" + amount+ " in surname box", rf.typeInEditBox(ObjDashBoard.dateSignal, amount));
	}
	
	@Then("^I enter the surname as '([^\']*)'$")
	public void enterClaimAmount(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);
		Assert.assertTrue("Enter surname" + amount+ " in surname box", rf.typeInEditBox(ObjDashBoard.achterNaam, amount));
	}
	
	@Then("^I enter the loannumber as '([^\']*)'$")
	public void enterLoanNumber(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);
		Assert.assertTrue("Enter loannumber" + amount+ " in loannumber box", rf.typeInEditBox(ObjDashBoard.loanNumber, amount));
	}
	
	@Then("^I enter the housenumber as '([^\']*)'$")
	public void enterHouseNum(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);
		Assert.assertTrue("Enter housenumber" + amount+ " in housenumber box", rf.typeInEditBox(ObjDashBoard.houseNumber, amount));
	}
	
	@Then("^I enter the postal code as '([^\']*)'$")
	public void enterPostalCode(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);
		Assert.assertTrue("Enter postalcode" + amount+ " in postalcode box", rf.typeInEditBox(ObjDashBoard.postalCode, amount));
	}
	
	@Then("^I enter the birthday as '([^\']*)'$")
	public void enterBithday(String amount ) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		Thread.sleep(1000);
		Assert.assertTrue("Enter birthday" + amount+ " in birthday box", rf.typeInEditBox(ObjDashBoard.birthDay, amount));
	}

	@When("^'([^\']*)' case opened in new tab$")
	public void caseOpenNewTab(String caseName) {
		String caseXpath = ObjDashBoard.newTab.replaceAll("<signalcase>",
				caseName);
		Assert.assertTrue(caseName + " is get opened in new tab",
				wait.waitForElementVisible(10, By.xpath(caseXpath)));
	}
	
	@When("^I select the case in the results$")
	public void selectCase() throws InterruptedException {
		Thread.sleep(2000);
		Assert.assertTrue("select case",
				rf.clickButtonLink(ObjDashBoard.selectDossier));
		//rf.clickButtonLink(ObjDashBoard.selectDossier);
	}

	@Then("^I see the status of the PCP case as '([^\']*)'$")
	public void checkCaseStatus(String status) throws Exception {
		Thread.sleep(3000);
		String strStatus = rf.getText(ObjDashBoard.caseStatus);
		Assert.assertEquals("case status", status, strStatus);
	}
	@Then("^I see the rebuttable trigger is '([^\']*)'$")
	public void checkRebTrig(String status) throws Exception {
		Thread.sleep(3000);
		String strStatus = rf.getText(ObjDashBoard.caseStatus);
		Assert.assertEquals("case status", status, strStatus);
	}

	@Then("^I should see the following phases$")
	public void checkAllPhases(DataTable phases) {

		List<List<String>> lstPhases = phases.raw();
		int i = 0;

		for (List<String> eachRow : lstPhases) {

			if (rf.isElementDisplayed(By.linkText(eachRow.get(0))))
				i++;
		}
		Assert.assertEquals("Check all phases", i, lstPhases.size());
	}

	@Then("^I should see the following 360 degreee view$")
	public void checkThreeSixtyView(DataTable threeSixtyView) throws Throwable {
		List<List<String>> lstPhases = threeSixtyView.raw();
		int i = 0;

		for (List<String> eachRow : lstPhases) {

			String xpathThreeSixty = ObjDashBoard.groupItems.replaceAll(
					"<groupItem>", eachRow.get(0));
			if (rf.isElementDisplayed(By.xpath(xpathThreeSixty)))
				i++;
		}
		Assert.assertEquals("Check all 360 view tab present", i,
				lstPhases.size());

	}

	@Then("^I click the button 'Verzenden'$")
	public void clickVerzenden() {
		//Assert.assertTrue("Click the button verzenden",
			//	rf.clickButtonLink(ObjDashBoard.btnVerzenden));
		$(By.cssSelector("a.RaboOrange")).waitUntil(Condition.visible,10000).click();
	}
	
	@Then("^I click the button 'Nieuw Dossier'$")
	public void clickNewDos() throws InterruptedException{
		Assert.assertTrue("Click the button nieuw dossier",
				rf.clickButtonLink(ObjDashBoard.newDosBtn));
		Thread.sleep(1000);
	}
	
	@Then("^I click the button 'TeamBoard'$")
	public void clickTeamBoard() throws InterruptedException{
		Assert.assertTrue("Click the button teamboard",
				rf.clickButtonLink(ObjDashBoard.teamBtn));
		Thread.sleep(1000);
	}
	
	@Then("^I click the button 'Wissen'$")
	public void clickWissen() throws InterruptedException{
		Assert.assertTrue("Click the button wissen",
				rf.clickButtonLink(ObjDashBoard.wissenBtn));
		Thread.sleep(1000);
	}
	
	@Then("^I click the button 'Nieuwe Case'$")
	public void newCaseBtn() throws InterruptedException{
		Assert.assertTrue("Click the button nieuw case",
				rf.clickButtonLink(ObjDashBoard.newCaseBtn));
		Thread.sleep(1000);
	}
	
	@Then("^I click the second button 'Nieuwe Case'$")
	public void newCaseBtnSubmit() throws InterruptedException{
		Thread.sleep(1000);
		Assert.assertTrue("Click the button nieuwe case",
				rf.clickButtonLink(ObjDashBoard.newCaseBtnSub));
		Thread.sleep(1000);
	}
	
	@Then("^I click the button 'Zoeken'$")
	public void clickSearch() throws InterruptedException{
		Assert.assertTrue("Click the button zoeken",
				rf.clickButtonLink(ObjDashBoard.searchBtn));
		Thread.sleep(1000);
	}
	
	@Then("^I stop the process$")
	public void clickActie() throws InterruptedException {
		Assert.assertTrue("Click the case Id filter",
				rf.clickButtonLink(ObjDashBoard.actieButton));
		rf.clickButtonLink(ObjDashBoard.actieButton);
		Thread.sleep(1000);
		Assert.assertTrue("Click the case Id filter",
				rf.clickButtonLink(ObjDashBoard.stopBtn));
	}
	
	@Then("^I can see the message as '([^\']*)'$")
	public void confirmationMessage(String msg) throws InterruptedException{
		Thread.sleep(1000);
		String actualMsg = rf.getText(ObjDashBoard.inforMsg);
		Assert.assertEquals("Confirmation message", msg, actualMsg);
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
	}
	
	@Then("^I can see the eventdate as '([^\']*)'$")
	public void eventDate(String msg) throws InterruptedException{
		String actualMsg = rf.getText(ObjDashBoard.eventDate);
		Assert.assertEquals("Event date", msg, actualMsg);
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
	}
	
	@Then("^I can see the submit message as '([^\']*)'$")
	public void subMessage(String msg) throws InterruptedException{
		Thread.sleep(1000);
		String actualMsg = rf.getText(ObjDashBoard.submMsg);
		Assert.assertEquals("Confirmation message", msg, actualMsg);
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
	}
	
	@Then("^I can see the error message as '([^\']*)'$")
	public void errorMessage(String msg) throws InterruptedException{
		String actualMsg = rf.getText(ObjDashBoard.errorMsg);
		Assert.assertEquals("Confirmation message", msg, actualMsg);
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
	}

	@When("^I switch to opened tab$")
	public void switchToTab() {
		BaseUtil.Driver.switchTo().frame(
				BaseUtil.Driver.findElement(ObjDashBoard.gadgetFrame));
	}

	@Then("^I closed (my|any existing) case tab$")
	public void closeTab(String arg1) throws Exception {

		BaseUtil.Driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		Thread.sleep(20000);
		List<WebElement> lstCloseTabs = driver
				.findElements(ObjDashBoard.closeTabs);
		//since the close button is not view port, the java script will do the trick

		for(int i=0; i<lstCloseTabs.size(); i++){
			JavascriptExecutor js =(JavascriptExecutor) BaseUtil.Driver;
			js.executeScript("document.getElementById(\"close\").click();");
			Thread.sleep(2000);
		}
		/*for (WebElement ele : lstCloseTabs)
			ele.click();*/
		BaseUtil.Driver.navigate().refresh();
		Thread.sleep(2000);
		BaseUtil.Driver.manage().timeouts()
		.implicitlyWait(BaseUtil.timeOut, TimeUnit.SECONDS);
		/*lstCloseTabs = driver.findElements(ObjDashBoard.closeTabs);
		Assert.assertEquals("Close all existing opened tabs", 0,
				lstCloseTabs.size());*/
	}

	@Then("^I checked my work basket does not contain this case for '([^\']*)'$")
	public void clearBasket(String facility) throws Exception {
		rf.clickButtonLink(ObjDashBoard.refreshIcon);

		rf.WaitForSpinner(driver);

		//Thread.sleep(8000);

		// again search the filter

		Assert.assertTrue("Click the case Id filter",
				rf.clickButtonLink(ObjDashBoard.caseIdFilter));
		Thread.sleep(2000);
		String caseId = rf.getcaseIDusingFacId(RuntimeValues.hashMap, facility);
		Assert.assertTrue("Search the case Id" + caseId
				+ " in filter search box",
				rf.typeInEditBox(ObjDashBoard.searchText, caseId));
		Assert.assertTrue("Click Apply",
				rf.clickButtonLink(ObjDashBoard.btnApply));
		Thread.sleep(3000);
		Assert.assertTrue("Case should not present in workbasket", rf.isElementDisplayed(ObjDashBoard.noItems));
		/*int sizeRows = BaseUtil.Driver
				.findElements(ObjDashBoard.dashboardTable).size();
		Assert.assertEquals("Case should not present in basket", 0, sizeRows);*/
	}

	@Then("^I select the SAM manage radio button as '([^\']*)'$")
	public void samManaged(String samManaged) throws InterruptedException {
		Thread.sleep(1000);
		if (samManaged.equals("Ja"))
			rf.clickButtonLink(ObjDashBoard.samManagedTrue);
		else
			rf.clickButtonLink(ObjDashBoard.samManagedFalse);
	}
	
	@Then("^I select the hypotheek radio button as '([^\']*)'$")
	public void hypotheekBedrag(String hypBedrag) throws InterruptedException {
		Thread.sleep(1000);
		if (hypBedrag.equals("Ja")) {
			Selenide.switchTo().frame("PegaGadget0Ifr");
			$("label[for$='OVERIGBNPQ1A1']").click();
		}

		else
			Selenide.switchTo().frame("PegaGadget0Ifr");
			$("label[for$='OVERIGBNPQ1A2']").click();
	}

	@Then("^I see the reason assessment as '([^\']*)'$")
	public void assessMentReason(String reason) {
		String text = rf.getText(ObjDashBoard.assessMentReason);
		Assert.assertEquals("Assessment reason...", reason, text.trim());
	
	}
	
	@Then("^I see the reason assessment conservatoir as '([^\']*)'$")
	public void assessMentReasonConservatoir(String reason) {
		String text = rf.getText(ObjDashBoard.assessMentReasonCons);
		Assert.assertEquals("Assessment reason...", reason, text.trim());
	
	}

	@Then("^I click all mandatory radio button in Processen section for '([^\']*)' cases$")
	public void clickAllMandatoryButtons(String caseName) throws Exception {

		if (caseName.equals("BKR")) {
			Assert.assertTrue("Click 1st mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory1));
			Thread.sleep(2000);
			Assert.assertTrue("Click 2nd mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory2));
			Thread.sleep(2000);
			Assert.assertTrue("Click 3rd mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory3));
			Thread.sleep(2000);
			Assert.assertTrue("Click 4th mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory4));
			Thread.sleep(2000);
		} else if (caseName.equals("DPD30")) {
			Assert.assertTrue("Click 1st mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory1));
			Thread.sleep(2000);
			Assert.assertTrue("Click 2nd mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory5));
			Thread.sleep(2000);
			Assert.assertTrue("Click 3rd mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory6));
			Thread.sleep(2000);
		}else if (caseName.equals("BESLAG")) {
			Assert.assertTrue("Click 1st mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory1));
			Thread.sleep(2000);
			Assert.assertTrue("Click 2nd mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory2));
			Thread.sleep(2000);
			Assert.assertTrue("Click 3rd mandatory radio button",
					rf.clickButtonLink(ObjDashBoard.mandatory3));
			Thread.sleep(2000);
		}
	}

	@Then("^I click the 'Beoordeling' as '([^\']*)'$")
	public void clickRating(String rating) {

		if (rating.equals("Weerleggen"))
			rf.clickButtonLink(ObjDashBoard.refute);
		else if (rating.equals("Niet weerleggen"))
			rf.clickButtonLink(ObjDashBoard.Notrefute);
		else
			rf.clickButtonLink(ObjDashBoard.doNotJudge);
	}

	@Then("^I select the 'Reden'as '([^\']*)'$")
	public void selectReason(String reason) throws InterruptedException {

		Assert.assertTrue(rf.selectByText(ObjDashBoard.slctRsonCode, reason));
		Thread.sleep(1000);

	}
	
	@Then("^I fill in the reason '([^\']*)'$")
	public void enterReason(String reason) throws InterruptedException {

		rf.typeInEditBox(ObjDashBoard.enterReason, reason);
		Thread.sleep(2000);
		rf.clickButtonLink(ObjDashBoard.verzendBtn);
		Thread.sleep(1000);
	}	
	
	@Then("^I select the 'Team'as '([^\']*)'$")
	public void selectTeam(String reason) throws InterruptedException {

		Assert.assertTrue(rf.selectByText(ObjDashBoard.slctTeam, reason));
		Thread.sleep(2000);

	}
	
	@Then("^I wait several seconds$")
	public void waitSeconds() throws InterruptedException {
		Thread.sleep(9000);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Then("^I see the following options in dropdown 'Reden'$")
	public void checkAllOprionDropDowm(DataTable dataTable) {
		List<List<String>> lstText = dataTable.raw();
		List<WebElement> lstWebEle = rf.getAllValuesInList(ObjDashBoard.slctRsonCode);
		
		ArrayList<String> lstValues=new ArrayList<String>();
		
		for(WebElement ele:lstWebEle)
			lstValues.add(ele.getText());
		
		for (List<String> lstString : lstText) 
		{
					
			if(lstValues.contains(lstString.get(0))){
				
			} else
				Assert.assertFalse(false);
		}
		

	}
	
	@Then("^I switch the team dropdown$")
	public void switchTeam(DataTable dataTable) {
		List<List<String>> lstText = dataTable.raw();
		List<WebElement> lstWebEle = rf.getAllValuesInList(ObjDashBoard.slctTeam);
		
		ArrayList<String> lstValues=new ArrayList<String>();
		
		for(WebElement ele:lstWebEle)
			lstValues.add(ele.getText());
		
		for (List<String> lstString : lstText) 
		{
					
			if(lstValues.contains(lstString.get(0))){
				
			} else
				Assert.assertFalse(false);
		}
		

	}

	@Then("^I should not see any entry present in RAM workbasket$")
	public void noCaseInWorkBasket() throws Exception{
		Thread.sleep(2000);
		BaseUtil.Driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int sizeRows = BaseUtil.Driver
				.findElements(ObjDashBoard.dashboardTable).size();
		Assert.assertEquals("Case should not present in basket", 0, sizeRows);
		BaseUtil.Driver.manage().timeouts().implicitlyWait(BaseUtil.timeOut, TimeUnit.SECONDS);
	}
	


	@Then("^I see the status as '([^\']*)' for the facility '([^\']*)'$")
	public void dpdCaseStatus(String status, String facility) throws Exception {

		String caseId = rf.getcaseIDusingFacId(RuntimeValues.hashMap, facility);

		String query = "Select pyID, PYSTATUSWORK, owner_rar.pr_read_from_stream('.DPDStatus.NumberOfDaysPastDue', pzInsKey, pzPVStream)  DPD_Days , owner_rar.pr_read_from_stream('.Assessment.ResultReasonDesc', pzInsKey, pzPVStream) Result_Reason"
				+ " from RAM_WORK_CREDITPROBLEM where pyid in ('"
				+ caseId
				+ "')";

		dbConnection.openRAMConnection();

		List<HashMap<String, Object>> lstRslts = dbConnection
				.connectDB2DatabaseGetRowSet(query);

		if (lstRslts.size() > 0) {
			String actualStatus = lstRslts.get(0).get("PYSTATUSWORK")
					.toString();
			
		RuntimeValues.assesMentReason=lstRslts.get(0).get("RESULT_REASON")
				.toString();
			Assert.assertEquals("Assessment status", status, actualStatus);

		} else
			Assert.assertFalse("No entry found in the blob", false);
		dbConnection.closeRAMDBConnection();

	}
	
	
	@Then("^I checked the assessment reason as '([^\']*)' in blob for the facility '([^\']*)'$")
	public void dpdAssReason(String assReason, String facilityId) throws Exception {
		String caseId = rf.getcaseIDusingFacId(RuntimeValues.hashMap,
		facilityId);		

		String query = "Select pyID, PYSTATUSWORK, owner_rar.pr_read_from_stream('.DPDStatus.NumberOfDaysPastDue', pzInsKey, pzPVStream)  DPD_Days , owner_rar.pr_read_from_stream('.Assessment.ResultReasonDesc', pzInsKey, pzPVStream) Result_Reason"
				+ " from RAM_WORK_CREDITPROBLEM where pyid in ('"
				+ caseId
				+ "')";

		dbConnection.openRAMConnection();

		List<HashMap<String, Object>> lstRslts = dbConnection
				.connectDB2DatabaseGetRowSet(query);
		

		dbConnection.closeRAMDBConnection();
		Assert.assertEquals("Assessment reason", assReason,lstRslts.get(0).get("RESULT_REASON")
				.toString());
	}
	
	@Given("^Check in RAM_WORK_CREDITPROBLEM table in DB creditproblem for the facility '([^\']*)'$")
	public void dbProblem(String loanNumber, DataTable rows) throws Exception {
	   
		
		String caseId=rf.getcaseIDusingFacId(RuntimeValues.hashMap,
				loanNumber);	
		//String query="Select pyID, PYSTATUSWORK, pr_read_from_stream('PotentialCreditProblemType', pzInsKey, pzPVStream) AS  PCP , pr_read_from_stream('pxCurrentStageLabel', pzInsKey, pzPVStream)AS  current_stage, pr_read_from_stream('Assessment.ResultReasonDesc.ResultReasonCode',"
			//	+ " pzInsKey, pzPVStream)AS  REASON_CODE from RAM_WORK_CREDITPROBLEM where pyid in ('"+caseId+"')";
		String query="Select pyID, PYSTATUSWORK, owner_rar.pr_read_from_stream('.PotentialCreditProblemType', pzInsKey, pzPVStream) AS PCP ,\r\n" + 
				"owner_rar.pr_read_from_stream('.Assessment.ResultReasonDesc', pzInsKey, pzPVStream)AS REASON,\r\n" + 
				"owner_rar.pr_read_from_stream('.pxCurrentStageLabel', pzInsKey, pzPVStream)AS current_stage,\r\n" + 
				"owner_rar.pr_read_from_stream('..Assessment.ResultReasonCode',pzInsKey, pzPVStream)AS REASON_CODE from RAM_WORK_CREDITPROBLEM where pyid in ('"+caseId+"')";

		
		
		dbConnection.openRAMConnection();
		List<HashMap<String, Object>> lstRslts = dbConnection
				.connectDB2DatabaseGetRowSet(query);
		
		//traverse the list
		
		List<List<String>> lstPhases = rows.raw();
		int i=0;
		for (List<String> eachRow : lstPhases) {
			
			String eachColumnHeder=lstRslts.get(0).get(eachRow.get(0)).toString();
			if(eachColumnHeder.equals(eachRow.get(1))){
				i++;
			}
			
		}
		
		Assert.assertEquals("Data is not populated in DB", lstPhases.size(), i);
		dbConnection.closeRAMDBConnection();
		
	}
	
	@Given("^Check in RAM_WORK_CREDITPROBLEM table in DB for the loan '([^\']*)'$")
	public void db(String loanNumber, DataTable rows) throws Exception {
	   
		
		String caseId=rf.getcaseIDusingFacId(RuntimeValues.hashMap,
				loanNumber);	
		String query="Select pyID, PYSTATUSWORK, owner_rar.pr_read_from_stream('.PotentialCreditProblemType', pzInsKey, pzPVStream) AS PCP ,\r\n" + 
				"owner_rar.pr_read_from_stream('.Assessment.ResultReasonDesc', pzInsKey, pzPVStream)AS REASON,\r\n" + 
				"owner_rar.pr_read_from_stream('.pxCurrentStageLabel', pzInsKey, pzPVStream)AS current_stage,\r\n" + 
				"owner_rar.pr_read_from_stream('..Assessment.ResultReasonCode',pzInsKey, pzPVStream)AS REASON_CODE from RAM_WORK_CREDITPROBLEM where pyid in ('"+caseId+"')";
		
		
		dbConnection.openRAMConnection();
		List<HashMap<String, Object>> lstRslts = dbConnection
				.connectDB2DatabaseGetRowSet(query);
		
		//traverse the list
		
		List<List<String>> lstPhases = rows.raw();
		int i=0;
		for (List<String> eachRow : lstPhases) {
			
			String eachColumnHeder=lstRslts.get(0).get(eachRow.get(0)).toString();
			if(eachColumnHeder.equals(eachRow.get(1))){
				i++;
			}
			
		}
		
		Assert.assertEquals("Data is not populated in DB", lstPhases.size(), i);
		dbConnection.closeRAMDBConnection();
		
	}
	
	@Then("^I click on the '([^\']*)' tab$")
	public void clickProductTab(String tabInfo) {
		if(tabInfo.equals("BKR"))
			rf.clickButtonLink(ObjDashBoard.bkrTab);
		else if(tabInfo.equals("Onderpand"))
			rf.clickButtonLink(ObjDashBoard.collateralTab);
		else
			rf.clickButtonLink(ObjDashBoard.productEn);
	}

	@Then("^I can see the '([^\']*)' in product tab$")
	public void validateLoanNumber(String loanNumber)  {
		ArrayList<String> lstLoanNum=new ArrayList<>(Arrays.asList(loanNumber.split(",")));
		List<WebElement> loanNumbers=Driver.findElements(ObjDashBoard.lstLoanNo);
		for(WebElement ele:loanNumbers){
			String strLoanNum=ele.getText();
			
			if(lstLoanNum.contains(strLoanNum)){
				
			}else
				Assert.assertTrue("Loan number "+strLoanNum+" is not present", false);
		}

	}
	
	@Then("^I can see the '([^\']*)' in collateral tab$")
	public void validateCollateralObjectTypeDesc(String collObjTypeDesc) {
		List<WebElement> collObjTypes = Driver.findElements(ObjDashBoard.collObjTypeDesc);
		for (WebElement ele:collObjTypes) {
			String collObjType = ele.getText();
			if (collObjTypeDesc.equals(collObjType)) {
			} else {
				Assert.assertTrue("Collateral Object Type description " + collObjTypeDesc + " is not present", false);
			}
		}
	}

	@Then("^I can see the BKR information$")
	public void checkBKRInfo() {

	Assert.assertTrue("Checking the BKR Information section", rf.isElementDisplayed(ObjDashBoard.bkrContRel));
	
	}
	
	@Then("^I can see the confirmation$")
	public void checkConfirmation() {

	Assert.assertTrue("Hartelijk dank! De volgende stap in dit dossiers is correct omgeleid.", rf.isElementDisplayed(ObjDashBoard.overlijdenContRel));
	
	}
	
	@Then("^I can see the correct date plus 120 days$")
	public void checkDate120() throws ParseException {

		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "dd-MMM-yyyy";
		
		String newDateString;

		LocalDate dayToday = LocalDate.now();
		LocalDate endDay = dayToday.plusDays(120);
		String forParse = endDay.toString();
		Locale localeNL = new Locale("nl", "NL" );
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, localeNL);
		Date d = sdf.parse(forParse);
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);

		String strStatus = rf.getText(ObjDashBoard.primDate);
		
		Assert.assertEquals("current date", newDateString, strStatus);
	
	}
	
	@Then("^I can see the correct date plus 14 days$")
	public void checkDate14() throws ParseException, InterruptedException {
			 //String strDateFormat = "dd/MM/yyyy"; //Date format is Specified
			 //SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat); //Date format string is passed as an argument to the Date format object
		TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
		TimeZone.setDefault(timeZone);

		Thread.sleep(2000);
		
		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "dd-MM-yyyy";
		
		String newDateString;

		LocalDate dayToday = LocalDate.now();
		LocalDate endDay = dayToday.plusDays(14);
		String forParse = endDay.toString();

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(forParse);
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);

		String strStatus = rf.getText(ObjDashBoard.secDate);
		
		Assert.assertEquals("current date", newDateString, strStatus);
	
	}

}
