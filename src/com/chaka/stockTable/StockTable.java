package com.chaka.stockTable;

import java.util.*;
import java.lang.*;

public class StockTable {
    public HashMap<Integer, TableData> stockTable;

    private String generateUID() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    private void stored (String merchantId, String userId ) {
        if (merchantId.isEmpty() || userId.isEmpty()) return;
        if(this.stockTable.containsKey(userId)) return;

        Integer index = this.stockTable.size() + 1;
        TableData table = new TableData(merchantId, userId);

        this.stockTable.put(index, new TableData(merchantId, userId));
    }

    public HashMap<Integer, TableData> viewStockTable() {
        return this.stockTable;
    }

    public StockTable () {
        this.stockTable = new HashMap<>();
    }

    private void displayToTerminal(HashMap<Integer, TableData> sortedData) {
        for (Object key : sortedData.keySet()) {
            System.out.println("==============================");
            System.out.println(sortedData.get(key).getMerchantId());
            System.out.println(sortedData.get(key).getUserId());
            System.out.println(sortedData.get(key).getCreatedAt());
            System.out.println("==============================");
        }
    }

    private static HashMap<Integer, TableData> sortByDate(HashMap<Integer, TableData> dataToSort, String orderBy) {
        // Validate orderBy to be either "asc" or "desc"
        if(!orderBy.equals("asc") && !orderBy.equals("desc")) {
            throw new IllegalArgumentException("orderBy should be asc or desc");
        }

        // Create a list from elements of HashMap
        List<Map.Entry<Integer, TableData> > list =
                new LinkedList<Map.Entry<Integer, TableData> >(dataToSort.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, TableData> >() {
            public int compare(Map.Entry<Integer, TableData> first, Map.Entry<Integer, TableData> second) {
                if (orderBy.equals("asc")) {
                    return (first.getValue().getCreatedAt()).compareTo(second.getValue().getCreatedAt());
                } else {
                    return (second.getValue().getCreatedAt()).compareTo(first.getValue().getCreatedAt());
                }
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, TableData> temp = new LinkedHashMap<Integer, TableData>();
        for (Map.Entry<Integer, TableData> item : list) {
            temp.put(item.getKey(), item.getValue());
        }

        return temp;
    }

    public HashMap<Integer, TableData> filterTable(String filterBy, String filterValue) {
        // Validate filterBy and filterValue values (check if empty)
        if(filterBy.isEmpty() || filterValue.isEmpty()) {
            throw new IllegalArgumentException("Please enter a value for filterBy or filterValue");
        }

        // Validate filterBy to be either "userId" or "merchantId"
        if(!filterBy.equals("userId") && !filterBy.equals("merchantId")) {
            throw new IllegalArgumentException("filterBy should be either userId or merchantId");
        }

        // initialize a new HashMap
        HashMap<Integer, TableData> newMap = new HashMap<>();

        // iterate through stockTable to get values that correspond with filterValue
        for (Map.Entry<Integer, TableData> entry : this.stockTable.entrySet()) {
            if(filterBy.equals("userId")) {
                if(entry.getValue().getUserId().equals(filterValue)) {
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }

            if(filterBy.equals("merchantId")) {
                System.out.println(entry.getValue().getMerchantId());
                if(entry.getValue().getMerchantId().equals(filterValue)) {
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }
        }

        // sort the new hashmap by date
        HashMap<Integer, TableData> sortedData = this.sortByDate(newMap, "desc");

        // Display the sorted data on terminal
        // It is not totally necessary, I just wanted a way to show the data in the class on the terminal
        this.displayToTerminal(sortedData);

        return sortedData;
    }

    public static void main(String[] args) {
        StockTable stockTable = new StockTable();
        stockTable.stored("merchantId", "userId3");
        stockTable.stored("merchantId2", "userId2");
        stockTable.stored("merchantId", "userId3");

        System.out.println(stockTable.filterTable("userId", "userId3"));
    }
}

