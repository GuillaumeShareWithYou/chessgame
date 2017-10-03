
package Model;




public class Pion extends Piece {
    private boolean premierCoup;
    public Pion(Joueur _j) {
        super(_j);
        this.type = "pion";
        this.premierCoup = true;
        this.value = 10;
    }
    public void consommerPremierCoup() {
    	this.premierCoup = false;
    }
    public boolean coupPossible(Plateau p, Coup c)
    {
        boolean depl = false;
        Point [] interm = this.getPosIntermediaire(c);
        
        int depH = c.fin.x -c.dep.x;
        int depV = c.fin.y - c.dep.y;
        boolean premier = (this.premierCoup)? true : false;
        
        if(this.getJoueur() == p.getJoueur1())
        {
            //les pions blancs montent
            if(depV == -1 || (premier && depV == -2))
            {
                if(depH == 0 && p.getEtatPlateau(c.fin.x, c.fin.y)==null)
                {
                    depl = true;
                    for (Point interm1 : interm) {
                        if (p.getEtatPlateau(interm1) != null) {
                            depl = false;
                        }
                    }
                }else if(Math.abs(depH)==1 && Math.abs(depV)==1 && p.getEtatPlateau(c.fin.x, c.fin.y)!=null)
                {
                    depl = true;
                    for (Point interm1 : interm) {
                        if (p.getEtatPlateau(interm1) != null) {
                            depl = false;
                        }
                    }
                }
            }
        }else{
            //les pions noirs descendent
            if(depV == 1 || (premier && depV == 2))
            {
                if(depH == 0 && p.getEtatPlateau(c.fin.x, c.fin.y)==null)
                {
                    //deplacement vertical correct, pas de piece en avant
                   depl = true;
                    for (Point interm1 : interm) {
                        if (p.getEtatPlateau(interm1) != null) {
                            depl = false;
                        }
                    }
                }else if(Math.abs(depH)==1 && Math.abs(depV)==1 && p.getEtatPlateau(c.fin.x, c.fin.y)!=null)
                {
                    //deplacement diagonale d'une case en mangeant
                    depl = true;
                    for (Point interm1 : interm) {
                        if (p.getEtatPlateau(interm1) != null) {
                            depl = false;
                        }
                    }
                }
            }
        }
       // if(depl) this.premierCoup = false;
        return depl;
    }
    
    
    
}
