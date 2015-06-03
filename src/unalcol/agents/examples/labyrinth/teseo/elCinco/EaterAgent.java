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
public abstract class EaterAgent implements AgentProgram{
  protected SimpleLanguage language;
  protected Vector<String> cmd = new Vector<>();
  
  public void setLanguage(  SimpleLanguage _language ){
    language = _language;
  }
    
    @Override
    public Action compute(Percept p) {
        if( cmd.size() == 0 ){
            Boolean RE_C = null;
            Boolean RE_SH = null;
            Boolean RE_SI = null;
            Boolean RE_W = null;
            boolean PF = ( (Boolean) p.getAttribute(language.getPercept(0)));
            boolean PD = ( (Boolean) p.getAttribute(language.getPercept(1)));
            boolean PA = ( (Boolean) p.getAttribute(language.getPercept(2)));
            boolean PI = ( (Boolean) p.getAttribute(language.getPercept(3)));
            boolean MT = ( (Boolean) p.getAttribute(language.getPercept(4)));
            boolean RE = ( (Boolean) p.getAttribute(language.getPercept(5)));
            if (RE){
                RE_C = ( (Boolean) p.getAttribute(language.getPercept(6)));
                RE_SH = ( (Boolean) p.getAttribute(language.getPercept(7)));
                RE_SI = ( (Boolean) p.getAttribute(language.getPercept(8)));
                RE_W = ( (Boolean) p.getAttribute(language.getPercept(9)));
            }
            int EN = (int)p.getAttribute(language.getPercept(10));

            int d = accion(PF, PD, PA, PI, MT,RE, RE_C, RE_SH, RE_SI,RE_W,EN);
            /**
             * d = 0 el automata no gira
             * d = 1 el automata gira una vez
             * d = 2 el automata gira dos veces
             * d = 3 el automata gira tres veces
             * d = 4 el automata come
             * d = 5 el automata no opera
             * d = -1 el automata muere
             * Despues de terminar los giros el automata se mueve hacia adelante
             */
            if (0 <= d && d < 4) {
                for (int i = 1; i <= d; i++) {
                    cmd.add(language.getAction(3)); //rotate
                }
                cmd.add(language.getAction(2)); // advance
            }
            else if(d==4){
                cmd.add(language.getAction(4)); //eat
            }
            else if(d==-1){
                cmd.add(language.getAction(1)); //die
            }
            else {
              cmd.add(language.getAction(0)); // no_op
            }
        }
        String x = cmd.get(0);
        cmd.remove(0);
        return new Action(x);
    }

    @Override
    public void init() {
        cmd.clear();
    }

     public abstract int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean RE, Boolean RE_C, Boolean RE_SH, Boolean RE_SI, Boolean RE_W, int EN);
    
}
