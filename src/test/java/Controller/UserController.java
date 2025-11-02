package Controller;

import config.ItemModel;
import config.UserModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop)
    {
        this.prop=prop;
        RestAssured.baseURI="https://dailyfinanceapi.roadtocareer.net";
    }
    public Response doLogin(UserModel user)
    {
        Response res=given().contentType("application/json")
                .body(user).when().post("/api/auth/login");
        return res;
    }
    public Response getItem()
    {
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/api/costs");
        return res;
    }
    public Response addItem(ItemModel item)
    {
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .body(item)
                .when().post("/api/costs");
        return res;
    }
    public Response updateItem(ItemModel item,String ItemID)
    {
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .body(item)
                .when().put("/api/costs/"+ItemID);
        return res;
    }
    public Response deleteItem(String ItemID)
    {
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().delete("/api/costs/"+ItemID);
        return res;
    }
}
