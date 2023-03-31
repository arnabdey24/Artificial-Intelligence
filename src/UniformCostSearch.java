import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class UniformCostSearch {
    private static ArrayList<ArrayList<Pair>> map;

    public static void main(String[] args) {
        map = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            map.add(new ArrayList<>());
        }

        map.get(0).add(new Pair(1, 2));
        map.get(0).add(new Pair(2, 4));
        map.get(1).add(new Pair(3, 7));
        map.get(1).add(new Pair(2, 1));
        map.get(2).add(new Pair(4, 3));
        map.get(3).add(new Pair(5, 1));
        map.get(4).add(new Pair(3, 2));
        map.get(4).add(new Pair(5, 5));

        ArrayList<Integer> path = uniformSearch(0, 5);

        System.out.println("OPTIMAL PATH: " + path);
    }

    private static ArrayList<Integer> uniformSearch(int src, int dest) {
        HashMap<Integer, Integer> parent = new HashMap<>();
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        int[] distance = new int[map.size()];
        for (int i = 0; i < map.size(); i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        pq.add(new Pair(src, 0));
        distance[src] = 0;
        parent.put(src, src);

        while (!pq.isEmpty()) {
            Pair pair = pq.poll();
            int u = pair.getNode();
            int w = pair.getCost();
            if (w <= distance[u]) {
                for (Pair v : map.get(u)) {
                    if (distance[u] + v.getCost() < distance[v.getNode()]) {
                        distance[v.getNode()] = distance[u] + v.getCost();
                        pq.add(new Pair(v.getNode(), distance[v.getNode()]));
                        parent.put(v.getNode(), u);
                    }
                }
            }
        }

        ArrayList<Integer> path = new ArrayList<>();
        int cur = dest;
        while (cur != src) {
            path.add(cur);
            cur = parent.get(cur);
        }
        path.add(src);

        Collections.reverse(path);

        return path;

    }

    static class Pair implements Comparable<Pair> {
        private int node;
        private int cost;

        public Pair(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        public int getNode() {
            return node;
        }

        public void setNode(int node) {
            this.node = node;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        @Override
        public int compareTo(Pair o) {
            return this.cost - o.cost;
        }
    }
}
