package gti350.slalom.models;

import android.annotation.SuppressLint;
import java.util.*;

/**
 * 
 * @author Christopher Lariviere
 * 
 * The main purpose of this class is to verify that the country 
 * in the standard ISO 3166-1 alpha-3 country list
 *
 */
@SuppressLint("DefaultLocale")
public class CountryLocales {

	//For country verification and 3 letter extraction
	private Locale locales[] = Locale.getAvailableLocales();
	private ArrayList<String> countries = new ArrayList<String>();
	
	public CountryLocales() {
		for (Locale locale: locales){
			String country = locale.getDisplayCountry();
			
			country = country.trim();
			
			if (!country.equals("") && !countries.contains(country)) {
				countries.add(country);
			}
		}
		
		Collections.sort(countries);
	}
	
	public String verifyCountry(String countryToCheck) {
		for (Locale locale : locales) {
            String country = locale.getDisplayCountry().toUpperCase();
            
            if(country.equalsIgnoreCase(countryToCheck)){
            	return locale.getISO3Country();
            }
        }
		
		return "Country not found";
	}
	
	public ArrayList<String> getCountries() {
		return countries;
	}
}
