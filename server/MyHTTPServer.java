package server;

import java.io.*;
import java.net.*;
import java.util.*;
import servlet.Servlet;
import test.RequestParser;
import test.RequestParser.RequestInfo;

public class MyHTTPServer extends Thread implements HTTPServer {
    private final int port;
    private volatile boolean stop = false;
    private ServerSocket server;
    // מפה של מפות: Command -> (URI -> Servlet)
    private final Map<String, TreeMap<String, Servlet>> handlers = new HashMap<>();

    public MyHTTPServer(int port, int nThreads) {
        this.port = port;
        handlers.put("GET", new TreeMap<>(Comparator.reverseOrder()));
        handlers.put("POST", new TreeMap<>(Comparator.reverseOrder()));
        handlers.put("DELETE", new TreeMap<>(Comparator.reverseOrder()));
    }

    @Override
    public void addServlet(String httpCommand, String uri, Servlet s) {
        handlers.get(httpCommand.toUpperCase()).put(uri, s);
    }

    @Override
    public void removeServlet(String httpCommand, String uri) {
        handlers.get(httpCommand.toUpperCase()).remove(uri);
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000); // בדיקת עצירה כל שנייה
            while (!stop) {
                try {
                    Socket client = server.accept();
                    handleClient(client);
                } catch (SocketTimeoutException e) {}
            }
            server.close();
        } catch (IOException e) {}
    }

    private void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             OutputStream out = client.getOutputStream()) {
            
            RequestInfo ri = RequestParser.parseRequest(in);
            if (ri != null) {
                Servlet s = null;
                // חיפוש Longest Match
                for (String prefix : handlers.get(ri.getHttpCommand()).keySet()) {
                    if (ri.getUri().startsWith(prefix)) {
                        s = handlers.get(ri.getHttpCommand()).get(prefix);
                        break;
                    }
                }
                if (s != null) s.handle(ri, out);
            }
        } catch (IOException e) {}
    }

    @Override public void close() { stop = true; }
}