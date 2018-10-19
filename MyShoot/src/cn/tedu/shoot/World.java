package cn.tedu.shoot;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/** 整个游戏世界 */
public class World extends JPanel {
	public static final int WIDTH = 400;//窗口的宽
	public static final int HEIGHT = 700;//窗口的高
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	static{//初始化静态图片
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");
	}
	
	private Sky sky = new Sky();    //天空对象
	private Hero hero = new Hero(); //英雄机对象
	private FlyingObject[] enemies = {}; //敌人(小敌机、大敌机、小蜜蜂)数组
	private Bullet[] bullets = {}; //子弹数组
	
	/**生产敌人对象*/
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
	/**敌人（小敌机、大敌机、小蜜蜂）入场*/
	public void enterAction(){//每10毫秒走一次
		enterIndex++;
		if(enterIndex%40==0){//每400毫秒走一次
			FlyingObject obj = nextOne();
			enemies = Arrays.copyOf(enemies,enemies.length+1);//扩容
			enemies[enemies.length-1] = obj;
		}
	}
	
	int shootIndex = 0;
	/**子弹入场*/
	public void shootAction(){//10毫秒走一次
		shootIndex++;
		if(shootIndex%30==0){//每300毫秒走一次
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length);
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
		}
	}
	
	/**飞行物移动*/
	public void stepAction(){//每10毫秒走一次
		sky.step();
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].step();
		}
	}
	
	/**删除越界的飞行物*/
	public void outOfBoundsAction(){//每10毫秒走一次
		int index = 0;
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(!f.outOfBounds() && !f.isRemove()){
				enemyLives[index] = f;
				index++;
			}
		}
		enemies = Arrays.copyOf(enemyLives,index);
		
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];
			if(!b.outOfBounds() && !b.isRemove()){
				bulletLives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives,index);
	}
	
	int score = 0;
	/**子弹与敌人的碰撞*/
	public void bulletBangAction(){//每10毫秒走一次
		for(int i=0;i<bullets.length;i++){
			Bullet b = bullets[i];
			for(int j=0;j<enemies.length;j++){
				FlyingObject f = enemies[j];
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
	public void heroBangAction(){//每10毫秒走一次
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.isLife() && f.hit(hero)){
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	/**检测游戏结束*/
	public void checkGameOverAction(){//每10毫秒走一次
		if(hero.getLife()<=0){//游戏结束了
			state = GAME_OVER;
			
		}
	}
	
	/**游戏启动方法*/
	public void action() { //测试代码
		//侦听器对象
		MouseAdapter l = new MouseAdapter(){
			/**重写mouseMoved()鼠标移动事件*/
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			/**重写mouseClicked()鼠标点击事件*/
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state=RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state=START;
					break;
				}
			}
			/**重写mouseExited()鼠标移出事件*/
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state=PAUSE;
				}
			}
			/**重写mouseEnter()鼠标移入事件*/
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l);//处理鼠标事件
		this.addMouseMotionListener(l);//处理鼠标滑到事件
		
		Timer timer = new Timer();//定时器对象
		int intervel = 10;//定时间隔
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
	
	
	/**重写画笔*/
	public void paint(Graphics g){
		sky.paintObject(g);
		hero.paintObject(g);
		for(int i=0;i<enemies.length;i++){
			enemies[i].paintObject(g);
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
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		
		world.action();
	}
	
}



















