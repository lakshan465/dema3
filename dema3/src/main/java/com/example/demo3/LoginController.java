package com.example.demo3;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.transform.Result;

/**
 * @author l3n
 */
public class LoginController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private PasswordField pwdTxt;

    @FXML
    private TextField unameTxt;

//    private Connection connection;
//    private PreparedStatement prepare;
//    private ResultSet result;

    public void close() {

        System.exit(0);
    }

    public void adminLogin() {
        try {

            if (unameTxt.getText().isEmpty() || pwdTxt.getText().isEmpty()) {
                //Forced to Fill all text box

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error massage !");
                alert.setHeaderText(null);
                alert.setContentText("Fill all text box please !");
                alert.showAndWait();
            } else {
                //after filling all text box checking weather enter data exist or not database
                String sqlForAdmin = "SELECT * from admin WHERE username = ? and password = ?";
                String sqlForStaff = "SELECT * from staff WHERE username = ? and password = ?";
                String sqlForUser = "SELECT * from user WHERE username = ? and password = ?";

                Connection conn = dbConnection.connection();

                PreparedStatement preAdmin = conn.prepareStatement(sqlForAdmin);
                PreparedStatement preStaff = conn.prepareStatement(sqlForStaff);
                PreparedStatement preUser = conn.prepareStatement(sqlForUser);

                //String uname= unameTxt.getText();
                //String pwd=getHashPwd(pwdTxt.getText());

                preAdmin.setString(1, unameTxt.getText());
                preAdmin.setString(2, getHashPwd(pwdTxt.getText()) );

                preStaff.setString(1, unameTxt.getText());
                preStaff.setString(2,getHashPwd(pwdTxt.getText()));

                preUser.setString(1, unameTxt.getText());
                preUser.setString(2, getHashPwd(pwdTxt.getText()));


                ResultSet resultAdmin = preAdmin.executeQuery();
                if (resultAdmin.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    System.out.println("You login as an Admin!");
                    alert.setContentText("You login as an Admin!");
                    alert.showAndWait();


                    //based on username admin name wil change
                    AdminController.name= unameTxt.getText();







//                    String name =unameTxt.getText();
//                    AdminController ad = new AdminController();
//                    public Label una;
//                    una.setText(name);
//                    ad.static_admin_name = una;
//                    System.out.println("next window load");
//                    resultAdmin.close();

                    //hide login window
                    loginBtn.getScene().getWindow().hide();
                    //preAdmin.close();

                    //load admin window with alert
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo3/Admin.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 950, 600);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);
                }


                ResultSet resultStaff = preStaff.executeQuery();
                if (resultStaff.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    alert.setContentText("You login as a Staff member!");
                    alert.showAndWait();
                    //resultStaff.close();
                    //preStaff.close();
                    //load staff window with alert

                    ZooKeeperController.name = unameTxt.getText();

                    //hide login window
                    loginBtn.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo3/CoWorker.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);

                }

                ResultSet resultUser = preUser.executeQuery();
                if (resultUser.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("login Successful!");
                    alert.setHeaderText(null);
                    System.out.println("You login as Customer!");
                    alert.setContentText("You login as Customer!");
                    alert.showAndWait();
                    //load user window with alert

                    CuzController.name= unameTxt.getText();//this take the username and pass that to customer class to show in the interface

                    //hide login window
                    loginBtn.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo3/Cuz.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.show();
                    stage.setResizable(false);
                }
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getHashPwd(String pwd) {
        try {
            MessageDigest md = null;

            md = MessageDigest.getInstance("SHA");

            md.update(pwd.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : rbt) {

                sb.append(String.format("%02x", b));

            }


            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void registerFormLoad() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/demo3/RegForm.fxml"));
        Scene scene=new Scene(loader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}