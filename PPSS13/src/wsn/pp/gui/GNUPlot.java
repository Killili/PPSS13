/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author user
 */
public class GNUPlot extends JFrame implements MouseListener {

    private Process process;
    private OutputStream os;
    private BufferedImage img;
    private boolean isDirty;
    private String lastPlot;
    private boolean isRunning;

    public GNUPlot() throws HeadlessException {
        this.setSize(640, 500);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setVisible(true);
        this.isDirty = false;
        this.isRunning = false;
        this.lastPlot = "";
        this.addMouseListener(this);
    }
    
   
    public void plot(String script) throws InterruptedException, IOException, URISyntaxException {
        lastPlot = script;
        isDirty = true;
        repaint();
    }

    public void graph(String script) throws InterruptedException, IOException, URISyntaxException {
        isDirty = false;
        List<String> command = new ArrayList<String>();

        File gnuplot = new File("gnuplot");
        File wdir = new File(System.getProperty("user.dir"));

        command.add(gnuplot.getPath());

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(wdir);
        builder.redirectErrorStream(false);

        process = builder.start();
        os = process.getOutputStream();
        InputStream is = process.getInputStream();

        os.write(script.getBytes());
        os.flush(); // empty Buffer
        if (img != null) {
            synchronized (img) {
                img = ImageIO.read(is);
            }
        } else {
            img = ImageIO.read(is);
        }
        repaint();
        os.close();
        os = null;
        is.close();
        process.destroy();
        process = null;
        System.out.println("Gnuplot exited!");
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g); //To change body of generated methods, choose Tools | Templates.
        if (isDirty) {
            try {
                graph(lastPlot);
                g.drawImage(img, 0, 25, this);
            } catch (InterruptedException ex) {
                Logger.getLogger(GNUPlot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GNUPlot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(GNUPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(new StringSelection(lastPlot), null);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {       
    }

    @Override
    public void mouseEntered(MouseEvent e) {       
    }

    @Override
    public void mouseExited(MouseEvent e) {       
    }
}
