(function() {
  var Network;

  Network = function(svgClass, width, height) {
    var network, svg, x;
    if (svgClass == null) {
      svgClass = "chart";
    }
    if (width == null) {
      width = window.innerWidth - 20;
    }
    if (height == null) {
      height = window.innerHeight - 20;
    }
    x = d3.scale.log().range([0, width]);
    svg = d3.select("." + svgClass).attr("width", width);
    network = function(data, length) {
      var barHeight, bars, enter, usedData;
      if (length == null) {
        length = data.length;
      }
      barHeight = d3.max([height / length, 25]);
      usedData = data.slice(0, length);
      x.domain([
        1, d3.max(usedData, function(d) {
          return d.value.count;
        }) + 1
      ]);
      svg.attr("height", barHeight * length);
      bars = svg.selectAll("g").data(usedData);
      enter = bars.enter().append("g");
      enter.append("rect");
      enter.append("text").attr("x", 5).attr("dy", ".35em").attr("class", "count");
      bars.attr("transform", function(d, i) {
        return "translate(0," + (i * barHeight) + ")";
      }).select("rect").attr("height", barHeight - 1).attr("width", function(d) {
        return x(d.value.count + 1);
      }).attr("class", function(d) {
        if (d.value.count === d.value.oldcount) {
          return "remain";
        } else {
          return "increased";
        }
      }).style("fill", function(d) {
        if (d.value.count === d.value.oldcount) {
          return d.value.color;
        } else {
          return "";
        }
      }).transition().delay(900).attr("class", function(d) {
        return "remain";
      }).style("fill", function(d) {
        return d.value.color;
      });
      bars.select(".count").attr("y", barHeight / 2).text(function(d) {
        var base, diff;
        base = "" + d.key + "  " + d.value.count;
        diff = d.value.count - d.value.oldcount;
        if (diff === 0) {
          return base;
        } else if (diff > 0) {
          return "" + base + " (+" + diff + ")";
        } else {
          return "" + base + " (" + diff + ")";
        }
      });
      return data.forEach(function(d) {
        d.value.oldcount = d.value.count;
        return d.value.count = 0;
      });
    };
    return network;
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).CountBar = Network;

}).call(this);

//# sourceMappingURL=count-bar.js.map
