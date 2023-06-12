package patron_visitor.procesamientos;

import java.util.ArrayList;

import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;

public class ComprobacionTipos extends ProcesamientoPorDefecto {
	
	
	public static final ArrayList<String> errores = new ArrayList<String>();
	
	public enum tipoError{
		NoExisteCampo, SoloUsoRegistro, NoDesignador, TiposNoCompatibles, TipoNoBasico, NoPuntero, ParamsInvalidos, NoDeclaracion, NoDecTipo
	}
	
	public Tipo ambosOk(Tipo first, Tipo second) {
		if(first instanceof OkTipo && second instanceof OkTipo) {
			return new OkTipo();
		} else {
			return new ErrorTipo();
		}		
	}
	
	public Tipo refType(Exp exp) {
        if (exp.getTipoNodo() instanceof Ref)
        {
            if (exp.getVinculo().getTipoNodo() instanceof Ref)
            	return refType((Exp) exp.getVinculo());
            else if (exp.getVinculo() instanceof DecTipo)
            	return ((DecTipo) exp.getVinculo()).getTipo();
        }
        else if (exp instanceof Indireccion)
		{
			return refType(((Indireccion) exp).getExp());
		}
        
        return exp.getTipoNodo();
	}
	
	public boolean sonCompatibles(Exp first, Exp second) {
		if(refType(first).getEnumTipo() == refType(second).getEnumTipo()) {
			return true;
		}
		else { 
			return false;
		}
	}
	
	public boolean sonCompatibles(Tipo first, Tipo second) {
			return (first.getEnumTipo() == second.getEnumTipo());

	}
	
	public boolean sonCompatiblesAsignacion(Exp first, Exp second) {
		// Falta añadir arrays y punteros
		if (sonCompatibles(first.getTipoNodo(), second.getTipoNodo()) ||
				((first.getTipoNodo().getEnumTipo() == EnumTipo.REAL) && second.getTipoNodo().getEnumTipo() == EnumTipo.INT)){
			return true;
		}
		return false;

}
	
	
	
