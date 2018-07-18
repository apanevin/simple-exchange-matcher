package com.github.apanevin.matcher.model

import org.scalatest.{FlatSpec, Matchers}

class ClientSpec extends FlatSpec with Matchers {

  it should "change balance and securities amount after selling" in {
    val client = Client("C1", 1000, 0, 0, 20, 0)
    val updated = client.sell(C, 30, 10)

    updated.balance should equal(1300)
    updated.securities(C) should equal(10)
  }

  it should "change balance and securities amount after buying" in {
    val client = Client("C1", 500, 5, 5, 5, 5)
    val updated = client.buy(D, 30, 10)

    updated.balance should equal(200)
    updated.securities(D) should equal(15)
  }

  it should "be equal after selling and buying the same amount with equal price" in {
    val client = Client("C1", 100, 3, 3, 3, 3)
    val updated = client.sell(C, 100, 5).buy(C, 100, 5)

    client should equal(updated)
  }

  it should "make string interpretation" in {
    val client = Client("C1", 100, 1, 2, 3, 4)
    val clientString = "C1,100,1,2,3,4"

    client.mkString(",") should equal(clientString)
  }
}
