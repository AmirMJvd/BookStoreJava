package model;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageManager {
    private List<Stage> openStages = new ArrayList<>();

    public void addStage(Stage stage) {
        openStages.add(stage);
    }

    public void closeAllStages() {
        for (Stage stage : openStages) {
            if (stage.isShowing()) {
                stage.close();
            }
        }
    }
}
