import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpLogin {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restdb";
    private static final String DB_USER = "root"; // Change this to your MySQL username
    private static final String DB_PASSWORD = "root"; // Change this to your MySQL password

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/login", new LoginHandler());
        server.setExecutor(null);
        System.out.println("Server started on port 8000...");
        server.start();
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "Only POST method is supported");
                return;
            }

            try {
                // Read the request body
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                
                // Parse form data
                Map<String, String> params = parseFormData(formData);
                String username = params.get("username");
                String password = params.get("password");

                if (username == null || password == null) {
                    sendResponse(exchange, 401, "Invalid credentials");
                    return;
                }

                // Validate credentials against database
                if (validateCredentials(username, password)) {
                    sendResponse(exchange, 200, "Login successful");
                } else {
                    sendResponse(exchange, 401, "Invalid credentials");
                }

            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "Internal Server Error");
            }
        }

        private Map<String, String> parseFormData(String formData) {
            Map<String, String> params = new HashMap<>();
            if (formData != null) {
                String[] pairs = formData.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        try {
                            params.put(URLDecoder.decode(keyValue[0], "UTF-8"), 
                                     URLDecoder.decode(keyValue[1], "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return params;
        }

        private boolean validateCredentials(String username, String password) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, username);
                stmt.setString(2, password);
                
                ResultSet rs = stmt.executeQuery();
                return rs.next(); // Returns true if a matching record is found
                
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                return false;
            }
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
