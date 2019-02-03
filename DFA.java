package compilerDesign;
import compilerDesign.*;
import java.util.*;

public class DFA {
	private Set<DFAStates> states;
	private List<DFAStates> listStates;
	private Set<Character> alphabet;
	private SyntaxTree sTree;
	private char[] symbolSet;
	
	public DFA(String regex) {
		sTree=new SyntaxTree(regex);
		alphabet= new HashSet<Character>();
		states=new HashSet<DFAStates>();
		listStates=new ArrayList<DFAStates>();
		symbolSet=new char[26+26];
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
		this.genAlpha(regex);
		this.genTransit();
		this.genDFA();
	}
	
	public List<DFAStates> getStates(){
		return listStates;
	}
	
	public Set<Character> getAlpha(){
		return alphabet;
	}
	
	public void printTable() {
		for(DFAStates ds:listStates) {
			if(ds.isStart()) {
				System.out.print("->");
			}
			else if(ds.isFinal()) {
				System.out.print("F_");
			}
			System.out.print("Q"+ds.getNumber()+"("+ds.getName()+"):\r\n");
			for(char sym:alphabet) {
				if(ds.existsTransitions(sym)) {
					System.out.println("D(Q"+ds.getNumber()+","+sym+")= "+"Q"+ds.getTransitions(sym).getNumber()+"("+ds.getTransitions(sym).getName()+")");

				}
			}
		}
	}
	
	private void genAlpha(String str) {
		for(int i=0;i<str.length();i++) {
			if(isSymbol(str.charAt(i)))alphabet.add(str.charAt(i));
			else if(str.charAt(i)=='[') {
				i++;
				int tem=(int)str.charAt(i);
				i=i+2;
				int tem2=(int)str.charAt(i);
				while(tem<=tem2) {
					alphabet.add((char)tem);
					tem++;
				}
			}
		}
	}
	
	public void isValidString(String str) {
		int index=0,i,tempi;
		for( i=0;i<str.length();i++) {
			DFAStates temp=listStates.get(index).getTransitions(str.charAt(i));
			tempi=index;
			index=listStates.indexOf(temp);
			System.out.println("Q"+listStates.get(tempi).getNumber()+","+str.charAt(i)+" -> Q"+listStates.get(index).getNumber());
			if(i==str.length()-1&&listStates.get(index).isFinal()) {
				System.out.println("STRING ACCEPTED");return;
			}
			
		}
		System.out.println("STRING REJECTED");
	}
	
	private LeafNode getLeaf(int id) {
		for(LeafNode temp:sTree.getLeaves()) {
			if(temp.getNum()==id) return temp;
		}
		return null;
	}
	
	private boolean isSymbol(char ch) {
		
		for(int i=0;i<symbolSet.length;i++) {
			if(ch==symbolSet[i])return true;
		}
		return false;
	}
	
	private Set<Integer> nextStateName(Set<Integer> cur,char sym){
		Set<Integer> temp=new HashSet<Integer>();
		for(int i:cur) {
			if(this.getLeaf(i).getSymbol().charAt(0)==sym) temp.addAll(this.getLeaf(i).getFollowPos());
		}
		return temp;
	}
	
