
package Model;


public class Fou extends Piece {
    public Fou(Joueur j)
    {
        super(j);
        this.type = "fou";
        this.value = 50;
    }
    
    public boolean coupPossible(Plateau p, Coup c)
    {
        int depH = Math.abs(c.dep.x - c.fin.x);
        int depV = Math.abs(c.dep.y - c.fin.y);
        boolean depl = false;
        Point [] interm = this.getPosIntermediaire(c);
        if(depH==depV)
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
