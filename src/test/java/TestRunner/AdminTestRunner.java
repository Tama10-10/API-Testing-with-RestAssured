package TestRunner;

import Controller.AdminController;
import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

public class AdminTestRunner extends Setup {
    AdminController adminController;
    @BeforeClass
    public void myAdminTestRunner()
    {
        adminController=new AdminController(prop);
    }
    @Test(priority = 1)
    public void dologinInvalidCredentials() throws ConfigurationException {

        UserModel user=new UserModel();
        user.setEmail("admin@test.com");
        user.setPassword("12345");
        Response res=adminController.dologin(user);
        System.out.println(res.asString());
        JsonPath jsonObj=res.jsonPath();
        String msg=jsonObj.get("message");
        Assert.assertEquals(msg,"Invalid email or password");
    }
   @Test(priority = 2)
    public void dologin() throws ConfigurationException {

        UserModel user=new UserModel();
        user.setEmail("admin@test.com");
        user.setPassword("admin123");
        Response res=adminController.dologin(user);
        JsonPath obj=res.jsonPath();
        String token=obj.get("token");
        Utils.setEnv("token",token);
        System.out.println(res.asString());
        int statusCode=res.getStatusCode();
       Assert.assertEquals(statusCode,200);
    }
    @Test(priority = 8)
    public void getUserList()
    {

        Response res=adminController.getUserList();
        System.out.println(res.asString());
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }
    @Test(priority = 3)
    public void doregisterWithMissingField() throws ConfigurationException {
        UserModel user=new UserModel();
        Faker faker=new Faker();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail("");
        user.setPhoneNumber("0130"+Utils.generateRandomNumber(1000000,9999999));
        user.setAddress(faker.address().cityName());
        user.setPassword("1234");
        user.setGender("Female");
        user.setTermsAccepted(true);
        Response res= adminController.RegisterUser(user);
        System.out.println(res.asString());
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,400);
    }

    @Test(priority = 4)
    public void doregister() throws ConfigurationException {
        UserModel user=new UserModel();
        Faker faker=new Faker();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPhoneNumber("0130"+Utils.generateRandomNumber(1000000,9999999));
        user.setAddress(faker.address().cityName());
        user.setPassword("1234");
        user.setGender("Female");
        user.setTermsAccepted(true);
        Response res= adminController.RegisterUser(user);
        System.out.println(res.asString());
        JsonPath obj=res.jsonPath();
        String UserID=obj.get("_id");
        Utils.setEnv("UserID",UserID);
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,201);
    }
    @Test(priority = 5)
    public void searchUserWithInvalidID() throws ConfigurationException {

        Response res=adminController.searchUser("12345uwewyr");
        System.out.println(res.asString());
        JsonPath obj=res.jsonPath();
        String msg=obj.get("message");
        Assert.assertEquals(msg,"User not found");
    }
    @Test(priority = 6)
    public void searchUser() throws ConfigurationException {
        String userID=prop.getProperty("UserID");
        Response res=adminController.searchUser(userID);
        System.out.println(res.asString());
        JsonPath obj=res.jsonPath();
        String firstName=obj.get("firstName");
        String lastName=obj.get("lastName");
        String address=obj.get("address");
        String email=obj.get("email");
        String password=obj.get("password");
        String phoneNumber=obj.get("phoneNumber");
        String gender=obj.get("gender");
        Utils.setEnv("firstname",firstName);
        Utils.setEnv("lastname",lastName);
        Utils.setEnv("address",address);
        Utils.setEnv("phoneNumber",phoneNumber);
        Utils.setEnv("email",email);
        Utils.setEnv("password",password);
        Utils.setEnv("gender",gender);
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }
    @Test(priority = 7)
    public void UpdateUser()
    {
        UserModel user=new UserModel();
        user.setFirstName(prop.getProperty("firstname"));
        user.setLastName("Alen");
        user.setEmail(prop.getProperty("email"));
        user.setPassword(prop.getProperty("password"));
        user.setGender(prop.getProperty("gender"));
        user.setAddress(prop.getProperty("address"));
        user.setPhoneNumber("01345612345");
        user.setTermsAccepted(true);
        String userID=prop.getProperty("UserID");
        user.set_id(userID);
        Response res=adminController.updateUser(user,userID);
        System.out.println(res.asString());
        int statusCode=res.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }


}