	/*private boolean hasNew() {
		for(DFAStates d:states) {
			if(!d.hasVisited()) return true;
		}
		return false;
	}
	
	private void genTransitions() {
		DFAStates start=new DFAStates(this.getLeaf(1).getFollowPos());
		start.setStart();
		start.visited();
		int i=0;
		listStates.add(start);
		for(char sym:alphabet) {
			Set<Integer> temp=this.nextStateName(start.getName(),sym);
			listStates.get(0).addTransition(new DFAStates(temp), sym);
			
			if(!temp.isEmpty()) {

				if(!listStates.contains(new DFAStates(temp))) {
					listStates.add(new DFAStates(temp));
					listStates.get(listStates.size()-1).setNumber(listStates.size()-1);
					i++;
					listStates.get(0).getTransitions(sym).setNumber(i);
				}
				else {
					int x=listStates.indexOf(new DFAStates(temp));
					listStates.get(0).getTransitions(sym).setNumber(x);
				}
			}
			else {
				listStates.add(new DFAStates(temp));
				listStates.get(listStates.size()-1).setNumber(listStates.size()-1);
				
				start.getTransitions(sym).setNumber(listStates.size()-1);
				List<Character> templ=new ArrayList<Character>();
				for(char s:alphabet) {
					if(listStates.get(0).existsTransitions(s)) {
					if(!listStates.get(0).getTransitions(s).getName().isEmpty()) {
						listStates.get(listStates.size()-1).addTransition(start.getTransitions(s), s);
						System.out.println("adding transition:"+start.getTransitions(s).getName()+" on "+s);
					}
					else templ.add(s);
					}
				}
				if(!templ.isEmpty()) {
					
					DFAStates xstart=new DFAStates();
					xstart.setNumber(listStates.size());
					for(char c:alphabet) {
						xstart.addTransition(xstart, c);
					}
					for(char ch:templ) {
						
						listStates.get(listStates.size()-1).addTransition(xstart, ch);
						
					}
					listStates.add(xstart);
				}
			}
			
		}
		listStates.get(0).setNumber(0);
		for(i=0;i<listStates.size();i++) {
			states.add(listStates.get(i));
		}
		i=0;
		
		int size=listStates.size();
		int j=size;
		while(i<size) {
			DFAStates ds=listStates.get(i);
			if(!ds.getName().isEmpty()) {
				if(!ds.hasVisited()) {
					for(char sym:alphabet) {
						Set<Integer> temp=this.nextStateName(ds.getName(), sym);
						ds.addTransition(new DFAStates(temp), sym);
						if(!temp.isEmpty()) {
							if(!listStates.contains(new DFAStates(temp))) {
								listStates.add(new DFAStates(temp));
								listStates.get(listStates.size()-1).setNumber(listStates.size()-1);
								j++;
								ds.getTransitions(sym).setNumber(listStates.size()-1);
							}
							else{
								int x=listStates.indexOf(new DFAStates(temp));
								ds.getTransitions(sym).setNumber(x);
							}
							
							states.add(listStates.get(listStates.size()-1));
						}
						else {
							listStates.add(new DFAStates(temp));
							listStates.get(listStates.size()-1).setNumber(listStates.size()-1);
							listStates.get(listStates.size()-1).visited();
							//j++;
							j=listStates.get(listStates.size()-2).getNumber();
							System.out.println("Q"+j);
							ds.getTransitions(sym).setNumber(listStates.size()-1);
							List<Character> templ=new ArrayList<Character>();
							for(char s:alphabet) {
								if(ds.existsTransitions(s)) {
									if(!ds.getTransitions(s).getName().isEmpty()) {
										listStates.get(listStates.indexOf(ds.getTransitions(sym))).addTransition(ds.getTransitions(s), s);;
										
									}
									else templ.add(s);
								}
								
							}
							if(!templ.isEmpty()) {
								//j++;
								
								DFAStates xds=new DFAStates();
								xds.setNumber(listStates.size());
								for(char c:alphabet) {
									xds.addTransition(xds, c);
								}
								for(char ch:templ) {
									
									//ds.getTransitions(sym).addTransition(xds, ch);
									listStates.get(listStates.size()-1).addTransition(xds, ch);
									System.out.println("adding transi"+" to Q"+listStates.get(listStates.size()-1).getNumber()+" "+xds.getName()+" on "+ch);
								}
								xds.visited();
								listStates.add(xds);
							}
							
							
						}
					}
					listStates.get(i).visited();
				}
				
			}
			
			i++;
			size=listStates.size();
				}
	}*/
	
	private void genTransit() {
		DFAStates start=new DFAStates(sTree.getRoot().getFirstPos());
		start.setStart();
		states.add(start);
		listStates.add(start);
		int i=0;
		int size=listStates.size();
		listStates.get(i).setNumber(i);
		while(i<size) {
			
			for(char sym:alphabet) {
				Set<Integer> temp=this.nextStateName(listStates.get(i).getName(), sym);
				DFAStates ds=new DFAStates(temp);
				if(!listStates.contains(ds)) {
					listStates.add(ds);
					listStates.get(listStates.size()-1).setNumber(listStates.size()-1);
					listStates.get(i).addTransition(ds, sym);
				}
				else {
					int in=listStates.indexOf(ds);
					ds.setNumber(in);
					listStates.get(i).addTransition(ds, sym);
				}
			}
			i++;
			size=listStates.size();
		}
		
		
	}
	
	private void genDFA() {
		int last=sTree.getLeaves().size();
		for(DFAStates ds:listStates) {
			if(ds.getName().contains(last)) ds.setFinal();
			int i=0;
			for(char sym:alphabet) {
				if(ds.getTransitions(sym).equals(ds))i++;
			}
			if(i==alphabet.size()&&!ds.isFinal()) ds.setTrap();
		}
	}
	
}
