(function() {
  var Network;

  Network = function(svgClass, width, height) {
    var color, draw, graticule, path, projection, svg, tooltip, zoom, zoomed;
    if (svgClass == null) {
      svgClass = "chart";
    }
    if (width == null) {
      width = window.innerWidth - 30;
    }
    if (height == null) {
      height = window.innerHeight - 30;
    }
    tooltip = d3.tip().attr("class", "d3-tip").html(function(d) {
      if (d.country != null) {
        return "" + d.country + "(" + d.total + ")";
      } else {
        return "" + d.properties.name;
      }
    }).offset(function() {
      var box, mouse, scale;
      box = this.getBBox();
      mouse = d3.mouse(this).map(function(d) {
        return parseInt(d);
      });
      scale = zoom.scale();
      return [(mouse[1] - box.y) * scale, (mouse[0] - box.x - box.width / 2) * scale];
    });
    color = d3.scale.linear().range(["#FFFF33", "#FF0033"]);
    graticule = d3.geo.graticule();
    projection = d3.geo.mercator().translate([width / 2, height / 2]).scale(d3.min([height, width]) / 2 / Math.PI);
    path = d3.geo.path().projection(projection);
    svg = d3.select("." + svgClass).attr("width", width).attr("height", height).call(tooltip).append("g");
    svg.append("rect").attr("width", width).attr("height", height).attr("fill", "#FFF");
    zoomed = function() {
      return svg.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
    };
    zoom = d3.behavior.zoom().scaleExtent([1, 3]).on("zoom", zoomed);
    zoom(svg);
    draw = function(countries) {
      var country;
      svg.append("path").datum(graticule).attr("class", "graticule").attr("d", path);
      svg.append("path").datum({
        type: "LineString",
        coordinates: [[-180, 0], [-90, 0], [0, 0], [90, 0], [180, 0]]
      }).attr("class", "equator").attr("d", path);
      country = svg.selectAll(".country").data(countries, function(d) {
        return d.properties.name;
      });
      return country.enter().insert("path").attr("class", "country").attr("d", path).attr("id", function(d) {
        return d.id;
      }).style("fill", "#AAA").on("mouseover", tooltip.show).on("mouseout", tooltip.hide);
    };
    draw.data = function(counts) {
      var selected;
      color.domain(d3.extent(counts, function(d) {
        return d.total;
      }));
      return selected = svg.selectAll(".country").data(counts, function(d) {
        var _ref;
        return (_ref = d.country) != null ? _ref : d.properties.name;
      }).style("fill", function(d) {
        return color(d.total);
      }).on("mouseover", tooltip.show).on("mouseout", tooltip.hide);
    };
    draw.color = color;
    return draw;
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).WordCloud = Network;

}).call(this);

//# sourceMappingURL=count-map.js.map
