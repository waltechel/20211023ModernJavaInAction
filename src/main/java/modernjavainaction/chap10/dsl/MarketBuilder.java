package modernjavainaction.chap10.dsl;

import modernjavainaction.chap10.dsl.model.Trade;

public class MarketBuilder {
	private final MethodChainingOrderBuilder builder;
	private final Trade trade;

	public MarketBuilder(MethodChainingOrderBuilder builder, Trade trade) {
		this.builder = builder;
		this.trade = trade;
	}

	/**
	 * 가격 넣으면 MethodChainginOrderBuilder
	 * @param price
	 * @return
	 */
	public MethodChainingOrderBuilder at(double price) {
		trade.setPrice(price);
		return builder.addTrade(trade);
	}
}
