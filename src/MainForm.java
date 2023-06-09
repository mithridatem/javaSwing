import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JDialog{
    private JLabel jlTitre;
    private JPanel jpMain;
    private JLabel jlNom;
    private JTextField tfNom;
    private JLabel jlPrenom;
    private JTextField tfPrenom;
    private JTextField tfEmail;
    private JLabel jlPwd;
    private JPasswordField pfPwd;
    private JButton jbtAdd;
    private JButton jbtCancel;
    private JLabel jlEmail;
    private JPasswordField pfVerify;
    private JLabel jlVerify;
    private JButton btUpdate;

    public MainForm(JFrame parent){
        super(parent);
        setTitle("Gestion des utilisateurs");
        setMinimumSize(new Dimension(500, 500));
        setContentPane(jpMain);
        setLocationRelativeTo(parent);
        setModal(false);
        setVisible(true);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clic sur update");
            }
        });
    }
    public void register(){
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPwd.getPassword());
        String verify = String.valueOf(pfVerify.getPassword());
        //vérification si les champs ne sont pas remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs du formulaire",
                    "Erreur Formulaire",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Vérification si les passwords ne correspondent pas
        if(!password.equals(verify)){
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correpondent pas",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        //créer un nouvel objet user
        User user = new User(nom,prenom,email,password);
        //tester si le compte existe
        if(Request.getUserByMail(user)!=null){
            JOptionPane.showMessageDialog(this,
                    "Le compte existe deja",
                    "Erreur BDD",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        //ajout de l'utilisateur en BDD
        User userAdd = Request.addUser(user);
        JOptionPane.showMessageDialog(this,
                "Le compte "+userAdd.getNom()+" a été ajouté en BDD",
                "Validation",
                JOptionPane.INFORMATION_MESSAGE);
        return;
    }
}
