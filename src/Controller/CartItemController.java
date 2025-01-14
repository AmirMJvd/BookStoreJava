package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import main.MyListener;
import model.Book;
import model.Cart;

public class CartItemController {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    private Cart cart;

    public void setData(Cart cart) {
        this.cart = cart;
        nameLabel.setText(cart.getName());
        priceLable.setText(cart.getPrice());
        Image image = new Image(getClass().getResourceAsStream(cart.getImgSrc()));
        img.setImage(image);
    }

}