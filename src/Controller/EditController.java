package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.Main.CURRENCY;

public class EditController {

    @FXML
    private Label BookNamelab;

    @FXML
    private TextField BookSizelab;

    @FXML
    private TextField CategoryLab;

    @FXML
    private TextField Coverlab;

    @FXML
    private TextField Nasherlab;

    @FXML
    private TextField PageNumlab;

    @FXML
    private TextField Pricelab;

    @FXML
    private TextField Publishedlab;

    @FXML
    private TextField Translatorlab;

    @FXML
    private TextField Writerlab;

    @FXML
    private Button addCart;

    @FXML
    private ImageView bookImg;

    @FXML
    private HBox combobox;

    @FXML
    private TextField countLab;

    @FXML
    private Label countLabel;

    @FXML
    private Button decreaseBtn;

    @FXML
    private Button increaseBtn;

    @FXML
    private TextField publicationlab;

    @FXML
    private TextField ImageAd;

    @FXML
    private TextField pdfAd;

    // مسیر فایل اطلاعات کتاب‌ها
    private final String filePath = "BookInfo.txt";
    private final String bookInfPath = "BookInf.txt";

    public static String bookName;

    @FXML
    public void initialize() {
        // مقدار نام کتاب را در لیبل قرار دهید
        BookNamelab.setText(bookName);

        // متد برای بارگذاری اطلاعات کتاب از فایل
        loadBookInfo();
    }
    private void loadBookInfo() {
        String filePath = "BookInfo.txt"; // مسیر فایل اطلاعات کتاب‌ها

        try {
            FileReader fr = new FileReader(filePath);
            Scanner sc = new Scanner(fr);
            String line;
            while (sc.hasNextLine()){
                line = sc.nextLine();
                if (line.trim().equals(bookName)) {
                    Writerlab.setText(sc.nextLine());
                    Translatorlab.setText(sc.nextLine());
                    Nasherlab.setText(sc.nextLine());
                    Publishedlab.setText(sc.nextLine());
                    publicationlab.setText(sc.nextLine());
                    BookSizelab.setText(sc.nextLine());
                    Coverlab.setText(sc.nextLine());
                    PageNumlab.setText(sc.nextLine());
                    CategoryLab.setText(sc.nextLine());
                    String imagePath =sc.nextLine();
                    ImageAd.setText(imagePath);
                    if (imagePath != null && !imagePath.isEmpty()) {
                        bookImg.setImage(new Image(imagePath)); // تنظیم عکس کتاب
                    }
                    pdfAd.setText(sc.nextLine());
                    String count = sc.nextLine();
                    countLab.setText(count);
                    countLabel.setText(count);
                    Pricelab.setText(sc.nextLine());
                    sc.nextLine();
                    break;
                }
            }
        }
         catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addCart(ActionEvent event) {
        String bookName = BookNamelab.getText();
        List<String> lines = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            // بررسی اینکه آیا نام کتاب قبلاً موجود است
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(bookName)) {
                    // کتاب پیدا شد، بروزرسانی اطلاعات
                    lines.set(i + 1, Writerlab.getText());
                    lines.set(i + 2, Translatorlab.getText());
                    lines.set(i + 3, Nasherlab.getText());
                    lines.set(i + 4, Publishedlab.getText());
                    lines.set(i + 5, publicationlab.getText());
                    lines.set(i + 6, BookSizelab.getText());
                    lines.set(i + 7, Coverlab.getText());
                    lines.set(i + 8, PageNumlab.getText());
                    lines.set(i + 9, CategoryLab.getText());
                    lines.set(i + 10, ImageAd.getText()); // فرض می‌کنیم تصویر به URL تبدیل می‌شود
                    lines.set(i + 11, pdfAd.getText()); // آدرس pdfAd
                    lines.set(i + 12, countLabel.getText());
                    lines.set(i + 13, Pricelab.getText());

                    FileReader myreader = new FileReader(bookInfPath);
                    FileReader myreader1 = new FileReader(bookInfPath);
                    Scanner sc = new Scanner(myreader);
                    while (sc.hasNextLine()) {
                        if (bookName.trim().equals(sc.nextLine())) {
                            sc.nextLine();
                            sc.nextLine();
                            lines.set(i + 14,sc.nextLine());
                        }
                    }

                    FileWriter mywriter2 = new FileWriter(filePath);
                    for (String line2 : lines) {
                        mywriter2.write(line2 + "\n");
                    }
                    mywriter2.close();

                    // ذخیره خطوط فایل در یک لیست
                    List<String> lines1 = new ArrayList<>();
                    // خواندن تمامی خطوط فایل و ذخیره در لیست
                    Scanner sc1 = new Scanner(myreader1);
                    while (sc1.hasNextLine()) {
                        lines1.add(sc1.nextLine());
//                        System.out.println(sc1.nextLine());
                    }
                    // جستجو برای کتاب و اعمال تغییرات در سطرهای بعدی
                    boolean foundBook = false;
                    for (i = 0; i < lines1.size(); i++) {
                        if (bookName.trim().equals(lines1.get(i))) {
                            foundBook = true;

                            // اعمال تغییرات در لیست
                            lines1.set(i + 1,Pricelab.getText().replaceAll("[^\\d]", ""));
                            lines1.set(i + 2, ImageAd.getText());
                            lines1.set(i + 4, Writerlab.getText());
                            lines1.set(i + 5, Translatorlab.getText());
                            lines1.set(i + 6, Nasherlab.getText());
                            lines1.set(i + 7, countLabel.getText());
                            lines1.set(i + 8, CategoryLab.getText());

                            // قطع حلقه پس از پیدا کردن کتاب
                            break;
                        }
                    }

                    // اگر کتاب پیدا شد، تغییرات را در فایل اعمال کنید
                    if (foundBook) {
                        FileWriter mywriter = new FileWriter(bookInfPath);
                        for (String line1 : lines1) {
                            mywriter.write(line1 + "\n");
                        }
                        mywriter.close();
                    } else {
                        // اگر کتاب پیدا نشد، پیام خطا نمایش دهید
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Book not found in the file!");
                        alert.showAndWait();
                    }
                    sc.close();
                    myreader.close();
                    showAlert1("تبریک", "اطلاعات بروزرسانی شدند !");
                    found = true;
                    break;
                }

            }

