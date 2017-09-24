/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Controller;

import View.*;
import Model.*;
import java.util.Observable;
import java.util.Observer;

public class Controller {
    private  MenuController cMenu;
    private   EchiquierController cEchiquier;
    
    public Controller(View v, Model m)
    {
        cMenu = new MenuController(v, m);
        cEchiquier = new EchiquierController(v.getEchiquier(), m.getPlateau());
        
    }
    
}
