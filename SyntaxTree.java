package compilerDesign;
import compilerDesign.*;
import java.util.*;

public class SyntaxTree {
	private BinaryTree binTree;
	private Node root;
	private Set<LeafNode> leafSet;
	public SyntaxTree(String regex) {
		binTree=new BinaryTree(regex);
		root=binTree.getbinTreeRoot();
		leafSet=new HashSet<LeafNode>();
		leafSet=binTree.getLeaves();
		this.checkNullable(root);
		this.genFirstPos(root);
		this.genLastPos(root);
		this.genFollowPos(root);
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void printData() {
		System.out.println("All nodes with Firstpos and Lastpos:");
		this.printInOrder(root);
		System.out.println("All leaves with followpos:");
		this.printDataFollowPos(root);
	}
	
	private void printInOrder(Node root) {
		if(root!=null) {
			printInOrder(root.getLeft());
			System.out.println(root.getSymbol()+":-"+root.getFirstPos()+"(FP);"+root.getLastPos()+"(LP)");
			printInOrder(root.getRight());
		}
	}
	
	private void printDataFollowPos(Node root) {
		if(root != null) {
			printDataFollowPos(root.getLeft());
			if(root instanceof LeafNode) {
				LeafNode temp=(LeafNode)root;
				System.out.println("("+temp.getSymbol()+","+temp.getNum()+"):-"+temp.getFollowPos());
			}
			printDataFollowPos(root.getRight());
		}
	}
	
	private void checkNullable(Node root) {
		if(root==null) return;
		checkNullable(root.getLeft());
		checkNullable(root.getRight());
		if(root instanceof LeafNode) {
			return;
		}
		else if(root.getSymbol().charAt(0)=='|') {
			if(root.getLeft().isNullable()||root.getRight().isNullable()) root.setNullable();
		}
		else if(root.getSymbol().charAt(0)=='&') {
			if(root.getLeft().isNullable()&&root.getRight().isNullable()) root.setNullable();
		}
		else if(root.getSymbol().charAt(0)=='*') {
			root.setNullable();
		}
	}
	
	private void genFirstPos(Node root) {
		if(root==null) return;
		genFirstPos(root.getLeft());
		genFirstPos(root.getRight());
		if(root instanceof LeafNode) {
			LeafNode temp=(LeafNode) root;
			root.addToFirstPos(temp.getNum());
			
		}
		else if(root.getSymbol().charAt(0)=='|') {
			Set<Integer> tempSetLeft=root.getLeft().getFirstPos();
			Set<Integer> tempSetRight=root.getRight().getFirstPos();
			root.addAllToFirstPos(tempSetLeft);
			root.addAllToFirstPos(tempSetRight);
			
		}
		else if(root.getSymbol().charAt(0)=='&') {
			if(root.getLeft().isNullable()) {
				Set<Integer> tempSetLeft=root.getLeft().getFirstPos();
				Set<Integer> tempSetRight=root.getRight().getFirstPos();
				root.addAllToFirstPos(tempSetLeft);
				root.addAllToFirstPos(tempSetRight);
			}
			else {
				Set<Integer> tempSetLeft=root.getLeft().getFirstPos();
				root.addAllToFirstPos(tempSetLeft);
			}
			
		}
		else if(root.getSymbol().charAt(0)=='*') {
			Set<Integer> tempSetLeft=root.getLeft().getFirstPos();
			root.addAllToFirstPos(tempSetLeft);
			
		}
		
	}
	
	private void genLastPos(Node root) {
		if(root==null) return;
		genLastPos(root.getLeft());
		genLastPos(root.getRight());
		if(root instanceof LeafNode) {
			LeafNode temp=(LeafNode) root;
			root.addToLastPos(temp.getNum());
		}
		else if(root.getSymbol().charAt(0)=='|') {
			Set<Integer> tempSetLeft=root.getLeft().getLastPos();
			Set<Integer> tempSetRight=root.getRight().getLastPos();
			root.addAllToLastPos(tempSetLeft);
			root.addAllToLastPos(tempSetRight);
		}
		else if(root.getSymbol().charAt(0)=='&') {
			if(root.getRight().isNullable()) {
				Set<Integer> tempSetLeft=root.getLeft().getLastPos();
				Set<Integer> tempSetRight=root.getRight().getLastPos();
				root.addAllToLastPos(tempSetLeft);
				root.addAllToLastPos(tempSetRight);
			}
			else {
				Set<Integer> tempSetRight=root.getRight().getLastPos();
				root.addAllToLastPos(tempSetRight);
			}
		}
		else if(root.getSymbol().charAt(0)=='*') {
			Set<Integer> tempSetLeft=root.getLeft().getLastPos();
			root.addAllToLastPos(tempSetLeft);
		}
	}
	
	private LeafNode getLeaf(int id) {
		for(LeafNode temp:leafSet) {
			if(temp.getNum()==id) return temp;
		}
		return null;
	}
	
	public Set<LeafNode> getLeaves(){
		return leafSet;
	}
	
	private void genFollowPos(Node root) {
		if(root==null||root instanceof LeafNode) return;
		if(root.getSymbol().charAt(0)=='|') {
			genFollowPos(root.getLeft());
			genFollowPos(root.getRight());
		}
		else{
			genFollowPos(root.getLeft());
			genFollowPos(root.getRight());
			if(root.getSymbol().charAt(0)=='&') {
				for(int i:root.getLeft().getLastPos()) {
					Set<Integer> temp=root.getRight().getFirstPos();
					this.getLeaf(i).addAllToFollowPos(temp);
				}
			}
			else if(root.getSymbol().charAt(0)=='*') {
				for(int i:root.getLastPos()) {
					Set<Integer> temp=root.getFirstPos();
					this.getLeaf(i).addAllToFollowPos(temp);
				}
			}
		}
		
	}
	
}
