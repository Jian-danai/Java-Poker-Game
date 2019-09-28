import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Card extends JLabel implements MouseListener{//Card��̳���JLabel��ʵ����MouseListener�ӿ�.
	Main main;
	String name;//ͼƬ
	boolean up;//�Ƿ�������
	boolean canClick=true;//�Ƿ�ɱ����
	boolean clicked=false;//�Ƿ�����

	public Card(Main m,String name,boolean up){  // ������
		this.main=m;
		this.name=name;
		this.up=up;
	    if(this.up)
	    	this.TurnFront();
	    else {
			this.TurnRear();
		}
		this.setSize(71, 96);//��ʾCard�������
        this.setVisible(true);//Card���ӷ�
		this.addMouseListener(this);//��������
	}
	//���淽��
	public void TurnFront() {
		this.setIcon(new ImageIcon("src\\images/" + name + ".gif"));
		this.up = true;
	}
	//���淽��
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
			int step; //�ƶ��ľ���
			if(clicked){
				step=-20;
				if (from.y<430)TurnRear();//��������ҵ��ƣ�������
			}
			else {
				step=20;
				if (from.y<430)TurnFront();
			}
			clicked=!clicked; //����
			//����ѡ�е�ʱ����ǰ�����ˣ�һ��
			Mainfc.move(this,from,new Point(from.x,from.y-step));//����Mainfc���ϵ�move������
		}
	}
	public void mouseReleased(MouseEvent arg0) {}
}
