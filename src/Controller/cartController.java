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
        loadCartItems();
    }
    private void loadCartItems() {
        grid.getChildren().clear(); // پاک کردن آیتم‌های قبلی از GridPane
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
                cartItemController.setCartController(this); // ارجاع به این کنترلر

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCart() {
        loadCartItems(); // بارگذاری مجدد آیتم‌ها
    }


    @FXML
    void BackMarket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Market.fxml"));
        AnchorPane pane = loader.load();

        MarketController marketController = loader.getController();
        String username1 = SharedData.getInstance().getUsername(); // دریافت نام کاربری از SharedData
        marketController.setId(username1); // تنظیم مجدد نام کاربری در صفحه Market

        rootPanecart.getChildren().setAll(pane);
    }

    @FXML
    void BackMarketPay(ActionEvent event) throws IOException{
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String formattedDate = today.format(formatter);

            try {
                // 1. گزارش پرداخت را ایجاد کنید
                File reportFile = new File("Report.txt");
                if (!reportFile.exists()) {
                    reportFile.createNewFile();
                }
                FileReader cartReader = new FileReader("CartInf.txt");
                FileWriter reportWriter = new FileWriter("Report.txt", true);
                Scanner cartScanner = new Scanner(cartReader);

                // 2. اطلاعات CartInf را بخوانید و به گزارش اضافه کنید
                List<String> cartBooks = new ArrayList<>();
                List<Integer> cartCounts = new ArrayList<>();

                while (cartScanner.hasNextLine()) {
                    String name = cartScanner.nextLine(); // نام کتاب
                    String price = cartScanner.nextLine(); // قیمت کتاب
                    String imgSrc = cartScanner.nextLine(); // تصویر کتاب
                    int count = Integer.parseInt(cartScanner.nextLine()); // تعداد کتاب

                    // ذخیره اطلاعات برای استفاده در به‌روزرسانی BookInf
                    cartBooks.add(name);
                    cartCounts.add(count);

                    // نوشتن اطلاعات در فایل گزارش
                    reportWriter.write(name + "\n");
                    reportWriter.write(price + "\n");
                    reportWriter.write(imgSrc + "\n");
                    reportWriter.write(count + "\n");
                    reportWriter.write(formattedDate + "\n");
                }
                cartScanner.close();
                cartReader.close();
                reportWriter.close();

                // 3. اطلاعات BookInf را بخوانید و به‌روزرسانی کنید
                File bookFile = new File("BookInf.txt");
                List<String> updatedBookLines = new ArrayList<>();
                Scanner bookScanner = new Scanner(bookFile);

                while (bookScanner.hasNextLine()) {
                    String bookName = bookScanner.nextLine(); // نام کتاب
                    List<String> bookDetails = new ArrayList<>();
                    for (int i = 0; i < 6; i++) { // خواندن جزئیات کتاب
                        bookDetails.add(bookScanner.nextLine());
                    }

                    int bookCount = Integer.parseInt(bookScanner.nextLine()); // تعداد موجودی کتاب
                    String subject = bookScanner.nextLine(); // سطر 9: موضوع کتاب


                    // اگر کتاب در CartInf موجود است، تعداد را کاهش دهید
                    if (cartBooks.contains(bookName)) {
                        int index = cartBooks.indexOf(bookName);
                        int cartCount = cartCounts.get(index);

                        if (bookCount >= cartCount) {
                            bookCount -= cartCount;
                        } else {
                            System.out.println("تعداد کافی برای کتاب " + bookName + " وجود ندارد.");
                        }
                    }

                    // ذخیره اطلاعات بدون تغییر، به جز تعداد
                    updatedBookLines.add(bookName);
                    updatedBookLines.addAll(bookDetails);
                    updatedBookLines.add(String.valueOf(bookCount)); // سطر 8: تعداد جدید
                    updatedBookLines.add(subject); // سطر 9: موضوع
                }
                bookScanner.close();

                // 4. فایل BookInf را به‌روزرسانی کنید
                FileWriter bookWriter = new FileWriter("BookInf.txt", false);
                for (String line : updatedBookLines) {
                    bookWriter.write(line + "\n");
                }
                bookWriter.close();

                // 5. فایل CartInf را تخلیه کنید
                FileWriter cartWriter = new FileWriter("CartInf.txt", false);
                cartWriter.write("");
                cartWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        // بازگشت به صفحه Market
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Market.fxml"));
        AnchorPane pane = loader.load();
        MarketController marketController = loader.getController();
        String username1 = SharedData.getInstance().getUsername(); // دریافت نام کاربری از SharedData
        marketController.setId(username1); // تنظیم مجدد نام کاربری در صفحه Market
        rootPanecart.getChildren().setAll(pane);


    }
}
