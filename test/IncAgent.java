package test;

import test.TopicManagerSingleton.TopicManager;
import test.Agent;

public class IncAgent implements Agent{
    private final String[] outputs;

    public IncAgent(String[] input, String[] outputs){
        this.outputs = outputs;
        TopicManagerSingleton.get().getTopic(input[0]).subscribe(this);
    }

    @Override
    public void callback(String topic, Message msg){
        double val = msg.asDouble + 1;
        for (String out : outputs) TopicManagerSingleton.get().getTopic(out).publish(new Message(val));
    }
    @Override public String getName() { return "IncAgent"; }
    @Override public void reset() {}
    @Override public void close() {}
}
