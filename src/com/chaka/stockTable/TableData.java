package com.chaka.stockTable;

import java.time.LocalDateTime;

public class TableData {
    public String userId;
    public String merchantId;
    public LocalDateTime createdAt;

    public TableData (final String merchantId, final String userId) {
        this.userId = userId;
        this.merchantId = merchantId;
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public String getUserId() {
        return this.userId;
    }
}

