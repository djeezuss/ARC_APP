package io.github.djeezuss.arc_app.thread.ui;

import io.github.djeezuss.arc_app.MainActivity;

public class UpdateUIConnectionThread implements Runnable {
    private String msg;

    public UpdateUIConnectionThread(String msg)
    {
        this.msg = msg;
    }

    @Override
    public void run() { MainActivity.INSTANCE.lb_connected.setText(msg); }
}
