package io.github.djeezuss.arc_app.thread;

import io.github.djeezuss.arc_app.ISendMessage;
import io.github.djeezuss.arc_app.MainActivity;
import io.github.djeezuss.arc_app.thread.ui.UpdateUIThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ClientThread implements Runnable, ISendMessage {

    private Socket clientSocket;
    private BufferedReader input;
    private BufferedWriter output;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            input  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

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
                    MainActivity.INSTANCE.UIHandler.post(new UpdateUIThread(read));
                // If received nothing,
                } else {
                    MainActivity.INSTANCE.thread = new Thread(new ServerThread());
                    MainActivity.INSTANCE.thread.start();
                    return;
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @Override
    public void sendMessage(String msg) {
        try {
            clientSocket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
