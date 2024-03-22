package com.melita.cafeshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    //Pulling up the login page
    @Override
    public void start(Stage stage) throws Exception {
        FormFactory.pullForm(FormFactory.FormType.HELLO_FORM, 610, 410);
    }

    public static void main(String[] args) {
        launch(args);
    }
}