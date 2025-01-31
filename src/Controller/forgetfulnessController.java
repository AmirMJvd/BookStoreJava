/*package Controller;

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

 */
package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.*;

public class forgetfulnessController {

    @FXML
    private TextField EmailAdd;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField userName1;

    private static String verificationCode;
    private static String verifiedEmail;
    private static final String USER_FILE = "user.txt";

    @FXML
    public void initialize() {
        userName1.setOnAction(event -> PhoneNumber.requestFocus());
        PhoneNumber.setOnAction(event -> EmailAdd.requestFocus());
        EmailAdd.setOnAction(event -> {
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
        String userName = userName1.getText().trim();
        String email = EmailAdd.getText().trim();
        String phoneNumber = PhoneNumber.getText().trim();

        if (userName.isEmpty()) {
            showAlert("لطفاً نام کاربری را وارد کنید.");
            return;
        }

        List<String> users = readUsersFromFile(USER_FILE);

        // بررسی شماره تلفن و نمایش رمز عبور
        if (!phoneNumber.isEmpty()) {
            for (int i = 0; i < users.size(); i += 5) {
                if (users.get(i).equals(userName) && users.get(i + 2).equals(phoneNumber)) {
                    showAlert("رمز عبور شما: " + users.get(i + 1));
                    return;
                }
            }
            showAlert("نام کاربری یا شماره تلفن نادرست است.");
            return;
        }

        // بررسی ایمیل و ارسال کد تأیید
        if (!email.isEmpty()) {
            for (int i = 0; i < users.size(); i += 5) {
                if (users.get(i).equals(userName) && users.get(i + 3).equals(email)) {
                    verificationCode = generateVerificationCode();
                    verifiedEmail = email;

                    boolean success = sendEmail(email, verificationCode);
                    if (success) {
                        showAlert("کد تأیید به ایمیل ارسال شد.");
                        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/ForgettingViaEmail.fxml"));
                        rootPane.getChildren().setAll(pane);
                    } else {
                        showAlert("ارسال ایمیل ناموفق بود. لطفاً دوباره تلاش کنید.");
                    }
                    return;
                }
            }
            showAlert("نام کاربری یا ایمیل نادرست است.");
            return;
        }

        // اگر هیچ اطلاعاتی وارد نشده باشد
        showAlert("لطفاً شماره تلفن یا ایمیل را وارد کنید.");
    }


    private String generateVerificationCode() {
        int code = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    private boolean sendEmail(String recipient, String code) {
        final String senderEmail = "bookstore.java.1403@gmail.com";
        final String senderPassword = "grgf ycdk suio bxbl";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("کد تأیید شما");
            message.setText("کد تأیید شما: " + code);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("پیغام");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static String getVerificationCode() {
        return verificationCode;
    }

    public static String getVerifiedEmail() {
        return verifiedEmail;
    }
}

