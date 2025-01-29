package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import model.Cart;
import model.SharedData;

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
            String username = SharedData.getInstance().getUsername();
            if (username == null || username.isEmpty()) {
                showAlert("هشدار", "برای مشاهده سبد خرید اول باید وارد حساب کاربری خود شوید! ");

            }

            File cartFile = new File(username + ".txt");
            if (!cartFile.exists()) {

                showAlert("هشدار", "فایلی برای کاربر " + username + " یافت نشد.");
                return carts;
            }
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
        loadCartItems();
    }
    private void loadCartItems() {
        grid.getChildren().clear();
        carts.clear();
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
                cartItemController.setCartController(this);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
            calculateFinalAmountFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCart() {
        loadCartItems();
        calculateFinalAmountFromFile();
    }


    @FXML
    void BackMarket(MouseEvent event)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Market.fxml"));
        AnchorPane pane = loader.load();

        pane.setPrefWidth(1315);
        pane.setPrefHeight(810);

        MarketController marketController = loader.getController();
        String username1 = SharedData.getInstance().getUsername();
        marketController.setId(username1);
        rootPanecart.setPrefSize(600, 400);
        rootPanecart.getChildren().setAll(pane);
    }

    @FXML
    void BackMarketPay(ActionEvent event) throws IOException{
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String formattedDate = today.format(formatter);

            try {
                String username = SharedData.getInstance().getUsername();
                File reportFile = new File("Report.txt");
                if (!reportFile.exists()) {
                    reportFile.createNewFile();
                }
                FileReader cartReader = new FileReader(username +".txt");
                FileWriter reportWriter = new FileWriter("Report.txt", true);
                Scanner cartScanner = new Scanner(cartReader);
                List<String> cartBooks = new ArrayList<>();
                List<Integer> cartCounts = new ArrayList<>();

                while (cartScanner.hasNextLine()) {
                    String name = cartScanner.nextLine();
                    String price = cartScanner.nextLine();
                    String imgSrc = cartScanner.nextLine();
                    int count = Integer.parseInt(cartScanner.nextLine());

                    cartBooks.add(name);
                    cartCounts.add(count);


                }
                cartScanner.close();
                cartReader.close();


                File bookFile = new File("BookInf.txt");
                List<String> updatedBookLines = new ArrayList<>();
                Scanner bookScanner = new Scanner(bookFile);

                while (bookScanner.hasNextLine()) {
                    String bookName = bookScanner.nextLine();
                    List<String> bookDetails = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        bookDetails.add(bookScanner.nextLine());
                    }

                    int bookCount = Integer.parseInt(bookScanner.nextLine());
                    String subject = bookScanner.nextLine();



                    if (cartBooks.contains(bookName)) {
                        int index = cartBooks.indexOf(bookName);
                        int cartCount = cartCounts.get(index);

                        if (bookCount >= cartCount) {
                            bookCount -= cartCount;

                            reportWriter.write(bookName + "\n");
                            reportWriter.write(bookDetails.get(0) + "\n");
                            reportWriter.write(bookDetails.get(1) + "\n");
                            reportWriter.write(cartCount + "\n");
                            reportWriter.write(formattedDate + "\n");
                        } else {
                            System.out.println("تعداد کافی برای کتاب " + bookName + " وجود ندارد.");
                            showAlert("هشدار", "تعداد کافی برای کتاب " + " وجود ندارد.");
                        }
                    }

                    updatedBookLines.add(bookName);
                    updatedBookLines.addAll(bookDetails);
                    updatedBookLines.add(String.valueOf(bookCount));
                    updatedBookLines.add(subject);
                }
                bookScanner.close();
                reportWriter.close();

                FileWriter bookWriter = new FileWriter("BookInf.txt", false);
                for (String line : updatedBookLines) {
                    bookWriter.write(line + "\n");
                }
                bookWriter.close();
                FileWriter cartWriter = new FileWriter( username +".txt", false);
                cartWriter.write("");
                cartWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        FinalAmount.setText("0.00");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Market.fxml"));
        AnchorPane pane = loader.load();
        pane.setPrefWidth(1315);
        pane.setPrefHeight(810);
        MarketController marketController = loader.getController();
        String username1 = SharedData.getInstance().getUsername();
        marketController.setId(username1);
        rootPanecart.setPrefSize(600, 400);
        rootPanecart.getChildren().setAll(pane);


    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void calculateFinalAmountFromFile() {
        double totalAmount = 0.0;
        try {

            String username = SharedData.getInstance().getUsername();
            File cartFile = new File(username + ".txt");

            if (!cartFile.exists()) {
                showAlert("خطا", "فایل سبد خرید یافت نشد!");
                return;
            }

            Scanner reader = new Scanner(cartFile);
            int lineCount = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                lineCount++;

                if (lineCount % 4 == 2) {
                    String numericString = line.replaceAll("[^\\d.]", "").trim();
                    try {
                        double price = Double.parseDouble(numericString);
                        totalAmount += price;
                    } catch (NumberFormatException e) {
                        System.err.println("فرمت قیمت نامعتبر است: " + line);
                    }
                }
            }
            reader.close();

            if (totalAmount < 200) {
                totalAmount += 50;
            }

            FinalAmount.setText(String.format("%.2f", totalAmount));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showAlert("خطا", "مشکلی در خواندن فایل به وجود آمد.");
        }
    }







}
