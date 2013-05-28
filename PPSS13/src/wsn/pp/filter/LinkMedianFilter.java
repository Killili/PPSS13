/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class LinkMedianFilter extends Filter {

    private LinkedList<Double> data = new LinkedList<Double>();
    private int window;
    private LinkInfoReciver lsr;

    public LinkMedianFilter(int window, LinkInfoReciver lsr) {
        super(lsr);
        this.window = window;
        this.lsr = lsr;
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        data.addLast(ls.power);
        if (data.size() >= window) {
            LinkedList<Double> clone = (LinkedList<Double>) data.clone();
            Collections.sort(clone);
            ls = new LinkInfo(ls);
            ls.power = clone.get(window/2);

            data.removeFirst();

            super.recvLinkInfo(ls);
        }
    }
}
