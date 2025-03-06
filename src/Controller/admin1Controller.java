package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;
import main.MyListener;
import model.Book;
import model.BookLists;
import model.SharedData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;


import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import static main.Main.CURRENCY;

public class admin1Controller implements Initializable{

        @FXML
        private Label AdminName;

        @FXML
        private AnchorPane AdminPane;

        @FXML
        private Label AmountReceivedLab;

        @FXML
        private TextField BookNamelab;

        @FXML
        private TextField BookSizelab;

        @FXML
        private TextField Category;

        @FXML
        private Label CategoryLab;

        @FXML
        private TextField Categorylab;

        @FXML
        private TextField Countlab;

        @FXML
        private TextField Coverlab;

        @FXML
        private Label DateLab;

        @FXML
        private TextField ImgAdr;

        @FXML
        private TextField ImgAdr1;

        @FXML
        private TextField Nasherlab;

        @FXML
        private Label NumberOfSalesLab;

        @FXML
        private TextField PageNumlab;

        @FXML
        private TextField PasswordRepet;

        @FXML
        private TextField PdfAdr;

        @FXML
        private TextField PriceTxt;

        @FXML
        private TextField Publishedlab;

        @FXML
        private Button Registration;

        @FXML
        private TextField Translatorlab;

        @FXML
        private TextField Writerlab;

        @FXML
        private TextField author;

        @FXML
        private TextField bDay;

        @FXML
        private ImageView bookImg;

        @FXML
        private TextField bookName;

        @FXML
        private Label bookNameLable;

        @FXML
        private VBox chosenBookCard;

        @FXML
        private ColorPicker color;

        @FXML
        private ColorPicker color1;

        @FXML
        private TextField count;

        @FXML
        private Label countLab;

        @FXML
        private Label countLabel;

        @FXML
        private Button decreaseBtn;

        @FXML
        private GridPane grid1;

        @FXML
        private ImageView image1;

        @FXML
        private ImageView image11;

        @FXML
        private TextField nasher;

        @FXML
        private Label nasherLab;

        @FXML
        private TextField password;

        @FXML
        private TextField phineNumber;

        @FXML
        private TextField priceTextField;

        @FXML
        private TextField priceTextField1;

        @FXML
        private TextField publicationlab;

        @FXML
        private Label totalProfitLab;

        @FXML
        private TextField translator;

        @FXML
        private Label translatorLab;

        @FXML
        private TextField userName;

        @FXML
        private Label writerLab;

    private String lastDownloadDate = "";


