import java.io.PrintWriter;
import java.io.File;

class Generate {
    PrintWriter writer = null;
    Instr instr[];
    int LC, linelen;
    final int MAXLINE = 70;
    
    public Generate(Tree t, Instr instr[], String outfile) throws Exception{
        this.instr = instr;
        LC = 0;
        linelen = 0;
        writer = new PrintWriter(outfile, "UTF-8");
        writer.println(".CODE"); 
        gen(t.next);
        prLC();
        writer.println("HALT  .ENTRY 0");
        writer.close();
    }
    
    private boolean areTheyReal(short t1, short t2) {
        return (t1 == Parser.Real || t1 == Parser.RealConst) && 
            (t2 == Parser.Real || t2 == Parser.RealConst);
    }
    
    private boolean areTheyInt(short t1, short t2) {
        return (t1 == Parser.integer || t1 == Parser.IntConst) && 
            (t2 == Parser.integer || t2 == Parser.IntConst);
    }
    
    private Integer getAddr(Tree t) {
        Integer temp = Integer.valueOf((ST.getEntry(t).index - 1) * 8);
        return temp;
    }
    
    private void prLC() {
        writer.print(LC + ": ");
        linelen = 8;
    }
    
    private void prNL() {
        writer.print("\n");
    }
    
    private void code(int opcode, Integer arg) {
        String s;
        if (arg != null){
            LC += 1 + instr[opcode].arg_size;
            s = String.format("%s %d ", this.instr[opcode].name, arg.intValue());
        }
        else {
            LC += 1;
            s = this.instr[opcode].name + " ";
        }
        if ((linelen += s.length()) > MAXLINE) {
            linelen = 8; 
            writer.print("\n\t");
        }
        writer.print(s);
    }
    
    private Tree codeData(Tree p) {
        for (; p != null; p = p.next){
            for (Tree t = p.first; t != null && t.name == Parser.Ident; t = t.next) {
                writer.print(getAddr(t) + ": " + t.value +": ");
            }
            if (p.next != null && p.next.name != Parser.Var){
                return p;
            }
        }
        return p;
    }
    
    private short convert(short operation, short t1, short t2){
        switch (operation) {
            case Parser.Plus :
            case Parser.Minus :
            case Parser.Mod :
            case Parser.Star :
                if (areTheyInt(t1, t2)) {
                    code(
                    operation == Parser.Plus ? Instr.ADDI : 
                    operation == Parser.Minus ? Instr.SUBI : 
                    operation == Parser.Star ? Instr.MULI : 
                    0 //still need mod
                    , null);
                    return t1;
                }
                else {
                    code(Instr.CVTIF, null);
                    code(
                    operation == Parser.Plus ? Instr.ADDF : 
                    operation == Parser.Minus ? Instr.SUBF : 
                    operation == Parser.Star ? Instr.MULF : 
                    0 // still need mod  
                    , null);
                    return Parser.RealConst;
                }
            case Parser.Slash :
                if (areTheyReal(t1, t2)){
                    code(Instr.DIVF, null);
                    return t1;
                }
                else {
                    code(Instr.DIVF, null);
                    code(Instr.CVTIF, null);
                    return Parser.RealConst;
                }
            case Parser.Div : 
        }
        return -1;
    }
    
    private short gen_expr(Tree t) {
        short n, t1, t2;
        if (t == null){    
            System.err.println("Error with assignment, no expression");
            return -1;
        }
        switch (n = t.name.shortValue()){
            case Parser.Eq : case Parser.Ne :
    		case Parser.Lt : case Parser.Le :
    		case Parser.Gt : case Parser.Ge :
                if (!areTheyReal(ST.getEntry(t.first).type, ST.getEntry(t.second).type) 
                && !areTheyInt(ST.getEntry(t.first).type, ST.getEntry(t.second).type)) {
                    if (ST.getEntry(t.first).type != Parser.integer || 
                        ST.getEntry(t.first).type != Parser.IntConst){
                        gen_expr(t.first);
                        gen_expr(t.second);    
                        code(Instr.CVTIF, null);
                        }
                    else {
                        gen_expr(t.first);
                        code(Instr.CVTIF, null);
                        gen_expr(t.second);    
                    }
                }
    			if (n == Parser.Gt || n == Parser.Le){
                    code(Instr.SWAPW, null);
                }
                gen_expr (t.first); 
                gen_expr (t.second);
    			code (Instr.SUBI, null);
    			code ( (n == Parser.Eq || n == Parser.Ne) ? Instr.TSTEQI : Instr.TSTLTI, null);
    			if (n == Parser.Ne || n == Parser.Le || n == Parser.Ge) {
                    code(Instr.NOTW, null);
                }
                return Parser.Boolean;
            case Parser.Plus : case Parser.Minus :
    		case Parser.Star : case Parser.Slash :
            case Parser.Div : 
                    t1 = gen_expr(t.first);
                    t2 = gen_expr(t.second);
                    return convert(n, t1, t2);
            case Parser.Ident :
                code(Instr.PUSHW, getAddr(t)); 
                ST temp = ST.getEntry(t);
                if (temp.type == Parser.RealConst || temp.type == Parser.Real)
                    code(Instr.GETSL, null);
                else
                    code(Instr.GETSW, null);
                return ST.getEntry(t).type;
            case Parser.IntConst : case Parser.Real :
            case Parser.Boolean : case Parser.RealConst :
                code(Instr.PUSHW, Integer.parseInt(t.value));
                return n;
            default :
                System.err.println("Internal error on \t" + Parser.yyname[n]);
                return -1;
        }
    }

    private void gen(Tree t) {
        short n;
        ST temp;
        if (t == null) return;
        for (; t != null; t = t.next){
            switch (n = t.name.shortValue()) {
                case Parser.Var :
                    prNL();
                    writer.print(".DATA\t");
                    t = codeData(t);
                    prNL();
                    writer.println(".CODE");
                    break;
                case Parser.integer: case Parser.Boolean: case Parser.Bind:
                    break;
                case Parser.Assign :
                    prLC();
                    code(Instr.PUSHW, getAddr(t.first));
                    gen_expr(t.second);
                    temp = ST.getEntry(t.first);
                    if (temp.type == Parser.RealConst || temp.type == Parser.Real)
                        code(Instr.PUTSL, null);
                    else
                        code(Instr.PUTSW, null);
                    prNL();
                    break;
                case Parser.If :
                    writer.println("/* Begin if */");
                    prLC();
                    gen_expr(t.first);
                    gen(t.second);
                    writer.println("/* End of if */");
                    prLC();
                    gen(t.third);
                    break;
                case Parser.Loop :
                    writer.println("/* Begin loop */");
                    prLC();
                    gen(t.first);
                    writer.println("/* End of loop */");
                    break;
                case Parser.Else :
                    writer.println("\n/* Else */");
                    gen(t.first);
                    writer.println("/* End Else */");
                    prLC();
                    break;
                case Parser.Elsif :
                    writer.println("\n/* Elsif */");
                    gen_expr(t.first);
                    gen(t.second);
                    writer.println("/* End Elsif */");
                    prLC();
                    gen(t.third);
                    
                default :
                    gen(t.first);
                    gen(t.second);
                    gen(t.third);
                    
            }
        }
    }
}
