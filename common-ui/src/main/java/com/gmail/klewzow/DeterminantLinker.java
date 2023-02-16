package com.gmail.klewzow;

import com.gmail.klewzow.controllers.UiController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Log4j
@Component
public class DeterminantLinker {

    private final String MRK_HTML = "http";
    private File fileWhereToWrite;
    private File folderWhereToWrite;
    private String fileExt;
    private RadioButton[] buttons;
    private UiController uiController;




    public void run(){
        log.debug("Send sendToDownloadService");
    }

    public void setUiController(UiController uiController) {
        this.uiController = uiController;
        buttons = new RadioButton[]{uiController.getXlsx(), uiController.getXml()};
        extension(uiController.getFileSet().getSelectedToggle().getToggleGroup());
    }

    private void clearingDataFields(TextArea fieldLogTextArea, Text fieldFolderName) {
        fileExt = null;
        fileWhereToWrite = null;
        fieldLogTextArea.setText("Файл не выбран.");
        fieldFolderName.setText("");
        disableButtonStart();
    }


    public void clickFromRadioButtons(ActionEvent actionEvent) {
        clearingDataFields(uiController.getFieldLogTextarea(), uiController.getFolderText());
        fileWhereToWrite = null;
        Optional<String> optionalS = Arrays.stream(buttons).filter(b -> b == actionEvent.getSource())
                .map(Node::getId).findAny();
        fileExt = "*." + optionalS.orElse(null);
    }

    /*
   Выбор расширения при запуске
    */
    public void extension(ToggleGroup toggleGroup) {
        Optional<Toggle> optionalToggle = toggleGroup.getToggles().stream().filter(Toggle::isSelected).findFirst();
        Toggle btn = optionalToggle.orElse(null);
        Optional<String> optionalS = Arrays.stream(buttons).filter(b -> b == btn)
                .map(Node::getId).findAny();
        fileExt = "*." + optionalS.orElse(null);
    }

    /*
     * У становка Multipart файла
     */
    public void selectMultipartFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileExt, fileExt));
        setFileWhereToWrite(fileChooser, uiController.getFieldLogTextarea(), uiController.getFolderText());
    }


    private void setFileWhereToWrite(FileChooser fileChooser, TextArea logTextArea, Text folderName) {
        List<File> fileList = fileChooser.showOpenMultipleDialog(null);
        if (fileList == null) return;
        Optional<File> pathFileToWrite = fileList.stream().findFirst();
        if (pathFileToWrite.isPresent()) {
            this.fileWhereToWrite = pathFileToWrite.get();
            logTextArea.setText("Выбранный файл : " + fileWhereToWrite.getAbsolutePath());
            log.debug(fileWhereToWrite);
            setFolder(this.fileWhereToWrite, folderName);
        }
    }

    private void setFolder(File file, Text folderName) {
        String pathFolder = file.getAbsolutePath();
        folderWhereToWrite = new File(pathFolder.substring(0, pathFolder.lastIndexOf("\\") + 1));
        folderName.setText(folderWhereToWrite.getAbsolutePath());
        log.debug(folderWhereToWrite);
    }

    public void disableButtonStart() {
        if (fileWhereToWrite == null) {
            uiController.getStart().setDisable(true);
            return;
        }
        if (!fileWhereToWrite.isFile() ||  uiController.getHttpTextField().getText().startsWith(MRK_HTML)) {
            uiController.getStart().setDisable(false);
        } else {
            uiController.getStart().setDisable(true);
            log.debug("fileWhereToWrite.isFile() else " + fileWhereToWrite.isFile()+"\n\n\n");
            log.debug("uiController.getHttpTextField().getText().equals(\"\") else " + uiController.getHttpTextField().getText().equals("")+"\n\n\n");
            log.debug("uiController.getHttpTextField().getText().startsWith(MRK_HTML) else " + !uiController.getHttpTextField().getText().startsWith(MRK_HTML)
                    +  uiController.getHttpTextField().getText() +"\n\n\n");
        }
    }

//private DataModel linkerToModel(){
//        dataModel.setUrl(uiController.getHttpTextField().getText());
//        dataModel.setFileWhereToWrite(fileWhereToWrite);
//        dataModel.setFolderWhereToWrite(folderWhereToWrite);
//        return dataModel;
//}
}
