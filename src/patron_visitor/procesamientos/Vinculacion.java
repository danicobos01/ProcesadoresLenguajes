package patron_visitor.procesamientos;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;



public class Vinculacion extends ProcesamientoPorDefecto {

	private Stack<Map<String, NodoAST>> ts;
	
	public Vinculacion() {
		ts = new Stack<Map<String, NodoAST>>();
		ts.push(new HashMap<String, NodoAST>());
	}
	
	public enum tipoError {
		idDeclarado
	}
	
	public Dec getDec(StringLocalizado id) {
		for (Map<String, NodoAST> t : ts) {
			if (t.containsKey(id.toString())) {
				return t.get(id.toString()).getVinculo();
			}
		}
		return null;
	}

	public boolean idDuplicadoTodos(StringLocalizado id) {
		for (Map<String, NodoAST> t : ts) {
			if (t.containsKey(id.toString()))
				return true;
		}
		return false;
	}

	public boolean idDuplicadoAct(StringLocalizado id) {
		if (ts.peek().containsKey(id.toString()))
			return true;
		return false;
	}
	
	public void aniade(StringLocalizado id, Decs d) {
		ts.peek().put(id.toString(), new NodoAST()); // Que pasamos aquí ??
	}
	
	public void recolectaTodos(StringLocalizado id, Decs d) {
		if (idDuplicadoTodos(id)) {
			printError(id, tipoError.idDeclarado);
		} else {
			aniade(id, d);
		}
	}

	public void recolectaAct(StringLocalizado id, Decs d) {
		if (idDuplicadoAct(id)) {
			printError(id, tipoError.idDeclarado);
		} else {
			aniade(id, d);
		}
	}
	
	public void printError(StringLocalizado s, tipoError err) {
		if(err == tipoError.idDeclarado) {
			System.out.println("Error: " + s.toString() + " - id ya declarado");
		}
	}
	
	
	
	
	// VINCULACION DE LAS DISTINTAS CLASES
	
	public void procesa(Prog_sin_decs prog) {
		prog.getInstrucciones().procesa(this);
	}
	
	public void procesa(Prog_con_decs prog) {
		this.vincula1(prog.getDeclaraciones());
		this.vincula2(prog.getDeclaraciones());
		prog.getInstrucciones().procesa(this);
	}
	
	
	public void vincula1(Decs_muchas decs) {
		this.vincula1(decs.decs());
		this.vincula1(decs.dec());
	}
	
	public void vincula1(Decs_una dec) {
		this.vincula1(dec.dec());
	}
	
	public void vincula1(Decs_vacia decs) {}
	
	public void vincula1(DecTipo dec) {
		this.vincula1(dec.getTipo());
		this.recolecta 
	}
	
	public void vincula1(DecVar dec) {
		this.vincula1(dec.getTipo());
		this.recolecta
	}
	
	public void vincula1(DecProc dec) {
		this.recolecta
		this.abreNivel
		this.vincula1(dec.getPforms());
		this.vincula1(dec.getDecs());
		this.vincula2(dec.getDecs());
		dec.getIns().procesa(this);
		this.cierraNivel
	}
	
	public void vincula2(Decs_muchas decs) {
		this.vincula2(decs.decs());
		this.vincula2(decs.dec());
	}
	
	public void vincula2(Decs_una decs) {
		this.vincula2(decs.dec());
	}
	
	public void vincula2(Decs_vacia decs) {}
	
	public void vincula2(DecTipo dec) {
		this.vincula2(dec.getTipo());
	}
	
	public void vincula2(DecVar dec) {
		this.vincula2(dec.getTipo());
	}
	
	public void vincula2(DecProc dec) {
		this.vincula2(dec.getPforms());
		this.recolecta
		if(this.idDuplicadoAct(dec.getId())) {
			error
		}
		else {
			this.addTabla
		}
	}
	
	// Tipos
	
