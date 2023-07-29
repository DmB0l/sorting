package main;

import sortings.Sorting;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> l = new ArrayList<>();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("in.txt"));
            for (int i = 0; i < 100; i++) {
                int val = (int) (Math.random() * 100);
                bw.write(String.valueOf(val) + ' ');
                l.add(val);
            }
            bw.flush();
            System.out.println("random values write in file in.txt");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sorting.quickSort(l, (integer, t1) -> integer - t1);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"));
            for (int val : l) {
                bw.write(String.valueOf(val) + ' ');
            }
            bw.flush();
            System.out.println("soring values write in file out.txt");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}