package compilerDesign;
import compilerDesign.*;
import java.util.*;

public class BinaryTree {
	private String regex;
	private int leafNodeId=0;
	@SuppressWarnings("unused")
	private char[] opSet= {'(',')','*','&','|','-'};
	private char[] symbolSet;
	private Stack<Node> nodeStack;
	private Stack<Character> opStack;
	private Node root;
	private Set<LeafNode> leaves;
	
	public BinaryTree(String reg) {

		symbolSet=new char[26+26+1];
		int i=(int)'A';
		while(i!=(int)'Z'+1) {
			symbolSet[i-(int)'A']=(char)i;
			i++;
		}
		i=(int)'a';
		while(i!=(int)'z'+1) {
			symbolSet[i-(int)'a']=(char)i;
			i++;
		}
		symbolSet[52]='#';
		regex=initConcat(reg);
		nodeStack=new Stack<Node>();
		opStack=new Stack<Character>();
		leaves=new HashSet<LeafNode>();
		this.makeBinaryTree();
	}
	
	public Node getbinTreeRoot() {
		return this.root;
	}
	
	public String getRegStr() {
		return this.regex;
	}
	
	public void printTree() {
		System.out.println("Inorder:");
		this.printInorder(root);
		System.out.println("\r\nLeaves only:");
		this.printInorderLeaf(root);
	}
	
	private void printInorder(Node root) {
		if(root != null) {
			printInorder(root.getLeft());
			System.out.print(root.getSymbol());
			printInorder(root.getRight());
		}
		
	}
	
	public Set<LeafNode> getLeaves() {
		return leaves;
	}
	
	private void printInorderLeaf(Node root) {
		if(root != null) {
			printInorderLeaf(root.getLeft());
			if(root instanceof LeafNode) {
				LeafNode temp=(LeafNode) root;
				System.out.println("("+ temp.getSymbol()+","+temp.getNum()+")");
			}
			
			printInorderLeaf(root.getRight());
		}
		
	}
	
	public void showRegCon() {
		System.out.println(regex);
	}
	
	private void makeBinaryTree() {
		int len=regex.length();
		for(int i=0;i<len;i++) {
			if(isSymbol(regex.charAt(i))) {
				this.pushNode(regex.charAt(i));
			}
			else if(opStack.isEmpty()) opStack.push(regex.charAt(i));
			else if(regex.charAt(i)==')') {
				while(opStack.peek()!='(') this.operate();
				opStack.pop();
			}
			else {
				if(this.priority(opStack.peek(), regex.charAt(i))) this.operate();
				opStack.push(regex.charAt(i));
			}
		}
		while(!opStack.isEmpty()) operate();
	}
	
	private boolean isSymbol(char ch) {
		for(int i=0;i<symbolSet.length;i++) {
			if(ch==symbolSet[i])return true;
		}
		return false;
	}
	
	private String initConcat(String raw) {
		int len=raw.length(),pos=0;
		raw=raw.replace(']', ')');
		String ret=new String("");
		for(int i=0;i<len-1;i++) {
			if(isSymbol(raw.charAt(i)) && isSymbol(raw.charAt(i+1))) {
				ret=ret+raw.substring(pos, i+1)+"&";
				pos=i+1;
			}
			else if((raw.charAt(i)==')'||raw.charAt(i)=='*')&& (isSymbol(raw.charAt(i+1))||raw.charAt(i+1)=='(')) {
				ret=ret+raw.substring(pos, i+1)+"&";
				pos=i+1;
			}
			else if(isSymbol(raw.charAt(i)) && raw.charAt(i+1)=='(') {
				ret=ret+raw.substring(pos, i+1)+"&";
				pos=i+1;
			}
			else if(raw.charAt(i)=='[') {
				i++;
				int temp=(int)raw.charAt(i);
				i=i+2;
				int tempn=(int)raw.charAt(i);
				String t="";
				while(temp<=tempn) {
					t+=(char)temp;
					temp++;
				}
				t=this.initConcat(t);
				t=t.substring(0, t.length()-2);
				t="("+t;
				pos=i+1;
				ret=ret+t;
			}
		}
		ret+=raw.substring(pos);
		ret+="&#";
		return ret;
	}
	
	private boolean priority(char c1,char c2) {
		if(c1==c2) return true;
		else if(c1=='*') return true;
		else if(c2=='*') return false;
		else if(c1=='&'&&c2=='|') return true;
		else if(c1=='(') return false;
		else return false;
	}
	
	private Node pushNode(char id) {
		if(isSymbol(id)) {
			Node newNode=new LeafNode(Character.toString(id),++leafNodeId);
			nodeStack.push(newNode);
			leaves.add((LeafNode)newNode);
			return newNode;
		}
		else {
			Node newNode=new Node(Character.toString(id));
			nodeStack.push(newNode);
			return newNode;
		}
	}
	
	private void operate() {
		char sw=opStack.pop();
		if(sw=='|') union();
		else if(sw=='&') concat();
		else if(sw=='*') closure();
	}
	
	private void union() {
		Node right=nodeStack.pop();
		Node left=nodeStack.pop();
		Node newRoot=pushNode('|');
		newRoot.setLeft(left);
		newRoot.setRight(right);
		root=newRoot;
	}
	
	private void concat() {
		Node right=nodeStack.pop();
		Node left=nodeStack.pop();
		Node newRoot=pushNode('&');
		newRoot.setLeft(left);
		newRoot.setRight(right);
		root=newRoot;
	}
	
	private void closure() {
		Node left=nodeStack.pop();
		Node newRoot=pushNode('*');
		newRoot.setLeft(left);
		root=newRoot;
	}
}
