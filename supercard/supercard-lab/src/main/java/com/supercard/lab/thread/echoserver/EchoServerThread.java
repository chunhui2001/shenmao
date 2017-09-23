package com.supercard.lab.thread.echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class EchoServerThread
{
    public static void main( String[] args ) throws IOException {

        class SocketConnectionHandler implements Runnable {

            InputStream inputStream = null;
            OutputStream outputStream = null;

            SocketConnectionHandler(Socket socket) throws IOException {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            }

            @Override
            public void run() {

                try {

                    int readcount;

                    byte[] readbuffer = new byte[1024];

                    while ((readcount = inputStream.read(readbuffer)) != -1) {
                        outputStream.write(readbuffer, 0, readcount);
                        outputStream.flush();
                    }

                } catch (IOException e) {}

            }
        }

        ServerSocket socketServer = new ServerSocket(4567);

        System.out.println("Your server available processor count is: " + Runtime.getRuntime().availableProcessors() +
                           ", Your socket server is running port on: " + 4567);

         // 每个连接请求创建一个新的线程, 当请求连接的速度高于处理连接的速度时，系统的线程数会无线增长
         // 导致服务器停止服务甚至崩溃, 可以用 "线程池" (ExecutorService) 来避免这些问题
         /* while (true) {

            Socket worker = socketServer.accept();

            Thread socketHander = new Thread(new SocketConnectionHandler(worker));
            socketHander.start();

         } */


        // 使用线程池, 如果同一时间有超过线程池大小的 execute() 存在, 超出的部分将进行排队直到某线程被释放
        //
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        while (true) {
            Socket worker = socketServer.accept();
            executorService.execute(new SocketConnectionHandler(worker));
        }

    }

}
