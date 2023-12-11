package io.github.escposjava.print;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkPrinter implements Printer {
   private OutputStream printer = null;
   private final String address;
   private final int port;
   private final int timeout;

   public NetworkPrinter(String address, int port, int timeout) {
      this.address = address;
      this.port = port;
      this.timeout = timeout;
   }

   public void open(){
      try {
         Socket socket = new Socket();
         // This limits the time allowed to establish a connection in the case
         // that the connection is refused or server doesn't exist.
         socket.connect(new InetSocketAddress(address, port), timeout);
         // This stops the request from dragging on after connection succeeds.
         socket.setSoTimeout(timeout);
         printer = socket.getOutputStream();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void write(byte[] command){
      try {
         printer.write(command);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void close(){
      try {
         printer.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

}
