

public class Event implements Comparable<Event> {
	public enum EventType { ARRIVAL, DEPARTURE, EXIT };
	
	public EventType typeOfEvent;
	public int time;
	
	public Event(int time, EventType type)
	{
		typeOfEvent = type;
		this.time = time;
	}
	

	@Override
	public int compareTo(Event other)
	{
		int result;
		if (time < other.time) {
			result = -1;
		} else if (time > other.time) {
			result = +1;
		} else {
			result = 0;
		}
		return result;
	}
	
	public String toString()
	{ return String.format("(%s,%d)", typeOfEvent, time); }

	
}

