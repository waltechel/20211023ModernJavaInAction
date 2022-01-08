package modernjavainaction.chap10.dsl;

import modernjavainaction.chap10.dsl.model.Stock;
import modernjavainaction.chap10.dsl.model.Trade;

public class StockBuilder {
	private final MethodChainingOrderBuilder builder;
	private final Trade trade;
	private final Stock stock = new Stock();

	public StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
		this.builder = builder;
		this.trade = trade;
		stock.setSymbol(symbol);
	}

	public MarketBuilder on(String market) {
		stock.setMarket(market);
		trade.setStock(stock);
		return new MarketBuilder(builder, trade);
	}

}
