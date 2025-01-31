package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import main.Main;
import main.MyListener;
import model.Book;
import model.BookLists;
import model.Report;
import model.SharedData;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class admin1Controller implements Initializable{

    @FXML
    private AnchorPane AdminPane;

    @FXML
    private ColorPicker color;

    @FXML
    private Label AdminName;

    @FXML
    private TextField Category;

    @FXML
    private Label CategoryLab;

    @FXML
    private TextField ImgAdr;

    @FXML
    private TextField PasswordRepet;

    @FXML
    private TextField PriceTxt;

    @FXML
    private Button Registration;

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
    private TextField translator;

    @FXML
    private Label translatorLab;

    @FXML
    private TextField userName;

    @FXML
    private Label writerLab;


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
    List<Book> politicalBooks = new ArrayList<>();
    List<Book> psychologyBooks = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    private Book selectedBook;
    private BookLists getData() {
        List<Book> allBooks = new ArrayList<>();
        List<Book> scientificBooks = new ArrayList<>();
        List<Book> politicalBooks = new ArrayList<>();
        List<Book> psychologyBooks = new ArrayList<>();
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

        return new BookLists(allBooks, scientificBooks, politicalBooks, psychologyBooks);
    }
    @FXML
    public void initialize (URL location, ResourceBundle resources) {
//        reports.addAll(getReportData());
        BookLists bookLists = getData();
        allBooks.addAll(bookLists.getAllBooks());
        scientificBooks.addAll(bookLists.getScientificBooks());
        politicalBooks.addAll(bookLists.getPoliticalBooks());
        psychologyBooks.addAll(bookLists.getPsychologyBooks());
        if (allBooks.size() > 0) {
            setChosenBook(allBooks.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Book fruit) {
                    setChosenBook(fruit);
                }
            };
        }
        int row = 1;
        int column = 0;

//            for (Report report : reports) {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/views/report.fxml"));
//                HBox HBox = fxmlLoader.load();
//
//                ReportItemController cartItemController = fxmlLoader.getController();
//                cartItemController.setData(report);
//
//                if (column == 2) {
//                    column = 0;
//                    row++;
//                }
//                grid.add(HBox, column++, row);
//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMaxHeight(Region.USE_PREF_SIZE);
//
//                GridPane.setMargin(HBox, new Insets(10));
//            }
        try {
            for (int i = 0; i < allBooks.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(allBooks.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid1.add(anchorPane, column++, row);
                grid1.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid1.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid1.setMaxWidth(Region.USE_PREF_SIZE);

                grid1.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid1.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid1.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        calculateTotalAmountReceived();
        userName.setOnAction(event -> phineNumber.requestFocus());
        phineNumber.setOnAction(event -> bDay.requestFocus());
        bDay.setOnAction(event -> password.requestFocus());
        password.setOnAction(event -> PasswordRepet.requestFocus());
        PasswordRepet.setOnAction(event -> {Registration(new ActionEvent());
        });
    }


    private void setChosenBook (Book book) {
        selectedBook = book;
        bookNameLable.setText(book.getName());
        PriceTxt.setText(Main.CURRENCY + book.getPrice());
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

}