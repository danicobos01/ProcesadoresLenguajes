/* ConstructorAST.java */
/* Generated By:JavaCC: Do not edit this line. ConstructorAST.java */
package c_ast_descendente;

import asint.TinyASint.Exp;
import asint.TinyASint.Dec;
import asint.TinyASint.Decs;
import asint.TinyASint.Prog;
import semops.SemOps;


public class ConstructorAST implements ConstructorASTConstants {
   private SemOps sem = new SemOps();

  final public Prog Init() throws ParseException {Prog prog;
    prog = Prog();
    jj_consume_token(0);
{if ("" != null) return prog;}
    throw new Error("Missing return statement in function");
  }

  final public Prog Prog() throws ParseException {Exp exp; Decs decs;
    jj_consume_token(evalua);
    exp = E0();
    decs = PDonde();
{if ("" != null) return sem.prog(exp,decs);}
    throw new Error("Missing return statement in function");
  }

  final public Decs PDonde() throws ParseException {Decs decs;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case donde:{
      jj_consume_token(donde);
      decs = Decs();
{if ("" != null) return decs;}
      break;
      }
    default:
      jj_la1[0] = jj_gen;
{if ("" != null) return null;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp E0() throws ParseException {Exp exp1, resul;
    exp1 = E1();
    resul = RE0(exp1);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
  }

  final public Exp RE0(Exp exph) throws ParseException {char op; Exp exp1; Exp exp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case 14:
    case 15:{
      op = OP0();
      exp1 = E2();
      exp = RE0(sem.exp(op,exph,exp1));
{if ("" != null) return exp;}
      break;
      }
    default:
      jj_la1[1] = jj_gen;
{if ("" != null) return exph;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp E1() throws ParseException {Exp exp2, resul;
    exp2 = E2();
    resul = RE1(exp2);
{if ("" != null) return resul;}
    throw new Error("Missing return statement in function");
  }

  final public Exp RE1(Exp exph) throws ParseException {char op; Exp exp2; Exp exp;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case 16:
    case 17:{
      op = OP1();
      exp2 = E2();
      exp = RE1(sem.exp(op,exph,exp2));
{if ("" != null) return exp;}
      break;
      }
    default:
      jj_la1[2] = jj_gen;
{if ("" != null) return exph;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp E2() throws ParseException {Exp exp; Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case num:{
      t = jj_consume_token(num);
{if ("" != null) return sem.num(sem.str(t.image,t.beginLine,t.beginColumn));}
      break;
      }
    case id:{
      t = jj_consume_token(id);
{if ("" != null) return sem.id(sem.str(t.image,t.beginLine,t.beginColumn));}
      break;
      }
    case 12:{
      jj_consume_token(12);
      exp = E0();
      jj_consume_token(13);
{if ("" != null) return exp;}
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public char OP0() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case 14:{
      jj_consume_token(14);
{if ("" != null) return '+';}
      break;
      }
    case 15:{
      jj_consume_token(15);
{if ("" != null) return '-';}
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public char OP1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case 16:{
      jj_consume_token(16);
{if ("" != null) return '*';}
      break;
      }
    case 17:{
      jj_consume_token(17);
{if ("" != null) return '/';}
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Decs Decs() throws ParseException {Dec dec; Decs decs;
    dec = Dec();
    decs = RDecs(sem.decs_una(dec));
{if ("" != null) return decs;}
    throw new Error("Missing return statement in function");
  }

  final public Decs RDecs(Decs decsh) throws ParseException {Dec dec; Decs decs;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case 18:{
      jj_consume_token(18);
      dec = Dec();
      decs = RDecs(sem.decs_muchas(decsh,dec));
{if ("" != null) return decs;}
      break;
      }
    default:
      jj_la1[6] = jj_gen;
{if ("" != null) return decsh;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Dec Dec() throws ParseException {Token iden,numb;
    iden = jj_consume_token(id);
    jj_consume_token(19);
    numb = jj_consume_token(num);
{if ("" != null) return sem.dec(sem.str(iden.image,iden.beginLine,iden.beginColumn),
                                                    sem.str(numb.image,numb.endLine,numb.endColumn));}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ConstructorASTTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x100,0xc000,0x30000,0x1c00,0xc000,0x30000,0x40000,};
   }

  /** Constructor with InputStream. */
  public ConstructorAST(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ConstructorAST(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ConstructorASTTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ConstructorAST(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ConstructorASTTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ConstructorAST(ConstructorASTTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ConstructorASTTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[20];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 20; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
