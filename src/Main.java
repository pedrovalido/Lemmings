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

//			System.out.println("GAME1");

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

		for (int i = 0; i < line1.length + 1; i++) {
			for (int j = 0; j < line2.length + 1; j++) {
				Point p = new Point(0, 0);
				if (i != 0 && j != 0) {
					long option1 = pointMatrix[i - 1][j].points;

					long option2 = pointMatrix[i][j - 1].points;

					int drop = dropTwoLemmings(line1[i - 1], line2[j - 1]);
					long option3 = pointMatrix[i - 1][j - 1].points + dropTwoLemmings(line1[i - 1], line2[j - 1]);

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
//
//						System.out.println(
//								"antes :" + pointMatrix[i - 1][j - 1].points + " " + pointMatrix[i - 1][j - 1].pairs);
						p = new Point(option3,
								drop > 0 ? pointMatrix[i - 1][j - 1].pairs + 1 : pointMatrix[i - 1][j - 1].pairs);

//						System.out.println("depois " + p.points + " " + p.pairs);
					}

				}
				pointMatrix[i][j] = p;

//				System.out.println();
//				System.out.println("matrix " + i + "," + j);
//				for (int k = 0; k < pointMatrix.length; k++) {
//					String line = "";
//					for (int k1 = 0; k1 < pointMatrix[k].length; k1++) {
//						if (pointMatrix[k][k1] == null)
//							line += "0,0 ";
//						else
//							line += pointMatrix[k][k1].points + "," + pointMatrix[k][k1].pairs + " ";
//					}
//					System.out.println(line);
//				}
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
	private static int dropTwoLemmings(Lemming l1, Lemming l2) {
		return l1.tribe == l2.tribe ? l1.points + l2.points : 0;
	}

}
