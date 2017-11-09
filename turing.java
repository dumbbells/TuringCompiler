import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

public class turing {
	public static Queue<String> values = new LinkedList<>();
	public static void main (String args[]) throws IOException
	{
		InputStreamReader f = new FileReader (args[0]);
		Parser parser = new Parser (false);
		Yylex lexer = new Yylex (f, parser);
		parser.lexer = lexer;
		System.out.println("Begin Parse");
		parser.run();
		Tree.printTree(parser.root, -1);
		System.out.println("\nDone");
	}
}
