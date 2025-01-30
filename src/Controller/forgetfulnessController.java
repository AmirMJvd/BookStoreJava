package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class forgetfulnessController {

    @FXML
    private TextField EmailAdd;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField userName1;

    @FXML
    void BackLogin(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void Reset(ActionEvent event) throws IOException {
        // دریافت اطلاعات از تکست فیلدها
        String userName = userName1.getText().trim();
        String phoneNumber = PhoneNumber.getText().trim();
        String email = EmailAdd.getText().trim();

        // بررسی اینکه آیا شماره تلفن یا ایمیل وارد شده است
        if (phoneNumber.isEmpty() && email.isEmpty()) {
            showAlert("لطفاً شماره تلفن یا ایمیل را وارد کنید.");
            return;
        }

        // خواندن فایل کاربران
        List<String> users = readUsersFromFile("user.txt");

        boolean found = false;

        for (int i = 0; i < users.size(); i += 5) {
            String currentUserName = users.get(i);           // نام کاربری
            String currentPassword = users.get(i + 1);       // رمز عبور
            String currentPhoneNumber = users.get(i + 2);   // شماره تلفن
            String currentEmail = users.get(i + 3);         // ایمیل

            // بررسی نام کاربری
            if (currentUserName.equals(userName)) {
                // اگر شماره تلفن وارد شده است
                if (!phoneNumber.isEmpty() && currentPhoneNumber.equals(phoneNumber)) {
                    showAlert("رمز عبور شما: " + currentPassword);
                    found = true;
                    break;
                }
                // اگر ایمیل وارد شده است
                if (!email.isEmpty() && currentEmail.equals(email)) {
                    showAlert("رمز عبور شما: " + currentPassword);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            showAlert("اطلاعات وارد شده صحیح نیست.");
        }
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    // متدی برای خواندن فایل کاربران
    private List<String> readUsersFromFile(String filePath) {
        List<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // متدی برای نمایش آلارم
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("پیغام");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
