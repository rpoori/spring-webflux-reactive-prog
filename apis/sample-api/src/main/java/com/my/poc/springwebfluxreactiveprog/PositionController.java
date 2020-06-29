package com.my.poc.springwebfluxreactiveprog;

import com.my.poc.position.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PositionController {

    private final PositionStore positionStore;
    private final GetConsolidatedSymbols getConsolidatedSymbols;
    private final GetPositionDataConsolidatedBySymbol getPositionDataConsolidatedBySymbol;

    @GetMapping
    public Flux<Position> getPositionsForAccount(@RequestParam String accountId) {
        return positionStore.getPositions(accountId);
    }

    @GetMapping("symbols/consolidated")
    public Flux<String> getConsolidatedSymbols(@RequestParam List<String> accountIds) {
        return getConsolidatedSymbols.execute(accountIds);
    }

    @GetMapping("positions/consolidated")
    public Flux<PositionSymbolData> getPositionDataConsolidatedBySymbols(@RequestParam List<String> accountIds) {
        return getPositionDataConsolidatedBySymbol.execute(accountIds);
    }
}
