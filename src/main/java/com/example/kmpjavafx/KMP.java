package com.example.kmpjavafx;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class KMP {

    // Метод поиска: возвращает список позиций вхождений pattern в text
    public List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int[] lps = computeLPS(pattern);

        int i = 0; // индекс по тексту
        int j = 0; // индекс по шаблону

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                result.add(i - j);
                j = lps[j - 1]; // откатываемся по LPS
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return result;
    }

    // Построение массива LPS (длины наибольшего префикса, совпадающего с суффиксом)
    private int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}