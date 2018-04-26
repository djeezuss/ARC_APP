package io.github.djeezuss.arc_app.thread;

import io.github.djeezuss.arc_app.MainActivity;

import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    private int port;

    public ServerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted())
        {
            try {
                socket = MainActivity.serverSocket.accept();

                new Thread( new ClientThread(socket)).start();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
