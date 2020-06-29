package com.my.poc.position;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountPosition {
    String accountId;
    Position position;
}
