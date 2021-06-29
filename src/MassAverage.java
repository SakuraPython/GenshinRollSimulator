public class MassAverage {
    static int size = 1;
    static double averagePrimos = 0;
    static double averageCost = 0;
    static double averageRolls = 0;
    static final int timesToRun = 1000000;
    static final int constellation = 6;
    static final int refinement = 5;

    /**
     * I could stand to be cleaner than this but oh well
     * @author @SakuraPython#1272
     * @param args unused
     */
    public static void main(String[] args) {
        RollSim simulator = new RollSim();
        int i = 0;
        //Initialize the averages
        simulator.reset(constellation, refinement);
        simulator.RollOldSystem();
        averagePrimos = simulator.calculatePrimos();
        averageCost = simulator.calculateRealDollars();
        averageRolls = simulator.getRolls();
        simulator.reset(constellation, refinement);
        do {
            simulator.RollOldSystem();
            i = run(simulator, i);
        } while (i < timesToRun);
        System.out.println("OLD SYSTEM");
        System.out.println("You rolled an average of " + averageRolls + " times.");
        System.out.println("The average primos it took was " + averagePrimos + " and this would cost $" + averageCost + " for the old system.");
        size = 1;
        simulator.reset(constellation, refinement);
        simulator.RollNewSystem();
        averagePrimos = simulator.calculatePrimos();
        averageCost = simulator.calculateRealDollars();
        i = 0;
        do {
            simulator.RollNewSystem();
            i = run(simulator, i);
        } while (i < timesToRun);
        System.out.println("NEW SYSTEM");
        System.out.println("You rolled an average of " + averageRolls + " times.");
        System.out.println("The average primos it took was " + averagePrimos + " and this would cost $" + averageCost + " for the new system.");
        simulator.reset(constellation, refinement);
        i = 0;
        size = 1;
        simulator.RollWeaponSystem();
        averagePrimos = simulator.calculatePrimos();
        averageCost = simulator.calculateRealDollars();
        do {
            simulator.RollWeaponSystem();
            i = run(simulator, i);
        } while (i < timesToRun);
        System.out.println("WEAPON SYSTEM");
        System.out.println("You rolled an average of " + averageRolls + " times.");
        System.out.println("The average primos it took was " + averagePrimos + " and this would cost $" + averageCost + " for the weapon system.");
    }

    private static int run(RollSim simulator, int i) {
        size++;
        averagePrimos = (averagePrimos * (size-1) + simulator.calculatePrimos()) / size;
        averageCost = (averageCost * (size-1) + simulator.calculateRealDollars()) / size;
        averageRolls = (averageRolls * (size-1) + simulator.getRolls()) / size;
        simulator.reset(constellation, refinement);
        i++;
        return i;
    }
}
