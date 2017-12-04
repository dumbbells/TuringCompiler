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
public final static short integer=45;
public final static short Div=48;
public final static short Mod=49;
public final static short Semi=60;
public final static short Colon=61;
public final static short Dot=62;
public final static short Comma=63;
public final static short Real=64;
public final static short start=100;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    3,    3,    3,    3,
    3,    4,    4,    5,    5,    5,    6,    6,    6,    7,
    7,    7,    7,    7,    7,    7,    8,    8,    9,    9,
    9,   10,   10,   11,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   14,   14,   14,   15,   15,   15,
   15,   15,   16,   16,   16,   17,   17,   17,   17,
};
final static short yylen[] = {                            2,
    1,    0,    3,    3,    1,    3,    1,    1,    1,    1,
    4,    3,    5,    0,    3,    3,    4,    4,    5,    3,
    2,    3,    4,    1,    3,    5,    1,    3,    2,    4,
    5,    3,    1,    3,    1,    2,    1,    1,    3,    3,
    3,    3,    3,    3,    1,    3,    3,    1,    3,    3,
    3,    3,    2,    2,    1,    1,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,   58,   59,    0,    0,    0,    0,
   56,    0,    0,   35,    0,    0,    0,   48,   55,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   28,    0,   53,   54,   36,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   22,    0,    0,    0,    0,
    0,    4,    3,    0,   57,    0,    0,   34,    0,    0,
    0,    0,    0,    0,    0,    0,   49,   50,   51,   52,
    6,    0,   18,   16,   15,   23,    8,   10,    0,    7,
    9,   17,    0,    0,    0,   26,   19,    0,    0,   29,
    0,    0,    0,    0,    0,    0,    0,   11,    0,   30,
    0,   31,   13,
};
final static short yydgoto[] = {                          9,
   10,  108,  102,  109,   34,   11,   12,   21,  106,   22,
   23,   24,   25,   26,   27,   28,   29,
};
final static short yysindex[] = {                       283,
  -62,  171,  171,   13,  283,  283,  -24,   18,    0,    0,
  -36,  -22,   25,   64,    0,    0,  171,   56,   56,  171,
    0,   43,   45,    0,  299,  105,  189,    0,    0,   55,
   12,   18,   50,   92,   37,   39,   95,  171,   77,  283,
  283,  171,    0,    3,    0,    0,    0,  283,  171,  171,
  188,  188,  188,  188,  188,  188,  188,  188,  188,  188,
  188,  188,   18,   75,  122,    0,  283,  283,  102,   55,
    9,    0,    0,   55,    0,   48,   45,    0,  105,  105,
  105,  105,  105,  105,  189,  189,    0,    0,    0,    0,
    0,  122,    0,    0,    0,    0,    0,    0,   18,    0,
    0,    0,  114,  171,  283,    0,    0,  107,  134,    0,
   84,  149,    9,  118,  283,  158,  111,    0,   48,    0,
   18,    0,    0,
};
final static short yyrindex[] = {                       182,
    4,    0,    0,    0,  170,  170,  124,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   86,    0,   62,  147,   59,    0,    0,  128,
  -18,    0,    0,    0,    0,    0,    0,    0,    0,   81,
   81,    0,    0,    0,    0,    0,    0,  214,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  221,  221,    0,  144,
    0,    0,    0,  146,    0,    0,  101,    0,  169,  191,
  213,  235,  257,  279,  103,  125,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  170,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  214,    0,  181,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   99,    7,   98,   96,    2,   -3,   -1,    1,  113,    6,
  177,  -10,    0,  282,   87,  195,  133,
};
final static int YYTABLESIZE=339;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         14,
   13,   35,   35,   36,   36,   13,   13,   37,   30,   47,
   33,   97,   98,   31,   39,   27,   38,    5,   31,   49,
   27,   27,   44,   40,   27,   75,   27,   27,   27,   27,
   27,   27,   27,   27,   27,   27,   27,   41,   64,   78,
   13,   13,    5,   70,   35,   42,   36,   74,   13,   76,
   99,   27,   27,  100,   48,   32,    1,   15,   16,   49,
  103,  104,   50,   27,   43,   93,  105,   13,   13,   91,
   45,   49,  101,   37,   63,   45,   45,   17,   37,   37,
    2,   45,   45,   45,   37,   65,   45,   45,   45,   45,
   45,   45,  107,    2,    2,  115,   67,   33,   68,    2,
   49,   35,   33,   36,   66,   13,  112,   69,   33,  111,
   92,   35,   32,   36,   46,   13,  119,   32,   45,   46,
   46,   37,    1,   32,  110,   46,   46,   46,   57,   58,
   46,   46,   46,   46,   46,   46,   47,   71,   72,   73,
   96,   47,   47,   85,   86,   33,  114,   47,   47,   47,
   45,   46,   47,   47,   47,   47,   47,   47,   38,  118,
   32,  116,   46,   38,   38,   94,   95,  113,  120,   38,
  121,    1,   15,   16,   38,   38,   38,   38,   38,   38,
   39,    2,   14,   24,   47,   39,   39,   21,    1,   15,
   16,   39,   17,   12,   18,   19,   39,   39,   39,   39,
   39,   39,   40,   25,   20,   20,   38,   40,   40,   17,
  117,   18,   19,   40,   59,   60,  123,    0,   40,   40,
   40,   40,   40,   40,   41,   77,   14,   14,   39,   41,
   41,  122,   14,    2,    2,   41,   61,   62,    0,    2,
   41,   41,   41,   41,   41,   41,   42,    0,    0,    0,
   40,   42,   42,   87,   88,   89,   90,   42,    0,    0,
    0,    0,   42,   42,   42,   42,   42,   42,   43,    0,
    0,    0,   41,   43,   43,    0,    0,    0,    0,   43,
    0,    0,    0,    1,   43,   43,   43,   43,   43,   43,
   44,    0,    0,    2,   42,   44,   44,    3,    0,    0,
    0,   44,    0,    0,    0,    0,   44,   44,   44,   44,
   44,   44,    0,    0,    0,    0,   43,    4,    0,    0,
    5,    6,    7,    0,    0,    8,   51,   52,   53,   54,
   55,   56,   79,   80,   81,   82,   83,   84,   44,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         62,
    0,    5,    6,    5,    6,    5,    6,    6,    3,   20,
    4,    3,    4,    1,    8,   12,   41,   36,    1,   17,
   17,   18,   17,   60,   21,   23,   23,   24,   25,   26,
   27,   28,   29,   30,   31,   32,   33,   60,   32,   50,
   40,   41,   61,   38,   48,   21,   48,   42,   48,   48,
   42,   48,   49,   45,   12,   43,    1,    2,    3,   17,
   13,   14,   18,   60,    1,   65,   19,   67,   68,   63,
   12,   17,   64,   12,   63,   17,   18,   22,   17,   18,
    0,   23,   24,   25,   23,   36,   28,   29,   30,   31,
   32,   33,   92,   13,   14,   12,   60,   12,   60,   19,
   17,  105,   17,  105,   13,  105,  105,   13,   23,  104,
   36,  115,   12,  115,   12,  115,  115,   17,   60,   17,
   18,   60,    1,   23,   11,   23,   24,   25,   24,   25,
   28,   29,   30,   31,   32,   33,   12,   61,   40,   41,
   39,   17,   18,   57,   58,   60,   13,   23,   24,   25,
   18,   19,   28,   29,   30,   31,   32,   33,   12,   42,
   60,   13,   60,   17,   18,   67,   68,   61,   11,   23,
   60,    1,    2,    3,   28,   29,   30,   31,   32,   33,
   12,    0,   13,   60,   60,   17,   18,   60,    1,    2,
    3,   23,   22,   13,   24,   25,   28,   29,   30,   31,
   32,   33,   12,   60,   34,   60,   60,   17,   18,   22,
  113,   24,   25,   23,   26,   27,  121,   -1,   28,   29,
   30,   31,   32,   33,   12,   49,   13,   14,   60,   17,
   18,  119,   19,   13,   14,   23,   48,   49,   -1,   19,
   28,   29,   30,   31,   32,   33,   12,   -1,   -1,   -1,
   60,   17,   18,   59,   60,   61,   62,   23,   -1,   -1,
   -1,   -1,   28,   29,   30,   31,   32,   33,   12,   -1,
   -1,   -1,   60,   17,   18,   -1,   -1,   -1,   -1,   23,
   -1,   -1,   -1,    1,   28,   29,   30,   31,   32,   33,
   12,   -1,   -1,   11,   60,   17,   18,   15,   -1,   -1,
   -1,   23,   -1,   -1,   -1,   -1,   28,   29,   30,   31,
   32,   33,   -1,   -1,   -1,   -1,   60,   35,   -1,   -1,
   38,   39,   40,   -1,   -1,   43,   28,   29,   30,   31,
   32,   33,   51,   52,   53,   54,   55,   56,   60,
};
}
final static short YYFINAL=9;
final static short YYMAXTOKEN=100;
final static String yyname[] = {
"end-of-file","Ident","IntConst","RealConst","Boolean",null,null,null,null,null,
null,"If","Then","End","Elsif","Assert","Put","Or","And","Else",null,"Assign",
"LParen","RParen","Plus","Minus","Star","Slash","Eq","Ne","Lt","Le","Gt","Ge",
"Not","Bind","To",null,"Begin","Loop","Exit","When","Record","Var","NoType",
"integer",null,null,"Div","Mod",null,null,null,null,null,null,null,null,null,
null,"Semi","Colon","Dot","Comma","Real",null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,"start",
};
final static String yyrule[] = {
"$accept : program",
"program : pStateDeclSeq",
"pStateDeclSeq :",
"pStateDeclSeq : statement Semi pStateDeclSeq",
"pStateDeclSeq : declaration Semi pStateDeclSeq",
"idlist : Ident",
"idlist : Ident Comma idlist",
"type : integer",
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
"statement : ref Assign expr",
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
"rel_expr : rel_expr Eq sum",
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

//#line 182 "proj2.y"
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

//#line 384 "Parser.java"
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
case 1:
//#line 22 "proj2.y"
{root = new Tree(-1, null, null, null, null);
		root.addNext(val_peek(0).obj);}
break;
case 2:
//#line 26 "proj2.y"
{yyval.obj = null;}
break;
case 3:
//#line 28 "proj2.y"
{yyval.obj = val_peek(2).obj; ((Tree)yyval.obj).addNext(val_peek(0).obj);}
break;
case 4:
//#line 30 "proj2.y"
{yyval.obj = val_peek(2).obj; ((Tree) yyval.obj).addNext(val_peek(0).obj);}
break;
case 5:
//#line 33 "proj2.y"
{yyval.obj = new Tree(Ident, null, null, null, null);}
break;
case 6:
//#line 35 "proj2.y"
{yyval.obj = new Tree(Ident, null, null, null, null); ((Tree)yyval.obj).addNext(val_peek(0).obj);}
break;
case 7:
//#line 38 "proj2.y"
{yyval.obj = integer; }
break;
case 8:
//#line 40 "proj2.y"
{yyval.obj = RealConst;}
break;
case 9:
//#line 42 "proj2.y"
{yyval.obj = Real;}
break;
case 10:
//#line 44 "proj2.y"
{yyval.obj = Boolean;}
break;
case 11:
//#line 46 "proj2.y"
{yyval.obj = new Tree(Record, null, val_peek(2).obj, null, null);}
break;
case 12:
//#line 49 "proj2.y"
{yyval.obj = val_peek(2).obj;
		((Tree) yyval.obj).type = (Short)val_peek(0).obj;}
break;
case 13:
//#line 52 "proj2.y"
{yyval.obj = val_peek(4).obj; ((Tree)yyval.obj).addNext(val_peek(0).obj);
		((Tree) yyval.obj).type = (Short)val_peek(2).obj;
		}
break;
case 14:
//#line 57 "proj2.y"
{yyval.obj = null;}
break;
case 15:
//#line 59 "proj2.y"
{yyval.obj = val_peek(2).obj; ((Tree)yyval.obj).addNext(val_peek(0).obj);}
break;
case 16:
//#line 61 "proj2.y"
{yyval.obj = val_peek(2).obj; ((Tree)yyval.obj).addNext(val_peek(0).obj);}
break;
case 17:
//#line 64 "proj2.y"
{
			if (val_peek(0).obj instanceof Short){
				yyval.obj = new Tree(Var, null, val_peek(2).obj, null, null); 
				((Tree) yyval.obj).type = (Short) val_peek(0).obj;
			}
			else {
				yyval.obj = new Tree(Var, null, val_peek(2).obj, val_peek(0).obj, null); 
				((Tree) yyval.obj).type = Record;
			}
		}
break;
case 18:
//#line 75 "proj2.y"
{
		yyval.obj = new Tree(Bind, null, val_peek(2).obj, val_peek(0).obj, null);
		}
break;
case 19:
//#line 79 "proj2.y"
{
		yyval.obj = new Tree(Bind, " var", val_peek(2).obj, val_peek(0).obj, null);
		}
break;
case 20:
//#line 84 "proj2.y"
{yyval.obj = new Tree(Assign, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 21:
//#line 86 "proj2.y"
{yyval.obj = new Tree (Assert, null, val_peek(0).obj, null, null);}
break;
case 22:
//#line 88 "proj2.y"
{yyval.obj = new Tree(Begin, null, val_peek(1).obj, null, null);}
break;
case 23:
//#line 90 "proj2.y"
{yyval.obj = new Tree(Loop, null, val_peek(2).obj, null, null);
		((Tree)yyval.obj).addNext(new Tree(End, " Loop", null, null, null));}
break;
case 24:
//#line 93 "proj2.y"
{yyval.obj = new Tree(Exit, null, null, null, null);}
break;
case 25:
//#line 95 "proj2.y"
{yyval.obj = new Tree(Exit, " When", val_peek(0).obj, null, null);}
break;
case 26:
//#line 97 "proj2.y"
{yyval.obj = new Tree(If, null, val_peek(3).obj, val_peek(1).obj, val_peek(0).obj);}
break;
case 27:
//#line 100 "proj2.y"
{yyval.obj = new Tree(Ident, null, null, null, null);}
break;
case 28:
//#line 102 "proj2.y"
{
		yyval.obj = new Tree(Dot, null, null, null, null );
		}
break;
case 29:
//#line 107 "proj2.y"
{yyval.obj = new Tree(End, " If", null, null, null);}
break;
case 30:
//#line 109 "proj2.y"
{yyval.obj = new Tree(Else, null, val_peek(2).obj, null, null); 
		((Tree)yyval.obj).addNext(new Tree(End, " If", null, null, null));}
break;
case 31:
//#line 112 "proj2.y"
{yyval.obj = new Tree(Elsif, null, val_peek(3).obj, val_peek(1).obj, null);
		((Tree)yyval.obj).addNext(val_peek(0).obj);}
break;
case 32:
//#line 116 "proj2.y"
{yyval.obj = new Tree(Or, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 33:
//#line 118 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 34:
//#line 121 "proj2.y"
{yyval.obj = new Tree(And, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 35:
//#line 123 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 36:
//#line 126 "proj2.y"
{yyval.obj = new Tree(Not, null, val_peek(0).obj, null, null);}
break;
case 37:
//#line 128 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 38:
//#line 131 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 39:
//#line 133 "proj2.y"
{yyval.obj = new Tree(Eq, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 40:
//#line 135 "proj2.y"
{yyval.obj = new Tree(Ne, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 41:
//#line 137 "proj2.y"
{yyval.obj = new Tree(Lt, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 42:
//#line 139 "proj2.y"
{yyval.obj = new Tree(Le, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 43:
//#line 141 "proj2.y"
{yyval.obj = new Tree(Gt, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 44:
//#line 143 "proj2.y"
{yyval.obj = new Tree(Ge, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 45:
//#line 146 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 46:
//#line 148 "proj2.y"
{yyval.obj = new Tree(Plus, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 47:
//#line 150 "proj2.y"
{yyval.obj = new Tree(Minus, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 48:
//#line 153 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 49:
//#line 155 "proj2.y"
{yyval.obj = new Tree(Star, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 50:
//#line 157 "proj2.y"
{yyval.obj = new Tree(Slash, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 51:
//#line 159 "proj2.y"
{yyval.obj = new Tree(Div, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 52:
//#line 161 "proj2.y"
{yyval.obj = new Tree(Mod, null, val_peek(2).obj, val_peek(0).obj, null);}
break;
case 53:
//#line 164 "proj2.y"
{yyval.obj = new Tree(Plus, null, val_peek(0).obj, null, null);}
break;
case 54:
//#line 166 "proj2.y"
{yyval.obj = new Tree(Minus, null, val_peek(0).obj, null, null);}
break;
case 55:
//#line 168 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 56:
//#line 171 "proj2.y"
{yyval.obj = val_peek(0).obj;}
break;
case 57:
//#line 173 "proj2.y"
{yyval.obj = new Tree(LParen, null, val_peek(1).obj, null, null);
		((Tree)yyval.obj).addNext(new Tree(RParen, null, null, null, null));}
break;
case 58:
//#line 176 "proj2.y"
{yyval.obj = new Tree(IntConst, null, null, null, null);}
break;
case 59:
//#line 178 "proj2.y"
{yyval.obj = new Tree(RealConst, null, null, null, null);}
break;
//#line 792 "Parser.java"
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
