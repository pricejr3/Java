import java.util.Random;

public class WebServerSimulation {

	// Variables to hold the count for minutes working
	// and minutes not working.
	public static int working = 0;
	public static int notWorking = 0;

	// Variables to hold the status of each component.
	// all are true as they all work at the beginning.
	public static boolean routerOneStatus = true;
	public static boolean routerTwoStatus = true;
	public static boolean webServerOneStatus = true;
	public static boolean webServerTwoStatus = true;
	public static boolean dataServerOneStatus = true;
	public static boolean dataServerTwoStatus = true;
	public static boolean diskOneStatus = true;
	public static boolean diskTwoStatus = true;
	public static boolean diskThreeStatus = true;

	// Variables to determine if the component failed.
	public static boolean routerOneFailed = false;
	public static boolean routerTwoFailed = false;
	public static boolean webServerOneFailed = false;
	public static boolean webServerTwoFailed = false;
	public static boolean dataServerOneFailed = false;
	public static boolean dataServerTwoFailed = false;
	public static boolean diskOneFailed = false;
	public static boolean diskTwoFailed = false;
	public static boolean diskThreeFailed = false;

	// Variables to determine if a component just failed
	// during the same time interval. e.g, if this is
	// true, then it just happened and it can't
	// recover until the next time.
	public static boolean routerOneJustFailed = false;
	public static boolean routerTwoJustFailed = false;
	public static boolean webServerOneJustFailed = false;
	public static boolean webServerTwoJustFailed = false;
	public static boolean dataServerOneJustFailed = false;
	public static boolean dataServerTwoJustFailed = false;
	public static boolean diskOneJustFailed = false;
	public static boolean diskTwoJustFailed = false;
	public static boolean diskThreeJustFailed = false;

	// Variables to determine if a component has
	// recovered during the current time interval.
	// Only possible if the "just failed" variable
	// is false.
	public static boolean routerOneRecovered = false;
	public static boolean routerTwoRecovered = false;
	public static boolean webServerOneRecovered = false;
	public static boolean webServerTwoRecovered = false;
	public static boolean dataServerOneRecovered = false;
	public static boolean dataServerTwoRecovered = false;
	public static boolean diskOneRecovered = false;
	public static boolean diskTwoRecovered = false;
	public static boolean diskThreeRecovered = false;

	/*
	 * Checks to see if the components have failed.
	 */
	public static void checkFailure() {

		routerOneFailed = new Random().nextInt(1000) == 0;
		routerTwoFailed = new Random().nextInt(1000) == 0;
		webServerOneFailed = new Random().nextInt(500) == 0;
		webServerTwoFailed = new Random().nextInt(500) == 0;
		dataServerOneFailed = new Random().nextInt(500) == 0;
		dataServerTwoFailed = new Random().nextInt(500) == 0;
		diskOneFailed = new Random().nextInt(150) == 0;
		diskTwoFailed = new Random().nextInt(150) == 0;
		diskThreeFailed = new Random().nextInt(150) == 0;

		// Set variables accordingly.
		if (routerOneFailed && routerOneStatus == true) {
			routerOneStatus = false;
			routerOneJustFailed = true;
		}
		if (routerTwoFailed && routerTwoStatus == true) {
			routerTwoStatus = false;
			routerTwoJustFailed = true;
		}
		if (webServerOneFailed && webServerOneStatus == true) {
			webServerOneStatus = false;
			webServerOneJustFailed = true;
		}
		if (webServerTwoFailed && webServerTwoStatus == true) {
			webServerTwoStatus = false;
			webServerTwoJustFailed = true;
		}
		if (dataServerOneFailed && dataServerOneStatus == true) {
			dataServerOneStatus = false;
			dataServerOneJustFailed = true;
		}
		if (dataServerTwoFailed && dataServerTwoStatus == true) {
			dataServerTwoStatus = false;
			dataServerTwoJustFailed = true;
		}
		if (diskOneFailed && diskOneStatus == true) {
			diskOneStatus = false;
			diskOneJustFailed = true;
		}
		if (diskTwoFailed && diskTwoStatus == true) {
			diskTwoStatus = false;
			diskTwoJustFailed = true;
		}
		if (diskThreeFailed && diskThreeStatus == true) {
			diskThreeStatus = false;
			diskThreeJustFailed = true;
		}

	}

	public static void checkRecovery() {

		routerOneRecovered = new Random().nextInt(30) == 0;
		routerTwoRecovered = new Random().nextInt(30) == 0;
		webServerOneRecovered = new Random().nextInt(60) == 0;
		webServerTwoRecovered = new Random().nextInt(60) == 0;
		dataServerOneRecovered = new Random().nextInt(30) == 0;
		dataServerTwoRecovered = new Random().nextInt(30) == 0;
		diskOneRecovered = new Random().nextInt(15) == 0;
		diskTwoRecovered = new Random().nextInt(15) == 0;
		diskThreeRecovered = new Random().nextInt(15) == 0;

	}

	public static void recover() {

		if (routerOneRecovered && routerOneJustFailed == false
				&& routerOneStatus == false) {
			routerOneStatus = true;

		}
		if (routerTwoRecovered && routerTwoJustFailed == false
				&& routerTwoStatus == false) {
			routerTwoStatus = true;

		}

		if (webServerOneRecovered && webServerOneJustFailed == false
				&& webServerOneStatus == false) {
			webServerOneStatus = true;

		}

		if (webServerTwoRecovered && webServerTwoJustFailed == false
				&& webServerTwoStatus == false) {
			webServerTwoStatus = true;

		}

		if (dataServerOneRecovered && dataServerOneJustFailed == false
				&& dataServerOneStatus == false) {
			dataServerOneStatus = true;

		}
		if (dataServerTwoRecovered && dataServerTwoJustFailed == false
				&& dataServerTwoStatus == false) {
			dataServerTwoStatus = true;

		}

		if (diskOneRecovered && diskOneJustFailed == false
				&& diskOneStatus == false) {
			diskOneStatus = true;

		}
		if (diskTwoRecovered && diskTwoJustFailed == false
				&& diskTwoStatus == false) {
			diskTwoStatus = true;

		}
		if (diskThreeRecovered && diskThreeJustFailed == false
				&& diskThreeStatus == false) {
			diskThreeStatus = true;

		}

	}

	public static void resetJustFailed() {

		routerOneJustFailed = false;
		routerTwoJustFailed = false;
		webServerOneJustFailed = false;
		webServerTwoJustFailed = false;
		dataServerOneJustFailed = false;
		dataServerTwoJustFailed = false;
		diskOneJustFailed = false;
		diskTwoJustFailed = false;
		diskThreeJustFailed = false;

	}

	public static void determineNumberWorking() {

		if ((routerOneStatus || routerTwoStatus)
				&& (webServerOneStatus || webServerTwoStatus)
				&& (dataServerOneStatus || dataServerTwoStatus)
				&& (diskOneStatus || diskTwoStatus || diskThreeStatus)) {
			working++;
		} else {
			notWorking++;
		}

	}

	public static void main(String[] args) {

		for (int i = 0; i < 1051200; i++) {
			checkFailure();
			checkRecovery();
			recover();
			resetJustFailed();
			determineNumberWorking();

		}

		System.out.println("Number of minutes working: " + working);
		System.out.println("Number of minutes not working: " + notWorking);

	}

}
