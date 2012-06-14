package es.nxtlink;
/**
 * this class specifies the fixed rate of updates of the screen
 */
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

class UiThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private GameEngine _panel;
    private boolean _run = false;
    private final int FRAMES_PER_SECOND = 30;
    private final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    public UiThread(SurfaceHolder surfaceHolder, GameEngine panel) {
        _surfaceHolder = surfaceHolder;
        _panel = panel;
    }

    public void setRunning(boolean run) {
        _run = run;
    }

    public SurfaceHolder getSurfaceHolder() {
        return _surfaceHolder;
    }

    @Override
    public void run() {
        Canvas c;
        long elapsed = System.currentTimeMillis();
        long sleep_time;
        long last = 0;
        while (_run) {
//        	Log.d("MI_DEP","im alive!");
            c = null;
            last = System.currentTimeMillis();
            try {
                c = _surfaceHolder.lockCanvas(null);
               synchronized (_surfaceHolder) {
                	//update positions
                	_panel.updateObjects();
                    _panel.onDraw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    _surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            
            elapsed = System.currentTimeMillis() - last;
            sleep_time =  SKIP_TICKS - elapsed;
            if (sleep_time > 0){
            	try {
					Thread.sleep(sleep_time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            
        }
    }
}
