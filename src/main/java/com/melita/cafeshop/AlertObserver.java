package com.melita.cafeshop;

import javafx.scene.control.Alert;

public interface AlertObserver {
    void showAlert(Alert.AlertType alertType, String title, String message);
    boolean showConfirmationDialog(String title, String message);
}
