package com.my.poc.position;

import reactor.core.publisher.Flux;

public interface PositionStore {
    Flux<Position> getPositions(String accountId);
    Flux<AccountPosition> getPositionsForSymbol(String accountId, String symbol);
}
