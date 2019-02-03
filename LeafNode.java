package compilerDesign;
import compilerDesign.Node;
import java.util.*;

public class LeafNode extends Node{
	private int num;
    private Set<Integer> followPos;

    public LeafNode(String symbol, int num) {
        super(symbol);
        this.num = num;
        followPos = new HashSet<>();
    }
    public boolean equals(Object o) {
    	if(o==this) return true;
    	else if(!(o instanceof LeafNode)) return false;
    	else {
    		LeafNode temp=(LeafNode)o;
    		return Integer.compare(this.getNum(), temp.getNum())==0;
    	}
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public void addToFollowPos(int number){
        followPos.add(number);
    }

    public Set<Integer> getFollowPos() {
        return followPos;
    }

    public void addAllToFollowPos(Set<Integer> followPos) {
        this.followPos.addAll(followPos);
    }
}
