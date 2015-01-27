Network = (svgClass = "chart", width, height) ->
  width ?= window.innerWidth - 20
  height ?= window.innerHeight - 20

  fill = d3.scale.category20()
  fontSize = d3.scale.sqrt().range([15, 100])
  tooltip = d3.tip()
    .attr("class", "d3-tip")
    .html((d) -> "#{d.word}(#{d.count})")

  svg = d3.select(".#{svgClass}")
    .attr("width", width)
    .attr("height", height)
    .call(tooltip)
    .append("g")
    .attr("transform", "translate(#{width/2},#{height/2})")

  layout = d3.layout.cloud()
    .size([width, height])
    .timeInterval(10)
    .text((d) -> d.word)
    .font("Comic Sans MS, Ubuntu")
    .fontWeight("900")
    .rotate((d) -> Math.random() * 180 - 90)
    .padding(1)

  network = (words) ->
    fontSize.domain(d3.extent(words, (d) -> d.count))
    layout.words(words)
      .fontSize((d) -> fontSize(d.count))
      .on("end", draw)
      .start()

  # An empty callback for initial state
  next = (words) ->

  network.OnFinished = (callback) ->
    next = callback

  draw = (words) ->
    text = svg.selectAll("text").data(words, (d) -> d.text)
    text.exit().remove()
    text.enter().append("text")
      .style("font-family", (d) -> d.font)
      .style("font-weight", (d) -> d.weight)
      .attr("text-anchor", "middle")
      .on("mouseover", tooltip.show)
      .on("mouseout", tooltip.hide)
    text.transition().duration(1000)
      .style("font-size", (d) -> "#{d.size}px")
      .style("fill", (d, i) -> fill(i))
      .text((d) -> d.text)
      .attr("transform", (d) -> "translate(#{d.x},#{d.y})rotate(#{d.rotate})")
    next(words)

  return network

(exports ? this).WordCloud = Network
