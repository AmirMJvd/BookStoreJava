package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Cart;
import javafx.event.ActionEvent;
import model.SharedData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CartItemController {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private Label Numberlabel;


    private Cart cart;

    private cartController cartController;



/*    @FXML
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


*/
    public void setData(Cart cart) {
        this.cart = cart;
//        this.cartController = cartController;
        nameLabel.setText(cart.getName());
        priceLable.setText(cart.getPrice());
        Image image = new Image(getClass().getResourceAsStream(cart.getImgSrc()));
        img.setImage(image);
        Numberlabel.setText(cart.getCount());
    }

    public void setCartController(cartController cartController) {
        this.cartController = cartController;
    }

    @FXML
    void Delet(ActionEvent event) throws IOException {
        String username = SharedData.getInstance().getUsername();
        // فایل CartInf را می‌خوانیم
        FileReader myReader = new FileReader(username +".txt");
        Scanner scanner = new Scanner(myReader);

        // لیبل نام کتاب را دریافت می‌کنیم
        String bookName = nameLabel.getText();

        // لیستی برای نگهداری خطوط باقی‌مانده
        List<String> linesToKeep = new ArrayList<>();

        // مشخص‌کننده اینکه آیا کتاب مورد نظر پیدا شده است
        boolean found = false;
        int skipLines = 0;

        // خواندن فایل خط به خط
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (found && skipLines > 0) {
                // اگر کتاب پیدا شده و باید خطوط بعدی را حذف کنیم
                skipLines--;
                continue;
            }

            if (line.equals(bookName)) {
                // اگر خط فعلی برابر نام کتاب است، شروع حذف
                found = true;
                skipLines = 3; // سه خط بعدی را حذف کنیم
                continue;
            }

            // اگر خطی برای حذف نیست، آن را به لیست اضافه می‌کنیم
            linesToKeep.add(line);
        }

        scanner.close();
        myReader.close();

        // بازنویسی فایل با خطوط باقی‌مانده
        FileWriter myWriter = new FileWriter(username+".txt", false);
        for (String line : linesToKeep) {
            myWriter.write(line + System.lineSeparator());
        }
        myWriter.close();

        // بازخوانی داده‌ها و به‌روزرسانی GridPane
        if (cartController != null) {
            cartController.refreshCart();
        }



    }
}