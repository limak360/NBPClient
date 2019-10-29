package sample;

import animatefx.animation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    Download download = new Download();
    Adresy adresy = new Adresy();
    ObjectMapper mapper = new ObjectMapper();
    TableInfo tableInfo = new TableInfo();
    LastDays lastDays = new LastDays();
    Double max, min, yunit;

//    final NumberAxis xAxis;
//    //Defining y axis
//    final NumberAxis yAxis;

    @FXML
    private Button close;
    @FXML
    private ListView listView;
    @FXML
    private Label code;
    @FXML
    private Label price;
    @FXML
    private Label currencyName;
    @FXML
    private Label kodWaluty;
    @FXML
    private Label nazwaWaluty;
    @FXML
    private Label sredniaCena;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ImageView image;

    @FXML
    public void initialize() {
        for (int i = 0; i < tabela().getRates().size(); i++)
            listView.getItems().add(tabela().getRates().get(i).getCurrency());
    }

    @FXML
    public void clickButton(ActionEvent e) {
        if (e.getSource().equals(close)) {
            Platform.exit();
        }
    }

    //zrobic wykresy dla kazdej waluty jakies ciekawe
    @FXML
    public void listViewClicked() throws IOException {
        //czyszczenie wykresu
        image.setVisible(false);
        lineChart.getData().clear();
        code.setVisible(true);
        price.setVisible(true);
        currencyName.setVisible(true);
        lineChart.setVisible(true);
        kodWaluty.setVisible(true);
        nazwaWaluty.setVisible(true);
        sredniaCena.setVisible(true);

        currencyName.setText(tabela().getRates().get(listView.getSelectionModel().getSelectedIndex()).getCurrency());
        price.setText(tabela().getRates().get(listView.getSelectionModel().getSelectedIndex()).getMid().toString() + "zł");
        code.setText(tabela().getRates().get(listView.getSelectionModel().getSelectedIndex()).getCode());

        //wykres ceny sredniej 7 ostatnich dni
        lineChart.setTitle("Średnia cena z ostatnich 7 dni");
        lastDays = mapper.readValue(download.download(adresy.days(tabela().getRates().get(listView.getSelectionModel().getSelectedIndex()).getCode())), LastDays.class);
        max = min = lastDays.getRates().get(0).getMid();

        for (int i = 0; i < lastDays.getRates().size(); i++) {
            if (max < lastDays.getRates().get(i).getMid()) max = lastDays.getRates().get(i).getMid();
            if (min > lastDays.getRates().get(i).getMid()) min = lastDays.getRates().get(i).getMid();
        }

        yunit = (max - min) / 5;

        XYChart.Series series = new XYChart.Series();

        xAxis.setLabel("dni");
        lineChart.setCreateSymbols(false);
        lineChart.setHorizontalGridLinesVisible(true);
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        xAxis.setLowerBound(1);
        xAxis.setUpperBound(7);
        xAxis.setTickUnit(1);
        yAxis.setLowerBound(min);
        yAxis.setTickUnit(yunit);
        yAxis.setUpperBound(max);

        for (int i = 0; i < lastDays.getRates().size(); i++)
            series.getData().add(new XYChart.Data(i + 1, lastDays.getRates().get(i).getMid()));

        lineChart.getData().add(series);
    }

    @FXML
    public void buttonEntered(Event e) {
        if (e.getSource().equals(close))  new Pulse(close).play();
    }

    public TableInfo tabela() {
        try {
            tableInfo = mapper.readValue(download.downloadTable(adresy.tabelaA), TableInfo.class);
        } catch (IOException e) {
            System.out.println("nie ");
        }
        return tableInfo;
    }
}
