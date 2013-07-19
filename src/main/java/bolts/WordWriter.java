package bolts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class WordWriter implements IRichBolt {

	private OutputCollector collector;
	private FileWriter writer;
	private BufferedWriter bufferedWriter;
	private File file;
	private final String filename = "src/main/resources/output.txt";

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		file = new File(filename);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			writer = new FileWriter(file.getAbsoluteFile());
			bufferedWriter = new BufferedWriter(writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute(Tuple input) {
		String content = input.getString(0);
		try {
			System.out.println("WRITING CONTENT :: " + content + ">>> " + content.length());
			bufferedWriter.write(content);
			bufferedWriter.newLine();

		} catch (IOException e) {
			System.out.println("Error writing to file ");
			e.printStackTrace();
		}

		collector.ack(input);

	}

	public void cleanup() {
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error closing file at cleanup");
			e.printStackTrace();
		}

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