	public void vincula1(Int int_) {
		
	}
	
	public void vincula1(Real real) {
		
	}
	
	public void vincula1(Bool bool) {
		
	}
	
	public void vincula1(StringTipo st) {
		
	}
	
	public void vincula1(Null null_) {
		
	}
	
	public void vincula1(Array arr) {
		this.vincula1(arr.getTipoElems());
	}
	
	public void vincula1(RecordTipo rec) {
		this.vincula1(rec.getCampos());
	}
	
	public void vincula1(Pointer pointer) {
		if(pointer.getTipoApuntado() != EnumTipo.REF ) {
			this.vincula1(pointer.getApuntado());
		}
	}
	
	public void vincula1(Ref ref) {
		if(this.existe(ref.getId(), ts)) {
			// $.vinculo = valorDe(ts,id)
			ref.setVinculo(ts.valorDe(ts, ref.getId()));
		}
		else {
			error // id no declarado
		}
	}
	
	public void vincula1(Campo campo) {
		this.vincula1(campo.getTipo());
	}
	
	public void vincula1(Campos_muchos campos) {
		this.vincula1(campos.getCampos());
		this.vincula1(campos.getCampo());
	}
	
	public void vincula1(Campos_uno campo) {
		this.vincula1(campo.getCampo());
	}
	
	public void vincula2(Int int_) {}
	
	public void vincula2(Bool bool_) {}
	
	public void vincula2(Real real_) {}
	
	public void vincula2(StringTipo string_) {}
	
	public void vincula2(Null null_) {}
	
	public void vincula2(Array arr) {
		this.vincula2(arr.getTipo());
	}
	
	public void vincula2(RecordTipo rec) {
		this.vincula2(rec.getCampos());
	}
	
	public void vincula2(Pointer p) {
		if (p.getTipo() == EnumTipo.REF) {
			Tipo apuntado = p.getApuntado();
			Ref r = (Ref)apuntado; // ojo esto
			if(this.existe(r.getId(), ts)) {
				p.setVinculo(this.valorDe(r.getId(), ts));
			}
			else {
				error
			}
		}
		else {
			this.vincula2(p.getTipo());
		}
	}
	
	public void vincula2(Ref ref) {}
	
	public void vincula2(Campo campo) {
		this.vincula2(campo.getTipo());
	}
	
	public void vincula2(Campos_muchos campos) {
		this.vincula2(campos.getCampos());
		this.vincula2(campos.getCampo());
	}
	
	public void vincula2(Campos_uno campo) {
		this.vincula2(campo.getCampo());
	}
	
	// Parametros
	
	
	
	
	public void procesa(Ins_una ins) {
		ins.in().procesa(this);
	}
	
	public void procesa(Ins_muchas ins) {
		ins.ins().procesa(this); // Hay que ver lo del vector...
		ins.in().procesa(this);
	}
	
	public void procesa(Asignacion asig) {
		asig.getFirst().procesa(this);
		asig.getSecond().procesa(this);
	}
	
	public void procesa(Id id) {
		if(ts.contains(id.toString())) {
			id.setVinculo(getDec(id.id())); // No sé si es getDec o no
		}
		else {
			// LANZAREMOS ERROR
		}
	}
	
	public void procesa(Suma suma) {
		suma.arg0().procesa(this);
		suma.arg1().procesa(this);
	}
	
	public void procesa(Resta resta) {
		resta.arg0().procesa(this);
		resta.arg1().procesa(this);
	}
	
	public void procesa(NumEnt num) {
		num.procesa(this); // No hay que hacer nada aquí
	}
	
	
	// PRIMERA PASADA
	
	public void vincula1(Decs decs) {
		
	}
	
	public void vincula1 (Dec dec) {
		
	}
	
	
	// SEGUNDA PASADA
	
	public void vincula2(Decs decs) {
		
	}
	
	public void vincula2(Dec dec) {
		
	}
	

}
	