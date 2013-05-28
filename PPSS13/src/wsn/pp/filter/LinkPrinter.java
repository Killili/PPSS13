package wsn.pp.filter;


import java.util.logging.Level;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class LinkPrinter extends Filter {

    public LinkPrinter(LinkInfoReciver nextFilter) {
        super(nextFilter);
    }

    public void recvLinkInfo(LinkInfo ls) {
        java.util.logging.Logger.getLogger(LinkFilter.class.getName()).log(Level.INFO, String.format("Link source %d dest %d pow %f", ls.sourceNode,ls.destinationNode,ls.power));
        super.recvLinkInfo(ls);
    }
    
}
