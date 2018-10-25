package application.view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
	
public class MainItemsController {
	private Main main;
	
	@FXML
	public void goAddJob() throws IOException {
		main.showAddJobScene();
	}
	
	public void goAddResource() throws IOException {
		main.showAddResourceScene();
	}
}