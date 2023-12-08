package io.github.escposjava.print;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class NetworkPrinter implements Printer {
   private OutputStream printer = null;
   private final String address;
   private final int port;

   public NetworkPrinter(String address, int port){
      this.address = address;
      this.port = port;
   }

   public void open(){
      try {
         Socket socket = new Socket(this.address, this.port);
         socket.setSoTimeout(1000);
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
