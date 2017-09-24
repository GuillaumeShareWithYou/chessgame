

package Model;


public class Cavalier extends Piece {
    public Cavalier(Joueur j)
    {
        super(j);
        this.type = "cavalier";
        this.value = 200;
    }

    @Override
    public boolean coupPossible(Plateau p, Coup c) {
       boolean depl = false;
       
       if((Math.abs(c.dep.x-c.fin.x) == 2 && Math.abs(c.dep.y - c.fin.y) == 1) || (Math.abs(c.dep.x-c.fin.x) == 1 && Math.abs(c.dep.y - c.fin.y) == 2))
       {
           depl = true;
       }
       
       return depl;
    }
}
