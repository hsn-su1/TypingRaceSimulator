public class StartRace {
    public static void startRace() {
        TypingRace race = new TypingRace(40);
        race.addTypist(new Typist('A', "TURBOFINGERS", 0.85), 1);
        race.addTypist(new Typist('B', "QWERTY_QUEEN",  0.60), 2);
        race.addTypist(new Typist('C', "HUNT_N_PECK",   0.30), 3);
        race.startRace();
    }

    public static void main(String[] args) {
        startRace();
    }
}
