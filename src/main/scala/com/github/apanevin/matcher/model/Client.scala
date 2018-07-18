package com.github.apanevin.matcher.model

/**
  * Represents client's information
  *
  * @param name Unique client's name
  * @param balance Client's currency balance (in dollars)
  * @param securities Map which stores all securities balances (map key is a unique capital letter)
  */
case class Client(name: String,
                  balance: Long,
                  securities: Map[Security, Int]) {
  /**
    * Creates new [[Client]] with sold security processed
    *
    * @param security [[Security]] paper identifier
    * @param price Price for one security
    * @param amount Amount of securities which should be sold
    * @return new [[Client]] object due to immutable approach
    */
  def sell(security: Security, price: Long, amount: Int) = {
    val total = price * amount
    new Client(this.name, this.balance + total, this.securities + (security -> (securities(security) - amount)))
  }

  /**
    * Creates new [[Client]] with bought security processed
    *
    * @param security [[Security]] paper identifier
    * @param price Price for one security
    * @param amount Amount of securities which should be bought
    * @return new [[Client]] object due to immutable approach
    */
  def buy(security: Security, price: Long, amount: Int) = {
    val total = price * amount
    new Client(this.name, this.balance - total, this.securities + (security -> (securities(security) + amount)))
  }

  /**
    * Builds a string from [[Client]] object
    * @param sep Fields separator
    * @return String with all client's fields, separated with parametrized symbol
    */
  def mkString(sep: String): String = {
    val builder = StringBuilder.newBuilder
    builder.append(name).append(sep)
    builder.append(balance).append(sep)
    builder.append(securities(A)).append(sep)
    builder.append(securities(B)).append(sep)
    builder.append(securities(C)).append(sep)
    builder.append(securities(D))

    builder.toString()
  }
}

object Client {
  def apply(name: String,
            balance: Long,
            securityAAmount: Int,
            securityBAmount: Int,
            securityCAmount: Int,
            securityDAmount: Int): Client = {
    val securities: Map[Security, Int] = Map(A -> securityAAmount,
      B -> securityBAmount,
      C -> securityCAmount,
      D -> securityDAmount)
    new Client(name, balance, securities)
  }
}
