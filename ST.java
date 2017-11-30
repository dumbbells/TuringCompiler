import java.util.*;

class ST {
	String name;
	int scope = -1;
	short type = -1;
	List<ST> members = null;
	String boundTo = null;
	boolean bound = false;
	
	public static Stack<ST> STStack = new Stack();
	public static List<ST> STList = new ArrayList();
	public static int currentScope = 0;

	public ST(ST oldEntry) {
		this.name = oldEntry.name;
		this.scope = oldEntry.scope;
		this.type = oldEntry.type;
	}

	public ST(String name, int scope, Tree members) {
		this.name = name;
		this.scope = scope;
		this.type = Parser.Record;
		this.members = new ArrayList();
		for (; members != null; members = members.next) {
			this.members.add(new ST(members.value, currentScope, members.type.shortValue()));	
		} 
	}

	public ST(String name, int scope, short type) {
		this.name = name;
		this.scope = scope;
		this.type = type;
	}

	public static void upScope() {
		currentScope++;
		printST();
	}

	public static void downScope() {
		currentScope--;
		Iterator<ST> iter = STList.iterator();
		while (iter.hasNext()) {
		    ST entry = iter.next();
		    if (entry.boundTo != null && entry.scope > currentScope){
				ST bound = getEntry(entry.boundTo);
				bound.bound = false;
				iter.remove();
			}
		}
		if (!STStack.empty()){
			while (!STStack.empty() && currentScope == STStack.peek().scope) {
				ST stackMember = STStack.pop();
				for (ST entry : STList) {
					if (entry.name.equals(stackMember.name)) {
						STList.add(stackMember);
						STList.remove(entry);
						break;
					}
				}		
			}
		}
		printST();
	}

	public static void printST() {
		System.out.println("\t\t\t<------Symbol Table------->");
		if (STList.isEmpty()) {
			System.out.println("\tEmpty");
			return;
		}
		System.out.println("Current Scope: " + currentScope + "\n");
		System.out.println("scope\tvisible\tid\tbound\ttype");
		for (ST entry : STList){
			System.out.print(entry.scope + "\t" + !entry.bound + "\t"  + entry.name + "\t" 
			+ (entry.boundTo == null ? "none" : entry.boundTo)
			+ "\t" + Parser.yyname[entry.type]);
			if (entry.type == Parser.Record) {
				System.out.println("\n\t\t<--Fields of " + entry.name + "-->");
				for (ST member : entry.members){
					System.out.println(member.scope + "\t" + (!entry.bound)
					+ "\t"  + member.name + "\t" 
					+ (member.boundTo == null ? "none" : member.boundTo)
					+ "\t" + Parser.yyname[member.type]);
				}
			System.out.print("\t\t<---End Fields-->");
			}
			System.out.println();
		}
		if (!STStack.empty()){			
			System.out.println("\t\t\t<----------Stack----------> ");
			Object[] temp = STStack.toArray();
			for (int x = 0; x < temp.length; x++){
				System.out.println(((ST) temp[x]).scope + "\t" + ((ST)temp[x]).name + "\t" + Parser.yyname[((ST)temp[x]).type]);
			}
		}
		System.out.println("\t\t\t<------End of Table------->");
	}

	public static boolean exists(Object obj) {
		if (obj == null) return false;
		Tree temp = ((Tree) obj);
		if (temp.name == Parser.Dot){
			if (getEntry(temp.value) != null) return true;
			return false;
		}
		if (obj == null) return false;
			for (ST entry : STList) {
				if (entry.name.equals(((Tree) obj).value) && !entry.bound) {
					return true;
				}
			}
		return false;
	}


	public static boolean validRedec(Object obj) {
		if (obj == null) return false;
		for (ST entry : STList) {
			if (entry.name.equals(((Tree) obj).value)) {
				if (entry.scope == currentScope || entry.bound)
					return false;
				STStack.push(new ST(entry));
				STList.remove(entry);
				return true;
			}
		}
		return true;
	}
	
	public static ST getEntry(Object obj) {
		if (obj == null) return null;
		if (obj instanceof Tree) {
			if (((Tree) obj).name == Parser.Dot) {
				obj = ((Tree) obj).value;
			}
			else {
				for (ST entry : STList) {
					if (entry.name.equals(((Tree) obj).value) && !entry.bound) {
						return entry;
					}
				}
				return null;
			}
		}
		if (obj instanceof String) {
			String temp = (String) obj;
			String[] temp1 = temp.split("\\.");
			for (ST entry : STList) {
				if (entry.name.equals(temp1[0]) && entry.type == Parser.Record) {
					for (ST member : entry.members) {
						if (member.name.equals(temp1[1]) && !member.bound){
							return member;
						}
					}
				}
			}
			
		}
		return null;
	}

