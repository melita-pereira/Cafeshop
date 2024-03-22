package com.melita.cafeshop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//factory method design pattern to improve code readability and reduce redundancy
public class FormFactory {
    public enum FormType {
        HELLO_FORM,
        MAIN_FORM
    }
    public static void pullForm(FormType formType, int min_width, int min_height) {
        try {
            String formPath = switch (formType){
                case HELLO_FORM -> "hello-view.fxml";
                case MAIN_FORM -> "main-form.fxml";
            };
            Parent root = FXMLLoader.load(FormFactory.class.getResource(formPath));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Cafeshop Management System");
            stage.setMinWidth(min_width);
            stage.setMinHeight(min_height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
