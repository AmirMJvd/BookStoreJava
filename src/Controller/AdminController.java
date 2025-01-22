package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import model.Cart;
import model.Report;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AdminController implements Initializable {


    @FXML
    private AnchorPane AdminPane;

    @FXML
    private TextField Color;

    @FXML
    private TextField ImgAdr;

    @FXML
    private TextField author;

    @FXML
    private TextField bookName;

    @FXML
    private TextField count;

    @FXML
    private TextField nasher;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField translator;

    @FXML
    private TextField Category;


    @FXML
    private Label Report;

    @FXML
    private GridPane grid;

    @FXML
    void CameBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/market.fxml"));
        ;
        AdminPane.getChildren().setAll(pane);
    }

    @FXML
    void registration(ActionEvent event) {
        try {
            // بررسی اینکه هیچ فیلدی خالی نباشد
            if (bookName.getText().isEmpty() || ImgAdr.getText().isEmpty() || Color.getText().isEmpty() ||
                    priceTextField.getText().isEmpty()) {
                Report.setText("لطفاً همه فیلدها را پر کنید!");
                return;
            }

            // بررسی مقدار عددی بودن فیلدهای قیمت و تعداد
            int price;
            int countValue;
            try {
                price = Integer.parseInt(priceTextField.getText());
                countValue = Integer.parseInt(count.getText());
            } catch (NumberFormatException e) {
                Report.setText("قیمت و تعداد باید عدد باشند!");
                return;
            }

            // نوشتن داده‌ها در فایل
            FileWriter myWriter = new FileWriter("BookInf.txt", true);

            myWriter.write(bookName.getText());
            myWriter.write("\n");

            myWriter.write(priceTextField.getText());
            myWriter.write("\n");

            myWriter.write(ImgAdr.getText());
            myWriter.write("\n");

            myWriter.write(Color.getText());
            myWriter.write("\n");

            myWriter.write(author.getText());
            myWriter.write("\n");

            myWriter.write(translator.getText());
            myWriter.write("\n");

            myWriter.write(nasher.getText());
            myWriter.write("\n");

            myWriter.write(count.getText());
            myWriter.write("\n");

            myWriter.write(Category.getText());
            myWriter.write("\n");

            myWriter.close();

            Report.setText("محصول با موفقیت ثبت شد!");
            clearFields(); // پاک کردن فیلدها

        } catch (IOException e) {
            Report.setText("خطا در ذخیره اطلاعات!");
            e.printStackTrace();
        }
    }

    // متد برای پاک کردن فیلدهای ورودی پس از ثبت اطلاعات
    private void clearFields() {
        bookName.clear();
        author.clear();
        translator.clear();
        nasher.clear();
        ImgAdr.clear();
        Color.clear();
        priceTextField.clear();
        count.clear();
    }


    private List<model.Report> reports = new ArrayList<>();
    private List<Report> getReportData() {
        List<Report> reports = new ArrayList<>();
        try {
            File cartFile = new File("Report.txt");
            Scanner reader = new Scanner(cartFile);
            while (reader.hasNextLine()) {
                Report report = new Report();
                report.setName(reader.nextLine());
                report.setPrice(reader.nextLine());
                report.setImgSrc(reader.nextLine());
                report.setCount(reader.nextLine());
                report.setDate(reader.nextLine());
                reports.add(report);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public void initialize(URL location, ResourceBundle resources) {
        reports.addAll(getReportData());
        int row = 1;
        int column = 0;
        try {
            for (Report report : reports) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/report.fxml"));
                HBox HBox = fxmlLoader.load();

                ReportItemController cartItemController = fxmlLoader.getController();
                cartItemController.setData(report);

                if (column == 2) {
                    column = 0;
                    row++;
                }
                grid.add(HBox,column++,row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(HBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




