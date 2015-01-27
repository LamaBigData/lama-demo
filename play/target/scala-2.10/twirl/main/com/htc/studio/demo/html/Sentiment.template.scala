
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

/* SentimentBar Template File */
object Sentiment extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /* SentimentBar Template File */
  def apply/*2.2*/(table: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*2.17*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="twitter:widgets:theme" content="light">
  <meta name="twitter:widgets:link-color" content="#55acee">
  <meta name="twitter:widgets:border-color" content="#55acee">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script type="text/javascript" src='"""),_display_(/*11.40*/routes/*11.46*/.Assets.at("lib/d3js/d3.min.js")),format.raw/*11.78*/("""'></script>
  <script type="text/javascript" src='"""),_display_(/*12.40*/routes/*12.46*/.Assets.at("lib/jquery/jquery.min.js")),format.raw/*12.84*/("""'></script>
  <script type='text/javascript' src='"""),_display_(/*13.40*/routes/*13.46*/.Assets.at("javascripts/d3-tip.js")),format.raw/*13.81*/("""'></script>
  <script type="text/javascript" src='"""),_display_(/*14.40*/routes/*14.46*/.Assets.at("lib/coffee-script/coffee-script.min.js")),format.raw/*14.98*/("""'></script>
  <script type="text/javascript" src='"""),_display_(/*15.40*/routes/*15.46*/.Assets.at("coffeescripts/sentiment-bar.js")),format.raw/*15.90*/("""'></script>
  <link rel="stylesheet" href='"""),_display_(/*16.33*/routes/*16.39*/.Assets.at("css/sentiment.css")),format.raw/*16.70*/("""'>
  <title>Sentiment Charts</title>
</head>

<body>
  <div class="charts--container">
    <ul>
      <li class="chart">
        <h2 class="chart--headline">Tweets Sentiment Pie</h2>
        <div id="pieChart">
          <svg>
            <defs>
              <filter id='pieChartInsetShadow'>
                <feOffset dx='0' dy='0'/>
                <feGaussianBlur stdDeviation='3' result='offset-blur' />
                <feComposite operator='out' in='SourceGraphic' in2='offset-blur' result='inverse' />
                <feFlood flood-color='black' flood-opacity='1' result='color' />
                <feComposite operator='in' in='color' in2='inverse' result='shadow' />
                <feComposite operator='over' in='shadow' in2='SourceGraphic' />
              </filter>
              <filter id="pieChartDropShadow">
                <feGaussianBlur in="SourceAlpha" stdDeviation="3" result="blur" />
                <feOffset in="blur" dx="0" dy="3" result="offsetBlur" />
                <feMerge>
                  <feMergeNode />
                  <feMergeNode in="SourceGraphic" />
                </feMerge>
              </filter>
            </defs>
            <g id="pieChartSVG"></g>
          </svg>
        </div>
      </li>
      <li class="chart">
        <h2 class="chart--headline">Tweets Bar Chart</h2>
        <svg class="seriesGraph"></svg>
      </li>
      <li class="chart">
        <!--  <h2 class="chart--headline">Recent Tweets List</h2> -->
        <div id="lineChart">
          <div class="wrapper" id="wrapper0"><h3>Positive Tweets</h3></div>
          <div class="wrapper" id="wrapper1"><h3>Negative Tweets</h3></div>
        </div>
      </li>
    </ul>
  </div>
  <script type='text/coffeescript'>
     twttr.events.bind('loaded', (event) ->
       d3.selectAll(".rendered-twitter-div").remove()
       d3.selectAll(".rendering-twitter-div")
         .style("visible", "true")
         .attr("class", "rendered-twitter-div")
       setTimeout(d3.json, 5000, dataUrl, callback)
     )

     dataUrl = '"""),_display_(/*71.18*/com/*71.21*/.htc.studio.demo.routes.Application.h2Sentiment(table, "ID", "TWEET", "SENTIMENT")),format.raw/*71.103*/("""'
     callback = (error, json) -> SentimentPie(json)
     d3.json(dataUrl, callback)
  </script>
  <script type='text/javascript' src='"""),_display_(/*75.40*/routes/*75.46*/.Assets.at("javascripts/widgets.js")),format.raw/*75.82*/("""'></script>
</body>
</html>
"""))}
  }

  def render(table:String): play.twirl.api.HtmlFormat.Appendable = apply(table)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (table) => apply(table)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Sun Jan 25 20:15:10 CST 2015
                  SOURCE: /Users/simong_wu/Workspace/lama-demo-spain/play/app/com/htc/studio/demo/Sentiment.scala.html
                  HASH: 22b6661d85e0d4657e08c47024682af061581440
                  MATRIX: 579->34|682->49|709->50|1081->395|1096->401|1149->433|1227->484|1242->490|1301->528|1379->579|1394->585|1450->620|1528->671|1543->677|1616->729|1694->780|1709->786|1774->830|1845->874|1860->880|1912->911|3986->2958|3998->2961|4102->3043|4266->3180|4281->3186|4338->3222
                  LINES: 19->2|22->2|23->3|31->11|31->11|31->11|32->12|32->12|32->12|33->13|33->13|33->13|34->14|34->14|34->14|35->15|35->15|35->15|36->16|36->16|36->16|91->71|91->71|91->71|95->75|95->75|95->75
                  -- GENERATED --
              */
          