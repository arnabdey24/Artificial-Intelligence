package hierarchicalClustering;

import java.util.Arrays;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        List<Double> vals= Arrays.asList(1d,2d,10d);
        SingleLinkage<Double> la = new SingleLinkage<>((o, o2) -> Math.abs(o - o2));
        ClusterAlgorithm<Double> h = new ClusterAlgorithm<Double>(vals, la);
        h.cluster();
        System.out.println(h.getFirstCluster());
        System.out.println("Top Cluster distance: "+h.getFirstCluster().distance);

    }
}
