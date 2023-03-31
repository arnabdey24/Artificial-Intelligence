import java.io.File;
import java.util.*;

public class KMeansAlgorithm {

    private static final int MAX_ITERATION = 1000;
    private static ArrayList<ArrayList<Double>> dataTrain;

    public static void main(String[] args) {
        dataTrain = new ArrayList<>();

        readFileTrain("Wholesalecustomersdata_processed.csv");

        int k = 5;
        int n = 5;

        ArrayList<Result> results = kmean(dataTrain, k, n);

        int i = 1;
        for (Result result : results) {
            System.out.println("#iteration-" + i);
            System.out.println("Objective value: " + result.getObjective());

            System.out.println("TestPoint-index\t\t\tCluster#");
            System.out.println("-----------------------------------------");
            for (Integer index : result.getParent().keySet()) {
                System.out.println((index + 1) + "\t\t\t\tcluster-" + (result.getParent().get(index) + 1));
            }
            System.out.println();
            i++;
        }
    }

    private static ArrayList<Result> kmean(ArrayList<ArrayList<Double>> dataTrain, int k, int n) {
        ArrayList<Result> results = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            ArrayList<Centroid> centroids = randomCentroids(k, dataTrain);
            ArrayList<Centroid> nextCentroids = null;

            HashMap<Integer, Integer> parent = new HashMap<>();

            int p = 0;
            while (p < MAX_ITERATION) {
                for (int j = 0; j < dataTrain.size(); j++) {
                    int nearestCentroid = nearestCentroid(dataTrain.get(j), centroids);
                    parent.put(j, nearestCentroid);
                }

                nextCentroids = relocateCentroids(k, dataTrain, parent);

                if(centroids.equals(nextCentroids)){
                    break;
                }

                centroids.clear();
                centroids.addAll(nextCentroids);

                p++;
            }

            double objective = objective(parent, dataTrain, centroids);

            results.add(new Result(parent, objective));

        }

        return results;
    }

    private static double objective(HashMap<Integer, Integer> parent, ArrayList<ArrayList<Double>> dataTrain, ArrayList<Centroid> centroids) {
        double objective = 0;
        for (Integer index : parent.keySet()) {
            objective += getDistance(dataTrain.get(index), centroids.get(parent.get(index)).getPoints());
        }

        return objective;
    }

    private static ArrayList<Centroid> relocateCentroids(int k, ArrayList<ArrayList<Double>> dataTrain, HashMap<Integer, Integer> parent) {
        HashMap<Integer, ArrayList<ArrayList<Double>>> pointsByCentroid = new HashMap<>();
        for (int i = 0; i < k; i++) {
            pointsByCentroid.put(i, new ArrayList<>());
        }

        for (Integer index : parent.keySet()) {
            pointsByCentroid.get(parent.get(index)).add(dataTrain.get(index));
        }

        ArrayList<Centroid> centroids = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            centroids.add(new Centroid(meanOfPoints(pointsByCentroid.get(i))));
        }

        return centroids;

    }

    private static ArrayList<Double> meanOfPoints(ArrayList<ArrayList<Double>> points) {
        ArrayList<Double> mean = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        int size = points.size();
        if (size != 0) {
            for (ArrayList<Double> point : points) {
                for (int i = 0; i < point.size(); i++) {
                    mean.set(i, mean.get(i) + point.get(i));
                }
            }

            for (int i = 0; i < mean.size(); i++) {
                mean.set(i, mean.get(i) / size);
            }

        }

        return mean;
    }

    private static int nearestCentroid(ArrayList<Double> point, ArrayList<Centroid> centroids) {
        int nearestIndex = 0;
        for (int i = 1; i < centroids.size(); i++) {
            if (getDistance(point, centroids.get(i).getPoints()) < getDistance(point, centroids.get(nearestIndex).getPoints())) {
                nearestIndex = i;
            }
        }

        return nearestIndex;
    }

    private static ArrayList<Centroid> randomCentroids(int k, ArrayList<ArrayList<Double>> dataTrain) {
        ArrayList<Centroid> centroids = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(dataTrain.size());
            centroids.add(new Centroid(dataTrain.get(index)));
        }

        return centroids;
    }


    public static double getDistance(ArrayList<Double> a, ArrayList<Double> b) {
        double dist = 0;
        for (int i = 0; i < a.size(); i++) {
            double c = Math.abs(a.get(i) - b.get(i));
            dist += Math.pow(c, 2);
        }
        dist = Math.sqrt(dist);
        return dist;
    }


    public static int readFileTrain(String inputFile) {
        try {
            File file = new File(inputFile);
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine()) {
                String thisline = sc.nextLine();
                String[] values = thisline.split(",");
                double[] temp = Arrays.stream(values).mapToDouble(Double::parseDouble).toArray();
                ArrayList<Double> data = new ArrayList<>();
                for (double v : temp) {
                    data.add(v);
                }
                dataTrain.add(data);
                i++;
            }
            sc.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getClass());
        }
        return 0;
    }

    public static class Centroid {
        private ArrayList<Double> points;

        public Centroid(ArrayList<Double> points) {
            this.points = points;
        }

        public ArrayList<Double> getPoints() {
            return points;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Centroid)) return false;
            Centroid centroid = (Centroid) o;
            return this.points.equals(centroid.points);
        }

        @Override
        public int hashCode() {
            return Objects.hash(points);
        }

        @Override
        public String toString() {
            return points.toString();
        }
    }

    public static class Result {
        private HashMap<Integer, Integer> parent;
        private double objective;

        public Result(HashMap<Integer, Integer> parent, double objective) {
            this.parent = parent;
            this.objective = objective;
        }

        public HashMap<Integer, Integer> getParent() {
            return parent;
        }

        public double getObjective() {
            return objective;
        }
    }
}
