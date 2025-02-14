package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.History;


public class HistoryItemController {

    @FXML
    private Label Date;

    @FXML
    private Label price;

    private History history;

    public void setData(History history) {
        this.history = history;
        Date.setText(history.getDate());
        price.setText(history.getPrice());

    }
    private ProfileController profileController; // متغیری برای ذخیره نمونه‌ی اصلی ProfileController

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }


    @FXML

    void Show(MouseEvent event) {

    if (profileController != null) {
        String selectedDate = Date.getText(); // گرفتن تاریخ انتخاب‌شده
        profileController.filterReportsByDate(selectedDate); // ارسال تاریخ به ProfileController
    } else {
        System.out.println("ProfileController مقداردهی نشده است!");
    }
}


}
