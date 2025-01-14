package memory.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import memory.MemoryApp;

public class MenuController implements Initializable {
	
	
	@FXML
	private Button butTriche;
	
	
	private MemoryApp getMemoryApp;
	

	public void setMemoryApp(MemoryApp getMemoryApp) {
		this.getMemoryApp = getMemoryApp;
	}
	
	
	@FXML
	private void actionSwitch() { //action qui permet de changer vers le mode Triche
		this.getMemoryApp.loadGame(true);
	}
	
	@FXML
	private void actionSimple() { //action qui permet de changer vers le mode simple
		this.getMemoryApp.loadGame(false);
	}
	
	
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	

}
