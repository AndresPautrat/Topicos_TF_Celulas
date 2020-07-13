package frame;

import TF.HostAgent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
    //Paneles
    public static EnvironmentPanel panel_principal;
    public static StatsPanel panel_stats;
    
    protected HostAgent anfitrion;
    
    public MainFrame(HostAgent anfitrion) {
        this.anfitrion = anfitrion;      
        try { inicializar(); }
        catch(Exception e) { e.printStackTrace(); }
    }
    
    private void inicializar() throws Exception {
        //maximizar el frame
        final GraphicsConfiguration config = getGraphicsConfiguration();
        final int left = Toolkit.getDefaultToolkit().getScreenInsets(config).left;
        final int right = Toolkit.getDefaultToolkit().getScreenInsets(config).right;
        final int top = Toolkit.getDefaultToolkit().getScreenInsets(config).top;
        final int bottom = Toolkit.getDefaultToolkit().getScreenInsets(config).bottom;
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = screenSize.width - left - right;
        final int height = screenSize.height - top - bottom;
        setResizable(false);
        setSize(width, height);
        //System.out.println(EnvironmentPanel.MAX_X + "-" + EnvironmentPanel.MAX_Y);
        EnvironmentPanel.MAX_X = width;
        EnvironmentPanel.MAX_Y = height;
        //System.out.println(EnvironmentPanel.MAX_X + "-" + EnvironmentPanel.MAX_Y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        setLayout(new BorderLayout());
        panel_stats = new StatsPanel();
        add(panel_stats, BorderLayout.SOUTH);
        //
        panel_principal = new EnvironmentPanel();
        panel_principal.setBackground(Color.BLUE);
        add(panel_principal, BorderLayout.CENTER);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { }
        });
    }
}