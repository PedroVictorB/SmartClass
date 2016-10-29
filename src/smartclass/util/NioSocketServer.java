package smartclass.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartclass.entities.Attributes;
import smartclass.entities.ContextResponsesContainer;
import smartclass.entities.ContextResponsesSubscriptionContainer;

public class NioSocketServer {

    public NioSocketServer() {
        // Create an AsynchronousServerSocketChannel that will listen on port 5000
        final AsynchronousServerSocketChannel listener;
        try {
            listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(1026));
            // Listen for a new request
        listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            @Override
            public void completed(AsynchronousSocketChannel ch, Void att) {
                // Accept the next connection
                listener.accept(null, this);
                
                // Greet the client
                ch.write(ByteBuffer.wrap("Hello, I am Echo Server 2020, let's have an engaging conversation!\n".getBytes()));

                // Allocate a byte buffer (4K) to read from the client
                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                try {
                    // Read the first line
                    int bytesRead = ch.read(byteBuffer).get(20, TimeUnit.SECONDS);

                    boolean running = true;
                    while (bytesRead != -1 && running) {
                        // Make sure that we have data to read
                        if (byteBuffer.position() > 2) {
                            // Make the buffer ready to read
                            byteBuffer.flip();

                            // Convert the buffer into a line
                            byte[] lineBytes = new byte[bytesRead];
                            byteBuffer.get(lineBytes, 0, bytesRead);
                            String line = new String(lineBytes);

                            // Debug
                            String line2 = line.substring(line.indexOf("\r\n")+225);
                            ObjectMapper mapper = new ObjectMapper();
                            ContextResponsesSubscriptionContainer crc = mapper.readValue(line2, ContextResponsesSubscriptionContainer.class);
//                            for(Attributes a : crc.getContextResponses()[0].getContextElement().getAttributes()){
//                                System.out.println("Nome: "+a.getName()+" / Tipo: "+a.getType()+" / Valor: "+a.getValue());
//                            }
                            Service s = new Service(crc.getContextResponses()[0].getContextElement().getAttributes(), crc.getContextResponses()[0].getContextElement().getId());
                            
                            // Echo back to the caller
                            ch.write(ByteBuffer.wrap(line.getBytes()));

                            // Make the buffer ready to write
                            byteBuffer.clear();

                            // Read the next line
                            bytesRead = ch.read(byteBuffer).get(20, TimeUnit.SECONDS);
                        } else {
                            // An empty line signifies the end of the conversation in our protocol
                            running = false;
                        }
                    }
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    // The user exceeded the 20 second timeout, so close the connection
                    ch.write(ByteBuffer.wrap("Good Bye\n".getBytes()));
                    System.out.println("Connection timed out, closing connection");
                } catch (IOException ex) {
                    Logger.getLogger(NioSocketServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("End of conversation");
                // Close the connection if we need to
                if (ch.isOpen()) {
                    System.out.println("Channel is open");
                    try {
                        ch.close();
                        System.out.println("Channel is closed");
                    } catch (IOException ex) {
                        Logger.getLogger(NioSocketServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Void att) {
                ///...
            }
        });
        } catch (IOException ex) {
            Logger.getLogger(NioSocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
