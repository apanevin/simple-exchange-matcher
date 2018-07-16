package com.github.apanevin.matcher.model

/**
  * Represents different security papers names
  */
sealed trait Security { def value: String }
case object A extends Security { val value = "A" }
case object B extends Security { val value = "B" }
case object C extends Security { val value = "C" }
case object D extends Security { val value = "D" }

object Security {
  def fromString(value: String): Security = {
    List(A, B, C, D).find(_.value == value).get
  }
}
