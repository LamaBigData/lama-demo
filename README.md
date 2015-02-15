LAMA demo for [Big Data 2015 winter school](http://grammars.grlmc.com/bigdat2015/).

# Pre-requisite

* JDK 1.7+
* Linux/Mac Operating System (For Windows Users, see [below](#windows))
* Network connection: You need to connect to maven and ivy repositories when compiling the demo.

# Prepare to run the demo

Before you can run the demo, you need to first compile the job codes, start h2 database and play web server.

## Compile the job codes

This repo uses [sbt](http://www.scala-sbt.org/) to compile. We provide commands in this readme to run all our demos. To compile the job codes, simply run:

```
./sbt compile
```

## Start h2 in-memory database

All the demo jobs writes data to the h2 database so we can visualize them in the play web server. We chose h2 database since it does not require any installation. To start the h2 database, simply run the command:

```
./sbt "runMain org.h2.tools.Server"
```

H2 will start a web console in your browser. If you want to play the data, you can login with the JDBC url `jdbc:h2:/tmp/demo/h2`. All our demo job will create and write to the tables in this database.

## Start the play web server

We use play for visualizing the results of our demo. Althouth you don't need to start the play if you only want to run the job, we recommend you to start the server so that you can see what is happening in the real-time job.

```
./sbt "project play" run
```

Play uses port 9000 by default, if you encounter an "Address in use" exception, you can try with another port (e.g. 10000):

```
./sbt "project play" "run 10000"
```

# Run the demo job and visualization

We have three demo jobs in this repo.

For better visualization, we provided streaming version of all three jobs. However, it's not difficult to implement the job on batch platform, as we included in our word count demo. You can see that the real-time job and the batch job can share lot of codes and logic.

## Word Count Demo

1. Start the real-time job

```
./sbt "runMain com.htc.studio.summingbird.job.HTCJobRunner --htc.job com.htc.studio.example.wordcount.WordCountStormJob --local"
```

The job will run until you press Ctrl+C.

2. Navigate to the word graph

Open the link http://localhost:9000/demo/wordcount/wordcount?reload=2 in your browser.

Since it may took several seconds for the job to start, you may see a table not found error in this page. Don't panic, just leave it alone, the page will reload when the data is ready. It will continue to refresh every other second, so you can see your real-time job running.

3. Run the batch job

You can also run the batch version of word count job.

```
./sbt "runMain com.htc.studio.summingbird.job.HTCJobRunner --htc.job com.htc.studio.example.wordcount.WordCountScaldingJob --table wordcountscalding"
```

4. Navigate to the word graph of the batch job

The batch job will append the current batch id to the table name. So you need to get the table name yourself. Wait until the job finishes, go to the h2 web console and refresh the table list. Find the table name that starts with `wordcountscalding`. Open the link `http://localhost:9000/demo/wordcount/<table_name>` in your browser.

## Map Count Demo

1. Start the job

```
./sbt "runMain com.htc.studio.summingbird.job.HTCJobRunner --htc.job com.htc.studio.example.mapcount.MapCountStormJob --local"
```

The job will run until you press Ctrl+C.

2. Navigate to the graph

Open the link http://localhost:9000/demo/mapcount/mapcount?reload=1 in your browser.

Since it may took several seconds for the job to start, you may see any results on the graph. Don't panic, just leave it alone, the page will reload when the data is ready. It will continue to refresh every second, so you can see your real-time job running.

## Tweet Sentiment Demo

1. Create a twitter app key

Follow the instructions at https://apps.twitter.com/ to create a twitter app key and store them in the `twitter4j.properties` file in the following format:

```
oauth.consumerKey=<api-key-for-your-app>
oauth.consumerSecret=<api-secret-for-your-app>
oauth.accessToken=<access-token>
oauth.accessTokenSecret=<access-token-secret>
```

2. Start the job

We use [standord nlp](nlp.stanford.edu/software/corenlp.shtml) library to do sentiment analysis. However, the model file is very large (about 200M), so we by default use random numbers to generate tweets' sentiment score:

```
./sbt "runMain com.htc.studio.summingbird.job.HTCJobRunner --htc.job com.htc.studio.example.sentiment.SentimentJob --twitter4j twitter4j.properties --local"
```

If you want to use real model, run the job like the following:

```
./sbt "set fakeModel := false" "runMain com.htc.studio.summingbird.job.HTCJobRunner --htc.job com.htc.studio.example.sentiment.SentimentJob --twitter4j twitter4j.properties --local"
```

The job will run until you press Ctrl+C.

3. Navigate to the graph

Open the link http://localhost:9000/demo/sentiment/sentiment in your browser.

Since it may took several seconds for the job to start, you may need to manually refresh the page until the data is loaded. Once the data is loaded, the page will automatically refresh.

# For Windows Users<a name="windows"></a>

Since all the codes are written in java, the demo should be able to run on Windows, but with a few more extra efforts.

## Install sbt

You need to install sbt your self. [An official msi install package](http://www.scala-sbt.org/download.html) is available.

## Change the path of the h2 database

In our codes, we have provided a default file path for h2 with `/tmp/demo/h2`. You need to change this to a Windows path, e.g. `D:\demo\h2`. You need to change them in two places:

* In the job. You don't need to change the code, just add command line argument `--connection h2:tcp://sa:@localhost/D:\demo\h2` when running the job.
* In the play web server. Change the [appliction.conf](play/conf/appliction.conf) file in ther `play/conf` foler.

# Authors
## Owners
 * [Gang Wu](https://github.com/simonandluna)
 * [Zhongyang Zheng](https://github.com/zyzheng)
 * Roy Jiang

## Contributor
 * Wanming Lu
 * Yonglin Fu

## Supervisor
 * Edward Chang
