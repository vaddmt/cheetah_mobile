package cheetah.cheetah_GUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import cheetah.cheetah_MGR.Globals;
import cheetah.cheetah_MGR.Messenger;

public class ViewGraphics extends View {

    private Paint  paint;
    private int    xPos = 0, yPos = 0;

    public ViewGraphics(Context context) {
        super(context);
        init(context);
    }

    public ViewGraphics(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    public ViewGraphics(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init(context);
    }

    public void throwGraphics(String text, int r, int g, int b) {
        Globals.drawAlpha = 255;
        Globals.drawColor = Color.argb(Globals.drawAlpha, r, g, b);
        Globals.drawMessage = text;
    }

    private void init(Context context) {
        this.setWillNotDraw(false);
        try {
            paint = new Paint();
            paint.setTextSize(120);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setShadowLayer(50, 0, 0, Globals.drawColor);
            paint.setStrokeMiter(1);
        }
        catch (OutOfMemoryError ex) {
            Messenger.showError("ERROR:", ex);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(Globals.drawAlpha > 0) {
            xPos = (canvas.getWidth() / 2);
            yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2) - canvas.getHeight() / 5);

            paint.setARGB(Globals.drawAlpha, 0, 0, 0);
            canvas.drawRect(0, yPos - 5, canvas.getWidth(), yPos + (paint.descent() + paint.ascent()) + 5, paint);

            paint.setARGB(Globals.drawAlpha,
                          Color.red(Globals.drawColor),
                          Color.green(Globals.drawColor),
                          Color.blue(Globals.drawColor));
            canvas.drawText(Globals.drawMessage, xPos, yPos, paint);

            Globals.drawAlpha -= 5; // changing from 255 to 0
            invalidate();
        }
    }
}
