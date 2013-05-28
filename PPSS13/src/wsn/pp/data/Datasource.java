package wsn.pp.data;


import wsn.pp.filter.LinkInfoReciver;
import wsn.pp.filter.LinkInfo;
import wsn.pp.messages.SnoopBCMsg;
import wsn.pp.messages.ConfigMsg;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan
 */
public class Datasource implements MessageListener {

    private MoteIF mote;
    private final LinkInfoReciver slave;
    private ObjectOutputStream out;
    private FileOutputStream fileOut;
    private long startTime;

    public void startRecording(File file) throws FileNotFoundException {
        try {
            fileOut = new FileOutputStream(file);
            out = new ObjectOutputStream(fileOut);
            startTime = System.nanoTime();
        } catch (IOException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playRecording(File file) {
        if (slave != null) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fis);
                Packet p = null;
                while (true) {
                    p = (Packet) in.readObject();
                    this.messageReceivedWithTimestamp(p.adress, new SnoopBCMsg(p.data, 8),p.time);
                }
            } catch (EOFException e) { // Playback done
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendConfig(int node, int interval, int level) {
        try {
            ConfigMsg msg = new ConfigMsg();
            msg.set_interval(interval);
            msg.set_signalStrength((byte) level);
            mote.send(node, msg);
        } catch (IOException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopRecording() {
        try {
            synchronized (out) {
                out.close();
                fileOut.close();
                out = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Datasource(LinkInfoReciver slave,File file) {
        this.slave = slave;
        if( file == null ){
            mote = new MoteIF(PrintStreamMessenger.err);
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
        messageReceivedWithTimestamp(node, msg, System.nanoTime());
    }
    
    private synchronized void messageReceivedWithTimestamp(int node, Message msg,long timestamp) {
        if (slave != null && msg instanceof SnoopBCMsg) {
            SnoopBCMsg sm = (SnoopBCMsg) msg;
            Logger.getLogger(Datasource.class.getName()).log(Level.INFO, String.format("Msg from %d", sm.get_nodeid()));
            for (int i = 0; i < 10; i++) {
                slave.recvLinkInfo(new LinkInfo(i+1, sm.get_nodeid(), sm.get_othernodes()[i],timestamp));
            }
        }
        if (out != null && msg instanceof SnoopBCMsg) {
            synchronized (out) {
                try {
                    out.writeObject(new Packet(System.nanoTime() - startTime, node, msg));
                } catch (IOException ex) {
                    Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
