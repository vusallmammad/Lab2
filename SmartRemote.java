public class SmartRemote {
    private final Command[] arr;
    private Command last;

    public SmartRemote(int size) {
        arr = new Command[size];
        Command no = new NoCommand();
        for (int i = 0; i < size; i++) {
            arr[i] = no;
        }
        last = no;
    }

    public void setCommand(int slot, Command c) {
        arr[slot] = c;
    }

    public void pressButton(int slot) {
        arr[slot].execute();
        last = arr[slot];
    }

    public void pressUndo() {
        last.undo();
    }
}