package com.bookit.step_definitions;

import com.bookit.utilities.DBUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.*;

public class DBStepDefs {
    Map<String, Object> dbMap;
    public static String DBName;
    public static String DBRole;
    public static String DBTeam;
    public static String DBBatch;
    public static String DBCampus;

    @When("User sends a query to DataBase with {string}")
    public void user_sends_a_query_to_DataBase_with(String email) {
        String query = "select firstname, lastname, role, name,location,batch_number\n" +
                "from users u join (select t.id,name,batch_number,location from\n" +
                "team t join campus c on t.campus_id=c.id) r\n" +
                "on u.team_id=r.id where u.email='" + email + "';";

        dbMap = DBUtils.getRowMap(query);
        System.out.println(dbMap);
    }

    @Then("User gets DataBase information")
    public void user_gets_DataBase_information() {
        DBName = dbMap.get("firstname") + " " + dbMap.get("lastname");
        DBCampus = (String) dbMap.get("location");
        DBBatch = "#" + dbMap.get("batch_number");
        DBRole = (String) dbMap.get("role");
        DBTeam = (String) dbMap.get("name");

    }

    @Then("All information from environments must match")
    public void allInformationFromEnvironmentsMustMatch() {

        System.out.println(UIStepDefs.UIname);
        System.out.println(APIStepDefs.apiName);
        System.out.println(DBName);

        Assert.assertTrue(UIStepDefs.UIname.equals(DBName) && APIStepDefs.apiName.equals(DBName));
        Assert.assertTrue(UIStepDefs.UIbatch.equals(DBBatch) && APIStepDefs.apiBatch.equals(DBBatch));
        Assert.assertTrue(UIStepDefs.UIteam.equals(DBTeam) && APIStepDefs.apiTeam.equals(DBTeam));
        Assert.assertTrue(UIStepDefs.UIcampus.equals(DBCampus) && APIStepDefs.apiCampus.equals(DBCampus));
        Assert.assertTrue(UIStepDefs.UIrole.equals(DBRole) && APIStepDefs.apiRole.equals(DBRole));

    }
}

