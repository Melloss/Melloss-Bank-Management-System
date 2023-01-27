import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

abstract class ManagerAccountInfo {
    private String managerName;
    private String managerPassword;
    public void setManagerName(String n){
        this.managerName = n;
    }
    public void setManagerPassword(String p){
        this.managerPassword = p;
    }
    public String getManagerName(){
        return managerName;
    }
    public String getManagerPassword(){
        return managerPassword;
    }
}
public class Manager_Account extends ManagerAccountInfo{
    static ManagerAccountInfo account = new Manager_Account();
    public static void getReady(){
        ArrayList<String> accountInfo = new ArrayList<String>();
        if(account.getManagerName() == null){
            try{
                File f = new File("Accounts/Manager_Account.txt");
                Scanner s = new Scanner(f);
                if(f.exists()){
                    while(s.hasNext()){
                        accountInfo.add(s.nextLine());
                    }
                }
                else System.out.println("-> The file does not exist!");
                account.setManagerName(accountInfo.get(0));
                account.setManagerPassword(accountInfo.get(1));
            }
            catch (FileNotFoundException e){
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
    public static void createAccount(ManagerAccountInfo a){
        String name = a.getManagerName();
        String psw = a.getManagerPassword();
        try{
                FileWriter f = new FileWriter("Accounts/Manager_Account.txt");
                f.write(name+"\n");
                f.write(psw);
                f.close();
        }
        catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public static boolean isManager(String name ,String psw){
        getReady();
            if(name.equalsIgnoreCase(account.getManagerName()) && psw.equals(account.getManagerPassword())){
                return true;
            }
            else return false;
    }
    public static boolean isThereManager(){
        File f = new File("Accounts/Manager_Account.txt");
        if(f.exists() && f.length() > 0){
            return true;
        }
        else return false;
    }
}
class CreateManagerAccount extends javax.swing.JFrame {

    public CreateManagerAccount() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        FullNameInput = new javax.swing.JTextField();
        psw1Input = new javax.swing.JPasswordField();
        psw2Input = new javax.swing.JPasswordField();
        header = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("Full Name: ");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Password: ");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Rewrite Password: ");

        FullNameInput.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        FullNameInput.setForeground(new java.awt.Color(0, 51, 51));
        FullNameInput.setMargin(new java.awt.Insets(5, 10, 5, 5));

        psw1Input.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        psw1Input.setForeground(new java.awt.Color(0, 51, 51));
        psw1Input.setMargin(new java.awt.Insets(5, 10, 5, 5));

        psw2Input.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        psw2Input.setForeground(new java.awt.Color(0, 51, 51));
        psw2Input.setMargin(new java.awt.Insets(5, 10, 5, 5));
        psw2Input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psw2InputActionPerformed(evt);
            }
        });

        header.setFont(new java.awt.Font("Lohit Devanagari", 1, 25)); // NOI18N
        header.setForeground(new java.awt.Color(51, 51, 255));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Create Manager Account");

        saveButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        saveButton.setForeground(new java.awt.Color(51, 51, 255));
        saveButton.setText("Save");
        saveButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(saveButton)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(FullNameInput)
                                                                .addComponent(psw1Input)
                                                                .addComponent(psw2Input, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 38, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(FullNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(psw1Input, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(psw2Input, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(saveButton)
                                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String name = FullNameInput.getText();
        String p1 = psw1Input.getText();
        String p2 = psw2Input.getText();
        ManagerAccountInfo ma = new Manager_Account();
        if(p1.equals(p2)) {
            ma.setManagerName(name);
            ma.setManagerPassword(p1);
            Manager_Account.createAccount(ma);
            LoginPage l = new LoginPage();
            l.setLocationRelativeTo(null);
            l.setVisible(true);
            l.setResizable(false);
            dispose();
        }
        else JOptionPane.showMessageDialog(new CreateManagerAccount(),"Password does not match!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
    }

    private void psw2InputActionPerformed(java.awt.event.ActionEvent evt) {
        saveButtonActionPerformed(evt);
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField FullNameInput;
    private javax.swing.JLabel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField psw1Input;
    private javax.swing.JPasswordField psw2Input;
    private javax.swing.JButton saveButton;
    // End of variables declaration
}
