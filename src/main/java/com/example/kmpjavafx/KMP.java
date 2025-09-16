package com.example.kmpjavafx;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class KMP {

    private final TextArea logArea;

    public KMP(TextArea logArea) {
        this.logArea = logArea;
    }

    public List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int[] lps = computeLPS(pattern);

        int i = 0, j = 0;

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                logArea.appendText("Сравнение: " + text.charAt(i) + " == " + pattern.charAt(j) + "\n");
                i++;
                j++;
            }

            if (j == pattern.length()) {
                result.add(i - j);
                logArea.appendText("Найдено в позиции: " + (i - j) + "\n");
                j = lps[j - 1];
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                logArea.appendText("Сравнение: " + text.charAt(i) + " != " + pattern.charAt(j) + "\n");
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return result;
    }

    private int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        logArea.appendText("Построение LPS массива:\n");

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                logArea.appendText("lps[" + i + "] = " + len + "\n");
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    logArea.appendText("lps[" + i + "] = 0\n");
                    i++;
                }
            }
        }

        return lps;
    }
}
