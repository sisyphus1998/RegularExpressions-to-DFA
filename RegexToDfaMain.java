package compilerDesign;
import compilerDesign.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RegexToDfaMain { 
	public static void main(String[] args) throws Exception, IOException {
		/*BinaryTree btobj=new BinaryTree("[a-d]");
		
		System.out.println(btobj.getRegStr());
		btobj.printTree();
		SyntaxTree stobj=new SyntaxTree("[a-f]");
		stobj.printData();
	
		DFA dobj=new DFA("[a-d]");
		dobj.printTable();
		
		DFAGraphics okob=new DFAGraphics(dobj);*/
		System.out.println("---REGULAR EXPRESSION TO DFA CONVERSION PROGRAM---");
		System.out.println("INPUT SPECIFICATIONS--->\r\nUse Characters from a-z or A-Z\r\nAvailable operations/input specifications:--->");
		System.out.println("-->Input can be taken in the form [X_i-X_j] where X_i and X_j are alphabetic characters\r\nNOTE**==(i<j always following the ordering of alphabet characters) ");
		System.out.println("*==>CLOSURE;|==>BOOLEAN OR are the allowed operators, concatenation is done automatically---");
		System.out.println("SPECIFY INPUT STRING AT THE BEGINNING:------>\r\n");
		int choice=0,choice2=0;
		String str;
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		while(choice!=2) {
			System.out.println("1.ENTER REGULAR EXPRESSION IN STRING FORM:\r\n2.EXIT");
			choice=Integer.parseInt(in.readLine());
			if(choice==1) {
				System.out.println("Enter the regEx in string format:-->");
				str=in.readLine();
				while(choice2!=5) {
					System.out.println("1.Generate Table for Syntax Tree\r\n2.Generate the DFA transition table\r\n3.Check for a String's acceptance\r\n4.Generate DFA State Diagram\r\n5.Exit to menu");
					choice2=Integer.parseInt(in.readLine());
					switch(choice2) {
					case(1):{
						SyntaxTree stobj=new SyntaxTree(str);
						stobj.printData();
						break;
					}
					case(2):{
						DFA dobj=new DFA(str);
						dobj.printTable();
						break;
					}
					case(4):{
						DFA dobj=new DFA(str);
						DFAGraphics newobj=new DFAGraphics(dobj);
						break;
					}
					case(3):{
						String str2;
						System.out.println("Enter the string for checking--->");
						str2=in.readLine();
						DFA dobj=new DFA(str);
						dobj.isValidString(str2);
					}
					case(5):{
						System.out.println("----EXITING TO MAIN MENU----");
						break;
					}
					}
				}
				
			}
		}
	}
}
