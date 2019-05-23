import java.awt.*;
import java.util.ArrayList;

public class PixelPlayerTester extends Player {
    static final int LEVEL = 3;		// 알파베타 알고리즘의 노드 탐색 깊이

	PixelPlayerTester(int[][] map) { super(map); }
	public Point nextPosition(Point lastPosition) {
		int x = (int)lastPosition.getX(), y = (int)lastPosition.getY();
		int player = map[(int)currentPosition.getX()][(int)currentPosition.getY()] == 1 ? 1 : 2;	// 현재 플레이어가 1번 돌인지 2번 돌인지 판단
		Point nextPosition;

        // 메인 필요
		nextPosition = new Point(x, y);
		return nextPosition;
	}

	// 주어진 상태에서 가능한 수들의 집합을 돌려준다.
	private Point[] Actions(Point lastPosition) {
		ArrayList<Point> actionsPoint = new ArrayList<Point>();
		// 다음 돌이 놓을 수 있는(map의 값이 0인) 곳 검색
		for (int i = 0; i < PixelTester.SIZE_OF_BOARD; i++) {
			if (map[(int) lastPosition.getX()][i] == 0) {
				actionsPoint.add(new Point((int) lastPosition.getX(), i));
			}
		}
		for (int i = 0; i < PixelTester.SIZE_OF_BOARD; i++) {
			if (map[i][(int)lastPosition.getY()] == 0) {
				actionsPoint.add(new Point(i,(int)lastPosition.getY()));
			}
		}
		return actionsPoint.toArray(new Point[actionsPoint.size()]);  // arraylist -> array
	}
	// 알파베타 가지치기 알고리즘
	private void AlphaBetaSearch(Point lastPosition, int player) {
        MaxValue(LEVEL, map, lastPosition, player, -100, +100);
	}
	private int MaxValue(int level, int[][] map, Point lastPosition, int player, int alpha, int beta) {
	    if (TerminalTest(lastPosition, player, map)) return 100;
	    if (level == 0) return Result(map, player);
        int max = -100;

        Point[] actions = Actions(lastPosition);
        for (Point i : actions) {
            int [][] myMap = ArrayCopy(map);
            myMap[(int)i.getX()][(int)i.getY()] = player;
            int result = MinValue(--level, myMap, i, NotPlayer(player), alpha, beta);
            if (result > max) max = result;
        }
        return max;
	}
	private int MinValue(int level, int[][] map, Point lastPosition, int player, int alpha, int beta) {
		if (TerminalTest(lastPosition, player, map)) return -100;
		if (level == 0) return Result(map, player);
		int min = 100;

		Point[] actions = Actions(lastPosition);
		for (Point i : actions) {
			int [][] myMap = ArrayCopy(map);
			myMap[(int)i.getX()][(int)i.getY()] = player;
			int result = MaxValue(--level, myMap, i, NotPlayer(player), alpha, beta);
			if (result < min) min = result;
		}
		return min;
	}

	private int Result(int[][] map, int player) { return 0; }
	// 2차원 배열 복사 메소드
	// 사용법: arrayDest = ArrayCopy(arraySrc)
	private int[][] ArrayCopy(int[][] src) {
		if (src == null) return null;
		int[][] dest = new int[src.length][src[0].length];

		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
		}

		return dest;
	}
	// 상대 플레이어 번호를 출력하는 메소드
	private int NotPlayer(int player) { return player == 1 ? 2 : 1;	}
	// 이긴 상태인지 확인 하는 메소드
	private boolean TerminalTest(Point lastPosition, int player, int[][] map) {
		int x = (int) lastPosition.getY();
		int y = (int) lastPosition.getX();

		for (int i = 0; i < 9; i++) {
			int count = 0;
			Point[] newPoint = new Point[3];
			for (int j = 0; j < 3; j++) {
				newPoint[j] = new Point();
				newPoint[j].setLocation(PixelTester.inspectionList[i][j][1] + x, PixelTester.inspectionList[i][j][0] + y);
				if (newPoint[j].y >= 0 && newPoint[j].y < PixelTester.SIZE_OF_BOARD && newPoint[j].x >= 0 && newPoint[j].x < PixelTester.SIZE_OF_BOARD) {
					if (map[newPoint[j].y][newPoint[j].x] == player) {
						count += 1;
					}
				}
			}
			if (count == 3) return true;
		}
		return false;
	}
}
