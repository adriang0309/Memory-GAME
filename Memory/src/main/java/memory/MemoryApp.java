package memory;


import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import memory.view.MenuController;
import memory.view.GameController;


public class MemoryApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootPane;
	

	@Override
	public void start(Stage primaryStage) {
		
		
		this.primaryStage = primaryStage;
		this.rootPane     = new BorderPane();
		
		Scene scene = new Scene(rootPane);
	    primaryStage.setTitle("Jeu Memory");
	    primaryStage.setScene(scene);
	    loadMenu();
	    primaryStage.show();
	
	}
	
	public void loadMenu() {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Menu.fxml"));
	        BorderPane vueMenu = loader.load();
	        MenuController ctrl = loader.getController();
	        this.rootPane.setCenter(vueMenu);
	        ctrl.setMemoryApp(this);
	        
		} catch (Exception e) {
			System.out.println("Ressource FXML non disponible : Menu");
			System.exit(1);
		}
		
	}
	
	public void loadGame(boolean modetriche) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Game.fxml"));
	        BorderPane vueSimple = loader.load();
	        GameController ctrl = loader.getController();
	        if(modetriche == true) {
	        	ctrl.setmodetriche();
	        }
	        this.rootPane.setCenter(vueSimple);
	        ctrl.setFenetrePrincipale(primaryStage);
	        
	    } catch (IOException e) {
	        System.out.println("Ressource FXML non disponible : Game");
	        System.exit(1);
	    }
	}
	
	public static void main2(String[] args) {
		Application.launch(args);
	}

}
