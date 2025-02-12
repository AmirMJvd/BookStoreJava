package Controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoadingController {

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    private boolean wasConnected = true;
    private ScheduledExecutorService internetChecker;

    public void initialize() {
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        new Thread(() -> {
            boolean isConnected = isInternetAvailable();
            Platform.runLater(() -> {
                if (isConnected) {
                    statusLabel.setText("اتصال به اینترنت برقرار است. در حال انتقال به صفحه اصلی...");
                    progressIndicator.setVisible(false);

                    PauseTransition delay = new PauseTransition(Duration.millis(200));
                    delay.setOnFinished(event -> {
                        try {
                            goToMainPage();
                        } catch (IOException e) {
                            showErrorAlert("خطا در تغییر صفحه", "خطا در رفتن به صفحه اصلی: " + e.getMessage());
                        }
                    });
                    delay.play();
                } else {
                    statusLabel.setText("اتصال به اینترنت برقرار نیست. لطفا اتصال اینترنت را بررسی کنید.");
                    progressIndicator.setVisible(false);

                    showRetryExitSupportAlert();
                }
            });
        }).start();
    }

    private void startInternetMonitor() {
        internetChecker = Executors.newSingleThreadScheduledExecutor();
        internetChecker.scheduleAtFixedRate(() -> {
            boolean isConnected = isInternetAvailable();

            if (!isConnected && wasConnected) {
                Platform.runLater(this::showInternetLostAlert);
            }

            wasConnected = isConnected;
        }, 10, 10, TimeUnit.SECONDS);
    }

    private boolean isInternetAvailable() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            int responseCode = connection.getResponseCode();
            return (responseCode == 200);
        } catch (IOException e) {
            return false;
        }
    }

    private void goToMainPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/market.fxml"));
        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("BOOK STORE");
        stage.show();

        startInternetMonitor();
    }

    private void showInternetLostAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("قطع اتصال اینترنت");
        alert.setHeaderText("اتصال اینترنت شما قطع شده است!");
        alert.setContentText("لطفاً اتصال خود را بررسی کنید.");
        alert.showAndWait();
        ButtonType retryButton = new ButtonType("تلاش مجدد");
        ButtonType exitButton = new ButtonType("خروج");
        ButtonType supportButton = new ButtonType("ارتباط با پشتیبانی");

        alert.getButtonTypes().setAll(retryButton, exitButton, supportButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == retryButton) {
                statusLabel.setText("در حال بررسی اتصال به اینترنت...");
                checkInternetConnection();
            } else if (result.get() == exitButton) {
                Platform.exit();
            } else if (result.get() == supportButton) {
                openSupportPage();
            }
        }
    }

    private void showRetryExitSupportAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطا در اتصال");
        alert.setHeaderText(null);
        alert.setContentText("اتصال به اینترنت برقرار نیست. لطفا اینترنت را بررسی کنید.");

        ButtonType retryButton = new ButtonType("تلاش مجدد");
        ButtonType exitButton = new ButtonType("خروج");
        ButtonType supportButton = new ButtonType("ارتباط با پشتیبانی");

        alert.getButtonTypes().setAll(retryButton, exitButton, supportButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == retryButton) {
                statusLabel.setText("در حال بررسی اتصال به اینترنت...");
                checkInternetConnection();
            } else if (result.get() == exitButton) {
                Platform.exit();
            } else if (result.get() == supportButton) {
                openSupportPage();
            }
        }
    }

    private void openSupportPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/support.fxml"));
            Stage stage = new Stage();
            stage.setTitle("پشتیبانی");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("خطا", "امکان باز کردن صفحه پشتیبانی وجود ندارد.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
