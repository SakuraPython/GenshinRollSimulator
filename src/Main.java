public class Main {
    public static void main(String[] args) {
        RollSim simulator = new RollSim();
        //simulator.setConstellation(X); //X = I want a C[X] banner feature, or I need X+1 of this character
        //simulator.setStartingRoll(Y); //Y = I made Y rolls since my last 5 star
        //simulator.setStartingFifty(false); //I lost my last 50/50 (true) or won (false)
        simulator.RollOldSystem();
        System.out.println("OLD SYSTEM");
        System.out.println(simulator.generateStats());
        simulator.reset(6,5 );
        //simulator.setConstellation(X);
        //simulator.setStartingRoll(Y);
        //simulator.setStartingFifty(false); //This is irrelevant in the new system, but if it makes you happy
        simulator.RollNewSystem();
        System.out.println("NEW SYSTEM");
        System.out.println(simulator.generateStats());
        simulator.reset(6, 5);
        //simulator.setRefinementLevel(Z); //Z = I want R[Z] of ONE of the banner features
        //simulator.setStartingFifty(false); //Weapons are 75/25, but they do respect the "50/50" rule
        simulator.RollWeaponSystem();
        System.out.println("WEAPON SYSTEM");
        System.out.println(simulator.generateWeaponStats());
        simulator.reset(6, 5);
    }
}
