package compilerDesign;
import compilerDesign.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class DFAGraphics extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<DFAStates> statelist=new ArrayList<DFAStates>();
	private int size;
	private int distances;
	private Set<Character> symbols;
	
	public DFAGraphics(DFA obj) {
		symbols=new HashSet<Character>();
		symbols.addAll(obj.getAlpha());
		statelist.addAll(obj.getStates());
		size=statelist.size();
		distances=(int)(1800/size);
		this.setSize(1700, 726);
		this.setTitle("DFA");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new DrawHandler(),BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	private class DrawHandler extends JComponent{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			Graphics2D gr2=(Graphics2D)g;
			/*Shape drawCirc=new Ellipse2D.Double(10, (int)(726/2)-50, 50, 50);
			Shape drawCirc2=new Ellipse2D.Double(200, (int)(726/2)-50, 50, 50);
			Shape drawCirc3=new Ellipse2D.Double(400, (int)(726/2)-50, 50, 50);
			Shape drawarc=new Arc2D.Float(35, (int)(726/2)-75, 190, 150,180, 180, Arc2D.OPEN);
			Shape drawarc2=new Arc2D.Float(35, (int)(726/2)-75, 390, 150,180, 180, Arc2D.OPEN);
			gr2.draw(drawCirc);
			gr2.draw(drawCirc2);
			gr2.draw(drawarc);
			gr2.draw(drawarc2);
			gr2.draw(drawCirc3);*/
			int i=0;
			for(DFAStates ds:statelist) {
				Shape circle=new Ellipse2D.Double(10+(i)*distances,(int)(726/2)-50,50,50);
				int sign=1;
				String str="";
				int de;
				Set<Integer> tempset=new HashSet<Integer>();
				boolean flag=true;
				for(char sym:symbols) {
					int x=ds.getTransitions(sym).getNumber();
					
					if(x==i) {
						if(flag) {
							Shape arc1=new Arc2D.Float(35+(i)*distances, (int)(726/2)-(60+25), 50, 65,180 , -270, Arc2D.OPEN);
							gr2.draw(arc1);
							flag=false;
						}
						str=str+" "+sym;
					}
					else{
						tempset.add(x-i);
						de=Math.abs(x-i);
						if(x-i<0) {
							sign=1;
							Shape arc=new Arc2D.Float(35+(i)*distances+(x-i)*distances, (int)(726/2)-(100-(sign)*25)-(sign)*20*de, Math.abs((x-i)*distances), 150+40*de,0 , -180, Arc2D.OPEN);
							gr2.draw(arc);
						}
						else if(i-x<0) {
							sign=-1;
							Shape arc=new Arc2D.Float(35+(i)*distances, (int)(726/2)-(100-(sign)*25)+(sign)*20*de, Math.abs((x-i)*distances), 150+40*de,0 , 180, Arc2D.OPEN);
							gr2.draw(arc);
						}
						
						
					}
					
					
				}
				for(int diff:tempset) {
					String str2="";
					int x1=1;
					de=Math.abs(diff);
					if(diff<0)x1=-1;
					for(char sym:symbols) {
						if(ds.getTransitions(sym).getNumber()==diff+i) {
							str2=str2+" "+sym;
						}
					}
					gr2.setFont(new Font("TimesRoman", Font.PLAIN, 25));
					gr2.drawString(str2, 35+i*distances+(int)(diff*distances/2),(int)(726/2)-20-(x1)*(120+de*20));
					if(diff<0) {
						gr2.drawString("<", 35+i*distances+(int)(diff*distances/2),(int)(726/2)-16-(x1)*(100+de*20));
					}
					else {
						gr2.drawString(">", 35+i*distances+(int)(diff*distances/2),(int)(726/2)-6-(x1)*(110+de*20));
					}
					gr2.setFont(new Font("TimesRoman", Font.PLAIN, 14));
				}
				gr2.setFont(new Font("TimesRoman", Font.PLAIN, 14));
				gr2.drawString("Q"+ds.getNumber(), 25+(i)*distances, (int)(726/2)-25);
				gr2.setFont(new Font("TimesRoman", Font.PLAIN, 25));
				gr2.drawString(str, 45+(i)*distances, (int)(726/2)-60);
				gr2.draw(circle);
				if(ds.isFinal()) {
					gr2.setColor(Color.BLUE);
					Shape circle2=new Ellipse2D.Double(5+(i)*distances,(int)(726/2)-55,60,60);
					gr2.draw(circle2);
					gr2.setColor(Color.BLACK);
				}
				if(ds.isTrap()) {
					gr2.setColor(Color.RED);
					Shape circle2=new Ellipse2D.Double(5+(i)*distances,(int)(726/2)-55,60,60);
					gr2.draw(circle2);
					gr2.setColor(Color.BLACK);
				}
				i++;
			}
			
		}
	}
}
