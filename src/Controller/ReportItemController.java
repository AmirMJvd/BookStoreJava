package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    }
}
