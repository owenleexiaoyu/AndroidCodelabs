package life.lixiaoyu.catchthatcat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CatchCatView extends View {
    private static final String TAG = "CatchCatView";
    private int viewSize;//view的尺寸，取屏幕宽高中小的那个值

    private CatchCatData mData;
    private int N;   //一行中圆的个数
    private int radius;  //圆的半径
    private int paddingTop;//顶部空白的尺寸
    private Paint emptyCirclePaint;
    private Paint blockCirclePaint;
    private Paint catCirclePaint;
    private Bitmap catBitmap;

    public CatchCatView(Context context) {
        this(context, null);
    }

    public CatchCatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CatchCatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mData = new CatchCatData();
        N = mData.getN();
        //初始化画笔
        emptyCirclePaint = new Paint();
        emptyCirclePaint.setColor(getResources().getColor(R.color.light_blue));
        emptyCirclePaint.setAntiAlias(true);
        emptyCirclePaint.setStyle(Paint.Style.FILL);

        blockCirclePaint = new Paint();
        blockCirclePaint.setColor(getResources().getColor(R.color.dark_blue));
        blockCirclePaint.setAntiAlias(true);
        blockCirclePaint.setStyle(Paint.Style.FILL);

        catCirclePaint = new Paint();
        catCirclePaint.setColor(getResources().getColor(R.color.colorAccent));
        catCirclePaint.setAntiAlias(true);
        catCirclePaint.setStyle(Paint.Style.FILL);
        // TODO draw a cat image
//        catBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Log.d(TAG, "onDraw: width=" + width + " height=" + height);
        viewSize = width < height ? width : height;
        radius = viewSize / (N * 2 + 5);
        paddingTop = (viewSize - radius * N) / 2;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                drawCircles(canvas, i, j);
            }
        }
    }

    /**
     * 绘制圆圈
     *
     * @param canvas
     * @param i
     * @param j
     */
    private void drawCircles(Canvas canvas, int i, int j) {
        if (i % 2 == 0) {
            if (mData.getGameData()[i][j] == CatchCatData.TYPE_BLOCKED) {
                canvas.drawCircle((3 + 2 * j) * radius, paddingTop + radius * (i * 2 + 1),
                        radius - 2, blockCirclePaint);
            } else if (mData.getGameData()[i][j] == CatchCatData.TYPE_CAT) {
                canvas.drawCircle((3 + 2 * j) * radius, paddingTop + radius * (i * 2 + 1),
                        radius - 2, catCirclePaint);
            } else {
                canvas.drawCircle((3 + 2 * j) * radius, paddingTop + radius * (i * 2 + 1),
                        radius - 2, emptyCirclePaint);
            }
        } else {
            if (mData.getGameData()[i][j] == CatchCatData.TYPE_BLOCKED) {
                canvas.drawCircle((4 + 2 * j) * radius, paddingTop + radius * (i * 2 + 1),
                        radius - 2, blockCirclePaint);
            } else if (mData.getGameData()[i][j] == CatchCatData.TYPE_CAT) {
                canvas.drawCircle((4 + 2 * j) * radius, paddingTop + radius * (i * 2 + 1),
                        radius - 2, catCirclePaint);
            } else {
                canvas.drawCircle((4 + 2 * j) * radius, paddingTop + radius * (i * 2 + 1),
                        radius - 2, emptyCirclePaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            //根据获得的屏幕坐标来计算当前点击的是哪个圆圈
            calculatePosition(x, y);
        }
        return true;
    }

    private void calculatePosition(float x, float y) {
        float dy = y - paddingTop;
        if (dy < 0) {
            return;
        }
        int iPosition = (int) (dy / (2 * radius));
        if (iPosition >= N) {
            return;
        }
        int jPosition;

        if (iPosition % 2 == 0) {
            float dx = x - 2 * radius;
            if (dx < 0) {
                return;
            }
            jPosition = (int) (dx / (2 * radius));
        } else {
            float dx = x - 3 * radius;
            if (dx < 0) {
                return;
            }
            jPosition = (int) (dx / (2 * radius));
        }
        if (jPosition >= N) {
            return;
        }
        //修改data数据
        if (mData.isBlockedInPosition(iPosition, jPosition)) {
            Toast.makeText(getContext(), "当前位置已经是墙了，不可点击", Toast.LENGTH_SHORT).show();
        } else if (mData.isCatInPosition(iPosition, jPosition)) {
            Toast.makeText(getContext(), "当前位置是猫，不可点击", Toast.LENGTH_SHORT).show();
        } else {
            mData.setBlocked(iPosition, jPosition);
            //猫逃跑
            mData.catEscape();
            //重绘界面
            invalidate();
//            Toast.makeText(getContext(), "点击了：【" + iPosition + "，" + jPosition + "】", Toast.LENGTH_SHORT).show();
            int result = mData.checkGameResult();
            switch (result) {
                case CatchCatData.RESULT_CONTINUE:
                    break;
                case CatchCatData.RESULT_WIN:
                    Toast.makeText(getContext(), "你赢啦，哈哈哈", Toast.LENGTH_SHORT).show();
                    break;
                case CatchCatData.RESULT_LOSE:
                    Toast.makeText(getContext(), "猫逃跑了，你输了，呜呜呜", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
