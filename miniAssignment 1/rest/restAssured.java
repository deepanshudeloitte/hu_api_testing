package rest;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class restAssured {
    @Test
    public void getCallTest(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type","application/json").
                when().
                get("/posts")
                .then().
                statusCode(200);
    }
    public void validateUserID(){
        Response response =  given().
                when().
                get("https://jsonplaceholder.typicode.com/posts").

                then().extract().response();
        assertThat(response.path("[39].userId"),is(equalTo(4)));
    }
    @Test
    public void test_put_call(){
        File jsondata= new File("src//test//resources//putcall.json");
        given().
                baseUri("https://reqres.in/api").
                body(jsondata).
                header("Content-Type", "application/json").
                when().
                get("/users")
                .then().statusCode(200);
    }
}