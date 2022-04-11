package com.zz.zfb.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.zz.zfb.mod.TransInfo;
import com.zz.zfb.util.TransferUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainController {
    @FXML
    private Label welcomeText;

    private Stage stage;

    @FXML
    private TableView<TransInfo> mytableView;

    @FXML
    private TableColumn identityT = new TableColumn();
    @FXML
    private TableColumn transAmountT = new TableColumn();
    @FXML
    private TableColumn remarkT = new TableColumn();
    @FXML
    private TableColumn<TransInfo, String> orderTitleT = new TableColumn();
    @FXML
    private TableColumn nameT = new TableColumn();
    @FXML
    private TableColumn status = new TableColumn();

    @FXML
    private TextField remark, transAmount, identity, orderTitle, name;

    private ObservableList<TransInfo> transInfoList;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Hi,Welcome to JavaFX Application!");
    }

    @FXML
    protected void transfer() throws AlipayApiException, IOException {
        //remark.setText("转账成功");
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set("请先打开一个word文档再点编辑按钮");
        alert.showAndWait();*/
       /* System.out.println(name.getText());
        System.out.println(transAmount.getText());
        System.out.println(identity.getText());
        System.out.println(orderTitle.getText());
        System.out.println(remark.getText());*/

        AlipayFundTransUniTransferResponse response = TransferUtil.transfer(transAmount.getText(),identity.getText(),name.getText(),"",orderTitle.getText(),remark.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        //String str=readLineByPath("appId.txt");

        if (response.isSuccess()) {
            alert.headerTextProperty().set("转账成功！");

        } else {
            alert.headerTextProperty().set("转账失败！");
        }
        alert.showAndWait();
    }

    @FXML
    public void uploadBtn(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("EXCEL Files", "*.xlsx")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {


            FileInputStream file = new FileInputStream(selectedFile);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            //List<TransInfo> transInfoList = new ArrayList<>();
            transInfoList = FXCollections.observableArrayList();
            for (Row row : sheet) {
                TransInfo transInfo = new TransInfo();
                if (row.getRowNum() >= 1 && row.getCell(0).toString() != "") {
                    for (Cell cell : row) {
                        //StringProperty value = new SimpleStringProperty(cell.toString());
                        String value = cell.toString();
                        switch (cell.getCellType()) {
                            case STRING: // 字符串
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                DecimalFormat df = new DecimalFormat("0");
                                value = df.format(cell.getNumericCellValue()).trim();
                                //value = String.valueOf(cell.getNumericCellValue());
                                break;
                            default:
                                value = cell.getStringCellValue();

                        }
                        switch (cell.getColumnIndex()) {
                            case 0:
                                transInfo.setIdentityT(value);
                                break;
                            case 1:
                                transInfo.setNameT(value);
                                break;
                            case 2:
                                transInfo.setTransAmountT(value);
                                break;
                            case 3:
                                transInfo.setOrderTitleT(value);
                                break;
                            case 4:
                                transInfo.setRemarkT(value);
                                break;

                        }

                        //System.out.println(value);
                    }

                    transInfoList.add(transInfo);
                }
            }
            remarkT.setCellValueFactory(new PropertyValueFactory("remarkT"));
            transAmountT.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("transAmountT"));
            identityT.setCellValueFactory(new PropertyValueFactory<TransInfo, Object>("identityT"));
            orderTitleT.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("orderTitleT"));
            nameT.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("nameT"));
            status.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("status"));
            mytableView.setItems(transInfoList);
            mytableView.refresh();
            //mytableView.getColumns().addAll(identityT);
            //mytableView.getItems().addAll(transInfoList);
        }
    }


    @FXML
    public void downBtn() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        //String str=readLineByPath("appId.txt");
        alert.headerTextProperty().set("hello");
        alert.showAndWait();
    }
    @FXML
    public void transBtn() throws IOException, AlipayApiException {
        //System.out.println(transInfoList.get(0).getNameT());
        for(TransInfo transInfo:transInfoList){
            //System.out.println(transInfo.getIdentityT()+transInfo.getRemarkT());
            AlipayFundTransUniTransferResponse response = TransferUtil.transfer(transInfo.getTransAmountT(),transInfo.getIdentityT(),transInfo.getNameT(),"",transInfo.getOrderTitleT(),transInfo.getRemarkT());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            //String str=readLineByPath("appId.txt");

            if (response.isSuccess()) {
                //alert.headerTextProperty().set("转账成功！");
                transInfo.setStatus("转账成功！");

            } else {
                transInfo.setStatus("转账失败！");
            }
           // System.out.println(transInfo.getNameT()+transInfo.getRemarkT());
            //transInfo.setStatus("转账成功！");
        }
        //mytableView.getItems().clear();
       /* remarkT.setCellValueFactory(new PropertyValueFactory("remarkT"));
        transAmountT.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("transAmountT"));
        identityT.setCellValueFactory(new PropertyValueFactory<TransInfo, Object>("identityT"));
        orderTitleT.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("orderTitleT"));
        nameT.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("nameT"));
        status.setCellValueFactory(new PropertyValueFactory<TransInfo, String>("status"));*/
        mytableView.setItems(transInfoList);
        mytableView.refresh();
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        //String str=readLineByPath("appId.txt");
        alert.headerTextProperty().set("hello");
        alert.showAndWait();*/
    }

    @FXML
    protected void numberMethod() {

        transAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    transAmount.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }


}