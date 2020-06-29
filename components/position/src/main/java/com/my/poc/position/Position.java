package com.my.poc.position;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class Position {
    Instrument instrument;
    BigDecimal marketValue;
    BigDecimal quantity;
    BigDecimal price;
    BigDecimal purchasePrice;
    BigDecimal costBasis;
    BigDecimal gainLoss;
    BigDecimal gainLossPercent;
}
