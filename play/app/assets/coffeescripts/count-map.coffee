Network = (svgClass = "chart", width, height) ->
  width ?= window.innerWidth - 30
  height ?= window.innerHeight - 30

  tooltip = d3.tip()
    .attr("class", "d3-tip")
    .html((d) -> if d.country? then "#{d.country}(#{d.total})" else "#{d.properties.name}")
    .offset(() ->
      box = this.getBBox()
      mouse = d3.mouse(this).map((d) -> parseInt(d))
      scale = zoom.scale()
      [(mouse[1] - box.y) * scale, (mouse[0] - box.x - box.width / 2) * scale]
    )

  color = d3.scale.linear()
    .range(["#FFFF33", "#FF0033"])
  graticule = d3.geo.graticule()
  projection = d3.geo.mercator()
    .translate([(width / 2), (height / 2)])
    .scale(d3.min([height, width]) / 2 / Math.PI)
  path = d3.geo.path().projection(projection)

  svg = d3.select(".#{svgClass}")
    .attr("width", width)
    .attr("height", height)
    .call(tooltip)
    .append("g")
  # back ground
  svg.append("rect")
    .attr("width", width)
    .attr("height", height)
    .attr("fill", "#FFF")

  zoomed = () ->
    svg.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")")

  zoom = d3.behavior.zoom()
    .scaleExtent([1, 3])
    .on("zoom", zoomed)
  zoom(svg)

  draw = (countries) ->
    svg.append("path")
      .datum(graticule)
      .attr("class", "graticule")
      .attr("d", path)

    svg.append("path")
     .datum({type: "LineString", coordinates: [[-180, 0], [-90, 0], [0, 0], [90, 0], [180, 0]]})
     .attr("class", "equator")
     .attr("d", path)

    country = svg.selectAll(".country")
      .data(countries, (d) -> d.properties.name)

    country.enter().insert("path")
      .attr("class", "country")
      .attr("d", path)
      .attr("id", (d) -> d.id)
      .style("fill", "#AAA")
      .on("mouseover", tooltip.show)
      .on("mouseout", tooltip.hide)

  draw.data = (counts) ->
    color.domain(d3.extent(counts, (d) -> d.total))
    selected = svg.selectAll(".country")
      .data(counts, (d) -> d.country ? d.properties.name)
      .style("fill", (d) -> color(d.total))
      .on("mouseover", tooltip.show)
      .on("mouseout", tooltip.hide)

  draw.color = color

  return draw

(exports ? this).WordCloud = Network
