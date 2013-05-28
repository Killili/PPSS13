package wsn.pp.gui;

import wsn.pp.filter.LinkFilter;
import wsn.pp.filter.LinkMeanFilter;
import wsn.pp.filter.LinkPrinter;
import wsn.pp.messages.SnoopBCMsg;
import java.awt.Point;
import java.io.File;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import wsn.pp.data.Datasource;
import wsn.pp.filter.LinkATMFFilter;
import wsn.pp.filter.LinkMedianFilter;
import wsn.pp.filter.LinkPlot;
import wsn.pp.gui.ConfigView;
import wsn.pp.gui.View;

public class Main implements MessageListener {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
    }
    private MoteIF mote;
    private View v;
    private ConfigView cv;

    public Main() {
        // Set up topologie View
        v = new View();
        v.setVisible(true);
        v.initNodes(getInitPoints(5));
        
        // Configure Filters
        LinkFilter lf = new LinkFilter();
        lf.registerLinkFilter(2, 3, new LinkPlot("Raw"));
        //lf.registerLinkFilter(2, 3, new LinkATMFFilter(11,0.1f, new LinkPlot("ATMF")));
        //lf.registerLinkFilter(2, 3, new LinkMeanFilter(10, new LinkPlot("Mean")));
        lf.registerLinkFilter(2, 3, new LinkMedianFilter(10, new LinkPlot("Median")));
        lf.registerLinkFilter(2, 3, new LinkMedianFilter(10, new LinkATMFFilter(11,0.1f, new LinkPlot("Median+ATMF"))));
        
        //lf.registerLinkFilter(2, 3, new LinkMeanValueFilter(10, new LinkPrinter()));
        //LinkPlot test = new LinkPlot();
        //test.show();
        
        Datasource loggin = new Datasource(lf,null);

        //Datasource loggin = new Datasource(lf,new File("Pentagram-e"));

        //cv = new ConfigView(loggin);
        //cv.setVisible(true);
        //loggin.sendConfig(2,125, 31);
        //loggin.sendConfig(3,125, 31);
        //try{loggin.startRecording(new File("test.log"));} catch (FileNotFoundException e) {e.printStackTrace();}
        //loggin.playRecording(new File("test.log"));
		/*
         mote = new MoteIF(PrintStreamMessenger.err);
         mote.registerListener(new SnoopBCMsg(), this);*/
    }

    private Point[] getInitPoints(int amount) {
        amount = Math.min(amount, 6);
        Point[] result = new Point[6];
        result[0] = new Point(30, 70);
        result[1] = new Point(200, 40);
        result[2] = new Point(400, 70);
        result[3] = new Point(370, 300);
        result[4] = new Point(200, 400);
        result[5] = new Point(20, 300);

        Point[] out = new Point[amount];
        for (int i = 0; i < amount; i++) {
            out[i] = result[i];
        }
        return out;
    }

    @Override
    public void messageReceived(int arg0, Message arg1) {
        //System.out.println("recieved");
        if (arg1 instanceof SnoopBCMsg) {

            SnoopBCMsg rssi = (SnoopBCMsg) arg1;

            rssi.get_nodeid();
            v.updateRssi(rssi.get_nodeid(), rssi.get_othernodes());
        }

    }
}
