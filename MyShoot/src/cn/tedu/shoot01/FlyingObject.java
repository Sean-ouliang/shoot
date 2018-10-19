package cn.tedu.shoot01;

import java.util.Random;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**������*/
public abstract class FlyingObject {
	public static final int LIFE = 0;//���ŵ�
	public static final int DEAD = 1;//���˵�
	public static final int REMOVE = 2;//ɾ����
	protected int state = LIFE;//��ǰ״̬
	
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
	
	/**ר�Ÿ�С�л�����л���С�����ṩ��*/
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random();
		x = rand.nextInt(World.WIDTH-this.width);
		y = -height;
	}
	
	/**�������ƶ�*/
	public abstract void step();
	
	/**��ȡͼƬ*/
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("ͼƬ����ʧ�ܣ�");
		}
	}
	
	/**��ȡͼƬ*/
	public abstract BufferedImage getImage();
	
	/**�жϵ�ǰ״̬�Ƿ�Ϊ���ŵ�*/
	public boolean isLife(){
		return state==LIFE;
	}
	
	/**�жϵ�ǰ״̬�Ƿ�Ϊ���˵�*/
	public boolean isDead(){
		return state==DEAD;
	}
	
	/**�жϵ�ǰ״̬�Ƿ�Ϊɾ����*/
	public boolean isRemove(){
		return state==REMOVE;
	}
	
	/**����*/
	public void paintObject(Graphics g){
		g.drawImage(this.getImage(),this.x,this.y,null);
	}
	
	/**
	 * Խ���⣨С�۷䣬С�л�����л���
	 * ����true��ʾ�Ѿ�Խ��
	 * @return
	 */
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT;
	}
	
	/**
	 * ���������ӵ�/Ӣ�ۻ���ײ
	 * this:���ˣ�other:�ӵ�/Ӣ�ۻ�
	 * ����true��ʾ�Ѿ�ײ��
	 * @return
	 */
	public boolean hit(FlyingObject other){
		int x1 = this.x - other.width;
		int x2 = this.x + this.width;
		int y1 = this.y - other.height;
		int y2 = this.y + this.height;
		int x = other.x;
		int y = other.y;
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	
	/**������ȥ��*/
	public void goDead(){
		state = DEAD;
	}
}









