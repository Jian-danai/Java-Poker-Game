import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;//导入java.util(工具包)包中List接口
//import java.util.Timer;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;


public class Main extends JFrame implements ActionListener{//Main 类继承了JFrame类实现了ActionListener接口

	public Container container = null;// 定义公有Container类变量container，变量值为null
	JMenuItem Start, Exit,Background, Rules,ContactUs,B1,B2,B3,B4,PB;// 定义JMenuItem组件Start,Exit,About,作为菜单按钮

	JButton publishCard[]=new JButton[5];//定义名为PublishCard[]的数组，数组中数据类型为JButton类组件，数组中元素个数为2，表示出牌按钮
	int SendCardsNum=13;//定义数据类型为整型的实例变量SendCardsNum，并赋值为13
	int PlayerNum=4;//定义选手4位
	
	List<Card> currentList[] = new ArrayList[13]; //定义名为currentList[]的数组，数组中数据类型List<数据类型，此处为Card型>,数组中元素个数为13，,表示当前的出牌
	List<Card> ShowList[] = new ArrayList[4];    //定义名为ShowList[]的数组，数组中数据类型List<数据类型，此处为Card型>,数组中元素个数为4,表示将要翻页的牌
	List<Card> playerList[] = new ArrayList[4]; // 定义名为playerList[]的数组，数组中数据类型List<数据类型，此处为Card型>,数组中元素个数为4,表示4个玩家
	List<Card> AllCards=new ArrayList<Card> (); //定义名为AllCards的实例变量，类型为List<数据类型，此处为Card型>,并用ArrayList<Card>,new关键字对它初始化，表示所有的牌。
	Card card[] = new Card[54]; // 定义名为card[]的数组，数组中数据类型为Card型,数组中元素个数为54,表示定义54张牌
	JLabel PlayerName;
	JLabel timelabel;
	Card othercard[] = new Card[3];
	List<Card> OtherCards=new ArrayList<Card> ();
	
	public Main(int i){//无返回值的构造函数
		PlayerName();//玩家姓名
		ChoiceCardNum();//选扑克牌张数
		Init(i);// 初始化
		SetMenu();//创建菜单
	    this.setVisible(true);//可见		
	    getTimelabel();//时间显示
		CardInit();//发牌
		PlayCards(); //出牌
	}
	
	public void PlayerName(){//玩家昵称
		String In = JOptionPane.showInputDialog("请输入玩家昵称","简单爱");
		PlayerName=new JLabel(In);
	}
	public Timer time;
	 
