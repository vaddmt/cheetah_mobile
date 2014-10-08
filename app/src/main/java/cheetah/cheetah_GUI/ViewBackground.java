package cheetah.cheetah_GUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ViewBackground extends View {

    WindowManager windowManager;
    Display display;
    int displayWidth, displayHeight;
    Paint paint;

    public ViewBackground(Context context) {
        super(context);
        init(context);
    }

    public ViewBackground(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    public ViewBackground(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init(context);
    }

    private void init(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth  = size.x;
        displayHeight = size.y;
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < displayHeight; i += 5) {
            canvas.drawLine(0, i, displayWidth, i, paint);
        }
    }
}
