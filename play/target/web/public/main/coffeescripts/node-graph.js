(function() {
  var Network;

  Network = function(svgClass) {
    var chart, circles, colorx, colory, drag, force, height, lines, network, tick, tooltip, width;
    if (svgClass == null) {
      svgClass = "chart";
    }
    width = window.innerWidth - 20;
    height = window.innerHeight - 20;
    tooltip = d3.tip().attr("class", "d3-tip").html(function(d) {
      return "" + d.page + "(" + d.rank + ")";
    });
    force = d3.layout.force().size([width, height]);
    drag = force.drag().on("dragstart", function(d) {
      return d3.select(this).classed("fixed", d.fixed = true);
    });
    chart = d3.select("." + svgClass).attr("width", width).attr("height", height).call(tooltip);
    circles = chart.selectAll("circle");
    lines = chart.selectAll("polyline");
    colorx = d3.scale.linear().range([0, 255]).domain([0, width]);
    colory = d3.scale.linear().range([0, 255]).domain([0, height]);
    network = function(nodes, links) {
      var pages, radius;
      pages = nodes.map(function(d) {
        return d.page;
      });
      nodes = nodes.map(function(d) {
        return {
          page: d.page,
          rank: d.rank,
          x: Math.floor((1 + 2 * Math.random()) * width / 4),
          y: Math.floor((1 + 2 * Math.random()) * height / 4)
        };
      });
      radius = d3.scale.linear().domain(d3.extent(nodes, function(d) {
        return d.rank;
      })).range([9 - nodes.length / 1000, 30]);
      links = links.map(function(d) {
        return {
          source: nodes[pages.indexOf(d.from)],
          target: nodes[pages.indexOf(d.to)]
        };
      });
      lines = lines.data(links);
      lines.enter().append("polyline").attr("fill", "none").attr("stroke", "#aaa").attr("stroke-opacity", 0.8).attr("stroke-width", ".5px").attr("id", function(d) {
        return d.source.page + "-" + d.target.page;
      }).attr("marker-mid", "url(#markerArrow)");
      lines.exit().remove();
      circles = circles.data(nodes);
      circles.enter().append("circle").attr("class", "node").attr("id", function(d) {
        return d.page;
      }).attr("r", function(d) {
        return radius(d.rank);
      }).on("mouseover", function(d) {
        return tooltip.show(d);
      }).on("mouseout", function(d) {
        return tooltip.hide(d);
      }).on("dblclick", function(d) {
        return d3.select(this).classed("fixed", d.fixed = false);
      }).call(drag);
      circles.exit().remove();
      return force.nodes(nodes).links(links).on("tick", tick).charge(-18000 / nodes.length).linkDistance(50 - nodes.length / 200).start();
    };
    tick = function(e) {
      circles.attr("cx", function(d) {
        return d.x;
      }).attr("cy", function(d) {
        return d.y;
      }).style("fill", function(d, i) {
        return d3.rgb(colorx(d.x), colory(d.y), 128);
      });
      return lines.attr("points", function(d) {
        return "" + d.source.x + "," + d.source.y + " " + ((d.source.x + d.target.x) / 2) + "," + ((d.source.y + d.target.y) / 2) + " " + d.target.x + "," + d.target.y;
      });
    };
    return network;
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).NodeNetwork = Network;

}).call(this);

//# sourceMappingURL=node-graph.js.map
