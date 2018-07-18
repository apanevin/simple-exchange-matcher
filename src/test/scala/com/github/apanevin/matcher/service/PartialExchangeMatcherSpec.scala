package com.github.apanevin.matcher.service

import com.github.apanevin.matcher.model._
import org.scalatest.{FlatSpec, Matchers}

class PartialExchangeMatcherSpec extends FlatSpec with Matchers {

  it should "process simple partial exchange" in {
    val clients = List(
      Client("C1", 0, 5, 0, 0, 0),
      Client("C2", 100, 0, 0, 0, 0)
    )
    val orders = List(
      Order("C1", Sell, A, 20, 5),
      Order("C2", Buy, A, 20, 4)
    )

    val updated = new PartialExchangeMatcher().process(clients, orders.toIterator)
    updated(0) should equal(Client("C1", 80, 1, 0, 0, 0))
    updated(1) should equal(Client("C2", 20, 4, 0, 0, 0))
  }

  it should "process complex partial exchange" in {
    val clients = List(
      Client("C1", 0, 8, 0, 0, 0),
      Client("C2", 100, 0, 0, 0, 0),
      Client("C3", 60, 0, 0, 0, 0)
    )
    val orders = List(
      Order("C3", Buy, A, 20, 3),
      Order("C1", Sell, A, 20, 8),
      Order("C2", Buy, A, 20, 5)
    )

    val updated = new PartialExchangeMatcher().process(clients, orders.toIterator)
    updated(0) should equal(Client("C1", 160, 0, 0, 0, 0))
    updated(1) should equal(Client("C2", 0, 5, 0, 0, 0))
    updated(2) should equal(Client("C3", 0, 3, 0, 0, 0))
  }

  it should "process even more complex partial exchange" in {
    val clients = List(
      Client("C1", 0, 8, 0, 0, 0),
      Client("C2", 100, 0, 0, 0, 0),
      Client("C3", 60, 0, 0, 0, 0)
    )
    val orders = List(
      Order("C3", Buy, A, 20, 3),
      Order("C1", Sell, A, 20, 4),
      Order("C1", Sell, A, 20, 4),
      Order("C2", Buy, A, 20, 5)
    )

    val updated = new PartialExchangeMatcher().process(clients, orders.toIterator)
    updated(0) should equal(Client("C1", 160, 0, 0, 0, 0))
    updated(1) should equal(Client("C2", 0, 5, 0, 0, 0))
    updated(2) should equal(Client("C3", 0, 3, 0, 0, 0))
  }

  it should "process even more complex partial exchange and ignore one user exchange" in {
    val clients = List(
      Client("C1", 0, 8, 0, 0, 0),
      Client("C2", 100, 0, 0, 0, 0),
      Client("C3", 60, 0, 0, 0, 0)
    )
    val orders = List(
      Order("C3", Buy, A, 20, 3),
      Order("C1", Sell, A, 20, 4),
      Order("C1", Buy, A, 20, 2),
      Order("C1", Sell, A, 20, 4),
      Order("C2", Buy, A, 20, 5)
    )

    val updated = new PartialExchangeMatcher().process(clients, orders.toIterator)
    updated(0) should equal(Client("C1", 160, 0, 0, 0, 0))
    updated(1) should equal(Client("C2", 0, 5, 0, 0, 0))
    updated(2) should equal(Client("C3", 0, 3, 0, 0, 0))
  }
}
