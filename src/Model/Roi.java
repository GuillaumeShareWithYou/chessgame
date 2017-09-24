
package Model;


public class Roi extends Piece {
    public Roi(Joueur j)
    {
        super(j);
        this.type = "roi";
        this.value = 1000;
    }
    
    public boolean coupPossible(Plateau p, Coup c)
    {
        boolean depl = false;
        
        int absH = Math.abs(c.dep.x - c.fin.x);
        int absV = Math.abs(c.dep.y - c.fin.y);
        Piece arrivee = p.getEtatPlateau(c.fin);
        if(absH<=1 && absV<=1)
        {
            depl = true;
            if(arrivee != null)
            {
                //le roi ne peut pas manger le roi adverse
                if((arrivee.getType()).equals("roi")) depl = false;
            }
            
        }
        return depl;
    }
}
