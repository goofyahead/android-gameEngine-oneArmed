package es.nxtlink;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import es.nxtlink.modelos.Elemento;
import es.nxtlink.persistence.ObjectMapper;
import MapBuilder.FloorBuilder;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class OneArmedActivity extends Activity {
	//FIELDS
	private LinearLayout frameGame;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //request for no title bar, notification bar will still appear.
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.main);
        
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        initMapa();
        
        frameGame = (LinearLayout) findViewById(R.id.gameplay);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        GameEngine game = new GameEngine(getApplicationContext(), 0, 0, dm);
        
        frameGame.addView(game);
    }

	private void initMapa() {
		//creamos elementos en el mapa
		Elemento piedra = new Elemento(BitmapFactory.decodeResource(getResources(), R.drawable.allchemy_stone),790,190);
		ObjectMapper.addObjects(piedra);
		Elemento contrario = new Elemento(BitmapFactory.decodeResource(getResources(), R.drawable.bender),560,90);
		ObjectMapper.addPlayer(contrario);
		Bitmap suelonormal = BitmapFactory.decodeResource(getResources(), R.drawable.tile1);
		
		//fila 1
		FloorBuilder.paintTile(0, 0, suelonormal);
		FloorBuilder.paintTile(0, 1, suelonormal);
		FloorBuilder.paintTile(2, 1, suelonormal);
		FloorBuilder.paintTile(2, 2, suelonormal);
		FloorBuilder.paintTile(3, 1, suelonormal);
		FloorBuilder.paintTile(3, 2, suelonormal);
		
//		
//		//fila 2
//		FloorBuilder.paintTile(1, 0, suelonormal);
//		FloorBuilder.paintTile(1, 1, suelonormal);
//		FloorBuilder.paintTile(1, 2, suelonormal);
//		FloorBuilder.paintTile(1, 3, suelonormal);
		
//		FloorBuilder.CreateRoom(1, 0, 2, 8, suelonormal);
		
	}
}