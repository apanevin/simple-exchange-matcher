package com.github.apanevin.matcher.service

import com.github.apanevin.matcher.model.{Client, Order}
import com.typesafe.scalalogging.StrictLogging

/**
  * Represents exchange mechanism between two clients
  */
trait ExchangeMatcher extends StrictLogging {

  case class Exchange(buy: Option[Client], sell: Option[Client])

  /**
    * Processes all orders: matches (buy, sell) pairs and updates corresponding clients
    *
    * @param clients List of clients, which should be updated
    * @param orders Buy/sell orders, which should be processed
    * @return Updated list of clients with changed balance and securities amount
    */
  def process(clients: List[Client], orders: Iterator[Order]): List[Client]
}
