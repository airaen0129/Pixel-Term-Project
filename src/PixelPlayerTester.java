import java.awt.*;
import java.util.Scanner;

public class PixelPlayerTester extends Player {
	PixelPlayerTester(int[][] map) { super(map); }

	Scanner scan = new Scanner (System.in);

	public Point nextPosition(Point lastPosition) {
		Point nextPosition;

		String num = scan.nextLine();
		int x = Integer.parseInt(num.substring(0, 1));
		int y = Integer.parseInt(num.substring(1));

		nextPosition = new Point(y, x);
		return nextPosition;
	}
}
