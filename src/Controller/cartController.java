package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Cart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class cartController  implements Initializable {
    @FXML
    private AnchorPane rootPanecart;

    @FXML
    private GridPane grid;

    @FXML
    private Label FinalAmount;


    private List<Cart> carts = new ArrayList<>();
    private List<Cart> getCartData() {
        List<Cart> carts = new ArrayList<>();
        try {
            File cartFile = new File("CartInf.txt");
            Scanner reader = new Scanner(cartFile);
            while (reader.hasNextLine()) {
                Cart cart = new Cart();
                cart.setName(reader.nextLine());
                cart.setPrice(reader.nextLine());
                cart.setImgSrc(reader.nextLine());
                carts.add(cart);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return carts;
    }

    public void initialize(URL location, ResourceBundle resources) {
        carts.addAll(getCartData());
        int row = 1;
        try {
            for (Cart cart : carts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/cartItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CartItemController cartItemController = fxmlLoader.getController();
                cartItemController.setData(cart);


                grid.add(anchorPane, 0, row++);





                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(15));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    void BackMarket(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Market.fxml"));;
        rootPanecart.getChildren().setAll(pane);
    }

    @FXML
    void BackMarketPay(ActionEvent event) throws IOException{
        try {
            FileWriter writer = new FileWriter("CartInf.txt", false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Market.fxml"));
        rootPanecart.getChildren().setAll(pane);
    }
}
