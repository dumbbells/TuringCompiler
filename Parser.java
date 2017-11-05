//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "proj2.y"
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short Ident=1;
public final static short IntConst=2;
public final static short RealConst=3;
public final static short Boolean=4;
public final static short If=11;
public final static short Then=12;
public final static short End=13;
public final static short Elsif=14;
public final static short Assert=15;
public final static short Put=16;
public final static short Or=17;
public final static short And=18;
public final static short Else=19;
public final static short Assign=21;
public final static short LParen=22;
public final static short RParen=23;
public final static short Plus=24;
public final static short Minus=25;
public final static short Star=26;
public final static short Slash=27;
public final static short Eq=28;
public final static short Ne=29;
public final static short Lt=30;
public final static short Le=31;
public final static short Gt=32;
public final static short Ge=33;
public final static short Not=34;
public final static short Bind=35;
public final static short To=36;
public final static short Begin=38;
public final static short Loop=39;
public final static short Exit=40;
public final static short When=41;
public final static short Record=42;
public final static short Var=43;
public final static short NoType=44;
public final static short Integer=45;
public final static short Div=48;
public final static short Mod=49;
public final static short Semi=60;
public final static short Colon=61;
public final static short Dot=62;
public final static short Comma=63;
public final static short Real=64;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    4,    4,    4,    4,
    4,    5,    5,    6,    6,    6,    7,    7,    7,    2,
    2,    2,    2,    2,    2,    2,    8,    8,   10,   10,
   10,    9,    9,   11,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   14,   14,   14,   15,   15,   15,
   15,   15,   16,   16,   16,   17,   17,   17,   17,
};
final static short yylen[] = {                            2,
    1,    0,    3,    6,    1,    3,    1,    1,    1,    1,
    4,    3,    5,    0,    3,    3,    4,    4,    5,    3,
    2,    3,    4,    1,    3,    5,    1,    3,    2,    4,
    5,    3,    1,    3,    1,    2,    1,    1,    3,    3,
    3,    3,    3,    3,    1,    3,    3,    1,    3,    3,
    3,    3,    2,    2,    1,    1,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,   58,   59,    0,    0,    0,    0,   56,    0,
    0,   35,    0,    0,    0,   48,   55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   28,
    0,   53,   54,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   22,    0,    0,    0,    0,    0,    3,
    0,   57,    0,    0,   34,    0,    0,    0,    0,    0,
    0,    0,    0,   49,   50,   51,   52,    0,    0,    0,
   15,   16,   23,    6,    8,   10,    0,    7,    9,    0,
    0,    0,    0,   26,    0,   18,   17,    0,    0,    0,
   29,    0,    0,   19,    0,    0,    4,    0,    0,    0,
   11,    0,   30,    0,   31,   13,
};
final static short yydgoto[] = {                          8,
    9,   10,  108,  100,  109,   32,   33,   19,   20,  104,
   21,   22,   23,   24,   25,   26,   27,
};
final static short yysindex[] = {                       321,
  -46,  188,  188,   43,   43,  -23,   23,    0,    0,  -32,
    2,   46,    0,    0,  188,   66,   66,  188,    0,   25,
   33,    0,  336,   36,   48,    0,    0,   38,   16,   23,
   -7,   59,   17,   74,  188,   26,   18,  321,  188,    0,
   15,    0,    0,    0,   43,  188,  188,  231,  231,  231,
  231,  231,  231,  231,  231,  231,  231,  231,  231,   23,
   64,   34,  321,    0,  321,   63,   38,   23,    7,    0,
   38,    0,  120,   33,    0,   36,   36,   36,   36,   36,
   36,   48,   48,    0,    0,    0,    0,   67,  104,    7,
    0,    0,    0,    0,    0,    0,   23,    0,    0,   47,
   99,  188,   43,    0,  104,    0,    0,   51,  122,  321,
    0,   45,  125,    0,    7,   90,    0,   43,  106,   81,
    0,  120,    0,   23,    0,    0,
};
final static short yyrindex[] = {                       147,
   97,    0,    0,  150,  150,   94,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    3,    0,   -4,  185,  119,    0,    0,  100,    0,    0,
    0,    0,    0,    0,    0,  -13,    0,   80,    0,    0,
    0,    0,    0,    0,  142,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  164,    0,  164,    0,  107,    0,    0,    0,
  108,    0,    0,   10,    0,  207,  229,  251,  273,  295,
  317,  141,  163,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  150,    0,    0,    0,    0,    0,    0,   80,
    0,    0,    0,    0,    0,    0,    0,  142,    0,  156,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -34,   -2,    5,  -65,   52,   -5,    0,    1,    6,   60,
  139,  -11,    0,  322,   30,  219,   75,
};
final static int YYTABLESIZE=377;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         34,
   11,   31,   31,   70,   11,   11,   44,   37,   28,   95,
   96,   37,   37,   37,   33,   12,   36,   35,   37,   33,
   41,   32,    5,   36,  107,   33,   32,   38,   91,   39,
   92,   46,   32,   61,   62,   75,   45,   72,   11,   73,
   67,   46,   31,    1,   71,   11,   40,    5,   97,  120,
   47,   98,   63,    2,   46,   37,  118,    3,   60,   54,
   55,   46,   33,   11,   88,   11,    1,   13,   14,   32,
   99,   64,   94,   56,   57,  117,   65,   29,   69,    2,
    4,    5,    6,   82,   83,   30,   66,   15,   68,  106,
   42,   43,    2,    2,   90,   58,   59,  113,    2,   89,
   31,   93,  105,   11,    1,  114,  110,  112,   27,  111,
   11,  115,  122,   27,   27,   31,  123,   27,   11,   27,
   27,   27,   27,   27,   27,   27,   27,   27,   27,   27,
   45,  121,  101,  102,  116,   45,   45,  119,  103,   45,
  124,   45,   45,   45,   27,   27,    2,   45,   45,   45,
   45,   45,   46,   24,   14,   14,   27,   46,   46,   21,
   14,   46,   14,   46,   46,   46,   25,   20,   12,   46,
   46,   46,   46,   46,   47,  126,    2,    2,   45,   47,
   47,  125,    2,   47,   74,   47,   47,   47,    1,   13,
   14,   47,   47,   47,   47,   47,   38,    0,    0,    0,
   46,   38,   38,    0,    0,   38,    0,   38,    0,   15,
    0,   16,   17,   38,   38,   38,   38,   38,   39,    0,
    0,   18,   47,   39,   39,    0,    0,   39,    0,   39,
    0,    1,   13,   14,    0,   39,   39,   39,   39,   39,
   40,    0,    0,    0,   38,   40,   40,    0,    0,   40,
    0,   40,   15,    0,   16,   17,    0,   40,   40,   40,
   40,   40,   41,    0,    0,    0,   39,   41,   41,    0,
    0,   41,    0,   41,   84,   85,   86,   87,    0,   41,
   41,   41,   41,   41,   42,    0,    0,    0,   40,   42,
   42,    0,    0,   42,    0,   42,    0,    0,    0,    0,
    0,   42,   42,   42,   42,   42,   43,    0,    0,    0,
   41,   43,   43,    0,    0,   43,    0,   43,    0,    0,
    0,    1,    0,   43,   43,   43,   43,   43,   44,    0,
    0,    2,   42,   44,   44,    3,    0,   44,    0,   44,
    0,    0,    0,    0,    0,   44,   44,   44,   44,   44,
    0,    0,    0,    0,   43,    0,   48,    0,    4,    5,
    6,    0,    0,    7,   49,   50,   51,   52,   53,   76,
   77,   78,   79,   80,   81,    0,   44,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          5,
    0,    4,    5,   38,    4,    5,   18,   12,    3,    3,
    4,    7,   17,   18,   12,   62,    1,   41,   23,   17,
   15,   12,   36,    1,   90,   23,   17,   60,   63,   28,
   65,   17,   23,   29,   30,   47,   12,   23,   38,   45,
   35,   17,   45,    1,   39,   45,    1,   61,   42,  115,
   18,   45,   60,   11,   17,   60,   12,   15,   43,   24,
   25,   17,   60,   63,   60,   65,    1,    2,    3,   60,
   64,   13,   68,   26,   27,  110,   60,   35,   61,    0,
   38,   39,   40,   54,   55,   43,   13,   22,   63,   89,
   16,   17,   13,   14,   61,   48,   49,  103,   19,   36,
  103,   39,   36,  103,    1,  105,   60,  102,   12,   11,
  110,   61,  118,   17,   18,  118,   11,   21,  118,   23,
   24,   25,   26,   27,   28,   29,   30,   31,   32,   33,
   12,   42,   13,   14,   13,   17,   18,   13,   19,   21,
   60,   23,   24,   25,   48,   49,    0,   29,   30,   31,
   32,   33,   12,   60,   13,   14,   60,   17,   18,   60,
   19,   21,   13,   23,   24,   25,   60,   60,   13,   29,
   30,   31,   32,   33,   12,  124,   13,   14,   60,   17,
   18,  122,   19,   21,   46,   23,   24,   25,    1,    2,
    3,   29,   30,   31,   32,   33,   12,   -1,   -1,   -1,
   60,   17,   18,   -1,   -1,   21,   -1,   23,   -1,   22,
   -1,   24,   25,   29,   30,   31,   32,   33,   12,   -1,
   -1,   34,   60,   17,   18,   -1,   -1,   21,   -1,   23,
   -1,    1,    2,    3,   -1,   29,   30,   31,   32,   33,
   12,   -1,   -1,   -1,   60,   17,   18,   -1,   -1,   21,
   -1,   23,   22,   -1,   24,   25,   -1,   29,   30,   31,
   32,   33,   12,   -1,   -1,   -1,   60,   17,   18,   -1,
   -1,   21,   -1,   23,   56,   57,   58,   59,   -1,   29,
   30,   31,   32,   33,   12,   -1,   -1,   -1,   60,   17,
   18,   -1,   -1,   21,   -1,   23,   -1,   -1,   -1,   -1,
   -1,   29,   30,   31,   32,   33,   12,   -1,   -1,   -1,
   60,   17,   18,   -1,   -1,   21,   -1,   23,   -1,   -1,
   -1,    1,   -1,   29,   30,   31,   32,   33,   12,   -1,
   -1,   11,   60,   17,   18,   15,   -1,   21,   -1,   23,
   -1,   -1,   -1,   -1,   -1,   29,   30,   31,   32,   33,
   -1,   -1,   -1,   -1,   60,   -1,   21,   -1,   38,   39,
   40,   -1,   -1,   43,   29,   30,   31,   32,   33,   48,
   49,   50,   51,   52,   53,   -1,   60,
};
}
final static short YYFINAL=8;
final static short YYMAXTOKEN=64;
final static String yyname[] = {
"end-of-file","Ident","IntConst","RealConst","Boolean",null,null,null,null,null,
null,"If","Then","End","Elsif","Assert","Put","Or","And","Else",null,"Assign",
"LParen","RParen","Plus","Minus","Star","Slash","Eq","Ne","Lt","Le","Gt","Ge",
"Not","Bind","To",null,"Begin","Loop","Exit","When","Record","Var","NoType",
"Integer",null,null,"Div","Mod",null,null,null,null,null,null,null,null,null,
null,"Semi","Colon","Dot","Comma","Real",
};
final static String yyrule[] = {
"$accept : program",
"program : pStateDeclSeq",
"pStateDeclSeq :",
"pStateDeclSeq : statement Semi pStateDeclSeq",
"pStateDeclSeq : Var idlist Colon type Semi pStateDeclSeq",
"idlist : Ident",
"idlist : Ident Comma idlist",
"type : Integer",
"type : RealConst",
"type : Real",
"type : Boolean",
"type : Record field_list End Record",
"field_list : idlist Colon type",
"field_list : idlist Colon type Semi field_list",
"state_decls :",
"state_decls : statement Semi pStateDeclSeq",
"state_decls : declaration Semi pStateDeclSeq",
"declaration : Var idlist Colon type",
"declaration : Bind idlist To ref",
"declaration : Bind Var idlist To ref",
"statement : ref Eq expr",
"statement : Assert expr",
"statement : Begin state_decls End",
"statement : Loop state_decls End Loop",
"statement : Exit",
"statement : Exit When expr",
"statement : If expr Then state_decls end_if",
"ref : Ident",
"ref : Ident Dot Ident",
"end_if : End If",
"end_if : Else state_decls End If",
"end_if : Elsif expr Then state_decls end_if",
"expr : expr Or and_expr",
"expr : and_expr",
"and_expr : and_expr And not_expr",
"and_expr : not_expr",
"not_expr : Not not_expr",
"not_expr : rel_expr",
"rel_expr : sum",
"rel_expr : rel_expr Assign sum",
"rel_expr : rel_expr Ne sum",
"rel_expr : rel_expr Lt sum",
"rel_expr : rel_expr Le sum",
"rel_expr : rel_expr Gt sum",
"rel_expr : rel_expr Ge sum",
"sum : prod",
"sum : sum Plus prod",
"sum : sum Minus prod",
"prod : factor",
"prod : prod Star factor",
"prod : prod Slash factor",
"prod : prod Div factor",
"prod : prod Mod factor",
"factor : Plus basic",
"factor : Minus basic",
"factor : basic",
"basic : ref",
"basic : LParen expr RParen",
"basic : IntConst",
"basic : RealConst",
};

//#line 93 "proj2.y"
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

//#line 388 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
