import java.util.Random;

public class LevelGenerator {

    Random random = new Random();

    int currentSection;

    public LevelGenerator() {
        currentSection = random.nextInt(2);
    }

    public int generateSection() {

        currentSection = random.nextInt(2);

        return currentSection;
    }

    public int getCurrentSection() {
        return currentSection;
    }
}