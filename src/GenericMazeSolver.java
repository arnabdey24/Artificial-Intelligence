import java.io.StringReader;
import java.util.*;

public class GenericMazeSolver {

    public static void main(String[] args) {
        char[][] maze = new char[4][5];

        Scanner scanner = new Scanner(new StringReader(
                "....#\n" +
                   ".##.#\n" +
                   ".#.@.\n" +
                   "...##"));

        int i = 0;
        while (scanner.hasNextLine()) {
            maze[i++] = scanner.nextLine().toCharArray();
        }

        if (solvable(maze)) {
            ArrayList<String> path = solve(maze);
            System.out.println(path);
        } else {
            System.out.println("Not solvable");
        }

    }

    private static ArrayList<String> solve(char[][] maze) {
        HashSet<Coordinate> possibleStates = new HashSet<>(possibleStates(maze));
        BeliefState initial = new BeliefState(possibleStates,null, "initial");

        leftBeliefState(initial,maze);

        Queue<BeliefState> queue = new LinkedList<>();
        queue.add(initial);

        while (!queue.isEmpty()) {
            BeliefState u = queue.poll();

            if(u.getStates().size()==1 && u.getStates().contains(goalState(maze))){
                System.out.println("solved");
                ArrayList<String> path=new ArrayList<>();
                while (!u.getFrom().equals("initial")){
                    path.add(u.getFrom());
                    u=u.getParent();
                }

                Collections.reverse(path);
                return path;
            }

            queue.add(leftBeliefState(u,maze));
            queue.add(rightBeliefState(u,maze));
            queue.add(upBeliefState(u,maze));
            queue.add(downBeliefState(u,maze));
        }

        return null;
    }

    private static BeliefState leftBeliefState(BeliefState beliefState,char[][] maze) {
        HashSet<Coordinate> coordinates=new HashSet<>();

        for (Coordinate state : beliefState.getStates()) {
            Coordinate left=state.left();
            if(maze[left.getX()][left.getY()]=='#'){
                coordinates.add(state);
            }else{
                coordinates.add(left);
            }
        }

        return new BeliefState(coordinates,beliefState,"LEFT");
    }

    private static BeliefState rightBeliefState(BeliefState beliefState,char[][] maze) {
        HashSet<Coordinate> coordinates=new HashSet<>();

        for (Coordinate state : beliefState.getStates()) {
            Coordinate right=state.right();
            if(maze[right.getX()][right.getY()]=='#'){
                coordinates.add(state);
            }else{
                coordinates.add(right);
            }
        }

        return new BeliefState(coordinates,beliefState,"RIGHT");
    }

    private static BeliefState downBeliefState(BeliefState beliefState,char[][] maze) {
        HashSet<Coordinate> coordinates=new HashSet<>();

        for (Coordinate state : beliefState.getStates()) {
            Coordinate down=state.down();
            if(maze[down.getX()][down.getY()]=='#'){
                coordinates.add(state);
            }else{
                coordinates.add(down);
            }
        }

        return new BeliefState(coordinates,beliefState,"DOWN");
    }

    private static BeliefState upBeliefState(BeliefState beliefState,char[][] maze) {
        HashSet<Coordinate> coordinates=new HashSet<>();

        for (Coordinate state : beliefState.getStates()) {
            Coordinate up=state.up();
            if(maze[up.getX()][up.getY()]=='#'){
                coordinates.add(state);
            }else{
                coordinates.add(up);
            }
        }

        return new BeliefState(coordinates,beliefState,"UP");
    }


    private static boolean solvable(char[][] maze) {
        int numberOfPossibleState = possibleStates(maze).size();

        Coordinate goalState = goalState(maze);

        HashSet<Coordinate> visited = new HashSet<>();
        Queue<Coordinate> queue = new LinkedList<>();

        queue.add(goalState);
        visited.add(goalState);

        while (!queue.isEmpty()) {
            Coordinate u = queue.poll();

            HashMap<String, Coordinate> childCoordinates = childCoordinates(u, maze);

            for (String s : childCoordinates.keySet()) {
                Coordinate v = childCoordinates.get(s);
                if (!visited.contains(v)) {
                    queue.add(v);
                    visited.add(v);
                }
            }
        }

        return visited.size() == numberOfPossibleState;
    }

    private static HashMap<String, Coordinate> childCoordinates(Coordinate coordinate, char[][] maze) {
        HashMap<String, Coordinate> childCoordinates = new HashMap<>();

        childCoordinates.put("LEFT", coordinate.left());
        childCoordinates.put("RIGHT", coordinate.right());
        childCoordinates.put("UP", coordinate.up());
        childCoordinates.put("DOWN", coordinate.down());

        for (String s : childCoordinates.keySet()) {
            Coordinate child = childCoordinates.get(s);
            if (maze[child.getX()][child.getY()] == '#') {
                childCoordinates.put(s, coordinate);
            }
        }

        return childCoordinates;
    }

    private static Coordinate goalState(char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '@') {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    private static ArrayList<Coordinate> possibleStates(char[][] maze) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '.' || maze[i][j] == '@') {
                    coordinates.add(new Coordinate(i, j));
                }
            }
        }

        return coordinates;
    }

    private static class BeliefState {
        private HashSet<Coordinate> states;
        private BeliefState parent;
        private String from;

        public BeliefState(HashSet<Coordinate> states,BeliefState parent, String from) {
            this.states = states;
            this.from = from;
            this.parent=parent;
        }

        public HashSet<Coordinate> getStates() {
            return states;
        }

        public BeliefState getParent() {
            return parent;
        }

        public String getFrom() {
            return from;
        }
    }

    private static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Coordinate left() {
            if (y == 0) {
                return this;
            }
            return new Coordinate(x, y - 1);
        }

        public Coordinate right() {
            if (y == 4) {
                return this;
            }
            return new Coordinate(x, y + 1);
        }

        public Coordinate down() {
            if (x == 3) {
                return this;
            }
            return new Coordinate(x + 1, y);
        }

        public Coordinate up() {
            if (x == 0) {
                return this;
            }
            return new Coordinate(x - 1, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinate)) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{" +
                      x +","+
                      y +
                    '}';
        }
    }


}
