package com.my.poc.position;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Instrument {
    String cusip;
    String symbol;
    String description;
    String assetType;
    String type;
}
