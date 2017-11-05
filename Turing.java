import java.io.*;

public class Turing {
	public static void main (String args[]) throws IOException
	{
		InputStreamReader f = new FileReader (args[0]);
		Parser parser = new Parser (true);
		Yylex lexer = new Yylex (f, parser);
		parser.lexer = lexer;
		System.out.println("\tBegin Parse");
		parser.run();
		System.out.println("\tDone");
	}
}
