
package com.htc.studio.demo.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._

import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._

/**/
object MapCount extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,Int,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(table: String, reload: Int):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.30*/("""
"""),format.raw/*2.1*/("""<!DOCTYPE html>
<meta charset="utf-8">
<script type="text/javascript" src='"""),_display_(/*4.38*/routes/*4.44*/.Assets.at("lib/jquery/jquery.min.js")),format.raw/*4.82*/("""'></script>
<script type="text/javascript" src='"""),_display_(/*5.38*/routes/*5.44*/.Assets.at("lib/d3js/d3.min.js")),format.raw/*5.76*/("""'></script>
<!--<script src="https://rawgit.com/Caged/d3-tip/master/index.js"></script>-->
<script type='text/javascript' src='"""),_display_(/*7.38*/routes/*7.44*/.Assets.at("javascripts/d3-tip.js")),format.raw/*7.79*/("""'></script>
<script type='text/javascript' src='"""),_display_(/*8.38*/routes/*8.44*/.Assets.at("javascripts/topojson.v1.min.js")),format.raw/*8.88*/("""'></script>
<script type="text/javascript" src='"""),_display_(/*9.38*/routes/*9.44*/.Assets.at("lib/coffee-script/coffee-script.min.js")),format.raw/*9.96*/("""'></script>
<script type='text/javascript' src='"""),_display_(/*10.38*/routes/*10.44*/.Assets.at("coffeescripts/count-bar.js")),format.raw/*10.84*/("""'></script>
<script type='text/javascript' src='"""),_display_(/*11.38*/routes/*11.44*/.Assets.at("coffeescripts/count-map.js")),format.raw/*11.84*/("""'></script>
<!--<link rel="stylesheet" href="https://rawgit.com/Caged/d3-tip/master/examples/example-styles.css">-->
<link rel="stylesheet" href='"""),_display_(/*13.31*/routes/*13.37*/.Assets.at("css/d3-tip.css")),format.raw/*13.65*/("""'>
<style>
.count """),format.raw/*15.8*/("""{"""),format.raw/*15.9*/(""" """),format.raw/*15.10*/("""fill: black; """),format.raw/*15.23*/("""}"""),format.raw/*15.24*/("""
"""),format.raw/*16.1*/(""".country:hover """),format.raw/*16.16*/("""{"""),format.raw/*16.17*/(""" """),format.raw/*16.18*/("""stroke: #fff; stroke-width: 1.5px; """),format.raw/*16.53*/("""}"""),format.raw/*16.54*/("""
"""),format.raw/*17.1*/(""".equator """),format.raw/*17.10*/("""{"""),format.raw/*17.11*/(""" """),format.raw/*17.12*/("""stroke: #ccc; stroke-width: 1px; """),format.raw/*17.45*/("""}"""),format.raw/*17.46*/("""
"""),format.raw/*18.1*/(""".graticule """),format.raw/*18.12*/("""{"""),format.raw/*18.13*/(""" """),format.raw/*18.14*/("""fill: none; stroke: #bbb; stroke-width: .5px; stroke-opacity: .5; """),format.raw/*18.80*/("""}"""),format.raw/*18.81*/("""
"""),format.raw/*19.1*/(""".increased """),format.raw/*19.12*/("""{"""),format.raw/*19.13*/(""" """),format.raw/*19.14*/("""fill: lime; """),format.raw/*19.26*/("""}"""),format.raw/*19.27*/("""
"""),format.raw/*20.1*/("""</style>
<title>Map Count</title>
<div style="float:left"><svg class="map"></svg></div>
<div id="bar-div" style="float:right;overflow-y:scroll"><svg class="bars"></svg></div>
<script type='text/coffeescript'>
width = window.innerWidth - 20
height = window.innerHeight - 20
bar = CountBar("bars", width - height - 30, height)
d3.select("#bar-div").style("height", "#"""),format.raw/*28.41*/("""{"""),format.raw/*28.42*/("""height"""),format.raw/*28.48*/("""}"""),format.raw/*28.49*/("""px")
worldMap = WordCloud("map", height, height)
country = '"""),_display_(/*30.13*/routes/*30.19*/.Assets.at("json/world-topo-min.json")),format.raw/*30.57*/("""'
data = '"""),_display_(/*31.10*/com/*31.13*/.htc.studio.demo.routes.Application.h2(table, "country", "total")),format.raw/*31.78*/("""'
map = d3.map()
color = worldMap.color
callback_data = (error, count) ->
  if count?
    worldMap.data(count)
    count.forEach((d) ->
      item = map.get(d.country)
      item.count = d.total
      item.color = color(d.total)
    )
    value = map.entries()
      .sort((a, b) -> b.value.count - a.value.count)
    countFn = (p, c) -> p + if c.value.count > 0 then 1 else 0
    size = value.reduce(countFn , 0)
    bar(value, size)
  setTimeout(d3.json, """),_display_(/*47.24*/reload),format.raw/*47.30*/(""" """),format.raw/*47.31*/("""* 1000, data, callback_data) if """),_display_(/*47.64*/reload),format.raw/*47.70*/(""" """),format.raw/*47.71*/(""">= 0
callback_country = (error, world) ->
  countries = topojson.feature(world, world.objects.countries).features
  worldMap(countries)
  countries.forEach((d) ->
    map.set(d.properties.name,
      count: 0
      oldcount: 0
    )
  )
  d3.json(data, callback_data)
d3.json(country, callback_country)
</script>
"""))}
  }

  def render(table:String,reload:Int): play.twirl.api.HtmlFormat.Appendable = apply(table,reload)

  def f:((String,Int) => play.twirl.api.HtmlFormat.Appendable) = (table,reload) => apply(table,reload)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Sun Jan 25 20:15:10 CST 2015
                  SOURCE: /Users/simong_wu/Workspace/lama-demo-spain/play/app/com/htc/studio/demo/MapCount.scala.html
                  HASH: 7f4063b6d6cd7504d89f39d8994ee8c8d69d2908
                  MATRIX: 526->1|642->29|669->30|771->106|785->112|843->150|918->199|932->205|984->237|1138->365|1152->371|1207->406|1282->455|1296->461|1360->505|1435->554|1449->560|1521->612|1597->661|1612->667|1673->707|1749->756|1764->762|1825->802|1999->949|2014->955|2063->983|2108->1001|2136->1002|2165->1003|2206->1016|2235->1017|2263->1018|2306->1033|2335->1034|2364->1035|2427->1070|2456->1071|2484->1072|2521->1081|2550->1082|2579->1083|2640->1116|2669->1117|2697->1118|2736->1129|2765->1130|2794->1131|2888->1197|2917->1198|2945->1199|2984->1210|3013->1211|3042->1212|3082->1224|3111->1225|3139->1226|3532->1591|3561->1592|3595->1598|3624->1599|3712->1660|3727->1666|3786->1704|3824->1715|3836->1718|3922->1783|4407->2241|4434->2247|4463->2248|4523->2281|4550->2287|4579->2288
                  LINES: 19->1|22->1|23->2|25->4|25->4|25->4|26->5|26->5|26->5|28->7|28->7|28->7|29->8|29->8|29->8|30->9|30->9|30->9|31->10|31->10|31->10|32->11|32->11|32->11|34->13|34->13|34->13|36->15|36->15|36->15|36->15|36->15|37->16|37->16|37->16|37->16|37->16|37->16|38->17|38->17|38->17|38->17|38->17|38->17|39->18|39->18|39->18|39->18|39->18|39->18|40->19|40->19|40->19|40->19|40->19|40->19|41->20|49->28|49->28|49->28|49->28|51->30|51->30|51->30|52->31|52->31|52->31|68->47|68->47|68->47|68->47|68->47|68->47
                  -- GENERATED --
              */
          