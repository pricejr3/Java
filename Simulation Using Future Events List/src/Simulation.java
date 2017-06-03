import java.util.*;

public class Simulation {

	public static Random rng = new Random();

	public static PriorityQueue<Event> Evt = new PriorityQueue<Event>();
	public static ArrayList<User> holder = new ArrayList<User>();
	public static ArrayList<User> jobs = new ArrayList<User>();
	public static boolean doingJob = false;
	public static int numCustomers;
	public static User user;
	public static int elapsedTime;
	public static int serviceTime = 0;
	public static int idleTime = 0;
	public static int runningTime = 0;
	public static boolean continuing = false;

	public static AcquireProbability<Integer> getProbs = new AcquireProbability<Integer>();
	public static AcquireProbability<Integer> getProbs2 = new AcquireProbability<Integer>();

	public static ArrayList<Integer> countQueueLength;

	public static void simulate() {

		Evt.add(new Event(0, Event.EventType.ARRIVAL));

		// System.out.println(Evt.peek());

		do {
			Event evnt = Evt.remove();
			elapsedTime = evnt.time;

			if (evnt.typeOfEvent == Event.EventType.DEPARTURE) {

				doingJob = false;
				user.finish = elapsedTime;

				jobs.add(user);
				if (holder.isEmpty()) {
					// Do nothing if there are no
					// customers in the arrayList.
				}
				else{
					user = holder.remove(0);
					user.begin = elapsedTime;

					doingJob = true;
					Evt.add(new Event(user.begin + user.lengthOfService,
							Event.EventType.DEPARTURE));

					// System.out.println(Evt.peek());
				
				}
			}

			if (evnt.typeOfEvent == Event.EventType.ARRIVAL) {
				
				//public int begin;
				//public int finish;
				//public int arrival; 
				//public int lengthOfService;
				
				user = new User();
				user.arrival = evnt.time;

				getProbs.add(0.2, 2);
				getProbs.add(0.5, 5);
				getProbs.add(0.2, 8);
				getProbs.add(0.05, 10);
				getProbs.add(0.05, 15);
				int jobLength = getProbs.next();

				user.lengthOfService = jobLength;
				if (!doingJob) {
					user.begin = elapsedTime;

					jobLength = jobLength + elapsedTime;
					doingJob = true;
					Evt.add(new Event( jobLength,
							Event.EventType.DEPARTURE));

				} else {
					holder.add(user);
				}

				getProbs2.add(0.05, 2);
				getProbs2.add(0.05, 4);
				getProbs2.add(0.1, 6);
				getProbs2.add(0.3, 8);
				getProbs2.add(0.5, 10);
				int interArrivalTime = getProbs2.next();
				interArrivalTime = interArrivalTime + elapsedTime;

				
				Evt.add(new Event(interArrivalTime, Event.EventType.ARRIVAL));
			}

			// System.out.println(elapsedTime);

		} while ( elapsedTime < 1000000 && !Evt.isEmpty() );

	}

	public static void main(String[] args) throws Exception {

		numCustomers = 999999999;
		simulate();

		// Acquire stats and print to screen.
		countQueueLength = new ArrayList<Integer>();

		for (int i = 0; i < jobs.size(); i++) {
			user = jobs.get(i);

			int waiting = user.begin - user.arrival;

			if (waiting == 0) {
				countQueueLength.add(0);
			}
			if (waiting > 0) {
				countQueueLength.add(1);
			}

			int idleUsers = user.begin - serviceTime;
			idleTime = idleTime + idleUsers;

			// Used in do-while loop.
			serviceTime = user.finish;

		}

		int finalLength = 1;
		int queueLength = 0;
		for (int i = 0; i < countQueueLength.size(); i++) {
			if (countQueueLength.get(i) == 1) {
				queueLength++;

				if (finalLength < queueLength) {

					finalLength = queueLength;
				}
			} else if (countQueueLength.get(i) == 0) {

				if (finalLength < queueLength) {

					finalLength = queueLength;
				}
				queueLength = 0;
			}
		}

		double timeIdle = ((double) idleTime / (double) serviceTime);
		System.out.println("All results below are from 1000000 time steps");
		System.out.println("Number of jobs processed: " + jobs.size());
		System.out.println("Percentage of the time the server is idle: "
				+ timeIdle);
		System.out.println("Percentage of the time the server is busy: "
				+ (1 - timeIdle));
		System.out.println("Maximum Queue length:" + finalLength);

		System.out
				.print("Final Service time is (the last time that a customer has been processed): "
						+ serviceTime);

	}
}
