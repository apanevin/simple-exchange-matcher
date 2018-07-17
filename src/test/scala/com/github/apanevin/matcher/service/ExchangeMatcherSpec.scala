package com.github.apanevin.matcher.service

import com.github.apanevin.matcher.model._
import org.scalatest.{FlatSpec, Matchers}

class ExchangeMatcherSpec extends FlatSpec with Matchers {

  it should "process simple exchange" in {
    val clients = List(
      Client("C1", 0, 5, 0, 0, 0),
      Client("C2", 100, 0, 0, 0, 0)
    )
    val orders = List(
      Order("C1", Sell, A, 20, 5),
      Order("C2", Buy, A, 20, 5)
    )

    val updated = ExchangeMatcher.process(clients, orders.toIterator)
    updated(0) should equal(Client("C1", 100, 0, 0, 0, 0))
    updated(1) should equal(Client("C2", 0, 5, 0, 0, 0))
  }

  it should "process complex exchange" in {
    val clients = List(
      Client("C1", 0, 5, 0, 0, 0),
      Client("C2", 100, 0, 0, 0, 0),
      Client("C3", 60, 0, 0, 0, 3)
    )
    val orders = List(
      Order("C3", Sell, D, 20, 2),
      Order("C1", Sell, A, 20, 5),
      Order("C2", Buy, A, 20, 5),
      Order("C1", Buy, D, 20, 2)
    )

    val updated = ExchangeMatcher.process(clients, orders.toIterator)
    updated(0) should equal(Client("C1", 60, 0, 0, 0, 2))
    updated(1) should equal(Client("C2", 0, 5, 0, 0, 0))
    updated(2) should equal(Client("C3", 100, 0, 0, 0, 1))
  }

  it should "ignore exchange from one client" in {
    val clients = List(
      Client("C1", 0, 5, 0, 0, 0),
      Client("C2", 100, 0, 0, 0, 0)
    )
    val orders = List(
      Order("C1", Sell, A, 20, 5),
      Order("C1", Buy, A, 20, 5),
      Order("C2", Buy, A, 20, 5)
    )

    val updated = ExchangeMatcher.process(clients, orders.toIterator)
    updated(0) should equal(Client("C1", 100, 0, 0, 0, 0))
    updated(1) should equal(Client("C2", 0, 5, 0, 0, 0))
  }
}
