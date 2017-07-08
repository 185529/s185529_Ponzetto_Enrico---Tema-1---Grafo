/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.CountryAndNum;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<?> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	String selezione = txtAnno.getText();
    	
    	if(selezione.length()==0){
    		txtResult.appendText("ERRORE: Inserire un anno.\n");
    		return;
    	}
    	
    	int anno = 0;
    	
		try {
			
			anno = Integer.parseInt(selezione);
			
		} catch (NumberFormatException e) {
			
			txtResult.appendText("ERRORE: Inserire un anno in formato numerico.\n");
			e.printStackTrace();
			return;
			
		}
		
		if(anno<1816){
			txtResult.appendText("ERRORE: Non esistono dati per anni precedenti al 1816.\n");
			return;
		}
    	
    	List<CountryAndNum> stati = model.creaGrafo(anno);
    	
    	for(CountryAndNum cn : stati)
    		txtResult.appendText(String.format("%s: %d\n", cn.getCountry().toString(), cn.getNum()));
    	
    	return;

    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    }
    
}
