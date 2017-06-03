// M/M/2/8/8
public class Queueing {
	public static double Lambda = 2.0;		// arrival rate
	public static double Mu = 4.0;			// service rate
	public static int C = 2;				// number of servers, should be <= K
	public static int K = 8;				// size of population and system capacity

	public static long partialFact(int m, int n)
	{
		long prod = 1;
		for (int i=m; i<=n; i++) {
			prod *= i;
		}
		return prod;
	}	
	public static long fact(int n)
	{
		return partialFact(1, n);
	}
	public static long comb(int n, int m)
	{
		return partialFact(m+1, n) / fact(n-m);
	}
	public static double P0()
	{
		double sum = 0;
		for (int n=0; n<=C-1; n++) {
			sum += comb(K, n) * Math.pow(Lambda / Mu, n);
		}
		for (int n=C; n<=K; n++) {
			sum += partialFact(K-n+1, K) / fact(C) / Math.pow(C, n-C) * Math.pow(Lambda / Mu, n);
		}
		return 1.0 / sum;
	}
	public static double P(int n)
	{
		if (n < C) {
			return comb(K, n) * Math.pow(Lambda / Mu, n) * P0();
		} else {
			return partialFact(K-n+1, K) / fact(C) / Math.pow(C, n-C) * Math.pow(Lambda / Mu, n) * P0();
		}
	}
	public static double L()
	{
		double sum = 0;
		for (int n=0; n<=K; n++) {
			sum += n * P(n);
		}
		return sum;
	}
	public static double LQ()
	{
		double sum = 0;
		for (int n=C+1; n<=K; n++) {
			sum += (n - C) * P(n);
		}
		return sum;
	}
	public static double lambdaE()
	{
		double sum = 0;
		for (int n=0; n<=K; n++) {
			sum += (K - n) * Lambda * P(n);
		}
		return sum;
	}
	public static double W()
	{
		return L() / lambdaE();
	}
	public static double WQ()
	{
		return LQ() / lambdaE();
	}
	public static double rho()
	{
		return lambdaE() / (C * Mu);
	}
	public static void main(String[] args)
	{
		System.out.printf("P0 : %f%n", P(0));
		System.out.printf("L : %f%n", L());
		System.out.printf("LQ : %f%n", LQ());
		System.out.printf("lambdaE : %f%n", lambdaE());
		System.out.printf("W : %f%n", W());
		System.out.printf("WQ : %f%n", WQ());
		System.out.printf("rho : %f%n", rho());

	}
}
