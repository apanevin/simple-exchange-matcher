package com.github.apanevin.matcher

import java.io.{File, PrintWriter}

import com.github.apanevin.matcher.service.ExchangeMatcher
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging

/**
 * Entry point for the whole project
 *
 */
object App extends StrictLogging {

  def main(args: Array[String]): Unit = {
    logger.info("Exchange matcher has been started")
    val config = ConfigFactory.load()
    val clientsFile = config.getString("file.input.clients.path")
    val ordersFile = config.getString("file.input.orders.path")
    val outputFileName = config.getString("file.output.clients.name")
    val delimiter = config.getString("file.delimiter")

    val clients = clientsFile.getClients(delimiter)
    val orders = ordersFile.getOrdersIterator(delimiter)

    val processed = ExchangeMatcher.process(clients, orders)

    val writer = new PrintWriter(new File(outputFileName))
    for (client <- processed) {
      writer.write(client.mkString(delimiter))
    }
    writer.close()
    logger.info("Exchange matcher has successfully processed all orders")
  }
}
