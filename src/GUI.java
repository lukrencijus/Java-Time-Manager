import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class GUI implements ActionListener{
    private
        int count = 0;
        JLabel label;
        JFrame frame;
        JButton button;
        JPanel panel;
    public GUI(){
        frame = new JFrame();

        button = new JButton("Click me");
        button.addActionListener(this);

        label = new JLabel("Number of clicks: 0", JLabel.CENTER);
        label.setPreferredSize(new Dimension(200, label.getPreferredSize().height));
        
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 30, 100));
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.NORTH);
        panel.add(label, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Surely safe software");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);
        if(count == 9){
            label.setText("Hello world");
        }
        if(count > 15){
            label.setText("Okay that is enough");
        }
    }
}
