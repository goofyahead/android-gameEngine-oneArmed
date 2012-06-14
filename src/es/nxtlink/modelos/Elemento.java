package es.nxtlink.modelos;

import android.graphics.Bitmap;

public class Elemento {
	
	private Bitmap imagen;
	private int sizeX;
	private int sizeY;
	private int x;
	private int y;
	
	public Elemento (Bitmap imagen, int x , int y){
		this.imagen = imagen;
		this.sizeX = imagen.getWidth();
		this.sizeY = imagen.getHeight();
		this.x = x;
		this.y = y;
	}
	
	public Bitmap getImagen() {
		return imagen;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setImagen(Bitmap imagen) {
		this.imagen = imagen;
	}
	public int getSizeX() {
		return sizeX;
	}
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	

}
