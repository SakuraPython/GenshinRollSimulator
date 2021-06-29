import java.util.Random;

import static java.lang.Math.ceil;

/**
 * RollSim, true to its name, simulates rolling the gacha on Genshin Impact.
 * This simulator is for comparing the new pity system to the old pity system.
 * This simulator does not account for soft pity theory.
 * @author @SakuraPython#1272
 * Please don't @ me for shitty code
 * Feel free to @ me if you have genuine questions or contributions
 */

public class RollSim {
    private int pityCount = 0;
    private int rollCount = 0;
    private final Random rng = new Random();
    private int bannerFeatures = 0;
    private int standardChars = 0; //refers to any 5 star character that isn't the banner feature
    private int failedRolls = 0;
    private boolean lastRollNotBannerFeature = false; //50/50 system
    private int fiftiesWon = 0;
    private int fiftiesLost = 0;
    private int unPity = 0; //counts the times you hit full-odds
    private int pityHit = 0;
    private int constellation = 6; //6 is default for whales, 0 is default for f2p/goldfish
    private int weaponFeatures = 0; //specific weapon on the banner
    private int otherWeapons = 0; //any other weapon, including the other banner feature
    private int refinement = 5; //5 for whales, 1 for f2p/goldfish

    public RollSim() {
        //just set the values yourself using the set methods
    }

    /**
     * Allows you to set how many constellations you want to unlock on the banner feature
     * @param C The constellation level you want to hit on the banner feature (min 0, max 6)
     */
    public void setConstellation(int C) {
        //not range checking is just irresponsible
        if (C < 0) {
            C = 0;
        }
        else if (C > 6) {
            C = 6;
        }
        this.constellation = C;
    }

    /**
     * Those who want to import their own stats may find this useful
     * @param fifty true if your last 5-star roll was NOT the banner feature
     */
    public void setStartingFifty(boolean fifty) {
        this.lastRollNotBannerFeature = fifty;
    }

    /**
     * Those who want to import their own stats may find this useful
     * Characters hit pity at 90, weapons at 80
     * I'm too lazy to range check them both, if you import genuine data you'll be fine
     * @param pity The number of rolls you made since the last 5 star roll
     */
    public void setStartingRoll(int pity) {
        if (pity > 89) {
            pity = 89;
        }
        else if (pity < 0) {
            pity = 0;
        }
        this.pityCount = pity;
    }

    /**
     * Allows you to set the refinement level you want to achieve on ONE of the banner features
     * @param refinement The desired refinement level (min 1, max 5)
     */
    public void setRefinementLevel(int refinement) {
        if (refinement < 1) {
            refinement = 1;
        }
        else if (refinement > 5) {
            refinement = 5;
        }
        this.refinement = refinement;
    }

    /**
     * What good is a simulator for Genshin if it doesn't tell you what it would cost?
     * Applies to the current attempt.
     * @return The number of primogems you would have to spend for the desired result
     */
    public int calculatePrimos() {
        return this.rollCount * 160;
    }

    /**
     * This gives you the real money cost estimate of the end result assuming you only buy $100 packs.
     * Does not account for first-time purchase bonus.
     * If you want the cost based on other packs, the numbers below are how much each one gives:
     * $1 = 60
     * $5 = 330
     * $15 = 1090
     * $30 = 2240
     * $50 = 3880
     * $100 = 8080
     * @return The closest real-money estimate of how much the primos would cost.
     */
    public float calculateRealDollars() {
        float base = (float)calculatePrimos() / 8080;
        return base * 100;
    }

    /**
     * Gives a summary of the latest attempt for character banners
     * @return Statistics regarding the simulation
     */
    public String generateStats() {
        return "You had to roll " + rollCount + " times for the desired amount of banner features.\n" +
                "You got " + bannerFeatures + " banner features and " + standardChars + " other characters.\n" +
                "This costed " + calculatePrimos() + " primogems and its real money equivalent is $" + calculateRealDollars() + '\n' +
                "[Old System] You won " + fiftiesWon + " 50/50s and lost " + fiftiesLost + " 50/50s.\n" +
                "The total number of failed rolls you made is " + failedRolls + '\n' +
                "The number of times you hit full-odds is " + unPity + " and you hit pity " + pityHit + " times.\n" +
                "In total, you would need to do " + (int)ceil((float)rollCount / 10.0f) + " 10-pulls.\n";
        //                                           ^my dude what the hell is this^
    }

    /**
     * Gives a summary of the latest attempt for weapon banners
     * @return Statistics regarding the simulation
     */
    public String generateWeaponStats() {
        return "You had to roll " + rollCount + " times for the desired amount of a specific weapon.\n" +
                "You got " + weaponFeatures + " of the desired weapon and " + otherWeapons + " other weapons.\n" +
                "This costed " + calculatePrimos() + " primogems and its real money equivalent is $" + calculateRealDollars() + '\n' +
                "The total number of failed rolls you made is " + failedRolls + '\n' +
                "The number of times you hit full-odds is " + unPity + " and you hit pity " + pityHit + " times.\n" +
                "In total, you would need to do " + (int)ceil((float)rollCount / 10.0f) + " 10-pulls.\n";
        //                                                  ^again, wtf^
    }

    /**
     * If you plan to use and edit this sim on your own, remember to reset the simulator between attempts.
     */
    public void reset(int constellation, int refinement) {
        this.pityCount = 0;
        this.rollCount = 0;
        this.bannerFeatures = 0;
        this.standardChars = 0;
        this.failedRolls = 0;
        this.lastRollNotBannerFeature = false;
        this.fiftiesWon = 0;
        this.fiftiesLost = 0;
        this.constellation = constellation;
        this.unPity = 0;
        this.pityHit = 0;
        this.weaponFeatures = 0;
        this.otherWeapons = 0;
        this.refinement = refinement;
    }

