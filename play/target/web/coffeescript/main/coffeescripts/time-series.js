(function() {
  var data, mode, onSelect;

  data = [];

  $.ajax({
    url: '/demo/h2st/sentiment/id/tweet/sentiment',
    async: false,
    dataType: 'json',
    success: function(json) {
      return data = json.reverse().map(function(d) {
        return {
          ID: d.id,
          TWEET: d.tweet,
          SENTIMENT: d.sentiment
        };
      }).slice(0, 30);
    }
  });

  mode = TimeLineGraph(data);

  onSelect = function() {
    mode.interpolate($("#selInterpolate :selected").text());
    mode.ease($("#selEaseType :selected").text() + "-" + $("#selEaseMode :selected").text());
    return mode.interval($("#rInterval").val());
  };

  onSelect();

  (typeof exports !== "undefined" && exports !== null ? exports : this).onSelect = onSelect;

  (typeof exports !== "undefined" && exports !== null ? exports : this).graph = mode;

}).call(this);

//# sourceMappingURL=time-series.js.map
