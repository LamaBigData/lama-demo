(function() {
  var Network;

  Network = function(minimum, svgClass) {
    var chart, circles, collide, colors, drag, force, height, network, texts, tick, tooltip, width;
    if (minimum == null) {
      minimum = 1;
    }
    if (svgClass == null) {
      svgClass = "chart";
    }
    width = 1200;
    height = 800;
    tooltip = d3.tip().attr("class", "d3-tip").html(function(d) {
      return "" + d.name + "(" + d.count + ")";
    });
    force = d3.layout.force().size([width, height]).gravity(0.01).charge(0).chargeDistance(0);
    drag = force.drag().on("dragstart", function(d) {
      return d3.select(this).classed("fixed", d.fixed = true);
    });
    chart = d3.select("." + svgClass).attr("width", width).attr("height", height).call(tooltip);
    circles = chart.selectAll("circle");
    texts = chart.selectAll("text");
    colors = d3.scale.category20();
    network = function(error, words) {
      var nodes, radius;
      nodes = words.filter(function(d) {
        return d.count >= minimum;
      });
      radius = d3.scale.linear().domain(d3.extent(nodes, function(d) {
        return d.count;
      })).range([30, 60]);
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
        return colors(i % 20);
      }).on("mouseover", function(d) {
        return tooltip.show(d);
      }).on("mouseout", function(d) {
        return tooltip.hide(d);
      }).on("dblclick", function(d) {
        return d3.select(this).classed("fixed", d.fixed = false);
      }).call(drag);
      texts = texts.data(nodes);
      texts.enter().append("text").text(function(d) {
        return d.name;
      }).attr("x", function(d) {
        return d.x;
      }).attr("y", function(d) {
        return d.y;
      }).attr("font-family", "sans-serif").attr("font-size", "10px").attr("text-anchor", "middle").attr("fill", "black");
      return force.nodes(nodes).on("tick", tick(nodes, radius)).start();
    };
    tick = function(nodes, radius) {
      return function(e) {
        var q;
        q = d3.geom.quadtree(nodes);
        nodes.forEach(function(n) {
          return q.visit(collide(n, radius));
        });
        circles.attr("cx", function(d) {
          return d.x;
        }).attr("cy", function(d) {
          return d.y;
        });
        return texts.attr("x", function(d) {
          return d.x;
        }).attr("y", function(d) {
          return d.y;
        });
      };
    };
    collide = function(node, radius) {
      var nx1, nx2, ny1, ny2, r, region;
      region = node.fixed ? 100 : 1;
      r = radius(node.count) + region;
      nx1 = node.x - r;
      nx2 = node.x + r;
      ny1 = node.y - r;
      ny2 = node.y + r;
      return function(quad, x1, y1, x2, y2) {
        var l, x, y;
        if (quad.point && (quad.point !== node)) {
          x = node.x - quad.point.x;
          y = node.y - quad.point.y;
          l = Math.sqrt(x * x + y * y);
          r = radius(node.count) + radius(quad.point.count) + region;
          if (l < r) {
            l = (l - r) / l * .4;
            x *= l;
            y *= l;
            if (!node.fixed) {
              node.x -= x;
              node.y -= y;
            }
            if (!quad.point.fixed) {
              quad.point.x += x;
              quad.point.y += y;
            }
          }
        }
        return x1 > nx2 || x2 < nx1 || y1 > ny2 || y2 < ny1;
      };
    };
    return network;
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).CollisionNetwork = Network;

}).call(this);

//# sourceMappingURL=collision.js.map
