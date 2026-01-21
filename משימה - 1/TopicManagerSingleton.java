package test;


public class TopicManagerSingleton {

    public static class TopicManager{
        private static final TopicManager instance = new TopicManager();
        private static Map<String, Topic> topics = new HashMap<>();

        private TopicManager(){
        }

        public Topic getTopic(String name){
            if (topics.containsKey(name)) {
                return topics.get(name);
            } else {
                Topic newTopic = new Topic(name);
                topics.put(name, newTopic);
                return newTopic;
            }
        }
        public Collection<Topic> getAllTopics() {
            return topics.values();
        }
    }

    public static TopicManager get(){
        return TopicManager.instance;
    }

    public void clear(){
        TopicManager.topics.clear();
    }
}
