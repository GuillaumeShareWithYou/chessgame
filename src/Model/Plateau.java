
package Model;

import java.util.Observable;
import java.util.Observer;


public class Plateau extends Observable{
    private Piece plateau[][];
    private Joueur j1;
    private Joueur j2;
    private String info = new String();
    private Point pointClique;
    private Model model;
  
    Observer labelInfo; // Observer particulier pour eviter un probleme de multithread sur le plateau
    public Plateau(Model m)
    {
        this.model = m;
        plateau = new Piece [8][8];
        this.j1 = m.getJoueur1();
        this.j2 = m.getJoueur2();
        
        init_plateau(this.j1, this.j2);
        
    }
    /**
     * initialise le plateau, appellée dans le constructeur la premiere fois,
     * puis par la fonction init_jeu() du model pour les parties suivantes
     * @param _j1
     * @param _j2
     */
    public void init_plateau(Joueur _j1, Joueur _j2)
    {
        Point pointClique = null;
        this.j1 = _j1;
        this.j2 = _j2;
        plateau = new Piece[8][8];
        
        for (int i = 0; i < 8; i++) {
            plateau[i][6] = new Pion(j1);
        }
        for(int i = 0; i<8; i++)
        {
            plateau[i][1] = new Pion(j2);
        }
        plateau[6][0] = new Cavalier(j2);
        plateau[1][0] = new Cavalier(j2);
        plateau[6][7] = new Cavalier(j1);
        plateau[1][7] = new Cavalier(j1);
        
        plateau[2][0] = new Fou(j2);
        plateau[5][0] = new Fou(j2);
        plateau[2][7] = new Fou(j1);
        plateau[5][7] = new Fou(j1);
        
        plateau[3][0] = new Dame(j2);
        plateau[3][7] = new Dame(j1);
        
        plateau[0][0] = new Tour(j2);
        plateau[7][0] = new Tour(j2);
        plateau[0][7] = new Tour(j1);
        plateau[7][7] = new Tour(j1);
        
        plateau[4][0] = new Roi(j2);
        plateau[4][7] = new Roi(j1);
        
        
        this.setChanged();
        this.notifyObservers();
        
    }
    public Piece getEtatPlateau(int i, int j)
    {
        return this.plateau[i][j];
    }
    public Piece getEtatPlateau(Point p)
    {
        return this.plateau[p.x][p.y];
    }
    /**
     * fais toutes les verifications sur la possibilité de bouger une pièce
     *
     * @param i abscisse du clic
     * @param j ordonnée du clic
     * @param coupCourant Le coup en cours de validation
     * @param joueurCourant Le joueur du tour
     * @return true ssi piece deplacée
     */
    public boolean clic(int i, int j, Coup coupCourant, Joueur joueurCourant){
        boolean error = false;
        
        Point temp = new Point(i,j); // Position du clic
        if(coupCourant.dep==null)
        {
            
            if(this.getEtatPlateau(i, j)==null) // clic sur case vide
            {
                setInfo("Aucune pièce à cet endroit");
                error = true;
            }else if(this.getEtatPlateau(i,j).getJoueur()!=joueurCourant)
            {
                
                setInfo("Cet pièce n'est pas à vous !");
                error = true;
            }
            
            if(!error)
            {
                
                coupCourant.setDepart(temp);
                setPointClique(coupCourant.dep);
                setInfo("...");
                
            }
            
        }else if(coupCourant.dep.equals(temp)) {
            //clic sur la même case deux fois pour annuler le coup
            setInfo("Coup annulé");
            setPointClique(null);
            coupCourant.effacer();
        }else{
            
            coupCourant.setFin(temp);
            if(this.getJoueurEn(coupCourant.fin.x, coupCourant.fin.y)==joueurCourant)
            {
                
                
                coupCourant.effacer();
                coupCourant.setDepart(temp);
                setPointClique(temp);
                error = true;
                setInfo("Changement de pièce");
            }
            if(!error && appliquerCoup(coupCourant))
            {
                
                setInfo("Coup réussi");
                coupCourant.effacer();
                setPointClique(null);
                return true;
            }
            
            
        }
        return false;
        
    }
    public boolean appliquerCoup(Coup c)
    {
        // faire les verifications sur les propriétés de déplacement intrinsèque à la piece
        if(this.plateau[c.dep.x][c.dep.y].coupPossible(this, c))
        {
            
            Piece depart = this.plateau[c.dep.x][c.dep.y];
            Piece fin =  this.plateau[c.fin.x][c.fin.y];
            if(fin!=null)
            {
                // On vérifie si un roi a été mangé
                if(fin.getType().equals("roi"))
                {
                    setInfo("fin de partie "+fin.getJoueur()+" a gagné");
                    setFinDePartie(true);
                }
                //mettre a jour le score :
                depart.manger(fin);
            }
            
            
            this.plateau[c.fin.x][c.fin.y] = this.plateau[c.dep.x][c.dep.y];
            this.plateau[c.dep.x][c.dep.y] = null;
            
            this.setChanged();
            notifyObservers();
            
            return true;
        }
        setInfo("deplacement impossible");
        return false;
        
        
    }
    
    //permet de savoir à qui appartient la pièce visée sans exception nullPointerException
    private Joueur getJoueurEn(int i, int j)
    {
        if(this.getEtatPlateau(i, j)!=null)
        {
            return this.getEtatPlateau(i, j).getJoueur();
        }
        return null;
    }
    public String getInfo()
    {
        return this.info;
    }
    public void setPointClique(Point p)
    {
        this.pointClique = p;
        this.setChanged();
        this.notifyObservers();
    }
    public Point getPointClique()
    {
        return this.pointClique;
    }
    /**
     * permet de changer le msg d'info et de notifier le labelInfo
     * @param msg msg à envoyer
     */
    public void setInfo(String msg)
    {
        this.info = msg;
        this.labelInfo.update(this, this);
    }
    public void setFinDePartie(boolean b)
    {
        if(b)
        {
            this.model.setAffichage(Affichage.FIN_DE_PARTIE);
            this.model.terminerPartie(); 
        }
    }
    public Joueur getJoueur1()
    {
        return this.j1;
    }
    public Joueur getJoueur2()
    {
        return this.j2;
    }
    
    public void setObserverInfo(Observer obs)
    {
        this.labelInfo = obs;
    }
}
