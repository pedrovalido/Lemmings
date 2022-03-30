import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lemming.Lemming;

/**
 * 
 * @author PedroValido 56760
 *
 */
public class Main {

	/**
	 * Function providing entry point to the problem resolution
	 * 
	 * Reads the inputs and processes it
	 * 
	 * @param args
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

		int nTrials = Integer.parseInt(buf.readLine().trim());

		for (int i = 0; i < nTrials; i++) {

			int nLemmingsFirstLine = Integer.parseInt(buf.readLine().trim());

			Lemming[] lemmingsFirstLine = new Lemming[nLemmingsFirstLine];

			for (int k = 0; k < nLemmingsFirstLine; k++) {
				String[] line = buf.readLine().split(" ");
				lemmingsFirstLine[k] = new Lemming(line[0].trim().charAt(0), Integer.parseInt(line[1]));
			}

			int nLemmingsSecondLine = Integer.parseInt(buf.readLine().trim());

			Lemming[] lemmingsSecondLine = new Lemming[nLemmingsSecondLine];

			for (int k = 0; k < nLemmingsSecondLine; k++) {
				String[] line = buf.readLine().split(" ");
				lemmingsSecondLine[k] = new Lemming(line[0].trim().charAt(0), Integer.parseInt(line[1]));
			}

			if (lemmingsFirstLine.length != 0 && lemmingsSecondLine.length !=0)
				calc(lemmingsFirstLine, lemmingsSecondLine);
			else
				System.out.println("0 0");
		}

	}

	/**
	 * Calculates the maximum amount of points possible and minimum amount of pairs
	 * to do so Prints the result in the default output
	 * 
	 * @param line1 - line of Lemmings
	 * @param line2 - line of Lemmings
	 */
	private static void calc(Lemming[] line1, Lemming[] line2) {
		int minPairs = 0;

		int[][] pointMatrix = new int[line1.length+1][line2.length+1];

		for (int i = 0; i < line1.length+1; i++) {
			//solucao alternativa j=i ver qual e maior e ir so de j=i ate length
			for (int j = 0; j < line2.length+1; j++) {
				int p = 0;
				if (i != 0 && j != 0) {
					int option12 = Math.max(pointMatrix[i - 1][j], pointMatrix[i][j - 1]);

					int drop = dropTwoLemmings(line1[i-1],line2[j-1]);
					if(drop>0)
						minPairs++;
					int option3 = pointMatrix[i - 1][j - 1] + drop;
					p = Math.max(option12, option3);
				}
				pointMatrix[i][j] = p;
			}
		}

		System.out.println(pointMatrix[line1.length][line2.length] + " " + minPairs/2);
	}

	/**
	 * Calculates the amount of points given by the pair of lemmings in the input
	 * 
	 * @param l1 - Lemming
	 * @param l2 - Lemming
	 * @return points generated
	 */
	private static int dropTwoLemmings(Lemming l1, Lemming l2) {
		if(l1.tribe == l2.tribe)
			return l1.points + l2.points;
		return 0;
	}

}
