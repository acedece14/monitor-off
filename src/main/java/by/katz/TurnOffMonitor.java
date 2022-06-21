package by.katz;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;

public class TurnOffMonitor {

    private final static User32 user32 = User32.INSTANCE;

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        int SC_MONITORPOWER = 0xF170;
        int SC_MONITOR_OFF = 2;
        int SC_MONITOR_ON = -1;

        WinDef.LRESULT SendMessageA(WinDef.HWND paramHWND,
                                    int paramInt,
                                    WinDef.WPARAM paramWPARAM,
                                    WinDef.LPARAM paramLPARAM);

        WinDef.LRESULT SendMessageA(WinDef.HWND paramHWND,
                                    int paramInt,
                                    int paramInt2,
                                    WinDef.LPARAM paramLPARAM);
    }

    private static final long SLEEP_TIME = 2 * 1000; // 4 seconds

    public static void main(String[] args) {

        new Thread(() -> {
            waitSomeTime();
            waitSomeTime();
            System.exit(0);
        }).start();
        waitSomeTime();
        turnOffMonitor();

/*        user32.SendMessageA(WinUser.HWND_BROADCAST, WinUser.WM_SYSCOMMAND,
                User32.SC_MONITORPOWER, new WinDef.LPARAM(User32.SC_MONITOR_ON));*/
    }

    private static void turnOffMonitor() {
        user32.SendMessageA(WinUser.HWND_BROADCAST, WinUser.WM_SYSCOMMAND, User32.SC_MONITORPOWER, new WinDef.LPARAM(User32.SC_MONITOR_OFF));
    }

    private static void waitSomeTime() {
        try { Thread.sleep(SLEEP_TIME); } catch (InterruptedException ignored) {}
    }
}
