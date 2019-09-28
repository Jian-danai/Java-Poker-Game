import java.awt.Point;
import java.util.*;

class Mainfc {
	// ����Ч������
	static void move(Card card, Point from, Point to) {//Ų���ƣ����������յ�����ߣ�ÿ��Ų��һС������ͣ5mm��ʵ�ֶ�̬Ч��
		if (to.x != from.x) {
			double k = (1.0) * (to.y - from.y) / (to.x - from.x);
			double b = to.y - to.x * k;
			int flag = 0;// �ж������������ƶ�  ����
			if (from.x < to.x)
				flag = 20;
			else 
				flag = -20;
			
			for (int i = from.x; Math.abs(i - to.x) > 20; i += flag) {//�����ֵ
				double y = k * i + b;
				card.setLocation(i, (int) y);
				try {
					Thread.sleep(5); // �ӳ�
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		card.setLocation(to);// λ��У׼
	}

	// ���¶�λ  FLAG=1�����·�
	static void rePosition(Main m, List<Card> list, int flag) {//�ض�λ��ʹ�ƶ�����ص���һ��
		Point p = new Point();
		if (flag == 0) {//���4�ĵ�һ���Ƶ��ض�λλ��
			p.x = 50;
			p.y = (450 / 2) - (list.size() + 1) * 15 / 2;
		}
		if (flag == 1) { // ���� y=450 width=830
			p.x = (800 / 2) - (list.size() + 1) * 21 / 2;
			p.y = 450;
		}
		if (flag == 2) {
			p.x = 700;
			p.y = (450 / 2) - (list.size() + 1) * 15 / 2;
		}
		if (flag == 3) {
			p.x = (760 / 2) - (list.size() + 1) * 17 / 2;
			p.y = (30) ;
		}
		int len = list.size();
		for (int i = 0; i < len; i++) {
			Card card = list.get(i);
			Mainfc.move(card, card.getLocation(), p);//Ų��
			m.container.setComponentZOrder(card, 0);//�ص�
			if (flag == 1 || flag == 3)
				p.x += 21;
			else	
				if(flag==3)
					p.x += 15;
				 else
					p.y += 15;
		}
	}
	
	public static void order(List<Card> list) { //���ƶѵ��ƽ�������2���A��֮
		Collections.sort(list, new Comparator<Card>() {			
			public int compare(Card C1, Card C2) {
				//���ַ����͵�����ת��Ϊ���͵�����
				int a1 = Integer.parseInt(C1.name.substring(0, 1));// ��ɫ
				int a2 = Integer.parseInt(C2.name.substring(0, 1));
				int b1 = Integer.parseInt(C1.name.substring(2, C1.name.length()));// ��ֵ
				int b2 = Integer.parseInt(C2.name.substring(2, C2.name.length()));
				int flag = 0;
				// �����A����2
				if (b1 == 1)b1 += 20;//�Ӵ�Ȩ��ʹ��A���ڳ���2�����������
				if (b2 == 1)b2 += 20;
				if (b1 == 2)b1 += 30;//�Ӵ�Ȩ��ʹ��2����������
				if (b2 == 2)b2 += 30;
				flag = b2 - b1;
				if (flag == 0)//���������ͬ�����ػ�ɫ��
					return a2 - a1;
				else {//������ֲ�ͬ��ֱ�ӷ������ֲ�
					return flag;
				}
			}
		}
		);
	}
}