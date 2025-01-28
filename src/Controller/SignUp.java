package Controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserDataManager;
import java.io.IOException;
import java.util.Objects;
public class SignUp {

    private UserDataManager userManager = new UserDataManager();
    @FXML
    public Label lbltaeed;
    @FXML
    private PasswordField txttaeed;
    @FXML
    private Button btnbargasht1;
    @FXML
    private RadioButton btnkarbar;
    @FXML
    private RadioButton btnmodir;
    @FXML
    private Button btnsignup;
    @FXML
    private CheckBox checketminan1;
    @FXML
    private ToggleGroup toggleButton;
    @FXML
    private TextField txtnamkarbarup;
    @FXML
    private PasswordField txtramzup;
    @FXML
    private PasswordField txttekrarramzup;
    @FXML
    private Label lblanswer;
    @FXML
    private ImageView imgBackground;  // تصویر پس‌زمینه برای تغییر شفافیت
    private static final String MASTER_KEY = "admin123";
    @FXML
    void initialize() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), imgBackground);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        txtnamkarbarup.setOnKeyPressed(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                pressbtnsignup(null);
            }
        });
        txtramzup.setOnKeyPressed(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                pressbtnsignup(null);
            }
        });
        txttekrarramzup.setOnKeyPressed(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                pressbtnsignup(null);
            }
        });
        txttaeed.setOnKeyPressed(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                pressbtnsignup(null);
            }
        });
    }
    @FXML
    void pressbtnbargashtsignup(ActionEvent event) throws IOException {
        Stage stagesignup = (Stage) btnbargasht1.getScene().getWindow();
        stagesignup.close();
        Stage stage = new Stage();
        AnchorPane newRoot = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../views/signin.fxml")));
        Scene newScene = new Scene(newRoot, 971, 770);
        stage.setResizable(false);
        stage.setTitle("ورود");
        stage.setScene(newScene);
        stage.show();
    }
    @FXML
    private void pressbtnsignup(ActionEvent event) {
        if (!btnmodir.isSelected() && !btnkarbar.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("هشدار");
            alert.setContentText("لطفاً یکی از گزینه‌های مدیر یا کاربر را انتخاب فرمایید!");
            alert.showAndWait();
            return;
        }
        if (txtnamkarbarup.getText().isEmpty() || txtramzup.getText().isEmpty() || txttekrarramzup.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("هشدار");
            alert.setContentText("مطمئن شوید که تمامی اطلاعات را پر کرده‌اید.");
            alert.showAndWait();
            return;
        }
        if (!txtramzup.getText().equals(txttekrarramzup.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("هشدار");
            alert.setContentText("تکرار رمز عبور و رمز عبور باید برابر باشند!");
            alert.showAndWait();
            return;
        }
        String username = txtnamkarbarup.getText();
        String password = txtramzup.getText();
        String role = btnkarbar.isSelected() ? "کاربر" : "مدیر";
        if (btnmodir.isSelected()) {
            if (!txttaeed.getText().equals(MASTER_KEY)) {
                lbltaeed.setText("رمز تاییدیه مدیریت صحیح نمیباشد!");
                return;
            }
        }
        try {
            userManager.addUser(username, password, role);
            lblanswer.setText("ثبت نام موفقیت‌آمیز بود!");
        } catch (IllegalArgumentException e) {
            lblanswer.setText("نام کاربری از قبل وجود دارد.");
        } catch (IOException e) {
            lblanswer.setText("خطا در ذخیره اطلاعات.");
        }
    }
}
