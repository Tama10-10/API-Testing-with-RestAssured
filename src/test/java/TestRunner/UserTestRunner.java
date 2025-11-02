package TestRunner;


import Controller.UserController;
import config.ItemModel;
import config.Setup;
import config.UserModel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

public class UserTestRunner extends Setup {
    UserController userController;
    @BeforeClass
    public void myUserController() {
       userController=new UserController(prop);
    }

   @Test(priority = 1)
    public void dologin() throws ConfigurationException {
        UserModel user=new UserModel();
        user.setEmail("tamadebnath2001+9198@gmail.com");
        user.setPassword("1234");
        Response res=userController.doLogin(user);
        System.out.println(res.asString());
        JsonPath jsonObj=res.jsonPath();
        String token=jsonObj.get("token");
        String userID=jsonObj.get("_id");
        Utils.setEnv("token",token);
        Utils.setEnv("UserID",userID);
       int statusCode=res.getStatusCode();
       Assert.assertEquals(statusCode,200);
    }
    @Test(priority = 2)
    public void getItemList()
    {
        Response res= userController.getItem();
        System.out.println(res.asString());
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }
    @Test(priority = 3)
    public void addItem() throws ConfigurationException {
        ItemModel item=new ItemModel();
        item.setItemName("Laptop");
        item.setAmount("50000");
        item.setQuantity(1);
        item.setMonth("February");
        item.setPurchaseDate("2025-10-30");
        item.setRemarks("note");
        Response res= userController.addItem(item);
        System.out.println(res.asString());
        JsonPath jsonObj=res.jsonPath();
        String itemID=jsonObj.get("_id");
        Utils.setEnv("itemID",itemID);
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,201);
    }

    @Test(priority = 4)
    public void updateItem()
    {
        ItemModel item=new ItemModel();
        item.setItemName("Charger");
        item.setAmount("500");
        item.setQuantity(1);
        item.setMonth("February");
        item.setRemarks("note");
        item.setPurchaseDate("2025-10-30");
        item.set_id(prop.getProperty("itemID"));
        item.setUserId(prop.getProperty("UserID"));
        Response res= userController.updateItem(item, prop.getProperty("itemID"));
        System.out.println(res.asString());
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }
    @Test(priority = 5)
    public void deleteItem()
    {
        String itemID=prop.getProperty("itemID");
        Response res= userController.deleteItem(itemID);
        System.out.println(res.asString());
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }


}
