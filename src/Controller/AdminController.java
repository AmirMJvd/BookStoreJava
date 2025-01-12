package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.FileWriter;
import java.io.IOException;

public class AdminController {


    @FXML
    private AnchorPane AdminPane;

    @FXML
    private TextField Color;

    @FXML
    private TextField ImgAdr;

    @FXML
    private TextField author;

    @FXML
    private TextField bookName;

    @FXML
    private TextField count;

    @FXML
    private TextField nasher;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField translator;

    @FXML
    private Label Report;

    @FXML
    void CameBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../views/market.fxml"));;
        AdminPane.getChildren().setAll(pane);
    }

    @FXML
    void registration(ActionEvent event)  {
        try {
            FileWriter myWriter = new FileWriter("BookInf.txt", true);
            myWriter.write("\n");
            myWriter.write(bookName.getText());
            myWriter.write("\n");
            myWriter.write(author.getText());
            myWriter.write("\n");
            myWriter.write(translator.getText());
            myWriter.write("\n");
            myWriter.write(nasher.getText());
            myWriter.write("\n");
            myWriter.write(ImgAdr.getText());
            myWriter.write("\n");
            myWriter.write(Color.getText());
            myWriter.write("\n");
            myWriter.write(priceTextField.getText());
            myWriter.write("\n");
            myWriter.write(count.getText());
            myWriter.write("\n");
            myWriter.write("-------------------");
            myWriter.close();
            Report.setText("Successfully Registered!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

