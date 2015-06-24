package gti350.slalom.activities;



import gti350.slalom.R;
import gti350.slalom.models.Contestant;
import gti350.slalom.models.ContestantList;
import gti350.slalom.models.CountryLocales;
import gti350.slalom.models.exceptions.ContestantAlreadyExistsException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

@SuppressLint("DefaultLocale")
public class AddContestantActivity extends Activity implements OnClickListener {

	private Button buttonAdd;
	private Button buttonBack;
	
	private EditText editTextFirstName;
	private EditText editTextLastName;
	private EditText editTextCountry;
	
	private Context context = this;
	
	private CountryLocales cLocales= new CountryLocales();
	
	//Filterable countrylist
	private ListView lView;
	private ArrayAdapter<String> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contestant);
		
		// find controls
		buttonAdd = (Button)findViewById(R.id.buttonAdd);
		buttonBack = (Button)findViewById(R.id.buttonBack);
		editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
		editTextLastName = (EditText)findViewById(R.id.editTextLastName);
		editTextCountry = (EditText)findViewById(R.id.editTextCountry);
		
		lView = (ListView)findViewById(R.id.genericListView);
		adapter = new ArrayAdapter<String>(this,R.layout.simple_list_item_1,cLocales.getCountries());
		lView.setAdapter(adapter);
		
		// add listeners
		editTextCountry.addTextChangedListener(new TextWatcher() {
		     
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        // When user changed the Text
		        AddContestantActivity.this.adapter.getFilter().filter(cs);   
		    }
		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {
		        // TODO Auto-generated method stub
		         
		    }

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        public void onItemClick(AdapterView<?> arg0, View view, int arg2,
	                long arg3) {
	            editTextCountry.setText((String)lView.getItemAtPosition(arg2));
	        }
	    });
		
		
		buttonAdd.setOnClickListener(this);
		buttonBack.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contestant, menu);
		return true;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void onClick(View v) {
		String countryCode = cLocales.verifyCountry(editTextCountry.getText().toString().toUpperCase());
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
			
			if (v == buttonAdd && (countryCode != "Country not found")) {
				Contestant c = new Contestant(editTextLastName.getText().toString(), editTextFirstName.getText().toString(), countryCode);
				try {
					ContestantList.getInstance().add(c);
					
					alertBuilder.setTitle("Contestant added");
					alertBuilder.setMessage(c.toString() + " has been added to the competition.");
					alertBuilder.setCancelable(false);
					alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					
					// show confirmation
				}
				catch (ContestantAlreadyExistsException e) {
					alertBuilder.setMessage(c.toString() + " is already a contestant");
					alertBuilder.setCancelable(false);
					alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
				}
				
				AlertDialog alert = alertBuilder.create();
				
				alert.show();
				// reset fields
				editTextFirstName.setText("");
				editTextLastName.setText("");
				editTextCountry.setText("");
			}
			else if (v == buttonBack) {
				Intent i = new Intent(getBaseContext(), MainActivity.class);
		        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(i);
			}
		else
		{
			alertBuilder.setTitle("Contestant country not in list");
			alertBuilder.setMessage("The country " + editTextCountry.getText().toString()+ " you entered is invalid");
			alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			AlertDialog alert = alertBuilder.create();
			alert.show();
			Log.d("SLALOM CONTESTANTACTIVITY","Country not found");
		}
	}
}
