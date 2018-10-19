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

/** ������Ϸ���� */
public class World extends JPanel {
	public static final int WIDTH = 400;//���ڵĿ�
	public static final int HEIGHT = 700;//���ڵĸ�
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	static{//��ʼ����̬ͼƬ
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");
	}
	
	private Sky sky = new Sky();    //��ն���
	private Hero hero = new Hero(); //Ӣ�ۻ�����
	private FlyingObject[] enemies = {}; //����(С�л�����л���С�۷�)����
	private Bullet[] bullets = {}; //�ӵ�����
	
	/**�������˶���*/
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
	/**���ˣ�С�л�����л���С�۷䣩�볡*/
	public void enterAction(){//ÿ10������һ��
		enterIndex++;
		if(enterIndex%40==0){//ÿ400������һ��
			FlyingObject obj = nextOne();
			enemies = Arrays.copyOf(enemies,enemies.length+1);//����
			enemies[enemies.length-1] = obj;
		}
	}
	
	int shootIndex = 0;
	/**�ӵ��볡*/
	public void shootAction(){//10������һ��
		shootIndex++;
		if(shootIndex%30==0){//ÿ300������һ��
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length);
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
		}
	}
	
	/**�������ƶ�*/
	public void stepAction(){//ÿ10������һ��
		sky.step();
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++){
			bullets[i].step();
		}
	}
	
	/**ɾ��Խ��ķ�����*/
	public void outOfBoundsAction(){//ÿ10������һ��
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
	/**�ӵ�����˵���ײ*/
	public void bulletBangAction(){//ÿ10������һ��
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
	
	/**Ӣ�ۻ��������ײ*/
	public void heroBangAction(){//ÿ10������һ��
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.isLife() && f.hit(hero)){
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	/**�����Ϸ����*/
	public void checkGameOverAction(){//ÿ10������һ��
		if(hero.getLife()<=0){//��Ϸ������
			state = GAME_OVER;
			
		}
	}
	
	/**��Ϸ��������*/
	public void action() { //���Դ���
		//����������
		MouseAdapter l = new MouseAdapter(){
			/**��дmouseMoved()����ƶ��¼�*/
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			/**��дmouseClicked()������¼�*/
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
			/**��дmouseExited()����Ƴ��¼�*/
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state=PAUSE;
				}
			}
			/**��дmouseEnter()��������¼�*/
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l);//��������¼�
		this.addMouseMotionListener(l);//������껬���¼�
		
		Timer timer = new Timer();//��ʱ������
		int intervel = 10;//��ʱ���
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
	
	
	/**��д����*/
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



















