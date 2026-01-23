package configs;

import java.util.function.BinaryOperator;
import test.Agent;
import test.TopicManagerSingleton.TopicManager;
import test.TopicManagerSingleton;

public class BinOpAgent implements Agent {
    private final String name;
    private final String first_topic_name;
    private final String second_topic_name;
    private final String result_topic_name;
    private final BinaryOperator<Double> operation;
    private double val1 = 0.0;
    private double val2 = 0.0;

    public BinOpAgent(String name, String first_topic_name, String second_topic_name, String result_topic_name, BinaryOperator<Double> operation) {
        this.name = name;
        this.first_topic_name = first_topic_name;
        this.second_topic_name = second_topic_name;
        this.result_topic_name = result_topic_name;
        this.operation = operation;
        TopicManager tm = TopicManagerSingleton.get();
        tm.getTopic(this.first_topic_name).subscribe(this);
        tm.getTopic(this.second_topic_name).subscribe(this);
        tm.getTopic(this.result_topic_name).addPublisher(this);
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void reset() {
        val1 = 0.0;
        val2 = 0.0;
    }

    @Override
    public void callback(String topic, test.Message msg) {
        if (topic.equals(this.first_topic_name)) {
            val1 = msg.asDouble;
        } else if (topic.equals(this.second_topic_name)) {
            val2 = msg.asDouble;
        }
        if (val1 != 0.0 && val2 != 0.0) {
            TopicManager tm = TopicManagerSingleton.get();
            tm.getTopic(this.result_topic_name).publish(new test.Message(String.valueOf(operation.apply(val1, val2))));
        }
    }

    @Override
    public void close() {
        TopicManager tm = TopicManagerSingleton.get();
        tm.getTopic(this.first_topic_name).unsubscribe(this);
        tm.getTopic(this.second_topic_name).unsubscribe(this);
        tm.getTopic(this.result_topic_name).removePublisher(this);
    }
}