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
                  securities: Map[Security, Int])

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
