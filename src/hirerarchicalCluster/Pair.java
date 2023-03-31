package hirerarchicalCluster;

import java.util.Objects;

public class Pair<A, B> {
    public final A first;
    public final B second;

    public Pair(final A a, final B b) {
        first = a;
        second = b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first) + Objects.hash(second);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Pair.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Pair<A, B> other = (Pair<A, B>) obj;
        if (this.first.equals(other.first) && this.second.equals(other.second))
            return true;
        if (this.first.equals(other.second) && this.second.equals(other.first))
            return true;
        return false;

    }

    @Override
    public String toString() {
        return "(" + first.toString() + "," + second.toString() + ")";
    }
}
