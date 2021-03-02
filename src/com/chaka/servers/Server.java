package com.chaka.servers;

import java.util.*;

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
    public int numberOfDays;
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

    public int updateAllServers(int[] array1, int[] array2, int[] array3) {

    }

    public Server() {
        this.adjacentElementsIndexes = this.getIndexesOfAdjacentElements();
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
