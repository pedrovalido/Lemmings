import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	private static final int MAX_N2 = 1000;

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

			calc(lemmingsFirstLine, lemmingsSecondLine);
		}

	}

	private static void calc(Lemming[] line1, Lemming[] line2) {
		int minPairs =0;
		
		int[][] pointMatrix = new int[line1.length][line2.length];
		
		//s(i, j) = 0 , i=0 || j=0
		//s(i, j) = max( s(i-1,j), s(i, j-1), drop(l1, l2) + s(i-1,j-1) )
		for(int i=0;i<line1.length;i++) {
			for(int j=0;j<line2.length;j++) {
				int p = 0;
				if(i!=0&&j!=0) {
					int option1 = pointMatrix[i-1][j];
					int option2 = pointMatrix[i][j-1];
					
					int option12 = Math.max(option1,  option2);
					
					int option3 = pointMatrix[i-1][j-1] + dropTwoLemmings(line1[i], line2[j]);
					
					p=Math.max(option12,  option3);
					if(p==option3)minPairs++;
				}
			}
		}
		
		System.out.println(pointMatrix[line1.length-1][line2.length-1] + " " + minPairs);
	}

	private static int dropTwoLemmings(Lemming l1, Lemming l2) {
		return l1.tribe == l2.tribe ? l1.points + l2.points : 0;
	}

}
