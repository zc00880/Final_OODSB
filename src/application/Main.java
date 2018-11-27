package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static BorderPane mainLayout;
	private Stage primaryStage;
	
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
	
	public static void showAssignResourceScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("dateCalendar/assignResource.fxml"));
		BorderPane assignResource = loader.load();
		mainLayout.setCenter(assignResource);
	}
	
	public static void showEditJobScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("jobTable/editJob.fxml"));
		BorderPane editJob = loader.load();
		mainLayout.setCenter(editJob);
	}
	
	public static void showAddResourceScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("resourceTable/addResource.fxml"));
		BorderPane addResource = loader.load();
		mainLayout.setCenter(addResource);
	}
	
	public static void showEditResourceScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("resourceTable/editResource.fxml"));
		BorderPane editResource = loader.load();
		mainLayout.setCenter(editResource);
	}
	public static void main(String[] args) {
		launch();
	}
}