    public int getRolls() {
        return this.rollCount;
    }

    /**
     * The main roll simulator.
     * This sim emulates the functionality of the current system.
     */
    public void RollOldSystem() {
        do {
            pityCount++;
            rollCount++;
            if (pityCount == 90) { //if we hit pity
                RollFiveStarOld();
                pityHit++;
            } else {
                int rand = rng.nextInt(1000); //[0, 999]
                if (rand < 6) { //we lucked out
                    RollFiveStarOld();
                    unPity++;
                } else {
                    failedRolls++;
                }
            }
        } while (bannerFeatures < constellation + 1); //assuming we want to hit banner feature at least once
    }

    /**
     * Same roll simulator, but uses the described upcoming system.
     */
    public void RollNewSystem() { //it's the same thing but with the new 5 star rolling system
        do {
            pityCount++;
            rollCount++;
            if (pityCount == 90) {
                RollFiveStarNew();
                pityHit++;
            } else {
                int rand = rng.nextInt(1000);
                if (rand < 6) {
                    RollFiveStarNew();
                    unPity++;
                } else {
                    failedRolls++;
                }
            }
        } while (bannerFeatures < constellation + 1);
    }

    /**
     * Weapons roll a bit differently from characters, but I figured I may as well put in it for completeness sake.
     */
    public void RollWeaponSystem() {
        do {
            pityCount++;
            rollCount++;
            if (pityCount == 80) { //weapons hit pity at 80 rolls instead of 90
                RollFiveStarWeapon();
                pityHit++;
            } else {
                int rand = rng.nextInt(1000);
                if (rand < 7) { //5 star weapons have base 0.7% drop rate instead of 0.6%
                    RollFiveStarWeapon();
                    unPity++;
                } else {
                    failedRolls++;
                }
            }
        } while (weaponFeatures < refinement); //characters can be C0, weapons are always at least R1
    }

    /**
     * The 5 star system that's currently in place. To be specific, the following happens whenever you roll a 5 star:
     * -The game checks to see whether or not the last 5 star roll you hit was the banner feature.
     * -If it wasn't the banner feature, this roll is GUARANTEED to be the banner feature.
     * -If it was the banner feature, you have a 50/50 chance of getting the banner feature(50%) or someone else(50%).
     * For an example, let's say the banner feature is Hu Tao.
     * ex: If your last 5 star roll was Mona, then this roll will be Hu Tao
     * ex2: If your last 5 star roll was Hu Tao, then there is a 50% chance this roll will be Hu Tao, and a 50% chance you will get anyone in the pool EXCEPT Hu Tao.
     */
    public void RollFiveStarOld() {
        if (lastRollNotBannerFeature) { //did we hit the banner guarantee?
            bannerFeatures++;
            lastRollNotBannerFeature = false;
        }
        else {
            boolean coin = rng.nextBoolean();
            if (coin) { //we won the 50/50
                bannerFeatures++;
                fiftiesWon++;
                lastRollNotBannerFeature = false;
            }
            else { //we lost the 50/50
                standardChars++;
                fiftiesLost++;
                lastRollNotBannerFeature = true;
            }
        }
        pityCount = 0; //all 5 stars reset pity
    }

    /**
     * The new 5 star system as described by a post made in the discord. How it works:
     * Whenever you hit a 5 star, there is a 75% chance that it will be the banner feature.
     * When you land in the 25%, this new pool still contains the banner feature.
     * This means that even if you lose the 75/25, you could still get the banner feature.
     * This new system does NOT have any guarantees.
     * Again, let's say the banner feature is Hu Tao:
     * If your last roll was Hu Tao, you have a 75/25 situation as described above.
     * If your last roll was Mona, you still have the 75/25 situation.
     * While there are no guarantees with this new system, you are still far more likely to get the banner feature than before (in theory).
     */
    public void RollFiveStarNew() {
        int firstRand = rng.nextInt(100); //[0, 99]
        if (firstRand < 75) { //The new system doesn't account for your last roll not being the banner feature
            bannerFeatures++;
        }
        else {
            int secondRand = rng.nextInt(6); //Jean, Diluc, Qiqi, Mona, Keqing, and Banner Feature
            if (secondRand < 1) { //we can hit our banner feature in this pool
                bannerFeatures++;
            }
            else {
                standardChars++;
            }
        }
        pityCount = 0;
    }

    /**
     * This is the 5 star weapon rolling system.
     * There aren't any current leaks as far as changing this system, but we do have leaks regarding 4 star stabilization.
     */
    public void RollFiveStarWeapon() {
        int firstRand = rng.nextInt(100);
        if (firstRand < 75 || lastRollNotBannerFeature) { //75% chance for a banner feature, or guaranteed if the last one wasn't a banner feature
            boolean secondRand = rng.nextBoolean();
            if (secondRand) { //let's say this weapon is Staff of Homa for Hu Tao
                weaponFeatures++;
            }
            else { //this would be the other banner feature, whether we like it or not depends on a number of factors
                //let's just say this is Skyward Atlas
                otherWeapons++;
            }
            lastRollNotBannerFeature = false; //both of these are banner features
        }
        else {
            otherWeapons++;
            lastRollNotBannerFeature = true;
        }
        pityCount = 0;
    }

}
