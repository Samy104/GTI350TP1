package gti350.slalom.models;

import gti350.slalom.models.comparators.BestTimeComparator;
import gti350.slalom.models.comparators.BibComparator;
import gti350.slalom.models.comparators.NameComparator;
import gti350.slalom.models.exceptions.ContestantAlreadyExistsException;

import java.util.ArrayList;
import java.util.Collections;

public class ContestantList {
	private static ContestantList instance;
	private ArrayList<Contestant> all = new ArrayList<Contestant>();
	
	private ContestantList() {
	}
	
	public static ContestantList getInstance() {
		if (instance == null)
			instance = new ContestantList();
		return instance;
	}
	
	/**
	 * Add a contestant to the system.
	 * 
	 * Throws an exception if the contestant already exists.
	 * To check
	 * 
	 * @throws Exception 
	 */
	public void add(Contestant c) throws ContestantAlreadyExistsException {
		for (Contestant con: all) {
			if (con.getLastName().equals(c.getLastName()) && con.getFirstName().equals(con.getLastName()) && con.getCountry().equals(c.getCountry())) {
				throw new ContestantAlreadyExistsException();
			}
		}
		
		all.add(c);
	}
	
	public void clear() {
		all.clear();
	}
	
	/**
	 * Returns an array sorted by best time and then alphabetically.
	 */
	public ArrayList<Contestant> getContestants() {
		ArrayList<Contestant> contestantsWithBestTime = new ArrayList<Contestant>();
		ArrayList<Contestant> contestantsWithNoTime = new ArrayList<Contestant>();
		
		for (Contestant c: all) {
			if (c.hasBestTime()) {
				contestantsWithBestTime.add(c);
			}
			else {
				contestantsWithNoTime.add(c);
			}
		}
		
		Collections.sort(contestantsWithBestTime, new BestTimeComparator());
		Collections.sort(contestantsWithNoTime, new NameComparator());
		
		contestantsWithBestTime.addAll(contestantsWithNoTime);
		
		return contestantsWithBestTime;
	}
	
	/**
	 * Returns an array of contestants sorted by their best time.
	 * 
	 * A contestant must have a best time to appear in this list.
	 */
	public ArrayList<Contestant> getTopContestants() {
		ArrayList<Contestant> contestantsWithBestTime = new ArrayList<Contestant>();
		
		for (Contestant c: all) {
			if (c.hasBestTime()) {
				contestantsWithBestTime.add(c);
			}
		}
		
		Collections.sort(contestantsWithBestTime, new BestTimeComparator());
		
		return contestantsWithBestTime;
	}
	
	/**
	 * Returns an array of the remaining runs. Contestants are sorted by their bib number.
	 * 
	 * @return
	 */
	public ArrayList<Contestant> getRemainingRuns() {
		ArrayList<Contestant> runs = new ArrayList<Contestant>();
		ArrayList<Contestant> contestantsWith2RunsLeft = new ArrayList<Contestant>();
		ArrayList<Contestant> contestantsWith1RunLeft = new ArrayList<Contestant>();
		
		for (Contestant c: all) {
			if (c.getRunsLeft() == 2) {
				contestantsWith2RunsLeft.add(c);
			}
			else if (c.getRunsLeft() == 1) {
				contestantsWith1RunLeft.add(c);
			}
		}
		
		Collections.sort(contestantsWith2RunsLeft, new BibComparator());
		Collections.sort(contestantsWith1RunLeft, new BibComparator());
		
		ArrayList<Contestant> contestantsWith2RunsLeftCopy = new ArrayList<Contestant>(contestantsWith2RunsLeft);
		
		runs.addAll(contestantsWith2RunsLeft);
		runs.addAll(contestantsWith1RunLeft);
		runs.addAll(contestantsWith2RunsLeftCopy);
		
		return runs;
	}
}
