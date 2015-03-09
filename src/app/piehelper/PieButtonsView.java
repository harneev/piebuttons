package app.piehelper;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PieButtonsView extends View {

	public final static int PIE_PIECES = 3;
	public final static int PIE_PIECE_ANGLE = 360 / PIE_PIECES;

	public static int mCircleRadius = 0, mCircleCenterY = 0,
			mCircleCenterX = 0;
	private Context mContext;
	private Paint mPaint;

	private int mSelectedPie = 0;

	// Event listeners
	private onPieClickListener mOnPieClickListener = null;

	private void init(AttributeSet attrs) {
		mCircleRadius = getDisplayMetrics().widthPixels / 4;
		mCircleCenterX = getDisplayMetrics().widthPixels / 2;
		mCircleCenterY = getDisplayMetrics().heightPixels / 2 - mCircleRadius
				/ 2;

		// Initialize Paint
		mPaint = new Paint();

		mPaint.setAntiAlias(true); // smooths
		mPaint.setStrokeWidth(15.0f);

		mPaint.setMaskFilter(new BlurMaskFilter(15, Blur.NORMAL));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Log.e("Harneev", "ondraw");

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.GRAY);
		canvas.drawCircle(mCircleCenterX, mCircleCenterY, mCircleRadius, mPaint);

		drawPieButtons(canvas);
	}

	private void drawPieButtons(Canvas canvas) {
		int rot = 0;
		for (int i = 1; i <= PIE_PIECES; i++) {
			setPaint(i);
			canvas.drawArc(getRectF(), rot, PIE_PIECE_ANGLE, true, mPaint);
			rot = rot + PIE_PIECE_ANGLE;
		}
	}

	private RectF getRectF() {
		RectF rectF;
		rectF = new RectF(mCircleCenterX - mCircleRadius, mCircleCenterY
				- mCircleRadius, mCircleCenterX + mCircleRadius, mCircleCenterY
				+ mCircleRadius);
		return rectF;
	}

	private void setPaint(int i) {
		mPaint.setStyle(Paint.Style.FILL);

		switch (i) {
		case 1:
			if (mSelectedPie == 1)
				mPaint.setColor(Color.YELLOW);
			else
				mPaint.setColor(Color.RED);
			break;
		case 2:
			if (mSelectedPie == 2)
				mPaint.setColor(Color.YELLOW);
			else
				mPaint.setColor(Color.GREEN);
			break;
		case 3:
			if (mSelectedPie == 3)
				mPaint.setColor(Color.YELLOW);
			else
				mPaint.setColor(Color.BLUE);
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float x = event.getX();
			float y = event.getY();

			if (!PieHelper.isInCircle(x, y))
				break;

			mSelectedPie = PieHelper.whichButton(x, y);

			if (mSelectedPie != 0) {
				if (this.mOnPieClickListener != null) {

					this.mOnPieClickListener.onPieClick(null, mSelectedPie);

				}
			}

			invalidate();
			return true;

		case MotionEvent.ACTION_UP:
			mSelectedPie = 0;
			invalidate();
			return true;
		}

		return false;
	}

	private DisplayMetrics getDisplayMetrics() {
		return PieHelper.getDisplayMetrics(mContext);
	}
	
	public PieButtonsView(Context context) {
		this(context, null);
		mContext = context;
	}

	public PieButtonsView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		mContext = context;
	}

	public PieButtonsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init(attrs);
	}

	public interface onPieClickListener {
		void onPieClick(View view, int position);
	}

	public void setOnPieClickListener(onPieClickListener onPieClick) {
		this.mOnPieClickListener = onPieClick;
	}

}
