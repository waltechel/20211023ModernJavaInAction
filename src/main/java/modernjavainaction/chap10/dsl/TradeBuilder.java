package modernjavainaction.chap10.dsl;

import modernjavainaction.chap10.dsl.model.Trade;

public class TradeBuilder {
	private final MethodChainingOrderBuilder builder;
	public final Trade trade = new Trade();

	TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
		this.builder = builder;
		trade.setType(type);
		trade.setQuantity(quantity);
	}

	/**
	 * 주식을 만들면 stockBuilder를 만들어야 한다.
	 * @param symbol
	 * @return
	 */
	public StockBuilder stock(String symbol) {
		return new StockBuilder(builder, trade, symbol);
	}
}
