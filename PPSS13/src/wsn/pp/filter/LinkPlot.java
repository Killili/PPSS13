/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import wsn.pp.gui.GNUPlot;

/**
 *
 * @author user
 */
public class LinkPlot extends Filter {

    private final List<LinkInfo> data;
    private final GNUPlot plot;
    private final String title;

    public LinkPlot(String title, LinkInfoReciver lsr) {
        super(lsr);
        this.title = title;
        data = new ArrayList<LinkInfo>();
        plot = new GNUPlot();
    }

    public LinkPlot(String title) {
        this(title, null);
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        try {
            data.add(ls);
            String dataString = "";
            for (LinkInfo i : data) {
                dataString += i.timestamp + " " + i.power + "\n";
            }
            plot.plot("set terminal png\n plot '-' title \"" + this.title + "( " + ls.sourceNode + " -> " + ls.destinationNode + " )\" with linespoints\n" + dataString + "e\nquit\n");


        } catch (InterruptedException ex) {
            Logger.getLogger(LinkPlot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkPlot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(LinkPlot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
