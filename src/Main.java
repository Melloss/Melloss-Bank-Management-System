import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        Menu m = new Menu();
        m.mainMenu();
    }
}
class Menu{
    public static void mainMenu(){
        Manager_Account ma = new Manager_Account();
        if(ma.isThereManager() == false){
            CreateManagerAccount a = new CreateManagerAccount();
            a.pack();
            a.setLocationRelativeTo(null);
            a.setVisible(true);
            a.setResizable(false);
        }
        else {
            LoginPage l = new LoginPage();
            l.setLocationRelativeTo(null);
            l.setVisible(true);
            l.setResizable(false);
        }
    }
}
class LoginPage extends javax.swing.JFrame {

    public LoginPage() {
        setTitle("Melloss Bank");
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        LoginList = new javax.swing.JList<>();
        welcomeLabel = new javax.swing.JLabel();
        selectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(java.awt.Color.white);

        LoginList.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        LoginList.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        LoginList.setForeground(new java.awt.Color(0, 51, 51));
        LoginList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Login As Manager", "Login As User\t", "Exit" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        LoginList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LoginList.setSelectedIndex(0);
        LoginList.setSelectionBackground(new java.awt.Color(0, 153, 255));
        LoginList.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(LoginList);

        welcomeLabel.setFont(new java.awt.Font("FreeSans", 0, 19)); // NOI18N
        welcomeLabel.setForeground(new java.awt.Color(0, 0, 204));
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setText("<html> <body>Welcome to Melloss Banking Management System!<br>Choose one of the following Login Type</body> </html>");
        welcomeLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        selectButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        selectButton.setForeground(new java.awt.Color(0, 51, 255));
        selectButton.setText("Select");
        selectButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(welcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(186, 186, 186)
                                .addComponent(selectButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(selectButton)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        welcomeLabel.getAccessibleContext().setAccessibleName("Welcome to Melloss Banking Management System.\n\nChoose one of the following");
        welcomeLabel.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(LoginList.getSelectedIndex() == 0){
            ManagerLoginPanel m = new ManagerLoginPanel();
            m.setVisible(true);
            m.setResizable(false);
            m.setLocationRelativeTo(null);
            dispose();
        }
        else if(LoginList.getSelectedIndex()==1){
            UserLoginPanel u = new UserLoginPanel();
            u.setVisible(true);
            u.setResizable(false);
            u.setLocationRelativeTo(null);
            dispose();
        }
        else dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JList<String> LoginList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton selectButton;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration
}
class ManagerLoginPanel extends javax.swing.JFrame {
    public ManagerLoginPanel() {
        setTitle("Melloss Bank");
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        header = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        passwordInput = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        header.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        header.setForeground(new java.awt.Color(0, 51, 204));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Manager Login");

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("Name:");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setText("Password: ");

        nameInput.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        nameInput.setMargin(new java.awt.Insets(0, 10, 0, 5));

        passwordInput.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        passwordInput.setMargin(new java.awt.Insets(0, 10, 0, 5));

        jButton1.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 255));
        jButton1.setText("Login");
        jButton1.setMargin(new java.awt.Insets(5, 19, 5, 19));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(31, 31, 31)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(nameInput)
                                                                        .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(156, 156, 156)
                                                                .addComponent(jButton1)))
                                                .addGap(0, 68, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addComponent(jButton1)
                                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String name = nameInput.getText();
        String psw = passwordInput.getText();
        if(Manager_Account.isManager(name,psw)== true){
            dispose();
            ManagerMenu mm = new ManagerMenu();
            mm.setVisible(true);
            mm.setResizable(false);
        }
        else JOptionPane.showMessageDialog(null,"Wrong Manager Name or Password!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nameInput;
    private javax.swing.JPasswordField passwordInput;
    // End of variables declaration
}
class UserLoginPanel extends javax.swing.JFrame {

    public UserLoginPanel() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        header = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        accountInput = new javax.swing.JTextField();
        pincodeInput = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        header.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        header.setForeground(new java.awt.Color(0, 51, 204));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("User Account Login");

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("Account No. ");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setText("Pin Code: ");

        accountInput.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        accountInput.setMargin(new java.awt.Insets(0, 10, 0, 5));

        pincodeInput.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        pincodeInput.setMargin(new java.awt.Insets(0, 10, 0, 5));

        loginButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        loginButton.setForeground(new java.awt.Color(0, 51, 255));
        loginButton.setText("Login");
        loginButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 47, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(accountInput)
                                        .addComponent(pincodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(loginButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(accountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(pincodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addComponent(loginButton)
                                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String accountNumber = accountInput.getText();
        String pincode = pincodeInput.getText();
        try{
            if(User_Account.isThereUserBy(Long.parseLong(accountNumber),Integer.parseInt(pincode))){
                setAccountNumber(Long.parseLong(accountNumber));
                dispose();
                UserAccountMenu am = new UserAccountMenu();
                am.setVisible(true);
                am.setResizable(false);
            }
            else JOptionPane.showMessageDialog(null,"Wrong Account Number or PinCode!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        }
        catch (java.lang.NumberFormatException e){
            JOptionPane.showMessageDialog(null,"You Entered Character Not Number!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setAccountNumber(long a){
        this.loginAccountNumber = a;
    }
    public static long getAccountNumber(){
        return loginAccountNumber;
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField accountInput;
    private javax.swing.JLabel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField pincodeInput;
    private static long loginAccountNumber;
}
class ManagerMenu extends javax.swing.JFrame {

    public ManagerMenu() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        selectButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        logoutButton = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        quitButton = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        textSizeButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Manager Account Option");

        jList1.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        jList1.setForeground(new java.awt.Color(51, 51, 51));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Create User Account", "Withdraw Balance", "Save Balance", "Show All Accounts", "Search By Account Number", "Search By Name", "Maximum Balance Account", "Minimum Balance Account", "Delete Account" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectedIndex(0);
        jList1.setSelectionBackground(new java.awt.Color(0, 204, 255));
        jList1.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jList1);

        selectButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        selectButton.setForeground(new java.awt.Color(0, 0, 153));
        selectButton.setText("Select");
        selectButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        jMenu1.setForeground(new java.awt.Color(0, 51, 51));
        jMenu1.setText("<html><body><u>F</u>ile</body></html>");
        jMenu1.setFont(new java.awt.Font("Liberation Sans", 0, 17));

        logoutButton.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(0, 51, 51));
        logoutButton.setText("Log Out");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        jMenu1.add(logoutButton);
        jMenu1.add(jSeparator1);

        quitButton.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        quitButton.setForeground(new java.awt.Color(0, 51, 51));
        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });
        jMenu1.add(quitButton);

        jMenuBar1.add(jMenu1);

        jMenu2.setForeground(new java.awt.Color(0, 51, 51));
        jMenu2.setText("<html><body><u>E</u>dit</body></html>");
        jMenu2.setFont(new java.awt.Font("Liberation Sans", 0, 17));

        textSizeButton.setText("Text Size");
        textSizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSizeButtonActionPerformed(evt);
            }
        });
        jMenu2.add(textSizeButton);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(142, 142, 142))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(255, 255, 255)
                                .addComponent(selectButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(selectButton)
                                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(jList1.getSelectedIndex() == 0){
            CreateUserAccount ua = new CreateUserAccount();
            ua.setVisible(true);
            ua.setResizable(false);
            ua.setLocationRelativeTo(null);
        }
        else if(jList1.getSelectedIndex() == 1){
            WithdrawBalance w = new WithdrawBalance();
            w.setVisible(true);
            w.setResizable(false);
        }
        else if(jList1.getSelectedIndex() == 2){
            SaveBalance s = new SaveBalance();
            s.setVisible(true);
            s.setResizable(false);
        }
        else if(jList1.getSelectedIndex() == 3){
            User_Account.displayAllAccounts();
        }
        else if(jList1.getSelectedIndex() == 4){
            SearchByAccountNumber sa = new SearchByAccountNumber();
            sa.setVisible(true);
            setResizable(false);
        }
        else if(jList1.getSelectedIndex() == 5){
            SearchByName sn = new SearchByName();
            sn.setVisible(true);
            sn.setResizable(false);
        }
        else if(jList1.getSelectedIndex() == 6){
            MaximumBalance mb = new MaximumBalance();
            mb.setResizable(false);
            mb.setVisible(true);
        }
        else if(jList1.getSelectedIndex() == 7){
            MinimumBalance mb = new MinimumBalance();
            mb.setResizable(false);
            mb.setVisible(true);
        }
        else if(jList1.getSelectedIndex() == 8){
            DeleteAccount da = new DeleteAccount();
            da.setResizable(false);
            da.setVisible(true);
            da.setLocationRelativeTo(null);
            da.pack();
        }
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LoginPage l = new LoginPage();
        l.setVisible(true);
        l.setResizable(false);
        dispose();
    }

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(1);
    }

    private void textSizeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String s = JOptionPane.showInputDialog(new UserAccountMenu(),"Enter Font Size");
        jList1.setFont(new java.awt.Font("Liberation Sans", 0, Integer.parseInt(s)));
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem logoutButton;
    private javax.swing.JMenuItem quitButton;
    private javax.swing.JButton selectButton;
    private javax.swing.JMenuItem textSizeButton;
    // End of variables declaration
}
class UserAccountMenu extends javax.swing.JFrame {

