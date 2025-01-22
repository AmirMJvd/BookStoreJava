package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.StageManager;
import model.UserDataManager;

import java.io.IOException;
import java.util.Objects;

public class SignUp {
    private StageManager stageManager = new StageManager();
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
    // رمز پیش‌فرض برای تأیید مدیر
    private static final String MASTER_KEY = "admin123";

    @FXML
    void pressbtnbargashtsignup(ActionEvent event) throws IOException {
        Stage stagesignup = (Stage) btnbargasht1.getScene().getWindow();
        stagesignup.close(); // بستن Stage اول
        // ایجاد و نمایش Stage جدید
        Stage stage = new Stage(); // ایجاد یک پنجره جدید
        AnchorPane newRoot = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../views/signin.fxml")));
        Scene newScene = new Scene(newRoot, 971, 770);
        stage.setResizable(false);
        stage.setTitle("ورود");
        stage.setScene(newScene);
        stage.show(); // نمایش پنجره جد
    }
    private UserDataManager userManager = new UserDataManager();
    @FXML
    private void pressbtnsignup(ActionEvent event) {
        if (!btnmodir.isSelected() && !btnkarbar.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("هشدار");
            alert.setContentText("لطفاً یکی از گزینه‌های مدیر یا کاربر را انتخاب فرمایید!");
            alert.showAndWait();
            return;
        }
        if (btnkarbar.isSelected()){
            if (txtnamkarbarup.getText().compareTo("") == 0 || txtramzup.getText().compareTo("") == 0||txttekrarramzup.getText().compareTo("")==0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("هشدار");
                alert.setContentText("مطمئن شوید که تمامی اطلاعات را پر کرده اید.");
                // نمایش هشدار
                alert.showAndWait();
            } if (!txtramzup.getText().equals(txttekrarramzup.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("هشدار");
                alert.setContentText("تکرار رمز عبور و رمز عبور باید برابر باشند!");
                alert.showAndWait();
                return;
            }
            else {
                String username = txtnamkarbarup.getText();
                String password = txtramzup.getText();
                String role = "کاربر";
                try {
                    userManager.addUser(username, password, role);
                    lblanswer.setText("ثبت نام موفقیت‌آمیز بود!");
                } catch (IllegalArgumentException e) {
                    lblanswer.setText("نام کاربری از قبل وجود دارد.");
                } catch (IOException e) {
                    lblanswer.setText("خطا در ذخیره اطلاعات.");
                }}
        }
        else if (btnmodir.isSelected()) {
            if (txtnamkarbarup.getText().isEmpty() || txtramzup.getText().isEmpty() || txttekrarramzup.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("هشدار");
                alert.setContentText("مطمئن شوید که تمامی اطلاعات را پر کرده اید.");
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
            // بررسی رمز تأیید
            if (!txttaeed.getText().equals(MASTER_KEY)) {
                lbltaeed.setText("رمز تاییدیه مدیریت صحیح نمیباشد!");
                // Alert alert = new Alert(Alert.AlertType.ERROR);
                //alert.setTitle("خطای تأیید");
                //alert.setContentText("رمز تأیید مدیر نادرست است.");
                // alert.showAndWait();
                return;
            }
            String username = txtnamkarbarup.getText();
            String password = txtramzup.getText();
            String role = "مدیر";
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
    public PasswordField gettxttees(){
        return txttaeed;
    }
    public void setTxttaeed(PasswordField txttaeed){this.txttaeed = txttaeed;}
    public Label getLbltaeed(){
        return lbltaeed;
    }
    public void setLbltaeed(Label lbltaeed){this.lbltaeed = lbltaeed;}
    public Label getLblanswer () {
        return lblanswer;
    }
    public void setLblanswer (Label lblanswer){
        this.lblanswer = lblanswer;
    }
    public PasswordField getTxttekrarramzup () {
        return txttekrarramzup;}
    public void setTxttekrarramzup (PasswordField txttekrarramzup){
        this.txttekrarramzup = txttekrarramzup;
    }
    public PasswordField getTxtramzup(){
        return txtramzup;
    }
    public void setTxtramzup(PasswordField txtramzup){
        this.txtramzup = txtramzup;
    }
    public CheckBox getChecketminan1 () {
        return checketminan1;
    }
    public void setChecketminan1 (CheckBox checketminan1){
        this.checketminan1 = checketminan1;
    }

    public Button getBtnsignup () {
        return btnsignup;
    }

    public void setBtnsignup (Button btnsignup){
        this.btnsignup = btnsignup;
    }

    public RadioButton getBtnmodirsup1 () {
        return btnmodir;
    }

    public void setBtnmodirsup1 (RadioButton btnmodirsup1){
        this.btnmodir = btnmodirsup1;
    }

    public RadioButton getBtnkarbarup () {
        return btnkarbar;
    }

    public void setBtnkarbarup (RadioButton btnkarbarup){
        this.btnkarbar = btnkarbarup;
    }

    public Button getBtnbargasht1 () {
        return btnbargasht1;
    }

    public void setBtnbargasht1 (Button btnbargasht1){
        this.btnbargasht1 = btnbargasht1;
    }

    public ToggleGroup getToggleButton () {
        return toggleButton;
    }

    public void setToggleButton (ToggleGroup toggleButton){
        this.toggleButton = toggleButton;
    }

    public RadioButton getBtnkarbar () {
        return btnkarbar;
    }

    public void setBtnkarbar (RadioButton btnkarbar){
        this.btnkarbar = btnkarbar;
    }

    public RadioButton getBtnmodir () {
        return btnmodir;
    }

    public void setBtnmodir (RadioButton btnmodir){
        this.btnmodir = btnmodir;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }
}