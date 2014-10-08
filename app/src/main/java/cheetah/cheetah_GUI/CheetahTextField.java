package cheetah.cheetah_GUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import cheetah.cheetah_mobile.R;

public class CheetahTextField extends TextView {

    private Paint paintFirst;
    private int width, height;

    public CheetahTextField(Context context) {
        super(context);
        init();
    }

    public CheetahTextField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheetahTextField(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        this.setBackgroundResource(R.drawable.background_light);
    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom )
    {
        super.onLayout( changed, left, top, right, bottom );
        if(changed)
        {
            getPaint().setShader( new LinearGradient(
                    0, 0, 0, getHeight(),
                    Color.WHITE, Color.GRAY,
                    Shader.TileMode.CLAMP ) );
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
