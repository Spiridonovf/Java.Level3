package Lesson5;
 
import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    public volatile boolean start = false;
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}