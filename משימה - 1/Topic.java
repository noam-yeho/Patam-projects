package test;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    public final String name;
    public final List<Agent> subscribers = new ArrayList<>();
    public final List<Agent> publishers = new ArrayList<>();
    Topic(String name){
        this.name=name;
    }

    public void subscribe(Agent a){
        this.subscribers.add(a);
    }
    public void unsubscribe(Agent a){
        this.subscribers.remove(a);
    }

    public void publish(Message m){
        for (Agent agent : this.subscribers) {
            agent.callback(this.name, m);
        }
    }

    public void addPublisher(Agent a){
        this.publishers.add(a);
    }

    public void removePublisher(Agent a){
        this.publishers.remove(a);
    }


}
