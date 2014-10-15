package cheetah.cheetah_GUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class CheetahImageButton extends ImageButton {

    private String[] text;
    private Paint    paint;
    private int      textSize = 32;
    private int      xPos = 200, yPos = 0;

    public CheetahImageButton(Context context) {
        super(context);
        Init();
    }

    public CheetahImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public CheetahImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init();
    }

    private void Init() {
        text = new String[3];
        text[0] = "";
        text[1] = "";
        text[2] = "";
        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    public void setText(String s, int i) {
        text[i] = s;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        yPos = (int) (paint.descent() - paint.ascent());
        paint.setColor(Color.RED);
        for(int i = 0; i < text.length; i++) {
            canvas.drawText(text[i], xPos, yPos, paint);
            paint.setColor(Color.YELLOW);
            yPos += textSize;
        }
    }
}
