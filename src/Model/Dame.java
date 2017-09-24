
package Model;


public class Dame extends Piece {
    public Dame(Joueur j)
    {
        super(j);
        this.type = "dame";
        this.value = 800;
    }
    
    public boolean coupPossible(Plateau p, Coup c)
    {
        boolean flag = false;
        int depH = Math.abs(c.dep.x - c.fin.x);
        int depV = Math.abs(c.dep.y - c.fin.y);
        if(depH==0 || depV==0 || depH==depV)
        {
            flag = true;
            Point [] interm = this.getPosIntermediaire(c);
            for (int i = 0; i < interm.length; i++) {
                if (p.getEtatPlateau(interm[i]) != null) flag = false;
            }
        }
        return flag;
    }
}
