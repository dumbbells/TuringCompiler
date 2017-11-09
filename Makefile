JC = javac
LEX = jflex
YACC = byacc-j -J

turing.class : turing.java Yylex.java Tree.class Parser.class
	$(JC) turing.java

Yylex.java : proj1.l proj2.y Parser.java
	-@rm -f Yylex.java
	$(LEX) proj1.l

Parser.class : Parser.java
	javac -Xdiags:verbose Parser.java

Parser.java : proj2.y
	$(YACC) proj2.y

Tree.class : Tree.java
	$(JC) Tree.java
clean :
	-@ rm *.class
	-@ rm Yylex.java
	-@ rm Parser.java 
	-@ rm ParserVal.java
