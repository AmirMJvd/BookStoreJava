package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.Main;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
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
    private GridPane grid;

    @FXML
    private GridPane grid2;

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
    private List<model.favorit> favorits = new ArrayList<>();
    private List<model.History> histores = new ArrayList<>();
    private List<Report> allReports = new ArrayList<>();

    private List<favorit> getfavoritData() {
        String currentUsername = SharedData.getInstance().getUsername();
        List<favorit> favorits = new ArrayList<>();
        try {
            File favoridFile = new File(currentUsername + "-favorit.txt");
            Scanner reader = new Scanner(favoridFile);
            while (reader.hasNextLine()) {
                favorit favorit = new favorit();
                favorit.setName(reader.nextLine());
                favorit.setPrice(reader.nextLine());
                favorit.setImgSrc(reader.nextLine());
                favorits.add(favorit);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return favorits;
    }
    private void loadAllReports() {
        String currentUsername = SharedData.getInstance().getUsername();
        allReports.clear();
        try {

            File reportFile = new File(currentUsername + "-history.txt");
            Scanner reader = new Scanner(reportFile);

            while (reader.hasNextLine()) {
                Report report = new Report();
                report.setName(reader.nextLine());
                report.setCount(reader.nextLine());
                report.setPrice(reader.nextLine());
                report.setDate(reader.nextLine());
                report.setImgSrc(reader.nextLine());
                allReports.add(report);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void filterReportsByDate(String selectedDate) {
        grid2.getChildren().clear(); // پاک کردن گزارش‌های قبلی

        if (selectedDate == null || selectedDate.isEmpty()) {
            return; // اگر تاریخی انتخاب نشده باشد، چیزی نمایش داده نشود
        }

        List<Report> filteredReports = new ArrayList<>();

        for (Report report : allReports) {
            if (report.getDate().equals(selectedDate)) {
                filteredReports.add(report);
            }
        }

        updateGrid(filteredReports);
    }

    private void updateGrid(List<Report> reports) {
        int row = 1;
        int column = 0;

        for (Report report : reports) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/report.fxml"));
                HBox anchorPane = fxmlLoader.load();
                ReportItemController itemController = fxmlLoader.getController();
                itemController.setData(report);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid2.add(anchorPane, column++, row);
                grid2.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid2.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid2.setMaxWidth(Region.USE_PREF_SIZE);

                grid2.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid2.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid2.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadCartItems(List<favorit> favorits){
        grid.getChildren().clear();
        favorits.clear();
        favorits.addAll(getfavoritData());
        int row = 1;
        int column = 0;

        for (favorit favorit : favorits) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item1.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                // ارسال ProfileController به Item1Controller
                Item1Controller itemController = fxmlLoader.getController();
                itemController.setData1(favorit, this);  // اینجا ProfileController را به Item1Controller ارسال می‌کنیم

                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private List<History> getHistoryData() {
        String currentUsername = SharedData.getInstance().getUsername();
        List<History> histores = new ArrayList<>();
        try {
            File favoridFile = new File(currentUsername +"-history.txt");
            Scanner reader = new Scanner(favoridFile);
            while (reader.hasNextLine()) {
                reader.nextLine(); // اسم کتاب
                reader.nextLine(); // تعداد سفارش
                String price = reader.nextLine(); // قیمت
                price = price.replaceAll("[^\\d.]", ""); // حذف پیشوندها و پسوندها
                double priceValue = Double.parseDouble(price); // تبدیل قیمت به عدد
                String date = reader.nextLine();  // تاریخ
                reader.nextLine(); // آدرس عکس

                // استفاده از کانستراکتور با پارامتر
                History history = new History(date, String.valueOf(priceValue));
                histores.add(history);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return histores;
    }

    @FXML
    public void initialize() {
        String currentUsername = SharedData.getInstance().getUsername();
        AdminName.setText(currentUsername);
        userName.setText(AdminName.getText());
        loadUserDetails(AdminName.getText());

        int row = 1;
        int column = 0;
        try {

            // بارگذاری لیست favorits
            List<favorit> favorits = getfavoritData(); // دریافت داده‌ها
            loadCartItems(favorits); // ارسال لیست به متد
            loadAllReports();


            // بارگذاری داده‌های "histores"
            row = 1;
            column = 0;
            histores.addAll(getHistoryData());

            // گروه‌بندی تاریخ‌ها و محاسبه مجموع قیمت‌ها
            List<History> groupedHistories = new ArrayList<>();
            double totalPriceForCurrentDate = 0.0;
            String currentDate = "";

            // مرتب‌سازی داده‌ها بر اساس تاریخ
            histores.sort(Comparator.comparing(History::getDate));

            // گروه‌بندی تاریخ‌ها و جمع قیمت‌ها
            for (int i = 0; i < histores.size(); i++) {
                History history = histores.get(i);
                String date = history.getDate();
                double price = Double.parseDouble(history.getPrice()); // فرض بر این است که قیمت‌ها عددی هستند

                // اگر تاریخ جدید باشد، مجموع قیمت تاریخ قبلی رو اضافه می‌کنیم
                if (!date.equals(currentDate)) {
                    if (!currentDate.isEmpty()) {
                        // اضافه کردن تاریخ و مجموع قیمت تاریخ قبلی
                        groupedHistories.add(new History(currentDate, String.valueOf(totalPriceForCurrentDate)));
                    }

                    // بازنشانی برای تاریخ جدید
                    currentDate = date;
                    totalPriceForCurrentDate = 0.0;
                }

                // جمع‌آوری مجموع قیمت‌ها برای تاریخ مشابه
                totalPriceForCurrentDate += price;

                // اگر به انتهای لیست رسیدیم، تاریخ و مجموع قیمت اون رو هم اضافه می‌کنیم
                if (i == histores.size() - 1) {
                    groupedHistories.add(new History(currentDate, String.valueOf(totalPriceForCurrentDate)));
                }
            }

            // نمایش داده‌های گروه‌بندی شده
            for (History history : groupedHistories) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/History.fxml"));
                VBox anchorPane = fxmlLoader.load();
                HistoryItemController itemController = fxmlLoader.getController();
                itemController.setData(history);
                itemController.setProfileController(this); // ارسال نمونه‌ی ProfileController

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid1.add(anchorPane, column++, row);
                grid1.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid1.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid1.setMaxWidth(Region.USE_PREF_SIZE);

                grid1.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid1.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid1.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
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

    @FXML
    void getOut(MouseEvent event) throws IOException {
        // بستن تمام پنجره‌های باز
//        for (Window window : Window.getWindows()) {
//            if (window instanceof Stage) {  // بررسی اینکه پنجره از نوع Stage است یا خیر
//                Stage stage = (Stage) window; // تبدیل به Stage
//                if (stage.isShowing()) {
//                    stage.close();  // بستن پنجره
//                }
//            }
//        }
        // بستن پنجره فعلی
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (currentStage != null) {
            currentStage.close();  // بستن پنجره فعلی
        }

        // دسترسی به نمونه‌ی SharedData و پاک کردن مقدار username
        SharedData.getInstance().setUsername(null); // پاک کردن username


        // باز کردن صفحه مارکت (برای مثال با استفاده از FXMLLoader)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/market.fxml"));
        Parent root = loader.load();

        // نمایش صفحه مارکت در یک پنجره جدید
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();

    }
    public void removeItemFromGrid(Item1Controller itemController) {
        // گرفتن نود مربوط به آیتم از کنترلر
        Node itemNode = itemController.getItemNode(); // فرض بر این است که متدی به نام getItemNode در Item1Controller دارید که نود را باز می‌گرداند

        // حذف نود از grid
        grid.getChildren().remove(itemNode);
    }

    public void removeItem(Item1Controller itemController) {
        // فرض می‌کنیم که در Item1Controller یک متغیر به نام 'itemNode' داریم که نود AnchorPane را ذخیره می‌کند
        Node itemNode = itemController.getItemNode();  // گرفتن نود از کنترلر
        grid.getChildren().remove(itemNode);  // حذف آیتم از grid
    }








}
