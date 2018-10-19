package cn.tedu.shoot01;

import java.awt.image.BufferedImage;
import java.util.Collection;

/**Ӣ�ۻ�*/
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	
	static{
		images = new BufferedImage[2];
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	
	private int life;
	private int doubleFire;
	
	/**���췽��*/
	public Hero(){
		super(97,124,140,400);
		life = 3;
		doubleFire = 0;
	}
	
	/**Ӣ�ۻ���������ƶ�*/
	public void moveTo(int x,int y){
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	
	/**��дstep()�ƶ�*/
	public void step(){
		
	}
	
	int index = 0;
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){
		if(isLife()){
			return images[index++%2];
		}
		return null;
	}
	
	/**�����ӵ�����*/
	public Bullet[] shoot(){
		int xSpeed = this.width/4;//�ӵ���x����
		int ySpeed = 20;//�ӵ���y����
		if(doubleFire>0){
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+xSpeed,this.y-ySpeed);
			bs[1] = new Bullet(this.x+3*xSpeed,this.y-ySpeed);
			doubleFire -= 2;
			return bs;
		}else{
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xSpeed,this.y-ySpeed);
			return bs;
		}
	}
	
	/**Ӣ�ۻ�����*/
	public void addLife(){
		life++;
	}
	
	/**��ȡӢ�ۻ�����*/
	public int getLife(){
		return life;
	}
	
	/**Ӣ�ۻ�����*/
	public void subtractLife(){
		life--;
	}
	
	/**Ӣ�ۻ�������*/
	public void addDoubleFire(){
		doubleFire += 40;
	}
	
	/**��ջ���*/
	public void clearDoubleFire(){
		doubleFire = 0;
	}
}




