package com.melita.cafeshop;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class CardProduct implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    private productData prodData;
    private Image image;
    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;
    private SpinnerValueFactory<Integer> spin;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private int qty;
    private double totalP;
    private double pr;

    //Initializing db -- Singleton design pattern
    database db = database.getInstance();
    public CardProduct(){
    }

    //Definitions
    public void setData(productData prodData) {
        this.prodData = prodData;

        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProductId();
        prod_name.setText(prodData.getProductName());
        prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 190, 94, false, true);
        prod_imageView.setImage(image);
        pr = prodData.getPrice();

    }

    //for the spinner to add no. of items
    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    //adding item to cart
    public void addBtn() {

        MainForm mForm = new MainForm();
        mForm.customerID();

        qty = prod_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM products WHERE prod_id = '"
                + prodID + "'";

        connect = db.getConnection();

        try {
            int checkStck = checkStockAvailability();

            if(checkStck == 0){
                updateProductStock(0);
            }

            check = checkProductAvailability(checkAvailable);

            if (!check.equals("Available") || qty == 0) {
                showAlert(ERROR,"Error Message", "Something went wrong!");
            } else {
                processOrder(mForm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //retrieving amount of stock
    private int checkStockAvailability() throws SQLException{
        String checkStock = "SELECT stock FROM products WHERE prod_id = '" + prodID + "'";
        prepare = connect.prepareStatement(checkStock);
        result = prepare.executeQuery();

        if(result.next()){
            return result.getInt("stock");
        }
        return 0;
    }

    //updating the stock when add command is issued
    private void updateProductStock(int stock) throws SQLException {
        String status = "";
        if (stock == 0){
            status = "Unavailable";
        } else {
            status = "Available";
        }
        String updateStock = "UPDATE products SET prod_name = '"
                + prod_name.getText() + "', type = '"
                + type + "', stock = " + stock + ", price = " + pr
                + ", status = '" + status + "', image = '"
                + prod_image + "', date = '"
                + prod_date + "' WHERE prod_id = '"
                + prodID + "'";
        prepare = connect.prepareStatement(updateStock);
        prepare.executeUpdate();
    }

    //retrieving the status of product
    private String checkProductAvailability(String checkAvailable) throws SQLException {
        prepare = connect.prepareStatement(checkAvailable);
        result = prepare.executeQuery();

        if(result.next()){
            return result.getString("status");
        }
        return "";
    }

    //processing the order if valid
    private void processOrder(MainForm mForm) throws SQLException{
        int checkStck = checkStockAvailability();
        if (checkStck < qty) {
            showAlert(ERROR,"Error Message", "Invalid! This product is out of stock!");
        }else {
            prod_image = prod_image.replace("\\", "\\\\");
            insertOrderToDatabase();
            updateProductStock(checkStck - qty);
            showAlert(INFORMATION,"Information Message", "Successfully added!");
            mForm.menuGetTotal();
        }
    }

    //inserting the order data into customers db
    private void insertOrderToDatabase() throws SQLException{
        String insertData = "INSERT INTO customers (customer_id, prod_id, prod_name, type, quantity, price, date, em_username)" +
                "VALUES(?,?,?,?,?,?,?,?)";
        prepare = connect.prepareStatement(insertData);
        prepare.setString(1, String.valueOf(data.cID));
        prepare.setString(2, prodID);
        prepare.setString(3, prod_name.getText());
        prepare.setString(4, type);
        prepare.setString(5, String.valueOf(qty));
        totalP = (qty*pr);
        prepare.setString(6, String.valueOf(totalP));
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        prepare.setString(7, String.valueOf(sqlDate));
        prepare.setString(8, data.username);
        prepare.executeUpdate();
    }

    //alerts or pop-ups
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        AlertHandler.showAlert(alertType, title, message);
    }

    public boolean showConfirmationDialog(String title, String message) {
        return AlertHandler.showConfirmationDialog(title, message);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();
    }

}