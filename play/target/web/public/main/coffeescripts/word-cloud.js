(function() {
  var Network;

  Network = function(svgClass, width, height) {
    var draw, fill, fontSize, layout, network, next, svg, tooltip;
    if (svgClass == null) {
      svgClass = "chart";
    }
    if (width == null) {
      width = window.innerWidth - 20;
    }
    if (height == null) {
      height = window.innerHeight - 20;
    }
    fill = d3.scale.category20();
    fontSize = d3.scale.sqrt().range([15, 100]);
    tooltip = d3.tip().attr("class", "d3-tip").html(function(d) {
      return "" + d.word + "(" + d.count + ")";
    });
    svg = d3.select("." + svgClass).attr("width", width).attr("height", height).call(tooltip).append("g").attr("transform", "translate(" + (width / 2) + "," + (height / 2) + ")");
    layout = d3.layout.cloud().size([width, height]).timeInterval(10).text(function(d) {
      return d.word;
    }).font("Comic Sans MS, Ubuntu").fontWeight("900").rotate(function(d) {
      return Math.random() * 180 - 90;
    }).padding(1);
    network = function(words) {
      fontSize.domain(d3.extent(words, function(d) {
        return d.count;
      }));
      return layout.words(words).fontSize(function(d) {
        return fontSize(d.count);
      }).on("end", draw).start();
    };
    next = function(words) {};
    network.OnFinished = function(callback) {
      return next = callback;
    };
    draw = function(words) {
      var text;
      text = svg.selectAll("text").data(words, function(d) {
        return d.text;
      });
      text.exit().remove();
      text.enter().append("text").style("font-family", function(d) {
        return d.font;
      }).style("font-weight", function(d) {
        return d.weight;
      }).attr("text-anchor", "middle").on("mouseover", tooltip.show).on("mouseout", tooltip.hide);
      text.transition().duration(1000).style("font-size", function(d) {
        return "" + d.size + "px";
      }).style("fill", function(d, i) {
        return fill(i);
      }).text(function(d) {
        return d.text;
      }).attr("transform", function(d) {
        return "translate(" + d.x + "," + d.y + ")rotate(" + d.rotate + ")";
      });
      return next(words);
    };
    return network;
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).WordCloud = Network;

}).call(this);

//# sourceMappingURL=word-cloud.js.map
