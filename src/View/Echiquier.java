
package View;
import Model.*;
import java.util.Observable;
import java.util.Observer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javax.swing.event.HyperlinkEvent;


public class Echiquier extends GridPane implements Observer {
    private Model m;
    private Plateau p;
    private Case cases [][];
    private Point caseSelectionnee;
    public Echiquier(Model _m)
    {
        this.m = _m;
        this.p = m.getPlateau();
        cases = new Case[8][8];
        for(int i=0;i<8;i++)
        {
            for(int j=0; j<8; j++)
            {
                cases[i][j] = new Case(i+j);
                cases[i][j].setIcone(this.getType(i, j), this.getCouleur(i, j));
                cases[i][j].setOnAction(new CaseHandler(this.m,i,j));
                this.add(cases[i][j], i,j);
            }
        }
        
    }
    
    
    private String getType(int i, int j)
    {
        if( m.getEtatPlateau(i, j)!=null)
        {
            return m.getEtatPlateau(i, j).getType();
        }
        return "";
    }
    private String getCouleur(int i, int j)
    {
        if( m.getEtatPlateau(i, j)==null) return "";
        return m.getEtatPlateau(i, j).getJoueur().getCouleur();
    }
    @Override
    public void update(Observable o, Object o1) {
        
        for(int i=0;i<8;i++)
        {
            for(int j=0; j<8; j++)
            {
                cases[i][j].setIcone(this.getType(i, j), this.getCouleur(i, j));
            }
        }
        
        /**
         * On efface l'ancienne bordure en faisant attentiion au début de partie
         */
        if(this.caseSelectionnee!=null)
        {
            cases[this.caseSelectionnee.x][this.caseSelectionnee.y].setBorder(null);
            cases[this.caseSelectionnee.x][this.caseSelectionnee.y].setSelection(false);
            cases[this.caseSelectionnee.x][this.caseSelectionnee.y].setOpacity(0.90);
        }
        /**
         * On met une bordure verte sur la case selectionnée
         */
        Point temp = p.getPointClique();
        if(temp!=null)
        {
            
            cases[temp.x][temp.y].setSelection(true);
            cases[temp.x][temp.y].setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID,new CornerRadii(50),
                    new BorderWidths(4))));
            this.caseSelectionnee = temp;
        }
        
    }
    
}
