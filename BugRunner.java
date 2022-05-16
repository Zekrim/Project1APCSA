public class BugRunner {
    public static void main(String[] args) {
        Bug mutantBee = new Bug(BugType.BEE, true, 8, 12);
        System.out.println("This Mutant bee has " + mutantBee.getLegs() +" legs.");
        if(mutantBee.getFlightCapacity()) {
            System.out.println("The bee originally cannfly.");
        } else {
            System.out.println("The bee originally cannot fly.");
        }
        mutantBee.trimWings();
        System.out.println("The bee's wings were now trimmed.");
        if(mutantBee.getFlightCapacity()) {
            System.out.println("The bee can still fly.");
        } else {
            System.out.println("The bee now cannot fly.");
        }
        System.out.println("\n\n\nAnd now for a set of bug specimens...");
        Bug.displayByLength(Bug.ant(), Bug.ant(), Bug.bee(), Bug.spider());
    }
}
