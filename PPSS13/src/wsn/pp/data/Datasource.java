package wsn.pp.data;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;
import wsn.pp.filter.LinkInfo;
import wsn.pp.filter.LinkInfoReciver;
import wsn.pp.messages.ConfigMsg;
import wsn.pp.messages.SnoopBCMsg;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan
 */
public class Datasource implements MessageListener {

    public static final boolean _MACOS = true;//System.getProperty("os.name").equals("Mac OS X");
    
    
    private MoteIF mote;
    private final LinkInfoReciver slave;
    private ObjectOutputStream out;
    private Object outSync = new Object();
    private FileOutputStream fileOut;
    private long startTime;
    private static Datasource _instance;

    public static Datasource getInstance() {
        return _instance;
    }
    private boolean liveData = true;

    public void startRecording(File file) throws FileNotFoundException {
        try {
            synchronized(outSync){
            fileOut = new FileOutputStream(file);
            out = new ObjectOutputStream(fileOut);
            startTime = System.nanoTime();
            }
        } catch (IOException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playRecording(File file) {
        if (slave != null) {
            try {
                liveData = false;
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fis);
                Packet p = null;
                while (true) {
                    p = (Packet) in.readObject();
                    this.messageReceivedWithTimestamp(p.adress, new SnoopBCMsg(p.data, 8), p.time, true);
                }
            } catch (EOFException e) { // Playback done
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {
                System.out.println(file.getPath());
                e.printStackTrace();
            }
            liveData = true;
        }
    }

    public void sendConfig(int node, int interval, int level) {
        try {
            ConfigMsg msg = new ConfigMsg();
            msg.set_interval(interval);
            msg.set_signalStrength((byte) level);
            if (mote != null) {
                mote.send(node, msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopRecording() {
        if (out == null) {
            return;
        }
        try {
            synchronized (outSync) {
                out.close();
                fileOut.close();
                
                    out = null;
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Datasource(LinkInfoReciver slave, File file) {
        this.slave = slave;
        _instance = this;
        if (file == null) {
            if (_MACOS) {
                return;
            }
            mote = new MoteIF(PrintStreamMessenger.err);
            //mote = new MoteIF(BuildSource.makePhoenix("sf@192.168.178.39:9002", PrintStreamMessenger.err));
            mote.registerListener(new SnoopBCMsg(), this);
        } else {
            playRecording(file);
        }
    }

    public void connectNextMote(Message msg, String src) {
        if (src == null) {
            src = "serial@/dev/ttyUSB3:telos";
        }
        PhoenixSource source = BuildSource.makePhoenix(src, PrintStreamMessenger.err);
        MoteIF m = new MoteIF(source);
        m.registerListener(msg, this);
    }

    @Override
    public synchronized void messageReceived(int node, Message msg) {
        if (liveData) {
            messageReceivedWithTimestamp(node, msg, System.nanoTime(), true);
        }
    }

    private synchronized void messageReceivedWithTimestamp(int node, Message msg, long timestamp, boolean plot) {
        if (slave != null && msg instanceof SnoopBCMsg) {
            SnoopBCMsg sm = (SnoopBCMsg) msg;
            //Logger.getLogger(Datasource.class.getName()).log(Level.INFO, String.format("Msg from %d", sm.get_nodeid()));
            for (int i = 0; i < 10; i++) {
                LinkInfo li = new LinkInfo(i + 1, sm.get_nodeid(), sm.get_othernodes()[i], timestamp);
                if (plot == false) {
                    li.getMetaData().put("DoNotPlot", null);
                }
                slave.recvLinkInfo(li);
            }
        }
        synchronized (outSync) {
            if (out != null && msg instanceof SnoopBCMsg) {
                try {
                    out.writeObject(new Packet(System.nanoTime() - startTime, node, msg));
                } catch (IOException ex) {
                    //Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
