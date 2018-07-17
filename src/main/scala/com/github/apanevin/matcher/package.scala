package com.github.apanevin

import com.github.apanevin.matcher.model.{Client, Order, OrderType, Security}

import scala.io.Source

/**
  * Provides an implicit class [[com.github.apanevin.matcher.RichFileString]] for manipulating with files
  */
package object matcher {

  implicit class RichFileString(fileString: String) {

    /**
      * Reads and filters [[fileString]] client file
      *
      * @param delimiter Row's delimiter
      * @return List with all filtered clients
      */
    def getClients(delimiter: String): List[Client] = {
      getLines.flatMap(row => row.split(delimiter) match {
        case Array(name, balance, securityAAmount, securityBAmount, securityCAmount, securityDAmount) =>
          Some(Client(name,
            balance.toLong,
            securityAAmount.toInt,
            securityBAmount.toInt,
            securityCAmount.toInt,
            securityDAmount.toInt))
        case _ =>
          println(s"Invalid client row: $row") // todo: add logger
          None
      }).toList
    }

    /**
      * Reads and filters [[fileString]] order file
      *
      * @param delimiter Row's delimiter
      * @return Iterator with all filtered [[Order]] objects
      */
    def getOrdersIterator(delimiter: String): Iterator[Order] = {
      getLines.flatMap(row => row.split(delimiter) match {
        case Array(client, sType, security, price, amount) =>
          Some(Order(client,
            OrderType.fromString(sType),
            Security.fromString(security),
            price.toLong,
            amount.toInt))
        case _ =>
          println(s"Invalid order row: $row") // todo: add logger
          None
      })
    }

    private def getLines: Iterator[String] = {
      Source.fromFile(fileString).getLines()
    }
  }
}
