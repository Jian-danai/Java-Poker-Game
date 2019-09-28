import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;//����java.util(���߰�)����List�ӿ�
//import java.util.Timer;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;


public class Main extends JFrame implements ActionListener{//Main ��̳���JFrame��ʵ����ActionListener�ӿ�

	public Container container = null;// ���幫��Container�����container������ֵΪnull
	JMenuItem Start, Exit,Background, Rules,ContactUs,B1,B2,B3,B4,PB;// ����JMenuItem���Start,Exit,About,��Ϊ�˵���ť

	JButton publishCard[]=new JButton[5];//������ΪPublishCard[]�����飬��������������ΪJButton�������������Ԫ�ظ���Ϊ2����ʾ���ư�ť
	int SendCardsNum=13;//������������Ϊ���͵�ʵ������SendCardsNum������ֵΪ13
	int PlayerNum=4;//����ѡ��4λ
	
	List<Card> currentList[] = new ArrayList[13]; //������ΪcurrentList[]�����飬��������������List<�������ͣ��˴�ΪCard��>,������Ԫ�ظ���Ϊ13��,��ʾ��ǰ�ĳ���
	List<Card> ShowList[] = new ArrayList[4];    //������ΪShowList[]�����飬��������������List<�������ͣ��˴�ΪCard��>,������Ԫ�ظ���Ϊ4,��ʾ��Ҫ��ҳ����
	List<Card> playerList[] = new ArrayList[4]; // ������ΪplayerList[]�����飬��������������List<�������ͣ��˴�ΪCard��>,������Ԫ�ظ���Ϊ4,��ʾ4�����
	List<Card> AllCards=new ArrayList<Card> (); //������ΪAllCards��ʵ������������ΪList<�������ͣ��˴�ΪCard��>,����ArrayList<Card>,new�ؼ��ֶ�����ʼ������ʾ���е��ơ�
	Card card[] = new Card[54]; // ������Ϊcard[]�����飬��������������ΪCard��,������Ԫ�ظ���Ϊ54,��ʾ����54����
	JLabel PlayerName;
	JLabel timelabel;
	Card othercard[] = new Card[3];
	List<Card> OtherCards=new ArrayList<Card> ();
	
	public Main(int i){//�޷���ֵ�Ĺ��캯��
		PlayerName();//�������
		ChoiceCardNum();//ѡ�˿�������
		Init(i);// ��ʼ��
		SetMenu();//�����˵�
	    this.setVisible(true);//�ɼ�		
	    getTimelabel();//ʱ����ʾ
		CardInit();//����
		PlayCards(); //����
	}
	
	public void PlayerName(){//����ǳ�
		String In = JOptionPane.showInputDialog("����������ǳ�","�򵥰�");
		PlayerName=new JLabel(In);
	}
	public Timer time;
	 
