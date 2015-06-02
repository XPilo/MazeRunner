/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.elCinco;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.*;
import unalcol.agents.Action;
/**
 *
 * @author Jose
 */
public class MyEater implements AgentProgram{
  protected SimpleLanguage language;
  protected Vector<String> cmd = new Vector<String>();
  
  public void setLanguage(  SimpleLanguage _language ){
    language = _language;
  }
    
    @Override
    public Action compute(Percept p) {
        if( cmd.size() == 0 ){
      boolean PF = ( (Boolean) p.getAttribute(language.getPercept(0))).
          booleanValue();
      boolean PD = ( (Boolean) p.getAttribute(language.getPercept(1))).
          booleanValue();
      boolean PA = ( (Boolean) p.getAttribute(language.getPercept(2))).
          booleanValue();
      boolean PI = ( (Boolean) p.getAttribute(language.getPercept(3))).
          booleanValue();
      boolean MT = ( (Boolean) p.getAttribute(language.getPercept(4))).
          booleanValue();
      boolean RE = ( (Boolean) p.getAttribute(language.getPercept(5))).
          booleanValue();
      if (RE){
        boolean RE_C = ( (Boolean) p.getAttribute(language.getPercept(6))).
            booleanValue();
        boolean RE_SH = ( (Boolean) p.getAttribute(language.getPercept(7))).
            booleanValue();
        boolean RE_SI = ( (Boolean) p.getAttribute(language.getPercept(8))).
            booleanValue();
        boolean RE_W = ( (Boolean) p.getAttribute(language.getPercept(9))).
            booleanValue();
      }
      int EN = (int) p.getAttribute(language.getPercept(10));
        cmd.add(language.getAction(4)); //advance
        }
        String x = cmd.get(0);
        cmd.remove(0);
        return new Action(x);
    }

    @Override
    public void init() {
        cmd.clear();
    }
    
}
