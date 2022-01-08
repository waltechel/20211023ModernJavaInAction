/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package modernjavainaction.chap10.dsl;

import modernjavainaction.chap10.dsl.model.Order;
import modernjavainaction.chap10.dsl.model.Trade;

/**
 * 최상위 수준 빌더를 만들고 주문을 감싼 다음 한 개 이상의 거래를 주문에 추가할 수 있어야 한다.
 * 
 * @author leedongjun
 *
 */
public class MethodChainingOrderBuilder {

	// 빌더로 감싼 주문
	public final Order order = new Order();

	private MethodChainingOrderBuilder(String customer) {
		order.setCustomer(customer);
	}

	public static MethodChainingOrderBuilder forCustomer(String customer) {
		return new MethodChainingOrderBuilder(customer);
	}

	public Order end() {
		return order;
	}

	public TradeBuilder buy(int quantity) {
		return new TradeBuilder(this, Trade.Type.BUY, quantity);
	}

	public TradeBuilder sell(int quantity) {
		return new TradeBuilder(this, Trade.Type.SELL, quantity);
	}

	// 한 개 이상의 거래를 주문에 추가할 수 있어야 한다.
	public MethodChainingOrderBuilder addTrade(Trade trade) {
		order.addTrade(trade);
		return this;
	}

}
