import java.awt.*;
import java.util.ArrayList;

public class PixelPlayerTester extends Player {
//	private int[][] valueMap = new int[PixelTester.SIZE_OF_BOARD][PixelTester.SIZE_OF_BOARD];
//	private boolean isFirstStap = false;

	PixelPlayerTester(int[][] map) { super(map); }
	public Point nextPosition(Point lastPosition) {
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int player = map[(int)currentPosition.getX()][(int)currentPosition.getY()];	// 현재 플레이어가 1번 돌인지 2번 돌인지 판단
		Point nextPosition;

	    //	myMap = ArrayCopy(map);		// 현재 맵 복사
        // 메인 필요

		nextPosition = new Point(x, y);
		return nextPosition;
	}

	// 주어진 상태에서 가능한 수들의 집합을 돌려준다.
	private Point[] Actions(Point lastPosition) {
        ArrayList<Point> actionsPoint = new ArrayList<Point>();
        // 다음 돌이 놓을 수 있는(map의 값이 0인) 곳 검색
        for (int i = 0; i < PixelTester.SIZE_OF_BOARD; i++) {
            if (map[(int)lastPosition.getX()][i] == 0) {
                actionsPoint.add(new Point((int)lastPosition.getX(), i));
            }
        }
		for (int i = 0; i < PixelTester.SIZE_OF_BOARD; i++) {
			if (map[i][(int)lastPosition.getY()] == 0) {
                actionsPoint.add(new Point(i,(int)lastPosition.getY()));
			}
		}
        Point[] actions = actionsPoint.toArray(new Point[actionsPoint.size()]);  // arraylist -> array
		return actions;
	}
	/* 만들어야 할 메소드들
	private Player AlphaBetaSearch() { Player v = MaxValue(); }
	private int MaxValue() { ... }
	private int MinValue() { ... }
	private int Result(int[][] map, int player) { return result }
	private boolean TerminalTest() { ... }
	private Utility() { ... }
	*/

	// 2차원 배열 복사 메소드
    // 사용법: arrayDest = ArrayCopy(arraySrc)
	private final int[][] ArrayCopy(int[][] src) {
		if (src == null) return null;
		int[][] dest = new int[src.length][src[0].length];

		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
		}

		return dest;
	}

}
