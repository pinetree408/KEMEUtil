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

import java.awt.*;
import java.awt.event.KeyEvent;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    final int PROCESS_VM_READ = 0x0010;
    final int PROCESS_QUERY_INFORMATION = 0x0400;

    final static User32 user32 = User32.INSTANCE;
    final static Kernel32 kernel32 = Kernel32.INSTANCE;

    static String processName;
    static String nowLanguage;

    static String enH = "rRseEfaqQtTdwWczxvg";
    static String regH;
    static String regB = "hk|ho|hl|nj|np|nl|ml|k|o|i|O|j|p|u|P|h|y|n|b|m|l";
    static String regF = "rt|sw|sg|fr|fa|fq|ft|fx|fv|fg|qt|r|R|s|e|f|a|q|t|T|d|w|c|z|x|v|g|";

    static Map<String, Integer> enB;
    static Map<String, Integer> enF;
    static String regex;

    static Map<Integer, Double> pme;
    static Map<Integer, Double> pmk;

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

        pme = new HashMap<Integer, Double>();

        pme.put(20, 60.28);
        pme.put(22, 39.72);

        pme.put(30, 55.64);
        pme.put(32, 12.26);
        pme.put(33, 32.1);

        pme.put(40, 60.74);
        pme.put(42, 16.11);
        pme.put(43, 11.45);
        pme.put(44, 11.69);

        pme.put(50, 62.56);
        pme.put(52, 17.75);
        pme.put(53, 8.24);
        pme.put(54, 3.44);
        pme.put(55, 8.02);

        pme.put(60, 67.77);
        pme.put(62, 14.4);
        pme.put(63, 7.18);
        pme.put(64, 3.29);
        pme.put(65, 4.43);
        pme.put(66, 2.93);

        pme.put(70, 66.63);
        pme.put(72, 15.42);
        pme.put(73, 7.38);
        pme.put(74, 3.43);
        pme.put(75, 3.27);
        pme.put(76, 66.63);
        pme.put(77, 66.63);

        pme.put(80, 66.63);
        pme.put(82, 16.68);
        pme.put(83, 7.98);
        pme.put(84, 2.45);
        pme.put(85, 2.68);
        pme.put(86, 1.04);
        pme.put(87, 1.25);
        pme.put(88, 1.57);

        pme.put(90, 64.72);
        pme.put(92, 18.1);
        pme.put(93, 7.98);
        pme.put(94, 2.35);
        pme.put(95, 2.54);
        pme.put(96, 0.96);
        pme.put(97, 1.12);
        pme.put(98, 1.4);
        pme.put(99, 0.82);

        pme.put(100, 63.78);
        pme.put(102, 18.17);
        pme.put(103, 7.62);
        pme.put(104, 2.34);
        pme.put(105, 3.6);
        pme.put(106, 0.94);
        pme.put(107, 0.53);
        pme.put(108, 2.03);
        pme.put(109, 0.24);
        pme.put(110, 0.77);

        pmk = new HashMap<Integer, Double>();

        pmk.put(20, 0.0);
        pmk.put(22, 100.0);

        pmk.put(30, 0.0);
        pmk.put(32, 0.45);
        pmk.put(33, 99.55);

        pmk.put(40, 0.0);
        pmk.put(42, 0.0);
        pmk.put(43, 43.69);
        pmk.put(44, 56.31);

        pmk.put(50, 0.0);
        pmk.put(52, 0.0);
        pmk.put(53, 0.0);
        pmk.put(54, 2.91);
        pmk.put(55, 97.09);

        pmk.put(60, 0.0);
        pmk.put(62, 0.0);
        pmk.put(63, 0.0);
        pmk.put(64, 0.0);
        pmk.put(65, 15.98);
        pmk.put(66, 84.02);

        pmk.put(70, 0.0);
        pmk.put(72, 0.0);
        pmk.put(73, 0.0);
        pmk.put(74, 0.0);
        pmk.put(75, 0.0);
        pmk.put(76, 24.84);
        pmk.put(77, 75.16);

        pmk.put(80, 0.0);
        pmk.put(82, 0.0);
        pmk.put(83, 0.0);
        pmk.put(84, 0.0);
        pmk.put(85, 0.0);
        pmk.put(86, 0.0);
        pmk.put(87, 7.85);
        pmk.put(88, 92.15);

        pmk.put(90, 0.0);
        pmk.put(92, 0.0);
        pmk.put(93, 0.0);
        pmk.put(94, 0.0);
        pmk.put(95, 0.0);
        pmk.put(96, 0.0);
        pmk.put(97, 0.0);
        pmk.put(98, 14.83);
        pmk.put(99, 85.17);

        pmk.put(100, 0.0);
        pmk.put(102, 0.0);
        pmk.put(103, 0.0);
        pmk.put(104, 0.0);
        pmk.put(105, 0.0);
        pmk.put(106, 0.0);
        pmk.put(107, 0.0);
        pmk.put(108, 0.0);
        pmk.put(109, 17.04);
        pmk.put(110, 82.96);

        regH = "[" + enH + "]";
        enB = new HashMap<String, Integer>();
        enB.put("k", 0);
        enB.put("o", 1);
        enB.put("i", 2);
        enB.put("O", 3);
        enB.put("j", 4);
        enB.put("p", 5);
        enB.put("u", 6);
        enB.put("P", 7);
        enB.put("h", 8);
        enB.put("hk", 9);
        enB.put("ho", 10);
        enB.put("hl", 11);
        enB.put("y", 12);
        enB.put("n", 13);
        enB.put("nj", 14);
        enB.put("np", 15);
        enB.put("nl", 16);
        enB.put("b", 17);
        enB.put("m", 18);
        enB.put("ml", 19);
        enB.put("l", 20);

        enF = new HashMap<String, Integer>();
        enF.put("", 0);
        enF.put("r", 1);
        enF.put("R", 2);
        enF.put("rt", 3);
        enF.put("s", 4);
        enF.put("sw", 5);
        enF.put("sg", 6);
        enF.put("e", 7);
        enF.put("f", 8);
        enF.put("fr", 9);
        enF.put("fa", 10);
        enF.put("fq", 11);
        enF.put("ft", 12);
        enF.put("fx", 13);
        enF.put("fv", 14);
        enF.put("fg", 15);
        enF.put("a", 16);
        enF.put("q", 17);
        enF.put("qt", 18);
        enF.put("t", 19);
        enF.put("T", 20);
        enF.put("d", 21);
        enF.put("w", 22);
        enF.put("c", 23);
        enF.put("z", 24);
        enF.put("x", 25);
        enF.put("v", 26);
        enF.put("g", 27);

        regex = "("+regH+")("+regB+")(("+regF+")(?=("+regH+")("+regB+"))|("+regF+"))";


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

    public static String joinArrayList(ArrayList<Integer> arrayString) {

        String listString = "";
        String regex = "[A-Za-z]";

        for (int i = 0; i < arrayString.size(); i++) {
            String temp = NativeKeyEvent.getKeyText(arrayString.get(i));

            if (temp.matches(regex)){
                if (!((i > 0) && NativeKeyEvent.getKeyText(arrayString.get(i-1)).contains("Shift"))) {
                    temp = temp.toLowerCase();
                }
                listString += temp;
            } else if (!temp.contains("Shift")){
                listString += ".";
            }
        }

        return listString;
    }

    public static double getPme(int a, int b) {

        int key = a * 10 + b;
        double result = pme.get(key);

        return result;
    }

    public static double getPmk(int a, int b) {

        int key = a * 10 + b;
        double result = pmk.get(key);

        return result;
    }

    public String realLanguage(ArrayList<Integer> arrayString) {

        String english = this.joinArrayList(arrayString).replace(".", "");

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(english);
        StringBuffer sb = new StringBuffer();

        int initialLength = english.length();
        int finalLength = 0;

        while (m.find()) {

            finalLength = finalLength + m.group().length();

            int charCode = enH.indexOf((m.group().charAt(0))) * 588;

            if (m.group().length() > 2) {
                if (enF.get(String.valueOf(m.group().charAt(2))) != null) {
                    charCode = charCode + enB.get(String.valueOf(m.group().charAt(1))) * 28 + enF.get(String.valueOf(m.group().charAt(2)));
                } else {
                    charCode = charCode + enB.get(String.valueOf(m.group().charAt(1))+String.valueOf(m.group().charAt(2))) * 28;
                }
            } else {
                charCode = charCode + enB.get(String.valueOf(m.group().charAt(1))) * 28;
            }

            charCode = charCode + 44032;

            m.appendReplacement(sb, Character.toString((char) charCode));
        }
        m.appendTail(sb);

        if (this.getPme(initialLength, finalLength) > this.getPmk(initialLength, finalLength)) {
            return "en";
        }

        return "ko";
    }

    public static String eTok(String english) {

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(english);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {

            int charCode = enH.indexOf((m.group().charAt(0))) * 588;

            if (m.group().length() > 2) {
                if (enF.get(String.valueOf(m.group().charAt(2))) != null) {
                    charCode = charCode + enB.get(String.valueOf(m.group().charAt(1))) * 28 + enF.get(String.valueOf(m.group().charAt(2)));
                } else {
                    charCode = charCode + enB.get(String.valueOf(m.group().charAt(1))+String.valueOf(m.group().charAt(2))) * 28;
                }
            } else {
                charCode = charCode + enB.get(String.valueOf(m.group().charAt(1))) * 28;
            }

            charCode = charCode + 44032;

            m.appendReplacement(sb, Character.toString((char) charCode));
        }
        m.appendTail(sb);

        return sb.toString();
    }

    public void robotInput(Robot robot, ArrayList<Integer> arrayString, String nowLang) throws Exception {

        int restoreSize = arrayString.size();
        String joinedString = this.joinArrayList(arrayString);
        int deleteSize = joinedString.length();

        if (Platform.isMac()) {

            if (nowLang.equals("ko")) {
                deleteSize = this.eTok(joinedString).length();
            }

        } else {
            if (nowLang.equals("en")) {
                deleteSize = this.eTok(joinedString).length();
            }
        }

        for (int i = 0; i < deleteSize; i++) {
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        }

        for (int i = 0; i < restoreSize; i++) {
            robot.keyPress(this.getJavaKeyCode(arrayString.get(i)));
            robot.keyRelease(this.getJavaKeyCode(arrayString.get(i)));
        }
    }

    public int getJavaKeyCode(int keyCode) {

        switch (keyCode) {
            case NativeKeyEvent.VC_ESCAPE:
                keyCode = KeyEvent.VK_ESCAPE;
                break;

            // Begin Function Keys
            case NativeKeyEvent.VC_F1:
                keyCode = KeyEvent.VK_F1;
                break;

            case NativeKeyEvent.VC_F2:
                keyCode = KeyEvent.VK_F2;
                break;

            case NativeKeyEvent.VC_F3:
                keyCode = KeyEvent.VK_F3;
                break;

            case NativeKeyEvent.VC_F4:
                keyCode = KeyEvent.VK_F4;
                break;

            case NativeKeyEvent.VC_F5:
                keyCode = KeyEvent.VK_F5;
                break;

            case NativeKeyEvent.VC_F6:
                keyCode = KeyEvent.VK_F6;
                break;

            case NativeKeyEvent.VC_F7:
                keyCode = KeyEvent.VK_F7;
                break;

            case NativeKeyEvent.VC_F8:
                keyCode = KeyEvent.VK_F8;
                break;

            case NativeKeyEvent.VC_F9:
                keyCode = KeyEvent.VK_F9;
                break;

            case NativeKeyEvent.VC_F10:
                keyCode = KeyEvent.VK_F10;
                break;

            case NativeKeyEvent.VC_F11:
                keyCode = KeyEvent.VK_F11;
                break;

            case NativeKeyEvent.VC_F12:
                keyCode = KeyEvent.VK_F12;
                break;

            case NativeKeyEvent.VC_F13:
                keyCode = KeyEvent.VK_F13;
                break;

            case NativeKeyEvent.VC_F14:
                keyCode = KeyEvent.VK_F14;
                break;

            case NativeKeyEvent.VC_F15:
                keyCode = KeyEvent.VK_F15;
                break;

            case NativeKeyEvent.VC_F16:
                keyCode = KeyEvent.VK_F16;
                break;

            case NativeKeyEvent.VC_F17:
                keyCode = KeyEvent.VK_F17;
                break;

            case NativeKeyEvent.VC_F18:
                keyCode = KeyEvent.VK_F18;
                break;

            case NativeKeyEvent.VC_F19:
                keyCode = KeyEvent.VK_F19;
                break;
            case NativeKeyEvent.VC_F20:
                keyCode = KeyEvent.VK_F20;
                break;

            case NativeKeyEvent.VC_F21:
                keyCode = KeyEvent.VK_F21;
                break;

            case NativeKeyEvent.VC_F22:
                keyCode = KeyEvent.VK_F22;
                break;

            case NativeKeyEvent.VC_F23:
                keyCode = KeyEvent.VK_F23;
                break;

            case NativeKeyEvent.VC_F24:
                keyCode = KeyEvent.VK_F24;
                break;
            // End Function Keys


            // Begin Alphanumeric Zone
            case NativeKeyEvent.VC_BACKQUOTE:
                keyCode = KeyEvent.VK_BACK_QUOTE;
                break;

            case NativeKeyEvent.VC_1:
                keyCode = KeyEvent.VK_1;
                break;

            case NativeKeyEvent.VC_2:
                keyCode = KeyEvent.VK_2;
                break;

            case NativeKeyEvent.VC_3:
                keyCode = KeyEvent.VK_3;
                break;

            case NativeKeyEvent.VC_4:
                keyCode = KeyEvent.VK_4;
                break;

            case NativeKeyEvent.VC_5:
                keyCode = KeyEvent.VK_5;
                break;

            case NativeKeyEvent.VC_6:
                keyCode = KeyEvent.VK_6;
                break;

            case NativeKeyEvent.VC_7:
                keyCode = KeyEvent.VK_7;
                break;

            case NativeKeyEvent.VC_8:
                keyCode = KeyEvent.VK_8;
                break;

            case NativeKeyEvent.VC_9:
                keyCode = KeyEvent.VK_9;
                break;

            case NativeKeyEvent.VC_0:
                keyCode = KeyEvent.VK_0;
                break;


            case NativeKeyEvent.VC_MINUS:
                keyCode = KeyEvent.VK_MINUS;
                break;

            case NativeKeyEvent.VC_EQUALS:
                keyCode = KeyEvent.VK_EQUALS;
                break;

            case NativeKeyEvent.VC_BACKSPACE:
                keyCode = KeyEvent.VK_BACK_SPACE;
                break;


            case NativeKeyEvent.VC_TAB:
                keyCode = KeyEvent.VK_TAB;
                break;

            case NativeKeyEvent.VC_CAPS_LOCK:
                keyCode = KeyEvent.VK_CAPS_LOCK;
                break;


            case NativeKeyEvent.VC_A:
                keyCode = KeyEvent.VK_A;
                break;

            case NativeKeyEvent.VC_B:
                keyCode = KeyEvent.VK_B;
                break;

            case NativeKeyEvent.VC_C:
                keyCode = KeyEvent.VK_C;
                break;

            case NativeKeyEvent.VC_D:
                keyCode = KeyEvent.VK_D;
                break;

            case NativeKeyEvent.VC_E:
                keyCode = KeyEvent.VK_E;
                break;

            case NativeKeyEvent.VC_F:
                keyCode = KeyEvent.VK_F;
                break;

            case NativeKeyEvent.VC_G:
                keyCode = KeyEvent.VK_G;
                break;

            case NativeKeyEvent.VC_H:
                keyCode = KeyEvent.VK_H;
                break;

            case NativeKeyEvent.VC_I:
                keyCode = KeyEvent.VK_I;
                break;

            case NativeKeyEvent.VC_J:
                keyCode = KeyEvent.VK_J;
                break;

            case NativeKeyEvent.VC_K:
                keyCode = KeyEvent.VK_K;
                break;

            case NativeKeyEvent.VC_L:
                keyCode = KeyEvent.VK_L;
                break;

            case NativeKeyEvent.VC_M:
                keyCode = KeyEvent.VK_M;
                break;

            case NativeKeyEvent.VC_N:
                keyCode = KeyEvent.VK_N;
                break;

            case NativeKeyEvent.VC_O:
                keyCode = KeyEvent.VK_O;
                break;

            case NativeKeyEvent.VC_P:
                keyCode = KeyEvent.VK_P;
                break;

            case NativeKeyEvent.VC_Q:
                keyCode = KeyEvent.VK_Q;
                break;

            case NativeKeyEvent.VC_R:
                keyCode = KeyEvent.VK_R;
                break;

            case NativeKeyEvent.VC_S:
                keyCode = KeyEvent.VK_S;
                break;

            case NativeKeyEvent.VC_T:
                keyCode = KeyEvent.VK_T;
                break;

            case NativeKeyEvent.VC_U:
                keyCode = KeyEvent.VK_U;
                break;

            case NativeKeyEvent.VC_V:
                keyCode = KeyEvent.VK_V;
                break;

            case NativeKeyEvent.VC_W:
                keyCode = KeyEvent.VK_W;
                break;

            case NativeKeyEvent.VC_X:
                keyCode = KeyEvent.VK_X;
                break;

            case NativeKeyEvent.VC_Y:
                keyCode = KeyEvent.VK_Y;
                break;

            case NativeKeyEvent.VC_Z:
                keyCode = KeyEvent.VK_Z;
                break;


            case NativeKeyEvent.VC_OPEN_BRACKET:
                keyCode = KeyEvent.VK_OPEN_BRACKET;
                break;

            case NativeKeyEvent.VC_CLOSE_BRACKET:
                keyCode = KeyEvent.VK_CLOSE_BRACKET;
                break;

            case NativeKeyEvent.VC_BACK_SLASH:
                keyCode = KeyEvent.VK_BACK_SLASH;
                break;


            case NativeKeyEvent.VC_SEMICOLON:
                keyCode = KeyEvent.VK_SEMICOLON;
                break;

            case NativeKeyEvent.VC_QUOTE:
                keyCode = KeyEvent.VK_QUOTE;
                break;

            case NativeKeyEvent.VC_ENTER:
                keyCode = KeyEvent.VK_ENTER;
                break;


            case NativeKeyEvent.VC_COMMA:
                keyCode = KeyEvent.VK_COMMA;
                break;

            case NativeKeyEvent.VC_PERIOD:
                keyCode = KeyEvent.VK_PERIOD;
                break;

            case NativeKeyEvent.VC_SLASH:
                keyCode = KeyEvent.VK_SLASH;
                break;

            case NativeKeyEvent.VC_SPACE:
                keyCode = KeyEvent.VK_SPACE;
                break;
            // End Alphanumeric Zone


            case NativeKeyEvent.VC_PRINTSCREEN:
                keyCode = KeyEvent.VK_PRINTSCREEN;
                break;

            case NativeKeyEvent.VC_SCROLL_LOCK:
                keyCode = KeyEvent.VK_SCROLL_LOCK;
                break;

            case NativeKeyEvent.VC_PAUSE:
                keyCode = KeyEvent.VK_PAUSE;
                break;


            // Begin Edit Key Zone
            case NativeKeyEvent.VC_INSERT:
                keyCode = KeyEvent.VK_INSERT;
                break;

            case NativeKeyEvent.VC_DELETE:
                keyCode = KeyEvent.VK_DELETE;
                break;

            case NativeKeyEvent.VC_HOME:
                keyCode = KeyEvent.VK_HOME;
                break;

            case NativeKeyEvent.VC_END:
                keyCode = KeyEvent.VK_END;
                break;

            case NativeKeyEvent.VC_PAGE_UP:
                keyCode = KeyEvent.VK_PAGE_UP;
                break;

            case NativeKeyEvent.VC_PAGE_DOWN:
                keyCode = KeyEvent.VK_PAGE_DOWN;
                break;
            // End Edit Key Zone


            // Begin Cursor Key Zone
            case NativeKeyEvent.VC_UP:
                keyCode = KeyEvent.VK_UP;
                break;
            case NativeKeyEvent.VC_LEFT:
                keyCode = KeyEvent.VK_LEFT;
                break;
            //case NativeKeyEvent.VC_CLEAR:
            //    keyCode = KeyEvent.VK_CLEAR;
            //    break;
            case NativeKeyEvent.VC_RIGHT:
                keyCode = KeyEvent.VK_RIGHT;
                break;
            case NativeKeyEvent.VC_DOWN:
                keyCode = KeyEvent.VK_DOWN;
                break;
            // End Cursor Key Zone


            // Begin Numeric Zone
            case NativeKeyEvent.VC_NUM_LOCK:
                keyCode = KeyEvent.VK_NUM_LOCK;
                break;

            //case NativeKeyEvent.VC_SEPARATOR:
            //    keyCode = KeyEvent.VK_SEPARATOR;
            //    break;
            // End Numeric Zone


            // Begin Modifier and Control Keys
            //case NativeKeyEvent.VC_SHIFT:
            //    keyCode = KeyEvent.VK_SHIFT;
            //    break;

            //case NativeKeyEvent.VC_CONTROL:
            //    keyCode = KeyEvent.VK_CONTROL;
            //    break;

            //case NativeKeyEvent.VC_ALT:
            //    keyCode = KeyEvent.VK_ALT;
            //    break;

            //case NativeKeyEvent.VC_META:
            //    keyCode = KeyEvent.VK_META;
            //    break;

            case NativeKeyEvent.VC_CONTEXT_MENU:
                keyCode = KeyEvent.VK_CONTEXT_MENU;
                break;
            // End Modifier and Control Keys


			/* Begin Media Control Keys
			case NativeKeyEvent.VC_POWER:
			case NativeKeyEvent.VC_SLEEP:
			case NativeKeyEvent.VC_WAKE:
			case NativeKeyEvent.VC_MEDIA_PLAY:
			case NativeKeyEvent.VC_MEDIA_STOP:
			case NativeKeyEvent.VC_MEDIA_PREVIOUS:
			case NativeKeyEvent.VC_MEDIA_NEXT:
			case NativeKeyEvent.VC_MEDIA_SELECT:
			case NativeKeyEvent.VC_MEDIA_EJECT:
			case NativeKeyEvent.VC_VOLUME_MUTE:
			case NativeKeyEvent.VC_VOLUME_UP:
			case NativeKeyEvent.VC_VOLUME_DOWN:
			case NativeKeyEvent.VC_APP_MAIL:
			case NativeKeyEvent.VC_APP_CALCULATOR:
			case NativeKeyEvent.VC_APP_MUSIC:
			case NativeKeyEvent.VC_APP_PICTURES:
			case NativeKeyEvent.VC_BROWSER_SEARCH:
			case NativeKeyEvent.VC_BROWSER_HOME:
			case NativeKeyEvent.VC_BROWSER_BACK:
			case NativeKeyEvent.VC_BROWSER_FORWARD:
			case NativeKeyEvent.VC_BROWSER_STOP:
			case NativeKeyEvent.VC_BROWSER_REFRESH:
			case NativeKeyEvent.VC_BROWSER_FAVORITES:
			// End Media Control Keys */


            // Begin Japanese Language Keys
            case NativeKeyEvent.VC_KATAKANA:
                keyCode = KeyEvent.VK_KATAKANA;
                break;

            case NativeKeyEvent.VC_UNDERSCORE:
                keyCode = KeyEvent.VK_UNDERSCORE;
                break;

            //case VC_FURIGANA:

            case NativeKeyEvent.VC_KANJI:
                keyCode = KeyEvent.VK_KANJI;
                break;

            case NativeKeyEvent.VC_HIRAGANA:
                keyCode = KeyEvent.VK_HIRAGANA;
                break;

            //case VC_YEN:
            // End Japanese Language Keys


            // Begin Sun keyboards
            case NativeKeyEvent.VC_SUN_HELP:
                keyCode = KeyEvent.VK_HELP;
                break;

            case NativeKeyEvent.VC_SUN_STOP:
                keyCode = KeyEvent.VK_STOP;
                break;

            //case VC_SUN_FRONT:

            //case VC_SUN_OPEN:

            case NativeKeyEvent.VC_SUN_PROPS:
                keyCode = KeyEvent.VK_PROPS;
                break;

            case NativeKeyEvent.VC_SUN_FIND:
                keyCode = KeyEvent.VK_FIND;
                break;

            case NativeKeyEvent.VC_SUN_AGAIN:
                keyCode = KeyEvent.VK_AGAIN;
                break;

            //case NativeKeyEvent.VC_SUN_INSERT:

            case NativeKeyEvent.VC_SUN_COPY:
                keyCode = KeyEvent.VK_COPY;
                break;

            case NativeKeyEvent.VC_SUN_CUT:
                keyCode = KeyEvent.VK_CUT;
                break;
            // End Sun keyboards
        }

        return keyCode;
    }

}
