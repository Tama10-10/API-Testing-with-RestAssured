package Controller;

import config.UserModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AdminController {
    Properties prop;
    public AdminController(Properties prop)
    {
       this.prop=prop;
        RestAssured.baseURI="https://dailyfinanceapi.roadtocareer.net";
    }
    public Response dologin(UserModel user)
    {
        Response res=given().contentType("application/json")
                     .body(user).when().post("/api/auth/login");
        return res;

    }
    public Response getUserList()
    {
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/api/user/users");
        return res;
    }
    public Response RegisterUser(UserModel user)
    {
        Response res=given().contentType("application/json")
                .body(user)
                .when().post("/api/auth/register");
        return res;
    }
    public Response searchUser(String userID)
    {
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/api/user/"+userID);
        return res;
    }
    public Response updateUser(UserModel user,String userID)
    {
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .body(user)
                .when().put("/api/user/"+userID);
        return res;
    }

}
