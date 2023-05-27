package patron_visitor.procesamientos;

import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;

public class ComprobacionTipos extends ProcesamientoPorDefecto {
	
	public enum tNodo{
		LIT_ENT, LIT_REAL;
	}
	
	private boolean compatibleNumero(tNodo t0, tNodo t1) {
		if (t0 == tNodo.LIT_ENT && t1 == tNodo.LIT_ENT) {
			return true;
		} else if (t0 == tNodo.LIT_REAL && t1 == tNodo.LIT_REAL) {
			return true;
		}
		
		return false;
	}
	
	public EnumTipo ambosOk(EnumTipo first, EnumTipo second) {
		if(first == EnumTipo.OK && second == EnumTipo.OK) {
			return EnumTipo.OK;
		}else {
			return EnumTipo.ERROR;
		}		
	}
	
	
	// Hay que hacer varias
	public boolean sonCompatibles(Exp first, Exp second) {
		if(first.getTipoNodo() == second.getTipoNodo()) {
			return true;
		}
		else { 
			return false;
		}
	}
	
	public boolean esDesignador(NodoAST nodo) {
		if(nodo instanceof Id || nodo instanceof Index || nodo instanceof Indireccion || nodo instanceof AccesoRegistro) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void procesa(Prog_con_decs prog) {
		prog.getDeclaraciones().procesa(this);
		prog.getInstrucciones().procesa(this);
		prog.setTipoNodo(ambosOk(prog.getDeclaraciones().getTipoNodo(), prog.getInstrucciones().getTipoNodo()));
	}
	
	public void procesa(Prog_sin_decs prog) {
		prog.getInstrucciones().procesa(this);
		prog.setTipoNodo(prog.getInstrucciones().getTipoNodo());
	}
	
	public void procesa(Decs_muchas decs) {
		decs.decs().procesa(this);
		decs.dec().procesa(this);
	}
	
	public void procesa(Decs_una dec) {
		dec.dec().procesa(this);
	}
	
	public void procesa(Decs_vacia decs) {}
	
	public void procesa(DecVar dec) {
		dec.getTipo().procesa(this);
		dec.setTipoNodo(dec.getTipoNodo());
	}
	
	public void procesa(DecTipo dec) {
		dec.getTipo().procesa(this);
		dec.setTipoNodo(dec.getTipoNodo());
	}
	
	public void procesa(DecProc dec) {
		dec.getPforms().procesa(this);
		dec.getDecs().procesa(this);
		dec.getIns().procesa(this);
		dec.setTipoNodo(ambosOk(dec.getPforms().getTipoNodo(), ambosOk(dec.getDecs().getTipoNodo(), dec.getIns().getTipoNodo())));
	}

	public void procesa(Int int_) {
		int_.setTipoNodo(EnumTipo.OK);
	}
	public void procesa(Real real) {
		real.setTipoNodo(EnumTipo.OK);
	}
	public void procesa(Bool bool) {
		bool.setTipoNodo(EnumTipo.OK);
	}
	public void procesa(StringTipo str) {
		str.setTipoNodo(EnumTipo.OK);
	}
	public void procesa(Null null_) {
		null_.setTipoNodo(EnumTipo.OK);
	}
	
	public void procesa(Array arr) {
		arr.getTipoElems().procesa(this);
		try {
            Integer.parseInt(arr.getNElems().toString());
            arr.setTipoNodo(arr.getTipoElems().getEnumTipo());
        } catch (NumberFormatException e) {
        	arr.setTipoNodo(EnumTipo.ERROR);
        }
	}
	
	public void procesa(RecordTipo rec) {
		rec.getCampos().procesa(this);
		rec.setTipoNodo(EnumTipo.OK);
	}
	
	public void procesa(Pointer p) {
		p.getTipo().procesa(this);
		p.setTipoNodo(EnumTipo.OK);
	}
	
	public void procesa(Ref ref) {
		if(ref.getVinculo() instanceof DecTipo) {
			ref.setTipoNodo(EnumTipo.OK);
		}
		else {
			// error
			ref.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Campo campo) {
		campo.getTipo().procesa(this);
		campo.setTipoNodo(campo.getTipoNodo());
	}
	
	public void procesa(Campos_muchos campos) {
		campos.getCampo().getTipo().procesa(this);
		campos.getCampo().getTipo().procesa(this);
		campos.setTipoNodo(campos.getCampo().getTipoNodo());
	}
	
	public void procesa(Campos_uno campos) {
		campos.getCampo().getTipo().procesa(this);
		campos.setTipoNodo(campos.getCampo().getTipoNodo());
	}
	
	// Parámetros
	
	public void procesa(Pf_muchos pf) {
		pf.getPforms().procesa(this);
		pf.getPf().procesa(this);
		pf.setTipoNodo(ambosOk(pf.getPforms().getTipoNodo(), pf.getPf().getTipoNodo()));
	}
	
	public void procesa(Pf_uno pf) {
		if(pf.getPf().getTipoNodo() == EnumTipo.OK) {
			pf.setTipoNodo(EnumTipo.OK);
		}
	}
	
	public void procesa(Pf_vacio pf) {
		pf.setTipoNodo(EnumTipo.OK);
	}
	
	public void procesa(Pr_muchos pr) {
		pr.getPreales().procesa(this);
		pr.getPr().procesa(this);
		pr.setTipoNodo(ambosOk(pr.getPreales().getTipoNodo(), pr.getPr().getTipoNodo()));
	}
	
	public void procesa(Pr_uno pr) {
		if(pr.getPr().getTipoNodo() == EnumTipo.OK) {
			pr.setTipoNodo(EnumTipo.OK);
		}
	}
	
	public void procesa(Pr_vacio pr) {
		pr.setTipoNodo(EnumTipo.OK);
	}
	
	public void procesa(Pf_valor p) {
		p.getTipo().procesa(this);
		p.setTipoNodo(p.getTipo().getTipoNodo());
	}
	
	public void procesa(Pf_ref p) {
		p.getTipo().procesa(this);
		p.setTipoNodo(p.getTipo().getTipoNodo());
	}
	
	public void procesa(Pr p) {
		p.getExp().procesa(this);
		p.setTipoNodo(p.getExp().getTipoNodo());
	}
	
	// CHECK PARAMETROS 
	
	
	
	
	// Instrucciones
	public void procesa(Asignacion ins) {
		ins.getFirst().procesa(this);
		ins.getSecond().procesa(this);
		if(esDesignador(ins.getFirst())) {
			if(sonCompatibles(ins.getFirst(), ins.getSecond())) {
				ins.setTipoNodo(EnumTipo.OK);
			}
			else {
				// error - no compatibles
				ins.setTipoNodo(EnumTipo.ERROR);
			}
		}
		else {
			// error - no designador
			ins.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(If_then ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
		if(ins.getExp().getTipoNodo() == EnumTipo.BOOL && ins.getIns().getTipoNodo() == EnumTipo.OK) { // MIRAR LO DE SI PUEDE SER BOOL O YA HA SIDO SOBREESCRITO PARA SER OK
			ins.setTipoNodo(EnumTipo.OK);
		}
	}
	
	public void procesa(If_then_else ins) {
		ins.getExp().procesa(this);
		ins.getIns1().procesa(this);
		ins.getIns2().procesa(this);
		if(ins.getExp().getTipoNodo() == EnumTipo.BOOL && ambosOk(ins.getIns1().getTipoNodo(), ins.getIns2().getTipoNodo()) == EnumTipo.OK) { // MIRAR LO DE SI PUEDE SER BOOL O YA HA SIDO SOBREESCRITO PARA SER OK
			ins.setTipoNodo(EnumTipo.OK);
		}
	}
	
	public void procesa(While ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
		if(ins.getExp().getTipoNodo() == EnumTipo.BOOL && ins.getIns().getTipoNodo() == EnumTipo.OK) { // MIRAR LO DE SI PUEDE SER BOOL O YA HA SIDO SOBREESCRITO PARA SER OK
			ins.setTipoNodo(EnumTipo.OK);
		}
	}
	
	public void procesa(Read ins) {
		ins.getExp().procesa(this);
		if((ins.getTipoNodo() == EnumTipo.INT || ins.getTipoNodo() == EnumTipo.REAL
				|| ins.getTipoNodo() == EnumTipo.BOOL || ins.getTipoNodo() == EnumTipo.STRING)
					&& esDesignador(ins.getExp())) {
			ins.setTipoNodo(EnumTipo.OK);
		}else {
			ins.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Write ins) {
		ins.getExp().procesa(this);
		if(ins.getTipoNodo() == EnumTipo.INT || ins.getTipoNodo() == EnumTipo.REAL
				|| ins.getTipoNodo() == EnumTipo.BOOL || ins.getTipoNodo() == EnumTipo.STRING) {
			ins.setTipoNodo(EnumTipo.OK);
		}else {
			ins.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(NewLine ins) {
		ins.setTipoNodo(EnumTipo.OK);
	}
	
	public void procesa(New ins) {
		ins.getExp().procesa(this);
		if(ins.getExp().getTipoNodo() == EnumTipo.POINTER) {
			ins.setTipoNodo(EnumTipo.OK);
		}
		else {
			// error
			ins.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Delete ins) {
		ins.getExp().procesa(this);
		if(ins.getExp().getTipoNodo() == EnumTipo.POINTER) {
			ins.setTipoNodo(EnumTipo.OK);
		}
		else {
			// error
			ins.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Seq ins) {
		ins.getDecs().procesa(this);
		ins.getIns().procesa(this);
		ins.setTipoNodo(ins.getIns().getTipoNodo());
	}
	
	// INVOC PROC HACERLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
	
	public void procesa(Ins_muchas ins) {
		ins.ins().procesa(this);
		ins.in().procesa(this);
		ins.setTipoNodo(ambosOk(ins.ins().getTipoNodo(), ins.in().getTipoNodo()));
	}
	
	public void procesa(Ins_una ins) {
		ins.in().procesa(this);
		ins.setTipoNodo(ins.in().getTipoNodo());
	}
	
	public void procesa (Ins_vacia ins) {
		ins.setTipoNodo(EnumTipo.OK);
	}
	
	
	// Expresiones
	
	public void procesa(Id exp) {
		if (exp.getVinculo() instanceof DecVar)
            exp.setTipoNodo(((DecVar) exp.getVinculo()).getTipoNodo());
        else if (exp.getVinculo() instanceof Pf_valor)
        	exp.setTipoNodo(((Pf_valor) exp.getVinculo()).getTipoNodo());
        else if (exp.getVinculo() instanceof Pf_ref)
        	exp.setTipoNodo(((Pf_ref) exp.getVinculo()).getTipoNodo());
        else {
        	// error
        	exp.setTipoNodo(EnumTipo.ERROR);
        }
	}
	
	public void procesa(NumEnt exp) {
		exp.setTipoNodo(EnumTipo.INT);
	}
	public void procesa(NumReal exp) {
		exp.setTipoNodo(EnumTipo.REAL);
	}
	public void procesa(TrueExp exp) {
		exp.setTipoNodo(EnumTipo.TRUE);
	}
	public void procesa(FalseExp exp) {
		exp.setTipoNodo(EnumTipo.FALSE);
	}
	public void procesa(StringExp exp) {
		exp.setTipoNodo(EnumTipo.STRING);
	}
	public void procesa(NullExp exp) {
		exp.setTipoNodo(EnumTipo.NULL);
	}
	
	public void procesa(Not exp) {
		exp.getExp().procesa(this);
		if(exp.getExp().getTipoNodo() == EnumTipo.BOOL) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			// error
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(MenosUnario exp) {
		exp.getExp().procesa(this);
		if(exp.getExp().getTipoNodo() == EnumTipo.INT) {
			exp.setTipoNodo(EnumTipo.INT);
		}
		if(exp.getExp().getTipoNodo() == EnumTipo.REAL) {
			exp.setTipoNodo(EnumTipo.REAL);
		}
		else {
			// error
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Suma exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.INT) {
			exp.setTipoNodo(EnumTipo.INT);
		}
		else if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.REAL
				|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.INT
					|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.REAL) {
			exp.setTipoNodo(EnumTipo.REAL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Resta exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.INT) {
			exp.setTipoNodo(EnumTipo.INT);
		}
		else if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.REAL
				|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.INT
					|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.REAL) {
			exp.setTipoNodo(EnumTipo.REAL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Mul exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.INT) {
			exp.setTipoNodo(EnumTipo.INT);
		}
		else if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.REAL
				|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.INT
					|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.REAL) {
			exp.setTipoNodo(EnumTipo.REAL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Div exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.INT) {
			exp.setTipoNodo(EnumTipo.INT);
		}
		else if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.REAL
				|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.INT
					|| exp.getFirst().getTipoNodo() == EnumTipo.REAL && exp.getSecond().getTipoNodo() == EnumTipo.REAL) {
			exp.setTipoNodo(EnumTipo.REAL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Mod exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(exp.getFirst().getTipoNodo() == EnumTipo.INT && exp.getSecond().getTipoNodo() == EnumTipo.INT) {
			exp.setTipoNodo(EnumTipo.INT);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(And exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(exp.getFirst().getTipoNodo() == EnumTipo.BOOL && exp.getSecond().getTipoNodo() == EnumTipo.BOOL) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Or exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(exp.getFirst().getTipoNodo() == EnumTipo.BOOL && exp.getSecond().getTipoNodo() == EnumTipo.BOOL) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Mayor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Menor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(MayorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(MenorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Igual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	public void procesa(Distinto exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(EnumTipo.BOOL);
		}
		else {
			exp.setTipoNodo(EnumTipo.ERROR);
		}
	}
	
	
	public void check_params() {
		
	}
	
	
	
}
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	