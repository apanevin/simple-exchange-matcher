package com.github.apanevin.matcher.util

import org.scalatest.{FlatSpec, Matchers}

class ListedMapSpec extends FlatSpec with Matchers {

  it should "get single element" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)

    map.get("key") should equal(Some(10))
  }

  it should "check single element" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)

    map.contains("key") should equal(true)
    map.contains("yek") should equal(false)
  }

  it should "pull single element" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)

    map.pull("key") should equal(Some(10))
  }

  it should "pull single element and remove it" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)

    map.pull("key") should equal(Some(10))
    map.pull("key") should equal(None)
  }

  it should "get the same element" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)
    map += ("key", 20)

    map.get("key") should equal(Some(10))
    map.get("key") should equal(Some(10))
  }

  it should "pull multiple elements" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)
    map += ("key", 20)

    map.pull("key") should equal(Some(10))
    map.pull("key") should equal(Some(20))
  }

  it should "remove single element" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)
    map += ("key", 20)
    map -= "key"

    map.get("key") should equal(Some(20))
  }

  it should "remove multiple element" in {
    val map = new ListedMap[String, Int]
    map += ("key", 10)
    map += ("key", 20)
    map -= "key"
    map -= "key"

    map.get("key") should be(None)
  }
}
