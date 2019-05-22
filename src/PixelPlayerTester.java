import java.awt.*;

public class PixelPlayerTester extends Player {
	private int[][] valueMap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
	private boolean isFirstStap = false;

	PixelPlayerTester(int[][] map) { super(map); }
	public Point nextPosition(Point lastPosition) {
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int myNum = map[(int)currentPosition.getX()][(int)currentPosition.getY()];
		Point nextPosition;

		if (!isFirstStap) {
			if ()
		}



		nextPosition = new Point(x, y);
		return nextPosition;
	}
	private final int[][] ArrayCopy(int[][] src) {
		if (src == null) return null;
		int[][] dest = new int[src.length][src[0].length];

		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
		}

		return dest;
	}

}