    public UserAccountMenu() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userOptionList = new javax.swing.JList<>();
        selectButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        logoutButton = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        quitButton = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        textSizeButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("User Account Option");

        userOptionList.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        userOptionList.setForeground(new java.awt.Color(51, 51, 51));
        userOptionList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Show Balance", "Transfer Balance", "Show Transaction History", "Buy Airtime", "Change Pincode" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        userOptionList.setSelectedIndex(0);
        userOptionList.setSelectionBackground(new java.awt.Color(0, 204, 255));
        userOptionList.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(userOptionList);

        selectButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        selectButton.setForeground(new java.awt.Color(0, 0, 153));
        selectButton.setText("Select");
        selectButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        jMenu1.setForeground(new java.awt.Color(0, 51, 51));
        jMenu1.setText("<html><body><u>F</u>ile</body></html>");
        jMenu1.setFont(new java.awt.Font("Liberation Sans", 0, 17));

        logoutButton.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(0, 51, 51));
        logoutButton.setText("Log Out");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        jMenu1.add(logoutButton);
        jMenu1.add(jSeparator1);

        quitButton.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        quitButton.setForeground(new java.awt.Color(0, 51, 51));
        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });
        jMenu1.add(quitButton);

        jMenuBar1.add(jMenu1);

        jMenu2.setForeground(new java.awt.Color(0, 51, 51));
        jMenu2.setText("<html><body><u>E</u>dit</body></html>");
        jMenu2.setFont(new java.awt.Font("Liberation Sans", 0, 17));

        textSizeButton.setText("Text Size");
        textSizeButton.setFont(new java.awt.Font("Liberation Sans", 0, 17));
        textSizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSizeButtonActionPerformed(evt);
            }
        });
        jMenu2.add(textSizeButton);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(142, 142, 142))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(selectButton)
                                                .addGap(257, 257, 257))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(selectButton)
                                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(userOptionList.getSelectedIndex() == 0){
            ShowBalance sb = new ShowBalance();
            sb.setVisible(true);
            sb.setResizable(false);
            sb.setLocationRelativeTo(null);
        }
        else if(userOptionList.getSelectedIndex() == 1){
            TransferBalance t = new TransferBalance();
            t.setVisible(true);
            t.setResizable(false);
            t.setLocationRelativeTo(null);
        }
        else if(userOptionList.getSelectedIndex() == 2){
            ShowTransaction t = new ShowTransaction();
            t.setVisible(true);
            t.setLocationRelativeTo(null);
        }
        else if(userOptionList.getSelectedIndex() == 3){
            BuyAirtime w = new BuyAirtime();
            w.setVisible(true);
            w.setResizable(false);
            w.setLocationRelativeTo(null);
        }
        else if(userOptionList.getSelectedIndex() == 4){
            ChangePinCode p = new ChangePinCode();
            p.setVisible(true);
            p.setResizable(false);
            p.setLocationRelativeTo(null);
        }
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LoginPage l = new LoginPage();
        l.setVisible(true);
        l.setResizable(false);
        dispose();
    }

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(1);
    }

    private void textSizeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String s = JOptionPane.showInputDialog(new UserAccountMenu(),"Enter Font Size");
        userOptionList.setFont(new java.awt.Font("Liberation Sans", 0, Integer.parseInt(s)));
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem logoutButton;
    private javax.swing.JMenuItem quitButton;
    private javax.swing.JButton selectButton;
    private javax.swing.JMenuItem textSizeButton;
    private javax.swing.JList<String> userOptionList;
}