import java.awt.*;
import java.util.ArrayList;
public class PixelPlayer08 extends Player {
    static final int DEPTH = 8;//알파베타 알고리즘의 노드 탐색 깊이
    static int originPlayer;
    static int[] temp;
    static int[] temp2by2 = new int[4]; //temp 배열을 선언해준다.
    static int[] temp3by3 = new int[9]; //temp 배열을 선언해준다.s
    static Point[] newPoint = new Point[] { new Point(0, 0), new Point(0, 0), new Point(0, 0) };

    /*평가함수에서 가중치를 부여할 모형*/
    static int onePattern2X2[][] = {{0, 1, -1, 1}, {1, 0, -1, 1}, {1, 1, -1, 0},
                                    {1, -1, 1, 0}, {1, -1, 0, 1}, {0, -1, 1, 1}};
    static int onePattern3X3[][] = {{1, -1, -1, -1, 1, -1, -1, -1, 0},
                                    {0, -1, -1, -1, 1, -1, -1, -1, 1},
                                    {1, -1, -1, -1, 0, -1, -1, -1, 1}};

    PixelPlayer08(int[][] map) { super(map); }

    public Point nextPosition(Point lastPosition) {
        if (lastPosition.getX() == 4 && lastPosition.getY() == 3) return new Point(4, 2);   // 시간을 줄이기 위해 공격 첫 수 지정
        originPlayer = map[(int) currentPosition.getX()][(int) currentPosition.getY()];
        //map에서 currentPosition의 값을 받아서 originplayer에 어떤 플레어가 돌을 놓았는지 저장한다.
        return AlphaBetaSearch(DEPTH, map, lastPosition, originPlayer);
        // 알파베타검색함수를 사용해서 depth, 맵, 마지막위치, 플레이어를 입력해주면 다음에 놓을 돌의 위치를 반환해준다.
    }

    // 주어진 상태에서 가능한 수들의 집합을 돌려준다.
    private ArrayList<Point> Actions(Point lastPosition, int[][] map) {
        ArrayList<Point> actionsPoint = new ArrayList<Point>(); // 다음 돌이 놓을 수 있는(map의 값이 0인) 곳 검색
        for (int i = 0; i < PixelTester.SIZE_OF_BOARD; i++) {
            if (map[(int)lastPosition.getX()][i] == 0) {
                actionsPoint.add(new Point((int) lastPosition.getX(), i));  //마지막돌이 놓인곳에서 돌을 놓을 수 있는 모든 행 조사.
            }
        }
        for (int i = 0; i < PixelTester.SIZE_OF_BOARD; i++) {
            if (map[i][(int) lastPosition.getY()] == 0) {
                actionsPoint.add(new Point(i, (int) lastPosition.getY()));  //마지막 돌이 놓인 곳에서 돌을 놓을 수 있는 모든 열 조사.
            }
        }
        return actionsPoint;
    }

    private Point AlphaBetaSearch(int depth, int[][] map, Point lastPosition, int player) {
        int max = -101;
        // max의 값은 -101로 지정해서 중간에 포기하는 수가 나오지 않게한다.

        Point nextDolPosition = new Point(0, 0);    //다음 돌은 (0,0)으로 지정한다
        ArrayList<Point> actions = Actions(lastPosition, map);      // 현재 위치에서 둘 수 있는 위치들 생성

        for (Point i : actions) {
            int[][] myMap = ArrayCopy(map); //myMap이라는 2차원배열에 map을 저장한다.

            myMap[(int) i.getX()][(int) i.getY()] = player; // 돌 i를 둔 새로운 맵 생성

            int result = MinValue(depth, myMap, i, player, -100, 100);  // MinValue 호출
            if (result > max) {
                max = result;
                nextDolPosition.setLocation(i.getX(), i.getY());
            }
            //만약 반환된 값이 max보다 크면 그때의 i값을 다음돌의 위치로 저장한다.
        }
        return nextDolPosition; //다음 놓을 돌의 위치 반환
    }

    // Max값 반환해주는 메소드
    private int MaxValue(int depth, int[][] map, Point lastPosition, int player, int alpha, int beta) {
        if (TerminalTest(lastPosition, player, map)) return -100;   // player가 이겼을 경우 -100 반환
        if (depth == 0) return Result(map, originPlayer);           // leaf 노드에 도달하면 평가함수 호출
        int max = -100;

        ArrayList<Point> actions = Actions(lastPosition, map);   // 현재 위치에서 둘 수 있는 위치들 생성

        // 노드 생성
        for (Point i : actions) {
            int[][] myMap = ArrayCopy(map); // myMap에 map을 저장
            myMap[(int) i.getX()][(int) i.getY()] = NotPlayer(player);  //돌 i를 둔 새로운 맵 생성, NotPlayer함수를 통해서 상대돌의 위치를 입력.
            int result = MinValue(depth - 1, myMap, i, NotPlayer(player), alpha, beta);    // MinValue 호출(재귀적으로 min - max - min - max - ... 호출), depth의 깊이는 min-max가 재귀적으로 호출 될 때마다 1씩 줄여준다.

            if (result > max) max = result; // max값 갱신
            if (max >= beta) return max;    // max가 beta보다 크면 유망하지 않으므로 검사 중지
            if (max > alpha) alpha = max;   // alpha를 가장 큰 값으로 갱신
        }
        return max;
    }
    // Min값 반환해주는 메소드
    private int MinValue(int depth, int[][] map, Point lastPosition, int player, int alpha, int beta) {
        if (TerminalTest(lastPosition, player, map)) return 100;    // player가 이겼을 경우 100 반환
        if (depth == 0) return Result(map, originPlayer);           // leaf 노드에 도달하면 평가함수 호출
        int min = 100;

        ArrayList<Point> actions = Actions(lastPosition, map);   // 현재 위치에서 둘 수 있는 위치들 생성
        for (Point i : actions) {
            int[][] myMap = ArrayCopy(map); // myMap에 map을 저장
            myMap[(int) i.getX()][(int) i.getY()] = NotPlayer(player);  //돌 i를 둔 새로운 맵 생성, NotPlayer함수를 통해서 상대돌의 위치를 입력.
            int result = MaxValue(depth - 1, myMap, i, NotPlayer(player), alpha, beta); // MaxValue 호출, depth의 깊이--;

            if (result < min) min = result; // min값 갱신
            if (min <= alpha) return min;   // alpha가 min보다 크면 유망하지 않으므로 검사중지
            if (min < beta) beta = min;     // beta가 min보다 크면 beta에 min 저장
        }
        return min;
    }

