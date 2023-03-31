package hirerarchicalCluster;

import java.util.Collection;
import java.util.LinkedList;

public class Cluster {
    final public Cluster right_child;
    final public Cluster left_child;
    final private Integer obj;
    private LinkedList<Integer> nested_objs = null;

    final public double distance;
    final public int size;

    public Cluster(Integer obj) {
        this.right_child=null;
        this.left_child=null;
        this.obj = obj;
        this.size = 1;
        this.distance=0;
    }

    public Cluster(Cluster rightChild, Cluster leftChild, double distance) {
        this.right_child = rightChild;
        this.left_child = leftChild;
        this.obj = null;
        this.distance = distance;
        this.size = size();

    }

    public Collection<Integer> getClusterElements() {
        if(nested_objs!=null)
            return nested_objs;
        nested_objs = new LinkedList<>();
        if (this.left_child != null)
            nested_objs.addAll(this.left_child.getClusterElements());
        if (this.right_child != null)
            nested_objs.addAll(this.right_child.getClusterElements());
        if (this.obj != null)
            nested_objs.add(obj);
        return nested_objs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Cluster.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Cluster other = (Cluster) obj;
        if(this.distance == other.distance && this.size == other.size && this.getClusterElements().equals(other.getClusterElements()))
            return true;
        return false;
    }

    private int size() {
        if (obj != null)
            return 1;
        else
            return this.left_child.size() + this.right_child.size();
    }

    @Override
    public String toString() {
        if (this.obj != null)
            return obj.toString();
        else
            return "(" + left_child.toString() + " , " + right_child.toString() + ")";
    }
}



