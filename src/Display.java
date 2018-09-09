package display;
import javax.swing.*;
import java.awt.*;

public class Display {

    private int height,width;
    public JFrame frame;
    private String title;
    public Canvas canvas;

    public Display(int height, int width, String title) {
        this.height = height;
        this.width = width;
        this.title = title;
        createDisplay();
    }

    public void createDisplay()
    {
        frame= new JFrame(title);
      frame.setSize(height,width);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      canvas=new Canvas();
      canvas.setPreferredSize(new Dimension(height,width));
      canvas.setBackground(new Color(180, 142, 119));
      canvas.setFocusable(false);
      frame.add(canvas);
      frame.pack();







    }
}
