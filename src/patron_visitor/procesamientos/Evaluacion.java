package patron_visitor.procesamientos;

import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;
import java.util.HashMap;



/*
 * 	Clase inoperativa 
 */

class Valores extends HashMap<String,Double> {}

public class Evaluacion extends ProcesamientoPorDefecto {
   private Valores valores;
   private double resul;
   public Evaluacion() {
       valores = new Valores();
   }
   public void procesa(Prog_sin_decs prog) {
       prog.getInstrucciones().procesa(this);
       System.out.println(">>>>"+resul);
   }    
   public void procesa(Prog_con_decs prog) {
       prog.decs().procesa(this);
       prog.getInstrucciones().procesa(this);
       System.out.println(">>>>"+resul);
   }    
   public void procesa(Decs_muchas decs) {
       decs.decs().procesa(this);
       decs.dec().procesa(this);
   }
   public void procesa(Decs_una decs) {
       decs.dec().procesa(this);
   }
   public void procesa(Dec dec) {
       if (valores.containsKey(dec.id().toString())) {
          throw new RuntimeException("Constante ya definida "+dec.id()+
                                        ".Fila: "+dec.id().fila()+", col: "+dec.id().col());
       }
        else {
           valores.put(dec.id().toString(), Double.valueOf(dec.id().toString()).doubleValue());
        }   
   }
   public void procesa(Suma exp) {
       exp.getFirst().procesa(this);
       double v0 = resul;
       exp.getSecond().procesa(this);
       resul += v0;
   }
   public void procesa(Resta exp) {
       exp.getFirst().procesa(this);
       double v0 = resul;
       exp.getSecond().procesa(this);
       resul = v0 - resul;
   }
   public void procesa(Mul exp) {
       exp.getFirst().procesa(this);
       double v0 = resul;
       exp.getSecond().procesa(this);
       resul *= v0;
   }
   public void procesa(Div exp) {
       exp.getFirst().procesa(this);
       double v0 = resul;
       exp.getSecond().procesa(this);
       resul = v0 / resul;
   }
   public void procesa(Id exp) {
       Double val = valores.get(exp.id().toString());
       if (val == null)
          throw new RuntimeException("Constante no definida:"+exp.id().toString()+
                                    ". Fila: "+exp.id().fila()+", col: "+exp.id().col());
       else 
         resul = val; 
   }
   public void procesa(NumEnt exp) {
       resul = Double.valueOf(exp.num().toString()).doubleValue();
   }
}   
 
