package com.stepdefinition;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;

import com.base.BaseUtil;
import com.base.ReusableLibrary;
import com.common.utility.RuntimeValues;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommMgmnt extends ReusableLibrary {

	public CommMgmnt(BaseUtil base) {
		super(base);
		// TODO Auto-generated constructor stub
	}

	@Given("^store the last successfull DM case in commonvariable$")
	public void lastDMcase() throws Throwable {
		// open db connection
		dbConnection.openRAMConnection();

		String strQuery = "Select GROUPID from COMM_MGMT_SPLITCOPY ORDER BY GROUPID DESC";
		ArrayList<HashMap<String, Object>> lstResults = dbConnection
				.connectDB2DatabaseGetRowSet(strQuery);

		if (lstResults.size() > 0)
			RuntimeValues.lastGroupId = lstResults.get(0).get("GROUPID")
					.toString();
		else
			RuntimeValues.lastGroupId = "No case";

		dbConnection.closeRAMDBConnection();
		Assert.assertTrue("Fetch the last DM case from table",
				lstResults.size() > 0);
	}

	@When("^Check the table 'COMM_MGMT_SPLITCOPY' with '([^\"]*)' split copy with status '([^\"]*)'$")
	public void checkCMtable(int count, String status) throws Throwable {
		Thread.sleep(25000);
		dbConnection.openRAMConnection();
		String query = "SELECT GROUPITEM, STATUSWORK, GROUPID from COMM_MGMT_SPLITCOPY ORDER BY GROUPID DESC";

		ArrayList<HashMap<String, Object>> lstResults = dbConnection
				.connectDB2DatabaseGetRowSet(query);

		if (lstResults.size() > 0) {

			String newGroupId = lstResults.get(0).get("GROUPID").toString();

			int intSplitItem = 0;
			if (!newGroupId.equals(RuntimeValues.lastGroupId)) {
				String statusWork = lstResults.get(0).get("STATUSWORK")
						.toString();
				Assert.assertTrue("Status of the DM case",
						statusWork.equals(status));

				// check the number of split copies
				for (int i = 0; i < lstResults.size(); i++) {
					String groupItem = lstResults.get(i).get("GROUPITEM")
							.toString();
					if (groupItem.contains(newGroupId))
						intSplitItem++;
				}

			} else
				Assert.assertTrue("New DM cases were not created", false);

			// check split copies count
			Assert.assertTrue(
					"Split copy count is not matching in table COMM_MGMT_SPLITCOPY",
					intSplitItem == count);

		}
		dbConnection.closeRAMDBConnection();

	}

	@When("^Run the service '([^\"]*)'$")
	public void runSendLetterInspire() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
	}

	@Then("^the status of the '(\\d+)' split copy will be '([^\"]*)'$")
	public void checkInspireStatus(int arg1, String expStatus) throws Throwable {
		dbConnection.openRAMConnection();
		String query = "SELECT GROUPITEM, STATUSWORK, GROUPID from COMM_MGMT_SPLITCOPY ORDER BY GROUPID DESC";

		ArrayList<HashMap<String, Object>> lstResults = dbConnection
				.connectDB2DatabaseGetRowSet(query);

		int intSplitItem = 0;
		if (lstResults.size() > 0) {

			String newGroupId = lstResults.get(0).get("GROUPID").toString();

			// check the number of split copies
			for (int i = 0; i < lstResults.size(); i++) {
				String orgStatusWrk = lstResults.get(i).get("STATUSWORK")
						.toString();
				String groupItem = lstResults.get(i).get("GROUPITEM")
						.toString();
				if (groupItem.contains(newGroupId)
						&& orgStatusWrk.equals(expStatus))
					intSplitItem++;
			}

		} else
			Assert.assertTrue("New DM cases were not created", false);

		// check split copies count
		Assert.assertTrue(
				"Split copy count is not matching in table COMM_MGMT_SPLITCOPY",
				intSplitItem == arg1);

		dbConnection.closeRAMDBConnection();
	}
}
