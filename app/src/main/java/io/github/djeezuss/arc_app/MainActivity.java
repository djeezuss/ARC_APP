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

    public static final int SERVER_PORT = 8080;
    public static MainActivity INSTANCE;

    public ServerSocket serverSocket;
    public Handler UIHandler;
    public Thread thread;

    private ServerThread serverThread;
    public ISendMessage clientShip;
//    private ServerAsyncTask serverTask;
//    public ClientSendAsyncTask clientTask;

    public  TextView lb_connected;
    public  TextView lb_messages;
    private TextView lb_IPAdrre;
    public  TextView lb_info;

    private  TextView tb_coordX;
    private  TextView tb_coordY;
    private  TextView tb_coordZ;

    private Button   bt_submit;

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

    public String getCoordinates()
    {
        return this.tb_coordX.getText() + " " + this.tb_coordY.getText() + " " + this.tb_coordZ.getText();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        INSTANCE = this;

        lb_connected = this.findViewById(R.id.lb_connected);
        lb_messages  = this.findViewById(R.id.lb_messages);
        lb_IPAdrre   = this.findViewById(R.id.lb_IPAddre);
        lb_info      = this.findViewById(R.id.lb_info);

        tb_coordX = this.findViewById(R.id.tb_coordX);
        tb_coordY = this.findViewById(R.id.tb_coordY);
        tb_coordZ = this.findViewById(R.id.tb_coordZ);

        bt_submit = this.findViewById(R.id.bt_submit);

        UIHandler = new Handler();

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(getLocalIpAddress(), SERVER_PORT));

            String text = serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort();
            lb_IPAdrre.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //serverTask = new ServerAsyncTask(serverSocket);
        //serverTask.execute();

        serverThread = new ServerThread();

        thread = new Thread(serverThread);
        thread.start();

        // Add a onClick event on bt_submit
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serverThread.socket != null)
                {
                    serverThread.sendMessage(getCoordinates());
                    serverThread.startClientThread();
                }


               /* String msg = getCoordinates();
                if (clientTask != null && msg.length() > 2)
                {
                    clientTask.setMsg(msg);
                    clientTask.execute();
                }*/
            }
        });
    }
}
