package gti350.slalom.activities;

import gti350.slalom.R;
import gti350.slalom.models.Contestant;
import gti350.slalom.models.ContestantList;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RunListActivity extends Activity implements OnClickListener {
	private ListView listView;
	private Button buttonBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run_list);
		
		// find controls
		listView = (ListView)findViewById(R.id.genericListView);
		buttonBack = (Button)findViewById(R.id.buttonBack);
		
		// fill list view
		fillListView();
		
		// add listeners
		buttonBack.setOnClickListener(this);
	}

	private void fillListView() {
		ArrayList<Contestant> all = ContestantList.getInstance().getRemainingRuns();
		List<String> raw = new ArrayList<String>();
		
		for (Contestant c: all) {
			raw.add(c.toString());
		}
		
		// The first result is the current contestant.
		if (raw.size() > 0) {
			raw.remove(0);
		}
		
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getBaseContext(), R.layout.simple_list_item_1, raw);
		listView.setAdapter(adapter1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.run_list, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == buttonBack) {
			Intent i = new Intent(getBaseContext(), MainActivity.class);
	        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	        startActivity(i);
		}
	}
}
