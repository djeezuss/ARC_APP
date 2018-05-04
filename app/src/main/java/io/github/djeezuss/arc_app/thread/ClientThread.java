package io.github.djeezuss.arc_app.thread;

import android.support.annotation.Nullable;
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

    private String msg;
    private boolean send;

    public ClientThread(Socket clientSocket)
    {
        this(clientSocket, false, null);
    }

    public ClientThread(Socket clientSocket, boolean send, @Nullable String msg) {
        this.clientSocket = clientSocket;
        this.send = send;
        this.msg = msg;

        try {
            input  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void sendMessage(String msg) {
        this.send = true;
        this.msg = msg;
    }

    @Override
    public void run() {
//        while (!Thread.currentThread().isInterrupted())
//        {
            try {
                String read = "";

                if(!this.send) {
                    read = this.input.readLine();
                }
                else if (this.msg != null)
                {
                    output.flush();
                    output.write(this.msg);
                    send = false;
                }


                // If received something, update the UI
                if(read != null) {
                    MainActivity.INSTANCE.UIHandler.post(new UpdateUIThread(read));
                } else if(!this.send) {
                    MainActivity.INSTANCE.thread = new Thread(new ServerThread());
                    MainActivity.INSTANCE.thread.start();
                }
            } catch (Exception e) { e.printStackTrace(); }
//        }
    }
}
