package app.piehelper;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;


public class PieHelper {

	public static boolean isInCircle(float x, float y) {
		double dx = Math.pow((x - PieButtonsView.mCircleCenterX), 2);
		double dy = Math.pow((y - PieButtonsView.mCircleCenterY), 2);

		if ((dx + dy) < Math.pow(PieButtonsView.mCircleRadius, 2))
			return true;

		return false;
	}

	public static int whichButton(float x, float y) {
		int angle = (int) getAngle(x, y);
		angle = 360 - angle;

		int tempAngle = 0;
		int selectedPie = 0;

		for (int i = 1; i <= PieButtonsView.PIE_PIECES; i++) {
			if (angle >= tempAngle
					&& angle < (tempAngle + PieButtonsView.PIE_PIECE_ANGLE)) {
				selectedPie = i;
				break;
			} else {
				tempAngle = tempAngle + PieButtonsView.PIE_PIECE_ANGLE;
			}
		}

		return selectedPie;
	}

	public static double getAngle(float xTouch, float yTouch) {
		double x = xTouch - PieButtonsView.mCircleCenterX;
		double y = PieButtonsView.mCircleCenterY - yTouch;

		switch (getQuadrant(x, y)) {
		case 1:
			return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
		case 2:
		case 3:
			return 180 - (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
		case 4:
			return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
		default:
			// ignore, does not happen
			return 0;
		}
	}
	
	public static int getQuadrant(double x, double y) {
		// Log.e(TAG, "(" + x + ", " + y + ")");
		if (x >= 0) {
			return y >= 0 ? 1 : 4;
		} else {
			return y >= 0 ? 2 : 3;
		}
	}
	
	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm;
	}
}
