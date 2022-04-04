import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
@Listeners (ExtentReport.class)
public class TestClass extends ExcelRead{
    static Logger log = Logger.getLogger(String.valueOf(TestClass.class));

    @BeforeMethod
    public  void readFromExcel() throws IOException {
        readExcel();

    }
    @Test(priority = 1)
    public void validRegistration() throws IOException {
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String payload = "{\n" +
                "  \"name\" : \""+username+"\",\n" +
                "  \"email\" : \""+email+"\",\n" +
                "  \"password\" : \""+password+"\",\n" +
                "  \"age\" : \""+age+"\"\n" +
                "}";
        request.header("Content-Type", "application/json");
        Response responsefromGeneratedToken = request.body(payload).post("/user/register");
        responsefromGeneratedToken.prettyPrint();

        String jsonString = responsefromGeneratedToken.getBody().asString();
        tokenGenerated = JsonPath.from(jsonString).get("token");
        request.header("Authorization", "Bearer" + tokenGenerated)
                .header("Content-Type", "application/json");
        int statusCode = responsefromGeneratedToken.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 201 /*expected value*/, "Correct status code returned");
        log.info("Registration Of New User Successful");
    }
    @Test(priority = 2)
    public void validLogin(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer" + tokenGenerated)
                .header("Content-Type", "application/json");
        String loginDetails = "{\n" +
                "  \"email\" : \""+email+"\",\n" +
                "  \"password\" : \""+password+"\"\n" +
                "}";
        Response responseLogin = request.body(loginDetails).post("/user/login");
        responseLogin.prettyPrint();
        int statusCode = responseLogin.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
    }


    @Test(priority = 4)
    public void validateUser(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com/user/me";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response responsevalidateUser = request.get();
        responsevalidateUser.prettyPrint();
        int statusCode = responsevalidateUser.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        log.info("Validation Successful");
    }
    @Test(priority = 5)
    public void getTask(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com/task";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response responsegetTask = request.get();
        responsegetTask.prettyPrint();
        int statusCode = responsegetTask.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        log.info("Task Successfully Generated");
    }
    @Test(priority = 6)
    public void paginationFor2(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request2 = RestAssured.given();
        request2.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response response2 = request2.get("/task?limit=2");
        response2.prettyPrint();
        int statusCode = response2.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        log.info("Pagination For 2");
    }
    @Test(priority = 7)
    public void paginationFor5(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request5 = RestAssured.given();
        request5.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response response5 = request5.get("/task?limit=5");
        response5.prettyPrint();
        int statusCode = response5.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        log.info("Pagination For 5");
    }
    @Test(priority = 8)
    public void paginationFor10(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request10 = RestAssured.given();
        request10.header("Authorization","Bearer "+ tokenGenerated)
                .header("Content-Type","application/json");
        Response response10 = request10.get("/task?limit=10");
        response10.prettyPrint();
        int statusCode = response10.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        log.info("Pagination For 10");
    }
    /*@Test (priority = 8)
    public void negativeAuthenticateTest() throws IOException {
        registrationAndLogin();
    }*/
    @Test (priority = 9)
    public void loginNotRegisterdUser()
    {
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        /*request.header("Authorization", "Bearer" + tokenGenerated)
                .header("Content-Type", "application/json");*/
        String loginDetails = "{\n" +
                "  \"email\" : \""+"manish@deloitte.com"+"\",\n" +
                "  \"password\" : \""+"manish@Abc"+"\"\n" +
                "}";
        Response responseLogin = request.body(loginDetails).post("/user/login");
        responseLogin.prettyPrint();
        String actual = responseLogin.getBody().asString();
        //System.out.println(actual);
        String expected ="Unable to login";
        Assert.assertNotEquals(actual,expected);
        int statusCode = responseLogin.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 400 /*expected value*/, "Correct status code returned");
        log.info("Not Registered User");

    }
    @Test(priority = 10)
    public void invalidTaskBody(){
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + tokenGenerated)
                .header("Content-Type", "application/json");
        String addTaskJson = "{\n" +
                "\t\"name\": \"body\"\n" +
                "}";
        Response responseaddTask = request.body(addTaskJson).post("/task");
        String actual = responseaddTask.getBody().asString();
        String expected = "Task validation failed: description: Path `description` is required.";
        responseaddTask.prettyPrint();
        Assert.assertNotEquals(actual,expected);
        int statusCode = responseaddTask.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 400 /*expected value*/, "Correct status code returned");
        log.info("Wrong request Body");

    }
    @Test(priority = 11)
    public void  registerWithAlreadyUser() throws IOException {
        RestAssured.baseURI = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        String payload = "{\n" +
                "  \"name\" : \""+username+"\",\n" +
                "  \"email\" : \""+email+"\",\n" +
                "  \"password\" : \""+password+"\",\n" +
                "  \"age\" : \""+age+"\"\n" +
                "}";
        request.header("Content-Type", "application/json");
        Response responsefromGeneratedToken = request.body(payload).post("/user/register");
        responsefromGeneratedToken.prettyPrint();
        Assert.assertEquals(responsefromGeneratedToken.statusCode(),400);

        log.info("Already Registered");
    }

}