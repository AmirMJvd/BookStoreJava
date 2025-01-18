package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.FileWriter;
import java.io.IOException;

public class AdminController {


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
    void CameBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/market.fxml"));;
        AdminPane.getChildren().setAll(pane);
    }

    @FXML
    void registration(ActionEvent event)  {
            try {
                // بررسی اینکه هیچ فیلدی خالی نباشد
                if (bookName.getText().isEmpty() ||  ImgAdr.getText().isEmpty() || Color.getText().isEmpty()||
                priceTextField.getText().isEmpty() ) {
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
    }




