package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import main.Main;
import main.MyListener;
import model.Book;
import model.BookLists;
import model.Report;
import model.SharedData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProfileController {

    @FXML
    private Label AdminName;

    @FXML
    private TextField Password;

    @FXML
    private TextField PasswordRepet;


    @FXML
    private TextField Email;


    @FXML
    private GridPane grid1;


    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField userName;


    @FXML
    private AnchorPane AdminPane;


    @FXML
    void CameBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Market.fxml"));
        AnchorPane pane = loader.load();
        pane.setPrefWidth(1315);
        pane.setPrefHeight(810);
        MarketController marketController = loader.getController();
        String username1 = SharedData.getInstance().getUsername();
        marketController.setId(username1);
        AdminPane.setPrefSize(1315, 810);
        AdminPane.setMaxWidth(1315);
        AdminPane.setMaxHeight(810);
        AdminPane.setMinWidth(1315);
        AdminPane.setMinHeight(810);
        AdminPane.getChildren().setAll(pane);
    }
    private List<model.Report> reports = new ArrayList<>();

    private List<Report> getReportData() {
        String currentUsername = SharedData.getInstance().getUsername();
        List<Report> reports = new ArrayList<>();
        try {
            File cartFile = new File(currentUsername + "-history.txt");
            Scanner reader = new Scanner(cartFile);
            while (reader.hasNextLine()) {
                Report report = new Report();
                report.setName(reader.nextLine());
                report.setCount(reader.nextLine());
                report.setPrice(reader.nextLine());
                report.setDate(reader.nextLine());
                report.setImgSrc(reader.nextLine());
                reports.add(report);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reports;
    }

    @FXML
    public void initialize() {
        String currentUsername = SharedData.getInstance().getUsername();
        AdminName.setText(currentUsername);
        userName.setText(AdminName.getText());
        loadUserDetails(AdminName.getText());

        reports.addAll(getReportData());
        int row = 1;
        int column = 0;
        try {
            for (Report report : reports) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/report.fxml"));
                HBox HBox = fxmlLoader.load();

                ReportItemController cartItemController = fxmlLoader.getController();
                cartItemController.setData(report);

                if (column == 2) {
                    column = 0;
                    row++;
                }
                grid1.add(HBox, column++, row);
                grid1.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid1.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid1.setMaxWidth(Region.USE_PREF_SIZE);

                grid1.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid1.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid1.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(HBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadUserDetails(String adminName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            int lineNumber = 0;
            boolean userFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.equals(adminName)) {
                    userFound = true;
                    break;
                }
                lineNumber++;
            }
            if (userFound) {
                for (int i = 0; i <1; i++);
                Password.setText(reader.readLine());// Skip two lines
                phoneNumber.setText(reader.readLine()); // Third line after username
                Email.setText(reader.readLine()); // Fourth line after username
                 // Fifth line after username
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Registration(ActionEvent event) {
        String adminName = AdminName.getText();
        String newUsername = userName.getText();
        String newPassword = Password.getText();
        String repeatPassword = PasswordRepet.getText();
        String newPhone = phoneNumber.getText();
        String newEmail = Email.getText();

        if (!newPassword.equals(repeatPassword)) {
            showAlert("خطا", "تکرار رمز عبور صحیح نمی‌باشد.");
            return;
        }

        try {
            File file = new File("user.txt");
            List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
            boolean userFound = false;

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(adminName)) {
                    userFound = true;
                    lines.set(i, newUsername); // تغییر نام کاربری
                    if (i + 1 < lines.size()) lines.set(i + 1, newPassword); // تغییر رمز عبور
                    if (i + 2 < lines.size()) lines.set(i + 2, newPhone); // تغییر شماره تلفن
                    if (i + 3 < lines.size()) lines.set(i + 3, newEmail); // تغییر ایمیل
                    break;
                }
            }

            if (userFound) {
                Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
                showAlert("موفقیت", "اطلاعات شما با موفقیت به‌روزرسانی شد.");
            } else {
                showAlert("خطا", "کاربر یافت نشد.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("خطا", "مشکلی در ذخیره اطلاعات به وجود آمده است.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
