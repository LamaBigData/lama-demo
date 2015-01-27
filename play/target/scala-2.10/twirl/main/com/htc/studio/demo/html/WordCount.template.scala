
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
object WordCount extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template4[String,Int,Int,Int,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(table: String, minimum: Int, length: Int, reload: Int):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.57*/("""
"""),format.raw/*2.1*/("""<!DOCTYPE html>
<meta charset="utf-8">
<script type="text/javascript" src='"""),_display_(/*4.38*/routes/*4.44*/.Assets.at("lib/jquery/jquery.min.js")),format.raw/*4.82*/("""'></script>
<script type="text/javascript" src='"""),_display_(/*5.38*/routes/*5.44*/.Assets.at("lib/d3js/d3.min.js")),format.raw/*5.76*/("""'></script>
<script type="text/javascript" src='"""),_display_(/*6.38*/routes/*6.44*/.Assets.at("lib/d3-cloud/d3.layout.cloud.js")),format.raw/*6.89*/("""'></script>
<!--<script src="https://rawgit.com/Caged/d3-tip/master/index.js"></script>-->
<script type='text/javascript' src='"""),_display_(/*8.38*/routes/*8.44*/.Assets.at("javascripts/d3-tip.js")),format.raw/*8.79*/("""'></script>
<script type="text/javascript" src='"""),_display_(/*9.38*/routes/*9.44*/.Assets.at("lib/coffee-script/coffee-script.min.js")),format.raw/*9.96*/("""'></script>
<script type='text/javascript' src='"""),_display_(/*10.38*/routes/*10.44*/.Assets.at("coffeescripts/count-bar.js")),format.raw/*10.84*/("""'></script>
<script type='text/javascript' src='"""),_display_(/*11.38*/routes/*11.44*/.Assets.at("coffeescripts/word-cloud.js")),format.raw/*11.85*/("""'></script>
<!--<link rel="stylesheet" href="https://rawgit.com/Caged/d3-tip/master/examples/example-styles.css">-->
<link rel="stylesheet" href='"""),_display_(/*13.31*/routes/*13.37*/.Assets.at("css/d3-tip.css")),format.raw/*13.65*/("""'>
<style type="text/css">
.cloud text:hover """),format.raw/*15.19*/("""{"""),format.raw/*15.20*/(""" """),format.raw/*15.21*/("""opacity: .5; """),format.raw/*15.34*/("""}"""),format.raw/*15.35*/("""
"""),format.raw/*16.1*/(""".count """),format.raw/*16.8*/("""{"""),format.raw/*16.9*/(""" """),format.raw/*16.10*/("""fill: black; """),format.raw/*16.23*/("""}"""),format.raw/*16.24*/("""
"""),format.raw/*17.1*/(""".increased """),format.raw/*17.12*/("""{"""),format.raw/*17.13*/(""" """),format.raw/*17.14*/("""fill: lime; """),format.raw/*17.26*/("""}"""),format.raw/*17.27*/("""
"""),format.raw/*18.1*/(""".remain """),format.raw/*18.9*/("""{"""),format.raw/*18.10*/(""" """),format.raw/*18.11*/("""fill: lightskyblue; """),format.raw/*18.31*/("""}"""),format.raw/*18.32*/("""
"""),format.raw/*19.1*/("""html, body """),format.raw/*19.12*/("""{"""),format.raw/*19.13*/(""" """),format.raw/*19.14*/("""margin:0; padding:0; height:100%; """),format.raw/*19.48*/("""}"""),format.raw/*19.49*/("""
"""),format.raw/*20.1*/("""</style>
<title>Word Count Graph
"""),_display_(/*22.2*/if(minimum == 1)/*22.18*/ {_display_(Seq[Any](format.raw/*22.20*/("""
  """),format.raw/*23.3*/("""of All Words
""")))}/*24.3*/else/*24.8*/{_display_(Seq[Any](format.raw/*24.9*/("""
  """),format.raw/*25.3*/("""(showing only words occurred more than """),_display_(/*25.43*/minimum),format.raw/*25.50*/(""" """),format.raw/*25.51*/("""times)
""")))}),format.raw/*26.2*/("""
"""),format.raw/*27.1*/("""</title>
<div id="error"></div>
<div style="float:left"><svg class="cloud"></svg></div>
<div style="float:left"><svg class="bars"></svg></div>
<script type='text/coffeescript'>
width = window.innerWidth - 20
height = window.innerHeight - 20
bar = CountBar("bars", width * 0.2, height)
cloud = WordCloud("cloud", width * 0.8, height)
data = '"""),_display_(/*36.10*/com/*36.13*/.htc.studio.demo.routes.Application.h2(table, "word", "count")),format.raw/*36.75*/("""'
allwords = []
reload = () ->
  setTimeout(d3.json, """),_display_(/*39.24*/reload),format.raw/*39.30*/(""" """),format.raw/*39.31*/("""* 1000, data, callback) if """),_display_(/*39.59*/reload),format.raw/*39.65*/(""" """),format.raw/*39.66*/(""">= 0
callback = (error, json) ->
  if json?
    allwords = json
    filtered = json.filter((d) -> d.count >= """),_display_(/*43.47*/minimum),format.raw/*43.54*/(""" """),format.raw/*43.55*/("""and d.word.length <= """),_display_(/*43.77*/length),format.raw/*43.83*/(""")
    cloud(filtered)
    d3.select("#error").html("")
  else
    d3.select("#error").html(error.response)
    console.log(error)
    reload()
map = d3.map()
map.set(String.fromCharCode('A'.charCodeAt(0) + i - 1),
  count: 0
  oldcount: 0
) for i in [1..26]
cloud.OnFinished(() ->
  allwords.forEach((w) -> map.get(w.word.charAt(0).toUpperCase()).count += w.count)
  bar(map.entries())
  reload()
)
d3.json(data, callback)
</script>
"""))}
  }

  def render(table:String,minimum:Int,length:Int,reload:Int): play.twirl.api.HtmlFormat.Appendable = apply(table,minimum,length,reload)

  def f:((String,Int,Int,Int) => play.twirl.api.HtmlFormat.Appendable) = (table,minimum,length,reload) => apply(table,minimum,length,reload)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Sun Jan 25 20:15:10 CST 2015
                  SOURCE: /Users/simong_wu/Workspace/lama-demo-spain/play/app/com/htc/studio/demo/WordCount.scala.html
                  HASH: 17e4dd44f401f1643e4db567c1aea5a76783d2ca
                  MATRIX: 535->1|678->56|705->57|807->133|821->139|879->177|954->226|968->232|1020->264|1095->313|1109->319|1174->364|1328->492|1342->498|1397->533|1472->582|1486->588|1558->640|1634->689|1649->695|1710->735|1786->784|1801->790|1863->831|2037->978|2052->984|2101->1012|2174->1057|2203->1058|2232->1059|2273->1072|2302->1073|2330->1074|2364->1081|2392->1082|2421->1083|2462->1096|2491->1097|2519->1098|2558->1109|2587->1110|2616->1111|2656->1123|2685->1124|2713->1125|2748->1133|2777->1134|2806->1135|2854->1155|2883->1156|2911->1157|2950->1168|2979->1169|3008->1170|3070->1204|3099->1205|3127->1206|3187->1240|3212->1256|3252->1258|3282->1261|3314->1276|3326->1281|3364->1282|3394->1285|3461->1325|3489->1332|3518->1333|3556->1341|3584->1342|3953->1684|3965->1687|4048->1749|4129->1803|4156->1809|4185->1810|4240->1838|4267->1844|4296->1845|4433->1955|4461->1962|4490->1963|4539->1985|4566->1991
                  LINES: 19->1|22->1|23->2|25->4|25->4|25->4|26->5|26->5|26->5|27->6|27->6|27->6|29->8|29->8|29->8|30->9|30->9|30->9|31->10|31->10|31->10|32->11|32->11|32->11|34->13|34->13|34->13|36->15|36->15|36->15|36->15|36->15|37->16|37->16|37->16|37->16|37->16|37->16|38->17|38->17|38->17|38->17|38->17|38->17|39->18|39->18|39->18|39->18|39->18|39->18|40->19|40->19|40->19|40->19|40->19|40->19|41->20|43->22|43->22|43->22|44->23|45->24|45->24|45->24|46->25|46->25|46->25|46->25|47->26|48->27|57->36|57->36|57->36|60->39|60->39|60->39|60->39|60->39|60->39|64->43|64->43|64->43|64->43|64->43
                  -- GENERATED --
              */
          