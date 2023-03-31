package genetic;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {

    private static Random rng;

    public Chromosome() {
        rng = new Random();
    }

    public Chromosome(ArrayList<Item> items) {
        this();
        for (Item item : items) {
            Item newItem = item.clone();
            newItem.setIncluded(rng.nextBoolean());
            add(newItem);
        }
    }


    public Chromosome crossover(Chromosome other) {
        Chromosome cross = new Chromosome();
        for (int i = 0; i < this.size(); i++) {
            int n = rng.nextInt(10) + 1;
            if (n <= 5) {
                cross.add(get(i).clone());
            } else {
                cross.add(other.get(i).clone());
            }
        }

        return cross;
    }


    public void mutate() {
        for (Item item : this) {
            int n = rng.nextInt(10) + 1;
            if (n == 1) {
                item.setIncluded(!item.isIncluded());
            }
        }
    }


    public int getFitness() {
        int fitness = 0;
        double totalWeights = 0;

        for (Item item : this) {
            if (item.isIncluded()) {
                totalWeights += item.getWeight();
                fitness += item.getValue();
            }
        }

        return (totalWeights > 10) ? 0 : fitness;
    }


    public int compareTo(Chromosome o) {
        return Integer.compare(o.getFitness(), getFitness());

    }


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : this) {
            if (item.isIncluded()) {
                stringBuilder.append(item).append(",\n");
            }
        }

        stringBuilder.append("Fitness: ").append(getFitness());
        return stringBuilder.toString();
    }

}
