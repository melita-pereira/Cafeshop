package com.melita.cafeshop;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

//manages the base page which is the login page, and handles register page, forgot password and set new password page
public class HelloController implements Initializable{

    @FXML
    private Label alreadyHave;

    @FXML
    private Button fp_backBtn;

    @FXML
    private TextField fp_email;

    @FXML
    private AnchorPane fp_emailForm;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private TextField fp_username;

    @FXML
    private Label notRegis;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private AnchorPane np_passwordForm;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_SignIn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private PasswordField su_confirmPass;

    @FXML
    private TextField su_email;

    @FXML
    private PasswordField su_password;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;


    private PreparedStatement prepare;
    private ResultSet result;
    private Connection connect;
    private AlertHandler alertHandler;

    database db = database.getInstance();
    public HelloController(){
        this.alertHandler = new AlertHandler();
    }


    //to facilitate logins
    public void loginBtn() {

        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showAlert(ERROR, "Error Message", "Please fill all blank fields!");
        } else {
            String selectData = "SELECT username, password FROM users WHERE username = ? and password = ?";

            connect = db.getConnection();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();
                // IF SUCCESSFUL LOGIN, THEN PROCEED TO MAIN FORM
                if (result.next()) {
                    // TO GET THE USERNAME THAT USER USED
                    data.username = si_username.getText();

                    showAlert(INFORMATION,"Information Message", "Successfully logged in!");

                    // LINK MAIN FORM
                    FormFactory.pullForm(FormFactory.FormType.MAIN_FORM, 1100, 600);

                    si_loginBtn.getScene().getWindow().hide();

                } else { // IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                    showAlert(ERROR,"Error Message", "Incorrect Username/Password");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //to facilitate registration to the system
    public void regBtn() {

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_confirmPass.getText().isEmpty() || su_email.getText().isEmpty()) {
            showAlert(ERROR, "Error Message", "Please fill all blank fields!");
        } else if (!su_password.getText().equals(su_confirmPass.getText())) {
            showAlert(ERROR,"Error Message", "Passwords don't match! Please make sure to enter the same password for both!");
        } else if (!su_email.getText().matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}")){
            showAlert(ERROR,"Error Message", "Please enter a valid email address!");
        } else {

            String regData = "INSERT INTO users (username, email, password, date) "
                    + "VALUES(?,?,?,?)";
            connect = db.getConnection();

            try {
                // CHECK IF THE USERNAME IS ALREADY RECORDED
                String checkUsername = "SELECT username FROM users WHERE username = '"
                        + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    showAlert(ERROR, "Error Message",su_username.getText() + " is already taken!");
                } else if (su_password.getText().length() < 8) {
                    showAlert(ERROR, "Error Message", "Invalid password, minimum of 8 characters are required!");
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_email.getText());
                    prepare.setString(3, su_password.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(4, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    showAlert(INFORMATION, "Information Message", "Account successfully registered!");

                    su_username.setText("");
                    su_password.setText("");
                    su_email.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_SignIn.setVisible(false);
                        side_CreateBtn.setVisible(true);
                    });

                    slider.play();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //to switch to forgot password page
    public void switchForgotPass() {
        si_loginForm.setVisible(false);
        fp_emailForm.setVisible(true);

    }

    //to proceed to setting new password
    public void proceedBtn() {

        if (fp_email.getText().isEmpty() || fp_username.getText().isEmpty()) {
            showAlert(ERROR, "Error Message", "Please fill all blank fields!");

        } else {

            String selectData = "SELECT email, username FROM users WHERE email = ? AND username = ?";
            connect = db.getConnection();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_email.getText());
                prepare.setString(2, fp_username.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                np_passwordForm.setVisible(true);
                fp_emailForm.setVisible(false);
                } else {
                    showAlert(ERROR, "Error Message", "Incorrect Information");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //to change password
    public void changePassBtn() {

        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            showAlert(ERROR, "Error Message", "Please fill all blank fields!");
        } else {

            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
                String getDate = "SELECT date FROM users WHERE username = '"
                + fp_username.getText() + "'";

                connect = db.getConnection();

                try {

                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date = "";
                    if (result.next()) {
                        date = result.getString("date");
                    }

                    String updatePass = "UPDATE users SET password = '"
                    + np_newPassword.getText() + "', date = '" + date + "' WHERE username = '"
                    + fp_username.getText() + "'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    showAlert(INFORMATION, "Information Message", "Password successfully changed!");

                    si_loginForm.setVisible(true);
                    np_passwordForm.setVisible(false);

                    // TO CLEAR FIELDS
                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_username.setText("");
                    fp_email.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert(ERROR, "Error Message", "Passwords don't match!");
            }
        }
    }

    //upon pressing back button when on forgot password page
    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_emailForm.setVisible(false);
    }

    //upon pressing back button when on set new password page
    public void backToEmailForm(){
        fp_emailForm.setVisible(true);
        np_passwordForm.setVisible(false);
    }

    //switching between different pages depending on the buttons pressed
    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_SignIn.setVisible(true);
                side_CreateBtn.setVisible(false);
                notRegis.setVisible(false);

                si_loginForm.setVisible(true);
                alreadyHave.setVisible(true);
                fp_emailForm.setVisible(false);
                np_passwordForm.setVisible(false);

            });

            slider.play();
        } else if (event.getSource() == side_SignIn) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                side_SignIn.setVisible(false);
                side_CreateBtn.setVisible(true);
                notRegis.setVisible(true);

                si_loginForm.setVisible(true);
                alreadyHave.setVisible(false);
                fp_emailForm.setVisible(false);
                np_passwordForm.setVisible(false);
            });

            slider.play();
        }

    }

    //alerts or pop-ups
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        alertHandler.showAlert(alertType, title, message);
    }

    public boolean showConfirmationDialog(String title, String message) {
        return alertHandler.showConfirmationDialog(title, message);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    // TODO
    }

}