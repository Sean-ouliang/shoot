package cn.tedu.shoot02;

import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**��������*/
public abstract class FlyingObject {
	public static final int LIFE = 0;//���ŵ�
	public static final int DEAD = 1;//���˵�
	public static final int REMOVE = 2;//ɾ����
	protected int state = LIFE;//��ǰ״̬��Ĭ�ϻ��ŵģ�
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	/**ר�Ÿ��ӵ���Ӣ�ۻ�������ṩ��*/
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/**ר�Ÿ�С�л�����л���С�۷��ṩ*/
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random();
		x = rand.nextInt(World.WIDTH-width);
		y = -height;
	}
	
	/**�������ƶ�*/
	abstract public void step();
	
	/**��ȡͼƬ*/
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**��ȡͼƬ*/
	public abstract BufferedImage getImage();
	
	/**�ж϶����Ƿ��ǻ��ŵ�*/
	public boolean isLife(){
		return state == LIFE;
	}
	
	/**�ж϶����Ƿ������˵�*/
	public boolean isDead(){
		return state == DEAD;
	}
	
	/**�ж϶����Ƿ���ɾ����*/
	public boolean isRemove(){
		return state == REMOVE;
	}
	
	/**������  g:����*/
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);
	}
	
	/**�������Ƿ�Խ��*/
	public abstract boolean outOfBounds();
	
	/**���������ӵ�/Ӣ�ۻ�����ײ��this:���ˣ�other:�ӵ�/Ӣ�ۻ�*/
	public boolean hit(FlyingObject other){
		int x1 = this.x-other.width;
		int x2 = this.x+this.width;
		int y1 = this.y-other.height;
		int y2 = this.y+this.height;
		int x = other.x;
		int y = other.y;
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	
	/**������ȥ��*/
	public void goDead(){
		state = REMOVE;
	}
}



