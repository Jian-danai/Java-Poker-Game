import java.awt.Point;
import java.util.*;

class Mainfc {
	// 发牌效果方法
	static void move(Card card, Point from, Point to) {//挪用牌，按照起点和终点的连线，每次挪动一小步并暂停5mm来实现动态效果
		if (to.x != from.x) {
			double k = (1.0) * (to.y - from.y) / (to.x - from.x);
			double b = to.y - to.x * k;
			int flag = 0;// 判断向左还是向右移动  步幅
			if (from.x < to.x)
				flag = 20;
			else 
				flag = -20;
			
			for (int i = from.x; Math.abs(i - to.x) > 20; i += flag) {//求绝对值
				double y = k * i + b;
				card.setLocation(i, (int) y);
				try {
					Thread.sleep(5); // 延迟
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		card.setLocation(to);// 位置校准
	}

	// 重新定位  FLAG=1代表下方
	static void rePosition(Main m, List<Card> list, int flag) {//重定位，使牌堆整齐地叠在一起
		Point p = new Point();
		if (flag == 0) {//玩家4的第一张牌的重定位位置
			p.x = 50;
			p.y = (450 / 2) - (list.size() + 1) * 15 / 2;
		}
		if (flag == 1) { // 排序 y=450 width=830
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
			Mainfc.move(card, card.getLocation(), p);//挪动
			m.container.setComponentZOrder(card, 0);//重叠
			if (flag == 1 || flag == 3)
				p.x += 21;
			else	
				if(flag==3)
					p.x += 15;
				 else
					p.y += 15;
		}
	}
	
	public static void order(List<Card> list) { //对牌堆的牌进行排序，2最大，A次之
		Collections.sort(list, new Comparator<Card>() {			
			public int compare(Card C1, Card C2) {
				//将字符串型的数字转换为整型的数字
				int a1 = Integer.parseInt(C1.name.substring(0, 1));// 花色
				int a2 = Integer.parseInt(C2.name.substring(0, 1));
				int b1 = Integer.parseInt(C1.name.substring(2, C1.name.length()));// 数值
				int b2 = Integer.parseInt(C2.name.substring(2, C2.name.length()));
				int flag = 0;
				// 如果是A或者2
				if (b1 == 1)b1 += 20;//加大权重使得A大于除了2以外的其他牌
				if (b2 == 1)b2 += 20;
				if (b1 == 2)b1 += 30;//加大权重使得2大于其他牌
				if (b2 == 2)b2 += 30;
				flag = b2 - b1;
				if (flag == 0)//如果数字相同，返回花色差
					return a2 - a1;
				else {//如果数字不同，直接返回数字差
					return flag;
				}
			}
		}
		);
	}
}