package com.htc.studio.demo

import anorm._

import play.api.db.DB
import play.api.mvc._
import play.api.libs.json._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller {
  /**
   * Create a json string from a h2 table. Only shows two columns, one is
   * String type and the other is Long type.
   * @param table Table name.
   * @param strCol Name of the String column.
   * @param longCol Name of the Long column.
   * @author Zhongyang Zheng (zhongyang_zheng@htc.com)
   */
  def h2(table: String, strCol: String, longCol: String) = Action {
    DB.withConnection("h2") { implicit conn =>
      val data = SQL("select * from " + table)()
        .map(row => Json.obj(strCol -> row[String](strCol), longCol -> row[Long](longCol)))
      Ok(Json.toJson(data))
    }
  }

  /**
   * Create a map graph for map count demo.
   * @param table Table name of the data source. The column names must be word
   * and count.
   * @param reload The interval (in seconds) to sleep before reload the graph.
   * Useful for real-time demos. Default is -1 (i.e. no reload).
   * @author Zhongyang Zheng (zhongyang_zheng@htc.com)
   */
  def mapcount(table: String, reload: Int) =
    Action(Ok(html.MapCount(table, reload)))

  /**
   * Create a tag cloud graph for word count demo.
   * @param table Table name of the data source. The column names must be word
   * and count.
   * @param min Only words with at least min counts are shown.
   * Default is 1 (all words).
   * @param length Only words not longer than length are shown.
   * Default is Int.MaxValue (all words).
   * @param reload The interval (in seconds) to sleep before reload the graph.
   * Useful for real-time demos. Default is -1 (i.e. no reload).
   * @author Zhongyang Zheng (zhongyang_zheng@htc.com)
   */
  def wordcount(table: String, min: Int, length: Int, reload: Int) =
    Action(Ok(html.WordCount(table, min, length, reload)))

  /**
   * Create a json string from a h2 table for sentiment demo.
   * @param table Table name.
   * @param id id of tweet.
   * @param tweet content of tweet.
   * @param sentiment type of tweet.
   * @author Yonglin Fu (yonglin_fu@htc.com)
   */
  def h2Sentiment(table: String, id: String, tweet: String, sentiment: String) = 
    Action { DB.withConnection("h2") { implicit conn =>
      val data = SQL("select * from " + table +
          " where SENTIMENT <4 and SENTIMENT != 2 order by ID DESC ")()
        .map(row => Json.obj(id -> row[Long](id).toString, tweet -> row[String](tweet),
            sentiment -> row[Int](sentiment)))
      Ok(Json.toJson(data))
    }}

  /**
   * Charts for tweet sentiment demo.
   * @param table Table name of the data source.
   * @author Yonglin Fu (yonglin_fu@htc.com)
   */
  def sentiment(table: String) = Action(Ok(html.Sentiment(table)))
}
