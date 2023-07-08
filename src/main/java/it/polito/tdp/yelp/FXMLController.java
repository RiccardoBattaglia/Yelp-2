/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.Review;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnMiglioramento"
    private Button btnMiglioramento; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLocale"
    private ComboBox<String> cmbLocale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doRiempiLocali(ActionEvent event) {
    	this.cmbLocale.getItems().clear();
    	String citta = this.cmbCitta.getValue();
    	if(citta != null) {
    		//TODO popolare la tendina dei locali per la città selezionata
    		List<String> listaLocali= new LinkedList<>();
    		listaLocali.addAll(this.model.getLocali(citta));
        	Collections.sort(listaLocali);
        	
        	cmbLocale.getItems().addAll(listaLocali);
        	
        	btnCreaGrafo.setDisable(false);
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String citta = this.cmbCitta.getValue();
    	if(citta == null) {
    		this.txtResult.setText("Seleziona una città.\n");
    		return;
    	}
    	
    	String locale=cmbLocale.getValue();
    	if(locale == null) {
    		this.txtResult.setText("Seleziona un locale.\n");
    		return;
    	}
//    	if(locale != null) {
//    		
//    		
//    	}
    	
    	this.model.creaGrafo(locale);
    	
//    	stampa grafo
    	this.txtResult.setText("Grafo creato.\n");
    	this.txtResult.appendText("Ci sono " + this.model.nVertici() + " vertici\n");
    	this.txtResult.appendText("Ci sono " + this.model.nArchi() + " archi\n\n");
    	
    	this.txtResult.appendText("Recnsioni con il max numero di archi: " + this.model.trovaMaxArchiUscenti() + "\nOgnuno con "+this.model.dimmiNArchi(this.model.trovaMaxArchiUscenti())+" archi uscenti\n\n");
    	
    }

    @FXML
    void doTrovaMiglioramento(ActionEvent event) {
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMiglioramento != null : "fx:id=\"btnMiglioramento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	btnCreaGrafo.setDisable(true);
    	
    	
    	List<String> listaCity= new LinkedList<>();
    	listaCity.addAll(this.model.getCity());
    	Collections.sort(listaCity);
    	
    	cmbCitta.getItems().addAll(listaCity);
    }
}
