public class SmartLights implements Observer {
    private boolean on;

    @Override
    public void update() {
        turnOn();
    }

    public void turnOn() {
        on = true;
        System.out.println("Smart lights turned on");
    }

    public void turnOff() {
        on = false;
        System.out.println("Smart lights turned off");
    }

    public boolean isOn() {
        return on;
    }
}