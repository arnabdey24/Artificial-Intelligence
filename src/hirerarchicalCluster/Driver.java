package hirerarchicalCluster;

import java.util.Arrays;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        List<Integer> marks= Arrays.asList(50,41,77,55,53,66,78,81,61,62,20,33,39,40,0,46,48,50,31,65,31,59,79,48,35,56,67,71,73,64);
        SingleLinkage singleLinkage = new SingleLinkage();
        ClusterAlgorithm h = new ClusterAlgorithm(marks, singleLinkage);
        h.cluster();
        System.out.println("Clusters: "+h.getFirstCluster());
    }
}
