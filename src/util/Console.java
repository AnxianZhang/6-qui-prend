package util;

import java.io.IOException;

public class Console {
    private static final ProcessBuilder CLEANER_PROCESS;
    private static final ProcessBuilder PAUSE_PROCESS;

    private static final String MSG_PAUSE = "Appuyez sur une touche pour continuer..." + System.lineSeparator();

    static {
        if (System.console() != null) {
            String[] cdeClean;
            String[] cdePause;
            if (System.getProperty("os.name").contains("Windows")) {
                cdeClean = new String[] { "cmd", "/c", "cls" };
                cdePause = new String[] { "cmd", "/c", "pause" };
            }
            else {
                cdeClean = new String[] { "clear" };
                cdePause = new String[] { "read", "-n1", "-rsp", MSG_PAUSE };
            }
            CLEANER_PROCESS = new ProcessBuilder(cdeClean).inheritIO();
            PAUSE_PROCESS = new ProcessBuilder(cdePause).inheritIO();
        } else
            CLEANER_PROCESS = PAUSE_PROCESS = null;
    }

    private static final String MSG_C = "<clearScreen>";

    public static void clearScreen() {
        if (CLEANER_PROCESS != null)
            try {
                CLEANER_PROCESS.start().waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                System.out.println(MSG_C);
            }
        else
            System.out.println(MSG_C);
    }

    private static final String MSG_P = "<pause>";

    public static void pause() {
        if (PAUSE_PROCESS != null)
            try {
                PAUSE_PROCESS.start().waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                System.out.println(MSG_P);
            }
        else
            System.out.println(MSG_P);
    }

    private Console() {
    }
}
