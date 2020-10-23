package uniandes.algorithms.coinchange;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DynamicCoinChangeAlgorithm implements CoinChangeAlgorithm {


	public DynamicCoinChangeAlgorithm() {
	}

	public int[] calculateOptimalChange(int totalValue, int [] denominations) {
		return Arrays.stream(selectCoins(totalValue, denominations)).mapToInt(Integer::intValue).toArray();
	}

	private Integer[] selectCoins(int totalValue, int[]denominations) {
		int P = totalValue;
		int N = denominations.length;

		int[][] c = new int[N+1][P+1];

		int i, j;

		i = j = 0;

		while (i <= N) {
			if (i == 0) c[i][j] = 0;
			else if (j < denominations[i]) c[i][j] = c[i-1][j];
			else if (j >= denominations[i]) c[i][j] = Math.min(c[i-1][j], c[i-1][j-denominations[i]]+1);
			
			if (j < P) j += 1;
			else if (j == P) {
				i += 1;
				j = 0;
			}
		}
		
		i = N;
		j = P;
		
		Set<Integer> indexes = new HashSet<Integer>();
		while (i > 0) {
			if (denominations[i] <= j && c[i][j] == c[i-1][j - denominations[i]]+1) {
				i -= 1;
				j -= denominations[i];
				indexes.add(i);
			} else if (c[i][j] == c[i-1][j]) i -= 1;
		}
		
		return indexes.toArray(new Integer[indexes.size()]);
	}
}
