
class Tree{
	String value;
	Integer name;
	Short type = -1;
	String decor;
	Tree first, second, third, next;
	
	Tree (int nodeName, Object nodeDecor, 
	Object firstSub, Object secondSub, Object thirdSub)
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
				if (t.name == 62) {
					t.value = turing.values.poll() + "." + turing.values.poll();
					System.out.println(t.value);
				}
				else {
					System.out.print(Parser.yyname[t.name]);
					if (t.name == 1 || t.name == 2 || t.name == 3) {
						t.value = turing.values.poll();
						System.out.print(" " + t.value);
					}
					if (t.decor != null) 
						System.out.print(t.decor);
					System.out.println();
				}
			}
			printTree (t.first, indent + 1);
			printTree (t.second, indent + 1);
			printTree (t.third, indent + 1);
			printTree (t.next, indent);
		}
	}
}
