Pie = (json) ->
  pos = json.filter((d) -> d.SENTIMENT == 1)
    .map((d) -> {id:d.ID, tweet:d.TWEET})
  neg = json.filter((d) -> d.SENTIMENT == 3)
    .map((d) -> {id:d.ID, tweet:d.TWEET})

  arr = d3.entries()
  total = pos.length + neg.length
  arr["Positive"] = pos.length / total
  arr["Negative"] = 1.0 - arr["Positive"]

  pieChart= [
      {
         color: 'red'
         description: 'Negative Tweets'
         title: 'flowers'
         value: arr["Negative"]
      },
      {
         color: 'blue'
         description: 'Positive Tweets'
         title: 'trains'
         value: arr["Positive"]
      },
  ]

  DURATION = 500
  DELAY = 0
  drawPieChart = (elementId, data) ->
    containerEl = document.getElementById(elementId)
    width = containerEl.clientWidth * 0.8
    height = width * 0.4
    radius = Math.min(width, height) / 2
    twoPi = 2 * Math.PI
    container = d3.select(containerEl)

    svg = container.select('svg')
      .attr('width', width)
      .attr('height', height)
      .select('#pieChartSVG')
      .html('')
    pie = svg.append('g')
      .attr('transform', 'translate(' + width / 2 + ',' + (height / 2) + ')')
    detailedInfo = svg.append('g')
      .attr('class', 'pieChart--detailedInformation')
    pieData = d3.layout.pie()
      .value((d) -> d.value)
      .sort(null)
    arc = d3.svg.arc()
      .outerRadius(radius - 20)
      .innerRadius(0)
    pieChartPieces = pie.datum(data)
      .selectAll('path')
      .data(pieData).enter()
      .append('path')
      .attr('class', (d) -> 'pieChart__' + d.data.color)
      .attr('filter', 'url(#pieChartInsetShadow)')
      .attr('d', arc)
      .each(() -> 
        this._current =
           startAngle: 0
           endAngle: 0)
      .transition().duration(DURATION)
      .attrTween('d', (d) -> 
         interpolate = d3.interpolate(this._current, d)
         this._current = interpolate(0)
         (t) -> arc(interpolate(t)))
      .each('end', (d) -> drawDetailedInformation(d.data, this))

    drawChartCenter = ->
      centerContainer = pie.append('g')
        .attr('class', 'pieChart--center')
      centerContainer
        .append('circle')
        .attr('class', 'pieChart--center--outerCircle')
        .attr('r', 0).attr('filter', 'url(#pieChartDropShadow)')
        .transition().duration(DURATION).delay(DELAY)
        .attr('r', radius - 50)
      centerContainer.append('circle')
        .attr('id', 'pieChart-clippy')
        .attr('class', 'pieChart--center--innerCircle')
        .attr('r', 0).transition().delay(DELAY)
        .duration(DURATION).attr('r', radius - 55)
        .attr('fill', '#fff')
    drawChartCenter()

    drawDetailedInformation = (data, element) ->
      bBox = element.getBBox()
      infoWidth = width * 0.3
      anchor
      infoContainer
      position

      if element.className.baseVal == 'pieChart__red'
         infoContainer = detailedInfo
           .append('g')
           .attr('width', infoWidth)
           .attr('transform', 'translate(' +
             (width - infoWidth) +
             ',' + (bBox.height + bBox.y + 50) + ')')
         anchor = 'end'
         position = 'right'
      else
         infoContainer = detailedInfo
           .append('g')
           .attr('width', infoWidth)
           .attr('transform', 'translate(' + 0 +
             ',' + (bBox.height + bBox.y + 50) + ')')
         anchor = 'start'
         position = 'left'

      infoContainer
        .data([data.value * 100])
        .append('text')
        .text('0 %')
        .attr('class', 'pieChart--detail--percentage ' +
          'pieChart__' + data.color)
        .attr('x', if position == 'left' then 0 else infoWidth)
        .attr('y', -10)
        .attr('text-anchor', anchor)
        .transition()
        .duration(DURATION)
        .tween('text', (d) ->
           i = d3.interpolateRound(+this.textContent.replace(/\s%/gi, ''), d)
           (t) -> this.textContent = i(t) + ' %')

      infoContainer
        .append('line')
        .attr('class', 'pieChart--detail--divider')
        .transition()
        .duration(DURATION)
        .attr('x2', infoWidth)

      infoContainer
        .append('text')
        .attr('width', infoWidth)
        .attr('height', 100)
        .attr('transform', 'translate(0, 20)')
        .attr('class', 'pieChart--detail--textContainer ' +
          'pieChart--detail__' + position + ' pieChart__' + data.color)
        .text(data.description)

  drawPieChart('pieChart', pieChart)

  showList = (wrapper, tweets) ->
    container = d3.select("##{wrapper}")
      .append("div")
      .style("visible", "false")
      .attr("class", "rendering-twitter-div")
      .selectAll("blockquote")
      .data(tweets)
      .enter()
      .append("blockquote")
      .attr("class", "twitter-tweet")
      .append("a")
      .attr("href", (d) -> "https://twitter.com/twitterapi/status/#{d.id}")

  showList("wrapper0", pos.slice(0, 5))
  showList("wrapper1", neg.slice(0, 5))
  twttr.widgets.load()

(exports ? this).SentimentPie = Pie
