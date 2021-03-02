package com.chaka.stockTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

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

    private HashMap<Integer, TableData> viewStockTable() {
        return this.stockTable;
    }

    public StockTable () {
        this.stockTable = new HashMap<>();
    }

    public static void main(String[] args) {
        StockTable stockTable = new StockTable();
        stockTable.stored("merchantId", "userId");
        stockTable.stored("merchantId2", "userId2");
        stockTable.stored("merchantId3", "userId3");
        System.out.println(stockTable.viewStockTable());
        System.out.println(stockTable.generateUID());
    }
}

