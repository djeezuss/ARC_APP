package io.github.djeezuss.arc_app.task.async;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;

public class ClientSendAsyncTask extends AsyncTask {

    private BufferedWriter output;
    private String msg;

    public ClientSendAsyncTask(BufferedWriter output)
    {
        this.output = output;
        this.msg = msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            output.flush();
            output.write(msg);
            output.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
