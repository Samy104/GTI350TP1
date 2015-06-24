package gti350.slalom.models.comparators;

import gti350.slalom.models.Contestant;
import java.util.Comparator;

public class NameComparator implements Comparator<Contestant> {
	@Override
	public int compare(Contestant arg0, Contestant arg1) {
		String name0 = arg0.getLastName() + " " + arg0.getFirstName();
		String name1 = arg1.getLastName() + " " + arg1.getFirstName();
		
		return name0.compareTo(name1);
	}
}
