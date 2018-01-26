package main;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import org.json.JSONObject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.util.FileHandler;
import main.view.RootLayoutController;
import main.view.StatisticSceneController;


public class MainGraph extends Application {
		
	private Stage primaryStage;
	private BorderPane rootLayout;
	private JSONObject data = null;
	private StatisticSceneController sc;
	private final String VERSION = "Visualizer V1.00a";
		 
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Data Visualizer");
        
        initRootLayout();
        showStatGraph();
        File path = getDataFilePath();
        if(path != null) {
        	loadDataFromFile(path);
        }
		
	}
	
	public void showStatGraph() {
		try {
      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGraph.class.getResource("view/StatisticScene.fxml"));
            AnchorPane inputFunction = (AnchorPane) loader.load();

            rootLayout.setCenter(inputFunction);
            
            StatisticSceneController controller = loader.getController();
            controller.setMainGraph(this);
            sc = controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	private void initRootLayout() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGraph.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainGraph(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public void loadDataFromFile(File file) {
		try {
			String fileData = FileHandler.readFile(file);
			JSONObject json = new JSONObject(fileData);
			this.data = json;
			setDataFilePath(file);
			sc.loadNewJsonData();
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath() +"\n(" + e.getMessage() + ")");

	        alert.showAndWait();
		}
	}
	
	public void saveDataToFile(File file) {
		try {
			if(data == null) {
				throw new Exception();
			}
			String jsonAsString = data.toString(1);
			FileHandler.saveStringToFile(file, jsonAsString);
			setDataFilePath(file);
		} catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
		}
	}
	
	
	public File getDataFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainGraph.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	public void setDataFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainGraph.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("Data Visualizer - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("Data Visualizer");
	    }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public JSONObject getJson() {
		return data;
	}
	
	public void setJSONObject(JSONObject json) {
		this.data = json;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public String getVersion() {
		return VERSION;
	}

	
}
