package wsn.pp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame implements Runnable {

    private JPanel displayPanel;
    private byte[][] nodeRSSI;
    private Point[] nodes;
    private double[] timeout;
    private BufferedImage buf;
    private Graphics2D bg;
    //Graphics2D g;
    private int updateTime = 100;

    public View() {
        super();
        initComponent();
    }

    private void initComponent() {
        this.setSize(500, 500);

        displayPanel = new JPanel();
        displayPanel.setSize(500, 400);
        displayPanel.setBackground(Color.white);
        this.add(displayPanel);
        this.setResizable(false);
        this.setTitle("Detection");

        buf = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_4BYTE_ABGR);
        bg = (Graphics2D) buf.getGraphics();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Thread t = new Thread(this);
        t.start();
        //g = (Graphics2D) displayPanel.getGraphics();
    }

    public void initNodes(Point[] nodePositions) {
        nodes = nodePositions;
        nodeRSSI = new byte[nodePositions.length][nodePositions.length];
        timeout = new double[nodePositions.length];
        this.repaint();
    }

    public void updateRssi(int id, byte[] bs) {
        nodeRSSI[id - 1] = bs;
        timeout[id - 1] = 0;
        //this.repaint();
    }

    @Override
    public void paint(Graphics g) {      
        //super.paint(g);
        drawNodes(g);

    }

    private void drawNodes(Graphics gs) {
        //bg.setColor(Color.white);
        //bg.fillRect(0, 0, gs.get, 5000);

        for (int i = 0; i < timeout.length; i++) {
            timeout[i]++;
        }

        if (nodes == null) {
            return;
        }

        int id = 0;
        for (Point p : nodes) {
            int i = 0;

            for (byte d : nodeRSSI[id]) {
                if (timeout[id] > 20) {
                    bg.setColor(Color.DARK_GRAY);
                } else {
                    bg.setColor(Color.green);
                }

                if (nodes[i].equals(p)) {
                    continue;
                }


                bg.drawLine(p.x, p.y, nodes[i].x, nodes[i].y);
                Point mid = getMid(p, nodes[i]);
                bg.setColor(Color.black);

                bg.drawString("Rssi " + d, mid.x, mid.y);
                i++;
            }
            if (timeout[id] > 20) {
                bg.setColor(Color.red);
            } else {
                bg.setColor(Color.green);
            }

            bg.fillOval(p.x - 4, p.y - 4, 8, 8);
            bg.setColor(Color.black);
            bg.drawString("node " + (id + 1), p.x, p.y);
            id++;

        }
        //gs.clearRect(0, 0, 5000, 5000);
        gs.drawImage(buf, 0, 25, null);
    }

    private Point getMid(Point a, Point b) {
        Point result = new Point(a);
        result.translate((b.x - a.x) / 2, (b.y - a.y) / 2);
        return result;
    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
            try {
                Thread.sleep(updateTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
