public class SilentPushStrategy implements AlertStrategy {
    @Override
    public void executeAlert() {
        System.out.println("Sending silent push notification to homeowner's phone.");
    }
}