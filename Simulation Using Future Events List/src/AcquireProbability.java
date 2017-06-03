import java.util.*;

public class AcquireProbability<E> {
	public final NavigableMap<Double, E> map = new TreeMap<Double, E>();
	public static Random rng;
	public double result = 0;

	public static void initSeed(boolean arbitrarySeed) {
		if (arbitrarySeed) {
			rng.setSeed(System.currentTimeMillis());
		} else {
			rng.setSeed(560);
		}
	}

	public AcquireProbability() {
		rng = new Random();
	}

	public E next() {
		initSeed(true);
		return map.ceilingEntry(result * rng.nextDouble()).getValue();
	}

	public void add(double chance, E key) {
		if (chance >= 0)
			result = result + chance;
			map.put(result, key);
		if (chance <= 0) {
			return;
		}

	}

}