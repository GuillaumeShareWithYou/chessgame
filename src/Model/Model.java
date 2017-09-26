
package Model;

import java.util.Locale;
import java.util.Observable;


public class Model extends Observable {
    private boolean partieTerminee;
    private Plateau plateau;
    private Affichage affichage;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;
    private Coup coupCourant;
    private  int nbJoueursHumain;
    private String info;
    private  Minuteur minuteur;
    public Model()
    {
        this.affichage = Affichage.MENU;
        joueur1 = new Joueur(Couleur.BLANC);
        joueur2 = new Joueur(Couleur.NOIR);
        joueurCourant = joueur1;
        this.plateau = new Plateau(this);
        coupCourant = new Coup();
        this.partieTerminee = false;
        this.minuteur = new Minuteur(this);
        
    }
    /**
     * Permet de recommencer une partie sans avoir à toucher au controller,
     * qui a toujours une référence sur un certain model et un certain plateau,
     * donc je ne peux pas appeller les constructeurs pour lancer des parties en boucle
     */
    public void init_jeu()
    {
        this.partieTerminee = false;
        joueur1 = new Joueur(Couleur.BLANC);
        joueur2 = new Joueur(Couleur.NOIR);
        joueurCourant = joueur1;
        this.minuteur = new Minuteur(this);
        this.plateau.init_plateau(this.joueur1, this.joueur2);
        this.setChanged();
        this.notifyObservers();
        
    }
    /**
     * Permet de récupérer la position d'un clic sur l'echiquier
     * @param i
     * @param j
     * @return vrai ssi coup appliqué au plateau
     */
    public boolean clic(int i, int j)
    {
        
        
        if(plateau.clic( i,  j, coupCourant,  joueurCourant))
        {
            
            if(!partieTerminee)
            {
                joueurCourant = getJoueurSuivant();
                this.minuteur.reset();
            }else {
            	this.minuteur.stop();
            	
            }
            
            return true;
        }
        return false;
    }
    /**
     * permet à la vue de s'orienter suivant l'avancement de l'IHM
     * @return
     */
    public Affichage getAffichage()
    {
        return this.affichage;
    }
    /**
     * Avance dans les menus tout en mettant la vue a jour
     * @param a
     */
    public void setAffichage(Affichage a)
    {
        this.affichage = a;
        this.setChanged();
        this.notifyObservers();
        
        //On demarre le chrono quand la partie commence
        if(a == Affichage.JEU)
        {
            this.minuteur.start();
        }
    }
    
    public void setNomJoueur1(String nom)
    {
        
        String name = "";
        if(nom.equals(""))
        {
            name = "Inconnu";
        }else{
            char [] nomOK = nom.toCharArray();
            nomOK[0] = Character.toUpperCase(nomOK[0]);
            name = new String(nomOK);
        }
        
        joueur1.setNom(name);
    }
    public void setNomJoueur2(String nom)
    {
        String name = "";
        if(nom.equals(""))
        {
            
            if(this.joueur1.getNom() == "Inconnu")
            {
                name = "Inconnu 2";
            }else{
                name = "Inconnu";
            }
            
        }else{
            char [] nomOK = nom.toCharArray();
            nomOK[0] = Character.toUpperCase(nomOK[0]);
            name = new String(nomOK);
        }
        joueur2.setNom(name);
    }
    
    public String getNomJoueurCourant()
    {
        return joueurCourant.getNom();
    }
    /**
     * Fais passer le tour en notifiant la vue
     * @return
     */
    public Joueur getJoueurSuivant()
    {
        if(joueurCourant==joueur1)
        {
            joueurCourant = joueur2;
        }else{
            joueurCourant = joueur1;
        }
        setChanged();
        notifyObservers();
        return joueurCourant;
    }
    /**
     * retoure la piece à la position (i,j)
     * @param i
     * @param j
     * @return
     */
    public Piece getEtatPlateau(int i, int j)
    {
        return this.plateau.getEtatPlateau(i, j);
    }
    public Piece getEtatPlateau(Point p)
    {
        return this.plateau.getEtatPlateau(p);
    }
    public Plateau getPlateau()
    {
        return this.plateau;
    }
    public int getNbJoueurs()
    {
        return this.nbJoueursHumain;
    }
    public void setNbJoueurs(int nb)
    {
        this.nbJoueursHumain = nb;
    }
    /**
     * le message d'informations en bas de la vue
     * @return
     */
    public String getInfo()
    {
        return this.info;
    }
    public Joueur getJoueurCourant()
    {
        return this.joueurCourant;
    }
    public String getNomJoueur1()
    {
        return this.joueur1.getNom();
    }
    public String getNomJoueur2()
    {
        return this.joueur2.getNom();
    }
    public Joueur getJoueur1()
    {
        return this.joueur1;
    }
    public Joueur getJoueur2()
    {
        return this.joueur2;
    }
    
    public Minuteur getMinuteur()
    {
        return this.minuteur;
    }
    public boolean isPartieTerminee()
    {
        return this.partieTerminee;
    }
    public void terminerPartie()
    {
        this.partieTerminee = true;
      
    }
}
