package main.view;

import java.io.File;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import main.MainGraph;
import main.util.FileHandler;

public class RootLayoutController {
	
	private MainGraph mainGraph;
	
	
	public void setMainGraph(MainGraph mainGraph) {
        this.mainGraph = mainGraph;
    }
	
	@FXML
	public void handleQuit() {
		System.exit(0);
	}
	
	@FXML
	public void handleLoad() {
		
		FileChooser chooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
		chooser.getExtensionFilters().add(extFilter);
		
		File file = chooser.showOpenDialog(mainGraph.getPrimaryStage());
		
		if (file != null) {
			mainGraph.loadDataFromFile(file);
		}
	}
	
	@FXML 
	public void handleLoadFromClipboard() {
		String data = FileHandler.getStringFromClipboard();
		if(data == null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data from clipborad");
	        alert.setContentText("Could not load data from clipboard!\nPlease check if you copied something!");
	        alert.showAndWait();
	        return;
		}
		try {
			JSONObject json = new JSONObject(data);
			mainGraph.setJSONObject(json);
			mainGraph.getPrimaryStage().setTitle("Data Visualizer - data from clipboard");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data as .json!");
	        alert.setContentText("Could not save data as json object!\nPlease check if you copied the entire string!");
	        alert.showAndWait();
		}
	}
	
	@FXML
	public void handleSave() {
		File prefDataPath = mainGraph.getDataFilePath();
		if(prefDataPath != null) {
			mainGraph.saveDataToFile(prefDataPath);
		} else {
			handleSaveAs();
		}
	}
	
	@FXML
	public void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(mainGraph.getPrimaryStage());
        
        if(file != null) {
        	if(!file.getPath().endsWith(".json")) {
        		file = new File(file.getPath() + ".json");
        	}
        	mainGraph.saveDataToFile(file);
        }
	}
	
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(mainGraph.getVersion());
        alert.setHeaderText("Wunderschönes Tool zur Visualiserung 8i");
        alert.setContentText("Author: Palle Breitner");
        alert.showAndWait();
	}

}
