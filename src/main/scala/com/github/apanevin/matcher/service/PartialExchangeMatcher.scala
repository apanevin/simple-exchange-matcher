package com.github.apanevin.matcher.service

import com.github.apanevin.matcher.model._
import com.github.apanevin.matcher.util.ListedMap

/**
  * Represents partial (join only on price) exchange mechanism between two clients
  */
class PartialExchangeMatcher extends ExchangeMatcher {

  type PartialJoinKey = (Security, Long)

  /**
    * Matches orders using (security, price) key and updates corresponding clients
    *
    * @param clients List of clients, which should be updated
    * @param orders Buy/sell orders, which should be processed
    * @return Updated list of clients with changed balance and securities amount
    */
  override def process(clients: List[Client], orders: Iterator[Order]): List[Client] = {
    logger.info(s"Started processing orders stream for ${clients.size} clients")
    var sellMap = new ListedMap[PartialJoinKey, String]
    var buyMap = new ListedMap[PartialJoinKey, String]
    var processed = clients.map(client => (client.name, client)).toMap

    orders.foreach { order =>
      val key = (order.security, order.price)

      order.`type` match {
        case Sell =>
          (1 to order.amount) foreach { _ =>
            if (buyMap.contains(key) && buyMap.get(key).get != order.client) {
              for (buyClient <- processed.get(buyMap.pull(key).get);
                   sellClient <- processed.get(order.client)) {
                processed = processed + (buyClient.name -> buyClient.buy(order.security, order.price, 1))
                processed = processed + (sellClient.name -> sellClient.sell(order.security, order.price, 1))
              }
            } else {
              sellMap += key -> order.client
            }
          }
        case Buy =>
          (1 to order.amount) foreach { _ =>
            if (sellMap.contains(key) && sellMap.get(key).get != order.client) {
              for (buyClient <- processed.get(order.client);
                   sellClient <- processed.get(sellMap.pull(key).get)) {
                processed = processed + (buyClient.name -> buyClient.buy(order.security, order.price, 1))
                processed = processed + (sellClient.name -> sellClient.sell(order.security, order.price, 1))
              }
            } else {
              buyMap += key -> order.client
            }
          }
      }
    }
    processed.values.toList.sortBy(_.name)
  }
}
