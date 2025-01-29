package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import main.Main;
import main.MyListener;
import model.Book;
import model.BookLists;
import model.Report;
import model.SharedData;


import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private GridPane grid;

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
    private Label AmountReceivedLab;

    @FXML
    private Label totalProfitLab;

    @FXML
    private Label NumberOfSalesLab;

    @FXML
    private Label DateLab;

    @FXML
    private Label AdminName;

    @FXML
    private ImageView image1;


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



    @FXML
    void registration(ActionEvent event) {
        try {

            if (bookName.getText().isEmpty() || ImgAdr.getText().isEmpty() || Color.getText().isEmpty() ||
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

                String rawPrice = reader.nextLine();
                double price = extractPrice(rawPrice);

                report.setImgSrc(reader.nextLine());

                String rawCount = reader.nextLine();
                int count = Integer.parseInt(rawCount.trim());

                double totalPrice = price * count;
                report.setPrice(String.valueOf(totalPrice));
                report.setCount(String.valueOf(count));

                report.setDate(reader.nextLine());
                reports.add(report);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reports;
    }

    private double extractPrice(String priceString) {
        StringBuilder numberBuilder = new StringBuilder();
        for (char ch : priceString.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.') {
                numberBuilder.append(ch);
            }
        }
        try {
            return Double.parseDouble(numberBuilder.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public void initialize(URL location, ResourceBundle resources) {
        reports.addAll(getReportData());
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
        calculateTotalAmountReceived();
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

    @FXML
    void wasSeenBtn(ActionEvent event) {
        try {

            FileWriter writer = new FileWriter("Report.txt", false);
            writer.write("");
            writer.close();
            grid.getChildren().clear();

            AmountReceivedLab.setText("0.00");
            totalProfitLab.setText("0.00");
            NumberOfSalesLab.setText("0");

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String formattedDate = today.format(formatter);

            File file = new File("Date.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(formattedDate);
            fileWriter.write("\n");
            fileWriter.close();
            DateLab.setText(formattedDate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void calculateTotalAmountReceived() {
        double totalAmount = 0.0;
        int totalCount = 0;

        try {
            File reportFile = new File("Report.txt");
            Scanner reader = new Scanner(reportFile);

            int lineCounter = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                lineCounter++;

                if (lineCounter % 5 == 2) {

                    String numericPrice = line.replaceAll("[^\\d.]", "");

                    try {
                        double price = Double.parseDouble(numericPrice);
                        totalAmount += price;
                    } catch (NumberFormatException e) {
                        System.out.println("خطا در تبدیل قیمت: " + line);
                    }
                } else if (lineCounter % 5 == 4) {
                    String Count = line.replaceAll("[^\\d.]", "");
                    try {
                        int count = Integer.parseInt(Count);
                        totalCount += count;
                    } catch (NumberFormatException e) {
                        System.out.println("خطا در تبدیل تعداد: " + line);
                    }
                }
            }
            reader.close();

            AmountReceivedLab.setText(String.format( Main.CURRENCY + totalAmount));
            double profit = totalAmount * 0.10 ;
            totalProfitLab.setText(String.format( Main.CURRENCY + profit));

            NumberOfSalesLab.setText(String.format(String.valueOf(totalCount)));

            FileReader myReader = new FileReader("Date.txt");
            Scanner scanner = new Scanner(myReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                DateLab.setText(line);
            }




        } catch (FileNotFoundException e) {
            showAlert("خطا", "فایل Report.txt پیدا نشد!");
            e.printStackTrace();
        }
    }
    public void setId(String username1){
        AdminName.setText(username1);
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
        alert.setHeaderText("تبریک!");
        alert.setContentText(message);
        alert.showAndWait();
    }

}




