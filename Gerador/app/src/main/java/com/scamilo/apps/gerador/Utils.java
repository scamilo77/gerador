package com.scamilo.apps.gerador;

import java.util.Arrays;
import java.util.List;

/**
 * Created by scamilo on 2/2/2016.
 */
public class Utils {

    public static boolean Exists(List<Integer> list, List<Ticket> tickets){

        boolean exists = false;

        for(Ticket ticket : tickets) {
            List<Integer> listToCompare = ticket.GetValues();
            int count = 0;
            if (list.size() <= listToCompare.size()) {
                for (int item : list) {
                    for (int i = 0; i < listToCompare.size(); i++) {
                        if (item == listToCompare.get(i)) {
                            count++;
                            break;
                        }
                    }
                    if (count == list.size()) {
                        exists = true;
                        break;
                    }
                }
            } else {
                for (int item : listToCompare) {
                    for (int i = 0; i < list.size(); i++) {
                        if (item == list.get(i)) {
                            count++;
                            break;
                        }
                    }
                    if (count == listToCompare.size()) {
                        exists = true;
                        break;
                    }
                }
            }
            if(exists)
                break;
        }
        return exists;
    }

    public static Integer[] Sort(List<Integer> intArray) {

                /*
                 * In bubble sort, we basically traverse the array from first
                 * to array_length - 1 position and compare the element with the next one.
                 * Element is swapped with the next element if the next element is greater.
                 *
                 * Bubble sort steps are as follows.
                 *
                 * 1. Compare array[0] & array[1]
                 * 2. If array[0] > array [1] swap it.
                 * 3. Compare array[1] & array[2]
                 * 4. If array[1] > array[2] swap it.
                 * ...
                 * 5. Compare array[n-1] & array[n]
                 * 6. if [n-1] > array[n] then swap it.
                 *
                 * After this step we will have largest element at the last index.
                 *
                 * Repeat the same steps for array[1] to array[n-1]
                 *
                 */

        int n = intArray.size();
        int temp = 0;

        //int[] local = Arrays.copyOf(intArray.toArray(), intArray.size(), Integer[].class);

        Integer[] array = intArray.toArray (new Integer [intArray.size()]);

            for (int i = array.length; i >= 1; i--) {
                for (int j = 1; j < i; j++) {
                    if (array[j - 1] > array[j]) {
                        int aux = array[j];
                        array[j] = array[j - 1];
                        array[j - 1] = aux;
                    }
                }
            }

        return array;
    }
}
