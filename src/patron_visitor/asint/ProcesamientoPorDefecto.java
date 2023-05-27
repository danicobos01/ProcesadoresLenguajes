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
    public void procesa(NumReal exp) {}
    public void procesa(Dec dec) {}
    public void procesa(Decs_muchas decs) {}
    public void procesa(Decs_una decs) {}
    public void procesa(Decs_vacia decs) {}
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
	public void procesa(Array tipo) {}
	public void procesa(DecVar decs) {}
	public void procesa(DecTipo decs) {}
	public void procesa(DecProc decs) {}
	public void procesa(Ins_una ins) {}
	public void procesa(Ins_muchas ins) {}
	public void procesa(Ins_vacia ins) {}
	public void procesa(Asignacion ins) {}
	public void procesa(Pforms pforms) {}
	public void procesa(Null tipo) {}
	public void procesa(RecordTipo tipo) {}
	public void procesa(Pointer pointer) {}
	public void procesa(Ref ref) {}
	public void procesa(Campos_muchos campos) {}
	public void procesa(Campos_uno campo) {}
	public void procesa(TrueExp exp) {}
	public void procesa(FalseExp exp) {}
	public void procesa(StringExp exp) {}
	public void procesa(NullExp exp) {}
	public void procesa(If_then ins) {}
	public void procesa(If_then_else ins) {}
	public void procesa(While ins) {}
	public void procesa(Read ins) {}
	public void procesa(Write ins) {}
	public void procesa(NewLine ins) {}
	public void procesa(Seq ins) {}
	public void procesa(Invoc_proc ins) {}
	public void procesa(New ins) {}
	public void procesa(Delete ins) {}
	public void procesa(Pf_ref pf) {}
	public void procesa(Pf_valor pf) {}
	public void procesa(Pr pr) {}
	public void procesa(PReales pf) {}
	public void procesa(Campo campo) {}   
}