    private int Result(int[][] map, int player) {
        // mycount, oppocount라는 변수는 맵의 모형을 보고 누가 더 이길 수 있는 모형을 더 많이 가지고 있는지 센다.
        int myCount = 0;    //내가 가지고 있는 모형의 갯수.
        int oppoCount = 0;  //상대가 가지고 있는 모형의 갯수.
        int result = 0;
        int temp[] = null;  //temp배열은 2*2 혹은 3*3 으로 구현되있는 모형들을 1차원 배열에 저장하는 용도로 사용.
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                temp = copyPattern(2, i, j, map); // 2*2에서 이길수 있는 모형들을 copyPattern이라는 함수를 사용해 1차원 배열인 temp에 저장한다.
                myCount += arrayCompare(player, temp, onePattern2X2);
                //현재 맵을 2*2로 자르는 배열인 onePattern2X2 배열과 1차원 배열인 temp의 비교를 통해서 맵 전체에서 내가 가진 이길 수 있는 모형의 개수를 myCount 인덱스에 저장해준다.
                oppoCount += arrayCompare(NotPlayer(player), temp, onePattern2X2); // 같은 과정을 oppoCount에도 적용한다.
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                temp = copyPattern(3, i, j, map);   //3*3에서 이길수 있는 모형들을 copyPattern이라는 함수를 사용해 1차원 배열인 temp에 저장한다.
                myCount += arrayCompare(player, temp, onePattern3X3);
                oppoCount += arrayCompare(NotPlayer(player), temp, onePattern3X3); //현재 맵을 3*3로 자르는 onePattern3X3 배열과 1차원 배열인 temp의 비교를 통해서 맵 전체에서 3*3 상황에서의 이길 수 있는 모형의 개수를 myCount 인덱스에 저장해준다.
            }
        }
        result = myCount - oppoCount;   // myCount에서 oppoCount를 빼서 result에 저장한다. 이 과정을 통해서 뎁스가 0 이 되었을 때, 누가 더 유리한 상황인지를 알 수 있다.
        return result; //result 반환
    }

    //배열을 복사하는 함수
    private int[] copyPattern(int size, int row, int col, int[][] map) {
        if (size == 2) temp = temp2by2;
        else temp = temp3by3;
        int cut = 0; //cut이라는 인덱스를 생성한다. temp는 1차원배열, map은 2차원 배열이기 때문에, map의 값을 temp에 받기 위한 인덱스로 사용한다.
        for (int i = row; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                temp[cut] = map[i][j];
                cut++;
            }
        }
        return temp; //temp 반환
    }

    //onePattern2X2[][], onePattern3X3[][]과 temp[]를 비교하는 함수
    private int arrayCompare(int player, int[] temp, int[][] pattern) {
        int cnt = 0, midCnt = 0;
        for (int i = 0; i < pattern.length; i++) {  //pattern 배열이 가지고 있는 행의 개수만큼 반복
            midCnt = 0; //중간카운트, 한 행을 검사할 때마다 0으로 초기화
            for (int j = 0; j < pattern[0].length; j++) {   //행의 열을 검사
                if (pattern[i][j] == -1) midCnt++;  //만약 pattern에 -1로 저장된 값이 있으면 카운트++
                else if (pattern[i][j] == 1 && temp[j] == player) midCnt++;
                    //만약 pattern[i][j]에서 내가 놓은 돌인 1이 있고, temp배열에서 같은 위치의 값이 1이면 midcnt++;
                else if (pattern[i][j] == 0 && temp[j] == 0) midCnt++;
                    //만약 pattern[i][j]에서 놓지 않은 공간을 의미하는 0이 있는데 temp[j]에서 같은 곳의 값이 0이면 midCnt++;
                else break;
            }
            if (midCnt == pattern[0].length) cnt++; // 만약 midCnt의 값과 pattern의 열의 개수가 같다면 cnt증가.
        }
        return cnt;
    }

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
    private int NotPlayer(int player) {
        return player == 1 ? 2 : 1;
    }

    // 이긴 상태인지 확인 하는 메소드, 조교님이 올리신 코드를 사용하였습니다.
    private boolean TerminalTest(Point lastPosition, int player, int[][] map) {
        int x = (int) lastPosition.getY();
        int y = (int) lastPosition.getX();
        //y의 값은 인덱스 x에서 받고, x의 값은 인덱스 y에서 받음.
        for (int i = 0; i < 9; i++) {
            int count = 0;
            for (int j = 0; j < 3; j++) {
                //이전에 들어온 값에 이기는값을 더해서 종료되는값이 나오면 true, 아니면 false 반환
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
