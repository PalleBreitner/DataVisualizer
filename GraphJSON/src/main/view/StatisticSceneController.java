package main.view;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.MainGraph;
import main.model.SeriesMonth;
import main.model.Sub;
import main.model.SubTableData;
import main.util.ColorHandler;

public class StatisticSceneController {
	
	@FXML
	private TextField jsonStringField;
	@FXML
	private Button parseButton;
	@FXML
	private ComboBox<String> comboMonth;
	@FXML
	private ComboBox<String> comboSub;
	@FXML
	private LineChart<Number, Number> chart;
	@FXML
	private NumberAxis amount;
	@FXML
	private NumberAxis day;
	@FXML 
	private TableView<SubTableData> table;
	@FXML
	private TableColumn<SubTableData, String> sub; 
	@FXML
	private TableColumn<SubTableData, String> total; 
	@FXML
	private TableColumn<SubTableData, String> days; 
	
	private String selectedMonth;
	private String selectedSub = "J/V";
	private MainGraph mainGraph;
	
	private SeriesMonth jan;
	private SeriesMonth feb;
	private SeriesMonth mar;
	private SeriesMonth apr;
	private SeriesMonth may;
	private SeriesMonth jun;
	private SeriesMonth jul;
	private SeriesMonth aug;
	private SeriesMonth sep;
	private SeriesMonth okt;
	private SeriesMonth nov;
	private SeriesMonth dec;
	
	private SeriesMonth all;
	
