package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.SharedData;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    private static final String FILE_NAME = "user.txt";

    @FXML
    void LodRegister(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Registration.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void Login(ActionEvent event) {
        String username = userName.getText().trim();
        String pass = password.getText().trim();

        if (username.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "خطا", "نام کاربری و رمز عبور نمی‌توانند خالی باشند!");
            return;
        }

        String role = validateLogin(username, pass);

        if (role != null) {
            SharedData.getInstance().setUsername(username); // ذخیره نام کاربری در SharedData
            createUserFile(username); // ایجاد فایل متنی برای نام کاربری
            showAlert(Alert.AlertType.INFORMATION, "موفقیت", "ورود با موفقیت انجام شد!");
            closeLoginWindow(); // بستن پنجره ورود


            if (role.equals("کاربر")) {
                openPage("../views/market.fxml", "Market", username);
                closeAllWindows();
            } else if (role.equals("مدیر")) {
                openPage("../views/admin.fxml", "Admin Panel", username);
                closeAllWindows();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "خطا", "نام کاربری یا رمز عبور اشتباه است!");
        }
    }

    private String validateLogin(String username, String password) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String existingUsername = scanner.nextLine().trim();
                String existingPassword = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
                scanner.nextLine(); // خط سوم (نام)
                scanner.nextLine(); // خط چهارم (ایمیل)
                String role = scanner.hasNextLine() ? scanner.nextLine().trim() : ""; // خط پنجم (نقش)

                if (existingUsername.equals(username) && existingPassword.equals(password)) {
                    return role; // نقش کاربر را برمی‌گرداند (کاربر / مدیر)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createUserFile(String username) {
        File userFile = new File(username + ".txt");
        try {
            if (userFile.createNewFile()) {
                System.out.println("فایل " + username + ".txt" + " ایجاد شد.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLoginWindow() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    private void closeAllWindows() {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                ((Stage) window).close();
            }
        }
    }

                private void openPage(String fxmlPath, String title, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();

            // تنظیم نام کاربری در کنترلر
            if (title.equals("Market")) {
                MarketController controller = loader.getController();
                controller.setId(username);
            } else if (title.equals("Admin Panel")) {
                AdminController controller = loader.getController();
                controller.setId(username);
            }

            Scene scene = new Scene(pane, 1315, 810);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(title);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
/*
package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.SharedData;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField password;

    @FXML
    private TextField userName;

    private static final String FILE_NAME = "user.txt";

    @FXML
    void LodRegister(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/Registration.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void Login(ActionEvent event) {
        String username = userName.getText().trim();
        String pass = password.getText().trim();

        if (username.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "خطا", "نام کاربری و رمز عبور نمی‌توانند خالی باشند!");
            return;
        }

        if (validateLogin(username, pass)) {
            SharedData.getInstance().setUsername(username); // ذخیره نام کاربری در SharedData
            createUserFile(username); // ایجاد فایل متنی برای نام کاربری
            showAlert(Alert.AlertType.INFORMATION, "موفقیت", "ورود با موفقیت انجام شد!");
            closeAllWindows(); // بستن همه پنجره‌ها
            openMarketPage(username); // باز کردن صفحه مارکت
        } else {
            showAlert(Alert.AlertType.ERROR, "خطا", "نام کاربری یا رمز عبور اشتباه است!");
        }
    }

    private boolean validateLogin(String username, String password) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String existingUsername = scanner.nextLine().trim();
                String existingPassword = "";

                if (scanner.hasNextLine()) {
                    existingPassword = scanner.nextLine().trim();
                }
                if (scanner.hasNextLine()) scanner.nextLine();
                if (scanner.hasNextLine()) scanner.nextLine();
                if (scanner.hasNextLine()) scanner.nextLine();

                if (existingUsername.equals(username) && existingPassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createUserFile(String username) {
        File userFile = new File(username + ".txt");
        try {
            if (userFile.createNewFile()) {
                System.out.println("فایل " + username + ".txt" + " ایجاد شد.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeAllWindows() {
        for (Window window : Stage.getWindows()) {
            if (window instanceof Stage) {
                ((Stage) window).close();
            }
        }
    }
    private void closeLoginWindow() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void openMarketPage(String username) {
        try {
            closeLoginWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/market.fxml"));
            AnchorPane pane = loader.load();
            MarketController marketController = loader.getController();
            marketController.setId(username); // ارسال نام کاربری به کنترلر صفحه مارکت

            Scene scene = new Scene(pane, 1315, 810);
            Stage marketStage = new Stage();
            marketStage.setScene(scene);
            marketStage.setTitle("Market");
            marketStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
 */
