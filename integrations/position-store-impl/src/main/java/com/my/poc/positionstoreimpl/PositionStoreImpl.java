package com.my.poc.positionstoreimpl;

import com.my.poc.position.AccountPosition;
import com.my.poc.position.Position;
import com.my.poc.position.PositionStore;
import reactor.core.publisher.Flux;

public class PositionStoreImpl implements PositionStore {

    @Override
    public Flux<Position> getPositions(String accountId) {
        // Integration with downstream service that provides this data
        // May require mapping to the domain if response from external service is in different format
        return null;
    }

    @Override
    public Flux<AccountPosition> getPositionsForSymbol(String accountId, String symbol) {
        // Integration with downstream service that provides this data
        // May require mapping to the domain if response from external service is in different format
        return null;
    }
}
