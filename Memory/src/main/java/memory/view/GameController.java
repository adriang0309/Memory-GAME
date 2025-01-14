package memory.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import memory.om.Jeu;
import memory.om.Reponse;

public class GameController implements Initializable {
	
	private boolean ismodeTriche = false;
	
	private Stage fenetrePrincipale;
	
    private int numRows = 0; // nombre de lignes initial
    private int numCols = 0; // nombre de colonnes initial
    private int size = 0;
    
    private Jeu jeu;
    
    private boolean hidevaleur = false;
    
    private ArrayList<Button> butcarteList;
	
    @FXML
    private Label nbcoups;
    @FXML
    private Label nbPairs;
    @FXML
    private Label nbPairsTrouvees;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField score;
	
	
	@FXML private RadioButton size2x2;
    @FXML private RadioButton size3x4;
    @FXML private RadioButton size4x4;
    @FXML private RadioButton size6x4;
    
    
    
	public void setFenetrePrincipale(Stage fenetrePrincipale) {
		this.fenetrePrincipale = fenetrePrincipale;
	}
	
	public void setmodetriche() {
		ismodeTriche = true; 
	}
	
	
	@FXML
	private void actionQuitter() {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Fermature de l'application");
		confirmation.setHeaderText("Voulez-vous réellement quitter?");
		confirmation.initOwner(fenetrePrincipale);
		
		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		

		
		Optional<ButtonType> reponse = confirmation.showAndWait();
		
		if (reponse.isPresent()) {
			if(reponse.get() == ButtonType.YES) {
				this.fenetrePrincipale.close();
				
			} else if (reponse.get() == ButtonType.NO) {
				System.out.println("On continue");
			} else {
				System.out.println("On part pas mais on sait pas la reponse"+reponse.get());
			}
		}
		else {
			System.out.println("On continue car pas de reponse");
		}
	}
	
	@FXML
	private void actionJouer() {
		
		 switch (size) {
	        case 2:
	            numRows = 2;
	            numCols = 2;
	            break;
	        case 3:
	            numRows = 4;
	            numCols = 3;
	            break;
	        case 4:
	            numRows = 4;
	            numCols = 4;
	            break;
	        case 6:
	            numRows = 6;
	            numCols = 4;
	            break;
	        default:
	            break;
	    }

		 if (size2x2.isSelected()) {
		        size = 2;
		    } else if (size3x4.isSelected()) {
		        size = 3;
		    } else if (size4x4.isSelected()) {
		        size = 4;
		    } else if (size6x4.isSelected()) {
		        size = 6;
		    }
		 
		 initGrid(numRows, numCols);
		 
	}
	
	@FXML
	private void scoring () { //essaye de creation d'un score pas reusit
		
	}
	
	
	
	private void initGrid(int numRows, int numCols) { //genere la grille pour jouer avec des Buttons
		jeu = new Jeu((numCols * numRows)/2, ismodeTriche);
		butcarteList = new ArrayList<Button>();
		gridPane.getChildren().clear(); // efface tous les valeurs de la grille
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
            	Button button = new Button("?");
            	butcarteList.add(button);
            	button.setId("carte_" + Integer.toString(col * numRows + row));
                button.setPrefSize(50, 50);
                button.setOnAction(event -> {
                	if (event.getSource() instanceof Button) {
                		Button but = (Button) event.getSource();
                		revealButton(but);
                	}
                });
                
                gridPane.add(button, row, col);
            }
        }
        
	       
	}
	
	
	
	
	private void revealButton(Button but) { //methode qui nous permet de montrer les cartes puis la configuration du jeu
		int indiceCarte = Integer.parseInt( but.getId().split("_")[1]);
		
		if(hidevaleur == true) {
			for (int i = 0; i < butcarteList.size(); i++) {
				jeu.isCarteTrouvee(i);
				if (jeu.isCarteTrouvee(i) == false) {
					butcarteList.get(i).setText("?"); //cette ligne de texte nous permet de cacher la carte
				}
			}
			hidevaleur = false;
		}
		
		
		Reponse rep = jeu.jouer(indiceCarte);
		nbcoups.setText( Integer.toString((jeu.getNbCoupsJoues())));
		nbPairs.setText( Integer.toString((jeu.getNbPaires())));
		if(rep == Reponse.PREMIERE) {
			but.setText( Integer.toString(jeu.getCarteValeur(indiceCarte))); //cette ligne de texte nous permet de montrer la valeur de la carte.
			
			
		} else if(rep ==Reponse.GAGNE) {
			but.setText( Integer.toString(jeu.getCarteValeur(indiceCarte)));
			nbPairsTrouvees.setText( Integer.toString((jeu.getNbPairesTrouvees())));
		} else if (rep == Reponse.PERDU){
			but.setText( Integer.toString(jeu.getCarteValeur(indiceCarte)));
			hidevaleur = true;		
		}
		
		
		if (jeu.isPartieTerminee() == true) {
			Alert confirmation2 = new Alert(AlertType.CONFIRMATION);
			confirmation2.setTitle("La partie est finit");
			confirmation2.setHeaderText("Voulez-vous re-jouer?");
			confirmation2.initOwner(fenetrePrincipale);
			confirmation2.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> reponse = confirmation2.showAndWait();
			
			if (reponse.isPresent()) {
				if(reponse.get() == ButtonType.YES) {
					this.fenetrePrincipale.show();;
					
				} else if (reponse.get() == ButtonType.NO) {
					this.fenetrePrincipale.close();
				}
			
			
			}
		}
	    
	}

	
	
	
	
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}