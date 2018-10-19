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

/**��������*/
public class World extends JPanel {
	public static final int WIDTH = 400;//���ڿ�
	public static final int HEIGHT = 700;//���ڸ�
	
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
			throw new RuntimeException("ͼƬ����ʧ�ܣ�");
		}
	}
	
	private Sky sky = new Sky();//��ն���
	private Hero hero = new Hero();//Ӣ�ۻ�����
	private List<FlyingObject> enemies = new ArrayList<FlyingObject>();//���ˣ�С�л�����л���С�۷䣩����
	private List<Bullet> bullets = new ArrayList<Bullet>();//�ӵ�����
	
	/**���ɵ��˶���*/
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
	/**�����볡*/
	public void enterAction(){//ÿ10������һ��
		enterIndex++;
		if(enterIndex%40==0){//ÿ400������һ��
			FlyingObject f = nextOne();
			enemies.add(f);//��������ӵ�������
		}
	}
	
	int shootIndex = 0;
	/**�ӵ��볡*/
	public void shootAction(){//ÿ10������һ��
		shootIndex++;
		if(shootIndex%30==0){
			Bullet[] bs = hero.shoot();
			bullets.addAll(Arrays.asList(bs));
		}
	}
	
	/**�������ƶ�*/
	public void stepAction(){
		sky.step();
		for(FlyingObject f:enemies){
			f.step();
		}
		for(Bullet bs:bullets){
			bs.step();
		}
	}
	
	/**ɾ��Խ�������*/
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
	
	int score = 0;//�÷�
	/**�ӵ��������ײ*/
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
	
	/**Ӣ�ۻ��������ײ*/
	public void heroBangAction(){
		for(FlyingObject f:enemies){
			if(f.isLife() && hero.isLife() && f.hit(hero)){
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	/**�����Ϸ����*/
	public void checkGameOverAction(){
		if(hero.getLife()<=0){
			state=GAME_OVER;
		}
	}
	
	/**
	 * ������������
	 * ������ʱ�������������
	 * ��ʱ����������ʱ�������ķ���
	 * �����������������¼�����껬���¼�
	 */
	public void action(){
		//���������
		MouseAdapter l =new MouseAdapter() {
			/**����ƶ��¼�*/
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			/**������¼�*/
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
			/**����Ƴ��¼�*/
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING){
					state=PAUSE;
				}
			}
			/**��������¼�*/
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE){
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
		//��ʱ��
		Timer t = new Timer();
		int intervel = 10;
		t.schedule(new TimerTask() {
			public void run() {
				if(state==RUNNING){
					enterAction();//�����볡
					shootAction();//�ӵ��볡
					stepAction();//�������ƶ�
					outOfBoundsAction();//ɾ��Խ�������
					bulletBangAction();//�ӵ��������ײ
					heroBangAction();//Ӣ�ۻ��������ײ
					checkGameOverAction();//�����Ϸ����
				}
				repaint();//������
			}
		},intervel,intervel);
	}
	
	/**����*/
	public void paint(Graphics g){
		sky.paintObject(g);//�����
		hero.paintObject(g);//��Ӣ�ۻ�
		for(FlyingObject f:enemies){
			f.paintObject(g);//������
		}
		for(Bullet bs:bullets){
			bs.paintObject(g);//���ӵ�
		}
		g.drawString("SCORE:"+score,10,25);//����
		g.drawString("LIFE:"+hero.getLife(),10,45);//����
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
		JFrame frame = new JFrame("�ɻ���ս");//�������ڶ���
		World world = new World();//����������
		frame.add(world);//�������ӵ�������
		frame.setSize(WIDTH,HEIGHT);//���ô��ڴ�С
		frame.setAlwaysOnTop(true);//����������������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Ĭ�Ϲر�
		frame.setLocationRelativeTo(null);//���ô����ʼλ��
		frame.setVisible(true);//����paint����
		world.action();//��������
	}
}
