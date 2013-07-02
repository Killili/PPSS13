package wsn.pp.gui;


import wsn.pp.filter.SavitzkyGolay;
import java.awt.Point;
import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import wsn.pp.data.Datasource;
import wsn.pp.filter.LinkATMFFilter;
import wsn.pp.filter.LinkFilter;
import wsn.pp.filter.LinkKNN;
import wsn.pp.messages.SnoopBCMsg;

public class Main implements MessageListener {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
    }
    private View v;
    private ConfigView cv;
    private final LinkFilter lf;
    private final KNNControl knnc;
    
    private void addLink(int s, int d) {
        
        SavitzkyGolay sg = new SavitzkyGolay(11, null);
        
        LinkATMFFilter atmf = new LinkATMFFilter(30, 0.2f, null);
        
        LinkKNN knn = new LinkKNN(6, null);

        //atmf.registerFilter(visualGui);
        
        atmf.registerFilter(knn);
        sg.registerFilter(knn);
        
        //lf.registerLinkFilter(s, d, atmf);
        lf.registerLinkFilter(s, d, sg);
        
        
        //knnc.addKNN(knn,atmf,s,d);
        
        knnc.addKNN(knn,sg,s,d);
        knn.registerFilter(knnc);
    }

    public Main() {
        // Set up topologie View
        //v = new View();
        //v.setVisible(true);
        //v.initNodes(getInitPoints(5));

        // Configure Filters
        lf = new LinkFilter();

        //lf.registerLinkFilter(2, 3, new LinkPlot("Raw"));

        knnc = new KNNControl(lf);
        knnc.setVisible(true);
        Datasource loggin = new Datasource(lf, null);
        //Thread t = new Thread(visualGui = new VisualGuiControl(null, null, loggin));
        //t.start();

        int[] motes = {1,2,3,4,5};

        for (int i : motes) {
            for (int j = 0; j < 10; j++) {
             loggin.sendConfig(i,50, 31);
            }
            for (int j : motes) {
                if (i != j) {
                    addLink(i, j);
                }
            }
        }
 
        /*
         addLink(7, 6);
         addLink(1, 5);
         addLink(1, 4);
         addLink(1, 2);
         addLink(1, 3);
         addLink(1, 4);
         addLink(1, 6);
         knnc.learn("empty");
         loggin.playRecording(new File("naive-e"));
         knnc.stop();
        
         knnc.learn("standing");
         loggin.playRecording(new File("naive-s-a"));
         knnc.stop();
        
        
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
