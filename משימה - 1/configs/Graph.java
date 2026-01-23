package configs;

import java.util.ArrayList;
import java.util.HashMap;

import test.TopicManagerSingleton.TopicManager;
import test.TopicManagerSingleton;

public class Graph extends ArrayList<Node>{
    
    public boolean hasCycles() {
        for (Node node: this) {
            if (node.hasCycles()) {
                return true;
            }
        }
        return false;
    }
    public void createFromTopics(){
        this.clear();
        TopicManager tm = TopicManagerSingleton.get();
        HashMap<String, Node> nodesMap = new HashMap<>();
        for (test.Topic t : tm.getAllTopics()) {
            String tname = "T" + t.name;
            Node tNode = nodesMap.computeIfAbsent(tname, Node::new);
            this.add(tNode);
            for (test.Agent a : t.subscribers) {
                String aName = "A" + a.getName();
                Node aNode = nodesMap.computeIfAbsent(aName, Node::new);
                tNode.addEdge(aNode);
                if (!this.contains(aNode)) this.add(aNode);
            }
            for (test.Agent a: t.publishers) {
                String aName = "A" + a.getName();
                Node aNode = nodesMap.computeIfAbsent(aName, Node::new);
                aNode.addEdge(tNode);
                if (!this.contains(aNode)) this.add(aNode);
            }
        }
    }    

    
}
