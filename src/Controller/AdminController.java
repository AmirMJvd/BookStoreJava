package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import main.Main;
import main.MyListener;
import model.Book;
import model.BookLists;
import model.Cart;
import model.Report;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AdminController implements Initializable {


    @FXML
    private AnchorPane AdminPane;

    @FXML
    private TextField Color;

    @FXML
    private TextField ImgAdr;

    @FXML
    private TextField author;

    @FXML
    private TextField bookName;

    @FXML
    private TextField count;

    @FXML
    private TextField nasher;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField translator;

    @FXML
    private TextField Category;


    @FXML
    private Label Report;

    @FXML
    private GridPane grid;

    //-------
    @FXML
    private Label CategoryLab;

    @FXML
    private ImageView bookImg;

    @FXML
    private Label bookNameLable;

    @FXML
    private VBox chosenBookCard;

    @FXML
    private Label countLab;

    @FXML
    private Label countLabel;

    @FXML
    private Label nasherLab;

    @FXML
    private Label translatorLab;

    @FXML
    private Label writerLab;

    @FXML
    private TextField PriceTxt;

    @FXML
    private GridPane grid1;







    @FXML
    void CameBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/market.fxml"));
        ;
        AdminPane.getChildren().setAll(pane);
    }

    @FXML
    void registration(ActionEvent event) {
        try {
            // بررسی اینکه هیچ فیلدی خالی نباشد
            if (bookName.getText().isEmpty() || ImgAdr.getText().isEmpty() || Color.getText().isEmpty() ||
                    priceTextField.getText().isEmpty()) {
                Report.setText("لطفاً همه فیلدها را پر کنید!");
                return;
            }

            // بررسی مقدار عددی بودن فیلدهای قیمت و تعداد
            int price;
            int countValue;
            try {
                price = Integer.parseInt(priceTextField.getText());
                countValue = Integer.parseInt(count.getText());
            } catch (NumberFormatException e) {
                Report.setText("قیمت و تعداد باید عدد باشند!");
                return;
            }

            // نوشتن داده‌ها در فایل
            FileWriter myWriter = new FileWriter("BookInf.txt", true);

            myWriter.write(bookName.getText());
            myWriter.write("\n");

            myWriter.write(priceTextField.getText());
            myWriter.write("\n");

            myWriter.write(ImgAdr.getText());
            myWriter.write("\n");

            myWriter.write(Color.getText());
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

            Report.setText("محصول با موفقیت ثبت شد!");
            clearFields(); // پاک کردن فیلدها

        } catch (IOException e) {
            Report.setText("خطا در ذخیره اطلاعات!");
            e.printStackTrace();
        }
    }

    // متد برای پاک کردن فیلدهای ورودی پس از ثبت اطلاعات
    private void clearFields() {
        bookName.clear();
        author.clear();
        translator.clear();
        nasher.clear();
        ImgAdr.clear();
        Color.clear();
        priceTextField.clear();
        count.clear();
    }


    private List<model.Report> reports = new ArrayList<>();
    private List<Report> getReportData() {
        List<Report> reports = new ArrayList<>();
        try {
            File cartFile = new File("Report.txt");
            Scanner reader = new Scanner(cartFile);
            while (reader.hasNextLine()) {
                Report report = new Report();
                report.setName(reader.nextLine());
                report.setPrice(reader.nextLine());
                report.setImgSrc(reader.nextLine());
                report.setCount(reader.nextLine());
                report.setDate(reader.nextLine());
                reports.add(report);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public void initialize(URL location, ResourceBundle resources) {
        reports.addAll(getReportData());
        BookLists bookLists = getData();
        allBooks.addAll(bookLists.getAllBooks());
        scientificBooks.addAll(bookLists.getScientificBooks());
        politicalBooks.addAll(bookLists.getPoliticalBooks());
        psychologyBooks.addAll(bookLists.getPsychologyBooks());
        if (allBooks.size() > 0) {
            setChosenFruit(allBooks.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Book fruit) {
                    setChosenFruit(fruit);
                }
            };
        }
        int row = 1;
        int column = 0;
        try {
            for (Report report : reports) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/report.fxml"));
                HBox HBox = fxmlLoader.load();

                ReportItemController cartItemController = fxmlLoader.getController();
                cartItemController.setData(report);

                if (column == 2) {
                    column = 0;
                    row++;
                }
                grid.add(HBox, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(HBox, new Insets(10));
            }
             row = 1;
             column = 0;
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

                    grid1.add(anchorPane, column++, row); //(child,column,row)
                    //set grid width
                    grid1.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid1.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid1.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid1.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid1.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid1.setMaxHeight(Region.USE_PREF_SIZE);


                    GridPane.setMargin(anchorPane, new Insets(10));
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        return new BookLists(allBooks, scientificBooks, politicalBooks, psychologyBooks);
    }
    private void setChosenFruit(Book book) {
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
    void decreaseBtn(ActionEvent event) {
                try {
                    // دریافت مقدار فعلی از countLabel
                    int currentCount = Integer.parseInt(countLabel.getText());
                    // کاهش مقدار (اگر از صفر بزرگ‌تر است)
                    if (currentCount > 0) {
                        currentCount--;
                    }
                    // به‌روزرسانی countLabel
                    countLabel.setText(String.valueOf(currentCount));
                } catch (NumberFormatException e) {
                    Report.setText("خطا: مقدار تعداد نامعتبر است!");
                    e.printStackTrace();
                }
    }

    @FXML
    void increaseBtn(ActionEvent event) {
        try {
            // دریافت مقدار فعلی از countLabel
            int currentCount = Integer.parseInt(countLabel.getText());
            // افزایش مقدار
            currentCount++;
            // به‌روزرسانی countLabel
            countLabel.setText(String.valueOf(currentCount));
        } catch (NumberFormatException e) {
            Report.setText("خطا: مقدار تعداد نامعتبر است!");
            e.printStackTrace();
        }
    }

    @FXML
    void AddChange(ActionEvent event) {
        String bookNameToUpdate = bookNameLable.getText();
        String newPrice = PriceTxt.getText();
        String newCount = countLabel.getText();

        if (bookNameToUpdate.isEmpty() || newPrice.isEmpty() || newCount.isEmpty()) {
            Report.setText("لطفاً تمام اطلاعات را وارد کنید!");
            return;
        }

        try {
            File inputFile = new File("BookInf.txt");
            File tempFile = new File("TempBookInf.txt");

            Scanner scanner = new Scanner(inputFile);
            FileWriter writer = new FileWriter(tempFile);

            boolean found = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals(bookNameToUpdate)) {
                    // اگر نام کتاب پیدا شد
                    found = true;
                    writer.write(line + "\n"); // نام کتاب
                    writer.write(newPrice + "\n"); // قیمت جدید
                    scanner.nextLine(); // رد کردن قیمت قبلی
                    writer.write(scanner.nextLine() + "\n"); // آدرس تصویر
                    writer.write(scanner.nextLine() + "\n"); // رنگ
                    writer.write(scanner.nextLine() + "\n"); // نویسنده
                    writer.write(scanner.nextLine() + "\n"); // مترجم
                    writer.write(scanner.nextLine() + "\n"); // ناشر
                    writer.write(newCount + "\n"); // تعداد جدید
                    scanner.nextLine(); // رد کردن تعداد قبلی
                    writer.write(scanner.nextLine() + "\n"); // دسته‌بندی
                } else {
                    writer.write(line + "\n");
                }
            }

            scanner.close();
            writer.close();

            if (!found) {
                Report.setText("کتاب موردنظر پیدا نشد!");
                return;
            }

            // جایگزینی فایل اصلی با فایل موقت
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
                Report.setText("تغییرات با موفقیت ذخیره شد!");
            } else {
                Report.setText("خطا در ذخیره تغییرات!");
            }
        } catch (IOException e) {
            Report.setText("خطا در دسترسی به فایل!");
            e.printStackTrace();
        }
    }

}




