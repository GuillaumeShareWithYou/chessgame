/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package View;



import java.awt.Event;
import java.awt.Image;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class Case extends Button implements EventHandler<MouseEvent>{
  private  Image img;
  private  boolean selectionnee;
    public Case(int color)
    {
        setSelection(false);
        this.setOpacity(0.90);
        this.setPrefSize(75,75);
        this.setCursor(Cursor.HAND);
        if(color%2==0)
        { this.setStyle("-fx-background-color : white");
        
        }else{
            this.setStyle("-fx-background-color : rgb(0,0,160)");
            
        }
        this.setOnMouseEntered(this);
        this.setOnMouseExited(this);
    }
    
    
    
    /**
     * Fonction permettant de définir le fichier .png à charger pour chaque case de l'échiquier
     * d'où les variables type et couleur dans Piece
     * @param type type de la piece
     * @param couleur noire ou blanche
     * @return
     */
    public boolean setIcone(String type, String couleur)
    {
        if(type=="" || couleur=="")
        {
            this.setGraphic(null);
            return false;
        }
        ImageView v;
        v =  new ImageView(new javafx.scene.image.Image("file:img/"+type+"_"+couleur+".png"));
        v.setFitHeight(50);
        v.setFitWidth(50);
        this.setGraphic(v);
        return true;
    }
    
    /**
     * Permet d'afficher une bordure colorée sur la case survolée par le curseur
     * à condition qu'elle ne soit pas dans l'état selectionnée
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        
        {
            
            //Quand la souris entre, on met la bordure
            if(MouseEvent.MOUSE_ENTERED == event.getEventType())
            {
                this.setOpacity(1.0);
                if(!estSelectionnee())
                {
                    this.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID,new CornerRadii(0),
                            new BorderWidths(4))));
                }
                
            }
            // Quand elle sort , on enlève la bordure
            else if(MouseEvent.MOUSE_EXITED == event.getEventType())
            {
                if(!estSelectionnee())
                {
                    this.setBorder(null);
                    this.setOpacity(0.90);
                }
                 
                
            }
        }
    }
    public void setSelection(boolean b)
    {
        this.selectionnee = b;
    }
    public boolean estSelectionnee()
    {
        return this.selectionnee;
    }
    
}
