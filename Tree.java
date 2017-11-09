
class Tree{
	private Integer name; private String decor;
	private Tree first, second, third, next;
	
	Tree (int nodeName, Object nodeDecor, Object firstSub, Object secondSub, Object thirdSub)
	{
		this.name = nodeName; 
		this.decor = (String) nodeDecor;
		this.first = (Tree) firstSub;
		this.second = (Tree) secondSub;
		this.third = (Tree) thirdSub;
	}

	void addNext(Object follow)
	{
		this.next = (Tree) follow;
	}
	
	public static void printTree (Tree t, int indent)
	{
		if (t != null){
			if (t.name != -1){
			for (int i = 0; i < indent; i++){
				System.out.print ("   ");
			}
				System.out.print(Parser.yyname[t.name]);
				if (t.name == 1 || t.name == 2)
					System.out.print(" " + turing.values.poll());
				if (t.decor != null) 
					System.out.print(t.decor);
				System.out.println();
		}	
			printTree (t.first, indent + 1);
			printTree (t.second, indent + 1);
			printTree (t.third, indent + 1);
			printTree (t.next, indent);
		}
	}
}
