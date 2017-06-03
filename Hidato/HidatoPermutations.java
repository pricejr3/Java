
import java.util.Vector;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HidatoPermutations<E> implements Iterable<List<E>> {

	
	public List<? extends Collection> hidatoValues;

	public static int value = 0;
	public static int hidatoPermutations;
	public static int hidatoIdx = 0;
	public static List permutations = new Vector();

	@SuppressWarnings("unchecked")
	public HidatoPermutations(List hidatoPermutations) {

		this.hidatoValues = hidatoPermutations;
		int setSize = hidatoValues.size();

		if (setSize > 0) {
			value = 1;
		}
		for (Collection<? extends E> collection : hidatoValues) {
			value = value * collection.size();
		}
		HidatoPermutations.hidatoPermutations = value;
	}

	@Override
	public Iterator<List<E>> iterator() {
		return new Iterator() {

			public List hidatoCollection = new Vector(
					Collections.<Iterator<? extends E>> nCopies(hidatoValues.size(), null));

			{
				if (hidatoPermutations > 0) {
					for (int i = 0; i < hidatoValues.size(); i++) {
						Iterator<? extends E> itr = hidatoValues.get(i).iterator();
						permutations.add(itr.next());
						hidatoCollection.set(i, itr);
						
					}
				}
			}
			
			@Override
			public boolean hasNext() {
				if (hidatoIdx < hidatoPermutations) {
					return true;
				}
				return false;
			}

			@Override
			public List next() {

				List finalList = new Vector(permutations);
				nextElement(hidatoValues.size() - 1);
				hidatoIdx++;
				return finalList;
			}
			
			public void nextElement(int hidatoElement) {

				if (hidatoElement >= 0) {

					Iterator hidatoItr = (Iterator) hidatoCollection.get(hidatoElement);
					if (!hidatoItr.hasNext()) {
						hidatoItr = hidatoValues.get(hidatoElement).iterator();
						hidatoCollection.set(hidatoElement, hidatoItr);
						permutations.set(hidatoElement, (E) hidatoItr.next());
						nextElement(hidatoElement - 1);

					} else {
						permutations.set(hidatoElement, (E) hidatoItr.next());
					}
				}

			}
		};
		
		

	}
}