    //ʱ����ʾ
	public JLabel getTimelabel() {
		if (timelabel == null) {
			timelabel = new JLabel("");
			timelabel.setBounds(5, 5, 200, 20);
			timelabel.setFont(new Font("΢���ź�", Font.BOLD, 12));
			timelabel.setForeground(new Color(182, 229, 248));
			container.add(timelabel);
			timelabel.setVisible(true);
			time = new Timer(1000,new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					timelabel.setText(new SimpleDateFormat("yyyy��MM��dd�� EEEE hh:mm:ss").format(new Date()));
				}
			});
			time.start();
		}
		return timelabel;
	}
	public void ChoiceCardNum(){//����
		//����һ���ַ��������ʵ������In�����ҵ�����JOptionPane����ϵ�showInputDialog(����������ʾ��һ�����еĶԻ����з����û�������ַ�������������ֵ
		String In = JOptionPane.showInputDialog("������ÿλѡ�ֵ�������","13");
		SendCardsNum=Integer.parseInt(In);
		while(SendCardsNum<=0 || SendCardsNum >13){
			//����һ���ַ��ܶ����ʵ������ReIn�����ҵ�����JOptionPane����ϵ�showInputDialog(����������ʾ��һ�����еĶԻ����з����û�������ַ�������������ֵ
			String ReIn = JOptionPane.showInputDialog("�����д�����ѡ���������?","smile");
			SendCardsNum=Integer.parseInt(ReIn);//���û�����������ַ���ת�������ͣ����Ҹ�ֵ��SendCardsNum
		} 
	}
	//��ʼ���ƣ�����ϴ��
	public void CardInit() {//�������޷���ֵ�Ķ��󷽷�CardInit
		int count = 1;//��������ʵ������count�����Ҹ�ֵΪ1
		//��ʼ����
		int CardsNum=SendCardsNum*4;
		//othercard[0] = new Card(this , "win" , true);
		for (int i = 1; i <= 4 && count<=CardsNum ; i++) {
			for (int j = 1; j <= 13 && count<=CardsNum; j++) {
			    card[count] = new Card(this, i + "-" + j, false);
				card[count].setLocation(350, 200);
				container.add(card[count]);
				count++;
				}
		}
		//ϴ��
		for(int i=1;i<=300;i++){ //ѭ��������ֵΪ0����ѭ������С��300ʱ����ѭ����ѭ������ÿ�μ�һ
			Random random=new Random();//����Random���͵ı���random���ҳ�ʼ��			
			int random1 = (int) (Math.random() * SendCardsNum*4)+1;//������������Ϊ���͵�ʵ������random1��������Math���е������������һ����ֵ
			int random2 = (int) (Math.random() * SendCardsNum*4)+1;//������������Ϊ���͵�ʵ������random2��������Math���е������������һ����ֵ
			Card Temp=card[random1];//����Card���ʵ������Temp
			card[random1]=card[random2];
			card[random2]=Temp;
		}
		//����
		for(int i=0;i<4;i++){
			playerList[i]=new ArrayList<Card>(); //ÿ����ҵ���
			AllCards = new ArrayList<Card>();    //����������
		}
		
		for(int i=0;i<SendCardsNum*4;){
			switch ((i++)%4) {        //ע�����i++���ܷ���ѭ��������
			case 0://0:������
				Mainfc.move(card[i], card[i].getLocation(),new Point(50,60+i*5));
				playerList[0].add(card[i]);
				AllCards.add(card[i]);
				break;
			case 1://1�� �Լ�
				Mainfc.move(card[i], card[i].getLocation(),new Point(180+i*7,450));
				playerList[1].add(card[i]);
				AllCards.add(card[i]);
				card[i].TurnFront(); //��ʾ����
				break;
			case 2://2�� ���ұ�
				Mainfc.move(card[i], card[i].getLocation(),new Point(700,60+i*5));				
				playerList[2].add(card[i]);
				AllCards.add(card[i]);
				break;
			case 3://3�ţ��ϱ�
				Mainfc.move(card[i], card[i].getLocation(),new Point(180+i*5,30));
				playerList[3].add(card[i]);
				AllCards.add(card[i]);
				break;
			}
		}
		//����
		for(int i=0;i<4;i++){//ȫ��������ض�λ���ص�����
			Mainfc.order(playerList[i]); // ����
			Mainfc.rePosition(this,playerList[i],i);//���¶�λ
		}
	}

	public void Init(int i) {	// ��ʼ������
		SetsBack(i);//���ñ���ͼƬ
		this.setTitle("MyPoker");
		this.setSize(830, 620);
		setResizable(false);
		setLocationRelativeTo(getOwner()); // ��Ļ����
		container = this.getContentPane();
		container.add(PlayerName);
		container.setLayout(null);
		//getTimelabel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رմ��ں�������
	}
	/*public void Win(int i) {	// ��ʼ������
		((JPanel)this.getContentPane()).setOpaque(false);
		   ImageIcon img = new ImageIcon("src/images/win"+i+".gif"); 
		   JLabel background = new JLabel(img);this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE)); 
		   background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); //���ñ���ͼƬ
		this.setTitle("Winner");
		this.setSize(830, 620);
		setResizable(false);
		setLocationRelativeTo(getOwner()); // ��Ļ����
		container = this.getContentPane();
		container.add(PlayerName);
		container.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رմ��ں�������
	}*/
	public void TurnOn(boolean flag) {//���ư�ť���ӻ�
		publishCard[0].setVisible(flag);
		publishCard[1].setVisible(flag);
		publishCard[2].setVisible(flag);
		publishCard[3].setVisible(flag);
		publishCard[4].setVisible(flag);
	}
	public void SetMenu() {// �����˵� ���ܰ�ť
		JMenuBar jMenuBar = new JMenuBar();
		JMenu Game = new JMenu("GAME");
		JMenu Help = new JMenu("HELP");
		JMenu Background = new JMenu("BACKGROUND");
	
		Start = new JMenuItem("New Game");
		Exit = new JMenuItem("Exit");
		Rules = new JMenuItem("Rules");
		ContactUs = new JMenuItem("Contact us");
		B1 = new JMenuItem("B1");
		B2 = new JMenuItem("B2");
		B3 = new JMenuItem("B3");
		B4 = new JMenuItem("B4");
		PB = new JMenuItem("Poker-Back");
		
		Start.addActionListener(this);//���˵�������������
		Exit.addActionListener(this);
		Background.addActionListener(this);
		Rules.addActionListener(this);
		ContactUs.addActionListener(this);
		B1.addActionListener(this);
		B2.addActionListener(this);
		B3.addActionListener(this);
		B4.addActionListener(this);
		PB.addActionListener(this);
		
		Game.add(Start);//���������˵�
		Game.add(Exit);
		Background.add(B1);
		Background.add(B2);
		Background.add(B3);
		Background.add(B4);
		Background.add(PB);
		Help.add(Rules);
		Help.add(ContactUs);
		
		jMenuBar.add(Game);//�Ӳ˵���GUI
		jMenuBar.add(Background);
		jMenuBar.add(Help);
		this.setJMenuBar(jMenuBar);
		
		 publishCard[0]= new JButton("����");//���Ӱ�ť���趨λ��
		 publishCard[0].setBounds(250, 400, 60, 20);
		 container.add(publishCard[0]);
		 publishCard[0].setVisible(false);//���ư�ťһ��ʼ�����ӣ������ƺ����
		 publishCard[0].addActionListener(this);//������������
		 
		 publishCard[1]= new JButton("����");
		 publishCard[1].setBounds(310, 400, 60, 20);
		 container.add(publishCard[1]);
		 publishCard[1].setVisible(false);
		 publishCard[1].addActionListener(this);
		 
		 publishCard[2]= new JButton("����2");
		 publishCard[2].setBounds(370, 400, 70, 20);
		 container.add(publishCard[2]);
		 publishCard[2].setVisible(false);
		 publishCard[2].addActionListener(this);
		 
		 publishCard[3]= new JButton("����3");
		 publishCard[3].setBounds(430, 400, 70, 20);
		 container.add(publishCard[3]);
		 publishCard[3].setVisible(false);
		 publishCard[3].addActionListener(this);
		 
		 publishCard[4]= new JButton("����4");
		 publishCard[4].setBounds(490, 400, 70, 20);
		 container.add(publishCard[4]);
		 publishCard[4].setVisible(false);
		 publishCard[4].addActionListener(this);
	
		for(int i=0;i<4;i++){
			currentList[i]=new ArrayList<Card>();//����4��lsit��ÿһ��list�����Ӧ����Ѿ�����ȥ����
		}
	}
	
	public void SetsBack(int i){   //���ñ���ͼƬ
		  ((JPanel)this.getContentPane()).setOpaque(false);
		   ImageIcon img = new ImageIcon("src/images/bg"+i+".jpg"); 
		   JLabel background = new JLabel(img);
		   this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE)); 
		   background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); //������С
		  } 

	public void PlayCards() {   //����
		TurnOn(true);	
	}
	int turnto=0;//����turnto����������ʶ�����λ��ҳ�����
	Card card1 = null;//card1��Ϊ�м������������һЩ��ʱ���ֵ�Card���ͱ���
	List<Card> PCards=new ArrayList<Card>();//
	int x=0;
	
	public void actionPerformed(ActionEvent e) {
		int lenofpc = 0;//��Ȿ������Ƿ����Ƴ�
		//int back=0;
		if (e.getSource() == ContactUs) {//�������򵯳��Ի���
			JOptionPane.showMessageDialog(this, "yangbj@zju.edu.cn");
		}
		if (e.getSource() == Exit) {//��������رյ�ǰ�Ի���
			this.dispose();
		}		
		if (e.getSource() == Start) {//��������رյ�ǰ�Ի��򣬲���������
			this.dispose();
			Main lain=new Main(0);
		}
		if (e.getSource() == B1) {//��������رյ�ǰ�Ի��򣬲��������򣬸�������
			this.dispose();
			Main lain=new Main(0);
		}
		if (e.getSource() == B2) {
			this.dispose();
			Main lain=new Main(1);
		}
		if (e.getSource() == B3) {
			this.dispose();
			Main lain=new Main(2);
		}
		if (e.getSource() == B4) {
			this.dispose();
			Main lain=new Main(3);
		}
		if (e.getSource() == PB) {
			this.dispose();
			Main lain=new Main(3);//2
		}
		if (e.getSource() == Rules) {
			JOptionPane.showMessageDialog(this, "��Ϸ���ܣ�1.���Ի����������backgroundѡ�񱳾���" +
					"\n 2.������5����ť�������ơ�" +
					"��һ������Ҫ�ȵ���һ���ƣ��ٰ������ơ���\n����2��3��4�����2��3��4���ƣ�\n3.��Ϸ����:" +
					"3��С��2��󣬵�3����2�����������һ���ƴ�1���ơ�\n ����ֵ�ĳ����û�Ƴ������������x��ʱ���ᵯ�������xû�Ƴ�����" +
					"�����ͼ��������һ����ҵġ�����x����ť�ͺá�\n������Լ�û�Ƴ���ֱ�ӵ�������ơ��������ѡһ�����ٵ�" +
					"�������ơ����ɣ�ͬ��������û�Ƴ���\n" +
					"4. ���İ�ť������������ҷ��ģ� ���������ҵ��ƣ�����ʵ�ֵ���\n" +
					"5. ʱ����ʾ����");
		}
		if(e.getSource()==publishCard[0] && turnto == 0){//��������1�����ҵ���ˡ����ơ���ť
			for(int i=0;i<playerList[1].size();i++){  //�����1ʣ�µ�����Ѱ��
				card1=playerList[1].get(i);
				if(card1.clicked && PCards.isEmpty()==true){//������Ʊ�ѡ����֮ǰ��û���Ƴ���ȥ
					PCards.clear();//����ѳ��Ƶļ�¼
					PCards.add(card1);//��Ӹ���
					lenofpc++;//����ұ������Ƴ�
					break;//����forѭ��
				}
				else if(PCards.isEmpty()==false){//������Ʊ�ѡ����֮ǰ���Ƴ���ȥ
					if(strtoint(card1.name)==strtoint(PCards.get(0).name)+1
							||strtoint(card1.name)==1&&strtoint(PCards.get(0).name)==13){
						//������1ʣ�µ������б���һ����ҳ����ƴ�1  ����  �Է�����K�����1��K
						PCards.clear();
						PCards.add(card1);
						lenofpc++;
						break;
					}
				}
				else{
					;
				}
			}				
			if (lenofpc==1){//������γ�����	
				currentList[1]=PCards;//��ǰ��ҳ����Ƶ��Ŷ�����PCards
				playerList[1].removeAll(currentList[1]);//�����ʣ�������Ƴ�������
				//��λ����
				Point point=new Point();
				point.x=(770/2)-(currentList[1].size()+1)*15/2;
				point.y=300;
				for(int i=0,len=currentList[1].size();i<len;i++){
					card1=currentList[1].get(i);
					Mainfc.move(card1, card1.getLocation(), point);//���ƶ���
					point.x+=15;
				}
				//��������Ժ�����������
				Mainfc.rePosition(this, playerList[1], 1);//��ʣ�µ����ض�λ
				if(playerList[1].size()==0){//���û���ˣ���Ӯ��
					//System.out.println("���1Ӯ�ˣ�");
					JOptionPane.showMessageDialog(this, "���1Ӯ�ˣ�");
				}
				turnto ++;//turnto��1������һ����ҳ���
			}
			else {//���û���Ƴ�
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "���1û�Ƴ���");
					x=0;
					turnto++;//turnto��1������һ����ҳ���
				}
			}
		}
		if(e.getSource()==publishCard[2] && turnto == 1)//���2
		{
			for(int i=0;i<playerList[2].size();i++){  
				card1=playerList[2].get(i);
				if(strtoint(card1.name)==strtoint(PCards.get(0).name)+1||strtoint(card1.name)==1&&strtoint(PCards.get(0).name)==13){
					PCards.clear();
					PCards.add(card1);
					lenofpc++;
					break;
				}
			}
			if (lenofpc==1){
				currentList[2]=PCards;
				playerList[2].removeAll(currentList[2]);//�Ƴ��ߵ���
				if(playerList[2].size()==0){
					//System.out.println("���2Ӯ�ˣ�");
					JOptionPane.showMessageDialog(this, "���2Ӯ�ˣ�");
					//Win(2);
				}
				//��λ����
				Point point=new Point();
				point.y = 220;
				point.x = 600;
				card1=currentList[2].get(0);
				Mainfc.move(card1, card1.getLocation(), point);
				//Mainfc.move(othercard[0], othercard[0].getLocation(), point);
				card1.TurnFront();
				Mainfc.rePosition(this, playerList[2], 2);
				turnto ++;
			}
			else {
				//System.out.println("���2û�Ƴ�");
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "���2û�Ƴ���");
					x=0;
					turnto++;
				}
			}
		}
		if(e.getSource()==publishCard[3] && turnto==2)//���3
		{
			for(int i=0;i<playerList[3].size();i++){  
				card1=playerList[3].get(i);
				if(strtoint(card1.name)==strtoint(PCards.get(0).name)+1||strtoint(card1.name)==1&&strtoint(PCards.get(0).name)==13){
					PCards.clear();
					PCards.add(card1);
					lenofpc++;
					break;
				}
			}
			if (lenofpc==1){
				currentList[3]=PCards;
				playerList[3].removeAll(currentList[3]);//�Ƴ��ߵ���
				if(playerList[3].size()==0){
					JOptionPane.showMessageDialog(this, "���3Ӯ�ˣ�");
					//System.out.println("���3Ӯ�ˣ�");
					//Win(3);
				}
				//��λ����
				Point point=new Point();
				point.x=361;
				point.y=130;
				card1=currentList[3].get(0);
				Mainfc.move(card1, card1.getLocation(), point);
				card1.TurnFront();
				//��������Ժ�����������
				Mainfc.rePosition(this, playerList[3], 3);
				turnto ++;
			}
			else {
				//System.out.println("���3û�Ƴ���");
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "���3û�Ƴ���");
					x=0;
					turnto++;
				}
			}
		}
		if(e.getSource()==publishCard[4] && turnto==3)//���4
		{
			for(int i=0;i<playerList[0].size();i++){  
				card1=playerList[0].get(i);
				if(strtoint(card1.name)==strtoint(PCards.get(0).name)+1||strtoint(card1.name)==1&&strtoint(PCards.get(0).name)==13){
					PCards.clear();
					PCards.add(card1);
					lenofpc++;
					break;
				}
			}

			if (lenofpc==1){
				currentList[0]=PCards;
				playerList[0].removeAll(currentList[0]);//�Ƴ��ߵ���
				if(playerList[0].size()==0){
					JOptionPane.showMessageDialog(this, "���4Ӯ�ˣ�");
					//System.out.println("���4Ӯ�ˣ�");
					//Win(4);
				}
				//��λ����
				Point point=new Point();
				point.x=150;
				point.y=200;
				card1=currentList[0].get(0);
				Mainfc.move(card1, card1.getLocation(), point);
				card1.TurnFront();
				//��������Ժ�����������
				Mainfc.rePosition(this, playerList[0], 0);
				turnto=0;
			}
			else {
				//System.out.println("���4û�Ƴ�");
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "���4û�Ƴ���");
					x=0;
					turnto=0;
				}
			}
		}

		if(e.getSource()==publishCard[1]){
			List<Card> CardName=new ArrayList<Card> ();
			for(int i=0;i<4;i++){
				for(int j=0;j<playerList[i].size();j++){
					if(i!=1){
						card1=playerList[i].get(j);
						CardName.add(card1);
					}
				}
			}
			ShowList[0]=CardName; //���浱ǰҪ������
			for(int i=0,len=ShowList[0].size();i<len;i++){
				card1=ShowList[0].get(i);
				if(!card1.up)
					card1.TurnFront();
				else
					card1.TurnRear();
			}
		}
	}
	public static int strtoint(String x){//���һ�����еı��
		if (x.length()==3)return x.charAt(x.length()-1)-'0';
		else return x.charAt(x.length()-1)-'0'+10*(x.charAt(x.length()-2)-'0');
	}
	public static void main(String args[]) {
		Main lain=new Main(0);
	}
}