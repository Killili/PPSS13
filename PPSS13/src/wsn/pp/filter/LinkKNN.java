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
import wsn.pp.data.ScienceTool;
import wsn.pp.gui.GNUPlot;
import wsn.pp.gui.KNNControl;

/**
 *
 * @author Killi
 */
public class LinkKNN extends Filter implements Plotable {

    private String learnType;
    private Map<String, List<DataPoint>> data;
    private Map<String, Float> typeWeights;
    private List<DataPoint> learnPackage;
    private boolean learning;
    private int k;
    private GNUPlot plot;
    private String title = "NotSet";
    private KNNControl knnControl;
    private String testingType;
    private int testingScore;
    private int testingPoints;
    private float missfirePoints;
    private Map<String, Float> missfireWeights;
    private int totalPointsForMissfireRating;
    private LinkInfo lastLinkInfo;

    @Override
    public String toString() {
        return this.title;
    }

    public LinkKNN(int k, LinkInfoReciver nextFilter) {
        super(nextFilter);
        this.k = k;
        this.data = new HashMap<String, List<DataPoint>>();
        this.typeWeights = new HashMap<String, Float>();
        this.missfireWeights = new HashMap<String, Float>();
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
            if (learnPackage.size() > ScienceTool.getParameter("KnnWindow")) {
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
        if(ScienceTool._SCIENCE)
            k = (int)ScienceTool.getParameter("knn");
        List<Neighbores> neighbores = findNearestNeighbores(current);

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

            String estimatedType = hits.get(0).getKey();
            float confidence = (float) (counter.get(hits.get(0).getKey())) / (float) k;
            ls.metaData.put("KNNState", estimatedType);
            ls.metaData.put("KNNConfidence", confidence);


            if (testingType != null) {
                totalPointsForMissfireRating += 1;
                if (estimatedType.equals(testingType)) {
                    testingScore += 1;
                    if (!missfireWeights.containsKey(testingType)){
                        missfireWeights.put(testingType, 0f);
                    }
                } else {
                    missfirePoints += 1;
                    if (missfireWeights.containsKey(testingType)) {
                        missfireWeights.put(testingType, missfireWeights.get(testingType) + 1);
                    } else {
                        missfireWeights.put(testingType, 1f);
                    }
                }
                testingPoints += 1;
            } else {
                title += "\t w: " + typeWeights.get(estimatedType) + "\t mf: " + missfireWeights.get(estimatedType);
            }
            title = String.format("%d->%d %12s c:%.2f w:%.2f ( m:%4.0f mr:%5.0f dp:%5d )", ls.sourceNode, ls.destinationNode, estimatedType, confidence, typeWeights.get(estimatedType), missfireWeights.get(estimatedType), missfirePoints, totalPointsForMissfireRating);

            ls.metaData.put("KNNStateWeight", typeWeights.get(estimatedType));
            ls.metaData.put("KNNStateMissfired", missfireWeights.get(estimatedType));
            ls.metaData.put("KNNTotalMissfired", missfirePoints);

        } else {
            title = String.format("%d->%d", ls.sourceNode, ls.destinationNode);
        }

        ls.metaData.put("Datapoint", current);
        ls.metaData.put("Neighbores", neighbores);

        lastLinkInfo = ls;

        if (this.knnControl != null) {
            knnControl.updateKNN(this);
        }
        
        if(ScienceTool._SCIENCE)
            ScienceTool.addLinkInfor(ls);
        super.recvLinkInfo(ls);
    }

    public void startPloting() { 
        plot = new GNUPlot(this);
    }

    @Override
    public String getPlotString() {
        String dataString = "";
        String plotString = "set terminal png\n";

        plotString += "set title \"Link " + lastLinkInfo.sourceNode + "->" + lastLinkInfo.destinationNode + "\"\n";

        plotString += "set xrange [-25:15]\n set yrange [0:10]\n";
        plotString += "plot ";
        for (String type : data.keySet()) {
            if (data.get(type).size() > 0) {
                plotString += "'-' title \"" + type + "\" with circles,";
                for (DataPoint i : data.get(type)) {
                    dataString += i.position.x + " " + i.position.y + " 0.2\n";
                }
                dataString += "e\n";
            }
        }

        List<Neighbores> neighbores = (List<Neighbores>)lastLinkInfo.getMetaData().get("Neighbores");
        if (neighbores.size() > 0) {
            plotString += "'-' title \"Neighbores\" with circles fs solid,";
            for (Neighbores neighbore : neighbores) {
                dataString += neighbore.data.position.x + " " + neighbore.data.position.y + " 0.2\n";
            }
            dataString += "e\n";
        }

        plotString += "'-' title \"Current\" with circles fs solid";
        dataString += lastLinkInfo.power + " " + (Double) lastLinkInfo.metaData.get("StdDev") + " 0.2\ne\n";

        return plotString + "\n" + dataString;

    }

    public void testType(String type) {
        testingType = type;
        testingScore = 0;
        testingPoints = 0;
    }

    public void stopTesting() {
        typeWeights.put(testingType, (float) testingScore / (float) testingPoints);
        testingType = null;
    }

    public static class DataPoint {

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

        @Override
        public String toString() {
            return Type+","+position;
        }
        
        
    }

    public static class Point {

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

        @Override
        public String toString() {
        return x+","+y;
        }
        
        
    }

    public static class Neighbores implements Comparable<Neighbores> {

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

        @Override
        public String toString() {
            return data.toString() +","+distance;
        }
        
        
    }
}
