package org.example;

import java.util.List;

public class QuickSort {
    public void quickSort(List<DataPoint> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);  // Sort elements before partition
            quickSort(list, pi + 1, high);  // Sort elements after partition
        }
    }

    private int partition(List<DataPoint> list, int low, int high) {
        DataPoint pivot = list.get(high);  // Pivot element
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).price.compareTo(pivot.price) <= 0) {  // Sort by name
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(List<DataPoint> list, int i, int j) {
        DataPoint temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);

    }
}
