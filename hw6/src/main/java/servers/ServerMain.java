package servers;

public class ServerMain {
    public static void main(String[] args) {
        String port = null;
        String properties = null;
        for (int i = 0; i <args.length; i++) {
            if (args[i].startsWith("--port")) {
                port = args[i].split("=")[1];
            }
            if (args[i].startsWith("--db-properties-path")) {
                properties = args[i].split("=")[1];
            }
        }
        System.out.println(port + " " +  properties);
        MyServer server = new MyServer();
        server.start(7000);
    }
}
