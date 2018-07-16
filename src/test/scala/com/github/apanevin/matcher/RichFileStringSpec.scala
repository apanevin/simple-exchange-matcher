package com.github.apanevin.matcher

import com.github.apanevin.matcher.model.Buy
import org.scalatest.{FlatSpec, Matchers}

class RichFileStringSpec  extends FlatSpec with Matchers {
  val clientFile = "./src/test/resources/clients.txt"
  val invalidClientFile = "./src/test/resources/clients_inv.txt"
  val orderFile = "./src/test/resources/orders.txt"
  val invalidOrderFile = "./src/test/resources/orders_inv.txt"

  it should "read clients file" in {
    val clients = clientFile.getClients("\t")

    clients.size should equal(3)
    clients("C1").balance should equal(1000)
  }

  it should "filter invalid client's rows" in {
    val clients = invalidClientFile.getClients("\t")

    clients.size should equal(2)
    clients.get("C3") should be(None)
  }

  it should "read orders file" in {
    val orders = orderFile.getOrdersIterator("\t").toList

    orders.size should equal(5)
    orders.head.`type` should equal(Buy)
  }

  it should "filter invalid order's rows" in {
    val orders = invalidOrderFile.getOrdersIterator("\t").toList

    orders.size should equal(4)
  }
}
