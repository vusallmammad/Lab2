public class Main {
    public static void main(String[] args) {
        MotionSensor sensor = new MotionSensor();
        SmartLights lights = new SmartLights();
        SmartAlarm alarm = new SmartAlarm();

        alarm.setMode("SILENT");
        alarm.arm();

        sensor.addObserver(lights);
        sensor.addObserver(alarm);

        sensor.detectMotion();

        alarm.setMode("SIREN");
        sensor.detectMotion();

        SmartRemote remote = new SmartRemote(2);
        remote.setCommand(0, new TurnOnLightCommand(lights));
        remote.setCommand(1, new ArmAlarmCommand(alarm));

        remote.pressButton(0);
        remote.pressButton(1);
        remote.pressUndo();
    }
}