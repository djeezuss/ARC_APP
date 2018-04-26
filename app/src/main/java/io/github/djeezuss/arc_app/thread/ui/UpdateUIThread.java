package io.github.djeezuss.arc_app.thread.ui;

import io.github.djeezuss.arc_app.MainActivity;

public class UpdateUIThread implements Runnable {
    private String msg;

    public UpdateUIThread(String str) {
        msg = str;
    }

    @Override
    public void run() {
        MainActivity.lb_connected.setText("MSG : " + msg + "\n");
    }
}
