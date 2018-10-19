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

/**��������*/
public class World extends JPanel {
	/**���ڿ�*/
	protected static final int WIDTH = 400;
	/**���ڸ�*/
	protected static final int HEIGHT = 700;
	
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	private int state = START;
	
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	
	//���ؾ�̬ͼƬ
	static{
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");
	}
	
	/**��ն���*/
	Sky sky = new Sky();
	/**Ӣ�ۻ�����*/
	Hero hero = new Hero();
	/**���ˣ�С�л�����л���С�۷䣩����*/
	FlyingObject[] nenmies = {};
	/**�ӵ�����*/
	Bullet[] bullets = {};
	
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
		if(enterIndex%40==0){
			FlyingObject obj = nextOne();
			nenmies = Arrays.copyOf(nenmies,nenmies.length+1);
			nenmies[nenmies.length-1] = obj;
		}
	}
	
	int shootIndex = 0;
	/**�ӵ��볡*/
	public void shootAction(){//ÿ10������һ��
		shootIndex++;
		if(shootIndex%30==0){
			Bullet[] bt = hero.shoot();
			bullets = Arrays.copyOf(bullets,bullets.length+bt.length);
			System.arraycopy(bt,0,bullets,bullets.length-bt.length,bt.length);
		}
	}
	
	/**�������ƶ�*/
	public void stepAction(){
		sky.step();
		for(int i=0;i<nenmies.length;i++){
			nenmies[i].step();
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].step();
		}
	}
	
	/**ɾ��������*/
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
	
	int score = 0;//�÷�
	/**�������ӵ���ײ*/
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
	
	/**Ӣ�ۻ��������ײ*/
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
	
	/**�����Ϸ����*/
	public void checkGameOverAction(){
		if(hero.getLife()<=0){
			state = GAME_OVER;
		}
	}
	
	/**��������*/
	public void action(){
		//������
		MouseAdapter l = new MouseAdapter(){
			/**����ƶ��¼�*/
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			/**������¼�*/
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
			/**��������¼�*/
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state = RUNNING;
				}
			}
			/**����Ƴ��¼�*/
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state = PAUSE;
				}
			}
		};
		this.addMouseListener(l);//�������¼�
		this.addMouseMotionListener(l);//��껬���¼�
		
		Timer timer = new Timer();//��ʱ������
		int intervel = 10;//ʱ����
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
		},intervel,intervel);//�ƻ���
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
	
	/**������*/
	public static void main(String[] args){
		JFrame frame = new JFrame();//�������ڶ���
		World world = new World();//����������
		frame.add(world);//����������ӵ�������
		frame.setSize(WIDTH,HEIGHT);//���ô��ڵĴ�С
		frame.setAlwaysOnTop(true);//����������������
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);//Ĭ�Ϲر�
		frame.setLocationRelativeTo(null);//���ڳ�ʼλ��
		frame.setVisible(true);//����paint����
		
		world.action();//��������
	}
}
