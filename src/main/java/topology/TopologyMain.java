package topology;

import spouts.WordReader;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import bolts.WordConcatenator;
import bolts.WordWriter;

public class TopologyMain {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-concatenator", new WordConcatenator()).shuffleGrouping("word-reader");
		builder.setBolt("word-writer", new WordWriter()).shuffleGrouping("word-concatenator");

		Config conf = new Config();
		// conf.put("wordsFile", args[0]);
		conf.put("wordsFile", "src/main/resources/words.txt");
		conf.setDebug(true);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Word-Concatenetor-Toplogie", conf, builder.createTopology());

		Thread.sleep(1000);
		cluster.shutdown();

	}

}
