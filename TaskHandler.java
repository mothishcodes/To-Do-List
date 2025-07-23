import java.io.*;
import java.net.*;

public class TaskHandler {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4000)) {
            System.out.println("Server running on port 4000...");

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String line;
                StringBuilder requestBody = new StringBuilder();
                boolean isPost = false;

                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    if (line.startsWith("POST")) {
                        isPost = true;
                    }
                }

                if (isPost) {
                    char[] buffer = new char[1024];
                    int charsRead = in.read(buffer);
                    requestBody.append(buffer, 0, charsRead);

                    String body = requestBody.toString();
                    String[] parts = body.split("\"");
                    String task = parts[3];
                    String category = parts[7];

                    saveTask(task, category);
                }

                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Content-Type: text/plain\r\n");
                out.write("Access-Control-Allow-Origin: *\r\n");
                out.write("\r\n");
                out.write("Task saved successfully.");
                out.flush();

                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveTask(String task, String category) {
        try (FileWriter fw = new FileWriter("tasks.txt", true)) {
            fw.write("[" + category + "] " + task + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
