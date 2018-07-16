package com.github.apanevin.matcher

import com.typesafe.config.ConfigFactory

/**
 * Entry point for the whole project
 *
 */
object App {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val clientsFile = config.getString("file.input.clients.path")
    val ordersFile = config.getString("file.input.orders.path")
    val delimiter = config.getString("file.delimiter")

    clientsFile.getClients(delimiter) foreach println
    ordersFile.getOrdersIterator(delimiter).take(10) foreach println
  }
}
