package wsn.pp.data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import net.tinyos.message.Message;

/**
 *
 * @author Jan
 */
public class Packet implements Serializable {
    private static final long serialVersionUID = -3080305875112804296L;
    long time;
    int adress;
    int nodeid;
    byte[] data;

    public Packet(long time, int adress, Message msg) {
        this.time = time;
        this.adress = adress;
        this.data = msg.getSerialPacket().dataGet(); 
    }
}
