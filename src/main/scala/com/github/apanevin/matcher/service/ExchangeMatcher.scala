package com.github.apanevin.matcher.service

import com.github.apanevin.matcher.model._
import com.github.apanevin.matcher.util.ListedMap

/**
  * Represents exchange mechanism between two clients
  */
object ExchangeMatcher {

  type Exchange = (Option[Client], Option[Client])

  /**
    * Processes all orders: matches (buy, sell) pairs and updates corresponding clients
    *
    * @param clients List of clients, which should be updated
    * @param orders Buy/sell orders, which should be processed
    * @return Updated list of clients with changed balance and securities amount
    */
  def process(clients: List[Client],
              orders: Iterator[Order]): List[Client] = {
    var sellMap = new ListedMap[(Security, Long, Int), String]
    var buyMap = new ListedMap[(Security, Long, Int), String]
    var processed = clients.map(client => (client.name, client)).toMap

    orders.foreach { order =>
      val key = (order.security, order.price, order.amount) // todo: add entity
      var exchangeClients: Exchange = (None, None) // first buys, second sells

      order.`type` match {
        case Sell =>
          if (buyMap.contains(key) && buyMap.get(key).get != order.client) {
            exchangeClients = (Some(processed(buyMap.pull(key).get)), Some(processed(order.client)))
          } else {
            sellMap += key -> order.client
          }
        case Buy =>
          if (sellMap.contains(key) && sellMap.get(key).get != order.client) {
            exchangeClients = (Some(processed(order.client)), Some(processed(sellMap.pull(key).get)))
          } else {
            buyMap += key -> order.client
          }
      }

      for (buyClient <- exchangeClients._1;
        sellClient <- exchangeClients._2) {

        processed = processed + (buyClient.name -> buyClient.buy(order.security, order.price, order.amount))
        processed = processed + (sellClient.name -> sellClient.sell(order.security, order.price, order.amount))
      }
    }
    processed.values.toList.sortBy(_.name)
  }
}
