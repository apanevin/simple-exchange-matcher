package com.github.apanevin.matcher.model

/**
  * Represents exchange order's information
  *
  * @param client Client name who placed an order
  * @param `type` Order type: sell or buy
  * @param security Security paper which is used in order
  * @param price Price for one security paper
  * @param amount Amount of securities which need to be processed (sold or bought)
  */
case class Order(client: String,
                 `type`: OrderType,
                 security: Security,
                 price: Long,
                 amount: Int)
