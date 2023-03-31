package genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {

    public static ArrayList<Item> loadData() {
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("Phone",0.25,600));
        items.add(new Item("Laptop",10,2000));
        items.add(new Item("Jewelry",0.5,500));
        items.add(new Item("TV",0.5,300));
        items.add(new Item("Play Station",3,500));
        items.add(new Item("Clock",5,1500));
        items.add(new Item("Camera",2,400));

        return items;
    }


    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {
        ArrayList<Chromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            chromosomes.add(new Chromosome(items));
        }

        return chromosomes;
    }


    public static void main(String[] args){
        Random random = new Random();
        ArrayList<Item> items = loadData();

        ArrayList<Chromosome> currentPopulation = initializePopulation(items, 10);

        for (int k = 0; k < 20; k++) {

            ArrayList<Chromosome> nextGeneration = new ArrayList<>(currentPopulation);

            for (int i = 0; i < currentPopulation.size(); i++) {
                int a = random.nextInt(currentPopulation.size());
                int b = random.nextInt(currentPopulation.size());
                nextGeneration.add(currentPopulation.get(a).crossover(currentPopulation.get(b)));
            }

            for (int i = 0; i < nextGeneration.size() / 10; i++) {
                nextGeneration.get(random.nextInt(nextGeneration.size())).mutate();
            }

            Collections.sort(nextGeneration);

            currentPopulation.clear();
            for (int i = 0; i < 10; i++) {
                currentPopulation.add(nextGeneration.get(i));
            }

        }

        Collections.sort(currentPopulation);
        System.out.println("Solution: \n-----------------\n"+currentPopulation.get(0));

    }

}
