package com.github.apanevin.matcher

import com.typesafe.config.ConfigFactory

/**
 * Entry point for the whole project
 *
 */
object App {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val name = config.getString("name")

    println(s"Hello, $name!")
  }
}
