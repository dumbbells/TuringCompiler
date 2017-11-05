JC = javac
LEX = jflex
YACC = byaccj -Jv
LLIBS = 
%.class : %.java
	$(JC) $<

Turing.class : Turing.java proj1.l proj2.y
	-@ rm Yylex.java
	$(LEX) proj1.l
	$(YACC) proj2.y
	$(JC) *.java
	

clean :
	-@ rm *.class 
realclean : clean
	-@ rm Yylex.java
	-@ rm Parser.java 
