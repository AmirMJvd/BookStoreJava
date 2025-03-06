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

import static main.Main.CURRENCY;


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
    private Label BasePrice;


    private Cart cart;

    private cartController cartController;
    private NewCartController newCartController;
    private String imagePath; // متغیر برای ذخیره مسیر تصویر



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


    @FXML
    void setData(Cart cart) throws IOException {
        this.cart = cart;
        nameLabel.setText(cart.getName());

        // گرفتن نام کاربری از SharedData
        String username = SharedData.getInstance().getUsername();

        // خواندن فایل مربوط به کاربر
        FileReader myReader = new FileReader(username + ".txt");
        Scanner scanner = new Scanner(myReader);

        String bookName = nameLabel.getText(); // نام کتاب از nameLabel گرفته می‌شود
        List<String> lines = new ArrayList<>();

        boolean found = false;

        // جستجو در فایل برای پیدا کردن کتاب
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // زمانی که نام کتاب پیدا شد
            if (found) {
                if (lines.size() == 0) {
                    // خط اول بعد از پیدا کردن کتاب: قیمت کالا (Price)
                    priceLable.setText(line); // قیمت را در priceLable قرار می‌دهیم
                } else if (lines.size() == 1) {
                    // خط دوم بعد از پیدا کردن کتاب: آدرس تصویر
                     imagePath = line;
                    // بررسی صحت مسیر تصویر
                    if (getClass().getResourceAsStream(imagePath) != null) {
                        Image image = new Image(getClass().getResourceAsStream(imagePath));
                        img.setImage(image); // تصویر را بارگذاری و نمایش می‌دهیم
                    } else {
                        System.out.println("خطا: تصویر یافت نشد. مسیر نادرست است: " + imagePath);
                    }
                } else if (lines.size() == 2) {
                    // خط سوم بعد از پیدا کردن کتاب: تعداد کالا (Quantity)
                    Numberlabel.setText(line); // تعداد را در Numberlabel قرار می‌دهیم
                    break; // تمام اطلاعات خوانده شد، از حلقه خارج می‌شویم
                }

                lines.add(line); // ذخیره خط‌های جدید برای بعداً استفاده
            }

            if (line.equals(bookName)) {
                found = true; // زمانی که کتاب پیدا شد، flag را فعال می‌کنیم
                lines.clear(); // لیست خطوط را پاک می‌کنیم تا به‌طور جدید مقادیر را ذخیره کنیم
            }
        }

        scanner.close();
        myReader.close();

        // محاسبه قیمت پایه
        double currentPrice = extractPrice(priceLable.getText());
        int currentCount = Integer.parseInt(Numberlabel.getText());

        // محاسبه قیمت پایه و ذخیره آن در BasePrice
        double basePrice = currentPrice / currentCount;
        BasePrice.setText(String.valueOf(basePrice));

        // به‌روزرسانی اطلاعات در CartController
        if (cartController != null) {
            cartController.refreshCart();
        }

        if (newCartController != null) {
            newCartController.refreshCart();
        }
    }




    public void setCartController(cartController cartController) {
        this.cartController = cartController;
    }

    public void setNewCartController(NewCartController cartController) {
        this. newCartController = cartController;
    }

    @FXML
    void Delet(ActionEvent event) throws IOException {
        String username = SharedData.getInstance().getUsername();
        FileReader myReader = new FileReader(username +".txt");
        Scanner scanner = new Scanner(myReader);

        String bookName = nameLabel.getText();

        List<String> linesToKeep = new ArrayList<>();


        boolean found = false;
        int skipLines = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (found && skipLines > 0) {
                skipLines--;
                continue;
            }

            if (line.equals(bookName)) {
                found = true;
                skipLines = 3;
                continue;
            }
            linesToKeep.add(line);
        }

        scanner.close();
        myReader.close();

        FileWriter myWriter = new FileWriter(username+".txt", false);
        for (String line : linesToKeep) {
            myWriter.write(line + System.lineSeparator());
        }
        myWriter.close();

        if (cartController != null) {
            cartController.refreshCart();
        }

        if (newCartController != null) {
            newCartController.refreshCart();
        }
    }

    @FXML
    void decreaseBtn(ActionEvent event) throws IOException {
        int currentNumber = Integer.parseInt(Numberlabel.getText());

        // کاهش تعداد
        if (currentNumber > 1) {
            currentNumber--;
            Numberlabel.setText(String.valueOf(currentNumber));
        } else if (currentNumber == 1) {
            currentNumber--;
            Numberlabel.setText("0");
            Delet(event); // حذف محصول وقتی تعداد به صفر رسید
        }

        // محاسبه تغییر در قیمت
        double basePrice = extractPrice(BasePrice.getText());
        double priceDifference = basePrice * -1; // کاهش قیمت

        // نمایش قیمت جدید
        calculateTotal(currentNumber, priceDifference);

        // ذخیره تغییرات جدید در فایل
        saveChangesToFile(currentNumber, priceLable.getText());
    }

    @FXML
    void increaseBtn(ActionEvent event) throws IOException {
        int currentNumber = Integer.parseInt(Numberlabel.getText());

        // افزایش تعداد
        currentNumber++;
        Numberlabel.setText(String.valueOf(currentNumber));

        // محاسبه تغییر در قیمت
        double basePrice = extractPrice(BasePrice.getText());
        double priceDifference = basePrice; // افزایش قیمت

        // نمایش قیمت جدید
        calculateTotal(currentNumber, priceDifference);

        // ذخیره تغییرات جدید در فایل
        saveChangesToFile(currentNumber, priceLable.getText());
    }

    @FXML
    void calculateTotal(int currentNumber, double priceDifference) {
        // محاسبه مبلغ کل جدید
        double totalAmount = priceDifference * currentNumber;

        // جلوگیری از نمایش مقدار منفی
        totalAmount = Math.abs(totalAmount);

        // نمایش قیمت جدید در priceLable
        priceLable.setText( totalAmount + CURRENCY );
    }
    // متد برای استخراج عدد از قیمت
    private double extractPrice(String priceText) {
        // فرض می‌کنیم که قیمت با واحدی مثل "تومان" همراه است
        // حذف تمامی کاراکترهای غیر عددی از قیمت (مثلاً "تومان")
        String numericPrice = priceText.replaceAll("[^\\d.]", "");

        // تبدیل رشته عددی به مقدار عددی (double)
        return Double.parseDouble(numericPrice);
    }
    // متد برای ذخیره تغییرات در فایل
    // متد برای ذخیره تغییرات در فایل
    private void saveChangesToFile(int newQuantity, String newPrice) throws IOException {
        String username = SharedData.getInstance().getUsername();
        FileReader myReader = new FileReader(username + ".txt");
        Scanner scanner = new Scanner(myReader);

        String bookName = nameLabel.getText(); // نام کتاب از nameLabel گرفته می‌شود
        List<String> linesToKeep = new ArrayList<>();
        boolean found = false;
        int skipLines = 0;

        // خواندن تمام اطلاعات از فایل و ذخیره تغییرات
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (found && skipLines > 0) {
                skipLines--;
                continue;
            }

            if (line.equals(bookName)) {
                found = true;
                skipLines = 3; // حذف 3 خط بعد از یافتن کتاب (قیمت، تصویر، تعداد)
                linesToKeep.add(bookName); // اضافه کردن نام کتاب
                linesToKeep.add(newPrice); // اضافه کردن قیمت جدید
                linesToKeep.add(imagePath); // اضافه کردن مسیر تصویر
                linesToKeep.add(String.valueOf(newQuantity)); // اضافه کردن تعداد جدید
                continue;
            }

            linesToKeep.add(line); // ذخیره سایر خطوط بدون تغییر
        }

        scanner.close();
        myReader.close();

        // نوشتن تغییرات در فایل
        FileWriter myWriter = new FileWriter(username + ".txt", false);
        for (String line : linesToKeep) {
            myWriter.write(line + System.lineSeparator());
        }
        myWriter.close();
    }
}