	public boolean esDesignador(NodoAST nodo) {
		if(nodo instanceof Id || nodo instanceof Index || nodo instanceof Indireccion || nodo instanceof AccesoRegistro) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Tipo getReference(Tipo tipo) {
	    if (tipo instanceof Ref) {
	        if (tipo.getVinculo() instanceof Ref) return getReference((Tipo)tipo.getVinculo());
	        else return getReference(((DecTipo)(tipo.getVinculo())).getTipo());
	    }
	    else return tipo;
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
		decs.setTipoNodo(ambosOk(decs.decs().getTipoNodo(), decs.dec().getTipoNodo()));
	}
	
	public void procesa(Decs_una dec) {
		dec.dec().procesa(this);
	}
	
	public void procesa(Decs_vacia decs) {
		decs.setTipoNodo(new OkTipo());
	}
	
	public void procesa(DecVar dec) {
		dec.getTipo().procesa(this);
		dec.setTipoNodo(dec.getTipo().getTipoNodo());
	}
	
	public void procesa(DecTipo dec) {
		dec.getTipo().procesa(this);
		dec.setTipoNodo(dec.getTipo().getTipoNodo());
	}
	
	public void procesa(DecProc dec) {
		dec.getPforms().procesa(this);
		dec.getDecs().procesa(this);
		dec.getIns().procesa(this);
		dec.setTipoNodo(ambosOk(dec.getPforms().getTipoNodo(), ambosOk(dec.getDecs().getTipoNodo(), dec.getIns().getTipoNodo())));
	}

	public void procesa(Int int_) {
		int_.setTipoNodo(new OkTipo());
	}
	public void procesa(Real real) {
		real.setTipoNodo(new OkTipo());
	}
	public void procesa(Bool bool) {
		bool.setTipoNodo(new OkTipo());
	}
	public void procesa(StringTipo str) {
		str.setTipoNodo(new OkTipo());
	}
	public void procesa(Null null_) {
		null_.setTipoNodo(new OkTipo());
	}
	
	public void procesa(Array arr) {
		arr.getTipoElems().procesa(this);
		arr.setTipoNodo(new OkTipo());
	}
	
	public void procesa(RecordTipo rec) {
		rec.getCampos().procesa(this);
		rec.setTipoNodo(new OkTipo());
	}
	
	public void procesa(Pointer p) {
		p.getTipo().procesa(this);
		p.setTipoNodo(new OkTipo());
	}
	
	public void procesa(Ref ref) {
		if(ref.getVinculo() instanceof DecTipo) {
			ref.setTipoNodo(new OkTipo());
		}
		else {
			errores.add(tipoError.NoDecTipo + " al hacer Ref no se está referenciando a una declaración de tipo");
			ref.setTipoNodo(new ErrorTipo());
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
	
	public void procesa(Pf_vacio pf) {
		pf.setTipoNodo(new OkTipo());
	}
	
	public void procesa(Pr_muchos pr) {
		pr.getPreales().procesa(this);
		pr.getPr().procesa(this);
		pr.setTipoNodo(ambosOk(pr.getPreales().getTipoNodo(), pr.getPr().getTipoNodo()));
	}
	
	public void procesa(Pr_vacio pr) {
		pr.setTipoNodo(new OkTipo());
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
	
    public boolean check_params(PReales preales, Pforms pformales) {

        if ((preales instanceof Pr_muchos && pformales instanceof Pf_vacio) || (preales instanceof Pr_vacio && pformales instanceof Pf_muchos))
            return false;
    	
        if (preales instanceof Pr_vacio && pformales instanceof Pf_vacio)
            return true;
        
        return check_params(((Pr_muchos)preales).getPreales(), ((Pf_muchos)pformales).getPforms())
            && check_param(((Pr_muchos)preales).getPr(), ((Pf_muchos)pformales).getPf());
    }
	
    public boolean check_param(Pr preal, Pf pformal) {
    	
    	if (pformal instanceof Pf_ref)
    	{
    		preal.procesa(this);
    		if (esDesignador(preal) && sonCompatibles(pformal.getTipo(), preal.getTipoNodo()))
    		{
    			return true;
    		}
    	}
    	else if (pformal instanceof Pf_valor)
    	{
    		preal.procesa(this);
    		if (sonCompatibles(pformal.getTipo(), preal.getTipoNodo()))
    		{
    			return true;
    		}
    	}
		return false;
    }
	
	
	// Instrucciones
	public void procesa(Asignacion ins) {
		ins.getFirst().procesa(this);
		ins.getSecond().procesa(this);
		if(esDesignador(ins.getFirst())) {
			if(sonCompatiblesAsignacion(ins.getFirst(), ins.getSecond())) {
				ins.setTipoNodo(new OkTipo());
			}
			else {
				errores.add(tipoError.TiposNoCompatibles + ": en la asignación de " + ins.getFirst().toString() + " a " + ins.getSecond().toString());
				ins.setTipoNodo(new ErrorTipo());
			}
		}
		else {
			errores.add(tipoError.NoDesignador + ": en la asignación de " + ins.getFirst().toString() + " a " + ins.getSecond().toString());
			ins.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(If_then ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
		
		if(refType(ins.getExp()) instanceof Bool && ins.getIns().getTipoNodo() instanceof OkTipo) { 
			ins.setTipoNodo(new OkTipo());
		}
	}
	
	public void procesa(If_then_else ins) {
		ins.getExp().procesa(this);
		ins.getIns1().procesa(this);
		ins.getIns2().procesa(this);
		
		if(refType(ins.getExp()) instanceof Bool && ambosOk(ins.getIns1().getTipoNodo(), ins.getIns2().getTipoNodo()) instanceof OkTipo) { 
			ins.setTipoNodo(new OkTipo());
		}
	}
	
	public void procesa(While ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
		if(refType(ins.getExp()) instanceof Bool && ins.getIns().getTipoNodo() instanceof OkTipo) { 
			ins.setTipoNodo(new OkTipo());
		}
	}
	
	public void procesa(Read ins) {
		ins.getExp().procesa(this);
		if((refType(ins.getExp()) instanceof Int || refType(ins.getExp()) instanceof Real
				|| refType(ins.getExp()) instanceof Bool || refType(ins.getExp()) instanceof StringTipo)
					&& esDesignador(ins.getExp())) {
			ins.setTipoNodo(new OkTipo());
		}else {
			errores.add(tipoError.NoDesignador + " o " + tipoError.TipoNoBasico + " en Read");
			ins.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Write ins) {
		ins.getExp().procesa(this);
		if(refType(ins.getExp()) instanceof Int || refType(ins.getExp()) instanceof Real
				|| refType(ins.getExp()) instanceof Bool || refType(ins.getExp()) instanceof StringTipo) {
			ins.setTipoNodo(new OkTipo());
		}else {
			errores.add(tipoError.TipoNoBasico + " en Write");
			ins.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(NewLine ins) {
		ins.setTipoNodo(new OkTipo());
	}
	
	public void procesa(New ins) {
		ins.getExp().procesa(this);
		if(refType(ins.getExp()) instanceof Pointer) {
			ins.setTipoNodo(new OkTipo());
		}
		else {
			// error
			errores.add(tipoError.NoPuntero + " al hacer New");
			ins.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Delete ins) {
		ins.getExp().procesa(this);
		if(refType(ins.getExp()) instanceof Pointer) {
			ins.setTipoNodo(new OkTipo());
		}
		else {
			// error
			errores.add(tipoError.NoPuntero + " al hacer Delete");
			ins.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Seq ins) {
		ins.getDecs().procesa(this);
		ins.getIns().procesa(this);
		ins.setTipoNodo(ins.getIns().getTipoNodo());
	}
	
	public void procesa(Invoc_proc ins) {
		if (ins.vinculo instanceof DecProc) {

			ins.getPreales().procesa(this);            
            if (check_params(ins.getPreales(), ((DecProc) ins.vinculo).getPforms()))
            	ins.setTipoNodo(new OkTipo());
            else
            	errores.add(tipoError.ParamsInvalidos + " en Invoc_proc");
            	ins.setTipoNodo(new ErrorTipo());
        }
        else
        	errores.add(tipoError.ParamsInvalidos + " en Invoc_proc");
        	ins.setTipoNodo(new ErrorTipo());
	}
	
	
	
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
		ins.setTipoNodo(new OkTipo());
	}

	// Expresiones
	
	public void procesa(Id exp) {
		if (exp.getVinculo() instanceof DecVar)
            exp.setTipoNodo(((DecVar) exp.getVinculo()).getTipo());
        else if (exp.getVinculo() instanceof Pf_valor)
        	exp.setTipoNodo(((Pf_valor) exp.getVinculo()).getTipo());
        else if (exp.getVinculo() instanceof Pf_ref)
        	exp.setTipoNodo(((Pf_ref) exp.getVinculo()).getTipo());
        else {
        	errores.add(tipoError.NoDeclaracion + " en Id: " + exp.id().toString());
        	exp.setTipoNodo(new ErrorTipo());
        }
	}
	
	public void procesa(NumEnt exp) {
		exp.setTipoNodo(new Int());
	}
	public void procesa(NumReal exp) {
		exp.setTipoNodo(new Real());
	}
	public void procesa(TrueExp exp) {
		exp.setTipoNodo(new Bool());
	}
	public void procesa(FalseExp exp) {
		exp.setTipoNodo(new Bool());
	}
	public void procesa(StringExp exp) {
		exp.setTipoNodo(new StringTipo());
	}
	public void procesa(NullExp exp) {
		exp.setTipoNodo(new Null());
	}
	
	public void procesa(Not exp) {
		exp.getExp().procesa(this);
		if(refType(exp.getExp()) instanceof Bool) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en Not: Se espera una expresion booleana");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(MenosUnario exp) {
		exp.getExp().procesa(this);
		if(refType(exp.getExp()) instanceof Int) {
			exp.setTipoNodo(new Int());
		}
		if(refType(exp.getExp()) instanceof Real) {
			exp.setTipoNodo(new Real());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en MenosUnario: Se espera una expresion entera o real");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Suma exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Int) {
			exp.setTipoNodo(new Int());
		}
		else if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Real
				|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Int
					|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Real) {
			exp.setTipoNodo(new Real());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la Suma");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Resta exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Int) {
			exp.setTipoNodo(new Int());
		}
		else if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Real
				|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Int
					|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Real) {
			exp.setTipoNodo(new Real());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la Resta");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Mul exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Int) {
			exp.setTipoNodo(new Int());
		}
		else if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Real
				|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Int
					|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Real) {
			exp.setTipoNodo(new Real());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la Multiplicación");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Div exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Int) {
			exp.setTipoNodo(new Int());
		}
		else if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Real
				|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Int
					|| refType(exp.getFirst()) instanceof Real && refType(exp.getSecond()) instanceof Real) {
			exp.setTipoNodo(new Real());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la División");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Mod exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(refType(exp.getFirst()) instanceof Int && refType(exp.getSecond()) instanceof Int) {
			exp.setTipoNodo(new Int());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en el Módulo");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(And exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(refType(exp.getFirst()) instanceof Bool && refType(exp.getSecond()) instanceof Bool) {
			exp.setTipoNodo(new Int());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica And");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Or exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(refType(exp.getFirst()) instanceof Bool && refType(exp.getSecond()) instanceof Bool) {
			exp.setTipoNodo(new Int());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica Or");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Mayor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica Mayor");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Menor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica Menor");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(MayorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica Mayor o Igual");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(MenorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica Menor o Igual");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Igual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica Igual");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Distinto exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la operación lógica Distinto");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	
	public void procesa(Index exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		
		if(sonCompatibles(exp.getFirst(), exp.getSecond())) {
			exp.setTipoNodo(new Bool());
		}
		else {
			errores.add(tipoError.TiposNoCompatibles + " en la Indexación");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(Indireccion exp) {
		exp.getExp().procesa(this);
		if(getReference(exp.getExp().getTipoNodo()) instanceof Pointer) {
			exp.setTipoNodo(((Pointer) exp.getExp().getTipoNodo()).getApuntado());
		}
		else {
			errores.add(tipoError.NoPuntero + " en la Indirección");
			exp.setTipoNodo(new ErrorTipo());
		}
	}
	
	public void procesa(AccesoRegistro exp) {
		exp.getExp().procesa(this);
        if (getReference(exp.getExp().getTipoNodo()) instanceof RecordTipo){
            if(((RecordTipo) getReference(exp.getExp().getTipoNodo())).existeCampo(exp.getId().toString())) {
                exp.setTipoNodo(((RecordTipo) getReference(exp.getExp().getTipoNodo())).tipoCampo(exp.getId().toString()));
            }
            else{
                errores.add(tipoError.NoExisteCampo + " en el Acceso a Registro");
                exp.setTipoNodo(new ErrorTipo());
            }
        }
        else{
            errores.add(tipoError.SoloUsoRegistro + " en el Acceso a Registro");
            exp.setTipoNodo(new ErrorTipo());
        }
	}
	

	
	
	
}
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	