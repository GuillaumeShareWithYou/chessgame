
package View;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.Label;
import Model.*;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MinuteurView extends Label implements Observer {
    
    private Time tempsRestant;
    private  Minuteur minuteur;
    public MinuteurView(Model model)
    {
        this.minuteur = model.getMinuteur();
        minuteur.setObserver(this);
        this.tempsRestant = minuteur.getTime();
        this.setText(tempsRestant.toString());
        this.setStyle("-fx-text-fill : darkblue");
        this.setFont(Font.font("System", FontWeight.BOLD, 40));
    }
    
    
    @Override
    public void update(Observable o, Object o1) {
        // System.out.println("temps modifié dans la vue");
        tempsRestant = minuteur.getTime();
        //System.out.println(tempsRestant.toString());
        Label myLabel = this;
        Platform.runLater(new Runnable(){
            
            
            @Override
            public void run() {
                myLabel.setText(tempsRestant.toString());
                if(minuteur.isLow())
                {
                    myLabel.setStyle("-fx-text-fill : red");
                }else{
                    myLabel.setStyle("-fx-text-fill : darkblue");
                }
            }
        });
        
    }
    
    
}
