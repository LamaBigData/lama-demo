// @SOURCE:/Users/simong_wu/Workspace/lama-demo-spain/play/conf/routes
// @HASH:c9b6081876af8ae065b215d88fd669a15375756c
// @DATE:Sun Jan 25 20:15:09 CST 2015


import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString

object Routes extends Router.Routes {

import ReverseRouteContext.empty

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val com_htc_studio_demo_Application_h20_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("demo/h2/"),DynamicPart("table", """[^/]+""",true),StaticPart("/"),DynamicPart("strCol", """[^/]+""",true),StaticPart("/"),DynamicPart("longCol", """[^/]+""",true))))
private[this] lazy val com_htc_studio_demo_Application_h20_invoker = createInvoker(
com.htc.studio.demo.Application.h2(fakeValue[String], fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "h2", Seq(classOf[String], classOf[String], classOf[String]),"GET", """ Demo""", Routes.prefix + """demo/h2/$table<[^/]+>/$strCol<[^/]+>/$longCol<[^/]+>"""))
        

// @LINE:7
private[this] lazy val com_htc_studio_demo_Application_mapcount1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("demo/mapcount/"),DynamicPart("table", """[^/]+""",true))))
private[this] lazy val com_htc_studio_demo_Application_mapcount1_invoker = createInvoker(
com.htc.studio.demo.Application.mapcount(fakeValue[String], fakeValue[Int]),
HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "mapcount", Seq(classOf[String], classOf[Int]),"GET", """""", Routes.prefix + """demo/mapcount/$table<[^/]+>"""))
        

// @LINE:8
private[this] lazy val com_htc_studio_demo_Application_wordcount2_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("demo/wordcount/"),DynamicPart("table", """[^/]+""",true))))
private[this] lazy val com_htc_studio_demo_Application_wordcount2_invoker = createInvoker(
com.htc.studio.demo.Application.wordcount(fakeValue[String], fakeValue[Int], fakeValue[Int], fakeValue[Int]),
HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "wordcount", Seq(classOf[String], classOf[Int], classOf[Int], classOf[Int]),"GET", """""", Routes.prefix + """demo/wordcount/$table<[^/]+>"""))
        

// @LINE:9
private[this] lazy val com_htc_studio_demo_Application_h2Sentiment3_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("demo/h2st/"),DynamicPart("table", """[^/]+""",true),StaticPart("/"),DynamicPart("id", """[^/]+""",true),StaticPart("/"),DynamicPart("tweet", """[^/]+""",true),StaticPart("/"),DynamicPart("senti", """[^/]+""",true))))
private[this] lazy val com_htc_studio_demo_Application_h2Sentiment3_invoker = createInvoker(
com.htc.studio.demo.Application.h2Sentiment(fakeValue[String], fakeValue[String], fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "h2Sentiment", Seq(classOf[String], classOf[String], classOf[String], classOf[String]),"GET", """""", Routes.prefix + """demo/h2st/$table<[^/]+>/$id<[^/]+>/$tweet<[^/]+>/$senti<[^/]+>"""))
        

// @LINE:10
private[this] lazy val com_htc_studio_demo_Application_sentiment4_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("demo/sentiment/"),DynamicPart("table", """[^/]+""",true))))
private[this] lazy val com_htc_studio_demo_Application_sentiment4_invoker = createInvoker(
com.htc.studio.demo.Application.sentiment(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "sentiment", Seq(classOf[String]),"GET", """""", Routes.prefix + """demo/sentiment/$table<[^/]+>"""))
        

// @LINE:13
private[this] lazy val controllers_Assets_at5_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_at5_invoker = createInvoker(
controllers.Assets.at(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Static assets""", Routes.prefix + """assets/$file<.+>"""))
        

// @LINE:14
private[this] lazy val controllers_Assets_versioned6_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("vassets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_versioned6_invoker = createInvoker(
controllers.Assets.versioned(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]),"GET", """""", Routes.prefix + """vassets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """demo/h2/$table<[^/]+>/$strCol<[^/]+>/$longCol<[^/]+>""","""com.htc.studio.demo.Application.h2(table:String, strCol:String, longCol:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """demo/mapcount/$table<[^/]+>""","""com.htc.studio.demo.Application.mapcount(table:String, reload:Int ?= -1)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """demo/wordcount/$table<[^/]+>""","""com.htc.studio.demo.Application.wordcount(table:String, min:Int ?= 1, length:Int ?= Int.MaxValue, reload:Int ?= -1)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """demo/h2st/$table<[^/]+>/$id<[^/]+>/$tweet<[^/]+>/$senti<[^/]+>""","""com.htc.studio.demo.Application.h2Sentiment(table:String, id:String, tweet:String, senti:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """demo/sentiment/$table<[^/]+>""","""com.htc.studio.demo.Application.sentiment(table:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """vassets/$file<.+>""","""controllers.Assets.versioned(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case com_htc_studio_demo_Application_h20_route(params) => {
   call(params.fromPath[String]("table", None), params.fromPath[String]("strCol", None), params.fromPath[String]("longCol", None)) { (table, strCol, longCol) =>
        com_htc_studio_demo_Application_h20_invoker.call(com.htc.studio.demo.Application.h2(table, strCol, longCol))
   }
}
        

// @LINE:7
case com_htc_studio_demo_Application_mapcount1_route(params) => {
   call(params.fromPath[String]("table", None), params.fromQuery[Int]("reload", Some(-1))) { (table, reload) =>
        com_htc_studio_demo_Application_mapcount1_invoker.call(com.htc.studio.demo.Application.mapcount(table, reload))
   }
}
        

// @LINE:8
case com_htc_studio_demo_Application_wordcount2_route(params) => {
   call(params.fromPath[String]("table", None), params.fromQuery[Int]("min", Some(1)), params.fromQuery[Int]("length", Some(Int.MaxValue)), params.fromQuery[Int]("reload", Some(-1))) { (table, min, length, reload) =>
        com_htc_studio_demo_Application_wordcount2_invoker.call(com.htc.studio.demo.Application.wordcount(table, min, length, reload))
   }
}
        

// @LINE:9
case com_htc_studio_demo_Application_h2Sentiment3_route(params) => {
   call(params.fromPath[String]("table", None), params.fromPath[String]("id", None), params.fromPath[String]("tweet", None), params.fromPath[String]("senti", None)) { (table, id, tweet, senti) =>
        com_htc_studio_demo_Application_h2Sentiment3_invoker.call(com.htc.studio.demo.Application.h2Sentiment(table, id, tweet, senti))
   }
}
        

// @LINE:10
case com_htc_studio_demo_Application_sentiment4_route(params) => {
   call(params.fromPath[String]("table", None)) { (table) =>
        com_htc_studio_demo_Application_sentiment4_invoker.call(com.htc.studio.demo.Application.sentiment(table))
   }
}
        

// @LINE:13
case controllers_Assets_at5_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at5_invoker.call(controllers.Assets.at(path, file))
   }
}
        

// @LINE:14
case controllers_Assets_versioned6_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_versioned6_invoker.call(controllers.Assets.versioned(path, file))
   }
}
        
}

}
     