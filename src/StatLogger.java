import static java.lang.Math.floor;

/**
 * This class is used to log way too detailed stats of the simulation.
 * Use at your own peril.
 * @author @SakuraPython#1272
 */
public class StatLogger {
    public final int[] fiveStarRollDist = new int[90]; //distribution of 5 star rolls at each roll

    private final float[] realMoneyDist = new float[100]; //distribution of real money cost of the simulation
    /*
    realMoneyDist[x] ->
    0 = 0-99 dollars
    1 = 100-199 dollars
    2 = 200-299 dollars
    ...
    99 = >9900 dollars
    index = floor(cost/100)
     */

    /**
     * This method is used to log which roll got a 5 star.
     * By mass logging, a distribution can be made.
     * @param roll The roll that got the 5 star
     */
    public void logRollAt(int roll) {
        fiveStarRollDist[roll]++;
    }

    /**
     * This method is used to log how much money the end result of a simulation costed.
     * @param money The calculated real money cost of the simulation.
     */
    public void logMoneyAt(float money) {
        int indexOf = (int) floor(money/100);
        realMoneyDist[indexOf]++;
    }

    public int calculateRollDistSize() {
        int size = 0;
        for (int i = 0; i < 90; i++) {
            size += fiveStarRollDist[i];
        }
        return size;
    }

    public int calculateRealMoneyDistSize() {
        int size = 0;
        for (int i = 0; i < 100; i++) {
            size += realMoneyDist[i];
        }
        return size;
    }

    /**
     * Reset the data between runs, if it's useful to.
     */
    public void reset() {
        for (int i = 0; i < 90; i++) {
            fiveStarRollDist[i] = 0;
        }
        for (int j = 0; j < 100; j++) {
            realMoneyDist[j] = 0;
        }
    }

    /**
     * Way too detailed stats
     */
    public void showRollDist() {
        for (int i = 0; i < 90; i++) {
            System.out.println((i + 1) + ": " + fiveStarRollDist[i]);
        }
    }

    /**
     * Way too detailed stats
     */
    public void showRollDistAsPercent() {
        int size = calculateRollDistSize();
        for (int i = 0; i < 90; i++) {
            System.out.println((i + 1) + ": " + ((float)fiveStarRollDist[i]/size)*100 + '%');
        }
    }

    /**
     * Way too detailed stats
     */
    public void showCostDist() {
        for (int i = 0; i < 100; i++) {
            System.out.println("$" + i * 100 + " to $" + ((i*100)+99) + ": " + (int)realMoneyDist[i]);
        }
    }

    /**
     * Way too detailed stats
     */
    public void showCostDistAsPercent() {
        int size = calculateRealMoneyDistSize();
        for (int i = 0; i < 100; i++) {
            System.out.println("$" + i * 100 + " to $" + ((i*100)+99) + ": " + (realMoneyDist[i] /size)*100 + '%');
        }
    }
}
