package compilerDesign;
import compilerDesign.*;
import java.util.*;

public class DFAStates {
	private Set<Integer> stateName;
	private Map<Character,DFAStates> transition;
	private boolean isStartState=false;
	private boolean isFinalState=false;
	private boolean isVisited=false;
	private boolean isTrap=false;
	private int number;
	
	public DFAStates(Set<Integer> name) {
		stateName=new HashSet<Integer>();
		stateName=name;
		transition=new HashMap<Character,DFAStates>();
	}
	
	public DFAStates() {
		stateName=new HashSet<Integer>();
		transition=new HashMap<Character,DFAStates>();
	}
	
	public void setTrap() {
		isTrap=true;
	}
	
	public boolean isTrap() {
		return isTrap;
	}
	
	public boolean equals(Object o) {
		if(o==this) return true;
    	else if(!(o instanceof DFAStates)) return false;
    	else {
    		DFAStates temp=(DFAStates)o;
    		if(this.stateName.equals(temp.getName())) return true;
    	}
		return false;
	}
	
	public Set<Integer> getName(){
		return stateName;
	}
	
	public void addTransition(DFAStates dState,char sym) {
		transition.put(sym, dState);
	}
	
	public DFAStates getTransitions(char sym){
		DFAStates temp=transition.get(sym);
		return temp;
	}
	
	public boolean existsTransitions(char x) {
		if(transition.containsKey(x)) return true;
		else return false;
	}
	
	public boolean isStart() {
		return isStartState;
	}
	
	public boolean isFinal() {
		return isFinalState;
	}
	
	public void setStart() {
		isStartState=true;
	}
	
	public void setFinal() {
		isFinalState=true;
	}
	
	public void visited() {
		isVisited=true;
	}
	
	public boolean hasVisited() {
		return isVisited;
	}
	
	public void setNumber(int i) {
		number=i;
	}
	public int getNumber() {
		return number;
	}
}
