package application;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jxl.write.WritableCellFormat;

public class Main extends Application {

	private static BorderPane mainLayout;
	private Stage primaryStage;
	private WritableCellFormat times;
	
	public void start (Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Project Calendar");
		
		showMainView();
		showMainItems();
	}
	
	private  void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainView.fxml"));
		mainLayout = loader.load();
		Scene s = new Scene(mainLayout);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	public static void showMainItems() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainItems.fxml"));
		BorderPane mainItems = loader.load();
		mainLayout.setCenter(mainItems);
	}
	
	public static void showAddJobScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("jobTable/addJob.fxml"));
		BorderPane addJob = loader.load();
		mainLayout.setCenter(addJob);
	}
	
	public static void showEditJobScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("jobTable/editJob.fxml"));
		BorderPane addJob = loader.load();
		mainLayout.setCenter(addJob);
	}
	
	public static void showAddResourceScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("resourceTable/addResource.fxml"));
		BorderPane addResource = loader.load();
		mainLayout.setCenter(addResource);
	}
	
	public static void main(String[] args) {
		launch();
	}
}