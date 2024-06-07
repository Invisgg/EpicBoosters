package gg.minecrush.boosters.util;

public class TimeFormatter {

    public static String formatTime(int seconds) {
        int days = seconds / 86400;
        seconds %= 86400;
        int hours = seconds / 3600;
        seconds %= 3600;
        int minutes = seconds / 60;
        int secs = seconds % 60;

        StringBuilder timeString = new StringBuilder();

        if (days > 0) {
            timeString.append(days).append("d").append(" ");
        }
        if (hours > 0) {
            timeString.append(hours).append("h").append(" ");
        }
        if (minutes > 0) {
            timeString.append(minutes).append("m").append(" ");
        }
        if (secs > 0 || timeString.length() == 0) {
            timeString.append(secs).append("s ");
        }

        return timeString.toString().trim();
    }

    public static String formatTimeLonger(int seconds) {
        int days = seconds / 86400;
        seconds %= 86400;
        int hours = seconds / 3600;
        seconds %= 3600;
        int minutes = seconds / 60;
        int secs = seconds % 60;

        StringBuilder timeString = new StringBuilder();

        if (days > 0) {
            timeString.append(days).append(" days").append(" ");
        }
        if (hours > 0) {
            timeString.append(hours).append(" hours").append(" ");
        }
        if (minutes > 0) {
            timeString.append(minutes).append(" minutes").append(" ");
        }
        if (secs > 0 || timeString.length() == 0) {
            timeString.append(secs).append(" seconds ");
        }

        return timeString.toString().trim();
    }
}

