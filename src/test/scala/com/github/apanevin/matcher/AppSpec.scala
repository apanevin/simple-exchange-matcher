package com.github.apanevin.matcher

import org.scalatest.{FlatSpec, Matchers}

/**
  * Template test for ''mvn test'' check
  */
class AppSpec extends FlatSpec with Matchers {

  "An App object" should "do nothing" in {
    2 + 2 should equal(4)
  }
}
