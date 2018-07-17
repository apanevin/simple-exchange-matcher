package com.github.apanevin.matcher.util

/**
  * Map which holds [[List]] as value
  */
class ListedMap[A, B] {

  private var map: Map[A, List[B]] = Map.empty

  /**
    * Adds a new pair into [[map]]
    *
    * @param kv Inserted pair
    */
  def +=(kv: (A, B)): Unit = map.get(kv._1) match {
    case Some(list) => map = map + (kv._1 -> (list :+ kv._2))
    case None => map = map + (kv._1 -> List(kv._2))
  }

  /**
    * Removes an entry from [[map]]
    *
    * @param key Key which should be removed
    */
  def -=(key: A): Unit = map.get(key) match {
    case Some(Nil) => map = map - key
    case Some(value :: tail) => map = map + (key -> tail)
    case _ =>
  }

  /**
    * Checks if key is present in [[map]]
    *
    * @param key Key which should be checked
    * @return Boolean value which indicates keys presence
    */
  def contains(key: A): Boolean = map.contains(key) && get(key).isDefined

  /**
    * Gets optional value associated with a key
    *
    * @param key Key value
    * @return Optional value associated with a given key
    */
  def get(key: A): Option[B] = map.get(key) match {
    case Some(Nil) => None
    case Some(list) => Some(list.head)
    case _ => None
  }

  /**
    * Gets optional value associated with a key and removes this value from [[map]]
    *
    * @param key Key value
    * @return Optional value associated with a given key
    */
  def pull(key: A): Option[B] = map.get(key) match {
    case Some(Nil) =>
      map = map - key
      None
    case Some(value :: tail) =>
      map = map + (key -> tail)
      Some(value)
    case _ => None
  }
}
