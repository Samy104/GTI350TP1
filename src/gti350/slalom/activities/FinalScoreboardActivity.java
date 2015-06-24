package gti350.slalom.activities;

import gti350.slalom.R;
import gti350.slalom.models.Contestant;
import gti350.slalom.models.ContestantList;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FinalScoreboardActivity extends Activity implements OnClickListener {
	ListView listView;
	Button buttonBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_scoreboard);
		
		// find controls
		buttonBack = (Button)findViewById(R.id.buttonBack);
		
		// fill list view
		fillTopThreeView();
		
		// add listeners
		buttonBack.setOnClickListener(this);
	}
	
	private void fillTopThreeView() {
		ArrayList<Contestant> top = ContestantList.getInstance().getTopContestants();
		ImageView qImageViewSilver = (ImageView)findViewById(R.id.imageViewSilverMedal);
		ImageView qImageViewBronze = (ImageView)findViewById(R.id.imageViewBronzeMedal);
		
		// Top 1 concurrent
		if (top.size() > 0) {
			((TextView)findViewById(R.id.textViewTop1)).setText(top.get(0).toString());
		}
		else {
			((TextView)findViewById(R.id.textViewTop1)).setText(R.string.tbd);
		}
		
		// Top 2 concurrent
		if (top.size() > 1) {
			((TextView)findViewById(R.id.textViewTop2)).setText(top.get(1).toString());
		}
		else {
			((TextView)findViewById(R.id.textViewTop2)).setText(R.string.tbd);
		}
		
		// Top 3 concurrent
		if (top.size() > 2) {
			((TextView)findViewById(R.id.textViewTop3)).setText(top.get(2).toString());
		}
		else {
			((TextView)findViewById(R.id.textViewTop3)).setText(R.string.tbd);
		}
		
		// Image change
		
		// Case 1. (1, 2 and 3 are identical)
		if(top.size() >= 3 && (top.get(0).getBestTime() == top.get(1).getBestTime() && top.get(1).getBestTime() == top.get(2).getBestTime())) {
			qImageViewSilver.setImageResource(R.drawable.gold_medal);
			qImageViewBronze.setImageResource(R.drawable.gold_medal);
			
		}
		// Case 2. (1 and 2 are identical)
		else if (top.size() >= 2 && (top.get(0).getBestTime() == top.get(1).getBestTime())) {
			qImageViewSilver.setImageResource(R.drawable.gold_medal);
			qImageViewBronze.setImageResource(R.drawable.bronze_medal);
		}
		// Case 3. (2 and 3 are identical)
		else if (top.size() >= 3 && (top.get(1).getBestTime() == top.get(2).getBestTime())) {
			qImageViewSilver.setImageResource(R.drawable.silver_medal);
			qImageViewBronze.setImageResource(R.drawable.silver_medal);
		}
		else {
			qImageViewBronze.setImageResource(R.drawable.bronze_medal);
			qImageViewSilver.setImageResource(R.drawable.silver_medal);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.final_scoreboard, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		ContestantList.getInstance().clear();
		
		Intent i = new Intent(getBaseContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
	}
	
	@Override
	public void onClick(View arg0) {
		if (arg0 == buttonBack) {
			ContestantList.getInstance().clear();
			
			Intent i = new Intent(getBaseContext(), MainActivity.class);
	        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	        startActivity(i);
		}
	}
}
