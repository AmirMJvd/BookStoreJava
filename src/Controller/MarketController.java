package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.Main;
import main.MyListener;
import model.Fruit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private Label writer;

    @FXML
    private AnchorPane rootPane;

    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private List<Fruit> getData() {
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;

        fruit = new Fruit();
        fruit.setName("مزرعه حیوانات");
        fruit.setPrice(99);
        fruit.setImgSrc("/img/مزرعه حیوانات.jpg");
        fruit.setColor("7E99A3");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("بازگشت شاه");
        fruit.setPrice(50);
        fruit.setImgSrc("/img/بازگشت شاه.png");
        fruit.setColor("055052");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("تلماسه");
        fruit.setPrice(80);
        fruit.setImgSrc("/img/تلماسه.jpg");
        fruit.setColor("355C7D");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("برفک");
        fruit.setPrice(60);
        fruit.setImgSrc("/img/برفک.jpg");
        fruit.setColor("0D7377");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("بر باد رفته");
        fruit.setPrice(40);
        fruit.setImgSrc("/img/بر باد رفته.jpg");
        fruit.setColor("AAAAAA");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("اینده ذهن");
        fruit.setPrice(70);
        fruit.setImgSrc("/img/اینده ذهن.jpg");
        fruit.setColor("D9ADAD");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("انقراض ششم");
        fruit.setPrice(45);
        fruit.setImgSrc("/img/انقراض ششم.jpg");
        fruit.setColor("917FB3");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("خوشه های خشم");
        fruit.setPrice(55);
        fruit.setImgSrc("/img/خوشه های خشم.jpg");
        fruit.setColor("5F060E");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("فارنهایت 451");
        fruit.setPrice(140);
        fruit.setImgSrc("/img/فارنهایت.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("هری پاتر");
        fruit.setPrice(74);
        fruit.setImgSrc("/img/هری پاتر.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("پرتقال کوکی");
        fruit.setPrice(85);
        fruit.setImgSrc("/img/پرتقال کوکی.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("کتابخانه نیمه شب");
        fruit.setPrice(95);
        fruit.setImgSrc("/img/کتابخانه نیمه شب.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("گتسبی بزرگ");
        fruit.setPrice(56);
        fruit.setImgSrc("/img/گتسبی بزرگ.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("یاران حلقه");
        fruit.setPrice(46);
        fruit.setImgSrc("/img/یاران حلقه.png");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("تبار انسان");
        fruit.setPrice(25);
        fruit.setImgSrc("/img/تبار انسان.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("جهان زیبا");
        fruit.setPrice(66);
        fruit.setImgSrc("/img/جهان زیبا.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("جهان های موازی");
        fruit.setPrice(42);
        fruit.setImgSrc("/img/جهان های موازی.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("نظم زمان");
        fruit.setPrice(43);
        fruit.setImgSrc("/img/نظم زمان.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("چه میشود اگر");
        fruit.setPrice(75);
        fruit.setImgSrc("/img/چه میشود اگر.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("چیستی تکامل");
        fruit.setPrice(86);
        fruit.setImgSrc("/img/چیستی تکامل.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("راهنمای کهکشان");
        fruit.setPrice(95);
        fruit.setImgSrc("/img/راهنمای کهکشان.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("اودیسه فضایی");
        fruit.setPrice(83);
        fruit.setImgSrc("/img/اودیسه فضایی.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("تبصره 22");
        fruit.setPrice(56);
        fruit.setImgSrc("/img/تبصره 22.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("خشم و هیاهو");
        fruit.setPrice(23);
        fruit.setImgSrc("/img/خشم و هیاهو.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("خانم دالاوی");
        fruit.setPrice(43);
        fruit.setImgSrc("/img/خانم دالاوی.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("ناطور دشت");
        fruit.setPrice(122);
        fruit.setImgSrc("/img/ناطور دشت.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("بنیاد");
        fruit.setPrice(69);
        fruit.setImgSrc("/img/بنیاد.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        fruit = new Fruit();
        fruit.setName("ماشین زمان");
        fruit.setPrice(99);
        fruit.setImgSrc("/img/ماشین زمان.jpg");
        fruit.setColor("E7C00F");
        fruits.add(fruit);

        return fruits;
    }

    private void setChosenFruit(Fruit fruit) {
        fruitNameLable.setText(fruit.getName());
        fruitPriceLabel.setText(Main.CURRENCY + fruit.getPrice());
        writer.setText(fruit.getWriter());
        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fruits.addAll(getData());
        if (fruits.size() > 0) {
            setChosenFruit(fruits.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Fruit fruit) {
                    setChosenFruit(fruit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < fruits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i),myListener);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void LoadCart(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Cart.fxml"));;
        rootPane.getChildren().setAll(pane);

    }


}
