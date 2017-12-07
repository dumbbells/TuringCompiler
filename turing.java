import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

public class turing {
	public static Queue<String> values = new LinkedList<>();
	public static void main (String args[]) throws Exception
	{
		InputStreamReader f = new FileReader (args[0]);
		String outfile = args[1];
		Parser parser = new Parser (false);
		Yylex lexer = new Yylex (f, parser);
		parser.lexer = lexer;
		System.out.println("Begin Parse");
		parser.run();
		Tree.printTree(parser.root, 0);
		System.out.println("\nDone\n");
		ST.beginCheck(parser.root);
		Instr instr[] = Instr.getInstructionArray();
		//System.out.println(instr[Instr.DUPW].name);
        Generate gen = new Generate(parser.root, instr, outfile);
	}
}
