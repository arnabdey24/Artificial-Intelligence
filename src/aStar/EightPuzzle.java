package aStar;

import java.util.PriorityQueue;

public class EightPuzzle {

    public static int[] dx = {1, 0, -1, 0};
    public static int[] dy = {0, -1, 0, 1};
    private static int[] initialBoard = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private int[] goalBoard;

    public EightPuzzle(int[] board) {
        this.goalBoard = new int[9];
        System.arraycopy(board, 0, this.goalBoard, 0, 9);

        if (checkReachable()) {
            System.out.println("\nPath to the goal:");
            solve();
        } else {
            System.out.println("\nGoal state is not reachable.");
        }
    }

    private boolean checkReachable() {
        int inversions = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (goalBoard[i] > 0 && goalBoard[j] > 0 && goalBoard[i] > goalBoard[j]) {
                    inversions++;
                }
            }
        }

        return inversions % 2 == 0;
    }

    private void solve() {
        PriorityQueue<BoardState> pq = new PriorityQueue<>();

        BoardState root = new BoardState(initialBoard, 0, manhattan(initialBoard, goalBoard), null);
        pq.add(root);

        while (!pq.isEmpty()) {
            BoardState min = pq.poll();

            if (min.getH() == 0) {
                printPath(min);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int newX = min.getX() + dx[i];
                int newY = min.getY() + dy[i];

                if (newX < 3 && newX >= 0 && newY < 3 && newY >= 0) {

                    int prev = min.getX() * 3 + min.getY();
                    int next = newX * 3 + newY;
                    int[] curBoard = new int[9];

                    for (int j = 0; j < 9; j++) {
                        curBoard[j] = min.getBoard()[j];
                    }

                    int temp = curBoard[prev];
                    curBoard[prev] = curBoard[next];
                    curBoard[next] = temp;

                    int h = manhattan(curBoard, goalBoard);
                    BoardState child = new BoardState(curBoard, min.getG() + 1, h, min);

                    if(!child.equals(min.getParent()))pq.add(child);
                }
            }
        }
    }

    private int n=0;

    private void printPath(BoardState boardState) {
        n++;
        if (boardState == null){
            System.out.println(n);
            return;
        }
        printPath(boardState.getParent());
        System.out.println(boardState);
    }

    private int manhattan(int[] board1, int[] board2) {
        int distance = 0;

        for (int i = 1; i < 9; i++) {
            int index = 0;
            for (int j = 0; j < 9; j++) {
                if (board1[j] == i) {
                    index = j;
                    break;
                }
            }

            int x1 = index / 3;
            int y1 = index % 3;

            index = 0;
            for (int j = 0; j < 9; j++) {
                if (board2[j] == i) {
                    index = j;
                    break;
                }
            }

            int x2 = index / 3;
            int y2 = index % 3;

            distance += Math.abs(x1 - x2) + Math.abs(y1 - y2);

        }

        return distance;
    }
}
