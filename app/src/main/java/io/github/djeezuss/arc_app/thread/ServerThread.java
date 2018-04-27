package io.github.djeezuss.arc_app.thread;

import io.github.djeezuss.arc_app.ISendMessage;
import io.github.djeezuss.arc_app.MainActivity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread implements Runnable, ISendMessage {

    private Socket socket;
    private ClientThread clientThread;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted())
        {
            try {
                socket = MainActivity.INSTANCE.serverSocket.accept();

                clientThread = new ClientThread(socket);
                new Thread(clientThread).start();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(String msg) {
        if (clientThread != null)
            clientThread.sendMessage(msg);
    }
}
