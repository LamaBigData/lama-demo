Chart = (data, svgClass = "seriesGraph", radius = 7) ->
  margin = {top: 10, right: 20, bottom: 50, left: 20}
  width = 960 - margin.right
  height = 300 - margin.top - margin.bottom
  tooltip = d3.tip()
    .attr("class", "d3-tip")
    .html((d) -> "#{d}")
  now = new Date()

  x = d3.scale.linear()
    .domain([0, data.length])
    .range([0, width])
  time = d3.time.scale()
    .domain([now - (data.length - 2) * duration, now - duration])
    .range([0, width]);
  y = d3.scale.linear()
    .domain([0,4])
    .range([height, 0])

  line = d3.svg.line()
    .interpolate("linear")
    .x((d, i) -> x(i))
    .y((d, i) -> y(d))

  chart = d3.select(".#{svgClass}")
    .attr("width", 672)
    .attr("height", 268)
    .append("g")
    .attr("transform", "translate(#{margin.left},#{margin.top})")
    .call(tooltip)

  chart.append("defs").append("clipPath")
    .attr("id", "clip")
    .append("rect")
    .attr("width", width)
    .attr("height", height)

  graph = chart.append("g")
  path = graph.append("g")
    .attr("clip-path", "url(#clip)")
    .append("path")
    .data(data)
    .attr("class", "line " + "lineChart--areaLine")
    .attr("d", line(data.map((d) -> d.SENTIMENT)))
  circles = graph.append("g")
    .selectAll("circle")
    .data(data)
  color = ["white","red","green","blue"]
  circles.enter().append("circle")
    .attr("class", (d) -> "node "  + 
      "pieChart__" + color[d.SENTIMENT] + 
      " lineChart--circle")
    .attr("id", (d, i) -> "node#{i}")
    .attr("r", radius)
    .attr("cx", (d, i) -> x(i))
    .attr("cy", (d,i) -> y(d.SENTIMENT))
    .on("mouseover", (d) -> tooltip.show(d.TWEET))
    .on("mouseout", (d) -> tooltip.hide(d.TWEET))

  chart.append("rect")
    .attr("transform", "translate(-#{margin.left},-#{margin.top})")
    .attr("width", "#{margin.left}px")
    .attr("height", height + margin.top + margin.bottom)
    .attr("fill", "white")

  xAxis = graph.append("g")
    .attr("class", "axis")
    .attr("width", width)
    .attr("transform", "translate(0,#{height})")
  yAxis = chart.append("g")
    .attr("class", "axis")
    .attr("height", height)

  easemode = "linear"
  duration = 1000

  interpolate = (interpolation) -> line.interpolate(interpolation)
  ease = (e) -> easemode = e
  interval = (i) -> duration = i

  source = []
  updateData = (json) ->
    last = data[data.length - 1]
    newdata = json.filter((d) -> d.ID > last.ID)
    source = source.concat(newdata)

  pushData = () ->
    if source.length < 1
      data[data.length - 1]
    else
      source.pop()

  tick = () ->
    data.push(pushData())
    data.shift()
    now = new Date()
    time.domain([now - (data.length - 1) * duration, now ])
    y.domain([0,4])
    xAxis.call(d3.svg.axis().scale(time).orient("bottom"))
    yAxis.call(d3.svg.axis().scale(y).ticks(5).orient("left"))
    path.attr("d", line(data.map((d) -> d.SENTIMENT)))
    circles.data(data)
      .attr("cx", (d, i) -> x(i))
      .attr("cy", (d) -> y(d.SENTIMENT))
      .attr("class", (d) -> "node " +
        "pieChart__" + color[d.SENTIMENT] +
        " lineChart--circle")
    graph.attr("transform", null)
      .transition()
      .duration(duration)
      .ease(easemode)
      .attr("transform", "translate(" + x(-1) + ")")
      .each("end", tick)

  tick()
  return { 
    ease: ease, 
    interpolate: interpolate,
    interval: interval,
    updateData: updateData
  }

(exports ? this).TimeLineGraph = Chart
