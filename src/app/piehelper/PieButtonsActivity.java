package app.piehelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import app.piehelper.PieButtonsView.onPieClickListener;

public class PieButtonsActivity extends Activity implements onPieClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pie_buttons_fragment);

		PieButtonsView pieView = (PieButtonsView) findViewById(R.id.pie_button_view);
		pieView.setOnPieClickListener(this);
	}

	@Override
	public void onPieClick(View view, int position) {

		Toast.makeText(this, "Clicked button: " + position, Toast.LENGTH_SHORT)
				.show();
	}

}
