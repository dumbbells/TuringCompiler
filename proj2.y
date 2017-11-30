%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
%}

/* YACC Declarations */
%type <int> Ident
%token Ident 1 IntConst 2 RealConst 3 Boolean 4 If 11 Then 12 End 13 Elsif 14
%token Assert 15 Put 16 Or 17 And 18 Else 19 Assign 21 LParen 22 RParen 23 Plus 24
%token Minus 25 Star 26 Slash 27 Eq 28 Ne 29 Lt 30 Le 31 Gt 32 Ge 33
%token Not 34 Bind 35 To 36 Begin 38 Loop 39 Exit 40 When 41 Record 42
%token Var 43 NoType 44 integer 45 Div 48 Mod 49 Semi 60
%token Colon 61 Dot 62 Comma 63 Real 64 start 100

%type <obj> pStateDeclSeq idlist type field_list state_decls declaration statement
%type <obj> ref end_if expr and_expr not_expr rel_expr sum prod factor basic
%start program
/* Grammar follows */
%%
program : pStateDeclSeq
		{root = new Tree(-1, null, null, null, null);
		root.addNext($1);}
    ;
pStateDeclSeq : /* empty string */
		{$$ = null;}
    | statement Semi pStateDeclSeq
		{$$ = $1; ((Tree)$$).addNext($3);}
    | declaration Semi pStateDeclSeq
		{$$ = $1; ((Tree) $$).addNext($3);}
    ;
idlist : Ident 
		{$$ = new Tree(Ident, null, null, null, null);}
   | Ident Comma idlist
		{$$ = new Tree(Ident, null, null, null, null); ((Tree)$$).addNext($3);}
    ;
type : integer
		{$$ = integer; }
    | RealConst 
		{$$ = RealConst;}
    | Real
		{$$ = Real;}
    | Boolean
		{$$ = Boolean;}
    | Record field_list End Record
		{$$ = new Tree(Record, null, $2, null, null);}
    ;
field_list : idlist Colon type
		{$$ = $1;
		((Tree) $$).type = (Short)$3;}
    | idlist Colon type Semi field_list
		{$$ = $1; ((Tree)$$).addNext($5);
		((Tree) $$).type = (Short)$3;
		}
    ;
state_decls : /* empty string */
		{$$ = null;}
    | statement Semi pStateDeclSeq
		{$$ = $1; ((Tree)$$).addNext($3);}
    | declaration Semi pStateDeclSeq
		{$$ = $1; ((Tree)$$).addNext($3);}
    ;
declaration : Var idlist Colon type
		{
			if ($4 instanceof Short){
				$$ = new Tree(Var, null, $2, null, null); 
				((Tree) $$).type = (Short) $4;
			}
			else {
				$$ = new Tree(Var, null, $2, $4, null); 
				((Tree) $$).type = Record;
			}
		}
    | Bind idlist To ref
		{
		$$ = new Tree(Bind, null, $2, $4, null);
		} 
    | Bind Var idlist To ref
		{
		$$ = new Tree(Bind, " var", $3, $5, null);
		} 
    ;
statement : ref Assign expr
		{$$ = new Tree(Assign, null, $1, $3, null);}
    | Assert expr
		{$$ = new Tree (Assert, null, $2, null, null);}
    | Begin state_decls End
		{$$ = new Tree(Begin, null, $2, null, null);}
    | Loop state_decls End Loop
		{$$ = new Tree(Loop, null, $2, null, null);
		((Tree)$$).addNext(new Tree(End, " Loop", null, null, null));}
    | Exit
		{$$ = new Tree(Exit, null, null, null, null);} 
    | Exit When expr
		{$$ = new Tree(Exit, " When", $3, null, null);}
    | If expr Then state_decls end_if
		{$$ = new Tree(If, null, $2, $4, $5);}
    ;
ref : Ident 
		{$$ = new Tree(Ident, null, null, null, null);}
    | Ident Dot Ident		
		{
		$$ = new Tree(Dot, null, null, null, null );
		} 
    ;
end_if : End If
		{$$ = new Tree(End, " If", null, null, null);}
    | Else state_decls End If
		{$$ = new Tree(Else, null, $2, null, null); 
		((Tree)$$).addNext(new Tree(End, " If", null, null, null));} 
    | Elsif expr Then state_decls end_if
		{$$ = new Tree(Elsif, null, $2, $4, null);
		((Tree)$$).addNext($5);}
    ;
expr : expr Or and_expr 
		{$$ = new Tree(Or, null, $1, $3, null);}
    | and_expr
		{$$ = $1;}
    ;
and_expr : and_expr And not_expr 
		{$$ = new Tree(And, null, $1, $3, null);}
    | not_expr
		{$$ = $1;}
    ;
not_expr : Not not_expr 
		{$$ = new Tree(Not, null, $2, null, null);}
    | rel_expr
		{$$ = $1;}
    ;
rel_expr : sum 
		{$$ = $1;}
    | rel_expr Eq sum 
		{$$ = new Tree(Eq, null, $1, $3, null);}
    | rel_expr Ne sum
		{$$ = new Tree(Ne, null, $1, $3, null);}
    | rel_expr Lt sum 
		{$$ = new Tree(Lt, null, $1, $3, null);}
    | rel_expr Le sum
		{$$ = new Tree(Le, null, $1, $3, null);}
    | rel_expr Gt sum 
		{$$ = new Tree(Gt, null, $1, $3, null);}
    | rel_expr Ge sum
		{$$ = new Tree(Ge, null, $1, $3, null);}
    ;
sum : prod
		{$$ = $1;} 
    | sum Plus prod 
		{$$ = new Tree(Plus, null, $1, $3, null);}
    | sum Minus prod
		{$$ = new Tree(Minus, null, $1, $3, null);}
    ;
prod : factor 
		{$$ = $1;}
    | prod Star factor 
		{$$ = new Tree(Star, null, $1, $3, null);}
    | prod Slash factor
		{$$ = new Tree(Slash, null, $1, $3, null);}
    | prod Div factor 
		{$$ = new Tree(Div, null, $1, $3, null);}
    | prod Mod factor
		{$$ = new Tree(Mod, null, $1, $3, null);}
    ;
factor : Plus basic 
		{$$ = new Tree(Plus, null, $2, null, null);}
    | Minus basic 
		{$$ = new Tree(Minus, null, $2, null, null);}
    | basic
		{$$ = $1;}    
    ;
basic : ref 
		{$$ = $1;}    
    | LParen expr RParen 
		{$$ = new Tree(LParen, null, $2, null, null);
		((Tree)$$).addNext(new Tree(RParen, null, null, null, null));} 
    | IntConst
		{$$ = new Tree(IntConst, null, null, null, null);} 
    | RealConst
		{$$ = new Tree(RealConst, null, null, null, null);} 
		
    ;
%%
PrintWriter outFile;
public Yylex lexer;
public Tree root;

private int yylex ()
{
        int yyl_return = -1;
        try {
                yyl_return = lexer.yylex ();
                }
        catch (IOException e) {
		System.err.println("Caught an exception!");
                System.err.println (e);
                }
        return yyl_return;
}

void yyerror (String s)
{
        lexer.yyerror (s);
}

