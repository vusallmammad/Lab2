import java.util.HashMap;
import java.util.Map;

public class SmartAlarm implements Observer {
    private boolean armed;
    private AlertStrategy now;
    private final Map<String, AlertStrategy> map = new HashMap<>();

    public SmartAlarm() {
        map.put("SILENT", new SilentPushStrategy());
        map.put("SIREN", new LoudSirenStrategy());
    }

    @Override
    public void update() {
        if (armed && now != null) {
            now.executeAlert();
        } else if (!armed) {
            System.out.println("Alarm is not armed");
        }
    }

    public void setMode(String name) {
        now = map.get(name);
    }

    public void arm() {
        armed = true;
        System.out.println("Alarm armed");
    }

    public void disarm() {
        armed = false;
        System.out.println("Alarm disarmed");
    }

    public boolean isArmed() {
        return armed;
    }
}