
package Model;


public abstract class Piece {
    protected boolean estVivante;
    protected String type;
    protected Joueur j;
    protected int value;
    public Piece(Joueur _j)
    {
        this.j = _j;
        this.estVivante = true;
        
    }
    public Joueur getJoueur()
    {
        return this.j;
    }
    public String getType()
    {
        return type;
    }
    public int getValue()
    {
        return this.value;
    }
    /**
     * Fonction non utilisée ici car test de fin de partie sur l'evenement
     * et les joueurs n'ont pas un attribt de type Piece non plus,
     * les pieces mangées sont écrasées par celle qui arrive, et donc une fois 
     * mangées, on ne les retrouvera plus.
     * @return 
     */
    public boolean estVivante()
    {
        return this.estVivante;
    }
    /**
     * Permet d'augementer le score du joueur en fonction de la valeur de la piece mangée
     * @param p Piece mangée
     */
    public void manger(Piece p)
    {
        this.j.scorer(p.getValue());
        p.estVivante = false;
    }
    
    /**
     * Fonction redéfinie dans chaque Piece car chaque pièce a sa propre façon de bouger
     * @param p
     * @param c
     * @return 
     */
    public abstract boolean coupPossible(Plateau p, Coup c);
    
    /**
     * Cette fonction permet de détecter les cases traversées par une piece pour se
     * rendre du point de depart au point de fin du coup passé en param.
     * Elle prend en compte les trois directions possibles dans chaque sens
     * @param c Coup en cours
     * @return 
     */
    public Point[] getPosIntermediaire(Coup c)
    {
        Point [] p;
        int depH = (c.fin.x - c.dep.x);
        int depV = (c.fin.y - c.dep.y);
        int absH = Math.abs(depH);
        int absV = Math.abs(depV);
        
        // On définit la taille du tableau
        if(absH > absV)
        {
           
            p = new Point[absH-1];
        }else {
           
            p = new Point[absV-1];
        }
        
        //Cas du deplacement Horizontal
        if(depV == 0)
        {
            if(depH<0)
            {
                for (int i = 1; i <= (absH - 1); i++) {
                    p[i-1] = new Point(c.dep.x - i, c.dep.y);
                }
            }else{
                for (int i = 1; i <= (absH - 1); i++) {
                    p[i-1] = new Point(c.dep.x + i, c.dep.y);
                }
            }
        }else if(depH==0)
        {
            //Cas du deplacement Vertical
            if(depV<0)
            {
                for (int i = 1; i <= (absV - 1); i++) {
                    p[i-1] = new Point(c.dep.x, c.dep.y - i);
                }
            }else{
                for (int i = 1; i <= (absV - 1); i++) {
                    p[i-1] = new Point(c.dep.x, c.dep.y + i);
                }
            }
        }else{
            // en diagonales
            if(depV<0)
            {
                if(depH<0)
                {
                    for (int i = 1; i <= (absH - 1); i++) {
                       p[i-1] = new Point(c.dep.x - i, c.dep.y - i);
                        
                    }
                }else{
                    for (int i = 1; i <= (absH - 1); i++) {
                       p[i-1] = new Point(c.dep.x + i, c.dep.y - i);
                        
                    }
                }
            }else{
                if(depH<0)
                {
                    for (int i = 1; i <= (absH - 1); i++) {
                       p[i-1] = new Point(c.dep.x - i, c.dep.y + i);
                        
                    }
                }else{
                     for (int i = 1; i <= (absH - 1); i++) {
                       p[i-1] = new Point(c.dep.x + i, c.dep.y + i);
                        
                    }
                }
            }
            
        }
        return p;
        
    }
}