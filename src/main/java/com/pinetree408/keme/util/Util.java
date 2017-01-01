package com.pinetree408.keme.util;

/**
 * Created by user on 2016-12-28.
 */

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class Util {

    final int PROCESS_VM_READ = 0x0010;
    final int PROCESS_QUERY_INFORMATION = 0x0400;

    final static User32 user32 = User32.INSTANCE;
    final static Kernel32 kernel32 = Kernel32.INSTANCE;

    static String processName;
    static String nowLanguage;

    public interface Psapi extends StdCallLibrary {
        Psapi INSTANCE = (Psapi) Native.loadLibrary("Psapi", Psapi.class);

        WinDef.DWORD GetModuleBaseNameW(Pointer hProcess, Pointer hModule, byte[] lpBaseName, int nSize);
    }

    final Psapi psapi = Psapi.INSTANCE;

    public interface Ime extends User32 {
        Ime INSTANCE = (Ime)Native.loadLibrary("imm32.dll", Ime.class);
        HWND ImmGetDefaultIMEWnd(HWND hWnd);
    }

    final static Ime ime = Ime.INSTANCE;

    public interface MyUser32 extends User32 {
        MyUser32 INSTANCE = (MyUser32)Native.loadLibrary("user32", MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);
        int SendMessage(HWND hWnd, int Msg, int wParam, int lParam);
    }

    final static MyUser32 myUser32 = MyUser32.INSTANCE;

    public Util() {

        processName = "";
        nowLanguage = "";

    }

    public String nowTopProcess() {

        if (Platform.isWindows()) {

            WinDef.HWND windowHandle = user32.GetForegroundWindow();
            IntByReference pid = new IntByReference();
            user32.GetWindowThreadProcessId(windowHandle, pid);
            WinNT.HANDLE processHandle = kernel32.OpenProcess(PROCESS_VM_READ | PROCESS_QUERY_INFORMATION, true, pid.getValue());

            byte[] filename = new byte[512];

            try {
                psapi.GetModuleBaseNameW(processHandle.getPointer(), Pointer.NULL, filename, filename.length);
            } catch(NullPointerException e) {
                e.getStackTrace();
            }

            String temp = "";

            for (int i = 0; i < 32; i++) {
                if (filename[i] != 0x00) {
                    temp += (char)filename[i];
                }
            }

            processName = temp;

        }

        return processName;
    }

    public String nowLanguage(){

        if (Platform.isWindows()) {

            WinDef.HWND windowHandle = user32.GetForegroundWindow();
            WinDef.HWND hwndIme = ime.ImmGetDefaultIMEWnd(windowHandle);
            int languageIme = myUser32.SendMessage(hwndIme, 0x0283, 0x05, 0);

            if (languageIme == 0){
                nowLanguage = "en";
            }else{
                nowLanguage = "ko";
            }

        }

        return nowLanguage;
    }

}
