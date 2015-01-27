Network = (svgClass = "chart", width, height) ->
  width ?= window.innerWidth - 20
  height ?= window.innerHeight - 20

  x = d3.scale.log().range([0, width])

  svg = d3.select(".#{svgClass}")
    .attr("width", width)

  network = (data, length) ->
    length ?= data.length
    barHeight = d3.max([height / length, 25])
    usedData = data.slice(0, length)
    x.domain([1, d3.max(usedData, (d) -> d.value.count) + 1])
    svg.attr("height", barHeight * length)
    bars = svg.selectAll("g")
      .data(usedData)
    enter = bars.enter().append("g")
    enter.append("rect")
    enter.append("text")
      .attr("x", 5)
      .attr("dy", ".35em")
      .attr("class", "count")
    bars.attr("transform", (d, i) -> "translate(0,#{i*barHeight})")
      .select("rect")
      .attr("height", barHeight - 1)
      .attr("width", (d) -> x(d.value.count + 1))
      .attr("class", (d) -> if d.value.count == d.value.oldcount then "remain" else "increased")
      .style("fill", (d) -> if d.value.count == d.value.oldcount then d.value.color else "")
      .transition()
      .delay(900)
      .attr("class", (d) -> "remain")
      .style("fill", (d) -> d.value.color)
    bars.select(".count")
      .attr("y", barHeight / 2)
      .text((d) ->
        base = "#{d.key}  #{d.value.count}"
        diff = d.value.count - d.value.oldcount
        if diff == 0
          base
        else if diff > 0
          "#{base} (+#{diff})"
        else
          "#{base} (#{diff})"
      )
    # reset counts
    data.forEach((d) ->
      d.value.oldcount = d.value.count
      d.value.count = 0
    )

  return network

(exports ? this).CountBar = Network
