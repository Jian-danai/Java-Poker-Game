import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Card extends JLabel implements MouseListener{//Card类继承了JLabel类实现了MouseListener接口.
	Main main;
	String name;//图片
	boolean up;//是否正反面
	boolean canClick=true;//是否可被点击
	boolean clicked=false;//是否点击过

	public Card(Main m,String name,boolean up){  // 构造器
		this.main=m;
		this.name=name;
		this.up=up;
	    if(this.up)
	    	this.TurnFront();
	    else {
			this.TurnRear();
		}
		this.setSize(71, 96);//表示Card组件长宽
        this.setVisible(true);//Card可视否
		this.addMouseListener(this);//鼠标监听器
	}
	//正面方法
	public void TurnFront() {
		this.setIcon(new ImageIcon("src\\images/" + name + ".gif"));
		this.up = true;
	}
	//反面方法
	public void TurnRear() {
		this.setIcon(new ImageIcon("src\\images/back"+"192.gif"));
		this.up = false;
	}
	/*public void TurnRear2() {
		this.setIcon(new ImageIcon("src\\images/back"+"191.gif"));
		this.up = false;
	}*/
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent e) {
		if(canClick){
			Point from=this.getLocation();
			int step; //移动的距离
			if(clicked){
				step=-20;
				if (from.y<430)TurnRear();//翻其他玩家的牌（单翻）
			}
			else {
				step=20;
				if (from.y<430)TurnFront();
			}
			clicked=!clicked; //反向
			//当被选中的时候，向前（后退）一步
			Mainfc.move(this,from,new Point(from.x,from.y-step));//调用Mainfc包上的move方法。
		}
	}
	public void mouseReleased(MouseEvent arg0) {}
}
