package gti350.slalom.models.comparators;

import gti350.slalom.models.Contestant;

import java.util.Comparator;

public class BibComparator implements Comparator<Contestant> {
	@Override
	public int compare(Contestant arg0, Contestant arg1) {
		return arg0.getBib() - arg1.getBib();
	}
}
