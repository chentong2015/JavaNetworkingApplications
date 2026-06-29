package com.httpclient4;

import org.apache.http.HttpHost;
import org.apache.http.config.Lookup;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.*;

// TODO. HttpClient底层源码，基于Socket建立起HTTP连接
public class HttpClientSourceCode {

    private DnsResolver dnsResolver;
    private SchemePortResolver schemePortResolver;

    public void connect(ManagedHttpClientConnection conn, HttpHost host, InetSocketAddress localAddress,
                        int connectTimeout, SocketConfig socketConfig, HttpContext context) throws IOException {
        Lookup<ConnectionSocketFactory> registry = this.getSocketFactoryRegistry(context);
        ConnectionSocketFactory sf = (ConnectionSocketFactory) registry.lookup(host.getSchemeName());
        if (sf == null) {
            throw new UnsupportedSchemeException(host.getSchemeName() + " protocol is not supported");
        } else {
            // 根据host name解析成list of pair (ip address + port)
            InetAddress[] addresses = host.getAddress() != null ?
                    new InetAddress[]{host.getAddress()} : this.dnsResolver.resolve(host.getHostName());
            int port = this.schemePortResolver.resolve(host);

            for (int i = 0; i < addresses.length; ++i) {
                InetAddress address = addresses[i];
                boolean last = i == addresses.length - 1;
                Socket sock = sf.createSocket(context);
                sock.setSoTimeout(socketConfig.getSoTimeout());
                sock.setReuseAddress(socketConfig.isSoReuseAddress());
                sock.setTcpNoDelay(socketConfig.isTcpNoDelay());
                sock.setKeepAlive(socketConfig.isSoKeepAlive());
                if (socketConfig.getRcvBufSize() > 0) {
                    sock.setReceiveBufferSize(socketConfig.getRcvBufSize());
                }
                if (socketConfig.getSndBufSize() > 0) {
                    sock.setSendBufferSize(socketConfig.getSndBufSize());
                }

                int linger = socketConfig.getSoLinger();
                if (linger >= 0) {
                    sock.setSoLinger(true, linger);
                }
                conn.bind(sock);
                InetSocketAddress remoteAddress = new InetSocketAddress(address, port);
                try {
                    // TODO. 由ConnectionSocketFactory连接工厂创建指定的socket
                    sock = sf.connectSocket(connectTimeout, sock, host, remoteAddress, localAddress, context);
                    conn.bind(sock);
                    return;
                } catch (SocketTimeoutException var19) {
                    if (last) {
                        throw new ConnectTimeoutException(var19, host, addresses);
                    }
                } catch (ConnectException var20) {
                    if (last) {
                        String msg = var20.getMessage();
                        if ("Connection timed out".equals(msg)) {
                            throw new ConnectTimeoutException(var20, host, addresses);
                        }

                        throw new HttpHostConnectException(var20, host, addresses);
                    }
                } catch (NoRouteToHostException var21) {
                    if (last) {
                        throw var21;
                    }
                }
            }

        }
    }

    private Lookup<ConnectionSocketFactory> getSocketFactoryRegistry(HttpContext context) {
        String attribute = "http.socket-factory-registry";
        Lookup<ConnectionSocketFactory> reg = (Lookup) context.getAttribute(attribute);
        if (reg == null) {
            // reg = this.socketFactoryRegistry;
        }
        return reg;
    }
}
