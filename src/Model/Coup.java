
package Model;


public class Coup {
   public Point dep, fin;
    public Coup()
    {
        
    }
    public Coup(Point _dep)
    {
        this.dep = _dep;
        
    }
    public Coup(Point _dep, Point _fin)
    {
        this.dep = _dep;
        this.fin = _fin;
    }
    
    public Coup getCoup()
    {
        return this;
    }
    
    public void setDepart(Point _dep)
    {
        this.dep = _dep;
    }
    public void setFin(Point _fin)
    {
        this.fin  = _fin;
    }
    public void effacer()
    {
        setDepart(null);
        setFin(null);
    }
    public void effacerFin()
    {
        this.fin = null;
    }
}
