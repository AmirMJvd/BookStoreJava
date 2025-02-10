package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import main.Main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;




import static main.Main.CURRENCY;

public class infoController{

    @FXML
    private Label BookNamelab;

    @FXML
    private Label BookSizelab;

    @FXML
    private Label Coverlab;

    @FXML
    private Label Nasherlab;

    @FXML
    private Label PageNumlab;

    @FXML
    private Label Pricelab;

    @FXML
    private Label Publishedlab;

    @FXML
    private Label Translatorlab;

    @FXML
    private Label Writerlab;

    @FXML
    private Label publicationlab;

    @FXML
    private HBox combobox;

    @FXML
    private Button addCart;

    @FXML
    private Label countLab;

    @FXML
    private Label CategoryLab;

    @FXML
    private ImageView bookImg;

    @FXML
    private Label countLabel;

    public static String bookName; // متغیر استاتیک برای ذخیره نام کتاب

    @FXML
    public void initialize() {
        // مقدار نام کتاب را در لیبل قرار دهید
        BookNamelab.setText(bookName);
        // خواندن اطلاعات از فایل و تنظیم در لیبل‌ها
        loadBookInfo();
        updateVisibility();
    }

    @FXML
    void addCart(ActionEvent event) {
        // نامرئی کردن دکمه و مرئی کردن combobox
        addCart.setVisible(false);
        combobox.setVisible(true);
    }
    private void loadBookInfo() {
        String filePath = "BookInfo.txt"; // مسیر فایل اطلاعات کتاب‌ها
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(bookName)) { // پیدا کردن نام کتاب
//                    Pricelab.setText( reader.readLine()+CURRENCY); // قیمت کتاب
//                    String imagePath = reader.readLine(); // آدرس تصویر
//                    reader.readLine(); // رد کردن خط چهارم
//                    Writerlab.setText(reader.readLine()); // نویسنده
//                    Translatorlab.setText(reader.readLine()); // مترجم
//                    Nasherlab.setText(reader.readLine()); // ناشر
//                    countLab.setText(reader.readLine()); // تعداد موجودی
//                    CategoryLab.setText(reader.readLine()); // دسته‌بندی
//
//                    // تنظیم تصویر کتاب
//                    Image image = new Image(getClass().getResourceAsStream(imagePath));
//                    bookImg.setImage(image);
                    Writerlab.setText(reader.readLine());
                    Translatorlab.setText(reader.readLine());
                    Nasherlab.setText(reader.readLine());
                    Publishedlab.setText(reader.readLine());
                    publicationlab.setText(reader.readLine());
                    BookSizelab.setText(reader.readLine());
                    Coverlab.setText(reader.readLine());
                    PageNumlab.setText(reader.readLine());
                    CategoryLab.setText(reader.readLine());
                    String imagePath = reader.readLine(); // آدرس تصویر
                    reader.readLine();
                    countLab.setText(reader.readLine());
                    Pricelab.setText(reader.readLine());
                    reader.readLine();



                    // تنظیم تصویر کتاب
                    Image image = new Image(getClass().getResourceAsStream(imagePath));
                    bookImg.setImage(image);

                    break; // خروج از حلقه بعد از یافتن کتاب
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // متد بررسی مقدار countLabel و تنظیم نمایش دکمه و کمبو‌باکس
    private void updateVisibility() {
        int currentCount = Integer.parseInt(countLabel.getText());

        if (currentCount == 0) {
            addCart.setVisible(true);
            combobox.setVisible(false);
        } else {
            addCart.setVisible(false);
            combobox.setVisible(true);
        }
    }

    @FXML
    void decreaseBtn(ActionEvent event) {
        int currentNumber = Integer.parseInt(countLabel.getText());

        if (currentNumber > 0) { // جلوگیری از مقدار منفی
            currentNumber--;
            countLabel.setText(String.valueOf(currentNumber));
            updateVisibility(); // بررسی وضعیت نمایش دکمه‌ها
        }
    }

    @FXML
    void increaseBtn(ActionEvent event) {
        int currentNumber = Integer.parseInt(countLabel.getText());
        currentNumber++;
        countLabel.setText(String.valueOf(currentNumber));
        updateVisibility(); // بررسی وضعیت نمایش دکمه‌ها
    }
    @FXML
    void downlodpdf(ActionEvent event) {
        try {
            // دریافت نام کتاب از BookNamelab
            String bookName = BookNamelab.getText().trim();

            if (bookName.isEmpty()) {
                showAlert("خطا", "نام کتاب را وارد کنید!");
                return;
            }

            // فایل BookInfo.txt را باز می‌کنیم
            BufferedReader reader = new BufferedReader(new FileReader("BookInfo.txt"));
            String line;
            boolean bookFound = false;

            while ((line = reader.readLine()) != null) {
                // چک می‌کنیم که آیا نام کتاب در سطر اول وجود دارد
                if (line.equalsIgnoreCase(bookName)) {
                    // اگر نام کتاب پیدا شد، 11 خط بعدی را می‌خوانیم تا به خط 12 برسیم
                    for (int i = 0; i < 10; i++) {
                        reader.readLine();  // خواندن خطوط 2 تا 11 که مورد نیاز نیست
                    }

                    // خط 12 که آدرس PDF است را می‌خوانیم
                    String pdfAddress = reader.readLine();  // آدرس PDF در خط 12

                    if (pdfAddress != null && !pdfAddress.isEmpty()) {
                        // تبدیل آدرس نسبی به آدرس کامل با استفاده از پوشه pdfs
                        Path pdfDirectory = Paths.get("src", "pdfs").toAbsolutePath(); // پوشه pdfs در مسیر src
                        Path pdfFilePath = pdfDirectory.resolve(pdfAddress); // ترکیب پوشه با آدرس نسبی PDF

                        File pdfFile = pdfFilePath.toFile(); // تبدیل به شیء File

                        if (pdfFile.exists()) {
                            // باز کردن PDF با استفاده از Desktop API
                            Desktop.getDesktop().open(pdfFile);  // باز کردن فایل PDF
                        } else {
                            showAlert("خطا", "فایل PDF یافت نشد: " + pdfFile.getAbsolutePath());
                        }
                    } else {
                        showAlert("خطا", "آدرس PDF برای کتاب مورد نظر موجود نیست!");
                    }

                    bookFound = true;
                    break;  // اگر کتاب پیدا شد، از حلقه خارج می‌شویم
                }
            }

            if (!bookFound) {
                showAlert("خطا", "خلاصه کتاب مورد نظر پیدا نشد!");
            }

            reader.close();
        } catch (IOException e) {
            showAlert("خطا", "خطا در خواندن فایل!");
            e.printStackTrace();
        }


    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

