data = []
$.ajax({
  url: '/demo/h2st/sentiment/id/tweet/sentiment',
  async: false,
  dataType: 'json',
  success: (json) ->
    data = json.reverse().map((d) ->
      { ID: d.id, TWEET: d.tweet, SENTIMENT: d.sentiment}).slice(0,30) })

mode = TimeLineGraph(data)

onSelect = () ->
  mode.interpolate($("#selInterpolate :selected").text())
  mode.ease($("#selEaseType :selected").text() + "-" + $("#selEaseMode :selected").text())
  mode.interval($("#rInterval").val())

onSelect()

(exports ? this).onSelect = onSelect
(exports ? this).graph = mode
