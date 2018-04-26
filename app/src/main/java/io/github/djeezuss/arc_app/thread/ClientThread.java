package io.github.djeezuss.arc_app.thread;

import io.github.djeezuss.arc_app.MainActivity;
import io.github.djeezuss.arc_app.thread.ui.UpdateUIThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientThread implements Runnable {

    private Socket clientSocket;
    private BufferedReader input;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            try {
                String read = input.readLine();

                // If received something, update the UI
                if(read != null) {
                    MainActivity.UIHandler.post(new UpdateUIThread(read));
                // If received nothing,
                } else {
                    MainActivity.thread = new Thread(
                        new ServerThread(MainActivity.SERVERPORT));
                    MainActivity.thread.start();
                    return;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
