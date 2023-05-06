package patron_visitor.asint;

import patron_visitor.asint.TinyASint.*;


public class ProcesamientoPorDefecto implements Procesamiento {
   public void procesa(Suma exp) {}
   public void procesa(Resta exp) {}
   public void procesa(Mul exp) {}
   public void procesa(Div exp) {}
   public void procesa(Mod exp) {}
   public void procesa(Mayor exp) {}
   public void procesa(Menor exp) {}
   public void procesa(Igual exp) {}
   public void procesa(Distinto exp) {}
   public void procesa(MayorIgual exp) {}
   public void procesa(MenorIgual exp) {}
   public void procesa(And exp) {}
   public void procesa(Not exp) {}
   public void procesa(Id exp) {}
   public void procesa(NumEnt exp) {}
   public void procesa (NumReal exp) {}
   public void procesa(Dec dec) {}
   public void procesa(Decs_muchas decs) {}
   public void procesa(Decs_una decs) {}
   public void procesa(Prog_sin_decs prog) {}    
   public void procesa(Prog_con_decs prog) {}   
}