	@FXML
    public void initialize() {
		
		//Setup Chart
		chart.setLegendVisible(false);
		day.setTickLabelFormatter(new NumberAxis.DefaultFormatter(day));
		day.setTickUnit(1);
		//Setup TableView
		table.setPlaceholder(new Label(""));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		sub.setCellValueFactory(cellData -> cellData.getValue().getSub());
		total.setCellValueFactory(cellData -> cellData.getValue().getTotal());
		days.setCellValueFactory(cellData -> cellData.getValue().getDays());
		
        //Setup Combobox for months
		comboMonth.setValue("Select Month...");
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			    	"All Months",
			        "January",
			        "February",
			        "March",
			        "April",
			        "May",
			        "June",
			        "July",
			        "August",
			        "September",
			        "Oktober",
			        "November",
			        "December"
			    );
		comboMonth.getItems().addAll(options);		
		comboMonth.valueProperty().addListener(new ChangeListener<String>() {
				@SuppressWarnings("rawtypes")
				@Override
				public void changed(ObservableValue ov, String t, String t1)  {
					selectedMonth = t1;
					try {
						if(allDataDefined()) {
							showMonth(selectedMonth);
							System.out.println("Success!");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		});
		
		//Setup ComboBox for subs
		
		comboSub.setValue("J/V");
		ObservableList<String> subList = 
			    FXCollections.observableArrayList(
			    	//"All",
			        "J/V",
			        "Kip",
			        "Peter",
			        "Kra",
			        "Pheni",
			        "Emma",
			        "N2O",
			        "Alk"
			    );
		comboSub.getItems().addAll(subList);		
		comboSub.valueProperty().addListener(new ChangeListener<String>() {
				@SuppressWarnings("rawtypes")
				@Override
				public void changed(ObservableValue ov, String t, String t1)  {
					if(selectedSub.equals(t1)) return;
					selectedSub = t1;
					try {
						if(allDataDefined()) {
							showMonth(selectedMonth);
							System.out.println("Success!");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		});
		
    }
	
	public void loadNewJsonData() throws JSONException, Exception {
		JSONObject json = mainGraph.getJson();
		if(json == null) {
			//Do Alert
			return;
		}
		Iterator<?> keys = json.keys();
		while(keys.hasNext()) {
			String month = (String)keys.next();
			switch(month) {
				case "Januar" :
					jan = new SeriesMonth(json.getJSONArray(month));
					break;
				case "Februar" :
					feb = new SeriesMonth(json.getJSONArray(month));
					break;
				case "März" :
					mar = new SeriesMonth(json.getJSONArray(month));
					break;
				case "April" :
					apr = new SeriesMonth(json.getJSONArray(month));
					break;
				case "Mai" :
					may = new SeriesMonth(json.getJSONArray(month));
					break;
				case "Juni" :
					jun = new SeriesMonth(json.getJSONArray(month));
					break;
				case "Juli" :
					jul = new SeriesMonth(json.getJSONArray(month));
					break;
				case "August" :
					aug = new SeriesMonth(json.getJSONArray(month));
					break;
				case "September" :
					sep = new SeriesMonth(json.getJSONArray(month));
					break;
				case "Oktober" :
					okt = new SeriesMonth(json.getJSONArray(month));
					break;
				case "November" :
					nov = new SeriesMonth(json.getJSONArray(month));
					break;
				case "Dezember" :
					dec = new SeriesMonth(json.getJSONArray(month));
					break;
			}
		}
		try {
			if(allDataDefined()) {
				ArrayList<SeriesMonth> allData = new ArrayList<SeriesMonth>();
				allData.add(jan);allData.add(feb);allData.add(mar);allData.add(apr);allData.add(may);allData.add(jun);
				allData.add(jul);allData.add(aug);allData.add(sep);allData.add(okt);allData.add(nov);allData.add(dec);
				all = new SeriesMonth(allData);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("All is not defined because not every month is loaded!");
		        alert.setContentText("Check if your data contains every month!\n Otherwise copy a new .json from sheets...");
		        alert.showAndWait();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showMonth(String month) {
		
		//Get selected month
		SeriesMonth actualMonth;
		boolean monthViewSelected = true;
		switch(month) {
			case "January":
				actualMonth = jan;
				break;
			case "February":
				actualMonth = feb;
				break;
			case "March":
				actualMonth = mar;
				break;
			case "April":
				actualMonth = apr;
				break;
			case "May":
				actualMonth = may;
				break;
			case "June":
				actualMonth = jun;
				break;
			case "July":
				actualMonth = jul;
				break;
			case "August":
				actualMonth = aug;
				break;
			case "September":
				actualMonth = sep;
				break;
			case "Oktober":
				actualMonth = okt;
				break;
			case "November":
				actualMonth = nov;
				break;
			case "December":
				actualMonth = dec;
				break;
			case "All Months" :
				monthViewSelected = false;
				actualMonth = all;
				break;
			default:
				//TODO Handle
				System.out.println(month+ " not found!");
				return;
		}
		
		Sub actualSub = null;
		boolean allSelected = false;
		switch(selectedSub) {
			case "J/V":
				actualSub = actualMonth.getJv();
				break;
			case "Kip":
				actualSub = actualMonth.getKip();
				break;
			case "Peter":
				actualSub = actualMonth.getPeter();
				break;
			case "Kra":
				actualSub = actualMonth.getKra();
				break;
			case "Emma":
				actualSub = actualMonth.getEmma();
				break;
			case "Pheni":
				actualSub = actualMonth.getPheni();
				break;
			case "N2O":
				actualSub = actualMonth.getN2o();
				break;
			case "Alk":
				actualSub = actualMonth.getAlk();
				break;
			case "All" :
				//allSelected = true;
			default:
				//TODO Add All
				return;
		}

		//Setup TableView
		showTable(actualMonth);
		
		//Setup Chart
		chart.getData().clear();
		setAxisStyle(monthViewSelected);
		if(!allSelected) {
			chart.getData().add(actualSub.getSub());
			// Style the data accordingly
			Node line = actualSub.getSub().getNode().lookup(".chart-series-line");
			line.setStyle("-fx-stroke: rgba(" + ColorHandler.colorToRgb(actualSub.getRGBColor()) + ", 1.0);");
			line.applyCss();
			for(XYChart.Data<Number, Number> data : actualSub.getSub().getData()) {
				Node symbol = data.getNode().lookup(".chart-line-symbol");
				symbol.setStyle("-fx-background-color: "+ ColorHandler.colorToHex(actualSub.getRGBColor()) +", "+ ColorHandler.colorToHex(actualSub.getRGBColor().darker()) +";\n" +
						"-fx-background-radius: 5px;"
						);
			}
		} else {
			//TODO Fix duplicate series added
			for(Sub sub : allSubs(actualMonth)) {
					chart.getData().add(sub.getSub());
			}
		}
	}
	
	public void showTable(SeriesMonth actualMonth) {
		
		ObservableList<SubTableData> data = FXCollections.observableArrayList();
		
		if(actualMonth.getJv().getDays() > 0)
		data.add(new SubTableData("Jv",actualMonth.getJv().getTotal(),actualMonth.getJv().getDays(),"g"));
		if(actualMonth.getKip().getDays() > 0)
		data.add(new SubTableData("Kip",actualMonth.getKip().getTotal(),actualMonth.getKip().getDays()));
		if(actualMonth.getPeter().getDays() > 0)
		data.add(new SubTableData("Peter",actualMonth.getPeter().getTotal(),actualMonth.getPeter().getDays()));
		if(actualMonth.getKra().getDays() > 0)
		data.add(new SubTableData("Kra",actualMonth.getKra().getTotal(),actualMonth.getKra().getDays(),"g"));
		if(actualMonth.getPheni().getDays() > 0)
		data.add(new SubTableData("Pheni",actualMonth.getPheni().getTotal(),actualMonth.getPheni().getDays(),"g"));
		if(actualMonth.getEmma().getDays() > 0)
		data.add(new SubTableData("Emma",actualMonth.getEmma().getTotal(),actualMonth.getEmma().getDays(),"mg"));
		if(actualMonth.getN2o().getDays() > 0)
		data.add(new SubTableData("N2O",actualMonth.getN2o().getTotal(),actualMonth.getN2o().getDays()," caps"));
		if(actualMonth.getAlk().getDays() > 0)
		data.add(new SubTableData("Alk",actualMonth.getAlk().getTotal(),actualMonth.getAlk().getDays()," Biers"));
		
		table.setItems(data);
	}
	
	public void setMainGraph(MainGraph mainGraph) {
        this.mainGraph = mainGraph;
    }
	
	public boolean allDataDefined() {
		if(jan != null && feb != null && mar != null && apr != null && may != null && jun != null && jul != null && aug != null && sep != null && okt != null && nov != null && dec != null) 
		return true;
		return false;	
	}
	
	public ArrayList<Sub> allSubs(SeriesMonth month) {
		ArrayList<Sub> subs = new ArrayList<Sub>();
		subs.add(month.getJv());
		subs.add(month.getKip());
		subs.add(month.getPeter());
		subs.add(month.getEmma());
		subs.add(month.getKra());
		subs.add(month.getPheni());
		subs.add(month.getN2o());
		subs.add(month.getAlk());
		return subs;
	}
	
	private void setAxisStyle(boolean month) {
		//Set all difference styling between month and year
		if(month) {
			day.setLabel("Days");
		} else {
			day.setLabel("Months");
		}
		
		//Set all styling which are equal for both cases
		amount.setLabel("Amount");
	}
	
	
}
