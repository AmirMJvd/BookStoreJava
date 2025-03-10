package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import main.MyListener;
import model.Book;
import model.Report;
import model.favorit;

import java.io.IOException;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    private favorit favorit;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(book);
    }

    private Book book;
    private MyListener myListener;

    public void setData(Book book, MyListener myListener) {
        this.book = book;
        this.myListener = myListener;
        nameLabel.setText(book.getName());
        priceLable.setText( book.getPrice() + Main.CURRENCY );
        Image image = new Image(getClass().getResourceAsStream(book.getImgSrc()));
        img.setImage(image);
    }
    @FXML
    void openInfo(MouseEvent event) {
//        try {
//            // مقدار نام کتاب را به InfoController ارسال کنید
//            infoController.bookName = nameLabel.getText();
//
//            // بارگذاری FXML جدید
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/info.fxml"));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(fxmlLoader.load()));
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void setData1(favorit favorit) {
        this.favorit =favorit;
        nameLabel.setText(favorit.getName());
        priceLable.setText(favorit.getPrice()+ Main.CURRENCY );
        Image image = new Image(getClass().getResourceAsStream(favorit.getImgSrc()));
        img.setImage(image);
    }


}