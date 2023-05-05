package patron_visitor.asint;

import patron_visitor.asint.TinyASint.Suma;
import patron_visitor.asint.TinyASint.Resta;
import patron_visitor.asint.TinyASint.Mul;
import patron_visitor.asint.TinyASint.Div;
import patron_visitor.asint.TinyASint.Id;
import patron_visitor.asint.TinyASint.Num;
import patron_visitor.asint.TinyASint.Dec;
import patron_visitor.asint.TinyASint.Decs_muchas;
import patron_visitor.asint.TinyASint.Decs_una;
import patron_visitor.asint.TinyASint.Prog_con_decs;
import patron_visitor.asint.TinyASint.Prog_sin_decs;


public class ProcesamientoPorDefecto implements Procesamiento {
   public void procesa(Suma exp) {}
   public void procesa(Resta exp) {}
   public void procesa(Mul exp) {}
   public void procesa(Div exp) {}
   public void procesa(Id exp) {}
   public void procesa(Num exp) {}
   public void procesa(Dec dec) {}
   public void procesa(Decs_muchas decs) {}
   public void procesa(Decs_una decs) {}
   public void procesa(Prog_sin_decs prog) {}    
   public void procesa(Prog_con_decs prog) {}   
}
