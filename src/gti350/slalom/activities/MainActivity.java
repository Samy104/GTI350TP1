package gti350.slalom.activities;

import gti350.slalom.R;
import gti350.slalom.models.Contestant;
import gti350.slalom.models.ContestantList;
import gti350.slalom.models.exceptions.ContestantAlreadyExistsException;
import gti350.slalom.utils.TimeFormat;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	// missed doors number picker
	private EditText editTextMissedDoors;
	
	// layouts
	private LinearLayout layoutChrono;
	private LinearLayout layoutDnfQuestion;
	private LinearLayout layoutMissedDoors;
	private LinearLayout layoutDnf;
	private LinearLayout layoutDq;
	private LinearLayout layoutScoreboard;
	
	// links
	private TextView textViewAllRunsLink;
	private TextView textViewAllContestantsLink;
	private TextView textTimer;
	
	// buttons
	private Button buttonChrono;
	private Button buttonDnfQuestionYes;
	private Button buttonDnfQuestionNo;
	private Button buttonMissedDoors;
	private Button buttonDnf;
	private Button buttonDq;
	private Button buttonScoreboard;
	private Button buttonAddContestant;
	
	// chrono
	private boolean chronoStarted = false;
	private Handler myHandler = new Handler();
	private long startTime = 0L;
	private long timeInMillis = 0L;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Find controls
		layoutChrono = (LinearLayout)findViewById(R.id.layoutChrono);
		layoutDnfQuestion = (LinearLayout)findViewById(R.id.layoutDnfQuestion);
		layoutMissedDoors = (LinearLayout)findViewById(R.id.layoutMissedDoors);
		layoutDnf = (LinearLayout)findViewById(R.id.layoutDNF);
		layoutDq = (LinearLayout)findViewById(R.id.layoutDQ);
		layoutScoreboard = (LinearLayout)findViewById(R.id.layoutScoreboard);
		
		buttonChrono = (Button)findViewById(R.id.buttonChrono);
		buttonDnfQuestionYes = (Button)findViewById(R.id.buttonDNFQuestionYes);
		buttonDnfQuestionNo = (Button)findViewById(R.id.buttonDNFQuestionNo);
		buttonMissedDoors = (Button)findViewById(R.id.buttonMissedDoors);
		buttonDnf = (Button)findViewById(R.id.buttonDNFNextContestant);
		buttonDq = (Button)findViewById(R.id.buttonDQNextContestant);
		buttonScoreboard = (Button)findViewById(R.id.buttonScoreboard);
		buttonAddContestant = (Button)findViewById(R.id.buttonAddContestant);
		
		editTextMissedDoors = (EditText)findViewById(R.id.editTextMissedDoors);

		textViewAllRunsLink = (TextView)findViewById(R.id.textViewAllRunsLink);
		textViewAllContestantsLink = (TextView)findViewById(R.id.textViewAllContestantsLink);
		textTimer = (TextView)findViewById(R.id.textViewTimer);
		
		// Set the color of the (view all runs) and (view all contestants) to blue
		textViewAllRunsLink.setTextColor(Color.BLUE);
		textViewAllContestantsLink.setTextColor(Color.BLUE);
		
		// Set the text (view all runs) and (view all contestants) to clickable
		textViewAllRunsLink.setClickable(true);
		textViewAllContestantsLink.setClickable(true);
		
		// Add listeners
		buttonChrono.setOnClickListener(this);
		buttonDnfQuestionYes.setOnClickListener(this);
		buttonDnfQuestionNo.setOnClickListener(this);
		buttonMissedDoors.setOnClickListener(this);
		buttonDnf.setOnClickListener(this);
		buttonDq.setOnClickListener(this);
		buttonScoreboard.setOnClickListener(this);
		buttonAddContestant.setOnClickListener(this);
		textViewAllRunsLink.setOnClickListener(this);
		textViewAllContestantsLink.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		this.updateCurrentContestantUI();
		this.updateNext2RunsUI();
		this.updateCurrentTop3UI();
	}
	
	private void updateCurrentContestantUI() {
		ArrayList<Contestant> runs = ContestantList.getInstance().getRemainingRuns();
		
		if (runs.size() > 0) {
			((TextView)findViewById(R.id.textViewCurrent)).setText(runs.get(0).toString());
			buttonChrono.setEnabled(true);
		}
		else {
			((TextView)findViewById(R.id.textViewCurrent)).setText(R.string.tbd);
			buttonChrono.setEnabled(false);
		}
	}
	
	private void updateNext2RunsUI() {
		ArrayList<Contestant> runs = ContestantList.getInstance().getRemainingRuns();
		
		// First next run
		if (runs.size() > 1) {
			((TextView)findViewById(R.id.textViewNextRun1)).setText(runs.get(1).toString());
		}
		else {
			((TextView)findViewById(R.id.textViewNextRun1)).setText(R.string.tbd);
		}
		
		// Second next run
		if (runs.size() > 2) {
			((TextView)findViewById(R.id.textViewNextRun2)).setText(runs.get(2).toString());
		}
		else {
			((TextView)findViewById(R.id.textViewNextRun2)).setText(R.string.tbd);
		}
	}
	
	private void updateCurrentTop3UI() {
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
	
	/*
	 * We use this instead of the chronometer widget.
	 * I took the code here : http://androidituts.com/android-stopwatch-example/
	 */
	private Runnable updateTimerMethod = new Runnable() {
		public void run() {
			timeInMillis = SystemClock.uptimeMillis() - startTime;
			int seconds = (int) (timeInMillis / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			int milliseconds = (int) (timeInMillis % 1000);
			textTimer.setText("" + minutes + ":"
			+ String.format("%02d", seconds) + ":"
			+ String.format("%03d", milliseconds));
			myHandler.postDelayed(this, 0);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		int missedDoors;
		
		/*
		 * Chronometer layout.
		 */
		if (arg0 == buttonChrono) {
			if (!chronoStarted) {
				startTime = SystemClock.uptimeMillis();
				myHandler.postDelayed(updateTimerMethod, 0);
				
				chronoStarted = true;
				buttonChrono.setText(R.string.stop_timer);
			}
			else {
				myHandler.removeCallbacks(updateTimerMethod);
				
				chronoStarted = false;
				buttonChrono.setText(R.string.start_timer);
				
				layoutChrono.setVisibility(LinearLayout.GONE);
				layoutDnfQuestion.setVisibility(LinearLayout.VISIBLE);
			}
		}
		
		/*
		 * DNF Question layout.
		 */
		else if (arg0 == buttonDnfQuestionYes) {
			layoutDnfQuestion.setVisibility(LinearLayout.GONE);
			layoutMissedDoors.setVisibility(LinearLayout.VISIBLE);
		}
		
		else if (arg0 == buttonDnfQuestionNo) {
			layoutDnfQuestion.setVisibility(LinearLayout.GONE);
			
			// Decrease the amount of runs left of the current user.
			Contestant current = ContestantList.getInstance().getRemainingRuns().get(0);
			current.decreaseRunsLeft();
			
			((TextView)findViewById(R.id.textViewDNF)).setText("The contestant did not finish (DNF)");
			layoutDnf.setVisibility(LinearLayout.VISIBLE);
		}
		
		/*
		 * Missed doors layout
		 */
		else if (arg0 == buttonMissedDoors) {
			layoutMissedDoors.setVisibility(LinearLayout.GONE);
			
			// Hide the soft keyboard
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editTextMissedDoors.getWindowToken(), 0);
			
			if (!"".equals(editTextMissedDoors.getText().toString())) {
				missedDoors = Integer.parseInt(editTextMissedDoors.getText().toString());
			}
			else {
				missedDoors = 0;
			}
			
			if (missedDoors > 2) {
				layoutDq.setVisibility(LinearLayout.VISIBLE);
				
				// Update the current contestant
				Contestant current = ContestantList.getInstance().getRemainingRuns().get(0);
				current.decreaseRunsLeft();
				
				((TextView)findViewById(R.id.textViewDQ)).setText("The contestant has been disqualified (DQ) for hitting " + missedDoors + " doors. Contestants must hit less than 3 doors.");
			}
			else {
				layoutScoreboard.setVisibility(LinearLayout.VISIBLE);
				
				// Set up values for the scoreboard
				long penalties = missedDoors * 30 * 1000;
				long timeAfterPenalties = timeInMillis + penalties;
				TimeFormat tf = new TimeFormat();
				
				// Update the current contestant
				Contestant current = ContestantList.getInstance().getRemainingRuns().get(0);
				current.setTime(timeAfterPenalties);
				current.decreaseRunsLeft();
				
				// Update the fields
				((TextView)findViewById(R.id.textViewTimeBeforePenalties)).setText(tf.format(timeInMillis));
				((TextView)findViewById(R.id.textViewMissedDoors)).setText(String.valueOf(missedDoors));
				((TextView)findViewById(R.id.textViewPenaltyTime)).setText(tf.format(penalties));
				((TextView)findViewById(R.id.textViewTotalTime)).setText(tf.format(timeAfterPenalties));
				((TextView)findViewById(R.id.textViewPosition)).setText("#" + (ContestantList.getInstance().getTopContestants().indexOf(current) + 1));
				
				this.updateCurrentTop3UI();
			}
			
			this.editTextMissedDoors.setText("");
		}
		
		/*
		 * DQ layout.
		 */
		else if (arg0 == buttonDq) {
			if (ContestantList.getInstance().getRemainingRuns().size() == 0) {
				Intent i = new Intent(getBaseContext(), FinalScoreboardActivity.class);
		        startActivity(i);
			}
			
			this.updateCurrentContestantUI();
			this.updateNext2RunsUI();
			timeInMillis = 0L;
			this.textTimer.setText(R.string.base_timer_time);
			this.layoutChrono.setVisibility(LinearLayout.VISIBLE);
			this.layoutDq.setVisibility(LinearLayout.GONE);
		}
		
		/*
		 * DNF layout
		 */
		else if (arg0 == buttonDnf) {
			if (ContestantList.getInstance().getRemainingRuns().size() == 0) {
				Intent i = new Intent(getBaseContext(), FinalScoreboardActivity.class);
		        startActivity(i);
			}
			
			this.updateCurrentContestantUI();
			this.updateNext2RunsUI();
			timeInMillis = 0L;
			this.textTimer.setText(R.string.base_timer_time);
			this.layoutChrono.setVisibility(LinearLayout.VISIBLE);
			this.layoutDnf.setVisibility(LinearLayout.GONE);
		}
		
		/*
		 * Scoreboard layout.
		 */
		else if (arg0 == buttonScoreboard) {
			if (ContestantList.getInstance().getRemainingRuns().size() == 0) {
				Intent i = new Intent(getBaseContext(), FinalScoreboardActivity.class);
		        startActivity(i);
			}
			
			this.updateCurrentContestantUI();
			this.updateNext2RunsUI();
			timeInMillis = 0L;
			this.textTimer.setText(R.string.base_timer_time);
			layoutChrono.setVisibility(LinearLayout.VISIBLE);
			layoutScoreboard.setVisibility(LinearLayout.GONE);
		}
		
		else if (arg0 == textViewAllRunsLink) {
			Intent i = new Intent(getApplicationContext(), RunListActivity.class);
			startActivity(i);
		}
		
		else if (arg0 == textViewAllContestantsLink) {
			Intent i = new Intent(getApplicationContext(), ContestantListActivity.class);
			startActivity(i);
		}
		
		else if (arg0 == buttonAddContestant) {
			Intent i = new Intent(getApplicationContext(), AddContestantActivity.class);
			startActivity(i);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ContestantList list = ContestantList.getInstance();
		
		switch (item.getItemId()) {
			case R.id.clear_contestants:
				list.clear();
				
				this.updateCurrentContestantUI();
				this.updateCurrentTop3UI();
				this.updateNext2RunsUI();
				
				return true;
				
			case R.id.add_default_values:
			
				list.clear();
				
				try {
					list.add(new Contestant("Schoch", "Philipp", "SUI"));
					list.add(new Contestant("Schoch", "Simon", "SUI"));
					list.add(new Contestant("Anderson", "Jasey-Jay", "CAN"));
					list.add(new Contestant("Richardsson", "Richard", "SWE"));
					list.add(new Contestant("Benjamin", "Karl", "AUT"));
					list.add(new Contestant("Klug", "Chris", "USA"));
					list.add(new Contestant("Grabner", "Siegfried", "AUT"));
				} catch (ContestantAlreadyExistsException e) {
					e.printStackTrace();
				}
				
				this.updateCurrentContestantUI();
				this.updateCurrentTop3UI();
				this.updateNext2RunsUI();
				
				return true;
			
			case R.id.add_default_values_2_identical_times_gold:

				list.clear();
				
				try {
					Contestant c1 = new Contestant("Schoch", "Philipp", "SUI");
					Contestant c2 = new Contestant("Schoch", "Simon", "SUI");
					
					c1.setTime(100000);
					c1.decreaseRunsLeft();
					c2.setTime(100000);
					c2.decreaseRunsLeft();
					
					list.add(c1);
					list.add(c2);
					list.add(new Contestant("Anderson", "Jasey-Jay", "CAN"));
					list.add(new Contestant("Richardsson", "Richard", "SWE"));
					list.add(new Contestant("Benjamin", "Karl", "AUT"));
					list.add(new Contestant("Klug", "Chris", "USA"));
					list.add(new Contestant("Grabner", "Siegfried", "AUT"));
				} catch (ContestantAlreadyExistsException e) {
					e.printStackTrace();
				}
				
				this.updateCurrentContestantUI();
				this.updateCurrentTop3UI();
				this.updateNext2RunsUI();
				
				return true;
				
			case R.id.add_default_values_2_identical_times_silver:

				list.clear();
				
				try {
					Contestant c1 = new Contestant("Schoch", "Philipp", "SUI");
					Contestant c2 = new Contestant("Schoch", "Simon", "SUI");
					Contestant c3 = new Contestant("Anderson", "Jasey-Jay", "CAN");
					
					
					c1.setTime(90000);
					c1.decreaseRunsLeft();
					c2.setTime(100000);
					c2.decreaseRunsLeft();
					c3.setTime(100000);
					c3.decreaseRunsLeft();
					
					list.add(c1);
					list.add(c2);
					list.add(c3);
					list.add(new Contestant("Richardsson", "Richard", "SWE"));
					list.add(new Contestant("Benjamin", "Karl", "AUT"));
					list.add(new Contestant("Klug", "Chris", "USA"));
					list.add(new Contestant("Grabner", "Siegfried", "AUT"));
				} catch (ContestantAlreadyExistsException e) {
					e.printStackTrace();
				}
				
				this.updateCurrentContestantUI();
				this.updateCurrentTop3UI();
				this.updateNext2RunsUI();
				
				return true;
				
			case R.id.add_default_values_3_identical_times:

				list.clear();
				
				try {
					Contestant c1 = new Contestant("Schoch", "Philipp", "SUI");
					Contestant c2 = new Contestant("Schoch", "Simon", "SUI");
					Contestant c3 = new Contestant("Benjamin", "Karl", "AUT");
					
					c1.setTime(100000);
					c1.decreaseRunsLeft();
					c2.setTime(100000);
					c2.decreaseRunsLeft();
					c3.setTime(100000);
					c3.decreaseRunsLeft();
					
					list.add(c1);
					list.add(c2);
					list.add(new Contestant("Anderson", "Jasey-Jay", "CAN"));
					list.add(new Contestant("Richardsson", "Richard", "SWE"));
					list.add(c3);
					list.add(new Contestant("Klug", "Chris", "USA"));
					list.add(new Contestant("Grabner", "Siegfried", "AUT"));
				} catch (ContestantAlreadyExistsException e) {
					e.printStackTrace();
				}
				
				this.updateCurrentContestantUI();
				this.updateCurrentTop3UI();
				this.updateNext2RunsUI();
				
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
