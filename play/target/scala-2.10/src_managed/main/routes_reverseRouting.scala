// @SOURCE:/Users/simong_wu/Workspace/lama-demo-spain/play/conf/routes
// @HASH:c9b6081876af8ae065b215d88fd669a15375756c
// @DATE:Sun Jan 25 20:15:09 CST 2015

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString


// @LINE:14
// @LINE:13
package controllers {

// @LINE:14
// @LINE:13
class ReverseAssets {


// @LINE:13
def at(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

// @LINE:14
def versioned(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "vassets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          
}
                  

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package com.htc.studio.demo {

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:9
def h2Sentiment(table:String, id:String, tweet:String, senti:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "demo/h2st/" + implicitly[PathBindable[String]].unbind("table", dynamicString(table)) + "/" + implicitly[PathBindable[String]].unbind("id", dynamicString(id)) + "/" + implicitly[PathBindable[String]].unbind("tweet", dynamicString(tweet)) + "/" + implicitly[PathBindable[String]].unbind("senti", dynamicString(senti)))
}
                        

// @LINE:10
def sentiment(table:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "demo/sentiment/" + implicitly[PathBindable[String]].unbind("table", dynamicString(table)))
}
                        

// @LINE:7
def mapcount(table:String, reload:Int = -1): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "demo/mapcount/" + implicitly[PathBindable[String]].unbind("table", dynamicString(table)) + queryString(List(if(reload == -1) None else Some(implicitly[QueryStringBindable[Int]].unbind("reload", reload)))))
}
                        

// @LINE:6
def h2(table:String, strCol:String, longCol:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "demo/h2/" + implicitly[PathBindable[String]].unbind("table", dynamicString(table)) + "/" + implicitly[PathBindable[String]].unbind("strCol", dynamicString(strCol)) + "/" + implicitly[PathBindable[String]].unbind("longCol", dynamicString(longCol)))
}
                        

// @LINE:8
def wordcount(table:String, min:Int = 1, length:Int = Int.MaxValue, reload:Int = -1): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "demo/wordcount/" + implicitly[PathBindable[String]].unbind("table", dynamicString(table)) + queryString(List(if(min == 1) None else Some(implicitly[QueryStringBindable[Int]].unbind("min", min)), if(length == Int.MaxValue) None else Some(implicitly[QueryStringBindable[Int]].unbind("length", length)), if(reload == -1) None else Some(implicitly[QueryStringBindable[Int]].unbind("reload", reload)))))
}
                        

}
                          
}
                  


// @LINE:14
// @LINE:13
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:14
// @LINE:13
class ReverseAssets {


// @LINE:13
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

// @LINE:14
def versioned : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.versioned",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "vassets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              
}
        

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package com.htc.studio.demo.javascript {
import ReverseRouteContext.empty

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:9
def h2Sentiment : JavascriptReverseRoute = JavascriptReverseRoute(
   "com.htc.studio.demo.Application.h2Sentiment",
   """
      function(table,id,tweet,senti) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "demo/h2st/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("table", encodeURIComponent(table)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", encodeURIComponent(id)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("tweet", encodeURIComponent(tweet)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("senti", encodeURIComponent(senti))})
      }
   """
)
                        

// @LINE:10
def sentiment : JavascriptReverseRoute = JavascriptReverseRoute(
   "com.htc.studio.demo.Application.sentiment",
   """
      function(table) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "demo/sentiment/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("table", encodeURIComponent(table))})
      }
   """
)
                        

// @LINE:7
def mapcount : JavascriptReverseRoute = JavascriptReverseRoute(
   "com.htc.studio.demo.Application.mapcount",
   """
      function(table,reload) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "demo/mapcount/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("table", encodeURIComponent(table)) + _qS([(reload == null ? null : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("reload", reload))])})
      }
   """
)
                        

// @LINE:6
def h2 : JavascriptReverseRoute = JavascriptReverseRoute(
   "com.htc.studio.demo.Application.h2",
   """
      function(table,strCol,longCol) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "demo/h2/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("table", encodeURIComponent(table)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("strCol", encodeURIComponent(strCol)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("longCol", encodeURIComponent(longCol))})
      }
   """
)
                        

// @LINE:8
def wordcount : JavascriptReverseRoute = JavascriptReverseRoute(
   "com.htc.studio.demo.Application.wordcount",
   """
      function(table,min,length,reload) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "demo/wordcount/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("table", encodeURIComponent(table)) + _qS([(min == null ? null : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("min", min)), (length == null ? null : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("length", length)), (reload == null ? null : (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("reload", reload))])})
      }
   """
)
                        

}
              
}
        


// @LINE:14
// @LINE:13
package controllers.ref {


// @LINE:14
// @LINE:13
class ReverseAssets {


// @LINE:13
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Static assets""", _prefix + """assets/$file<.+>""")
)
                      

// @LINE:14
def versioned(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.versioned(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]), "GET", """""", _prefix + """vassets/$file<.+>""")
)
                      

}
                          
}
        

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package com.htc.studio.demo.ref {


// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:9
def h2Sentiment(table:String, id:String, tweet:String, senti:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   com.htc.studio.demo.Application.h2Sentiment(table, id, tweet, senti), HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "h2Sentiment", Seq(classOf[String], classOf[String], classOf[String], classOf[String]), "GET", """""", _prefix + """demo/h2st/$table<[^/]+>/$id<[^/]+>/$tweet<[^/]+>/$senti<[^/]+>""")
)
                      

// @LINE:10
def sentiment(table:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   com.htc.studio.demo.Application.sentiment(table), HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "sentiment", Seq(classOf[String]), "GET", """""", _prefix + """demo/sentiment/$table<[^/]+>""")
)
                      

// @LINE:7
def mapcount(table:String, reload:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   com.htc.studio.demo.Application.mapcount(table, reload), HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "mapcount", Seq(classOf[String], classOf[Int]), "GET", """""", _prefix + """demo/mapcount/$table<[^/]+>""")
)
                      

// @LINE:6
def h2(table:String, strCol:String, longCol:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   com.htc.studio.demo.Application.h2(table, strCol, longCol), HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "h2", Seq(classOf[String], classOf[String], classOf[String]), "GET", """ Demo""", _prefix + """demo/h2/$table<[^/]+>/$strCol<[^/]+>/$longCol<[^/]+>""")
)
                      

// @LINE:8
def wordcount(table:String, min:Int, length:Int, reload:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   com.htc.studio.demo.Application.wordcount(table, min, length, reload), HandlerDef(this.getClass.getClassLoader, "", "com.htc.studio.demo.Application", "wordcount", Seq(classOf[String], classOf[Int], classOf[Int], classOf[Int]), "GET", """""", _prefix + """demo/wordcount/$table<[^/]+>""")
)
                      

}
                          
}
        
    