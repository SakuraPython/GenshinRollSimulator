/**
 * I heard you like mass averages, so I gave you some.
 */
public class MassAverage {
    //Probably don't edit these
    //They get initialized below anyway
    static int size = 1;
    static double averagePrimos = 0;
    static double averageCost = 0;
    static double averageRolls = 0;
    static int bestRolls = 9999999;
    static int worstRolls = 0;

    //These you can edit
    //Hopefully I don't need to explain them
    static final int timesToRun = 1000000; //Shouldn't be less than 30
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
        i = getStats(simulator);
        //showWayTooDetailedStats();
        System.out.println();
        simulator.RollNewSystem();
        averagePrimos = simulator.calculatePrimos();
        averageCost = simulator.calculateRealDollars();
        do {
            simulator.RollNewSystem();
            i = run(simulator, i);
        } while (i < timesToRun);
        System.out.println("NEW SYSTEM");
        i = getStats(simulator);
        //showWayTooDetailedStats();
        System.out.println();
        simulator.RollWeaponSystem();
        averagePrimos = simulator.calculatePrimos();
        averageCost = simulator.calculateRealDollars();
        do {
            simulator.RollWeaponSystem();
            i = run(simulator, i);
        } while (i < timesToRun);
        System.out.println("OLD WEAPON SYSTEM");
        i = getStats(simulator);
        //showWayTooDetailedStats();
        System.out.println();
        simulator.RollWeaponSystemNew();
        averagePrimos = simulator.calculatePrimos();
        averageCost = simulator.calculateRealDollars();
        do {
            simulator.RollWeaponSystemNew();
            i = run(simulator, i);
        } while (i < timesToRun);
        System.out.println("NEW WEAPON SYSTEM");
        getStats(simulator);
        //showWayTooDetailedStats();
        System.out.println();
    }

    private static int getStats(RollSim simulator) {
        int i;
        System.out.println("You rolled an average of " + averageRolls + " times.");
        System.out.println("The average primos it took was " + averagePrimos + " and this would cost $" + averageCost);
        System.out.println("Best rolls: " + bestRolls + " (= " + bestRolls * 160 + " primos or $" + (bestRolls * 160) / 80.8 + ')');
        System.out.println("Worst rolls: " + worstRolls + " (= " + worstRolls * 160 + " primos or $" + (worstRolls * 160) / 80.8 + ')');
        simulator.reset(constellation, refinement);
        i = 0;
        size = 1;
        return i;
    }

    private static int run(RollSim simulator, int i) {
        size++;
        averagePrimos = (averagePrimos * (size-1) + simulator.calculatePrimos()) / size;
        averageCost = (averageCost * (size-1) + simulator.calculateRealDollars()) / size;
        averageRolls = (averageRolls * (size-1) + simulator.getRolls()) / size;
        if (simulator.getRolls() < bestRolls) {
            bestRolls = simulator.getRolls();
        }
        if (simulator.getRolls() > worstRolls) {
            worstRolls = simulator.getRolls();
        }
        Globals.statLogger.logMoneyAt(simulator.calculateRealDollars());
        simulator.reset(constellation, refinement);
        i++;
        return i;
    }

    private static void showWayTooDetailedStats() {
        System.out.println("Stats for nerds:");
        System.out.println("Raw data of where each 5 star roll occurred:");
        Globals.statLogger.showRollDist();
        System.out.println("As a distribution:");
        Globals.statLogger.showRollDistAsPercent();
        System.out.println("Raw cost data, range is about index * 100:");
        Globals.statLogger.showCostDist();
        System.out.println("As a distribution: ");
        Globals.statLogger.showCostDistAsPercent();
        Globals.statLogger.reset();
        System.out.println();
    }
}
