package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.MyListener;
import model.Book;
import model.BookLists;
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
    private AnchorPane rootPane;

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
/*        book = new Book();
        book.setName("بازگشت شاه");
        book.setPrice(50);
        book.setImgSrc("/img/بازگشت شاه.png");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("تلماسه");
        book.setPrice(80);
        book.setImgSrc("/img/تلماسه.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("برفک");
        book.setPrice(60);
        book.setImgSrc("/img/برفک.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("بر باد رفته");
        book.setPrice(40);
        book.setImgSrc("/img/بر باد رفته.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("اینده ذهن");
        book.setPrice(70);
        book.setImgSrc("/img/اینده ذهن.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("انقراض ششم");
        book.setPrice(45);
        book.setImgSrc("/img/انقراض ششم.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("خوشه های خشم");
        book.setPrice(55);
        book.setImgSrc("/img/خوشه های خشم.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("فارنهایت 451");
        book.setPrice(140);
        book.setImgSrc("/img/فارنهایت.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("هری پاتر");
        book.setPrice(74);
        book.setImgSrc("/img/هری پاتر.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("پرتقال کوکی");
        book.setPrice(85);
        book.setImgSrc("/img/پرتقال کوکی.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("کتابخانه نیمه شب");
        book.setPrice(95);
        book.setImgSrc("/img/کتابخانه نیمه شب.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("گتسبی بزرگ");
        book.setPrice(56);
        book.setImgSrc("/img/گتسبی بزرگ.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("یاران حلقه");
        book.setPrice(46);
        book.setImgSrc("/img/یاران حلقه.png");
        book.setColor("7E99A3");
        allBooks.add(book);
        book = new Book();
        book.setName("تبار انسان");
        book.setPrice(25);
        book.setImgSrc("/img/تبار انسان.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("جهان زیبا");
        book.setPrice(66);
        book.setImgSrc("/img/جهان زیبا.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("جهان های موازی");
        book.setPrice(42);
        book.setImgSrc("/img/جهان های موازی.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("نظم زمان");
        book.setPrice(43);
        book.setImgSrc("/img/نظم زمان.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("چه میشود اگر");
        book.setPrice(75);
        book.setImgSrc("/img/چه میشود اگر.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("چیستی تکامل");
        book.setPrice(86);
        book.setImgSrc("/img/چیستی تکامل.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("راهنمای کهکشان");
        book.setPrice(95);
        book.setImgSrc("/img/راهنمای کهکشان.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("اودیسه فضایی");
        book.setPrice(83);
        book.setImgSrc("/img/اودیسه فضایی.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("تبصره 22");
        book.setPrice(56);
        book.setImgSrc("/img/تبصره 22.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("خشم و هیاهو");
        book.setPrice(23);
        book.setImgSrc("/img/خشم و هیاهو.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("خانم دالاوی");
        book.setPrice(43);
        book.setImgSrc("/img/خانم دالاوی.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("ناطور دشت");
        book.setPrice(122);
        book.setImgSrc("/img/ناطور دشت.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("بنیاد");
        book.setPrice(69);
        book.setImgSrc("/img/بنیاد.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);

        book = new Book();
        book.setName("ماشین زمان");
        book.setPrice(99);
        book.setImgSrc("/img/ماشین زمان.jpg");
        book.setColor("7E99A3");
        allBooks.add(book);


*/

//        return allBooks;


    private void setChosenFruit(Book book) {
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
            setChosenFruit(allBooks.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Book fruit) {
                    setChosenFruit(fruit);
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

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(10));
            }

            for (int i = 0; i < scientificBooks.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController2 = fxmlLoader.getController();
                itemController2.setData(scientificBooks.get(i), myListener);

                scientificGrid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                scientificGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                scientificGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                scientificGrid.setMaxWidth(Region.USE_PREF_SIZE);
                //set grid height
                scientificGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                scientificGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                scientificGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }

            for (int i = 0; i < politicalBooks.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController2 = fxmlLoader.getController();
                itemController2.setData(politicalBooks.get(i), myListener);

                politicalGrid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                politicalGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                politicalGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                politicalGrid.setMaxWidth(Region.USE_PREF_SIZE);
                //set grid height
                politicalGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                politicalGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                politicalGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }

            for (int i = 0; i < psychologyBooks.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController2 = fxmlLoader.getController();
                itemController2.setData(psychologyBooks.get(i), myListener);

                psychologyGrrid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                psychologyGrrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setMaxWidth(Region.USE_PREF_SIZE);
                //set grid height
                psychologyGrrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                psychologyGrrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void LoadCart(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Cart.fxml"));
        ;
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void AdminLoad(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Admin.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    void pressbtnvorud(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../views/signin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 971, 770);
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
            // اگر کتابی پیدا نشده باشد، هیچ کاری انجام نمی‌دهیم
            bookPriceLabel.setText("خطا !");
            return;
        }
        FileWriter myWriter = new FileWriter("CartInf.txt", true);

        myWriter.write(bookNameLable.getText());
        myWriter.write("\n");

        myWriter.write(bookPriceLabel.getText());
        myWriter.write("\n");

        myWriter.write(selectedBook.getImgSrc());
        myWriter.write("\n");

        myWriter.close();

    }
    @FXML
    void SearchBtn(ActionEvent event) {
        String search = SearchTxt.getText().trim(); // دریافت متن جستجو شده
        boolean found = false;

        // جستجوی کتاب مورد نظر در لیست همه کتاب‌ها
        for (Book book : allBooks) {
            if (book.getName().equalsIgnoreCase(search)) { // مقایسه نام کتاب به صورت غیرحساس به حروف بزرگ و کوچک
                setChosenFruit(book); // تنظیم کتاب انتخاب شده
                found = true;
                break; // خروج از حلقه بعد از پیدا کردن کتاب
            }
        }

        if (!found) {
            // اگر کتابی پیدا نشد، نمایش پیام به کاربر
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
}



