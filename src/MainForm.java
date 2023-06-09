import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
    private JButton btUpdate;
    private JLabel jlVerify;
    private JButton btShow;
    private JTextPane tpShowUsers;
    private static boolean state = false;

    public MainForm(JFrame parent){
        super(parent);
        setTitle("Gestion des utilisateurs");
        setMinimumSize(new Dimension(500, 500));
        setContentPane(jpMain);
        //on passe la textPane en visible false au chargement
        tpShowUsers.setVisible(state);
        jlTitre.setText("Gestion des utilisateurs");
        setLocationRelativeTo(parent);
        setModal(false);
        setVisible(true);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jlTitre.setText("Ajouter un compte utilisateur");
                state = false;
                tpShowUsers.setVisible(state);
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
                jlTitre.setText("Modifier un compte utilisateur");
                state = false;
                tpShowUsers.setVisible(state);
                updateUser();
            }
        });
        btShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jlTitre.setText("Afficher les utilisateurs");
                if(!state){
                    state = true;
                }
                else{
                    state = false;
                }
                //gérer l'affichage du composant tpShowUsers
                tpShowUsers.setVisible(state);
                //récupération de la liste des utilisateurs
                getAllUsers();
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
    public void updateUser(){
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
        User newUser = new User("","",email,"");
        User getUser = Request.getUserByMail(newUser);
        //tester si le compte existe
        if(getUser!=null){
            Request.updateUser(getUser, nom,prenom,getUser.getEmail(), password);
            JOptionPane.showMessageDialog(this,
                    "Le compte à été mis à jour en BDD",
                    "Validation",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //le compte n'existe pas
        else{
            JOptionPane.showMessageDialog(this,
                    "Le compte n'existe pas",
                    "Erreur BDD",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    public void getAllUsers(){
        //récupération de la liste des utilisateurs
        List<User> allUsers = Request.getAllUser();
        //chaine à afficher dans tpShowUsers
        String listeUsers = "";
        //Si on a des enregistrements en BDD
        if(allUsers.size()>0){
            //construire la liste des utilisateurs
            for (User user:allUsers) {
                listeUsers += user.getId()+", "+user.getNom()+", "+user.getEmail()+"\n";
            }
            tpShowUsers.setForeground(Color.BLACK);
            tpShowUsers.setText(listeUsers);
        }
        //Si on à aucun enregistrement en BDD
        if (allUsers.size()==0){
            listeUsers = "La liste est vide veuillez ajouter des utilisateurs";
            tpShowUsers.setForeground(Color.RED);
            tpShowUsers.setText(listeUsers);
        }
    }
}
