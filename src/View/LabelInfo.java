
package View;

import java.util.Observable;
import java.util.Observer;

import Model.Info;
import Model.Model;
import Model.Plateau;
import javafx.application.Platform;
import javafx.scene.control.Label;;

public class LabelInfo extends Label implements Observer {
    private   Model m;
    private  Plateau p;
    public LabelInfo(Model m)
    {
        super();
        this.p = m.getPlateau();
        p.addObserver(this);
        this.setStyle("-fx-text-fill : red;");
        
        p.setObserverInfo(this);
        
    }
    @Override
    public void update(Observable o, Object o1) {
        
        Label myLabel = this;
        
        Platform.runLater(new Runnable(){  @Override
        public void run() {
            myLabel.setText(p.getInfo());
            if(myLabel.getText()==Info.COUP_REUSSI.toString())
            {
                myLabel.setStyle("-fx-text-fill : green;");
            }else{
                myLabel.setStyle("-fx-text-fill : red;");
            }
            
        }
        });
        
        
        
        
    }
    
}
