package es.nxtlink;

import java.util.List;

import es.nxtlink.com.ComunicationIn;
import es.nxtlink.modelos.Elemento;
import es.nxtlink.persistence.ObjectMapper;
import es.nxtlink.utils.Values;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView implements SurfaceHolder.Callback {
	private Context context;
	private List<Elemento> suelo;
	private int originX;
	private int originY;
	private int screenHeight;
	private int screenWidth;
	private UiThread _thread;
	private Elemento pintame;
	private Elemento dPad;
	private int _x;
	private int _y;
	private int DISTANCE_PAD_FROM_TOP;
	private int direction;
	private Elemento jugador;
	private Bitmap zoidDere;
	private Bitmap zoidIzda;
	private Elemento contrario;
	private ComunicationIn _threadCom = new ComunicationIn();

	public GameEngine(Context context, int originX,
			int originY, DisplayMetrics dm) {
		super(context);
		Log.d("MI_DEP", "creado game engine");
		this.context = context;
		this.suelo = ObjectMapper.getSuelo();
		this.originX = originX;
		this.originY = originY;
		this.contrario = ObjectMapper.getContrario();
		
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;
		direction = Values.CENTRO;
		
		//creamos los zoidbergs
		zoidDere = BitmapFactory.decodeResource(getResources(), R.drawable.zoidbergdere);
		zoidIzda = BitmapFactory.decodeResource(getResources(), R.drawable.zoidbergizda);

		// creamos el pad
		dPad = new Elemento(BitmapFactory.decodeResource(getResources(),
				R.drawable.ui_dpad), 0, 0);
		DISTANCE_PAD_FROM_TOP = screenHeight - dPad.getSizeY();
		dPad.setY(DISTANCE_PAD_FROM_TOP);
		
		jugador = new Elemento(zoidDere, 0 , 0);
		jugador.setX((screenWidth / 2) - (jugador.getSizeX()/2));
		jugador.setY((screenHeight / 2) - (jugador.getSizeY() /2));

		getHolder().addCallback(this);
		_thread = new UiThread(getHolder(), this);
	}

	@Override
	public void onDraw(Canvas canvas) {
		
		// paint background
		canvas.drawColor(Color.BLACK);
		
		// paint suelo
		for (int x = 0; x < suelo.size(); x++) {
			pintame = suelo.get(x);
			if (((pintame.getX() > originX) && (pintame.getX() < originX
					+ screenWidth))
					&& (pintame.getY() > originY && pintame.getY() < originY
							+ screenHeight))
				canvas.drawBitmap(pintame.getImagen(),
						pintame.getX() - originX, pintame.getY() - originY,
						null);
		}
		
		// paint player
		canvas.drawBitmap(jugador.getImagen(), jugador.getX(), jugador.getY(), null);
		
		// paint d pad
		canvas.drawBitmap(dPad.getImagen(), dPad.getX(), dPad.getY(), null);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			_threadCom.start();
			_thread.setRunning(true);
			_thread.start();
		} catch (Exception ex) {
			_thread = new UiThread(getHolder(), this);
			_thread.setRunning(true);
			_thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		_thread.setRunning(false);
	}

	public void stopThread() {
		_thread.setRunning(false);
	}

	public void updateObjects() {

		switch (direction) {

		case Values.ARRIBA_IZDA:
			jugador.setImagen(zoidIzda);
			originX--;
			originY--;
			break;
		case Values.IZDA:
			jugador.setImagen(zoidIzda);
			originX--;
			break;
		case Values.ABAJO_IZDA:
			jugador.setImagen(zoidIzda);
			originX--;
			originY++;
			break;
		case Values.ARRIBA:
			originY--;
			break;
		case Values.CENTRO:
			break;
		case Values.ABAJO:
			originY++;
			break;
		case Values.ARRIBA_DERE:
			jugador.setImagen(zoidDere);
			originX++;
			originY--;
			break;
		case Values.DERE:
			jugador.setImagen(zoidDere);
			originX++;
			break;
		case Values.ABAJO_DERE:
			jugador.setImagen(zoidDere);
			originX++;
			originY++;
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get x & y coordinates of the touch.
		_x = (int) event.getX();
		_y = (int) event.getY();

		int eventAction = event.getAction();

		switch (eventAction) {

		case MotionEvent.ACTION_DOWN:

			if ((_y > DISTANCE_PAD_FROM_TOP) && (_x < dPad.getSizeX())) {

				if (_x < dPad.getSizeX() / 3) {
					if (_y < (dPad.getSizeY() / 3) + DISTANCE_PAD_FROM_TOP) {
						// diagonal arriba izda
						direction = Values.ARRIBA_IZDA;
						Log.d("MI_DEP", "diagonal arriba izda");
					} else if (_y < ((dPad.getSizeY() / 3) * 2)
							+ DISTANCE_PAD_FROM_TOP) {
						// movimiento izda
						direction = Values.IZDA;
						Log.d("MI_DEP", "izda");
					} else {
						// diagonal abajo izda
						direction = Values.ABAJO_IZDA;
						Log.d("MI_DEP", "diagonal abajo izda");
					}
				} else if (_x < ((dPad.getSizeX() / 3) * 2)) {
					if (_y < (dPad.getSizeY() / 3) + DISTANCE_PAD_FROM_TOP) {
						// arriba
						direction = Values.ARRIBA;
						Log.d("MI_DEP", "arriba");
					} else if (_y < ((dPad.getSizeY() / 3) * 2)
							+ DISTANCE_PAD_FROM_TOP) {
						direction = Values.CENTRO;
						// centro NADA

					} else {
						// abajo
						direction = Values.ABAJO;
						Log.d("MI_DEP", "abajo");
					}
				} else {
					if (_y < (dPad.getSizeY() / 3) + DISTANCE_PAD_FROM_TOP) {
						// arriba dere
						direction = Values.ARRIBA_DERE;
						Log.d("MI_DEP", "diagonal arriba dere");
					} else if (_y < ((dPad.getSizeY() / 3) * 2)
							+ DISTANCE_PAD_FROM_TOP) {
						// dere
						direction = Values.DERE;
						Log.d("MI_DEP", "dere");
					} else {
						// abajo dere
						direction = Values.ABAJO_DERE;
						Log.d("MI_DEP", "diagonal abajo dere");
					}
				}
			}
			break;
		}
		return false;
	}
}
