package sortings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorting {

    public static <T> void bubbleSort(List<T> list, Comparator<? super T> c) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (c.compare(list.get(i), list.get(j)) > 0) {
                    swap(list, i, j);
                }
            }
        }
    }

    public static <T> void mergeSort(List<T> list, Comparator<? super T> c) {
        class ThreadMergeSort extends Thread {
            final List<T> list;
            final int l, r;
            final Comparator<? super T> c;

            public ThreadMergeSort(List<T> list, int l, int r, Comparator<? super T> c) {
                this.list = list;
                this.l = l;
                this.r = r;
                this.c = c;
            }

            @Override
            public void run() {
                mergeSortFoo(list, l, r, c);
            }

            private void mergeSortFoo(List<T> list, int l, int r, Comparator<? super T> c) {
                if (l < r) {
                    int m = (l + r) / 2;

                    ThreadMergeSort thread1 = new ThreadMergeSort(list, l, m, c);
                    ThreadMergeSort thread2 = new ThreadMergeSort(list, m + 1, r, c);
                    thread1.start();
                    thread2.start();
                    try {
                        thread1.join();
                        thread2.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    int n2 = r - m;
                    int n1 = m - l + 1;

                    List<T> listL = new ArrayList<>(n1);
                    List<T> listR = new ArrayList<>(n2);

                    for (int i = 0; i < n1; i++)
                        listL.add(i, list.get(l + i));
                    for (int j = 0; j < n2; j++)
                        listR.add(j, list.get(m + 1 + j));

                    int i = 0, j = 0, k = l;

                    while (i < n1 && j < n2) {
                        if (c.compare(listL.get(i), listR.get(j)) <= 0)
                            list.set(k++, listL.get(i++));
                        else
                            list.set(k++, listR.get(j++));
                    }

                    while (i < n1) list.set(k++, listL.get(i++));
                    while (j < n2) list.set(k++, listR.get(j++));
                }
            }
        }

        ThreadMergeSort thread = new ThreadMergeSort(list, 0, list.size() - 1, c);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void quickSort(List<T> list, Comparator<? super T> c) {
        quickSortFoo(list, 0, list.size() - 1, c);
    }

    private static <T> void quickSortFoo(List<T> list, int left, int right, Comparator<? super T> c) {
        int index = partition(list, left, right, c);
        if (left < index - 1)
            quickSortFoo(list, left, index - 1, c);
        if (index + 1 < right)
            quickSortFoo(list, index + 1, right, c);
    }

    private static <T> int partition(List<T> list, int left, int right, Comparator<? super T> c) {
        T pivot = list.get(right);
        int pIndex = left;

        for (int i = left; i < right; i++) {
            if (c.compare(list.get(i), pivot) <= 0) {
                swap(list, i, pIndex);
                pIndex++;
            }
        }

        swap (list, pIndex, right);
        return pIndex;
    }

    private static <T> void swap(List<T> list, int ind1, int ind2) {
        T cur = list.get(ind2);
        list.set(ind2, list.get(ind1));
        list.set(ind1, cur);
    }
}
