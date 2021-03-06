package com.chaka.servers;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * NOTES:
 * Here are some of the assumptions I made before solving this problem:
 * I assume there are always going to be 3 arrays with 3 elements each => 3 x 3 array.
 * I assume each element in each array is always going to be either the numbers 0 or 1.
 *
 * The steps I took to implementing my solution:
 * I rearranged the array by stacking them on top of each other to achieve something like the below
 with each number representing the indexes of the element in that position.
 * [0, 1, 2]
 * [3, 4, 5]
 * [6, 7, 8]
 *
 * I then took the index of each element in the above arrays and stored them in a Map
 with each index being the key, pointing to the index of its adjacent elements stored in an array.
 * Using the indexes 0 and 4 as examples, they are stored in the Map like this:
     {
     0: [1, 3]
     4: [1, 3, 5, 7]
     }
 *
 * Initialize a counter to keep track of the number of days.
 * Merge each of the 3 input arrays into a single array.
 * Iterate over the merged array and check if an element has an updated state (1).
 * If yes, move to the next element.
 * If not, using the Map, check to see if the non-updated element has any adjacent element that is updated.
 * If yes, update the element to a 1 and move on to the next element.
 * If not, move to the next element.
 * The first iteration of the loop represents a day so counter incremented by 1.
 * Keep iterating until all elements in the merged array are updated.
 * Return the counter
 */

public class Server {
    private HashMap<Integer, int[]> adjacentElementsIndexes;

    private HashMap<Integer, int[]> getIndexesOfAdjacentElements() {
        HashMap<Integer, int[]> adjacentElementsIndexes = new HashMap<>();

        adjacentElementsIndexes.put(0, new int[]{1, 3});
        adjacentElementsIndexes.put(1, new int[]{0, 2, 4});
        adjacentElementsIndexes.put(2, new int[]{1, 5});
        adjacentElementsIndexes.put(3, new int[]{0, 4, 6});
        adjacentElementsIndexes.put(4, new int[]{1, 3, 5, 7});
        adjacentElementsIndexes.put(5, new int[]{2, 4, 8});
        adjacentElementsIndexes.put(6, new int[]{3, 7});
        adjacentElementsIndexes.put(7, new int[]{6, 8, 4});
        adjacentElementsIndexes.put(8, new int[]{5, 7});

        return adjacentElementsIndexes;
    }

    private Integer[] mergeArrays(Integer[] array1, Integer[] array2, Integer[] array3) {
        Integer firstArrayLength = array1.length;
        Integer secondArrayLength = array2.length;
        Integer thirdArrayLength = array3.length;

        Integer[] mergedArray = new Integer[firstArrayLength + secondArrayLength + thirdArrayLength];

        System.arraycopy(array1, 0, mergedArray, 0, firstArrayLength);
        System.arraycopy(array2, 0, mergedArray, firstArrayLength, secondArrayLength);
        System.arraycopy(array3, 0, mergedArray, firstArrayLength + secondArrayLength, thirdArrayLength);

        return mergedArray;
    }

    private boolean allServersUpdated(Integer[] array) {
        return array != null && Arrays.stream(array).allMatch(item -> item == 1);
    }

    public Integer updateAllServers(Integer[] array1, Integer[] array2, Integer[] array3) {
        Integer numberOfDays = 0;
        Integer [] mergedArray = this.mergeArrays(array1, array2, array3);

        while (true) {
            // Check if every element has an updated state of 1
            boolean allServersUpdated = this.allServersUpdated(mergedArray);

            if(!allServersUpdated) {
                AtomicInteger index = new AtomicInteger();
                Stream<Integer> myNewStream = Arrays.stream(mergedArray).map(element -> {
                    // Get the indexes of all adjacent elements for the current element
                    int i = index.getAndIncrement();
                    int[] adjacentElements = this.adjacentElementsIndexes.get(i);

                    // check if any of the adjacent elements has updated state (1)
                    for (int j = 0; j < adjacentElements.length; j++) {
                        boolean containsUpdatedState = mergedArray[adjacentElements[j]] == 1;

                        if (containsUpdatedState) {
                            element = 1;
                            break;
                        }
                    }

                    return element;
                });

                Integer[] myNewArray = myNewStream.toArray(Integer[]::new);

                // Update the merged array using the new array created
                for (int i = 0; i < myNewArray.length; i++) {
                    mergedArray[i] = myNewArray[i];
                }

                numberOfDays++;
            } else {
                break;
            }
        }

        return numberOfDays;
    }

    public Server() {
        this.adjacentElementsIndexes = this.getIndexesOfAdjacentElements();
    }

    public static void main(String[] args) {
        Server server = new Server();
        Integer[] myArray = {1, 0, 0};
        Integer[] myArray2 = {0, 0, 0};
        Integer[] myArray3 = {0, 0, 1};

        System.out.println(server.updateAllServers(myArray, myArray2, myArray3));
    }
}
