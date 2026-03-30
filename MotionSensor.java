import java.util.ArrayList;
import java.util.List;

public class MotionSensor implements Subject {
    private final List<Observer> list = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        list.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : list) {
            o.update();
        }
    }

    public void detectMotion() {
        System.out.println("Motion detected");
        notifyObservers();
    }
}