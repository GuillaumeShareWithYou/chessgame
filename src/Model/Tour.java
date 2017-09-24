
package Model;

public class Tour extends Piece {
    public Tour(Joueur j)
    {
        super(j);
        this.type = "tour";
        this.value = 100;
    }
    
 
    @Override
    public boolean coupPossible(Plateau p, Coup c)
    {
        boolean depl = false;
        int depH = Math.abs(c.dep.x - c.fin.x);
        int depV = Math.abs(c.dep.y - c.fin.y);
        Point [] interm = this.getPosIntermediaire(c);
        if(depH==0 || depV==0)
        {
            depl = true;
            for (Point interm1 : interm) {
                if (p.getEtatPlateau(interm1) != null) {
                    depl = false;
                }
            }
        }
        return depl;
    }
}
