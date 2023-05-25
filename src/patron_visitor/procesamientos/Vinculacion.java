package patron_visitor.procesamientos;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;



public class Vinculacion extends ProcesamientoPorDefecto {

	public static Stack<Map<String, NodoAST>> ts;
	
	public Vinculacion() {
		ts = new Stack<Map<String, NodoAST>>();
		this.abreNivel();
	}
	
	public void abreNivel() {
		ts.push(new HashMap<String, NodoAST>());
	}
	
	public void cierraNivel() {
		ts.remove(ts.size() - 1);
	}
	
	public boolean existeId(StringLocalizado id) {
        for (int i = ts.size() - 1; i >= 0; i--) {
            if (ts.get(i).containsKey(id)) {
            	return true;
            }
        }
        return false;
    }
	
	public enum tipoError {
		idDeclarado
	}
	
	
	public void add(StringLocalizado id, NodoAST nodo) {
		if (ts.get(ts.size() - 1).containsKey(id.toString())) {
			GestorErrores.addError("Identificador duplicado: " + id); // VER COMO HACER LO DE LOS ERRORES
		}
        else {
        	ts.peek().put(id.toString(), nodo);
        	// ts.get(ts.size() - 1).put(id.toString(), nodo);
        }
            
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
		this.abreNivel();
		this.vincula1(dec.getPforms());
		this.vincula1(dec.getDecs());
		this.vincula2(dec.getDecs());
		dec.getIns().procesa(this);
		this.cierraNivel();
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
	
	public void vincula1(Int int_) {}
	
	public void vincula1(Real real) {}
	
	public void vincula1(Bool bool) {}
	
	public void vincula1(StringTipo st) {}
	
	public void vincula1(Null null_) {}
	
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
		if(existeId(ref.getId())) {
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
			if(existeId(r.getId())) {
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
	
	public void vincula1(Pf_valor pf) {
		this.vincula1(pf.getTipo());
		this.recolecta...
	}
	
	public void vincula1(Pf_ref pf) {
		this.vincula1(pf.getTipo());
		this.recolecta...
	}
	
	public void vincula1(Pf_muchos pf) {
		this.vincula1(pf.getPforms());
		this.vincula1(pf.getPf());
	}
	
	public void vincula1(Pf_vacio pf) {}
	
	public void vincula1(Pf_uno pf) {
		this.vincula1(pf.getPf());
	}
	
	public void vincula2(Pf_valor pf) {
		this.vincula2(pf.getTipo());
	}
	
	public void vincula2(Pf_ref pf) {
		this.vincula2(pf.getTipo());
	}
	
	public void procesa(Pr pr) {
		pr.getExp().procesa(this);
	}
	public void procesa(Pr_muchos pr) {
		pr.getPreales().procesa(this);
		pr.getPr().procesa(this);
	}
	public void procesa(Pr_vacio pr) {}
	public void procesa(Pr_uno pr) {
		pr.getPr().procesa(this);
	}
	
	
	// Expresiones
	
	public void procesa(Id id) {
		if(!existeId(id.id())) {
			id.setVinculo(getDec(id.id())); // No sé si es getDec o no
		}
		else {
			// LANZAREMOS ERROR
		}
	}
	
	public void procesa (NumEnt n) {}
	public void procesa (NumReal n) {}
	public void procesa (TrueExp true_) {}
	public void procesa (FalseExp false_) {}
	public void procesa (StringExp str) {}
	public void procesa (NullExp n) {}
	
	public void procesa (Not exp) {
		exp.getExp().procesa(this);
	}
	
	public void procesa (MenosUnario exp) {
		exp.getExp().procesa(this);
	}
	
	public void procesa(Igual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Distinto exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Mayor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(MayorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Menor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(MenorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(And exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Or exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Suma exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Resta exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Mul exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Div exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Mod exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Index exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Indireccion exp) {
		exp.getExp().procesa(this);
	}
	
	public void procesa(AccesoRegistro exp) {
		exp.getExp().procesa(this);
	}
	
	
	
	// Instrucciones 
	
	public void procesa(Ins_una ins) {
		ins.in().procesa(this);
	}
	
	public void procesa(Ins_muchas ins) {
		ins.ins().procesa(this); // Hay que ver lo del vector...
		ins.in().procesa(this);
	}
	
	public void procesa(Ins_vacia ins) {}
		
	public void procesa(Asignacion asig) {
		asig.getFirst().procesa(this);
		asig.getSecond().procesa(this);
	}
	
	public void procesa(If_then ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
	}
	
	public void procesa(If_then_else ins) {
		ins.getExp().procesa(this);
		ins.getIns1().procesa(this);
		ins.getIns2().procesa(this);
	}
	
	public void procesa(While ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
	}
	
	public void procesa(Read ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(Write ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(New ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(NewLine ins) {}
	
	public void procesa(Delete ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(Seq seq) {
		this.abreNivel();
		this.vincula1(seq.getDecs());
		this.vincula2(seq.getDecs());
		seq.getIns().procesa(this);
		this.cierraNivel();
	}
	
	public void procesa(Invoc_proc inv) {
		if(this.existeId(inv.getId())) {
			inv.setVinculo(valorDe(ts, id));
		}
		else {
			error
		}
		inv.getPreales().procesa(this);
	}
	
	
	

}
	