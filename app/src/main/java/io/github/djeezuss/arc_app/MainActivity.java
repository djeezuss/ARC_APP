package io.github.djeezuss.arc_app;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.github.djeezuss.arc_app.thread.ServerThread;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    public static final int SERVERPORT = 8080;

    public static ServerSocket serverSocket;
    public static Handler UIHandler;
    public static Thread thread;

    public  static TextView lb_connected;
    private static TextView lb_IPAdrre;

    @Nullable
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lb_connected = this.findViewById(R.id.lb_connected);
        lb_IPAdrre = this.findViewById(R.id.lb_IPAddre);

        UIHandler = new Handler();

        // Shows the IP address of the device
        lb_IPAdrre.setText(getLocalIpAddress() + ":" + SERVERPORT);

        // Add a onClick event on bt_submit
        final Button bt_submit = findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        try {
            serverSocket = new ServerSocket(SERVERPORT, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread = new Thread(new ServerThread(SERVERPORT));
        thread.start();
    }
}
