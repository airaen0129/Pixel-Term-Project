import java.awt.*;

public class PixelPlayerTester extends Player {
//	private int[][] valueMap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
//	private boolean isFirstStap = false;

	PixelPlayerTester(int[][] map) { super(map); }
	public Point nextPosition(Point lastPosition) {
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int player = map[(int)currentPosition.getX()][(int)currentPosition.getY()];	// 현재 플레이어가 1번 돌인지 2번 돌인지 판단
		Point nextPosition;

	//	myMap = ArrayCopy(map);		// 현재 맵 복사

		nextPosition = new Point(x, y);
		return nextPosition;
	}
	private Actions(Point lastPosition) {
		for (int i = 0; i < PixelTester.SIZE_OF_BOARD; i++) {
			if (map[i][y]) {

			}
			if (map[x][i]) {

			}
		}
	}
	private Player AlphaBetaSearch() {
		Player v = MaxValue();
	}
	private int MaxValue() {

	}
	private int MinValue() {

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
