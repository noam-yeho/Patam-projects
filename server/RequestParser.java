package server;

import java.io.*;
import java.util.*;

public class RequestParser {
    public static RequestInfo parseRequest(BufferedReader reader) throws IOException {
        String firstLine = reader.readLine();
        if (firstLine == null || firstLine.isEmpty()) return null;

        String[] parts = firstLine.split(" ");
        String httpCommand = parts[0];
        String uri = parts[1];

        Map<String, String> parameters = new HashMap<>();
        String path = uri;
        
        if (uri.contains("?")) {
            String[] uriParts = uri.split("\\?");
            path = uriParts[0];
            String[] params = uriParts[1].split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length > 1) parameters.put(pair[0], pair[1]);
            }
        }
        
        String[] uriSegments = Arrays.stream(path.split("/"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
        }

        StringBuilder contentBuilder = new StringBuilder();
        
        while (reader.ready()) {
            line = reader.readLine();
            if (line == null) break;
            
            if (line.isEmpty()) continue;
            
            if (line.contains("filename=")) {
                String fileVal = line.split("filename=")[1].trim();
                parameters.put("filename", fileVal);
                continue;
            }
            
            contentBuilder.append(line).append("\n");
        }
        
        byte[] content = null;
        if (contentBuilder.length() > 0) {
            content = contentBuilder.toString().getBytes();
        }

        return new RequestInfo(httpCommand, uri, uriSegments, parameters, content);
    }

    public static class RequestInfo {
        private final String httpCommand, uri;
        private final String[] uriSegments;
        private final Map<String, String> parameters;
        private final byte[] content;

        public RequestInfo(String httpCommand, String uri, String[] uriSegments, Map<String, String> parameters, byte[] content) {
            this.httpCommand = httpCommand;
            this.uri = uri;
            this.uriSegments = uriSegments;
            this.parameters = parameters;
            this.content = content;
        }
        public String getHttpCommand() { return httpCommand; }
        public String getUri() { return uri; }
        public String[] getUriSegments() { return uriSegments; }
        public Map<String, String> getParameters() { return parameters; }
        public byte[] getContent() { return content; }
    }
}