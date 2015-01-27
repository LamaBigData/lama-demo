(function() {
  var Pie, barData, lastid, len;

  lastid = '0';

  barData = [];

  len = 5;

  Pie = function(json) {
    var DELAY, DURATION, arr, drapBarChart, drawPieChart, neg, pieChart, pos, showList, total, updateBarData;
    pos = json.filter(function(d) {
      return d.SENTIMENT === 3;
    }).map(function(d) {
      return {
        id: d.ID,
        tweet: d.TWEET
      };
    });
    neg = json.filter(function(d) {
      return d.SENTIMENT === 1;
    }).map(function(d) {
      return {
        id: d.ID,
        tweet: d.TWEET
      };
    });
    arr = d3.entries();
    total = pos.length + neg.length;
    arr["Positive"] = pos.length / total;
    arr["Negative"] = 1.0 - arr["Positive"];
    pieChart = [
      {
        color: 'red',
        description: 'Negative Tweets',
        title: 'flowers',
        value: arr["Negative"]
      }, {
        color: 'blue',
        description: 'Positive Tweets',
        title: 'trains',
        value: arr["Positive"]
      }
    ];
    updateBarData = function(json) {
      var negCount, newData, posCount;
      newData = json.filter(function(d) {
        return d.ID > lastid;
      });
      lastid = json[0].ID;
      if (barData.length === len) {
        barData.shift();
      }
      if (newData.length > 0) {
        posCount = newData.filter(function(d) {
          return d.SENTIMENT === 3;
        }).length;
        negCount = newData.length - posCount;
        return barData.push({
          pos: posCount,
          neg: negCount,
          date: new Date()
        });
      }
    };
    updateBarData(json);
    drapBarChart = function(data) {
      var bar, barWidth, bars, blueRect, buleRectText, chart, format, height, margin, redRect, redRectText, svg, tip, width, x, xAxis, xText, y, yAxis, yText;
      margin = {
        top: 50,
        right: 70,
        bottom: 50,
        left: 70
      };
      width = 700 - margin.right - margin.left;
      height = 300 - margin.top - margin.bottom;
      barWidth = 30;
      svg = d3.select(".seriesGraph").html("");
      xText = svg.append("text").attr("transform", "translate(" + (width + margin.left - 25) + "," + (height + margin.top + 20) + ")").attr("class", "axisText").text("Time");
      yText = svg.append("text").attr("transform", "translate( " + (margin.left - 40) + ", " + (margin.top + 50) + "), rotate(-90)").attr("class", "axisText").text("Number");
      redRect = svg.append("rect").attr("x", "" + (width + margin.left - 35)).attr("y", "5").attr("width", 10).attr("height", 10).attr("class", "pieChart__red");
      redRectText = svg.append("text").attr("x", "" + (width + margin.left - 10)).attr("y", "15").text("Negative");
      blueRect = svg.append("rect").attr("x", "" + (width + margin.left - 35)).attr("y", "20").attr("width", 10).attr("height", 10).attr("class", "pieChart__blue");
      buleRectText = svg.append("text").attr("x", "" + (width + margin.left - 10)).attr("y", "30").text("Positive");
      tip = d3.tip().attr('class', 'd3-tip').offset([-10, 0]).html(function(d) {
        return "<strong>Count:</strong> <span>" + d.count + "</span>";
      });
      chart = svg.attr("width", 700).attr("height", 300).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")").call(tip);
      xAxis = chart.append("g").attr("class", "axis").attr("width", width).attr("transform", "translate(0," + height + ")");
      yAxis = chart.append("g").attr("class", "axis").attr("height", height);
      x = d3.scale.ordinal().domain(d3.range(len)).rangePoints([0, width], 1.0);
      y = d3.scale.linear().domain([
        0, d3.max(data, function(d) {
          return d3.max([d.pos, d.neg]);
        })
      ]).range([height, 0]);
      format = function(i) {
        var dateFormat;
        dateFormat = d3.time.format("%H:%M:%S");
        if (data[i] != null) {
          return dateFormat(data[i].date);
        }
      };
      xAxis.call(d3.svg.axis().scale(x).tickFormat(function(d, i) {
        return format(i);
      }).orient("bottom")).selectAll("text").attr("transform", "rotate(-35)").attr("dy", "15").attr("dx", "-10");
      yAxis.call(d3.svg.axis().scale(y).ticks(7).orient("left"));
      bars = chart.append("g").attr("class", "barsArea").selectAll("g").data(data).enter().append("g");
      return bar = bars.selectAll("rect").data(function(d, i) {
        return [
          {
            x: x(i) - barWidth + 1,
            y: y(d.pos),
            color: "blue",
            count: d.pos
          }, {
            x: x(i),
            y: y(d.neg),
            color: "red",
            count: d.neg
          }
        ];
      }).enter().append("rect").attr("x", function(d) {
        return d.x;
      }).attr("y", function(d) {
        return d.y;
      }).attr("height", function(d) {
        return height - d.y;
      }).attr("width", barWidth).attr("class", function(d) {
        return 'pieChart__' + d.color;
      }).on('mouseover', tip.show).on('mouseout', tip.hide);
    };
    drapBarChart(barData);
    showList = function(wrapper, tweets) {
      var container;
      return container = d3.select("#" + wrapper).append("div").style("visible", "false").attr("class", "rendering-twitter-div").selectAll("blockquote").data(tweets).enter().append("blockquote").attr("class", "twitter-tweet").append("a").attr("href", function(d) {
        return "https://twitter.com/twitterapi/status/" + d.id;
      });
    };
    showList("wrapper0", pos.slice(0, 5));
    showList("wrapper1", neg.slice(0, 5));
    twttr.widgets.load();
    DURATION = 500;
    DELAY = 0;
    drawPieChart = function(elementId, data) {
      var arc, container, containerEl, detailedInfo, drawChartCenter, drawDetailedInformation, height, pie, pieChartPieces, pieData, radius, svg, twoPi, width;
      containerEl = document.getElementById(elementId);
      width = containerEl.clientWidth * 0.8;
      height = width * 0.4;
      radius = Math.min(width, height) / 2;
      container = d3.select(containerEl);
      svg = container.select('svg').attr('width', width).attr('height', height).select('#pieChartSVG').html('');
      pie = svg.append('g').attr('transform', 'translate(' + width / 2 + ',' + (height / 2) + ')');
      detailedInfo = svg.append('g').attr('class', 'pieChart--detailedInformation');
      twoPi = 2 * Math.PI;
      pieData = d3.layout.pie().value(function(d) {
        return d.value;
      }).sort(null);
      arc = d3.svg.arc().outerRadius(radius - 20).innerRadius(0);
      pieChartPieces = pie.datum(data).selectAll('path').data(pieData).enter().append('path').attr('class', function(d) {
        return 'pieChart__' + d.data.color;
      }).attr('filter', 'url(#pieChartInsetShadow)').attr('d', arc).each(function() {
        return this._current = {
          startAngle: 0,
          endAngle: 0
        };
      }).transition().duration(DURATION).attrTween('d', function(d) {
        var interpolate;
        interpolate = d3.interpolate(this._current, d);
        this._current = interpolate(0);
        return function(t) {
          return arc(interpolate(t));
        };
      }).each('end', function(d) {
        return drawDetailedInformation(d.data, this);
      });
      drawChartCenter = function() {
        var centerContainer;
        centerContainer = pie.append('g').attr('class', 'pieChart--center');
        centerContainer.append('circle').attr('class', 'pieChart--center--outerCircle').attr('r', 0).attr('filter', 'url(#pieChartDropShadow)').transition().duration(DURATION).delay(DELAY).attr('r', radius - 50);
        return centerContainer.append('circle').attr('id', 'pieChart-clippy').attr('class', 'pieChart--center--innerCircle').attr('r', 0).transition().delay(DELAY).duration(DURATION).attr('r', radius - 55).attr('fill', '#fff');
      };
      drawChartCenter();
      return drawDetailedInformation = function(data, element) {
        var anchor, bBox, infoContainer, infoWidth, position;
        bBox = element.getBBox();
        infoWidth = width * 0.3;
        anchor;
        infoContainer;
        position;
        if (element.className.baseVal === 'pieChart__red') {
          infoContainer = detailedInfo.append('g').attr('width', infoWidth).attr('transform', 'translate(' + (width - infoWidth) + ',' + (bBox.height + bBox.y + 50) + ')');
          anchor = 'end';
          position = 'right';
        } else {
          infoContainer = detailedInfo.append('g').attr('width', infoWidth).attr('transform', 'translate(' + 0 + ',' + (bBox.height + bBox.y + 50) + ')');
          anchor = 'start';
          position = 'left';
        }
        infoContainer.data([data.value * 100]).append('text').text('0 %').attr('class', 'pieChart--detail--percentage ' + 'pieChart__' + data.color).attr('x', position === 'left' ? 0 : infoWidth).attr('y', -10).attr('text-anchor', anchor).transition().duration(DURATION).tween('text', function(d) {
          var i;
          i = d3.interpolateRound(+this.textContent.replace(/\s%/gi, ''), d);
          return function(t) {
            return this.textContent = i(t) + ' %';
          };
        });
        infoContainer.append('line').attr('class', 'pieChart--detail--divider').transition().duration(DURATION).attr('x2', infoWidth);
        return infoContainer.append('text').attr('width', infoWidth).attr('height', 100).attr('transform', 'translate(0, 20)').attr('class', 'pieChart--detail--textContainer ' + 'pieChart--detail__' + position + ' pieChart__' + data.color).text(data.description);
      };
    };
    return drawPieChart('pieChart', pieChart);
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).SentimentPie = Pie;

}).call(this);

//# sourceMappingURL=sentiment-bar.js.map
