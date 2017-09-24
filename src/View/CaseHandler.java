
package View;
import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CaseHandler implements EventHandler<ActionEvent> {
    private  Model m;
    private  int colonne;
    private int ligne;
    public CaseHandler(Model _m, int _col, int _li)
    {
        this.m = _m;
        this.colonne = _col;
        this.ligne = _li;
    }
    
    @Override
    /**
     * Appelle la fonction clic du model pour être traité
     */
    public void handle(ActionEvent event) {
        boolean clic = this.m.clic(this.colonne, this.ligne);
        
    }
}
