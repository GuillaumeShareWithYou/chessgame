/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Model;


import java.util.Observer;

/**
 *
 * @author Marie_Estelle
 */
public class Minuteur extends Thread {
    private   Model m;
    private   Observer MinView;
    private   Time chrono;
    private   long depart;
    
    
    /*
    |----------------------------------------|
    |           Paramètres du Minuteur       |
    |----------------------------------------|
    */
    
    //Durée d'un tour
    int minuteMax = 0;
    int secMax = 30;
    
    //Changement de couleur du miniteur lorsque temps inférieur à
    int minuteLow = 0;
    int secLow = 50;
    
    int minuteSuperLow = 0;
    int secSuperLow = 20;
    
    
    public Minuteur(Model _m){
        this.m = _m;
        this.chrono = new Time(minuteMax, secMax);
    }
    
    @Override
    public void run()
    {
        this.reset();
        this.depart = System.currentTimeMillis();
        while(!m.isPartieTerminee())
        {
            
            
            if((System.currentTimeMillis() - depart) >= 1000)
            {
                
                this.chrono.decrementerSec();
                if(this.chrono.isZero())
                {
                    
                    this.reset();
                    this.m.getJoueurSuivant();
                    this.m.getPlateau().setInfo(Info.TEMPS_ECOULE);
                }
                this.MinView.update(m, m);
                
                depart = System.currentTimeMillis();
            }
        }
    }
    /**
     * Remet le compteur du miniteur au temps de départ
     */
    public void reset()
    {
        this.chrono = new Time(minuteMax,secMax);
        
    }
    /**
     * permet de relier la vue et le minuteur
     * @param obs Reference sur la vue
     */
    public void setObserver(Object obs)
    {
        this.MinView = (Observer)obs;
    }
    
    public Time getTime()
    {
        return this.chrono;
    }
    /**
     * Le minuteur est-il bientôt arrété arrivé à l'échéance ?
     * @return
     */
    public boolean isLow()
    {
        if(chrono.lessThan(minuteLow,secLow))
        {
            return true;
        }
        return false;
    }
    public boolean isSuperLow()
    {
        if(chrono.lessThan(minuteSuperLow,secSuperLow))
        {
            return true;
        }
        return false;
    }
}
