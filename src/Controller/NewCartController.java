package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.Cart;
import model.SharedData;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NewCartController  implements Initializable {
    @FXML
    private Label AdminName;

    @FXML
    private TextField ReceiverDelivery;

    @FXML
    private TextField address;

    @FXML
    private TextField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField zipCode;


    @FXML
    private Label FinalAmount;

    @FXML
    private GridPane grid;

    @FXML
    private AnchorPane rootPanecart;

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
        String username = SharedData.getInstance().getUsername();
        if (username != null && !username.isEmpty()) {
            AdminName.setText(username); // نمایش نام کاربری
        } else {
            AdminName.setText("Guest"); // اگر نام کاربری وجود ندارد، به عنوان مهمان نمایش دهید
        }
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
                 cartItemController.setNewCartController(this);

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
    private String generatedCode; // برای ذخیره کد تصادفی ارسال‌شده

    @FXML
    void BackMarketPay(ActionEvent event) throws IOException {
    String enteredCode = password.getText().trim(); // دریافت مقدار وارد شده توسط کاربر

    if (generatedCode == null) {
        showAlert("خطا", "لطفاً ابتدا کد تأیید را دریافت کنید.");
        return;
    }

    if (!enteredCode.equals(generatedCode)) {
        showAlert("خطا", "کد وارد شده صحیح نیست. لطفاً دوباره تلاش کنید.");
        return;
    }

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    String formattedDate = today.format(formatter);

    try {
        String username = AdminName.getText(); // گرفتن نام کاربری
        String receiverName = ReceiverDelivery.getText().trim(); // نام تحویل گیرنده
        String userAddress = address.getText().trim(); // آدرس
        String postalCode = zipCode.getText().trim(); // کد پستی
        String phone = phoneNumber.getText().trim(); // شماره تماس

        if (receiverName.isEmpty() || userAddress.isEmpty() || postalCode.isEmpty() || phone.isEmpty()) {
            showAlert("خطا", "لطفاً تمام فیلدهای آدرس و اطلاعات تحویل گیرنده را پر کنید.");
            return;
        }

        File reportFile = new File("Report.txt");
        if (!reportFile.exists()) {
            reportFile.createNewFile();
        }
        File cartFile = new File(username + ".txt");
        FileReader cartReader = new FileReader(cartFile);
        File historyFile = new File(username + "-history.txt");
        // بررسی و ایجاد فایل history در صورت عدم وجود
        if (!historyFile.exists()) {
            historyFile.createNewFile();
        }
        FileWriter reportWriter = new FileWriter("Report.txt", true);
        FileWriter historyWriter = new FileWriter(historyFile, true);
        Scanner cartScanner = new Scanner(cartReader);
        // خواندن اطلاعات BookInf.txt
        File bookFile = new File("BookInf.txt");
        List<String> updatedBookLines = new ArrayList<>();
        Scanner bookScanner = new Scanner(bookFile);
        List<String> bookLines = new ArrayList<>();

        while (bookScanner.hasNextLine()) {
            bookLines.add(bookScanner.nextLine());
        }
        bookScanner.close();



        while (cartScanner.hasNextLine()) {
            String name = cartScanner.nextLine(); // نام محصول
            String priceStr = cartScanner.nextLine(); // قیمت محصول
           String image = cartScanner.nextLine(); // ادرس عکس
            int count = Integer.parseInt(cartScanner.nextLine()); // تعداد محصول

            // استخراج عدد از قیمت (حذف واحد پول و تبدیل به عدد)
            double price = Double.parseDouble(priceStr.replaceAll("[^\\d.]", "").trim());
            double priceTenPercent = price * 0.10; // ۱۰٪ قیمت محصول

            // نوشتن اطلاعات در فایل گزارش
            reportWriter.write("نام محصول: " + name + "\n");
            reportWriter.write("قیمت: " + priceStr + "\n");
            reportWriter.write("۱۰٪ قیمت: " + String.format("%.2f", priceTenPercent) + "\n");
            reportWriter.write("تعداد: " + count + "\n");
            reportWriter.write("تاریخ: " + formattedDate + "\n");
            reportWriter.write("نام خریدار: " + username + "\n");
            reportWriter.write("نام تحویل گیرنده: " + receiverName + "\n");
            reportWriter.write("آدرس: " + userAddress + "\n");
            reportWriter.write("کد پستی: " + postalCode + "\n");
            reportWriter.write("شماره تماس: " + phone + "\n");
            reportWriter.write("-------------------------------------------------\n");

            historyWriter.write(name + "\n");
            historyWriter.write(count + "\n");
            historyWriter.write(priceStr + "\n");
            historyWriter.write(formattedDate + "\n");
            historyWriter.write(image + "\n");

            // کاهش موجودی در BookInf.txt
            for (int i = 0; i < bookLines.size(); i += 9) {
                if (bookLines.get(i).equals(name)) {
                    int availableCount = Integer.parseInt(bookLines.get(i + 7));
                    availableCount -= count;
                    bookLines.set(i + 7, String.valueOf(availableCount));
                }
            }
        }


        cartScanner.close();
        cartReader.close();
        reportWriter.close();
        historyWriter.close();

        // به‌روزرسانی فایل BookInf.txt با مقادیر جدید
        FileWriter bookWriter = new FileWriter("BookInf.txt", false);
        for (String line : bookLines) {
            bookWriter.write(line + "\n");
        }
        bookWriter.close();

        // پاک کردن سبد خرید بعد از خرید
        FileWriter cartWriter = new FileWriter(username + ".txt", false);
        cartWriter.write("");
        cartWriter.close();

    } catch (IOException e) {
        e.printStackTrace();
    }

    FinalAmount.setText("0.00");

    // بازگشت به صفحه بازار
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Market.fxml"));
    AnchorPane pane = loader.load();
    pane.setPrefWidth(1315);
    pane.setPrefHeight(810);
    MarketController marketController = loader.getController();
    marketController.setId(AdminName.getText());
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

    @FXML
    void ReceivePassword(ActionEvent event) {
        String username = AdminName.getText().replace("Welcome, ", ""); // گرفتن نام کاربری از AdminName
        if (username == null || username.isEmpty()) {
            showAlert("خطا", "نام کاربری یافت نشد.");
            return;
        }

        // فایل User را باز می‌کنیم
        File userFile = new File("User.txt");
        if (!userFile.exists()) {
            showAlert("خطا", "فایل کاربران یافت نشد.");
            return;
        }

        try (Scanner scanner = new Scanner(userFile)) {
            boolean userFound = false;
            String email = null;

            // جستجو برای نام کاربری در فایل
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equals(username)) {
                    userFound = true;  // نام کاربری پیدا شد
                    // خواندن سه خط بعد از نام کاربری (آدرس ایمیل در خط سوم)
                    for (int i = 0; i < 3; i++) {
                        if (scanner.hasNextLine()) {
                            String userDetails = scanner.nextLine().trim();
                            if (i == 2) {
                                email = userDetails; // آدرس ایمیل در خط سوم است
                            }
                        }
                    }
                    break;
                }
            }

            if (!userFound) {
                showAlert("خطا", "نام کاربری پیدا نشد.");
                return;
            }

            if (email == null || email.isEmpty()) {
                showAlert("خطا", "آدرس ایمیل پیدا نشد.");
                return;
            }

            // تولید کد تصادفی
            String code = generateRandomCode();
            generatedCode = generateRandomCode();

            // ارسال ایمیل به آدرس ایمیل
            boolean emailSent = sendEmail(email, generatedCode);
            if (emailSent) {
                showAlert("موفقیت", "کد بازنشانی به ایمیل ارسال شد.");
            } else {
                showAlert("خطا", "مشکلی در ارسال ایمیل به وجود آمد.");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showAlert("خطا", "مشکلی در خواندن فایل به وجود آمد.");
        }
    }

    // تابع برای ارسال ایمیل
    private boolean sendEmail(String recipient, String code) {
        final String senderEmail = "bookstore.java.1403@gmail.com";
        final String senderPassword = "grgf ycdk suio bxbl";  // رمز عبور خود را در اینجا وارد کنید

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("کد تأیید شما");
            message.setText("کد تأیید شما: " + code);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    // تابع برای تولید کد تصادفی 6 رقمی (فقط عددی)
    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // عدد تصادفی بین 100000 تا 999999
        return String.valueOf(code);
    }




}
