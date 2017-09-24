
package Model;


public class Joueur {
  private  String nom;
  private  Couleur c;
  private  int score;
    public Joueur(Couleur _c)
    {
        this.nom = "inconnu";
        this.c = _c;
        this.score = 0;
    }
   public Joueur(String _nom, Couleur _c)
    {
        this.nom = _nom;
        this.c = _c;
    }
   public void setNom(String _nom)
   {
       this.nom = _nom;
   }
   public String getNom()
   {
       return this.nom;
   }
   public String getCouleur()
   {
       if(this.c==Couleur.BLANC) return "blanc";
       return "noir";
   }
   
   public void scorer(int montant)
   {
       this.score += montant;
   }
   public int getScore()
   {
       return this.score;
   }
}
