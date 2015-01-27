(function() {
  var Network;

  Network = function(svgClass) {
    var chart, circles, colorx, colory, drag, force, height, lines, network, tick, tooltip, width;
    if (svgClass == null) {
      svgClass = "chart";
    }
    width = 1600;
    height = 800;
    tooltip = d3.tip().attr("class", "d3-tip").html(function(d) {
      return "" + d.name + "(" + d.count + ")";
    });
    force = d3.layout.force().size([width, height]);
    drag = force.drag().on("dragstart", function(d) {
      return d3.select(this).classed("fixed", d.fixed = true);
    });
    chart = d3.select("." + svgClass).attr("width", width).attr("height", height).call(tooltip);
    circles = chart.selectAll("circle");
    lines = chart.selectAll("line");
    colorx = d3.scale.linear().range([0, 255]).domain([0, width]);
    colory = d3.scale.linear().range([0, 255]).domain([0, height]);
    network = function(error, data) {
      var allnodes, links, nodelist, nodes, radius;
      allnodes = d3.merge(data.map(function(d, i, a) {
        return [d.node1, d.node2];
      }));
      nodelist = d3.set(allnodes).values().sort();
      nodes = nodelist.map(function(d, i, a) {
        return {
          index: i,
          name: d,
          x: Math.floor(Math.random() * width),
          y: Math.floor(Math.random() * height),
          count: allnodes.filter(function(e) {
            return e === d;
          }).length
        };
      });
      radius = d3.scale.linear().domain(d3.extent(nodes, function(d) {
        return d.count;
      })).range([5, 10]);
      links = data.map(function(d, i, a) {
        return {
          id: i,
          source: nodes[nodelist.indexOf(d.node1)],
          target: nodes[nodelist.indexOf(d.node2)],
          weight: d.weight
        };
      });
      lines = lines.data(links);
      lines.enter().append("line").attr("stroke", "#aaa").attr("stroke-opacity", 0.8).attr("stroke-width", ".5px").attr("id", function(d) {
        return d.id;
      }).attr("x1", function(d) {
        return d.source.x;
      }).attr("y1", function(d) {
        return d.source.y;
      }).attr("x2", function(d) {
        return d.target.x;
      }).attr("y2", function(d) {
        return d.target.y;
      });
      lines.exit().remove();
      circles = circles.data(nodes);
      circles.enter().append("circle").attr("class", "node").attr("id", function(d) {
        return d.name;
      }).attr("r", function(d) {
        return radius(d.count);
      }).attr("cx", function(d) {
        return d.x;
      }).attr("cy", function(d) {
        return d.y;
      }).style("fill", function(d, i) {
        return d3.rgb(colorx(d.x), colory(d.y), 128);
      }).on("mouseover", function(d) {
        return tooltip.show(d);
      }).on("mouseout", function(d) {
        return tooltip.hide(d);
      }).on("dblclick", function(d) {
        return d3.select(this).classed("fixed", d.fixed = false);
      }).call(drag);
      circles.exit().remove();
      return force.nodes(nodes).links(links).on("tick", tick).charge(-100).linkDistance(function(d) {
        return 52 - d.weight * 50;
      }).start();
    };
    tick = function(e) {
      circles.attr("cx", function(d) {
        return d.x;
      }).attr("cy", function(d) {
        return d.y;
      }).style("fill", function(d, i) {
        return d3.rgb(colorx(d.x), colory(d.y), 128);
      });
      return lines.attr("x1", function(d) {
        return d.source.x;
      }).attr("y1", function(d) {
        return d.source.y;
      }).attr("x2", function(d) {
        return d.target.x;
      }).attr("y2", function(d) {
        return d.target.y;
      });
    };
    return network;
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).ForceNetwork = Network;

}).call(this);

//# sourceMappingURL=force-graph.js.map
