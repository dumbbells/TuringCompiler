JC = javac
LEX = jflex
YACC = byaccj -Jv
LLIBS = 
Yylex.java : proj1.l proj2.y Parser.java
	-@rm Yylex.java
	$(LEX) proj1.l
Parser.java : proj2.y
	$(YACC) proj2.y

clean :
	-@ rm *.class
	-@ rm Yylex.java
	-@ rm Parser.java 
	-@ rm ParserVal.java