    @FXML
    void registration(ActionEvent event) {
        try {

            if (bookName.getText().isEmpty() || ImgAdr.getText().isEmpty() ||
                    priceTextField.getText().isEmpty() || author.getText().isEmpty() ||author.getText().isEmpty() ||translator.getText().isEmpty() ||nasher.getText().isEmpty() ||count.getText().isEmpty() ) {
                showAlert("خطا", "لطفا همه فیلد ها را پر کنید!");
                return;
            }

            int price;
            int countValue;
            try {
                price = Integer.parseInt(priceTextField.getText());
                countValue = Integer.parseInt(count.getText());
            } catch (NumberFormatException e) {
                showAlert("خطا", "قیمت و تعداد باید عدد باشند!");
                return;
            }

            // دریافت رنگ انتخاب شده از ColorPicker و تبدیل آن به HEX
            Color selectedColor = color.getValue();
            String hexColor = toHex(selectedColor);

            FileWriter myWriter = new FileWriter("BookInf.txt", true);

            myWriter.write(bookName.getText());
            myWriter.write("\n");

            myWriter.write(priceTextField.getText());
            myWriter.write("\n");

            myWriter.write(ImgAdr.getText());
            myWriter.write("\n");

            myWriter.write(hexColor);
            myWriter.write("\n");

            myWriter.write(author.getText());
            myWriter.write("\n");

            myWriter.write(translator.getText());
            myWriter.write("\n");

            myWriter.write(nasher.getText());
            myWriter.write("\n");

            myWriter.write(count.getText());
            myWriter.write("\n");

            myWriter.write(Category.getText());
            myWriter.write("\n");
            myWriter.close();

            showAlert1("عملیات موفقیت‌آمیز", "محصول با موفقیت ثبت شد!");
            clearFields();

        } catch (IOException e) {
            showAlert("خطا", "خطا در ذخیره اطلاعات!");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        bookName.clear();
        author.clear();
        translator.clear();
        nasher.clear();
        ImgAdr.clear();
        priceTextField.clear();
        count.clear();
    }

    private String toHex(Color color) {
        return String.format("%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }


    @FXML
    void CameBack(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Market.fxml"));
        AnchorPane pane = loader.load();


        pane.setPrefWidth(1315);
        pane.setPrefHeight(810);


        MarketController marketController = loader.getController();
        String username1 = SharedData.getInstance().getUsername();
        marketController.setId(username1);


        AdminPane.setPrefSize(1315, 810);
        AdminPane.setMaxWidth(1315);
        AdminPane.setMaxHeight(810);
        AdminPane.setMinWidth(1315);
        AdminPane.setMinHeight(810);

        AdminPane.getChildren().setAll(pane);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert1(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("تبریک!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<Book> allBooks = new ArrayList<>();
    List<Book> scientificBooks = new ArrayList<>();
    List<Book> historicalBooks = new ArrayList<>();
    List<Book> psychologyBooks = new ArrayList<>();
    List<Book> politicalBooks = new ArrayList<>();  // اضافه کردن دسته‌بندی سیاسی
    private Image image;
    private MyListener myListener;
    private Book selectedBook;

    private BookLists getData() {
        List<Book> allBooks = new ArrayList<>();
        List<Book> scientificBooks = new ArrayList<>();
        List<Book> historicalBooks = new ArrayList<>();
        List<Book> psychologyBooks = new ArrayList<>();
        List<Book> politicalBooks = new ArrayList<>();
        Book book;
        try {
            File BookInfo = new File("BookInf.txt");
            Scanner myReader = new Scanner(BookInfo);

            while (myReader.hasNextLine()) {
                book = new Book();
                book.setName(myReader.nextLine());
                book.setPrice(myReader.nextLine());
                book.setImgSrc(myReader.nextLine());
                book.setColor(myReader.nextLine());
                book.setWriter(myReader.nextLine());
                book.setTranslator(myReader.nextLine());
                book.setNasher(myReader.nextLine());
                book.setCount(myReader.nextLine());
                String category = myReader.nextLine();
                book.setCategory(category);
                switch (category) {
                    case "علمی":
                        scientificBooks.add(book);
                        break;
                    case "تاریخی":
                        historicalBooks.add(book);
                        break;
                    case "سیاسی":
                        politicalBooks.add(book);
                        break;
                    case "روانشناسی":
                        psychologyBooks.add(book);
                        break;
                    default:
                        break;
                }
                allBooks.add(book);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new BookLists(allBooks, scientificBooks, historicalBooks, psychologyBooks,politicalBooks);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    // دریافت داده‌ها از متد getData()
    BookLists bookLists = getData();
    allBooks.addAll(bookLists.getAllBooks());
    scientificBooks.addAll(bookLists.getScientificBooks());
    historicalBooks.addAll(bookLists.getHistoricalBooks());
    psychologyBooks.addAll(bookLists.getPsychologyBooks());
    politicalBooks.addAll(bookLists.getPoliticalBooks());  // اضافه کردن کتاب‌های سیاسی

    // اطمینان از این که حداقل یک کتاب موجود است
    if (!allBooks.isEmpty()) {
        setChosenBook(allBooks.get(0));

        // تنظیم MyListener برای واکنش به کلیک بر روی کتاب
        myListener = new MyListener() {
            @Override
            public void onClickListener(Book book) {
                setChosenBook(book);
            }
        };
    }

    // راه‌اندازی گرید برای نمایش کتاب‌ها
    int row = 1;
    int column = 0;
    try {
        for (int i = 0; i < allBooks.size(); i++) {
            // بارگذاری فایل FXML کتاب
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            // راه‌اندازی کنترلر ItemController
            ItemController itemController = fxmlLoader.getController();
            itemController.setData(allBooks.get(i), myListener);

            // مدیریت جایگذاری در گرید
            if (column == 3) {
                column = 0;
                row++;
            }

            grid1.add(anchorPane, column++, row);

            // تنظیم اندازه‌های گرید
            grid1.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid1.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid1.setMaxWidth(Region.USE_PREF_SIZE);

            grid1.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid1.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid1.setMaxHeight(Region.USE_PREF_SIZE);

            // اضافه کردن فاصله بین سلول‌ها
            GridPane.setMargin(anchorPane, new Insets(10));
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
        // خواندن تاریخ ذخیره‌شده از فایل
        try (BufferedReader reader = new BufferedReader(new FileReader("Date.txt"))) {
            String savedDate = reader.readLine();
            if (savedDate != null && !savedDate.isEmpty()) {
                DateLab.setText(savedDate);
            }
        } catch (IOException e) {
            System.err.println("هیچ تاریخ دانلودی یافت نشد!");
        }

        double totalAmount = readAndCalculateTotal("Report.txt",2);
        double totalProfit = readAndCalculateTotal("Report.txt", 3);
        double totalSales = readAndCalculateTotal("Report.txt", 4);
        AmountReceivedLab.setText(String.format("%.2f", totalAmount));
        totalProfitLab.setText(String.format("%.2f", totalProfit));
        NumberOfSalesLab.setText(String.format("%.0f", totalSales));


    // تنظیمات فیلدهای فرم (در صورت استفاده از فیلدهای ورود اطلاعات)
    userName.setOnAction(event -> phineNumber.requestFocus());
    phineNumber.setOnAction(event -> bDay.requestFocus());
    bDay.setOnAction(event -> password.requestFocus());
    password.setOnAction(event -> PasswordRepet.requestFocus());
    PasswordRepet.setOnAction(event -> {
        Registration(new ActionEvent());
    });
}

    private double readAndCalculateTotal(String fileName, int targetLine) {
        double total = 0.0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                int lineCounter = 0;
                while ((line = br.readLine()) != null) {
                    lineCounter++;
                    if (lineCounter % 11 == targetLine) { // خط مورد نظر هر محصول
                        String valueStr = line.replaceAll("[^\\d.]", "").trim();
                        try {
                            total += Double.parseDouble(valueStr);
                        } catch (NumberFormatException e) {
                            System.err.println("خطا در تبدیل مقدار: " + line);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return total;
    }




    private void setChosenBook (Book book) {
        selectedBook = book;
        bookNameLable.setText(book.getName());
        PriceTxt.setText(CURRENCY + book.getPrice());
        writerLab.setText(book.getWriter());
        translatorLab.setText(book.getTranslator());
        nasherLab.setText(book.getNasher());
        countLab.setText(book.getCount());
        countLabel.setText(book.getCount());
        CategoryLab.setText(book.getCategory());
        image = new Image(getClass().getResourceAsStream(book.getImgSrc()));
        bookImg.setImage(image);
        chosenBookCard.setStyle("-fx-background-color: #" + book.getColor() + ";\n" + "    -fx-background-radius: 30;");

    }

    @FXML
    void decreaseBtn (ActionEvent event) {
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
    void AddChange(ActionEvent event) {
        String bookNameToUpdate = bookNameLable.getText();
        String newPrice = PriceTxt.getText();
        String newCount = countLabel.getText();

        if (bookNameToUpdate.isEmpty() || newPrice.isEmpty() || newCount.isEmpty()) {
            showAlert("خطا", "لطفاً تمام اطلاعات را وارد کنید!");
            return;
        }

        newPrice = newPrice.replaceAll("[^0-9]", "");

        try {
            File inputFile = new File("BookInf.txt");
            File tempFile = new File("TempBookInf.txt");

            Scanner scanner = new Scanner(inputFile);
            FileWriter writer = new FileWriter(tempFile);

            boolean found = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals(bookNameToUpdate)) {
                    found = true;
                    writer.write(line + "\n");
                    writer.write(newPrice + "\n");
                    scanner.nextLine();
                    writer.write(scanner.nextLine() + "\n");
                    writer.write(scanner.nextLine() + "\n");
                    writer.write(scanner.nextLine() + "\n");
                    writer.write(scanner.nextLine() + "\n");
                    writer.write(scanner.nextLine() + "\n");
                    writer.write(newCount + "\n");
                    scanner.nextLine();
                    writer.write(scanner.nextLine() + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }

            scanner.close();
            writer.close();

            if (!found) {
                showAlert("خطا", "کتاب موردنظر پیدا نشد!");
                return;
            }

            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
                showAlert1("عملیات موفقیت‌آمیز", "تغییرات با موفقیت ذخیره شد!");
            } else {
                showAlert("خطا", "خطا در ذخیره تغییرات!");
            }
        } catch (IOException e) {
            showAlert("خطا", "خطا در دسترسی به فایل!");
            e.printStackTrace();
        }
    }

    public void setId(String username1){
        AdminName.setText(username1);
    }

    @FXML
    void choseImg(MouseEvent event) {
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
            image1.setImage(image);
            // دریافت اسم فایل از فایل انتخاب شده
            String fileName = selectedFile.getName();

            // ترکیب مسیر ثابت با اسم فایل
            String customPath = "/img/" + fileName;

            // قرار دادن آدرس دلخواه در تکست فیلد
            ImgAdr.setText(customPath);
        }
    }
        @FXML
        void choseImage(MouseEvent event) {
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
                image11.setImage(image);
                // دریافت اسم فایل از فایل انتخاب شده
                String fileName = selectedFile.getName();

                // ترکیب مسیر ثابت با اسم فایل
                String customPath = "/img/" + fileName;

                // قرار دادن آدرس دلخواه در تکست فیلد
                ImgAdr1.setText(customPath);
            }
        }

        @FXML
        void chosepdf(MouseEvent event) {
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
                        PdfAdr.setText(relativePath.toString());
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







        private static final String FILE_NAME = "user.txt";

    @FXML
    void Registration(ActionEvent event) {
        String username = userName.getText().trim();
        String phone = phineNumber.getText().trim();
        String birthDate = bDay.getText().trim();
        String pass = password.getText().trim();
        String passRepeat = PasswordRepet.getText().trim();

        // بررسی اینکه هیچ فیلدی خالی نباشد
        if (username.isEmpty() || phone.isEmpty() || birthDate.isEmpty() || pass.isEmpty() || passRepeat.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "خطا", "تمامی فیلدها باید پر شوند!");
            return;
        }

        // بررسی مطابقت رمز عبور
        if (!pass.equals(passRepeat)) {
            showAlert(Alert.AlertType.ERROR, "خطا", "رمز عبور و تکرار آن مطابقت ندارند!");
            return;
        }

        // بررسی اینکه نام کاربری از قبل وجود نداشته باشد
        if (isUsernameExists(username)) {
            showAlert(Alert.AlertType.ERROR, "خطا", "این نام کاربری قبلاً ثبت شده است!");
            return;
        }

        // ذخیره اطلاعات در فایل
        saveUserData(username, pass, phone, birthDate);
        showAlert(Alert.AlertType.INFORMATION, "موفقیت", "ثبت‌نام با موفقیت انجام شد!");
        clearFields1();
    }

    private boolean isUsernameExists(String username) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String existingUsername = scanner.nextLine().trim();
                if (existingUsername.equals(username)) {
                    return true;  // نام کاربری یافت شد
                }
                // رد شدن از سطرهای دیگر (رمز عبور، شماره موبایل، تاریخ تولد، نوع کاربر)
                if (scanner.hasNextLine()) scanner.nextLine();
                if (scanner.hasNextLine()) scanner.nextLine();
                if (scanner.hasNextLine()) scanner.nextLine();
                if (scanner.hasNextLine()) scanner.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveUserData(String username, String password, String phone, String birthDate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "\n");
            writer.write(password + "\n");
            writer.write(phone + "\n");
            writer.write(birthDate + "\n");
            writer.write("مدیر\n"); // سطر پنجم مشخص‌کننده نوع کاربر
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearFields1() {
        userName.clear();
        phineNumber.clear();
        bDay.clear();
        password.clear();
        PasswordRepet.clear();
    }

    @FXML
    void downlod(ActionEvent event) {
        // دریافت تاریخ و زمان فعلی
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        lastDownloadDate = now.format(formatter);

        // به روز رسانی تاریخ در لیبل
        DateLab.setText(lastDownloadDate);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("انتخاب مسیر ذخیره‌سازی");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        File saveFile = fileChooser.showSaveDialog(null);
        if (saveFile == null) {
            return; // اگر کاربر لغو کند، عملیات متوقف شود
        }

        try {
            // چک کردن اینکه فایل ورودی موجود است
            File inputFile = new File("Report.txt");
            if (!inputFile.exists()) {
                showAlert("خطا", "فایل گزارش یافت نشد!");
                return;
            }

            // خواندن محتویات فایل
            Scanner scanner = new Scanner(inputFile);
            List<List<String>> products = new ArrayList<>();
            double totalPrice = 0.0;
            double totalDiscountPrice = 0.0;
            int totalQuantity = 0;

            while (scanner.hasNextLine()) {
                List<String> productData = new ArrayList<>();
                productData.add(scanner.nextLine().split(":")[1].trim()); // نام محصول

                if (!scanner.hasNextLine()) break;
                String price = scanner.nextLine().split(":")[1].trim(); // قیمت
                price = price.replace("ريال", "").trim(); // حذف کلمه "ريال" و فاصله‌های اضافی
                productData.add(price);
                totalPrice += Double.parseDouble(price); // تبدیل به عدد و افزودن به جمع

                if (!scanner.hasNextLine()) break;
                String discountPrice = scanner.nextLine().split(":")[1].trim(); // ۱۰٪ قیمت
                discountPrice = discountPrice.replace("ريال", "").trim(); // حذف کلمه "ريال"
                productData.add(discountPrice);
                totalDiscountPrice += Double.parseDouble(discountPrice); // تبدیل به عدد و افزودن به جمع

                if (!scanner.hasNextLine()) break;
                String quantity = scanner.nextLine().split(":")[1].trim(); // تعداد
                productData.add(quantity);
                totalQuantity += Integer.parseInt(quantity); // تبدیل به عدد صحیح و افزودن به جمع

                if (!scanner.hasNextLine()) break;
                productData.add(scanner.nextLine().split(":")[1].trim()); // تاریخ
                if (!scanner.hasNextLine()) break;
                productData.add(scanner.nextLine().split(":")[1].trim()); // نام خریدار
                if (!scanner.hasNextLine()) break;
                productData.add(scanner.nextLine().split(":")[1].trim()); // نام تحویل گیرنده
                if (!scanner.hasNextLine()) break;
                productData.add(scanner.nextLine().split(":")[1].trim()); // آدرس
                if (!scanner.hasNextLine()) break;
                productData.add(scanner.nextLine().split(":")[1].trim()); // کد پستی
                if (!scanner.hasNextLine()) break;
                productData.add(scanner.nextLine().split(":")[1].trim()); // شماره تماس

                // افزودن اطلاعات به لیست محصولات
                products.add(productData);

                // گذر از خط جداکننده
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); // خط جداکننده
                }
            }
            scanner.close();

            // ایجاد سند Excel
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("گزارش خرید");

            // ایجاد ردیف هدر
            Row headerRow = sheet.createRow(0);
            String[] headers = {"نام محصول", "قیمت", "سود", "تعداد", "تاریخ", "نام حساب کاربری", "نام تحویل گیرنده", "آدرس", "کد پستی", "شماره تماس"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(getHeaderStyle(workbook)); // تنظیم استایل برای هدر
            }

            // افزودن داده‌ها به اکسل
            int rowNum = 1;
            for (List<String> product : products) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < product.size(); i++) {
                    row.createCell(i).setCellValue(product.get(i));
                }
            }

            // ردیف جمع
            Row totalRow = sheet.createRow(rowNum++);
            totalRow.createCell(0).setCellValue("جمع");
            totalRow.createCell(1).setCellValue(totalPrice); // جمع قیمت
            totalRow.createCell(2).setCellValue(totalDiscountPrice); // جمع ۱۰٪ قیمت
            totalRow.createCell(3).setCellValue(totalQuantity); // جمع تعداد

            // تنظیم عرض ستون‌ها برای نمایش بهتر
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // ذخیره در مسیر انتخاب شده
            try (FileOutputStream out = new FileOutputStream(saveFile)) {
                workbook.write(out);
            }
            workbook.close();

            // نمایش پیام موفقیت
            showAlert1("عملیات موفقیت‌آمیز", "فایل اکسل با موفقیت ذخیره شد!");

        } catch (IOException e) {
            showAlert("خطا", "مشکلی در پردازش فایل رخ داد!");
            e.printStackTrace();
        }
        // ذخیره تاریخ در فایل
        try (FileWriter writer = new FileWriter("Date.txt")) {
            writer.write(lastDownloadDate);
        } catch (IOException e) {
            System.err.println("خطا در ذخیره تاریخ دانلود!");
        }

    }

    // تابع برای ایجاد استایل هدر
    private CellStyle getHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    @FXML
    void ProductRegistration(ActionEvent event) {
        try {
            // بررسی اینکه هیچ فیلدی خالی نباشد
                if (BookNamelab.getText().isEmpty() || ImgAdr1.getText().isEmpty() ||
                        priceTextField1.getText().isEmpty() || Writerlab.getText().isEmpty() ||
                        Translatorlab.getText().isEmpty() || Nasherlab.getText().isEmpty() ||
                        Countlab.getText().isEmpty() ||Categorylab.getText().isEmpty() ||
                        Publishedlab.getText().isEmpty() ||publicationlab.getText().isEmpty() ||
                        PageNumlab.getText().isEmpty() ||Coverlab.getText().isEmpty() ||
                        PdfAdr.getText().isEmpty()) {
                    showAlert("خطا", "لطفا همه فیلدها را پر کنید!");
                    return;
                }

                int price;
                int countValue;
                try {
                    price = Integer.parseInt(priceTextField1.getText());
                    countValue = Integer.parseInt(Countlab.getText());
                } catch (NumberFormatException e) {
                    showAlert("خطا", "قیمت و تعداد باید عدد باشند!");
                    return;
                }

                // دریافت رنگ انتخاب شده از ColorPicker و تبدیل آن به HEX
                Color selectedColor = color1.getValue(); // فرض بر این است که رنگ از ColorPicker1 گرفته شده
                String hexColor = toHex1(selectedColor);

                // ایجاد و نوشتن در فایل متنی
                FileWriter myWriter = new FileWriter("BookInfo.txt", true);  // فایل BookInfo.txt ساخته می‌شود یا به آن افزوده می‌شود
                FileWriter printWriter = new FileWriter("BookInf.txt", true);

                myWriter.write(BookNamelab.getText());  // نام کتاب
                myWriter.write("\n");

                myWriter.write(Writerlab.getText());  // نام نویسنده
                myWriter.write("\n");

                myWriter.write(Translatorlab.getText());  // نام مترجم
                myWriter.write("\n");

                myWriter.write(Nasherlab.getText());  // نام ناشر
                myWriter.write("\n");

                myWriter.write(Publishedlab.getText());  // سال چاپ
                myWriter.write("\n");

                myWriter.write(publicationlab.getText());  // نوبت چاپ
                myWriter.write("\n");

                myWriter.write(BookSizelab.getText());  // قطع کتاب
                myWriter.write("\n");

                myWriter.write(Coverlab.getText());  // جلد کتاب
                myWriter.write("\n");

                myWriter.write(PageNumlab.getText());  // تعداد صفحات
                myWriter.write("\n");

                myWriter.write(Categorylab.getText());  // دسته‌بندی محصول
                myWriter.write("\n");

                myWriter.write(ImgAdr1.getText());  // آدرس تصویر
                myWriter.write("\n");

                myWriter.write(PdfAdr.getText());  // آدرس پی‌دی‌اف
                myWriter.write("\n");

                myWriter.write(Countlab.getText());  // موجودی کتاب
                myWriter.write("\n");

                myWriter.write(priceTextField1.getText() + CURRENCY);  // قیمت محصول
                myWriter.write("\n");

                myWriter.write(hexColor);  // رنگ انتخابی
                myWriter.write("\n");

                myWriter.close();  // بستن فایل

                printWriter.write(BookNamelab.getText());
                printWriter.write("\n");

                printWriter.write(priceTextField1.getText());
                printWriter.write("\n");

                printWriter.write(ImgAdr1.getText());
                printWriter.write("\n");

                printWriter.write(hexColor);
                printWriter.write("\n");

                printWriter.write(Writerlab.getText());
                printWriter.write("\n");

                printWriter.write(Translatorlab.getText());
                printWriter.write("\n");

                printWriter.write(Nasherlab.getText());
                printWriter.write("\n");

                printWriter.write(Countlab.getText());
                printWriter.write("\n");

                printWriter.write(Categorylab.getText());
                printWriter.write("\n");

                printWriter.close();


                // نمایش پیغام موفقیت‌آمیز بودن ثبت
                showAlert1("عملیات موفقیت‌آمیز", "محصول با موفقیت ثبت شد!");
                clearFields2();  // پاک کردن فیلدها بعد از ثبت

            } catch (IOException e) {
                showAlert("خطا", "خطا در ذخیره اطلاعات!");
                e.printStackTrace();
            }
        }

        private void clearFields2() {
            BookNamelab.clear();
            Writerlab.clear();
            Translatorlab.clear();
            Nasherlab.clear();
            ImgAdr1.clear();
            priceTextField1.clear();
            Countlab.clear();
            Categorylab.clear();
            Publishedlab.clear();
            publicationlab.clear();
            BookSizelab.clear();
            Coverlab.clear();
            PageNumlab.clear();
            PdfAdr.clear();
            color1.setValue(null);  // رنگ را نیز پاک می‌کنیم
        }

        private String toHex1(Color color) {
            return String.format("%02X%02X%02X",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255));
        }

        @FXML
        void openEdit(MouseEvent event) {
        try {
            // مقدار نام کتاب را به InfoController ارسال کنید
            EditController.bookName = bookNameLable.getText();

            // بارگذاری FXML جدید
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Edit.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
       }
}