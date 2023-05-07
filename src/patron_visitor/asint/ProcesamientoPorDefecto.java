package patron_visitor.asint;

import patron_visitor.asint.TinyASint.*;
import patron_visitor.asint.Procesamiento;


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
	public void procesa(Or exp) {}
	public void procesa(MenosUnario exp) {}
	public void procesa(Index exp) {}
	public void procesa(AccesoRegistro exp) {}
	public void procesa(Indireccion exp) {}
	public void procesa(Int tipo) {}
	public void procesa(Real tipo) {}
	public void procesa(StringTipo tipo) {}
	public void procesa(Bool tipo) {}
	public void procesa(DecVar decs) {}
	public void procesa(DecTipo decs) {}
	public void procesa(DecProc decs) {}
	public void procesa(Ins_una ins) {}
	public void procesa(Ins_muchas ins) {}
	public void procesa(Ins_vacia ins) {}
	public void procesa(Asignacion ins) {}
	public void procesa(Pforms pforms) {}   
}
