public class SimulatedAnnealing {

    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        return Math.exp((energy - newEnergy) / temperature);
    }

    public static double f(double x){
        return 20*x*x*x*x+8*x*x*x-18*x*x;
    }

    public static void main(String[] args) {

        double temp = 10000;
        double coolingRate = 0.003;

        double currentSolution = Math.random()*10;
        if(Math.random()<0.5){
            currentSolution*=-1;
        }

        System.out.println("Initial solution(Global Minima): " +currentSolution+"  "+ f(currentSolution));

        double best = currentSolution;

        while (temp > 1) {

            double rnd=Math.random();
            if(Math.random()<0.5){
                rnd*=-1;
            }
            double newSolution = currentSolution + rnd;

            double currentEnergy = f(currentSolution);
            double neighbourEnergy = f(newSolution);

            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = newSolution;
            }

            if (f(currentSolution) < f(best)) {
                best = currentSolution;
            }

            temp *= 1 - coolingRate;
        }

        System.out.println("Final solution(Global Minima): " + best+"  "+f(best));
    }
}