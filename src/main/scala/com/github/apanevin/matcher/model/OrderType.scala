package com.github.apanevin.matcher.model

/**
  * Represents different order types
  */
sealed trait OrderType { def value: String }
case object Sell extends OrderType { val value = "s" }
case object Buy extends OrderType { val value = "b" }

object OrderType {
  def fromString(value: String): OrderType = {
    List(Sell, Buy).find(_.value == value).get
  }
}
