//package Controller;
//
//import javafx.animation.FadeTransition;
//import javafx.animation.PathTransition;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import main.Main;
//import model.SharedData;
//import model.UserDataManager;
//import javafx.scene.input.KeyCode;
//import javafx.scene.shape.Circle;
//
//import java.io.IOException;
//import java.util.Objects;
//
//public class SignIn {
//
//    private UserDataManager userManager = new UserDataManager();
//
//    @FXML
//    private Label welcomeText;
//    @FXML
//    private AnchorPane rootPane;
//    @FXML
//    private RadioButton btnkarbar;
//    @FXML
//    private RadioButton btnmodir;
//    @FXML
//    private Button btnsabtnamin;
//    @FXML
//    private Button btnvorudin;
//    @FXML
//    private ToggleGroup toggleButton;
//    @FXML
//    private TextField txtnamkarbarin;
//    @FXML
//    private Label lblanswerin;
//    @FXML
//    private PasswordField txtramzin;
//    @FXML
//    private ImageView imageViewBook;
//    @FXML
//    void initialize() {
//        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), imageViewBook);
//        fadeTransition.setFromValue(0);
//        fadeTransition.setToValue(1);
//        fadeTransition.play();
//        Circle path = new Circle(300, 300, 250);
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setNode(welcomeText);
//        pathTransition.setPath(path);
//        pathTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
//        pathTransition.setCycleCount(PathTransition.INDEFINITE);
//        pathTransition.setRate(0.2);
//        pathTransition.play();
//        FadeTransition textFadeTransition = new FadeTransition(Duration.seconds(3), welcomeText);
//        textFadeTransition.setFromValue(0);
//        textFadeTransition.setToValue(1);
//        textFadeTransition.setCycleCount(FadeTransition.INDEFINITE);
//        textFadeTransition.setAutoReverse(true);
//        textFadeTransition.play();
//        txtnamkarbarin.setOnKeyPressed(event -> {
//            if (event.getCode().equals(KeyCode.ENTER)) {
//                try {
//                    pressbtnvorudin(null);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        txtramzin.setOnKeyPressed(event -> {
//            if (event.getCode().equals(KeyCode.ENTER)) {
//                try {
//                    pressbtnvorudin(null);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }
//    @FXML
//    void pressbtnsabtnamin(ActionEvent event) throws IOException {
//        Stage stage = (Stage) btnsabtnamin.getScene().getWindow();
//        stage.close();
//        Stage stagesignup = new Stage();
//        AnchorPane newRoot = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../views/signup.fxml")));
//        Scene newScene = new Scene(newRoot, 971, 770);
//        stagesignup.setResizable(false);
//        stagesignup.setTitle("پنجره جدید");
//        stagesignup.setScene(newScene);
//        stagesignup.show();
//    }
//    @FXML
//    void pressbtnvorudin(ActionEvent event) throws IOException {
//        if (!btnmodir.isSelected() && !btnkarbar.isSelected()) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("هشدار");
//            alert.setContentText("لطفاً یکی از گزینه‌های مدیر یا کاربر را انتخاب فرمایید!");
//            alert.showAndWait();
//            return;
//        }
//        if (txtnamkarbarin.getText().isEmpty() || txtramzin.getText().isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("هشدار");
//            alert.setContentText("مطمئن شوید که تمامی اطلاعات را پر کرده‌اید.");
//            alert.showAndWait();
//            return;
//        }
//        String username = txtnamkarbarin.getText();
//        String password = txtramzin.getText();
//        String role = btnkarbar.isSelected() ? "کاربر" : "مدیر";
//        if (userManager.isValidUser(username, password, role)) {
//            if (lblanswerin != null) {
//                lblanswerin.setText("ورود موفقیت‌آمیز بود!");
//            } else {
//                System.out.println("Error: lblanswerin is not initialized!");
//            }
//            SharedData.getInstance().setUsername(username);
//            if (role.equals("کاربر")) {
//                if (Main.primaryStage != null && Main.primaryStage.isShowing()) {    Main.primaryStage.close();}
//                Stage currentStage = (Stage) btnvorudin.getScene().getWindow();
//                if (currentStage != null && currentStage.isShowing()) {
//                    currentStage.close();
//                }
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/market.fxml"));
//                    AnchorPane marketRoot = loader.load();
//                    MarketController marketController = loader.getController();
//                    String username1 = SharedData.getInstance().getUsername();
//                    marketController.setId(username1);
//
//
//
//                    Stage marketStage = new Stage();
//                    marketStage.setTitle("مدیریت");
//
//                    Scene scene = new Scene(marketRoot, 1315, 810);
//                    marketStage.setScene(scene);
//
//                    marketStage.setMaximized(false);
//                    marketStage.setResizable(false);
//
//                    marketStage.show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (role.equals("مدیر")) {
//                if (Main.primaryStage != null && Main.primaryStage.isShowing()) {
//                    Main.primaryStage.close();
//                }
//                Stage currentStage = (Stage) btnvorudin.getScene().getWindow();
//                if (currentStage != null && currentStage.isShowing()) {
//                    currentStage.close();
//                }
//                try {
////                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Admin.fxml"));
////                    AnchorPane adminRoot = loader.load();
////                    AdminController adminController = loader.getController();
////                    String username1 = SharedData.getInstance().getUsername();
////                    adminController.setId(username1);
////
////                    Stage marketStage = new Stage();
////                    marketStage.setTitle("مدیریت");
////
////                    Scene scene = new Scene(adminRoot, 1315, 810);
////                    marketStage.setScene(scene);
////
////                    marketStage.setMaximized(false);
////                    marketStage.setResizable(false);
////
////                    marketStage.show();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//            }
//        } else {
//            if (lblanswerin != null) {
//                lblanswerin.setText("نام کاربری یا رمز عبور اشتباه است.");
//            } else {
//                System.out.println("Error: lblanswerin is not initialized!");
//            }
//        }
//    }
//    public RadioButton getBtnkarbar() {
//        return btnkarbar;
//    }
//    public void setBtnkarbar(RadioButton btnkarbar) {
//        this.btnkarbar = btnkarbar;
//    }
//    public RadioButton getBtnmodir() {
//        return btnmodir;
//    }
//    public void setBtnmodir(RadioButton btnmodir) {
//        this.btnmodir = btnmodir;
//    }
//    public Button getBtnvorudin() {
//        return btnvorudin;
//    }
//    public void setBtnvorudin(Button btnvorudin) {
//        this.btnvorudin = btnvorudin;
//    }}

