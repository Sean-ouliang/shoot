package cn.tedu.shoot02;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

/**整个世界*/
public class World extends JPanel {
	/**窗口宽*/
	protected static final int WIDTH = 400;
	/**窗口高*/
	protected static final int HEIGHT = 700;
	
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	private int state = START;
	
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	
	//加载静态图片
	static{
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");
	}
	
	/**天空对象*/
	Sky sky = new Sky();
	/**英雄机对象*/
	Hero hero = new Hero();
	/**敌人（小敌机、大敌机、小蜜蜂）对象*/
	FlyingObject[] nenmies = {};
	/**子弹数组*/
	Bullet[] bullets = {};
	
	/**生成敌人对象*/
	public FlyingObject nextOne(){
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type<5){
			return new Bee();
		}else if(type<12){
			return new Airplane();
		}else{
			return new BigAirplane();
		}
	}
	
	int enterIndex = 0;
	/**敌人入场*/
	public void enterAction(){//每10毫秒走一次
		enterIndex++;
		if(enterIndex%40==0){
			FlyingObject obj = nextOne();
			nenmies = Arrays.copyOf(nenmies,nenmies.length+1);
			nenmies[nenmies.length-1] = obj;
		}
	}
	
	int shootIndex = 0;
	/**子弹入场*/
	public void shootAction(){//每10毫秒走一次
		shootIndex++;
		if(shootIndex%30==0){
			Bullet[] bt = hero.shoot();
			bullets = Arrays.copyOf(bullets,bullets.length+bt.length);
			System.arraycopy(bt,0,bullets,bullets.length-bt.length,bt.length);
		}
	}
	
	/**飞行物移动*/
	public void stepAction(){
		sky.step();
		for(int i=0;i<nenmies.length;i++){
			nenmies[i].step();
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].step();
		}
	}
	
	/**删除飞行物*/
	public void outOfBoundsAction(){
		int index = 0;
		FlyingObject[] nenmiesLives = new FlyingObject[nenmies.length];
		for(int i=0;i<nenmies.length;i++){
			FlyingObject f = nenmies[i];
			if(!f.outOfBounds()){
				nenmiesLives[index] = f;
				index++;
			}
		}
		nenmies = Arrays.copyOf(nenmiesLives,index);
		
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];
			if(!b.outOfBounds()){
				bulletLives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives,index);
	}
	
	int score = 0;//得分
	/**敌人与子弹碰撞*/
	public void bulletBangAction(){
		for(int i=0;i<nenmies.length;i++){
			FlyingObject f = nenmies[i];
			for(int j=0;j<bullets.length;j++){
				Bullet b = bullets[j];
				if(f.isLife() && b.isLife() && f.hit(b)){
					f.goDead();
					b.goDead();
					if(f instanceof Enemy){
						Enemy e = (Enemy)f;
						score += e.getScore();
					}
					if(f instanceof Award){
						Award a = (Award)f;
						int type = a.getAwardType();
						switch(type){
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						case Award.LIFE:
							hero.addLife();
							break;
						}
					}
				}
			}
		}
	}
	
	/**英雄机与敌人碰撞*/
	public void heroBangAction(){
		for(int i=0;i<nenmies.length;i++){
			FlyingObject f = nenmies[i];
			if(f.isLife() && hero.isLife() && f.hit(hero)){
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	/**检测游戏结束*/
	public void checkGameOverAction(){
		if(hero.getLife()<=0){
			state = GAME_OVER;
		}
	}
	
	/**启动程序*/
	public void action(){
		//侦听器
		MouseAdapter l = new MouseAdapter(){
			/**鼠标移动事件*/
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			/**鼠标点击事件*/
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					sky = new Sky();
					hero = new Hero();
					nenmies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;
					break;
				}
			}
			/**鼠标移入事件*/
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state = RUNNING;
				}
			}
			/**鼠标移出事件*/
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state = PAUSE;
				}
			}
		};
		this.addMouseListener(l);//鼠标操作事件
		this.addMouseMotionListener(l);//鼠标滑动事件
		
		Timer timer = new Timer();//定时器对象
		int intervel = 10;//时间间隔
		timer.schedule(new TimerTask(){
			public void run(){
				if(state==RUNNING){
					enterAction();
					shootAction();
					stepAction();
					outOfBoundsAction();
					bulletBangAction();
					heroBangAction();
					checkGameOverAction();
				}
				repaint();
			}
		},intervel,intervel);//计划表
	}
	
	public void paint(Graphics g){
		sky.paintObject(g);
		hero.paintObject(g);
		for(int i=0;i<nenmies.length;i++){
			nenmies[i].paintObject(g);
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].paintObject(g);
		}
		g.drawString("SCORE:"+score,10,25);
		g.drawString("LIFE:"+hero.getLife(),10,45);
		switch(state){
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	/**主方法*/
	public static void main(String[] args){
		JFrame frame = new JFrame();//创建窗口对象
		World world = new World();//创建面板对象
		frame.add(world);//将面板对象添加到窗口中
		frame.setSize(WIDTH,HEIGHT);//设置窗口的大小
		frame.setAlwaysOnTop(true);//窗口总是在最上面
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);//默认关闭
		frame.setLocationRelativeTo(null);//窗口初始位置
		frame.setVisible(true);//调用paint方法
		
		world.action();//启动程序
	}
}
