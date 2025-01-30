package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.MyListener;
import model.Book;
import model.BookLists;
import model.SharedData;
import model.StageManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MarketController implements Initializable {
    private StageManager stageManager = new StageManager();
    @FXML
    private ImageView bookImg;

    @FXML
    private Label bookNameLable;

    @FXML
    private Label bookPriceLabel;

    @FXML
    private VBox chosenBookCard;

    @FXML
    private Label CategoryLab;

    @FXML
    private Label countLab;

    @FXML
    private Label nasherLab;

    @FXML
    private Label translatorLab;

    @FXML
    private Label writerLab;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane scientificGrid;

    @FXML
    private GridPane politicalGrid;

    @FXML
    private GridPane psychologyGrrid;

    @FXML
    private TextField SearchTxt;

    @FXML
    private Button AddCart;

    @FXML
    private Label countLabel;

    @FXML
    private Label lblid;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label QuantityInCart;


    @FXML
    private Label lblsabadkharid;

    private List<Book> allBooks = new ArrayList<>();
    List<Book> scientificBooks = new ArrayList<>();
    List<Book> politicalBooks = new ArrayList<>();
    List<Book> psychologyBooks = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    private Book selectedBook;

    public void setId(String username1){
        lblid.setText(username1);
    }


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


    private void setChosenBook(Book book) {
        selectedBook = book;
        bookNameLable.setText(book.getName());
        bookPriceLabel.setText(Main.CURRENCY + book.getPrice());
        writerLab.setText(book.getWriter());
        translatorLab.setText(book.getTranslator());
        nasherLab.setText(book.getNasher());
        countLab.setText(book.getCount());
        CategoryLab.setText(book.getCategory());
        image = new Image(getClass().getResourceAsStream(book.getImgSrc()));
        bookImg.setImage(image);
        chosenBookCard.setStyle("-fx-background-color: #" + book.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        int column = 0;
        int row = 1;
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

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(10));
            }
             column = 0;
             row = 1;

            for (int i = 0; i < scientificBooks.size(); i++) {


                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController2 = fxmlLoader.getController();
                itemController2.setData(scientificBooks.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                scientificGrid.add(anchorPane, column++, row);
                scientificGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                scientificGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                scientificGrid.setMaxWidth(Region.USE_PREF_SIZE);
                scientificGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                scientificGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                scientificGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
             column = 0;
             row = 1;
            for (int i = 0; i < politicalBooks.size(); i++) {


                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController2 = fxmlLoader.getController();
                itemController2.setData(politicalBooks.get(i), myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }

                politicalGrid.add(anchorPane, column++, row);
                politicalGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                politicalGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                politicalGrid.setMaxWidth(Region.USE_PREF_SIZE);
                politicalGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                politicalGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                politicalGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
             column = 0;
             row = 1;
            for (int i = 0; i < psychologyBooks.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController2 = fxmlLoader.getController();
                itemController2.setData(psychologyBooks.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                psychologyGrrid.add(anchorPane, column++, row);

                psychologyGrrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setMaxWidth(Region.USE_PREF_SIZE);
                psychologyGrrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
            // تنظیم نام کاربری در lblid
            String currentUser = SharedData.getInstance().getUsername();
            if (currentUser != null && !currentUser.isEmpty()) {
                lblid.setText(currentUser);
            }
            setId();
            updateQuantityInCart();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setId() {
        lblid.setText(SharedData.getInstance().getUsername());
    }

    @FXML
    void LoadCart (MouseEvent event)  throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Cart.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void pressbtnvorud1 (MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../views/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 810);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("ورود");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    boolean bookFound = true;
    @FXML
    void AddCart(ActionEvent event) throws IOException {
       if (!bookFound) {
        showAlert("خطا", "محصولی یافت نشد!");
        return;
       }

         if (countLab.getText().compareTo("0") == 0) {
        showAlert("خطا", "متاسفانه کتاب موجود نیست!");
        return;
         }

    // بررسی مقدار lblid قبل از استفاده از آن
    if (lblid.getText() == null || lblid.getText().isEmpty()) {
        showAlert("خطا", "ابتدا باید ورود بفرمایید!");
        return;
    }

    String id = lblid.getText();
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
            if (fileContent.get(i).equals(bookNameLable.getText())) {
                bookExists = true;
                bookIndex = i;
                break;
            }
        }
    }

    String priceText = bookPriceLabel.getText().replaceAll("[^\\d.]", ""); // فقط اعداد و نقطه
    double labelPrice = 0.0;

    try {
        labelPrice = Double.parseDouble(priceText);
    } catch (NumberFormatException e) {
        showAlert("خطا", "فرمت قیمت نامعتبر است!");
        return;
    }

    int countToAdd = Integer.parseInt(countLabel.getText());
    int maxCount = Integer.parseInt(countLab.getText());

    if (bookExists) {
        double currentPrice = Double.parseDouble(fileContent.get(bookIndex + 1).replaceAll("[^\\d.]", ""));
        double newPrice = currentPrice + labelPrice;

        int currentCount = Integer.parseInt(fileContent.get(bookIndex + 3));
        if (currentCount + countToAdd <= maxCount) {
            int newCount = currentCount + countToAdd;
            fileContent.set(bookIndex + 1, String.valueOf(Main.CURRENCY + newPrice));
            fileContent.set(bookIndex + 3, String.valueOf(newCount));

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
                myWriter.write(bookNameLable.getText());
                myWriter.write("\n");
                myWriter.write(String.valueOf(Main.CURRENCY + labelPrice));
                myWriter.write("\n");
                myWriter.write(selectedBook.getImgSrc());
                myWriter.write("\n");
                myWriter.write(String.valueOf(countToAdd));
                myWriter.write("\n");
            }
            showAlert1("عملیات موفقیت‌آمیز", "محصول به سبد خرید شما اضافه شد!");
        } else {
            showAlert("خطا", "تعداد مورد نظر بیشتر از موجودی است!");
        }
    }
    updateQuantityInCart();
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
    @FXML
    void SearchBtn(MouseEvent event) {
        String search = SearchTxt.getText().trim();
        boolean found = false;

        for (Book book : allBooks) {
            if (book.getName().toLowerCase().contains(search.toLowerCase())) {
                setChosenBook(book);
                found = true;
                break;
            }
        }

        if (!found) {
            bookNameLable.setText("کتاب یافت نشد!");
            bookPriceLabel.setText("0.0");
            writerLab.setText("");
            translatorLab.setText("");
            nasherLab.setText("");
            countLab.setText("");
            CategoryLab.setText("");
            Image Image = new Image(getClass().getResourceAsStream("/img/broken-image.png"));
            bookImg.setImage(Image);
            chosenBookCard.setStyle("-fx-background-color: #868686;\n    -fx-background-radius: 30;");
            bookFound = false;
        }
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @FXML
    void decreaseBtn(ActionEvent event) {
        int currentNumber = Integer.parseInt((countLabel.getText()));

        if (currentNumber > 1) {
            currentNumber--;
            countLabel.setText(String.valueOf(currentNumber));
            calculateTotal();
        }
    }

    @FXML
    void increaseBtn(ActionEvent event) {
        int currentNumber = Integer.parseInt( countLabel.getText());
        currentNumber++;
        countLabel.setText(String.valueOf(currentNumber));
        calculateTotal();
    }

    @FXML
    void calculateTotal() {

            if (selectedBook == null) {
                bookPriceLabel.setText("خطا!");
                return;
            }

            double price = Double.parseDouble(selectedBook.getPrice());

            int currentNumber = Integer.parseInt(countLabel.getText());

            double totalAmount = price * currentNumber;

            bookPriceLabel.setText(Main.CURRENCY + totalAmount);
        }


    private double extractPrice(String priceText) {

        String numericPrice = priceText.replaceAll("[^\\d.]", "");


        return Double.parseDouble(numericPrice);
    }

    @FXML
    void LodAdmin(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Admin.fxml"));
        rootPane.getChildren().setAll(pane);
    }


    private void updateQuantityInCart() {
        String id = lblid.getText();
        File fileName = new File(id + ".txt");

        if (!fileName.exists()) {
            return; // اگر فایل وجود نداشت، هیچ تغییری ایجاد نکن
        }

        int lineCount = 0;

        try (Scanner scanner = new Scanner(fileName)) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        if (lineCount <= 1) {
            return; // اگر فقط یک خط بود، یعنی محصولی وجود ندارد، پس نمایش نده
        }

        int productCount = lineCount / 4; // محاسبه تعداد محصولات
        QuantityInCart.setText(String.valueOf(productCount));
    }




}



