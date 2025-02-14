package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.SharedData;
import model.favorit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Item1Controller {
    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane itemPane;

    private Node itemNode;  // مرجع به نود

    public Node getItemNode() {
        return itemNode;  // بازگشت نود
    }

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    private model.favorit favorit;

    private ProfileController ProfileController;

    @FXML
    void openInfo(MouseEvent event) {
        try {
            // مقدار نام کتاب را به InfoController ارسال کنید
            infoController.bookName = nameLabel.getText();

            // بارگذاری FXML جدید
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/info.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setData1(favorit favorit, ProfileController profileController) {
        this.favorit =favorit;
        this.ProfileController = profileController;  // مقداردهی ProfileController
        nameLabel.setText(favorit.getName());
        priceLable.setText(favorit.getPrice());
        Image image = new Image(getClass().getResourceAsStream(favorit.getImgSrc()));
        img.setImage(image);
        this.itemNode = itemPane; // فرض کنید که itemPane نمای اصلی است که به آن نیاز دارید
    }

    @FXML
    void Delet(MouseEvent event) {
        String currentUsername = SharedData.getInstance().getUsername();
        String bookNameToDelete = nameLabel.getText().trim(); // گرفتن نام کتاب از لیبل

        if (bookNameToDelete.isEmpty()) {
            showAlert("خطا", "نام کتاب مشخص نشده است!", Alert.AlertType.ERROR);
            return;
        }

        String fileName =  currentUsername + "-favorit.txt";
        File inputFile = new File(fileName);
        File tempFile = new File("favorit_temp.txt");

        boolean bookFound = false; // برای بررسی اینکه کتاب پیدا شده یا نه

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            List<String> fileLines = new ArrayList<>();

            // خواندن کل فایل و ذخیره در لیست
            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }

            // بازنویسی فایل بدون کتابی که باید حذف شود
            for (int i = 0; i < fileLines.size(); i++) {
                if (fileLines.get(i).trim().equals(bookNameToDelete)) {
                    bookFound = true;
                    i += 2; // پرش به بعد از اطلاعات این کتاب
                } else {
                    writer.write(fileLines.get(i) + System.lineSeparator());
                }
            }

        } catch (IOException e) {
            showAlert("خطا", "مشکلی در خواندن/نوشتن فایل به وجود آمده است!", Alert.AlertType.ERROR);
            return;
        }

        // اگر کتاب پیدا نشد، فایل اصلی را تغییر نده
        if (!bookFound) {
            showAlert("نامعتبر", "کتاب موردنظر در لیست وجود ندارد!", Alert.AlertType.WARNING);
            tempFile.delete();
            return;
        }

        // جایگزینی فایل اصلی با فایل جدید بدون کتاب حذف‌شده
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            showAlert("خطا", "مشکلی در حذف فایل اصلی به وجود آمده است!", Alert.AlertType.ERROR);
            return;
        }
        // حذف آیتم از Grid یا نمای فعلی
        if (this.ProfileController != null) {
            this.ProfileController.removeItemFromGrid(this);  // حذف آیتم از گرید
        }

        // پیام موفقیت
        showAlert("موفقیت", "کتاب '" + bookNameToDelete + "' با موفقیت حذف شد!", Alert.AlertType.INFORMATION);
        // به‌روزرسانی صفحه با داده‌های جدید
    }

    // متد کمکی برای نمایش `Alert`
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
