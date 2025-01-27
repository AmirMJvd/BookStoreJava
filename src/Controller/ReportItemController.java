package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import model.Cart;
import model.Report;

public class ReportItemController {
    @FXML
    private Label datelab;

    @FXML
    private ImageView img;

    @FXML
    private Label namelab;

    @FXML
    private Label numlab;

    @FXML
    private Label pricelab;

    private Cart cart;

    private AdminController AdminController;

    @FXML
    private Label profitlab;

    private Report report;

    public void setData(Report report) {
        this.report = report;
//        this.AdminController = AdminController;
        namelab.setText(report.getName());
        pricelab.setText(report.getPrice());
        Image image = new Image(getClass().getResourceAsStream(report.getImgSrc()));
        img.setImage(image);
        numlab.setText(report.getCount());
        datelab.setText(report.getDate());

        calculateProfit(); // محاسبه سود و نمایش در profitlab
    }
    private void calculateProfit() {
        String priceText = pricelab.getText();

        // حذف تمامی کاراکترهای غیرعددی
        String numericPrice = priceText.replaceAll("[^\\d.]", "");

        try {
            // تبدیل رشته به عدد
            double price = Double.parseDouble(numericPrice);

            // محاسبه 10 درصد از قیمت
            double profit = price * 0.10;

            // تنظیم مقدار محاسبه‌شده در profitlab
            profitlab.setText(String.format( Main.CURRENCY + profit));
        } catch (NumberFormatException e) {
            // اگر خطایی در تبدیل قیمت رخ داد
            profitlab.setText("خطا");
            e.printStackTrace();
        }
    }
}
