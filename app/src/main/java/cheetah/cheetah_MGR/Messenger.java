package cheetah.cheetah_MGR;

public class Messenger {
    public static void showError(String s, VirtualMachineError err) {
        System.out.println(s + " " + err.toString());
    }

    public static void showException(String s, Exception ex) {
        System.out.println(s + " " + ex.toString());
    }
}
