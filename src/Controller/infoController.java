package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.Main;
import model.SharedData;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;



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

    @FXML
    private Button decreaseBtn;

    @FXML
    private Button increaseBtn;

    public static String bookName; // متغیر استاتیک برای ذخیره نام کتاب

    @FXML
    public void initialize() {
        // مقدار نام کتاب را در لیبل قرار دهید
        BookNamelab.setText(bookName);
        // خواندن اطلاعات از فایل و تنظیم در لیبل‌ها
        loadBookInfo();

    }

    @FXML
    void addCart(ActionEvent event) throws IOException {
        String currentUsername = SharedData.getInstance().getUsername();

        if (countLab.getText().compareTo("0") == 0) {
            showAlert("خطا", "متاسفانه کتاب موجود نیست!");
            return;
        }

        // بررسی مقدار lblid قبل از استفاده از آن
        if (currentUsername == null || currentUsername.isEmpty()) {
            showAlert("خطا", "ابتدا باید ورود بفرمایید!");
            return;
        }

        String id = currentUsername;
        File fileName = new File(id + ".txt");

        List<String> fileContent = new ArrayList<>();
        boolean bookExists = false;
        int bookIndex = -1;

        if (fileName.exists()) {
            try (Scanner scanner = new Scanner(fileName)) {
                while (scanner.hasNextLine()) {
                    fileContent.add(scanner.nextLine());
                }
            }

            for (int i = 0; i < fileContent.size(); i += 4) {
                if (fileContent.get(i).equals(BookNamelab.getText())) {
                    bookExists = true;
                    bookIndex = i;
                    break;
                }
            }
        }

        String priceText = Pricelab.getText().replaceAll("[^\\d.]", ""); // فقط اعداد و نقطه
        double labelPrice = 0.0;

        try {
            labelPrice = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert("خطا", "فرمت قیمت نامعتبر است!");
            return;
        }

        int countToAdd = Integer.parseInt(countLabel.getText());
        int maxCount = Integer.parseInt(countLab.getText());

        // محاسبه قیمت نهایی (ضرب قیمت در تعداد)
        double totalPrice = labelPrice * countToAdd;

        if (bookExists) {
            double currentPrice = Double.parseDouble(fileContent.get(bookIndex + 1).replaceAll("[^\\d.]", ""));
            double newPrice = currentPrice + totalPrice; // بروزرسانی قیمت با ضرب قیمت در تعداد

            int currentCount = Integer.parseInt(fileContent.get(bookIndex + 3));
            if (currentCount + countToAdd <= maxCount) {
                int newCount = currentCount + countToAdd;
                fileContent.set(bookIndex + 1, String.valueOf(Main.CURRENCY + newPrice)); // قیمت جدید
                fileContent.set(bookIndex + 3, String.valueOf(newCount)); // تعداد جدید

                try (FileWriter writer = new FileWriter(fileName, false)) {
                    for (String line : fileContent) {
                        writer.write(line + "\n");
                    }
                }
                showAlert1("عملیات موفقیت‌آمیز", "قیمت و تعداد محصول به‌روزرسانی شد!");
            } else {
                showAlert("خطا", "تعداد مورد نظر بیشتر از موجودی است!");
            }
        } else {
            if (countToAdd <= maxCount) {
                try (FileWriter myWriter = new FileWriter(fileName, true)) {
                    myWriter.write(BookNamelab.getText());
                    myWriter.write("\n");
                    myWriter.write(labelPrice * countToAdd + Main.CURRENCY); // ضرب قیمت در تعداد و ذخیره در فایل
                    myWriter.write("\n");

                    String filePath = "BookInfo.txt"; // مسیر فایل اطلاعات کتاب‌ها
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.equals(BookNamelab.getText())) { // پیدا کردن نام کتاب
                                // خواندن اطلاعات دیگر کتاب
                                for (int i = 0; i < 9; i++) {
                                    reader.readLine(); // رد کردن خطوط غیر ضروری
                                }
                                String imagePath = reader.readLine(); // آدرس تصویر
                                myWriter.write(imagePath); // نوشتن آدرس تصویر در فایل
                                myWriter.write("\n");
                                break; // خروج از حلقه بعد از یافتن کتاب
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    myWriter.write(String.valueOf(countToAdd)); // نوشتن تعداد کتاب در فایل
                    myWriter.write("\n");
                }
                showAlert1("عملیات موفقیت‌آمیز", "محصول به سبد خرید شما اضافه شد!");
            } else {
                showAlert("خطا", "تعداد مورد نظر بیشتر از موجودی است!");
            }
        }
    }




    private void loadBookInfo() {
        String filePath = "BookInfo.txt"; // مسیر فایل اطلاعات کتاب‌ها
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(bookName)) { // پیدا کردن نام کتاب

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

    @FXML
    void decreaseBtn(ActionEvent event) {
        int currentNumber = Integer.parseInt(countLabel.getText());

        if (currentNumber > 0) { // جلوگیری از مقدار منفی
            currentNumber--;
            countLabel.setText(String.valueOf(currentNumber));
        }
    }

    @FXML
    void increaseBtn(ActionEvent event) {
        int currentNumber = Integer.parseInt(countLabel.getText());
        currentNumber++;
        countLabel.setText(String.valueOf(currentNumber));
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

    @FXML
    void Save(MouseEvent event) throws IOException {
        String currentUsername = SharedData.getInstance().getUsername();
        if (currentUsername != null && !currentUsername.isEmpty()) {
            File favoridFile = new File(currentUsername + "-favorit.txt");
            if (!favoridFile.exists()) {
                favoridFile.createNewFile();
            }
            String filePath = "BookInfo.txt"; // مسیر فایل اطلاعات کتاب‌ها
            FileWriter fw = new FileWriter(favoridFile, true);
            fw.write(BookNamelab.getText() + "\n");
            fw.write(Pricelab.getText() + "\n");
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(bookName)) { // پیدا کردن نام کتاب

                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        String imagePath = reader.readLine(); // آدرس تصویر
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();
                        reader.readLine();

                        fw.write(imagePath + "\n");
                        showAlert1("تبریک", "محصول به لیست علاقه مندی شما اضافه شد");
                        break; // خروج از حلقه بعد از یافتن کتاب

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            fw.close();
        }else {
            showAlert("خطا","اپتدا باید وارد بشوید");
        }
    }

    public void setBookName(String bookName) {
        BookNamelab.setText(bookName); // تنظیم مقدار نام کتاب در لیبل
        loadBookInfo1();
    }

    private void loadBookInfo1() {
        String filePath = "BookInfo.txt"; // مسیر فایل اطلاعات کتاب‌ها
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals( BookNamelab.getText())) { // پیدا کردن نام کتاب

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

    private void showAlert1(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

