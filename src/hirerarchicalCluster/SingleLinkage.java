package hirerarchicalCluster;

import java.util.HashMap;

public class SingleLinkage {

    final HashMap<Pair<Integer, Integer>, Double> pairwise_distances = new HashMap<>();

    public double calc(Cluster a, Cluster b) {
        double min = Double.MAX_VALUE;
        for (Integer p1 : a.getClusterElements())
            for (Integer p2 : b.getClusterElements()) {
                Pair<Integer, Integer> p = new Pair<>(p1, p2);
                double dis = -1;
                if (this.pairwise_distances.containsKey(p)) {
                    dis = pairwise_distances.get(p);
                } else {
                    dis = distance(p1, p2);
                    pairwise_distances.put(p, dis);
                }

                if (dis < min)
                    min = dis;
            }
        return min;
    }

    private double distance(Integer a, Integer b) {
        return Math.abs(a - b);
    }


}
