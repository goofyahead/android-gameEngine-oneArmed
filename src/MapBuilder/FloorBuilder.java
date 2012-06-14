package MapBuilder;

import es.nxtlink.modelos.Elemento;
import es.nxtlink.persistence.ObjectMapper;
import android.graphics.Bitmap;

public class FloorBuilder {

	/**
	 * This method will create a room of x*y size in tiles on determined pixel start.
	 * for ex. (6x4 room tiles starting on tile 3, 5)  createRoom (3,5,6,4);
	 * @param originX the number of the tile in the X.
	 * @param oiringY the number of the tile in the Y.
	 * @param sizeX the size of the room on the X.
	 * @param sizeY the size of the room in the Y.
	 * @param tile the bitmap of the tile.
	 */
	public static void CreateRoom(int originX, int originY, int ancho, int largo, Bitmap tile){
		int posX = originX;
		int posY = originY;
		for (int a = 0; a < ancho; a++){
			posX++;
			for (int l = 0; l < largo; l++){
				if ((l % 2) == 0) {
					posY++;
				} 
				paintTile( posX, posY, tile );
			}
		}
	}
	
	/**
	 * paint a tile on designed row and column
	 * @param x
	 * @param y
	 * @param tile
	 * @return
	 */
	public static void paintTile(int fila, int columna, Bitmap tile){
		Elemento nuevo;
		if (fila % 2 == 0){
			 nuevo = new Elemento(tile, columna * 64, fila * 32);
		}else{
			 nuevo = new Elemento(tile,(( columna * 64) + 32), (fila * 32) + 16);
		}
		ObjectMapper.addElemenToFloor(nuevo);
	}
}
