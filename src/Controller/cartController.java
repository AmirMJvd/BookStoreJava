package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import model.Cart;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                cart.setCount(reader.nextLine());
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
        int column = 0;
        int row = 1;
        try {
            for (Cart cart : carts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/CartItem1.fxml"));
                HBox anchorPane = fxmlLoader.load();
                CartItemController cartItemController = fxmlLoader.getController();
                cartItemController.setData(cart);
                if (column == 2) {
                    column = 0;
                    row++;
                }


                grid.add(anchorPane,column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
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
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = today.format(formatter);

        try {
            File fileName = new File("Report.txt");
            if (!fileName.exists()) {
                fileName.createNewFile();
            }
            FileReader myReader = new FileReader("CartInf.txt");
            FileWriter myWriter = new FileWriter("Report.txt", true);
            Scanner scanner = new Scanner(myReader);
            while (scanner.hasNextLine()) {
                for (int i =0 ; i <4;i++){
                    myWriter.write(scanner.nextLine());
                    myWriter.write("\n");
                }
                myWriter.write(formattedDate);
                myWriter.write("\n");
            }
            myReader.close();
            myWriter.close();
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
