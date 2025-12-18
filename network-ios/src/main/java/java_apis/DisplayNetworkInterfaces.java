package java_apis;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

// TODO. 显示系统上运行的网络连接
// Network Interface: Systems often run with multiple active network connections
public class DisplayNetworkInterfaces {

    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            System.out.println("Display name: " + netIf.getDisplayName());
            System.out.println("Name: " + netIf.getName());
            for (NetworkInterface subIf : Collections.list(netIf.getSubInterfaces())) {
                System.out.println("Sub Interface Display name: " + subIf.getDisplayName());
                System.out.println("Sub Interface Name: " + subIf.getName());
            }
        }
    }
}