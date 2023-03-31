package hirerarchicalCluster;

import java.util.*;
import java.util.Map.Entry;

public class ClusterAlgorithm {

    List<Cluster> clusters;
    SingleLinkage la;
    HashMap<Pair<Cluster, Cluster>, Double> linkage_values = new HashMap<>();
    int initialSize;
    int max_linkage = -1;


    public ClusterAlgorithm(Collection<Integer> objects, SingleLinkage la, int max_linkage) {
        this(objects, la);
        this.max_linkage = max_linkage;
    }

    public ClusterAlgorithm(Collection<Integer> objects, SingleLinkage la) {
        this.la = la;
        this.initialSize = objects.size();
        clusters = new ArrayList<>(objects.size());
        Iterator<Integer> it = objects.iterator();
        for (int i = 0; i < objects.size(); i++) {
            clusters.add(i, new Cluster(it.next()));
        }
        for (Cluster c_1 : clusters) {
            for (Cluster c_2 : clusters) {
                if (c_1 == c_2)
                    continue;
                Pair<Cluster, Cluster> linkagepair = new Pair<>(c_1, c_2);
                if (!linkage_values.containsKey(linkagepair))
                    linkage_values.put(linkagepair, la.calc(c_1, c_2));
            }
        }
    }

    public Collection<Cluster> getClusters() {
        return Collections.unmodifiableList(clusters);
    }

    public Cluster getFirstCluster() {
        return getClusters().iterator().next();
    }


    public void cluster() {
        if (clusters.size() <= 1)
            return;

        Entry<Pair<Cluster, Cluster>, Double> min = Collections.min(linkage_values.entrySet(),
                Comparator.comparingDouble(Entry::getValue));

        if (max_linkage >= 0 && min.getValue() > max_linkage)
            return;
        Pair<Cluster, Cluster> p = min.getKey();

        Cluster merged = new Cluster(p.first, p.second, min.getValue());
        boolean t = clusters.remove(p.first);
        boolean tt = clusters.remove(p.second);

        List<Pair<Cluster, Cluster>> remove_pairs = new LinkedList<>();
        for (Pair<Cluster, Cluster> c : linkage_values.keySet()) {
            if (p.first == c.first || p.first == c.second || p.second == c.second || p.second == c.first)
                remove_pairs.add(c);
        }
        for (Pair<Cluster, Cluster> c : remove_pairs)
            linkage_values.remove(c);

        for (Cluster c_1 : clusters) {
            Pair<Cluster, Cluster> p_merged = new Pair<>(c_1, merged);
            linkage_values.put(p_merged, la.calc(p_merged.first, p_merged.second));
        }
        clusters.add(merged);
        cluster();
    }


}



