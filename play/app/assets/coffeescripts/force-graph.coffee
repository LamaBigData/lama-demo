Network = (svgClass = "chart") ->
  width = 1600
  height = 800
  tooltip = d3.tip()
    .attr("class", "d3-tip")
    .html((d) -> "#{d.name}(#{d.count})")
  force = d3.layout.force()
    .size([width, height])
  drag = force.drag()
    .on("dragstart", (d) -> d3.select(this).classed("fixed", d.fixed = true))
  chart = d3.select(".#{svgClass}")
    .attr("width", width)
    .attr("height", height)
    .call(tooltip)
  circles = chart.selectAll("circle")
  lines = chart.selectAll("line")
  colorx = d3.scale.linear().range([0, 255]).domain([0, width])
  colory = d3.scale.linear().range([0, 255]).domain([0, height])

  network = (error, data) ->
    allnodes = d3.merge(data.map((d, i, a) -> [d.node1, d.node2]))
    nodelist = d3.set(allnodes).values().sort()
    nodes = nodelist.map((d, i, a) ->
      {index: i, name: d,
      x: Math.floor(Math.random() * width),
      y: Math.floor(Math.random() * height),
      count: allnodes.filter((e) -> e == d).length})
    radius = d3.scale.linear()
      .domain(d3.extent(nodes, (d) -> d.count))
      .range([5, 10])

    links = data.map((d, i, a) ->
      {id: i, source: nodes[nodelist.indexOf(d.node1)],
      target: nodes[nodelist.indexOf(d.node2)], weight: d.weight})
    lines = lines.data(links)
    lines.enter().append("line")
      .attr("stroke", "#aaa")
      .attr("stroke-opacity", 0.8)
      .attr("stroke-width", ".5px")
      .attr("id", (d) -> d.id)
      .attr("x1", (d) -> d.source.x)
      .attr("y1", (d) -> d.source.y)
      .attr("x2", (d) -> d.target.x)
      .attr("y2", (d) -> d.target.y)
    lines.exit().remove()
    circles = circles.data(nodes)
    circles.enter().append("circle")
      .attr("class", "node")
      .attr("id", (d) -> d.name)
      .attr("r", (d) -> radius(d.count))
      .attr("cx", (d) -> d.x)
      .attr("cy", (d) -> d.y)
      .style("fill", (d, i) -> d3.rgb(colorx(d.x), colory(d.y), 128))
      .on("mouseover", (d) -> tooltip.show(d))
      .on("mouseout", (d) -> tooltip.hide(d))
      .on("dblclick", (d) -> d3.select(this).classed("fixed", d.fixed = false))
      .call(drag)
    circles.exit().remove()
    force.nodes(nodes)
      .links(links)
      .on("tick", tick)
      .charge(-100)
      .linkDistance((d) -> 52 - d.weight * 50)
      .start()

  tick = (e) ->
    circles
      .attr("cx", (d) -> d.x)
      .attr("cy", (d) -> d.y)
      .style("fill", (d, i) -> d3.rgb(colorx(d.x), colory(d.y), 128))
    lines
      .attr("x1", (d) -> d.source.x)
      .attr("y1", (d) -> d.source.y)
      .attr("x2", (d) -> d.target.x)
      .attr("y2", (d) -> d.target.y)

  return network

(exports ? this).ForceNetwork = Network
