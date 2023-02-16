package com.gmail.klewzow.controllers;


import com.gmail.klewzow.DeterminantLinker;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;


@Getter
@Log4j
@Controller
public class UiController {

    private final DeterminantLinker determinantLinker;

    @FXML
    private FontAwesomeIcon closeButton;
    @FXML
    private Button exit;
    @FXML
    private ToggleGroup fileSet;
    @FXML
    private TextField httpTextField;
    @FXML
    private Text folderText;
    @FXML
    private ProgressBar progressBar;  //TODO Сделай прогрес бар
    @FXML
    private RadioButton xml;
    @FXML
    private RadioButton xlsx;
    @FXML
    private Button start;
    @FXML
    private TextArea fieldLogTextarea;

    public UiController(DeterminantLinker determinantLinker) {
        this.determinantLinker = determinantLinker;
    }

    /*
            Крестик
     */
    @FXML
    void handlerEndProgram(MouseEvent event) {
        if (event.getSource() == closeButton || event.getSource() == exit) {
            System.exit(0);
        }
    }

    /*
        Клик по  Html полю
   */
    @FXML
    void clearFileTextField() {
        if (!httpTextField.getText().startsWith(determinantLinker.getMRK_HTML())) {
            httpTextField.setText("");
        }
    }

    @FXML
    private void onKeyPressedHtmlTextField() {
        determinantLinker.disableButtonStart();

    }

    /*
        initialize
   */
    @FXML
    private void initialize() {
        determinantLinker.setUiController(this);
        determinantLinker.disableButtonStart();
    }

    /*
    Выбор Файла
     */
    @FXML
    public void multipartFile() {
        determinantLinker.selectMultipartFile();
        determinantLinker.disableButtonStart();
    }

    @FXML
    private void startApplication() {
        determinantLinker.run();
        log.debug("Send start");
    }
    /*
     *  Клик по radio button
     */

    @FXML
    public void extension(ActionEvent actionEvent) {
        determinantLinker.disableButtonStart();
        determinantLinker.clickFromRadioButtons(actionEvent);
    }


}
