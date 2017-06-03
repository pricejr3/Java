import java.util.*;


public class sum {

	static ArrayList<Integer> returnChecker = new ArrayList<Integer>();
	static HashSet<Integer> empty = new HashSet<Integer>();
	

	public static boolean sum(int idx, int targetValue, int clearArrayList, int [] test, int answer) {
		

		
		if(targetValue == 0){

		int zeroTest = 0;

		     for (int n : test) {
         		if(n == 0){
			  zeroTest = 1;
				}
         		}

		if(zeroTest == 1){

			returnChecker.add(1);

		}

		if(zeroTest == 0){

			returnChecker.add(0);

		}

		}

		

		if(clearArrayList == 1){
			empty.clear();
			returnChecker.clear();
		}
	
		if (answer == targetValue){
		
			returnChecker.add(1);
		}


	
		
		if ( idx < test.length) {  
		
			int value = test[idx];
			sum(idx + 1, targetValue, 2, test, answer + value); 		
			sum(idx + 1, targetValue, 2, test, answer);  
		}
		
		boolean returnValue = false;
		
		if(returnChecker.contains(1)){
			
			returnValue = true;
		}

		if(returnChecker.contains(0)){

			returnValue = false;

		}
		
		
		
		return returnValue;
	}
	
	public static void main(String[] args) {
		
	
		
		int[] test1 = {1,2,3,4};
		int targetValue = 10;
		System.out.println(sum(0, targetValue, 1, test1, 0));

		int[] test2 = {1,2,3,4};
		targetValue = 14;
		System.out.println(sum(0, targetValue, 1, test2, 0));

		int[] test3 = {1,2,3,4};
		targetValue = 0;
		System.out.println(sum(0, targetValue, 1, test3, 0));

		int[] test4 = {0,1,2,3,4};
		targetValue = 0;
		System.out.println(sum(0, targetValue, 1, test4, 0));

		int[] test5 = {1,2,3,-4};
		targetValue = -3;
		System.out.println(sum(0, targetValue, 1, test5, 0));

		int[] test6 = {1,2,8,2};
		targetValue = 5;
		System.out.println(sum(0, targetValue, 1, test6, 0));

		int[] test7 = {5, -10, 0, 2};
		targetValue = -5;
		System.out.println(sum(0, targetValue, 1, test7, 0));

		int[] test8 = {-1,-2,-8,-2};
		targetValue = 10;
		System.out.println(sum(0, targetValue, 1, test8, 0));

		int[] test9 = {1,2,8,2};
		targetValue = 14;
		System.out.println(sum(0, targetValue, 1, test9, 0));
	
		int[] test10 = {};
		targetValue = 5;
		System.out.println(sum(0, targetValue, 1, test10, 0));
	
	}
}