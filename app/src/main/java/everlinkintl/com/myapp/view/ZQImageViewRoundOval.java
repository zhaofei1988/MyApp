package everlinkintl.com.myapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 *    private  ZQImageViewRoundOval iv_circle;//圆形图片
 * 13     private ZQImageViewRoundOval iv_roundRect;//圆角矩形图片
 * 14     private ZQImageViewRoundOval iv_oval;//椭圆图片
 *   iv_circle =(ZQImageViewRoundOval)findViewById(R.id.cicle);
 * 26       iv_roundRect =(ZQImageViewRoundOval)findViewById(R.id.roundRect);
 * 27       iv_oval =(ZQImageViewRoundOval)findViewById(R.id.oval);
 * 28
 * 29       iv_circle  默认圆形，不需要设置
 * 30       iv_roundRect.setType(ZQImageViewRoundOval.TYPE_ROUND);
 * 31       iv_roundRect.setRoundRadius(6);//矩形凹行大小
 * 32
 * 33        iv_oval.setType(ZQImageViewRoundOval.TYPE_OVAL);
 * 34        iv_oval.setRoundRadius(45);//圆角大小
 */
public class ZQImageViewRoundOval extends AppCompatImageView {
    private Paint mPaint;

    private int mWidth;

    private int mHeight;

    private int mRadius;//圆半径

    private RectF mRect;//矩形凹行大小

    private int mRoundRadius;//圆角大小

    private BitmapShader mBitmapShader;//图形渲染

    private Matrix mMatrix;

    private int mType;//记录是圆形还是圆角矩形

    public static final int TYPE_CIRCLE = 0;//圆形
    public static final int TYPE_ROUND = 1;//圆角矩形
    public static final int TYPE_OVAL = 2;//椭圆形
    public static final int DEFAUT_ROUND_RADIUS = 10;//默认圆角大小

    public ZQImageViewRoundOval(Context context) {
        this(context, null);
        // TODOAuto-generated constructor stub
    }

    public ZQImageViewRoundOval(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODOAuto-generated constructor stub
    }

    public ZQImageViewRoundOval(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();
        mRoundRadius = DEFAUT_ROUND_RADIUS;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODOAuto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 如果是绘制圆形，则强制宽高大小一致
        if (mType == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (null == getDrawable()) {
            return;
        }
        setBitmapShader();
        if (mType == TYPE_CIRCLE) {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        } else if (mType == TYPE_ROUND) {
            mPaint.setColor(Color.RED);
            canvas.drawRoundRect(mRect, mRoundRadius, mRoundRadius, mPaint);
        } else if (mType == TYPE_OVAL) {
            canvas.drawOval(mRect, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODOAuto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mRect = new RectF(0, 0, getWidth(), getHeight());
    }

    /**
     * 设置BitmapShader
     */
    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        if (null == drawable) {
            return;
        }
        Bitmap bitmap = drawableToBitmap(drawable);
        // 将bitmap作为着色器来创建一个BitmapShader
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (mType == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
            scale = mWidth * 1.0f / bSize;

        } else if (mType == TYPE_ROUND || mType == TYPE_OVAL) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);

    }

    /**
     * 120      * drawable转bitmap
     * 121      *
     * 122      * @paramdrawable
     * 123      * @return
     * 124
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public int getType() {
        return mType;
    }

    /**
     * 151      * 设置图片类型：圆形、圆角矩形、椭圆形
     * 152      * @param mType
     * 153
     */
    public void setType(int mType) {
        if (this.mType != mType) {
            this.mType = mType;
            invalidate();
        }

    }

    public int getRoundRadius() {
        return mRoundRadius;
    }

    /**
     * 设置圆角大小
     *
     * @parammRoundRadius
     */
    public void setRoundRadius(int mRoundRadius) {
        if (this.mRoundRadius != mRoundRadius) {
            this.mRoundRadius = mRoundRadius;
            invalidate();
        }

    }
}