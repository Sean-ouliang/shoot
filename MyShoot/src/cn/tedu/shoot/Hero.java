package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** Ӣ�ۻ� */
public class Hero extends FlyingObject{
	private static BufferedImage[] images; //ͼƬ����
	static {
		images = new BufferedImage[2]; //2��ͼƬ
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	
	private int life; //��
	private int doubleFire; //����ֵ
	/** ���췽�� */
	public Hero(){
		super(97,124,140,400);
		life = 3;
		doubleFire = 0;
	}
	
	/** Ӣ�ۻ���������ƶ�  x/y:����x����/y���� */
	public void moveTo(int x,int y) {
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	
	/** ��дstep()�ƶ� */
	public void step() {
		
	}
	
	int index = 0;
	/**��дgetImage()��ȡͼƬ*/
	public BufferedImage getImage(){//ÿ10������һ��
		if(isLife()){
			return images[index++%2];
		}
		return null;
		/*
		 *                    index=0;
		 * 10ms ����images[0]  index=1;
		 * 20ms ����images[1]  index=2;
		 * 30ms ����images[0]  index=3;
		 * 40ms ����images[1]  index=4;
		 * 50ms ����images[0]  index=5;
		 */
	}
	
	/**�����ӵ�����(Ӣ�ۻ������ӵ�)*/
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0){
			Bullet[] bs = new Bullet[3];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+2*xStep,this.y-yStep);
			bs[2] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire -= 2;
			return bs;
		}else{
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
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
	
	/**��ջ���ֵ*/
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	/**Ӣ�ۻ�������ֵ*/
	public void addDoubleFire(){
		doubleFire += 40;
	}
}