	public static short checkExp (Tree t) {
		if (t == null) {
			System.out.println("Missing expression");
			return -1;
		}
		switch(t.name.shortValue()) {
			case Parser.Dot :
				String[] temp = t.value.split("\\.");
				for (ST entry : STList) {
					if (entry.name.equals(temp[0]) && entry.type == Parser.Record) {
						for (ST member : entry.members) {
							if (member.name.equals(temp[1])){
								return member.type;
							}
						}
					}
				}
				System.out.println("Error, symbol " + t.value + " does not exist!!");
				return -1;
			case Parser.Gt: case Parser.Ge:
			case Parser.Eq: case Parser.Ne:
			case Parser.Lt: case Parser.Le:
			case Parser.Plus : case Parser.Minus :
			case Parser.Star : case Parser.Slash :
				short t1 = checkExp(t.first), t2 = checkExp(t.second);
				if (t1 != t2) {
					System.out.println("Type mismatch");
					return -1;
				}
				else {
					return t1;
				}
			case Parser.Ident :
				if (exists(t)) {
					ST entry = getEntry(t);
					return entry.type;
				}
				else {
					System.out.println("Symbol " + t.value + " does not exist");
					return -1;
				}
			case Parser.IntConst: case Parser.RealConst:
			case Parser.Boolean: case Parser.integer:
			case Parser.Real:
				return t.name.shortValue();
			default: 
				System.out.println("Invalid expression");
		}
		return -1;
	}

	public static ST checkAssign(Tree t) {
		if (exists(t)){
			if (getEntry(t).type != t.type){
				if (getEntry(t).type == Parser.Real && t.type == Parser.integer)
				return getEntry(t);
				if (getEntry(t).type == Parser.Real && t.type == Parser.IntConst)
				return getEntry(t);
				if (getEntry(t).type == Parser.Real && t.type == Parser.RealConst)
				return getEntry(t);
				System.out.println("Error, assignment of wrong type for " + t.value);
				return null;
			}
			return getEntry(t);  
		}
		else {
			System.out.println("Error, " + t.value + " not found in ST");
			return null;
		}
	}
	
	public static void checkDecl(Tree t) {
		short type = t.type;
		if (type == -1) {
			System.out.println("Invalid type: " + type);
			return;
		}
		for (Tree p = t.first; p!= null; p = p.next) {
			if (!validRedec(p)) {
				System.out.println("Error: redeclaration of var: " + p.value);	
				return;
			}
			else if (type == Parser.Record) {
				STList.add(new ST(p.value, currentScope, t.second.first));
			}
			else {
				STList.add(new ST(p.value, currentScope, type));
			}
		}
				
	}

	public static void beginCheck(Tree t) {
		System.out.println("\t\tBeginning type and scope checking");
		printST();
		check(t);
		System.out.println("\t\t Checking complete");
	}


	public static void check (Tree t) {
		for (; t != null; t = t.next) {
			switch (t.name.shortValue()) {
				case Parser.Loop :
					System.out.println("\t Enter loop");
					upScope();
					check(t.first);
					System.out.println("\t Exit loop");
					downScope();
					break;
				case Parser.Exit :
					if (t.first == null) break; 
					if (checkExp (t.first) != Parser.Boolean) {
						System.out.println("Error, must use a boolean expression inside of when.");
					}
					break;
				case Parser.And: case Parser.Or:
					if (checkExp(t.first) != Parser.Boolean || checkExp(t.second) != Parser.Boolean) {
						System.out.println("Must use a boolean expression!!!");
						break;
						}
				case Parser.Not: 
					if (checkExp(t.first) != Parser.Boolean){
						System.out.println("Must use a boolean expression!!!");
						break;
					}
				case Parser.Bind :
					if (exists(t.second)){
						ST entry = getEntry(t.second);
						t.type = entry.type;
						String boundTo = t.second.value;
						String[] temp = t.second.value.split("\\.");
						t.second = null;
						Tree parent = new Tree(-1, null, null, null, null);
						parent.value = temp[0];
						entry = getEntry(parent);
						checkDecl(t);
						ST newEntry = getEntry(t.first);
						newEntry.boundTo = boundTo;
						newEntry.bound = true;
						if (t.decor != null) {
							entry.bound = true;
						}
						}	
					break;
				case Parser.If :
					if (checkExp (t.first) != Parser.Boolean) {
						System.out.println("Error, must use a boolean expression inside of if.");
					}
					System.out.println("\tEntering if body");
					upScope();
					check (t.second);
					System.out.println("\tExiting if body");
					downScope();
					check (t.third);
					break;
				case Parser.Else :
					System.out.println("\tEntering else body");
					upScope();
					check(t.first);
					System.out.println("\tExiting else body");
					downScope();
					break;
				case Parser.Elsif :
					if (checkExp (t.first) != Parser.Boolean) {
						System.out.println("Error, must use a boolean expression inside of elsif.");
					}
					System.out.println("\tEntering elsif body");
					upScope();
					check (t.second);
					System.out.println("\tExiting elsif body");
					downScope();
					break;
				case Parser.Begin :
					System.out.println("\tEntering begin statement");
					upScope();
					check(t.first);
					System.out.println("\tExiting begin statement");
					downScope();
				case Parser.Var :
					checkDecl(t);
					break;
				case Parser.Assert :
					if (checkExp(t.first) != Parser.Boolean) {
						System.out.println("Bad Assertion!");
					}
					break;
				case Parser.Assign :
					ST temp = checkAssign(t.first);
					if (temp != null) {
						if (checkExp(t.second) == temp.type)
							break;
						System.out.println(t.value + " can't be assigned a " + temp.type + " value!!");
					}
					break;
			}
		}
	}	
}
