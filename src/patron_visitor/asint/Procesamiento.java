package patron_visitor.asint;

import patron_visitor.asint.TinyASint.*;

public interface Procesamiento {
    void procesa(Suma exp);
    void procesa(Resta exp);
    void procesa(Mul exp);
    void procesa(Div exp);
    void procesa(Mod exp);
    void procesa(Mayor exp);
    void procesa(Menor exp);
    void procesa(Igual exp);
    void procesa(Distinto exp);
    void procesa(MayorIgual exp);
    void procesa(MenorIgual exp);
    void procesa(Not exp);
    void procesa(And exp);
    void procesa(Or exp);
    void procesa(MenosUnario exp);
    void procesa(Index exp);
    void procesa(AccesoRegistro exp);
    void procesa(Indireccion exp);
    
    void procesa(Int tipo);
    void procesa(Real tipo);
    void procesa(StringTipo tipo);
    void procesa(Bool tipo);
    void procesa(Null tipo);
    void procesa(Array tipo);
    void procesa(RecordTipo tipo);
    void procesa(Pointer pointer);
    void procesa(Ref ref);
    
    void procesa(Campos_muchos campos);
    void procesa(Campos_uno campo);


    void procesa(Id exp);
    void procesa(NumEnt exp);
    void procesa(NumReal exp);
    void procesa(Dec dec);
    void procesa(Decs_muchas decs);
    void procesa(Decs_una decs);
    void procesa(Decs_vacia decs);
    void procesa(DecVar decs);
    void procesa(DecTipo decs);
    void procesa(DecProc decs);
    void procesa(Ins_una ins);
    void procesa (Ins_muchas ins);
    void procesa (Ins_vacia ins);
    void procesa (Asignacion ins);
    
    void procesa (Pforms pforms);
    void procesa(Prog_sin_decs prog);    
    void procesa(Prog_con_decs prog);    
}