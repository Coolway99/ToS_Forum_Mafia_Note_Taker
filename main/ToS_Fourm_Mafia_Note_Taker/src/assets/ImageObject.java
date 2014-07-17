package assets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class ImageObject {
	int width;
	int hight;
	int[][] pixelArray;
	int x;
	int y;
	String text;
	
	public ImageObject(int width, int hight){
		this.width = width;
		this.hight = hight;
		this.pixelArray = new int[hight][width];
	}
	public ImageObject(int[][] pixelArray){
		this.pixelArray = pixelArray;
		this.width = pixelArray[1].length;
		this.hight = pixelArray.length;
	}
	public void setImage(BufferedImage image){
		this.pixelArray = new int[image.getTileHeight()][image.getTileWidth()];
		this.hight =pixelArray.length;
		this.width = pixelArray[1].length;
	}
	public void setText(String text){
		this.text = text;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getWidth(){
		return this.width;
	}
	public int getHight(){
		return this.hight;
	}
	public void draw(Graphics2D g, boolean isMouseOver){
		int charWidth = g.getFontMetrics().charsWidth(text.toCharArray(), 0, text.toCharArray().length);
		int charHight = g.getFontMetrics().getHeight();
		charWidth = (width - (charWidth/2)) + x;
		charHight = (hight - (charHight/2)) + y;
		int[][] pixels;
		if(isMouseOver){
			pixels = new int[this.pixelArray.length][this.pixelArray[1].length];
			for(int y = 0; y < pixelArray.length; y++){
				for(int x = 0; x < pixelArray[1].length; x++){
					pixels[y][x] = pixelArray[y][x] + 0x001111;
				}
			}
		} else {
			pixels = this.pixelArray;
		}
		BufferedImage image = new BufferedImage(this.pixelArray[1].length, this.pixelArray.length, BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y < pixelArray.length; y++){
			for(int x = 0; x < pixelArray[1].length; x++){
				image.setRGB(x, y, pixels[y][x]);
			}
		}
		g.drawImage(image, x, y, null);
		g.drawString(text, charWidth, charHight);
	}
}