            // اگر کتاب پیدا نشد، اطلاعات جدید را به انتهای فایل اضافه کن
            if (!found) {
                lines.add(bookName); // نام کتاب
                lines.add(Writerlab.getText());
                lines.add(Translatorlab.getText());
                lines.add(Nasherlab.getText());
                lines.add(Publishedlab.getText());
                lines.add(publicationlab.getText());
                lines.add(BookSizelab.getText());
                lines.add(Coverlab.getText());
                lines.add(PageNumlab.getText());
                lines.add(CategoryLab.getText());
                lines.add(ImageAd.getText()); // آدرس تصویر
                lines.add(pdfAd.getText()); // آدرس pdfAd
                lines.add(countLabel.getText());
                lines.add(Pricelab.getText());
                FileReader myreader = new FileReader(bookInfPath);
                Scanner sc = new Scanner(myreader);
                while (sc.hasNextLine()) {
                    if (bookName.trim().equals(sc.nextLine())) {
                        sc.nextLine();
                        sc.nextLine();
                        lines.add(sc.nextLine());
                    }
                }
                showAlert1("تبریک ", "اطلاعات افزوده شد!");

            }
            // نوشتن مجدد اطلاعات به فایل
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String lineToWrite : lines) {
                    writer.write(lineToWrite);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void changeImg(MouseEvent event) {
        // ایجاد یک FileChooser
        FileChooser fileChooser = new FileChooser();

        // تنظیم فیلتر برای نمایش فقط فایل‌های تصویری
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // نمایش دیالوگ انتخاب فایل
        File selectedFile = fileChooser.showOpenDialog(null);

        // اگر فایلی انتخاب شد، آن را در ImageView نمایش می‌دهیم
        if (selectedFile != null) {
            // ساخت یک Image از فایل انتخاب شده
            Image image = new Image(selectedFile.toURI().toString());
            // تنظیم تصویر در ImageView
            bookImg.setImage(image);
            // دریافت اسم فایل از فایل انتخاب شده
            String fileName = selectedFile.getName();

            // ترکیب مسیر ثابت با اسم فایل
            String customPath = "/img/" + fileName;

            // قرار دادن آدرس دلخواه در تکست فیلد
            ImageAd.setText(customPath);
        }
    }

    @FXML
    void downlodpdf(ActionEvent event) {
        // ایجاد یک FileChooser
        FileChooser fileChooser = new FileChooser();

        // تنظیم فیلتر برای نمایش فقط فایل‌های PDF
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // نمایش دیالوگ انتخاب فایل
        File selectedFile = fileChooser.showOpenDialog(null);

        // اگر فایلی انتخاب شد
        if (selectedFile != null) {
            // مسیر مطلق پوشه src\pdfs
            Path pdfDirectory = Paths.get("src", "pdfs").toAbsolutePath();  // تغییر به پوشه src\pdfs
            Path selectedFilePath = selectedFile.toPath().toAbsolutePath(); // تبدیل مسیر فایل انتخابی به مسیر مطلق

            // بررسی اینکه آیا فایل درون پوشه pdfs است
            if (selectedFilePath.startsWith(pdfDirectory)) {
                // اگر فایل داخل پوشه pdfs است، مسیر نسبی را بسازید
                try {
                    Path relativePath = pdfDirectory.relativize(selectedFilePath);
                    // قرار دادن آدرس نسبی در تکست فیلد PdfAdr
                    pdfAd.setText(relativePath.toString());
                } catch (IllegalArgumentException e) {
                    // خطا در ایجاد مسیر نسبی
                    showAlert("خطا", "مسیر انتخابی قابل تبدیل به مسیر نسبی نیست!");
                }
            } else {
                // اگر فایل خارج از پوشه pdfs است، هشدار دهید
                showAlert("خطا", "فایل انتخابی در پوشه pdfs قرار ندارد!");
            }
        }
    }

    @FXML
    void increaseBtn(ActionEvent event) {
        try {
            int currentCount = Integer.parseInt(countLabel.getText());
            currentCount++;
            countLabel.setText(String.valueOf(currentCount));
        } catch (NumberFormatException e) {
            showAlert("خطا", "مقدار تعداد نامعتبر است!");
            e.printStackTrace();
        }
    }

    @FXML
    void decreaseBtn(ActionEvent event) {
        try {
            int currentCount = Integer.parseInt(countLabel.getText());
            if (currentCount > 0) {
                currentCount--;
            }
            countLabel.setText(String.valueOf(currentCount));
        } catch (NumberFormatException e) {
            showAlert("خطا", " مقدار تعداد نامعتبر است!");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert1(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
