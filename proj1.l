import java.io.*;
import java.util.Scanner;

%%
%byaccj

%{
private Parser parser;
private PrintWriter outFile;
public Yylex (java.io.Reader inFile, Parser parser)
{
	this (inFile);
	this.parser = parser;
	this.outFile = outFile;
}
private int line = 0;

void yyerror (String s)
{
	System.err.println (s);
}
%}

L = [A-Za-z]
D = [0-9]

%%
^"."C.*	{	; }
[ \t]+	{	; }
"if"			{return parser.If;}
"then"		{return parser.Then;}
"end"		{return parser.End;}
"var" 	{return parser.Var;}
"int"	{return parser.Integer;}
"real"	{return parser.Real;}
"boolean"	{return parser.Boolean;}
"record"	{return parser.Record;}
"end"	{return parser.End;}
"bind"	{return parser.Bind;}
"to"	{return parser.To;}
"assert"	{return parser.Assert;}
"begin"	{return parser.Begin;}
"loop"	{return parser.Loop;}
"exit"	{return parser.Exit;}
"when"	{return parser.When;}
"else"	{return parser.Else;}
"elsif"	{return parser.Elsif;}
"put"	{return parser.Put;}
"or"	{return parser.Or;}
"and"	{return parser.And;}
"not"	{return parser.Not;}
"div"	{return parser.Div;}
"mod"	{return parser.Mod;}
{L}({L}|{D}|"_")*	{return parser.Ident;}
{D}+"."+{D}+		{return parser.RealConst;}
{D}+		{return parser.IntConst;}
";"	{return parser.Semi;}
":"	{return parser.Colon;}
"."	{return parser.Dot;}
","	{return parser.Comma;}
"="		{return parser.Assign;}
":="	{return parser.Eq;}
"not="	{return parser.Ne;}
"<"	{return parser.Lt;}
"<="	{return parser.Le;}
">"	{return parser.Gt;}
">="	{return parser.Ge;}
"+"		{return parser.Plus;}
"-"		{return parser.Minus;}
"*"		{return parser.Star;}
"/"		{return parser.Slash;}
"("		{return parser.LParen;}
")"		{return parser.RParen;}
"\n"		{line++;}
.			{yyerror ("Bad Character");}
