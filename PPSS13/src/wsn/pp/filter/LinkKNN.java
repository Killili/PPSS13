/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import wsn.pp.gui.GNUPlot;
import wsn.pp.gui.KNNControl;

/**
 *
 * @author Killi
 */
public class LinkKNN extends Filter {

    private String learnType;
    private Map<String, List<DataPoint>> data;
    private List<DataPoint> learnPackage;
    private boolean learning;
    private int k;
    private GNUPlot plot;
    private int plotCounter = 0;
    private String title = "NotSet";
    private KNNControl knnControl;

    @Override
    public String toString() {
        return this.title;
    }

    public LinkKNN(int k, LinkInfoReciver nextFilter) {
        super(nextFilter);
        this.k = k;
        this.data = new HashMap<String, List<DataPoint>>();
    }

    public void learnType(String type) {
        this.learnType = type;
        this.data.put(type, new ArrayList<DataPoint>());
        this.learnPackage = new ArrayList<DataPoint>();
        this.learning = true;
    }

    public void stopLearning() {
        this.learning = false;
    }

    public void setKNNControl(KNNControl c) {
        this.knnControl = c;
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
                double dist = dp.position.distanceTo(other.position);
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
            learnPackage.add(new DataPoint(learnType, new Point(ls.power, (Double) (ls.metaData.get("StdDev")))));
            if (learnPackage.size() > 100) {
                DataPoint avgdp = new DataPoint(learnType, new Point(0, 0));
                for (DataPoint dp : learnPackage) {
                    avgdp.add(dp);
                }
                avgdp.div(learnPackage.size());
                learnPackage.clear();
                data.get(learnType).add(avgdp);
            }
        }



        DataPoint current = new DataPoint("Current", new Point(ls.power, (Double) ls.metaData.get("StdDev")));
        List<Neighbores> neighbores = findNearestNeighbores(current);

        title = ls.sourceNode + "->" + ls.destinationNode;

        if (neighbores.size() >= k) {
            HashMap<String, Integer> counter = new HashMap<String, Integer>();
            for (Neighbores nei : neighbores) {
                if (counter.containsKey(nei.data.Type)) {
                    int tmp = counter.get(nei.data.Type);
                    tmp += 1;
                    counter.put(nei.data.Type, tmp);
                } else {
                    counter.put(nei.data.Type, 1);
                }
            }
            List<Entry<String, Integer>> hits = new ArrayList<Entry<String, Integer>>(counter.entrySet());
            Collections.sort(hits, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    if (o1.getValue() > o2.getValue()) {
                        return -1;
                    } else if (o1.getValue() < o2.getValue()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            ls.metaData.put("KNNState", hits.get(0).getKey());
            ls.metaData.put("KNNConfidence", counter.get(hits.get(0).getKey()));
            title += " (" + hits.get(0).getKey() + " " + ((float) (counter.get(hits.get(0).getKey())) / (float) k) + ")";
        }

        ls.metaData.put("Datapoint", current);
        ls.metaData.put("Neighbores", neighbores);

        if (plotCounter > 50) {
            plot(ls, neighbores);
            plotCounter = 0;
        }
        plotCounter += 1;

        if (this.knnControl != null) {
            knnControl.updateKNN(this);
        }

        super.recvLinkInfo(ls);
    }

    public void startPloting() {
        if (plot == null) {
            plot = new GNUPlot();
            plot.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    plot = null;
                }

                @Override
                public void windowClosed(WindowEvent e) {
                }

                @Override
                public void windowIconified(WindowEvent e) {
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                }

                @Override
                public void windowActivated(WindowEvent e) {
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                }
            });
        }
        plotCounter += 1;


        super.recvLinkInfo(ls);
    }


    private void plot(LinkInfo ls, List<Neighbores> neighbores) {
        if(plot == null) return;
        try {
            String dataString = "";
            String plotString = "set terminal png\n";

            plotString += "set title \"Link " + ls.sourceNode + "->" + ls.destinationNode + "\"\n";

            plotString += "set xrange [-25:15]\n set yrange [0:5]\n";
            plotString += "plot ";
            for (String type : data.keySet()) {
                if (data.get(type).size() > 0) {
                    plotString += "'-' title \"" + type + "\" with circles,";
                    for (DataPoint i : data.get(type)) {
                        dataString += i.position.x + " " + i.position.y + "\n";
                    }
                    dataString += "e\n";
                }
            }


            if (neighbores.size() > 0) {
                plotString += "'-' title \"Neighbores\" with circles fs transparent solid 0.15 noborder,";
                for (Neighbores neighbore : neighbores) {
                    dataString += neighbore.data.position.x + " " + neighbore.data.position.y + "\n";
                }
                dataString += "e\n";
            }

            plotString += "'-' title \"Current\" with circles fs transparent solid 0.15 noborder";
            dataString += ls.power + " " + (Double) ls.metaData.get("StdDev") + "\ne\n";

            plot.plot(plotString + "\n" + dataString);
        } catch (InterruptedException ex) {
            Logger.getLogger(LinkKNN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkKNN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(LinkKNN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class DataPoint {

        public String Type;
        public Point position;

        public DataPoint(String Type, Point position) {
            this.Type = Type;
            this.position = position;
        }

        public void add(DataPoint p2) {
            this.position.add(p2.position);
        }

        public void div(double div) {
            this.position.div(div);
        }
    }

    private static class Point {

        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double distanceTo(Point op) {
            return Math.sqrt(((op.x - x) * (op.x - x)) + ((op.y - y) * (op.y - y)));
        }

        public void add(Point position) {
            this.x += position.x;
            this.y += position.y;
        }

        public void div(double div) {
            this.x /= div;
            this.y /= div;
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
