(function() {
  var Chart;

  Chart = function(data, svgClass, radius) {
    var chart, circles, color, duration, ease, easemode, graph, height, interpolate, interval, line, margin, now, path, pushData, source, tick, time, tooltip, updateData, width, x, xAxis, y, yAxis;
    if (svgClass == null) {
      svgClass = "seriesGraph";
    }
    if (radius == null) {
      radius = 7;
    }
    margin = {
      top: 10,
      right: 20,
      bottom: 50,
      left: 20
    };
    width = 960 - margin.right;
    height = 300 - margin.top - margin.bottom;
    tooltip = d3.tip().attr("class", "d3-tip").html(function(d) {
      return "" + d;
    });
    now = new Date();
    x = d3.scale.linear().domain([0, data.length]).range([0, width]);
    time = d3.time.scale().domain([now - (data.length - 2) * duration, now - duration]).range([0, width]);
    y = d3.scale.linear().domain([0, 4]).range([height, 0]);
    line = d3.svg.line().interpolate("linear").x(function(d, i) {
      return x(i);
    }).y(function(d, i) {
      return y(d);
    });
    chart = d3.select("." + svgClass).attr("width", 672).attr("height", 268).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")").call(tooltip);
    chart.append("defs").append("clipPath").attr("id", "clip").append("rect").attr("width", width).attr("height", height);
    graph = chart.append("g");
    path = graph.append("g").attr("clip-path", "url(#clip)").append("path").data(data).attr("class", "line " + "lineChart--areaLine").attr("d", line(data.map(function(d) {
      return d.SENTIMENT;
    })));
    circles = graph.append("g").selectAll("circle").data(data);
    color = ["white", "red", "green", "blue"];
    circles.enter().append("circle").attr("class", function(d) {
      return "node " + "pieChart__" + color[d.SENTIMENT] + " lineChart--circle";
    }).attr("id", function(d, i) {
      return "node" + i;
    }).attr("r", radius).attr("cx", function(d, i) {
      return x(i);
    }).attr("cy", function(d, i) {
      return y(d.SENTIMENT);
    }).on("mouseover", function(d) {
      return tooltip.show(d.TWEET);
    }).on("mouseout", function(d) {
      return tooltip.hide(d.TWEET);
    });
    chart.append("rect").attr("transform", "translate(-" + margin.left + ",-" + margin.top + ")").attr("width", "" + margin.left + "px").attr("height", height + margin.top + margin.bottom).attr("fill", "white");
    xAxis = graph.append("g").attr("class", "axis").attr("width", width).attr("transform", "translate(0," + height + ")");
    yAxis = chart.append("g").attr("class", "axis").attr("height", height);
    easemode = "linear";
    duration = 1000;
    interpolate = function(interpolation) {
      return line.interpolate(interpolation);
    };
    ease = function(e) {
      return easemode = e;
    };
    interval = function(i) {
      return duration = i;
    };
    source = [];
    updateData = function(json) {
      var last, newdata;
      last = data[data.length - 1];
      newdata = json.filter(function(d) {
        return d.ID > last.ID;
      });
      return source = source.concat(newdata);
    };
    pushData = function() {
      if (source.length < 1) {
        return data[data.length - 1];
      } else {
        return source.pop();
      }
    };
    tick = function() {
      data.push(pushData());
      data.shift();
      now = new Date();
      time.domain([now - (data.length - 1) * duration, now]);
      y.domain([0, 4]);
      xAxis.call(d3.svg.axis().scale(time).orient("bottom"));
      yAxis.call(d3.svg.axis().scale(y).ticks(5).orient("left"));
      path.attr("d", line(data.map(function(d) {
        return d.SENTIMENT;
      })));
      circles.data(data).attr("cx", function(d, i) {
        return x(i);
      }).attr("cy", function(d) {
        return y(d.SENTIMENT);
      }).attr("class", function(d) {
        return "node " + "pieChart__" + color[d.SENTIMENT] + " lineChart--circle";
      });
      return graph.attr("transform", null).transition().duration(duration).ease(easemode).attr("transform", "translate(" + x(-1) + ")").each("end", tick);
    };
    tick();
    return {
      ease: ease,
      interpolate: interpolate,
      interval: interval,
      updateData: updateData
    };
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).TimeLineGraph = Chart;

}).call(this);

//# sourceMappingURL=time-line-graph.js.map
