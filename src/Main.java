import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lemming.Lemming;
import point.Point;

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

			if (lemmingsFirstLine.length > 0 && lemmingsSecondLine.length > 0)
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
		Point[][] pointMatrix = new Point[line1.length + 1][line2.length + 1];

		//filling the pointMatrix bottomup
		for (int i = 0; i < line1.length + 1; i++) {
			for (int j = 0; j < line2.length + 1; j++) {
				Point p = new Point(0, 0);
				
				//no points if one of the lines is empty
				if (i != 0 && j != 0) {
					long option1 = pointMatrix[i - 1][j].points;

					long option2 = pointMatrix[i][j - 1].points;

					long drop = dropTwoLemmings(line1[i - 1], line2[j - 1]);
					long option3 = pointMatrix[i - 1][j - 1].points + dropTwoLemmings(line1[i - 1], line2[j - 1]);

					//which option generates most points
					//if there are two option with maxPoints -> tie break with minPairs
					if (option1 >= option2 && option1 >= option3) {
						if (option1 == option2)
							p = new Point(option1, Math.min(pointMatrix[i - 1][j].pairs, pointMatrix[i][j - 1].pairs));
						else
							p = new Point(option1, pointMatrix[i - 1][j].pairs);

					}
					if (option2 >= option1 && option2 >= option3) {
						if (option1 == option2)
							p = new Point(option1, Math.min(pointMatrix[i - 1][j].pairs, pointMatrix[i][j - 1].pairs));
						else
							p = new Point(option2, pointMatrix[i][j - 1].pairs);

					}
					if (option3 > option1 && option3 > option2) {
						p = new Point(option3,
								drop > 0 ? pointMatrix[i - 1][j - 1].pairs + 1 : pointMatrix[i - 1][j - 1].pairs);
					}
				}
				pointMatrix[i][j] = p;

			}
		}

		System.out.println(
				pointMatrix[line1.length][line2.length].points + " " + pointMatrix[line1.length][line2.length].pairs);
	}

	/**
	 * Calculates the amount of points given by the pair of lemmings in the input
	 * 
	 * @param l1 - Lemming
	 * @param l2 - Lemming
	 * @return points generated
	 */
	private static long dropTwoLemmings(Lemming l1, Lemming l2) {
		return l1.tribe == l2.tribe ? l1.points + l2.points : 0;
	}

}
