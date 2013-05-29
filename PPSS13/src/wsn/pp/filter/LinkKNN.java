/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import wsn.pp.gui.GNUPlot;

/**
 *
 * @author Killi
 */
public class LinkKNN extends Filter {

    private String learnType;
    private List<DataPoint> data;
    private boolean learning;
    private int k;
    private final GNUPlot plot;

    public LinkKNN(int k, LinkInfoReciver nextFilter) {
        super(nextFilter);
        this.k = k;
        this.plot = new GNUPlot();
    }

    public void learnType(String type) {
        this.learnType = type;
    }

    public void stopLearning() {
    }

    public void forget(String type) {
    }

    public void forgetAll(String type) {
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        if (learning && ls.metaData.containsKey("StdDev")) {
            data.add(new DataPoint(learnType, new Point(ls.power, (double) ls.metaData.get("StdDev"))));
        } else {
            
        }

        try {
            String dataString = "";
            for (DataPoint i : data) {
                dataString += i.position.x + " " + i.position.y + "\n";
            }
            plot.plot("set terminal png\n plot '-' title \"" + this.learnType + "\" with circles\n" + dataString + "e\nquit\n");
        } catch (InterruptedException | IOException | URISyntaxException ex) {
            Logger.getLogger(LinkKNN.class.getName()).log(Level.SEVERE, null, ex);
        }

        super.recvLinkInfo(ls);
    }

    private static class DataPoint {

        public String Type;
        public Point position;

        public DataPoint(String Type, Point position) {
            this.Type = Type;
            this.position = position;
        }
    }

    private static class Point {

        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
