package com.my.poc.position;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RequiredArgsConstructor
public class GetConsolidatedSymbols {

    private final PositionStore positionStore;

    public Flux<String> execute(List<String> accountIds) {

        return Flux.fromIterable(accountIds)
                .flatMap(accountId -> positionStore.getPositions(accountId)
                        .subscribeOn(Schedulers.elastic()))
                .map(Position::getInstrument)
                .filter(instrument -> instrument.getSymbol() != null)
                .map(Instrument::getSymbol)
                .distinct();
    }
}
