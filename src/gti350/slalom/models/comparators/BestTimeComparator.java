package gti350.slalom.models.comparators;

import gti350.slalom.models.Contestant;

import java.util.Comparator;

public class BestTimeComparator implements Comparator<Contestant> {
	@Override
	public int compare(Contestant arg0, Contestant arg1) {
		if(arg0.getBestTime() < arg1.getBestTime())
			return -1;
		else if (arg0.getBestTime() > arg1.getBestTime())
			return 1;
		
		else if(arg0.getBestTime() == arg1.getBestTime())
			return 0;
		return 2;
	}
}
