package cn.tedu.shoot02;

import java.awt.image.BufferedImage;

/**Ӣ�ۻ�*/
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	static{
		images = new BufferedImage[2];
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	
	private int life;//����
	private int doubleFire;//����ֵ

	/**���췽��*/
	public Hero(){
		super(97,124,140,400);
		life = 3;
		doubleFire = 0;
	}
	
	/**Ӣ�ۻ���������ƶ���x:����x���꣬y:����y����*/
	public void moveTo(int x,int y){
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	
	/**��д*/
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
	
	/**�����ӵ�*/
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0){
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire -= 2;//����˫���������������ÿ����һ�λ���ֵ��2
			return bs;
		}else{
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep);
			return bs;
		}
	}
	
	/**��дoutOfBounds()����Ƿ�Խ��*/
	public boolean outOfBounds(){
		return false;//����Խ��
	}
	
	/**Ӣ�ۻ�����*/
	public void addLife(){
		life++;
	}
	
	/**��ȡӢ�ۻ�������*/
	public int getLife(){
		return life;
	}
	
	/**Ӣ�ۻ�����*/
	public void subtractLife(){
		life--;
	}
	
	/**Ӣ�ۻ���ջ���ֵ*/
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	/**Ӣ�ۻ�������*/
	public void addDoubleFire(){
		doubleFire += 40;
	}
}






