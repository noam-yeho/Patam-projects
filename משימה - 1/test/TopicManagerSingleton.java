package test;

import java.util.*;

public class TopicManagerSingleton {
    private static final TopicManager instance = new TopicManager();
    private static final Map<String, Topic> topics = new HashMap<>();

    public static class TopicManager{

        private TopicManager(){}

        public Topic getTopic(String name){
            return topics.computeIfAbsent(name, Topic::new);
        }

        public Collection<Topic> getAllTopics() {
            return topics.values();
        }
        
        public static TopicManager get(){
            return instance;
        }
    
        public void clear(){
            topics.clear();
        }
    }
   
    public static TopicManager get(){
        return TopicManager.get();
    }
}
