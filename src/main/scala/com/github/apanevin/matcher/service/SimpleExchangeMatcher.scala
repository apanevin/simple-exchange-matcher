package com.github.apanevin.matcher.service

import com.github.apanevin.matcher.model._
import com.github.apanevin.matcher.util.ListedMap

/**
  * Represents simple (full join on amount and price) exchange mechanism between two clients
  */
class SimpleExchangeMatcher extends ExchangeMatcher {

  type FullJoinKey = (Security, Long, Int)

  /**
    * Matches orders using (security, price, amount) key and updates corresponding clients
    *
    * @param clients List of clients, which should be updated
    * @param orders Buy/sell orders, which should be processed
    * @return Updated list of clients with changed balance and securities amount
    */
  override def process(clients: List[Client], orders: Iterator[Order]): List[Client] = {
    logger.info(s"Started processing orders stream for ${clients.size} clients")
    var sellMap = new ListedMap[FullJoinKey, String]
    var buyMap = new ListedMap[FullJoinKey, String]
    var processed = clients.map(client => (client.name, client)).toMap

    orders.foreach { order =>
      val key = (order.security, order.price, order.amount)
      var exchangeClients = Exchange(None, None)

      order.`type` match {
        case Sell =>
          if (buyMap.contains(key) && buyMap.get(key).get != order.client) {
            exchangeClients = Exchange(processed.get(buyMap.pull(key).get), processed.get(order.client))
          } else {
            sellMap += key -> order.client
          }
        case Buy =>
          if (sellMap.contains(key) && sellMap.get(key).get != order.client) {
            exchangeClients = Exchange(processed.get(order.client), processed.get(sellMap.pull(key).get))
          } else {
            buyMap += key -> order.client
          }
      }

      for (buyClient <- exchangeClients.buy;
        sellClient <- exchangeClients.sell) {

        processed = processed + (buyClient.name -> buyClient.buy(order.security, order.price, order.amount))
        processed = processed + (sellClient.name -> sellClient.sell(order.security, order.price, order.amount))
      }
    }
    processed.values.toList.sortBy(_.name)
  }
}
