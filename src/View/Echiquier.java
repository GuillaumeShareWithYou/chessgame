
package View;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Model.Model;
import Model.Plateau;
import Model.Point;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class Echiquier extends GridPane implements Observer {
    private Model m;
    private Plateau p;
    private Case cases [][];
    private Point caseSelectionnee;
    private List<Point> casesAtteignables = new ArrayList<>();
    private List<Point> anciennesCasesAtteignables = new ArrayList<>();
    private List<Point> casesMangeables = new ArrayList<>();
    private List<Point> anciennesCasesMangeables = new ArrayList<>();
    private List<Point> casesCheckMate = new ArrayList<>();
    private List<Point> anciennesCheckMate = new ArrayList<>();
    public List<Point> getCasesCheckMate() {
		return casesCheckMate;
	}


	public void setCasesCheckMate(List<Point> casesCheckMate) {
		this.casesCheckMate = casesCheckMate;
	}


	public List<Point> getCasesAtteignables() {
		return casesAtteignables;
	}


	public void setCasesAtteignables(List<Point> casesAtteignables) {
		this.casesAtteignables = casesAtteignables;
	}


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
      
        Point temp = p.getPointClique();
        
        
        
        if(temp!=null)
        {
            
            cases[temp.x][temp.y].setSelection(true);
            cases[temp.x][temp.y].setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID,new CornerRadii(50),
                    new BorderWidths(4))));
            
            this.caseSelectionnee = this.p.getPointClique();
            this.casesAtteignables = new ArrayList(this.p.getCasesAtteignables());
            this.casesMangeables = new ArrayList(this.p.getCasesMangeables());
            this.casesCheckMate = new ArrayList(this.p.getCasesEchecEtMat());
            if(!this.anciennesCasesAtteignables.isEmpty())
            {
            	for(Point p: this.anciennesCasesAtteignables) {
            		cases[p.x][p.y].setBorder(null);
            		cases[p.x][p.y].setAtteignable(false);
            		cases[p.x][p.y].mettreCouleurNaturelle();
            	}
            }
            if(!this.anciennesCheckMate.isEmpty())
            {
            	for(Point p: this.anciennesCheckMate) {
            		cases[p.x][p.y].setBorder(null);
            		cases[p.x][p.y].setAtteignable(false);
            		cases[p.x][p.y].mettreCouleurNaturelle();
            	}
            }
            if(!this.anciennesCasesMangeables.isEmpty())
            {
            	for(Point p: this.anciennesCasesMangeables) {
            		cases[p.x][p.y].setBorder(null);
            		cases[p.x][p.y].setCheckMate(false);
            		cases[p.x][p.y].mettreCouleurNaturelle();
            		
            	}
            }
            if(!this.casesCheckMate.isEmpty())
            {
            	
            	   for(Point p: casesCheckMate) {
            		   cases[p.x][p.y].setCheckMate(true);
                  	 cases[p.x][p.y].setBorder(new Border(new BorderStroke(Color.BLACK,
                               BorderStrokeStyle.SOLID,new CornerRadii(0),
                               new BorderWidths(3))));
            		   cases[p.x][p.y].setStyle("-fx-background-color : red");
            		  
                  }
            }
            if(!this.casesAtteignables.isEmpty())
            {
            	   for(Point p: casesAtteignables) {
            		   cases[p.x][p.y].setAtteignable(true);
                  	 cases[p.x][p.y].setBorder(new Border(new BorderStroke(Color.BLACK,
                               BorderStrokeStyle.SOLID,new CornerRadii(0),
                               new BorderWidths(3))));
            		   cases[p.x][p.y].setStyle("-fx-background-color : green");
                  }
            }
            
           
            if(!this.casesMangeables.isEmpty())
            {
            	
            	   for(Point p: casesMangeables) {
            		   cases[p.x][p.y].setMangeable(true);
                  	 cases[p.x][p.y].setBorder(new Border(new BorderStroke(Color.BLACK,
                               BorderStrokeStyle.SOLID,new CornerRadii(0),
                               new BorderWidths(3))));
            		   cases[p.x][p.y].setStyle("-fx-background-color : orange");
            		  
                  }
            }
           
         this.anciennesCasesAtteignables = new ArrayList<>(this.casesAtteignables);
         this.anciennesCasesMangeables = new ArrayList<>(this.casesMangeables);
         this.anciennesCheckMate = new ArrayList<>(this.casesCheckMate);
        }
        
    }
    
}
