/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import wsn.pp.gui.GNUPlot;

/**
 *
 * @author user
 */
public class LinkPlot extends Filter implements Plotable {

    private final List<LinkInfo> data;
    private final GNUPlot plot;
    private final String title;
    public static int miny = -20;
    public static int maxy = 20;
    private int sourceNode;
    private int destinationNode;

    public LinkPlot(String title, final Object lf, LinkInfoReciver lsr) {
        super(lsr);
        this.title = title;
        data = new ArrayList<LinkInfo>();

        plot = new GNUPlot(this);
        plot.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if( lf instanceof LinkFilter){
                    ((LinkFilter)lf).removeFilter(LinkPlot.this);
                } else if (lf instanceof Filter) {
                    ((Filter)lf).removeFilter(LinkPlot.this);
                }
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

    public LinkPlot(String title, Object lf) {
        this(title,lf, null);
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        synchronized(data){
            data.add(ls);
        }
        sourceNode = ls.getSourceNode();
        destinationNode = ls.getDestinationNode();
    }

    @Override
    public String getPlotString() {
        String dataString = "";
        synchronized(data){
            for (LinkInfo i : data) {
                dataString += i.timestamp + " " + i.power + "\n";
            }
        }
        return "set terminal png\n set yrange [" + maxy + ":" + miny + "] \n plot '-' title \"" + this.title + "( " + sourceNode + " -> " + destinationNode + " )\" with linespoints\n" + dataString + "e\nquit\n";

    }
}
