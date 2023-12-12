package io.github.escposjava.print;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkPrinter implements Printer {
   private Socket socket;
   private OutputStream printer;
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
         socket = new Socket();
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
         //unfortunatelly no write timeout exists :/
         printer.write(command);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void close(){
      try {
         // better closing socket than stream only
         socket.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

}
