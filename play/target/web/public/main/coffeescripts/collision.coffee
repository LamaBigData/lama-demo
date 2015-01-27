Network = (minimum = 1, svgClass = "chart") ->
  width = 1200
  height = 800
  tooltip = d3.tip()
    .attr("class", "d3-tip")
    .html((d) -> "#{d.name}(#{d.count})")
  force = d3.layout.force()
    .size([width, height])
    .gravity(0.01)
    .charge(0)
    .chargeDistance(0)
  drag = force.drag()
    .on("dragstart", (d) -> d3.select(this).classed("fixed", d.fixed = true))
  chart = d3.select(".#{svgClass}")
    .attr("width", width)
    .attr("height", height)
    .call(tooltip)
  circles = chart.selectAll("circle")
  texts = chart.selectAll("text")
  colors = d3.scale.category20()

  network = (error, words) ->
    # Only use words with at least `minimum` counts
    nodes = words.filter((d) -> d.count >= minimum)
    # Linear scale function for counts of words
    radius = d3.scale.linear()
      .domain(d3.extent(nodes, (d) -> d.count))
      .range([30, 60])
    circles = circles.data(nodes)
    circles.enter().append("circle")
      .attr("class", "node")
      .attr("id", (d) -> d.name)
      .attr("r", (d) -> radius(d.count))
      .attr("cx", (d) -> d.x)
      .attr("cy", (d) -> d.y)
      .style("fill", (d, i) -> colors(i % 20))
      .on("mouseover", (d) -> tooltip.show(d))
      .on("mouseout", (d) -> tooltip.hide(d))
      .on("dblclick", (d) -> d3.select(this).classed("fixed", d.fixed = false))
      .call(drag)
    texts = texts.data(nodes)
    texts.enter().append("text")
      .text((d) -> d.name)
      .attr("x", (d) -> d.x)
      .attr("y", (d) -> d.y)
      .attr("font-family", "sans-serif")
      .attr("font-size", "10px")
      .attr("text-anchor", "middle")
      .attr("fill", "black")

    force.nodes(nodes)
      .on("tick", tick(nodes, radius))
      .start()

  # This will be called every tick
  tick = (nodes, radius) ->
    (e) ->
      # A space index tree, used to traverse all nodes effectively
      q = d3.geom.quadtree(nodes)
      # Detect collision for each node
      nodes.forEach((n) -> q.visit(collide(n, radius)))

      # Update positions
      circles
        .attr("cx", (d) -> d.x)
        .attr("cy", (d) -> d.y)
      texts
        .attr("x", (d) -> d.x)
        .attr("y", (d) -> d.y)

  # This function will do following staffs:
  # 1. Detect collision and move overlapped nodes
  # 2. Calculate the region of node to see whether to traverse subnodes in the quad tree
  collide = (node, radius) ->
    # Region of the node, fixed nodes should repulse other nodes
    region = if node.fixed then 100 else 1
    r = radius(node.count) + region
    # Top-left and bottom-right cornors of the region
    nx1 = node.x - r
    nx2 = node.x + r
    ny1 = node.y - r
    ny2 = node.y + r
    # Return a function for quad tree to traverse
    # quad.point may point to a node
    # x1, y1, x2, y2 are cornor of covering region of entire sub tree
    (quad, x1, y1, x2, y2) ->
      # quad.point may be null or node, we just ignore them
      if quad.point and (quad.point isnt node)
        x = node.x - quad.point.x
        y = node.y - quad.point.y
        # l is the real distance of two nodes
        l = Math.sqrt(x * x + y * y)
        # r is the distance we want
        r = radius(node.count) + radius(quad.point.count) + region
        if (l < r)
          l = (l - r) / l * .4
          x *= l
          y *= l
          # Move two nodes if they are not fixed
          if not node.fixed
            node.x -= x
            node.y -= y
          if not quad.point.fixed
            quad.point.x += x
            quad.point.y += y
      # If the region of entire sub tree is not within in the region of node,
      # we can skip the entire sub tree
      x1 > nx2 or x2 < nx1 or y1 > ny2 or y2 < ny1

  return network

# Export the function so it can be used out side
(exports ? this).CollisionNetwork = Network
