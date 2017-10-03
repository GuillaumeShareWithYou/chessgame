/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Controller;
import View.*;
import Model.*;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
/**
 *
 * @author Marie_Estelle
 */
public class MenuController implements EventHandler<ActionEvent> {
    private  View v;
    private Model model;
    private  Button btnNP1J;
    private  Button btnNP2J;
    private  Button btnI;
    private  Button btnContinuer;
    public MenuController( View v, Model model)
    {
        this.v = v;
        this.model = model;
        model.addObserver(v);
        btnNP1J = v.getBtnNP1J();
        btnNP2J = v.getBtnNP2J();
        btnI = v.getBtnI();
        btnContinuer = v.getBtnContinuer();
        btnContinuer.setOnAction(this);
        btnI.setOnAction(this);
        btnNP1J.setOnAction(this);
        btnNP2J.setOnAction(this);
//        v.getBtnQ().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent event) {
//                System.exit(0);
//            }
//        });
        
       v.getBtnQ().setOnAction((event)->System.exit(0));
        
    }
    
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource()==btnNP1J)
        {
            model.setNbJoueurs(1);
            model.setAffichage(Affichage.IDENTIFICATION);
            
        }else if(event.getSource()==btnNP2J)
        {
            model.setNbJoueurs(2);
            model.setAffichage(Affichage.IDENTIFICATION);
        }
        else if(event.getSource()==btnI){
            
            
            model.setNomJoueur1(v.getUsername());
            if(model.getNbJoueurs()==2)
            {
                model.setNomJoueur2(v.getUsername2());
            }else{
                model.setNomJoueur2("Ordinateur");
            }
            model.setAffichage(Affichage.JEU);
            
        }else if(event.getSource() == btnContinuer)
        {
            model.setAffichage(Affichage.MENU);
            model.init_jeu();
        }
    }
    
}
