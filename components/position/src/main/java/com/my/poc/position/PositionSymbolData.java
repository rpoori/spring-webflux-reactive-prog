package com.my.poc.position;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class PositionSymbolData {
    Instrument instrument;
    BigDecimal marketValue;
    BigDecimal quantity;
    BigDecimal price;
    BigDecimal purchasePrice;
    BigDecimal costBasis;
    BigDecimal gainLoss;
    BigDecimal gainLossPercent;
    List<String> accounts;
    List<AccountPosition> accountPositions;
}
