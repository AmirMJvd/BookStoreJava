package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Cart;
import javafx.event.ActionEvent;

import java.util.List;


public class CartItemController {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private Label Numberlabel;

    @FXML
    private Label Number;

    private Cart cart;

    private cartController cartController;



    @FXML
    void decrease(ActionEvent event)  {
        int currentNumber = Integer.parseInt((Numberlabel.getText()));

        if (currentNumber > 1) {
            currentNumber--;
            Numberlabel.setText(String.valueOf(currentNumber));
            calculateTotal();
        }
    }

    @FXML
    void increase(ActionEvent event) {
        int currentNumber = Integer.parseInt(Numberlabel.getText());
        currentNumber++;
        Numberlabel.setText(String.valueOf(currentNumber));
        calculateTotal();

    }

    @FXML
    void calculateTotal() {
        // گرفتن قیمت به عنوان رشته از priceLable
        String priceText = cart.getPrice();  // استفاده از getPrice از Cart

        // استخراج بخش عددی قیمت (مثلاً حذف "تومان" از انتهای رشته)
        double price = extractPrice(priceText);

        // گرفتن تعداد از Numberlabel
        int currentNumber = Integer.parseInt(Numberlabel.getText());


        // محاسبه مجموع مبلغ محصول خاص
        double totalAmount = price * currentNumber;

        // نمایش مجموع مبلغ محصول خاص در Number
        Number.setText("مجموع مبلغ: " + totalAmount);


    }

    // متد برای استخراج عدد از قیمت
    private double extractPrice(String priceText) {
        // فرض می‌کنیم که قیمت با واحدی مثل "تومان" همراه است
        // حذف تمامی کاراکترهای غیر عددی از قیمت (مثلاً "تومان")
        String numericPrice = priceText.replaceAll("[^\\d.]", "");

        // تبدیل رشته عددی به مقدار عددی (double)
        return Double.parseDouble(numericPrice);
    }



    public void setData(Cart cart) {
        this.cart = cart;
        this.cartController = cartController;
        nameLabel.setText(cart.getName());
        priceLable.setText(cart.getPrice());
        Image image = new Image(getClass().getResourceAsStream(cart.getImgSrc()));
        img.setImage(image);
        calculateTotal();
    }



}