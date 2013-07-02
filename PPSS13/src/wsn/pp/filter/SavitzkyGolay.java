/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.util.Collections;
import java.util.LinkedList;
import mr.go.sgfilter.ContinuousPadder;
import mr.go.sgfilter.MeanValuePadder;
import mr.go.sgfilter.SGFilter;

/**
 *
 * @author Marvin
 *
 * Implementation by http://code.google.com/p/savitzky-golay-filter/
 *
 */
public class SavitzkyGolay extends Filter {

    //private LinkedList<Double> data = new LinkedList<Double>();
    double[] data;
    private int window;
    private LinkInfoReciver lsr;
    int wc = 0;

    public SavitzkyGolay(int window, LinkInfoReciver nextFilter) {
        super(nextFilter);
        this.window = window;
        data = new double[window];

    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        //System.out.println("Recieved "+ls.power);
        if (ls.getSourceNode() == 1 && ls.getDestinationNode() == 4) {
            System.out.println(ls.power);
        }
        shift(data);
        data[data.length - 1] = ls.power;



        double[] coeffs = SGFilter.computeSGCoefficients(window, window, 6);
        double[] leftPad = left(data);
        ContinuousPadder padder1 = new ContinuousPadder();
        SGFilter sgFilter = new SGFilter(5, 5);
        sgFilter.appendPreprocessor(padder1);

        double[] smooth = sgFilter.smooth(data, coeffs);


        double mean = 0, stddev = 0;
        for (Double value : data) {
            mean += value;
        }
        mean = mean / data.length;
        for (Double value : data) {
            stddev += (mean - value) * (mean - value);
        }
        stddev = Math.sqrt(stddev);

        ls.metaData.put("StdDev", stddev);
        ls.power = smooth[smooth.length - 1];

        //if(ls.getSourceNode()==1 && ls.getDestinationNode()==4)
        //System.out.println(ls.power);
        super.recvLinkInfo(ls);
    }

    private void shift(double[] dat) {
        for (int i = 0; i < dat.length - 1; i++) {
            dat[i] = dat[i + 1];
        }

    }

    private double[] left(double[] source) {
        double[] out = new double[source.length / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = source[i];
        }
        return out;
    }

    private double[] right(double[] source) {
        double[] out = new double[source.length / 2 + 1];
        for (int i = source.length / 2; i < source.length; i++) {
            out[i - source.length / 2] = source[i];
        }
        return out;
    }
}
