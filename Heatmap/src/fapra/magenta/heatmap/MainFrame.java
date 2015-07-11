package fapra.magenta.heatmap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import fapra.magenta.heatmap.data.CombinedRow;
import fapra.magenta.heatmap.data.Filter;

import javax.swing.JLabel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import lib.HeatMap;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

    protected BufferedImage dimg = null;
    
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(794, 960);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        final JLabel lblNewLabel = new JLabel("");
        JScrollPane jsp = new JScrollPane(lblNewLabel);
        getContentPane().add(jsp, BorderLayout.CENTER);
        lblNewLabel.setSize(CombinedRow.pixelX/2, CombinedRow.pixelY/2);
        
        JPanel Configuration = new JPanel();
        getContentPane().add(Configuration, BorderLayout.NORTH);

        JLabel lblStartgrid = new JLabel("startGrid");
        Configuration.add(lblStartgrid);

        txtGridx = new JTextField();
        txtGridx.setText("1");
        Configuration.add(txtGridx);
        txtGridx.setColumns(10);

        txtGridy = new JTextField();
        txtGridy.setText("1");
        Configuration.add(txtGridy);
        txtGridy.setColumns(10);
        
        JButton btnRedraw = new JButton("Redraw");
        btnRedraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                float multiplier = ((float) slider.getValue()) / 10.0f;
                List<CombinedRow> filteredRows = Filter.queryRows(
                        Integer.parseInt(txtGridx.getText()),
                        Integer.parseInt(txtGridy.getText()),
                        Integer.parseInt(txtEndgridx.getText()),
                        Integer.parseInt(txtEndgridy.getText()));
                System.out.println("Multiplier=" + multiplier);
                Filter.normalizeRows(filteredRows);
                List<Point> convertedRows = Filter.convertPointsInRow(filteredRows);
                System.out.println("Rows=" + convertedRows.size());
                HeatMap hm = new HeatMap(convertedRows, CombinedRow.pixelX, CombinedRow.pixelY);
                BufferedImage img = hm.createHeatMap(multiplier, HeatMap.GRADIENT_HEAT_COLORS);

                
                List<fapra.magenta.heatmap.data.Point> startEndList = new ArrayList<fapra.magenta.heatmap.data.Point>();
                fapra.magenta.heatmap.data.Point normStart = new fapra.magenta.heatmap.data.Point(-1, -1, CombinedRow.refManager.targetToPxX(Integer.parseInt(txtGridx.getText())),
                        CombinedRow.refManager.targetToPxY(Integer.parseInt(txtGridy.getText())));
                fapra.magenta.heatmap.data.Point normEnd = new fapra.magenta.heatmap.data.Point(-1, -1, CombinedRow.refManager.targetToPxX(Integer.parseInt(txtEndgridx.getText())),
                        CombinedRow.refManager.targetToPxY(Integer.parseInt(txtEndgridy.getText())));
                startEndList.add(normStart);
                startEndList.add(normEnd);
                List<Point> psStartEnd = Filter.convertPoints(startEndList);
                
                // Draw real Line
                Graphics2D g2d = img.createGraphics();
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(10));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawLine(psStartEnd.get(0).x, psStartEnd.get(0).y, psStartEnd.get(1).x, psStartEnd.get(1).y);
                g2d.dispose();
                
                
                dimg = toBufferedImage(img.getScaledInstance(CombinedRow.pixelX/2, CombinedRow.pixelY/2,
                        Image.SCALE_SMOOTH));
                ImageIcon icon = new ImageIcon(dimg);
                lblNewLabel.setIcon(icon);
                //lblHeatmap.invalidate();
                
            }
        });

        JButton btnSave = new JButton("Save as");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (dimg == null) {
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    String filePath = f.getPath();
                    if (!filePath.toLowerCase().endsWith(".png")) {
                        f = new File(filePath + ".png");
                    }
                    try {
                        ImageIO.write(dimg, "png", f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        JLabel lblEndgrid = new JLabel("endGrid");
        Configuration.add(lblEndgrid);

        txtEndgridx = new JTextField();
        txtEndgridx.setText("0");
        Configuration.add(txtEndgridx);
        txtEndgridx.setColumns(10);

        txtEndgridy = new JTextField();
        txtEndgridy.setText("5");
        Configuration.add(txtEndgridy);
        txtEndgridy.setColumns(10);
        
        JLabel lblDirection = new JLabel("direction");
        Configuration.add(lblDirection);
        
        txtDir = new JTextField();
        txtDir.setText("3");
        Configuration.add(txtDir);
        txtDir.setColumns(10);
        Configuration.add(btnRedraw);
        Configuration.add(btnSave);
        
        panel = new JPanel();
        getContentPane().add(panel, BorderLayout.WEST);
        panel.setLayout(new BorderLayout(0, 0));
        
        slider = new JSlider();
        panel.add(slider);
        slider.setMinorTickSpacing(10);
        slider.setMaximum(50);
        slider.setValue(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setOrientation(SwingConstants.VERTICAL);
        
        lblValue = new JLabel("value");
        panel.add(lblValue, BorderLayout.NORTH);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lblValue.setText("" + (slider.getValue() / 10.0f));
            }
        });
        lblValue.setText("" + (slider.getValue() / 10.0f));
        
        pack();
    }

    private static final long serialVersionUID = 2269971701250845501L;
    private JTextField txtGridx;
    private JTextField txtGridy;
    private JTextField txtEndgridx;
    private JTextField txtEndgridy;
    private JTextField txtDir;
    private JSlider slider;
    private JPanel panel;
    private JLabel lblValue;

    public JSlider getSlider() {
        return slider;
    }
    
    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
