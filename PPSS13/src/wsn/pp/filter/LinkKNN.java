/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import wsn.pp.gui.GNUPlot;

/**
 *
 * @author Killi
 */
public class LinkKNN extends Filter {

    private String learnType;
    private Map<String, List<DataPoint>> data;
    private boolean learning;
    private int k;
    private final GNUPlot plot;

    public LinkKNN(int k, LinkInfoReciver nextFilter) {
        super(nextFilter);
        this.k = k;
        this.data = new HashMap<String, List<DataPoint>>();
        this.plot = new GNUPlot();
    }

    public void learnType(String type) {
        this.learnType = type;
        this.data.put(type, new ArrayList<DataPoint>());
        this.learning = true;
    }

    public void stopLearning() {
        this.learning = false;
        
    }

    public void forget(String type) {
        this.data.put(type, new ArrayList<DataPoint>());
    }

    public void forgetAll(String type) {
        this.data = new HashMap<String, List<DataPoint>>();
    }

    private List<Neighbores> findNearestNeighbores(DataPoint dp) {
        List<Neighbores> list = new LinkedList<Neighbores>();
        for (String type : data.keySet()) {
            for (DataPoint other : data.get(type)) {
                double dist = other.position.distanceTo(dp.position);
                list.add(new Neighbores(other, dist));
            }
        }
        Collections.sort(list);
        if (list.size() > k) {
            list = list.subList(0, k);
        }
        return list;
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        if (learning && ls.metaData.containsKey("StdDev")) {
            data.get(learnType).add(new DataPoint(learnType, new Point(ls.power, (Double)(ls.metaData.get("StdDev")))));
        }

        try {
            String dataString = "";
            String plotString = "set terminal png\n plot ";
            DataPoint current = new DataPoint("Current", new Point(ls.power, (Double) ls.metaData.get("StdDev")));

            for (String type : data.keySet()) {
                if (data.get(type).size() > 0) {
                    plotString += "'-' title \"" + type + "\" with circles,";
                    for (DataPoint i : data.get(type)) {
                        dataString += i.position.x + " " + i.position.y + "\n";
                    }
                    dataString += "e\n";
                }
            }
            
            List<Neighbores> neighbores = findNearestNeighbores(current);
            if (neighbores.size() > 0) {
                plotString += "'-' title \"Neighbores\" with circles fs transparent solid 0.15 noborder,";
                for (Neighbores neighbore : neighbores) {
                    dataString += neighbore.data.position.x + " " + neighbore.data.position.y + "\n";
                }
                dataString += "e\n";
            }

            plotString += "'-' title \"Current\" with circles fs transparent solid 0.15 noborder";
            dataString += ls.power + " " + (Double) ls.metaData.get("StdDev") + "\ne\n";

            plot.plot(plotString + "\n" + dataString );
        } catch (InterruptedException ex) {
            Logger.getLogger(LinkKNN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkKNN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
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

        public double distanceTo(Point op) {
            return Math.sqrt(((x - op.x) * (x - op.x)) + ((y - op.y) * (y - op.y)));
        }
    }

    private static class Neighbores implements Comparable<Neighbores> {

        public DataPoint data;
        public double distance;

        public Neighbores(DataPoint data, double distance) {
            this.data = data;
            this.distance = distance;
        }

        @Override
        public int compareTo(Neighbores o) {
            if (distance < o.distance) {
                return -1;
            } else if (distance > o.distance) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
