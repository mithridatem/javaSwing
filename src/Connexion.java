import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connexion extends JDialog{
    private JTextField tfEmail;
    private JLabel jlEmail;
    private JLabel jlPwd;
    private JPasswordField pfPwd;
    private JButton btConnexion;
    private JPanel jpConnexion;

    public Connexion(JFrame parent) {
        super(parent);
        setTitle("Connexion");
        setMinimumSize(new Dimension(300, 300));
        setContentPane(jpConnexion);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
        btConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
}
}
