package rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
public class Assignment3 {

    RequestSpecification requestSpecification1;
    ResponseSpecification responseSpecification1;
    RequestSpecification requestSpecification2;
    ResponseSpecification responseSpecification2;
    @BeforeClass
    public void setup(){
        RequestSpecBuilder requestSpecBuilder1 = new RequestSpecBuilder();
        requestSpecBuilder1.setBaseUri("https://jsonplaceholder.typicode.com").
                addHeader("Content_Type","application/json");
        requestSpecification1 = RestAssured.with().spec(requestSpecBuilder1.build());
        ResponseSpecBuilder responseSpecBuilder1 = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).expectStatusCode(200);
        responseSpecification1 = responseSpecBuilder1.build();
        RequestSpecBuilder requestSpecBuilder2 = new RequestSpecBuilder();
        requestSpecBuilder2.setBaseUri("https://reqres.in/api").
                addHeader("Content_Type","application/json");
        requestSpecification2 = RestAssured.with().spec(requestSpecBuilder2.build());
        ResponseSpecBuilder responseSpecBuilder2 = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).expectStatusCode(200);
        responseSpecification2 = responseSpecBuilder2.build();
    }

    @Test
    public void getRequest(){
        Response response =
                given().
                        spec(requestSpecification1).
                        when().
                        get("/posts").
                        then().spec(responseSpecification1).extract().response();
        JSONArray arr = new JSONArray(response.asString());
        for (int i = 0; i < arr.length(); i++) {
            if(arr.getJSONObject(i).getInt("id")==40){
                assert(arr.getJSONObject(i).getInt("userId")==4);
            }
            assertThat(arr.getJSONObject(i).getString("title"),true);
        }
    }

    @Test
    public void putRequest(){
        File jsonData = new File("src//main//resources//putcall.json");
        given().
                spec(requestSpecification2).
                body(jsonData).
                header("Content-Type","application/json").
                when().
                put("/users").
                then().
                spec(responseSpecification2).
                body("name",equalTo("Arun")).
                body("job",equalTo("Manager"));
    }
}