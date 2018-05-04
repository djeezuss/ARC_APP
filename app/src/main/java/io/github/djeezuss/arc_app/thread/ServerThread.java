package io.github.djeezuss.arc_app.thread;

import io.github.djeezuss.arc_app.ISendMessage;
import io.github.djeezuss.arc_app.MainActivity;
import io.github.djeezuss.arc_app.thread.ui.UpdateUIConnectionThread;

import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable, ISendMessage {

    public Socket socket;
    private ClientThread clientThread;

    @Override
    public void sendMessage(String msg) {
        if(clientThread != null)
        {
            clientThread.sendMessage(msg);
        }
    }

    public void startClientThread()
    {
        if (clientThread != null)
            new Thread(clientThread).start();
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted())
        {
            try {
                socket = MainActivity.INSTANCE.serverSocket.accept();
                MainActivity.INSTANCE.UIHandler.post(new UpdateUIConnectionThread("Connected to " + socket.getRemoteSocketAddress()));

                clientThread = new ClientThread(socket);
                startClientThread();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
