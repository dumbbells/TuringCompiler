%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
%}

/* YACC Declarations */
%token Ident 1 IntConst 2 RealConst 3 Boolean 4 If 11 Then 12 End 13 Elsif 14
%token Assert 15 Put 16 Or 17 And 18 Else 19 Assign 21 LParen 22 RParen 23 Plus 24
%token Minus 25 Star 26 Slash 27 Eq 28 Ne 29 Lt 30 Le 31 Gt 32 Ge 33
%token Not 34 Bind 35 To 36 Begin 38 Loop 39 Exit 40 When 41 Record 42
%token Var 43 NoType 44 Integer 45 Div 48 Mod 49 Semi 60
%token Colon 61 Dot 62 Comma 63 Real 64

%start program
/* Grammar follows */
%%
program : pStateDeclSeq
    ;
pStateDeclSeq : /* empty string */
    | statement Semi pStateDeclSeq
    | Var idlist Colon type Semi pStateDeclSeq
    ;
idlist : Ident 
   | Ident Comma idlist
    ;
type : Integer
    | RealConst 
    | Real
    | Boolean
    | Record field_list End Record
    ;
field_list : idlist Colon type
    | idlist Colon type Semi field_list
    ;
state_decls : /* empty string */
    | statement Semi pStateDeclSeq
    | declaration Semi pStateDeclSeq
    ;
declaration : Var idlist Colon type
    | Bind idlist To ref
    | Bind Var idlist To ref
    ;
statement : ref Eq expr
    | Assert expr
    | Begin state_decls End
    | Loop state_decls End Loop
    | Exit | Exit When expr
    | If expr Then state_decls end_if
    ;
ref : Ident 
    | Ident Dot Ident
    ;
end_if : End If
    | Else state_decls End If
    | Elsif expr Then state_decls end_if
    ;
expr : expr Or and_expr  
    | and_expr
    ;
and_expr : and_expr And not_expr 
    | not_expr
    ;
not_expr : Not not_expr 
    | rel_expr
    ;
rel_expr : sum 
    | rel_expr Assign sum 
    | rel_expr Ne sum
    | rel_expr Lt sum 
    | rel_expr Le sum
    | rel_expr Gt sum 
    | rel_expr Ge sum
    ;
sum : prod 
    | sum Plus prod 
    | sum Minus prod
    ;
prod : factor 
    | prod Star factor 
    | prod Slash factor
    | prod Div factor 
    | prod Mod factor
    ;
factor : Plus basic | Minus basic | basic
    ;
basic : ref 
    | LParen expr RParen 
    | IntConst 
    | RealConst
    ;
%%
PrintWriter outFile;
public Yylex lexer;

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

