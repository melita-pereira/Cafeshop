package com.melita.cafeshop;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {
    //error logger
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    //Pulling up the login page
    @Override
    public void start(Stage stage) {
        logger.info("Cafeshop App Started!");
        try {
            FormFactory.pullForm(FormFactory.FormType.HELLO_FORM, 610, 410);
        } catch (Exception e) {
            logger.error("Failed to load the form", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}