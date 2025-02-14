package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import model.Cart;
import model.Report;

import java.io.IOException;

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
        namelab.setText(report.getName());
        pricelab.setText(report.getPrice());
        Image image = new Image(getClass().getResourceAsStream(report.getImgSrc()));
        img.setImage(image);
        numlab.setText(report.getCount());
        datelab.setText(report.getDate());

        calculateProfit();
    }
    private void calculateProfit() {
        String priceText = pricelab.getText();
        String countText = numlab.getText();

        String numericPrice = priceText.replaceAll("[^\\d.]", "");

        try {
            double price = Double.parseDouble(numericPrice);
            int count = Integer.parseInt(countText);

            double profit = price/count;

            profitlab.setText(String.format( profit + Main.CURRENCY));
        } catch (NumberFormatException e) {
            profitlab.setText("خطا");
            e.printStackTrace();
        }
    }
    private static String lastSearchedItem; // ذخیره‌سازی نام کالا


    @FXML
    void buyAgain(MouseEvent event) {
        try {
            // گرفتن نام کتاب از Label
            String bookName = namelab.getText().trim();

            // بارگذاری info.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/info.fxml"));
            Parent root = loader.load();

            // دریافت کنترلر صفحه info
            infoController infoController = loader.getController();
            infoController.setBookName(bookName); // ارسال نام کتاب

            // نمایش صفحه جدید
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Info");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // متد کمکی برای دسترسی به مقدار ذخیره شده
    public static String getLastSearchedItem() {
        return lastSearchedItem;
    }
}
