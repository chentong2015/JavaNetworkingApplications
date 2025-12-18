package java_apis.tcp_socket;

import java.io.*;
import java.net.Socket;

public class SocketStreamUtils {

    // socket.getInputStream() 获取当前Socket接收到的流信息
    public static String getMessage(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream();
        ObjectInputStream inputStream = new ObjectInputStream(stream);
        // inputStream.readObject();
        return inputStream.readUTF();
    }

    // socket.getOutputStream() 获取当前Socket要发送出去的流信息
    public static void sendMessage(Socket socket, String message) throws IOException {
        OutputStream stream = socket.getOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(stream);
        // outputStream.writeObject(object);
        outputStream.writeUTF(message);
        outputStream.flush();
    }
}
