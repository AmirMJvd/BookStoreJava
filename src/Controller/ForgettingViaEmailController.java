package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.*;

public class ForgettingViaEmailController {

    @FXML
    private TextField VerificationCode;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordAgn;

    @FXML
    private TextField userName1;


    @FXML
    private AnchorPane rootPane;

    private static final String USER_FILE = "user.txt";

    @FXML
    public void initialize() {
        userName1.setOnAction(event -> VerificationCode.requestFocus());
        VerificationCode.setOnAction(event -> password.requestFocus());
        password.setOnAction(event -> passwordAgn.requestFocus());
        passwordAgn.setOnAction(event -> {
            try {
                Reset(new ActionEvent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void BackLogin(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void Reset(ActionEvent event) throws IOException {
        String enteredCode = VerificationCode.getText().trim();
        String newPassword = password.getText().trim();
        String confirmPassword = passwordAgn.getText().trim();
        String username = userName1.getText().trim();

        if (enteredCode.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
            showAlert("لطفاً تمام فیلدها را پر کنید.");
            return;
        }

        if (!enteredCode.equals(forgetfulnessController.getVerificationCode())) {
            showAlert("کد تأیید وارد شده نادرست است.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("رمز عبور جدید با تکرار آن مطابقت ندارد.");
            return;
        }

        List<String> users = readUsersFromFile(USER_FILE);
        boolean updated = false;

        for (int i = 0; i < users.size(); i += 5) {
            if (users.get(i).equals(username)) {  // بررسی نام کاربری در هر 5 سطر
                users.set(i + 1, newPassword);  // تغییر رمز عبور در سطر دوم
                updated = true;
                break;
            }
        }

        if (updated) {
            writeUsersToFile(USER_FILE, users);
            showAlert("رمز عبور با موفقیت تغییر کرد.");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            showAlert("نام کاربری یافت نشد.");
        }
    }


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

    private void writeUsersToFile(String filePath, List<String> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : users) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("پیغام");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
