package compilerDesign;
import compilerDesign.*;
import java.util.*;

public class Node {

	private Node leftChild;
	private Node rightChild;
	private Node parent;
	private boolean nullable;
	private String symbol;
	private Set<Integer> firstPos;
	private Set<Integer> lastPos;
	
	public Node(String symbol) {
		this.symbol=symbol;
		nullable=false;
		firstPos=new HashSet<Integer>();
		lastPos=new HashSet<Integer>();
		leftChild=null;
		rightChild=null;
		parent=null;
	}
	
	public Set<Integer> getFirstPos() {
		return firstPos;
	}
	
	public Set<Integer> getLastPos() {
		return lastPos;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public Node getLeft() {
		return leftChild;
	}
	
	public Node getRight() {
		return rightChild;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setLeft(Node left) {
		leftChild=left;
	}
	
	public void setRight(Node right) {
		rightChild=right;
	}
	
	public void setParent(Node paren) {
		parent=paren;
	}
	
	public void setSymbol(String sym) {
		symbol=sym;
	}
	
	public void addToFirstPos(int number) {
        firstPos.add(number);
    }
    
	public void addAllToFirstPos(Set<Integer> set) {
        firstPos.addAll(set);
    }

    public void addToLastPos(int number) {
        lastPos.add(number);
    }
    
    public void addAllToLastPos(Set<Integer> set) {
        lastPos.addAll(set);
    }
    
    public boolean isNullable() {
    	return nullable;
    }
	
    public void setNullable() {
    	nullable=true;
    }
    
}
