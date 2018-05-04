package io.github.djeezuss.arc_app.task.async;

import android.os.AsyncTask;
import io.github.djeezuss.arc_app.MainActivity;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAsyncTask extends AsyncTask<Void, Void, String> {

    ServerSocket serverSocket;
    Socket client;

    private BufferedReader input;

    public ServerAsyncTask(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String read = "";

        try {
            client = serverSocket.accept();

            input  = new BufferedReader(new InputStreamReader(client.getInputStream()));
            /*MainActivity.INSTANCE.clientTask =
                    new ClientSendAsyncTask(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));*/

            read = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return read;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        MainActivity.INSTANCE.lb_messages.setText(result);
        MainActivity.INSTANCE.lb_connected.setText(client.getRemoteSocketAddress().toString());
    }
}