    //时间显示
	public JLabel getTimelabel() {
		if (timelabel == null) {
			timelabel = new JLabel("");
			timelabel.setBounds(5, 5, 200, 20);
			timelabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
			timelabel.setForeground(new Color(182, 229, 248));
			container.add(timelabel);
			timelabel.setVisible(true);
			time = new Timer(1000,new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					timelabel.setText(new SimpleDateFormat("yyyy年MM月dd日 EEEE hh:mm:ss").format(new Date()));
				}
			});
			time.start();
		}
		return timelabel;
	}
	public void ChoiceCardNum(){//牌数
		//定义一个字符串对象的实例变量In，并且调用了JOptionPane组件上的showInputDialog(）方法，表示在一个居中的对话框中返回用户输入的字符串，来给它赋值
		String In = JOptionPane.showInputDialog("请输入每位选手的牌数：","13");
		SendCardsNum=Integer.parseInt(In);
		while(SendCardsNum<=0 || SendCardsNum >13){
			//定义一个字符窜对象的实例变量ReIn，并且调用了JOptionPane组件上的showInputDialog(）方法，表示在一个居中的对话框中返回用户输入的字符串，来给它赋值
			String ReIn = JOptionPane.showInputDialog("输入有错，重新选择玩家牌数?","smile");
			SendCardsNum=Integer.parseInt(ReIn);//将用户重新输入的字符窜转换成整型，并且赋值给SendCardsNum
		} 
	}
	//初始化牌，发牌洗牌
	public void CardInit() {//公共的无返回值的对象方法CardInit
		int count = 1;//定义整型实例变量count，并且赋值为1
		//初始化牌
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
		//洗牌
		for(int i=1;i<=300;i++){ //循环变量初值为0，当循环变量小于300时继续循环，循环次数每次加一
			Random random=new Random();//定义Random类型的变量random并且初始化			
			int random1 = (int) (Math.random() * SendCardsNum*4)+1;//定义数据类型为整型的实例变量random1，并且用Math类中的随机数方法加一来赋值
			int random2 = (int) (Math.random() * SendCardsNum*4)+1;//定义数据类型为整型的实例变量random2，并且用Math类中的随机数方法加一来赋值
			Card Temp=card[random1];//定义Card类的实例变量Temp
			card[random1]=card[random2];
			card[random2]=Temp;
		}
		//发牌
		for(int i=0;i<4;i++){
			playerList[i]=new ArrayList<Card>(); //每个玩家的牌
			AllCards = new ArrayList<Card>();    //保存所有牌
		}
		
		for(int i=0;i<SendCardsNum*4;){
			switch ((i++)%4) {        //注意这个i++不能放在循环条件里
			case 0://0:左边玩家
				Mainfc.move(card[i], card[i].getLocation(),new Point(50,60+i*5));
				playerList[0].add(card[i]);
				AllCards.add(card[i]);
				break;
			case 1://1号 自己
				Mainfc.move(card[i], card[i].getLocation(),new Point(180+i*7,450));
				playerList[1].add(card[i]);
				AllCards.add(card[i]);
				card[i].TurnFront(); //显示正面
				break;
			case 2://2号 ：右边
				Mainfc.move(card[i], card[i].getLocation(),new Point(700,60+i*5));				
				playerList[2].add(card[i]);
				AllCards.add(card[i]);
				break;
			case 3://3号：上边
				Mainfc.move(card[i], card[i].getLocation(),new Point(180+i*5,30));
				playerList[3].add(card[i]);
				AllCards.add(card[i]);
				break;
			}
		}
		//排序
		for(int i=0;i<4;i++){//全部排序和重定位，重叠放置
			Mainfc.order(playerList[i]); // 排序
			Mainfc.rePosition(this,playerList[i],i);//重新定位
		}
	}

	public void Init(int i) {	// 初始化窗体
		SetsBack(i);//设置背景图片
		this.setTitle("MyPoker");
		this.setSize(830, 620);
		setResizable(false);
		setLocationRelativeTo(getOwner()); // 屏幕居中
		container = this.getContentPane();
		container.add(PlayerName);
		container.setLayout(null);
		//getTimelabel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口后程序结束
	}
	/*public void Win(int i) {	// 初始化窗体
		((JPanel)this.getContentPane()).setOpaque(false);
		   ImageIcon img = new ImageIcon("src/images/win"+i+".gif"); 
		   JLabel background = new JLabel(img);this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE)); 
		   background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); //设置背景图片
		this.setTitle("Winner");
		this.setSize(830, 620);
		setResizable(false);
		setLocationRelativeTo(getOwner()); // 屏幕居中
		container = this.getContentPane();
		container.add(PlayerName);
		container.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口后程序结束
	}*/
	public void TurnOn(boolean flag) {//出牌按钮可视化
		publishCard[0].setVisible(flag);
		publishCard[1].setVisible(flag);
		publishCard[2].setVisible(flag);
		publishCard[3].setVisible(flag);
		publishCard[4].setVisible(flag);
	}
	public void SetMenu() {// 创建菜单 功能按钮
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
		
		Start.addActionListener(this);//给菜单增加鼠标监听器
		Exit.addActionListener(this);
		Background.addActionListener(this);
		Rules.addActionListener(this);
		ContactUs.addActionListener(this);
		B1.addActionListener(this);
		B2.addActionListener(this);
		B3.addActionListener(this);
		B4.addActionListener(this);
		PB.addActionListener(this);
		
		Game.add(Start);//增加下拉菜单
		Game.add(Exit);
		Background.add(B1);
		Background.add(B2);
		Background.add(B3);
		Background.add(B4);
		Background.add(PB);
		Help.add(Rules);
		Help.add(ContactUs);
		
		jMenuBar.add(Game);//加菜单入GUI
		jMenuBar.add(Background);
		jMenuBar.add(Help);
		this.setJMenuBar(jMenuBar);
		
		 publishCard[0]= new JButton("出牌");//增加按钮，设定位置
		 publishCard[0].setBounds(250, 400, 60, 20);
		 container.add(publishCard[0]);
		 publishCard[0].setVisible(false);//出牌按钮一开始不可视，发完牌后可视
		 publishCard[0].addActionListener(this);//增加鼠标监听器
		 
		 publishCard[1]= new JButton("翻牌");
		 publishCard[1].setBounds(310, 400, 60, 20);
		 container.add(publishCard[1]);
		 publishCard[1].setVisible(false);
		 publishCard[1].addActionListener(this);
		 
		 publishCard[2]= new JButton("出牌2");
		 publishCard[2].setBounds(370, 400, 70, 20);
		 container.add(publishCard[2]);
		 publishCard[2].setVisible(false);
		 publishCard[2].addActionListener(this);
		 
		 publishCard[3]= new JButton("出牌3");
		 publishCard[3].setBounds(430, 400, 70, 20);
		 container.add(publishCard[3]);
		 publishCard[3].setVisible(false);
		 publishCard[3].addActionListener(this);
		 
		 publishCard[4]= new JButton("出牌4");
		 publishCard[4].setBounds(490, 400, 70, 20);
		 container.add(publishCard[4]);
		 publishCard[4].setVisible(false);
		 publishCard[4].addActionListener(this);
	
		for(int i=0;i<4;i++){
			currentList[i]=new ArrayList<Card>();//定义4个lsit，每一个list存放相应玩家已经出出去的牌
		}
	}
	
	public void SetsBack(int i){   //设置背景图片
		  ((JPanel)this.getContentPane()).setOpaque(false);
		   ImageIcon img = new ImageIcon("src/images/bg"+i+".jpg"); 
		   JLabel background = new JLabel(img);
		   this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE)); 
		   background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); //背景大小
		  } 

	public void PlayCards() {   //发牌
		TurnOn(true);	
	}
	int turnto=0;//定义turnto变量，用来识别该哪位玩家出牌了
	Card card1 = null;//card1作为中间变量，用来存一些暂时出现的Card类型变量
	List<Card> PCards=new ArrayList<Card>();//
	int x=0;
	
	public void actionPerformed(ActionEvent e) {
		int lenofpc = 0;//检测本次玩家是否有牌出
		//int back=0;
		if (e.getSource() == ContactUs) {//如果点击则弹出对话框
			JOptionPane.showMessageDialog(this, "yangbj@zju.edu.cn");
		}
		if (e.getSource() == Exit) {//如果点击则关闭当前对话框
			this.dispose();
		}		
		if (e.getSource() == Start) {//如果点击则关闭当前对话框，并重启程序
			this.dispose();
			Main lain=new Main(0);
		}
		if (e.getSource() == B1) {//如果点击则关闭当前对话框，并重启程序，更换背景
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
			JOptionPane.showMessageDialog(this, "游戏功能：1.可以换背景（点击background选择背景；" +
					"\n 2.下面有5个按钮，“出牌”" +
					"第一次是需要先点起一张牌，再按“出牌”；\n出牌2、3、4是玩家2、3、4出牌；\n3.游戏规则:" +
					"3最小，2最大，但3大于2；必须跟比上一个牌大1的牌。\n 如果轮到某个人没牌出，点击“出牌x”时，会弹出“玩家x没牌出”，" +
					"这样就继续点击下一个玩家的“出牌x”按钮就好。\n如果我自己没牌出，直接点击“出牌”或者随便选一张牌再点" +
					"击“出牌”都可，同样弹出“没牌出”\n" +
					"4. 翻拍按钮对其他所有玩家翻拍， 点击其他玩家的牌，可以实现单翻\n" +
					"5. 时间显示功能");
		}
		if(e.getSource()==publishCard[0] && turnto == 0){//如果该玩家1出牌且点击了“出牌“按钮
			for(int i=0;i<playerList[1].size();i++){  //在玩家1剩下的牌中寻找
				card1=playerList[1].get(i);
				if(card1.clicked && PCards.isEmpty()==true){//如果该牌被选中且之前还没有牌出出去
					PCards.clear();//清空已出牌的记录
					PCards.add(card1);//添加该牌
					lenofpc++;//改玩家本次有牌出
					break;//跳出for循环
				}
				else if(PCards.isEmpty()==false){//如果该牌被选中且之前有牌出出去
					if(strtoint(card1.name)==strtoint(PCards.get(0).name)+1
							||strtoint(card1.name)==1&&strtoint(PCards.get(0).name)==13){
						//如果玩家1剩下的牌中有比上一个玩家出的牌大1  或者  对方出的K，玩家1有K
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
			if (lenofpc==1){//如果本次出牌了	
				currentList[1]=PCards;//当前玩家出的牌的排堆增加PCards
				playerList[1].removeAll(currentList[1]);//该玩家剩余牌中移除出的牌
				//定位出牌
				Point point=new Point();
				point.x=(770/2)-(currentList[1].size()+1)*15/2;
				point.y=300;
				for(int i=0,len=currentList[1].size();i<len;i++){
					card1=currentList[1].get(i);
					Mainfc.move(card1, card1.getLocation(), point);//出牌动作
					point.x+=15;
				}
				//出完结束以后，重新整理牌
				Mainfc.rePosition(this, playerList[1], 1);//还剩下的牌重定位
				if(playerList[1].size()==0){//如果没牌了，就赢了
					//System.out.println("玩家1赢了！");
					JOptionPane.showMessageDialog(this, "玩家1赢了！");
				}
				turnto ++;//turnto加1，该下一个玩家出牌
			}
			else {//如果没有牌出
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "玩家1没牌出！");
					x=0;
					turnto++;//turnto加1，该下一个玩家出牌
				}
			}
		}
		if(e.getSource()==publishCard[2] && turnto == 1)//玩家2
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
				playerList[2].removeAll(currentList[2]);//移除走的牌
				if(playerList[2].size()==0){
					//System.out.println("玩家2赢了！");
					JOptionPane.showMessageDialog(this, "玩家2赢了！");
					//Win(2);
				}
				//定位出牌
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
				//System.out.println("玩家2没牌出");
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "玩家2没牌出！");
					x=0;
					turnto++;
				}
			}
		}
		if(e.getSource()==publishCard[3] && turnto==2)//玩家3
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
				playerList[3].removeAll(currentList[3]);//移除走的牌
				if(playerList[3].size()==0){
					JOptionPane.showMessageDialog(this, "玩家3赢了！");
					//System.out.println("玩家3赢了！");
					//Win(3);
				}
				//定位出牌
				Point point=new Point();
				point.x=361;
				point.y=130;
				card1=currentList[3].get(0);
				Mainfc.move(card1, card1.getLocation(), point);
				card1.TurnFront();
				//出完结束以后，重新整理牌
				Mainfc.rePosition(this, playerList[3], 3);
				turnto ++;
			}
			else {
				//System.out.println("玩家3没牌出！");
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "玩家3没牌出！");
					x=0;
					turnto++;
				}
			}
		}
		if(e.getSource()==publishCard[4] && turnto==3)//玩家4
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
				playerList[0].removeAll(currentList[0]);//移除走的牌
				if(playerList[0].size()==0){
					JOptionPane.showMessageDialog(this, "玩家4赢了！");
					//System.out.println("玩家4赢了！");
					//Win(4);
				}
				//定位出牌
				Point point=new Point();
				point.x=150;
				point.y=200;
				card1=currentList[0].get(0);
				Mainfc.move(card1, card1.getLocation(), point);
				card1.TurnFront();
				//出完结束以后，重新整理牌
				Mainfc.rePosition(this, playerList[0], 0);
				turnto=0;
			}
			else {
				//System.out.println("玩家4没牌出");
				x++;
				if(x>0){
					JOptionPane.showMessageDialog(this, "玩家4没牌出！");
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
			ShowList[0]=CardName; //保存当前要翻的牌
			for(int i=0,len=ShowList[0].size();i<len;i++){
				card1=ShowList[0].get(i);
				if(!card1.up)
					card1.TurnFront();
				else
					card1.TurnRear();
			}
		}
	}
	public static int strtoint(String x){//抽出一组牌中的编号
		if (x.length()==3)return x.charAt(x.length()-1)-'0';
		else return x.charAt(x.length()-1)-'0'+10*(x.charAt(x.length()-2)-'0');
	}
	public static void main(String args[]) {
		Main lain=new Main(0);
	}
}