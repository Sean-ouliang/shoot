package cn.tedu.shoot01;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**整个世界*/
public class World extends JPanel {
	public static final int WIDTH = 400;//窗口宽
	public static final int HEIGHT = 700;//窗口高
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	static{
		try{
			start = ImageIO.read(World.class.getResource("start.png"));
			pause = ImageIO.read(World.class.getResource("pause.png"));
			gameover = ImageIO.read(World.class.getResource("gameover.png"));
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("图片加载失败！");
		}
	}
	
	private Sky sky = new Sky();//天空对象
	private Hero hero = new Hero();//英雄机对象
	private List<FlyingObject> enemies = new ArrayList<FlyingObject>();//敌人（小敌机、大敌机、小蜜蜂）集合
	private List<Bullet> bullets = new ArrayList<Bullet>();//子弹集合
	
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
		if(enterIndex%40==0){//每400毫秒走一次
			FlyingObject f = nextOne();
			enemies.add(f);//将敌人添加到集合中
		}
	}
	
	int shootIndex = 0;
	/**子弹入场*/
	public void shootAction(){//每10毫秒走一次
		shootIndex++;
		if(shootIndex%30==0){
			Bullet[] bs = hero.shoot();
			bullets.addAll(Arrays.asList(bs));
		}
	}
	
	/**飞行物移动*/
	public void stepAction(){
		sky.step();
		for(FlyingObject f:enemies){
			f.step();
		}
		for(Bullet bs:bullets){
			bs.step();
		}
	}
	
	/**删除越界飞行物*/
	public void outOfBoundsAction(){
		List<FlyingObject> enemiesLive = new ArrayList<FlyingObject>();
		for(FlyingObject f:enemies){
			if(!f.outOfBounds() && !f.isRemove()){
				enemiesLive.add(f);
			}
		}
		enemies = new ArrayList<FlyingObject>(enemiesLive);
		
		List<Bullet> bulletsLive = new ArrayList<Bullet>();
		for(Bullet b:bullets){
			if(!b.outOfBounds() && !b.isRemove()){
				bulletsLive.add(b);
			}
		}
		bullets = new ArrayList<Bullet>(bulletsLive);
	}
	
	int score = 0;//得分
	/**子弹与敌人碰撞*/
	public void bulletBangAction(){
		for(Bullet b:bullets){
			for(FlyingObject f:enemies){
				if(b.isLife() && f.isLife() && f.hit(b)){
					b.goDead();
					f.goDead();
					if(f instanceof Enemy){
						Enemy e = (Enemy)f;
						score += e.getScore();
					}
					if(f instanceof Award){
						Award a = (Award)f;
						int type = a.getAwardType();
						switch(type){
						case Award.LIFE:
							hero.addLife();
							break;
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						}
					}
				}
			}
		}
	}
	
	/**英雄机与敌人碰撞*/
	public void heroBangAction(){
		for(FlyingObject f:enemies){
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
			state=GAME_OVER;
		}
	}
	
	/**
	 * 程序启动方法
	 * 包含定时器和鼠标侦听器
	 * 定时器：包含定时器发生的方法
	 * 鼠标侦听器：鼠标点击事件和鼠标滑动事件
	 */
	public void action(){
		//鼠标侦听器
		MouseAdapter l =new MouseAdapter() {
			/**鼠标移动事件*/
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			/**鼠标点击事件*/
			public void mouseClicked(MouseEvent e) {
				switch(state){
				case START:
					state=RUNNING;
					break;
				case GAME_OVER:
					hero = new Hero();
					score = 0;
					bullets = new ArrayList<Bullet>();
					enemies = new ArrayList<FlyingObject>();
					state=START;
				}
			}
			/**鼠标移出事件*/
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING){
					state=PAUSE;
				}
			}
			/**鼠标移入事件*/
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE){
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
		//定时器
		Timer t = new Timer();
		int intervel = 10;
		t.schedule(new TimerTask() {
			public void run() {
				if(state==RUNNING){
					enterAction();//敌人入场
					shootAction();//子弹入场
					stepAction();//飞行物移动
					outOfBoundsAction();//删除越界飞行物
					bulletBangAction();//子弹与敌人碰撞
					heroBangAction();//英雄机与敌人碰撞
					checkGameOverAction();//检测游戏结束
				}
				repaint();//画对象
			}
		},intervel,intervel);
	}
	
	/**画笔*/
	public void paint(Graphics g){
		sky.paintObject(g);//画天空
		hero.paintObject(g);//画英雄机
		for(FlyingObject f:enemies){
			f.paintObject(g);//画敌人
		}
		for(Bullet bs:bullets){
			bs.paintObject(g);//画子弹
		}
		g.drawString("SCORE:"+score,10,25);//画分
		g.drawString("LIFE:"+hero.getLife(),10,45);//画命
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
	
	public static void main(String[] args){
		JFrame frame = new JFrame("飞机大战");//创建窗口对象
		World world = new World();//创建面板对象
		frame.add(world);//将面板添加到窗口中
		frame.setSize(WIDTH,HEIGHT);//设置窗口大小
		frame.setAlwaysOnTop(true);//窗口总是在最上面
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭
		frame.setLocationRelativeTo(null);//设置窗体初始位置
		frame.setVisible(true);//调用paint方法
		world.action();//启动程序
	}
}
