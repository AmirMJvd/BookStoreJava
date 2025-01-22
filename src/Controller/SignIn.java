package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.StageManager;
import model.UserDataManager;

import java.io.IOException;
import java.util.Objects;

public class SignIn {
    private StageManager stageManager = new StageManager();
    private UserDataManager userManager = new UserDataManager();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private RadioButton btnkarbar;

    @FXML
    private RadioButton btnmodir;

    @FXML
    private Button btnsabtnamin;

    @FXML
    private Button btnvorudin;

    @FXML
    private ToggleGroup toggleButton;

    @FXML
    private TextField txtnamkarbarin;
    @FXML
    private Label lblanswerin;

    @FXML
    private PasswordField txtramzin;
    @FXML
    void pressbtnsabtnamin(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnsabtnamin.getScene().getWindow();
        stage.close(); // بستن Stage اول
        // ایجاد و نمایش Stage جدید
        Stage stagesignup = new Stage(); // ایجاد یک پنجره جدید
        AnchorPane newRoot = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../views/signup.fxml")));
        Scene newScene = new Scene(newRoot, 971, 770);
        stagesignup.setResizable(false);
        stagesignup.setTitle("پنجره جدید");
        stagesignup.setScene(newScene);
        stagesignup.show();
        // نمایش پنجره جدید

    }
    @FXML
    void pressbtnvorudin(ActionEvent event) throws IOException {
        // بررسی اینکه آیا نقش انتخاب شده است
        if (!btnmodir.isSelected() && !btnkarbar.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("هشدار");
            alert.setContentText("لطفاً یکی از گزینه‌های مدیر یا کاربر را انتخاب فرمایید!");
            alert.showAndWait();
            return;
        }

        // بررسی اینکه آیا فیلدها پر شده‌اند
        if (txtnamkarbarin.getText().isEmpty() || txtramzin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("هشدار");
            alert.setContentText("مطمئن شوید که تمامی اطلاعات را پر کرده‌اید.");
            alert.showAndWait();
            return;
        }

        // خواندن اطلاعات از ورودی‌ها
        String username = txtnamkarbarin.getText();
        String password = txtramzin.getText();
        String role = btnkarbar.isSelected() ? "کاربر" : "مدیر";

        // بررسی اعتبار کاربر
        if (userManager.isValidUser(username, password, role)) {
            lblanswerin.setText("ورود موفقیت‌آمیز بود!");

            // بستن استیج فعلی (صفحه ورود)
            Stage currentStage = (Stage) btnvorudin.getScene().getWindow();
            if (currentStage != null && currentStage.isShowing()) {
                currentStage.close();
            }

            // اگر نقش "مدیر" است
            if (role.equals("مدیر")) {
                // بستن استیج اصلی مارکت (در صورت وجود)
                if (Main.primaryStage != null && Main.primaryStage.isShowing()) {
                    Main.primaryStage.close();
                }

                // باز کردن صفحه مدیریت
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Admin.fxml"));
                AnchorPane adminRoot = loader.load();
                Stage adminStage = new Stage();
                adminStage.setTitle("صفحه مدیریت");
                adminStage.setScene(new Scene(adminRoot, 800, 600)); // اندازه دلخواه صفحه
                adminStage.setMaximized(true);
                adminStage.show();
            }

            // اگر نقش "کاربر" است
            // هیچ صفحه جدیدی باز نمی‌شود و صفحه مارکت باز می‌ماند.
        } else {
            lblanswerin.setText("نام کاربری یا رمز عبور اشتباه است.");
        }
    }







    public RadioButton getBtnkarbar() {
        return btnkarbar;
    }

    public void setBtnkarbar(RadioButton btnkarbar) {
        this.btnkarbar = btnkarbar;
    }

    public RadioButton getBtnmodir() {
        return btnmodir;
    }

    public void setBtnmodir(RadioButton btnmodir) {
        this.btnmodir = btnmodir;
    }

    public Button getBtnvorudin() {
        return btnvorudin;
    }

    public void setBtnvorudin(Button btnvorudin) {
        this.btnvorudin = btnvorudin;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }
}










