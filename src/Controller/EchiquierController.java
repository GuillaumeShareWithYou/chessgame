
package Controller;
import Model.*;
import View.*;


public class EchiquierController  {
    
    Echiquier e;
    Plateau p;
    public EchiquierController(Echiquier _e, Plateau _p)
    {
        this.e = _e;
        this.p = _p;
        this.p.addObserver(this.e);
    }
}
