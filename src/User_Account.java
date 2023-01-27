import java.awt.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

abstract class UserAccountInfo{
    private String userName;
    private long accountNumber;
    private double balance;
    private int pinCode;
    public String getUserName(){
        return userName;
    }
    public long getAccountNumber(){
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public int getPinCode(){
        return pinCode;
    }
    public void setUserName(String n){
        this.userName = n;
    }
    public void setAccountNumber(long a){
        this.accountNumber = a;
    }
    public void setBalance(double b){
        this.balance = b;
    }
    public void setPinCode(int p){
        this.pinCode = p;
    }
}
public class User_Account extends UserAccountInfo{
    static LinkedList<User_Account> accountList = new LinkedList<User_Account>();
    public static void createAccount(User_Account a) {
        getReady();
         String fullName = a.getUserName();
         long accountNumber = a.getAccountNumber();
         double initialBalance = a.getBalance();
         int pinCode = a.getPinCode();
         accountList.addLast(a);
         Transaction.saveTransaction(accountNumber,initialBalance,initialBalance,false);
        String filePath = "Accounts/"+Long.toString(accountNumber)+".txt";
        try{
            File f = new File(filePath);
            if(f.createNewFile()){
                FileWriter w = new FileWriter(filePath);
                w.write(fullName+"\n");
                w.write(Long.toString(accountNumber)+"\n");
                w.write(Double.toString(initialBalance)+"\n");
                w.write(Integer.toString(pinCode)+"\n");
                w.close();
                FileWriter al = new FileWriter("Accounts/Account_list.txt",true);// al = account list
                al.write(Long.toString(accountNumber)+".txt"+"\n");
                al.close();
                FileWriter tl = new FileWriter("Transactions/Account_list.txt",true);// tl = transaction list
                tl.write(Long.toString(accountNumber)+".txt"+"\n");
                tl.close();
                File tr = new File("Transactions/"+accountNumber+".txt");
                if(!tr.exists()){
                    tr.createNewFile();
                }
            }
            else{
                throw new IOException("The Account is Already Exist!");
            }
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(new CreateUserAccount(),"The Account is Already Exist!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        }
        catch (InputMismatchException e){
            JOptionPane.showMessageDialog(new CreateUserAccount(),"Your Input is not Valid Try Again!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static boolean getReady() {
        ArrayList<String> accountNumbers = new ArrayList<String>();
        if(accountList.size() == 0) {
            try {
                File f = new File("Accounts/Account_list.txt");
                if(!f.exists()) {
                    f.createNewFile();
                }
                File accountFile = new File("Accounts/Account_list.txt");
                Scanner s = new Scanner(accountFile);
                if (accountFile.exists()) {
                    while (s.hasNext()) {
                        accountNumbers.add(s.nextLine());
                    }
                } else throw new FileNotFoundException("\nERR -> Account List File doesn't exist!");
            } catch (FileNotFoundException e) {
                System.out.println(e);
                e.printStackTrace();
                return false;
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(null,e,"Melloss Bank",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            for (String i : accountNumbers) {
                try {
                    File accountInfo = new File("Accounts/" + i);
                    Scanner cin = new Scanner(accountInfo);
                    if (accountInfo.exists()) {
                        User_Account a = new User_Account();
                        a.setUserName(cin.nextLine());
                        a.setAccountNumber(cin.nextLong());
                        a.setBalance(cin.nextDouble());
                        a.setPinCode(cin.nextInt());
                        accountList.addLast(a);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("ERR -> File Does Not Exist!");
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        else {
            return true;
        }
    }
    public static void displayAllAccounts(){
        if(getReady()){
            String fullText="";
            DisplayAccounts d = new DisplayAccounts();
            d.setVisible(true);
            d.setResizable(false);
            for(User_Account a: accountList){
                DefaultTableModel model = (DefaultTableModel)d.jTable1.getModel();
                model.addRow(new Object[]{a.getUserName(),a.getAccountNumber(),a.getBalance(),a.getPinCode()});
            }

        }

    }
    public static void withdrawBalance(long accountNumber ,double wBalance){
        if(getReady()){
            for (User_Account a: accountList){
                if(a.getAccountNumber() == accountNumber){
                    double balance = a.getBalance();
                    if(balance > wBalance && wBalance > 0){
                        a.setBalance(balance-wBalance);
                        Transaction.saveTransaction(accountNumber,wBalance,a.getBalance(),true);
                        try{
                            FileWriter w = new FileWriter("Accounts/"+a.getAccountNumber()+".txt");
                            w.write(a.getUserName()+"\n");
                            w.write(Long.toString(a.getAccountNumber())+"\n");
                            w.write(Double.toString(a.getBalance())+"\n");
                            w.write(Integer.toString(a.getPinCode()));
                            w.close();
                        }
                        catch (IOException e) {
                            System.out.println("-> File not Found!");
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                    else{
                        if(wBalance<=0){
                            System.out.println("-> Unable to withdraw zero or negative number!");
                            JOptionPane.showMessageDialog(new SaveBalance(),"Unable to withdraw zero or negative number!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
                        }
                        else JOptionPane.showMessageDialog(new SaveBalance(),"The Balance is Insufficient to withdraw!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        }
    }
    public static void saveBalance(long accountNumber ,double sBalance){
        if(getReady()) {
            for (User_Account a : accountList) {
                if (a.getAccountNumber() == accountNumber) {
                    double balance = a.getBalance();
                    if(sBalance > 0){
                        a.setBalance(balance+sBalance);
                        Transaction.saveTransaction(accountNumber,sBalance,a.getBalance(),false);
                        try{
                            FileWriter w = new FileWriter("Accounts/"+a.getAccountNumber()+".txt");
                            w.write(a.getUserName()+"\n");
                            w.write(Long.toString(a.getAccountNumber())+"\n");
                            w.write(Double.toString(a.getBalance())+"\n");
                            w.write(Integer.toString(a.getPinCode()));
                            w.close();
                        }
                        catch (IOException e) {
                            System.out.println("-> File not Found!");
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(new SaveBalance(),"The Amount we add should be positive number!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    public static void searchByAccountNumber(long accountNumber){
        if(getReady()) {
            SearchByAccountNumber sa = new SearchByAccountNumber();
            sa.setVisible(true);
            sa.setResizable(false);
            for (User_Account a : accountList) {
                if(a.getAccountNumber() == accountNumber) {
                    DefaultTableModel model = (DefaultTableModel)sa.jTable1.getModel();
                    model.addRow(new Object[]{a.getUserName(),a.getAccountNumber(),a.getBalance(),a.getPinCode()});
                    break;
                }
            }
        }
    }
    public static void searchByName(String name) {
        if(getReady()){
            boolean isFound = false;
            SearchByName sn = new SearchByName();
            sn.setVisible(true);
            sn.setResizable(false);
            if(getReady()) {
                for (User_Account a : accountList) {
                    if(a.getUserName().equalsIgnoreCase(name)) {
                        isFound =true;
                        DefaultTableModel model = (DefaultTableModel)sn.jTable1.getModel();
                        model.addRow(new Object[]{a.getUserName(),a.getAccountNumber(),a.getBalance(),a.getPinCode()});
                    }
                }
                if(isFound == false){
                    JOptionPane.showMessageDialog(new SearchByName(),"The Account doesn't Exist!","Melloss Bank",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    public static void maxBalance(double balance) {
        if(getReady()){
            boolean isFound = false;
            if(getReady()) {
                MaximumBalance mb = new MaximumBalance();
                mb.setVisible(true);
                mb.setResizable(false);
                for (User_Account a : accountList) {
                    if(a.getBalance() >= balance){
                        DefaultTableModel model = (DefaultTableModel)mb.jTable1.getModel();
                        model.addRow(new Object[]{a.getUserName(),a.getAccountNumber(),a.getBalance(),a.getPinCode()});
                        isFound = true;
                    }
                }
                if(isFound==false) {
                    JOptionPane.showMessageDialog(new SearchByName(),"The Account doesn't Exist!","Melloss Bank",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    public static void minBalance(double balance) {
        if(getReady()){
            boolean isFound = false;
            MinimumBalance mb = new MinimumBalance();
            mb.setVisible(true);
            mb.setResizable(false);
            if(getReady()) {
                for (User_Account a : accountList) {
                    if(a.getBalance() <= balance){
                        DefaultTableModel model = (DefaultTableModel)mb.jTable1.getModel();
                        model.addRow(new Object[]{a.getUserName(),a.getAccountNumber(),a.getBalance(),a.getPinCode()});
                        isFound = true;
                    }
                }
                if(isFound==false) {
                    JOptionPane.showMessageDialog(new SearchByName(),"The Account doesn't Exist!","Melloss Bank",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    public static void deleteAccount(long accountNumber){
        ArrayList<String> accountNumbers = new ArrayList<String>();
        boolean isFound = false;
        if(getReady()){
            int objectIndex=0;
            for (User_Account a : accountList) {
                if(a.getAccountNumber() == accountNumber){
                    File f = new File("Accounts/"+accountNumber+".txt");
                    f.delete();
                    File t = new File("Transactions/"+accountNumber+".txt");
                    t.delete();
                    accountList.remove(objectIndex);
                }
                objectIndex++;
            }
            try {
                File accountFile = new File("Accounts/Account_list.txt");
                if(accountFile.length() == 0){
                    System.out.println("-> There is no Account Created!");
                    System.exit(1);
                }
                Scanner s = new Scanner(accountFile);
                if (accountFile.exists()) {
                    while (s.hasNext()) {
                        accountNumbers.add(s.nextLine());
                    }
                } else throw new FileNotFoundException("\nERR -> Account List File doesn't exist!");
            } catch (FileNotFoundException e) {
                System.out.println(e);
                e.printStackTrace();
            }
            for(int i = 0; i<accountNumbers.size(); i++){
                String s = accountNumbers.get(i);
                if(s.equals(Long.toString(accountNumber)+".txt")){
                    isFound = true;
                    int index = accountNumbers.indexOf(s);
                    accountNumbers.remove(index);
                    try{
                        FileWriter f = new FileWriter("Accounts/Account_list.txt");
                        for(int j = 0; j<accountNumbers.size(); j++){
                            String str = accountNumbers.get(j);
                            f.write(str+"\n");
                        }
                        f.close();
                        FileWriter t = new FileWriter("Transactions/Account_list.txt");
                        for(int j = 0; j<accountNumbers.size(); j++){
                            String str = accountNumbers.get(j);
                            t.write(str+"\n");
                        }
                        t.close();
                    }
                    catch (IOException e){
                        System.out.println(e);
                        System.exit(1);
                        e.printStackTrace();
                    }
                }
                else{
                    isFound = false;
                }
            }
            if(isFound == false){
                System.out.println("-> The Account does not exist!");
            }
            else JOptionPane.showMessageDialog(new DeleteAccount(),"The Account Deleted Successfully!","Melloss Bank",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void transferBalance(long fromAccountNumber,long toAccountNumber,double amount){
        if(isThereUserByAccountNumber(fromAccountNumber) && isThereUserByAccountNumber(toAccountNumber)){
            if(UserLoginPanel.getAccountNumber() != toAccountNumber){
                withdrawBalance(fromAccountNumber,amount);
                saveBalance(toAccountNumber,amount);
            }
            else JOptionPane.showMessageDialog(new TransferBalance(),"Unable to Transfer to your Account Number!","Melloss Bank",JOptionPane.WARNING_MESSAGE);
        }
        else JOptionPane.showMessageDialog(new TransferBalance(),"The Account Does not Exist!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
    }
    public static void changePinCode(long accountNumber, int newPinCode){
        if(getReady()){
            for(User_Account a : accountList){
                if(a.getAccountNumber() == accountNumber){
                    a.setPinCode(newPinCode);
                    try{
                        FileWriter f = new FileWriter("Accounts/"+accountNumber+".txt");
                        f.write(a.getUserName()+"\n");
                        f.write(a.getAccountNumber()+"\n");
                        f.write(a.getBalance()+"\n");
                        f.write(Integer.toString(a.getPinCode()));
                        f.close();
                    }
                    catch (IOException e){
                        System.out.println("-> ERROR: "+e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static boolean isThereUserBy(long accountNumber, int pin){
        if(getReady()){
            for(User_Account a: accountList){
                if(a.getAccountNumber() == accountNumber && a.getPinCode() == pin){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isThereUserByAccountNumber(long accountNumber){
        if(getReady()){
            for(User_Account a: accountList){
                if(a.getAccountNumber() == accountNumber){
                    return true;
                }
            }
        }
        return false;
    }
    public static double getBalanceByAccountNumber(long accountNumber){
        if(getReady()){
            for(User_Account a: accountList){
                if(a.getAccountNumber() == accountNumber){
                    return a.getBalance();
                }
            }
        }
        return -1;
    }
    public static String getUserNameByAccountNumber(long acc){
        if(getReady()) {
            for (User_Account a : accountList) {
                if(a.getAccountNumber() == acc){
                    return a.getUserName();
                }
            }
        }
        return null;
    }
}
class CreateUserAccount extends JFrame {
    public CreateUserAccount() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Melloss Bank");
        jLabel1.setFont(new java.awt.Font("Liberation Sans Narrow", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Create User Account");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Full Name: ");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("Account Number: ");

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("Initial Balance: ");

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("Pin Code: ");

        jTextField1.setFont(new java.awt.Font("Liberation Sans", 0, 18));
        jTextField1.setMargin(new Insets(5,5,5,5));
        jTextField1.setFocusable(true);

        jTextField2.setFont(new java.awt.Font("Liberation Sans", 0, 18));
        jTextField2.setMargin(new Insets(5,5,5,5));

        jTextField3.setFont(new java.awt.Font("Liberation Sans", 0, 18));
        jTextField3.setMargin(new Insets(5,5,5,5));

        jTextField5.setFont(new java.awt.Font("Liberation Sans", 0, 18));
        jTextField5.setMargin(new Insets(5,5,5,5));

        saveButton.setFont(new java.awt.Font("Liberation Sans", 0, 18));
        saveButton.setForeground(new java.awt.Color(51, 51, 255));
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(51, 51, 255));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
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
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 74, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(159, 159, 159)
                                .addComponent(saveButton)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String name = jTextField1.getText();
        String accountNumber = jTextField2.getText();
        String initialBalance = jTextField3.getText();
        String pincode = jTextField5.getText();
        User_Account account = new User_Account();
        account.setUserName(name);
        account.setAccountNumber(Long.parseLong(accountNumber));
        account.setBalance(Double.parseDouble(initialBalance));
        account.setPinCode(Integer.parseInt(pincode));
        User_Account.createAccount(account);
        dispose();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton saveButton;
}
class SaveBalance extends JFrame {
    public SaveBalance() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        accountInput = new javax.swing.JTextField();
        amountInput = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Save Balance to Account");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Accout Number:");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Amount: ");

        accountInput.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        accountInput.setForeground(new java.awt.Color(51, 51, 51));
        accountInput.setMargin(new java.awt.Insets(5, 10, 5, 5));

        amountInput.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        amountInput.setForeground(new java.awt.Color(51, 51, 51));
        amountInput.setMargin(new java.awt.Insets(5, 10, 5, 5));

        saveButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        saveButton.setForeground(new java.awt.Color(0, 51, 255));
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 51, 255));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(accountInput, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                                        .addComponent(amountInput))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(accountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(amountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveButton)
                                        .addComponent(cancelButton))
                                .addGap(44, 44, 44))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String account = accountInput.getText();
        String amount = amountInput.getText();
        if(amount!=null && account != null){
            if(User_Account.isThereUserByAccountNumber(Long.parseLong(account))){
                User_Account.saveBalance(Long.parseLong(account),Double.parseDouble(amount));
                dispose();
            }
            else JOptionPane.showMessageDialog(new SaveBalance(),"The Account doesn't Exist!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        }
        else JOptionPane.showMessageDialog(new SaveBalance(),"Account or Amount Input is Empty!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }
    private javax.swing.JTextField accountInput;
    private javax.swing.JTextField amountInput;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton saveButton;

}
class WithdrawBalance extends JFrame {

    public WithdrawBalance() {
        setTitle("Melloss Bank");
        initComponents();
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        withdrawButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Withdraw Balance from Account");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Account Number:");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Amount: ");

        jTextField1.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        jTextField1.setMargin(new java.awt.Insets(5, 10, 5, 5));

        jTextField2.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(51, 51, 51));
        jTextField2.setMargin(new java.awt.Insets(5, 10, 5, 5));

        withdrawButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        withdrawButton.setForeground(new java.awt.Color(0, 51, 255));
        withdrawButton.setText("Withdraw");
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 51, 255));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                                        .addComponent(jTextField2))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(withdrawButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(withdrawButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String account = jTextField1.getText();
        String amount = jTextField2.getText();
        if(amount!=null && account != null){
            if(User_Account.isThereUserByAccountNumber(Long.parseLong(account))){
                User_Account.withdrawBalance(Long.parseLong(account),Double.parseDouble(amount));
                dispose();
            }
            else JOptionPane.showMessageDialog(new SaveBalance(),"The Account doesn't Exist!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        }
        else JOptionPane.showMessageDialog(new SaveBalance(),"Account or Amount Input is Empty!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton withdrawButton;
    // End of variables declaration
}
class DisplayAccounts extends JFrame {
    public DisplayAccounts() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Account List");

        jTable1.setFont(new java.awt.Font("Lohit Devanagari", 1, 17)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "Name", "Account Number", "Balance", "Pin Code"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setGridColor(new java.awt.Color(204, 204, 255));
        jTable1.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTable1.setRowHeight(35);
        jTable1.setShowGrid(true);
        jTable1.getTableHeader().setFont(new java.awt.Font("Lohit Devanagari", Font.BOLD, 19));
        jTable1.getTableHeader().setForeground(new java.awt.Color(0, 51, 51));
        jScrollPane1.setViewportView(jTable1);

        closeButton.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        closeButton.setForeground(new java.awt.Color(0, 0, 204));
        closeButton.setText("Close");
        closeButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(279, 279, 279)
                                                                .addComponent(closeButton)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    // End of variables declaration
}
class SearchByAccountNumber extends JFrame {

    public SearchByAccountNumber() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();
        searchInput = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Search By Account Number");

        jTable1.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 51, 51));
        jTable1.getTableHeader().setFont(new java.awt.Font("Lohit Devanagari", Font.BOLD, 19));
        jTable1.getTableHeader().setForeground(new java.awt.Color(0, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "Name", "Account Number", "Balance", "Pin Code"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(35);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        closeButton.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        closeButton.setForeground(new java.awt.Color(0, 51, 204));
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        searchInput.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        searchInput.setForeground(new java.awt.Color(0, 51, 51));
        searchInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchInput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchInput.setMargin(new java.awt.Insets(5, 10, 5, 5));
        searchInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInputActionPerformed(evt);
            }
        });


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(closeButton)
                                                .addGap(272, 272, 272))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(207, 207, 207))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeButton)
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }
    private void searchInputActionPerformed(java.awt.event.ActionEvent evt) {
        String text = searchInput.getText();
        if(User_Account.isThereUserByAccountNumber(Long.parseLong(text))){
            User_Account.searchByAccountNumber(Long.parseLong(text));
            dispose();
        }
        else JOptionPane.showMessageDialog(new SearchByAccountNumber(),"Account Number "+text+" Does Not Exist!","Melloss Bank",JOptionPane.ERROR_MESSAGE);

    }
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField searchInput;
}
 class SearchByName extends JFrame {

    public SearchByName() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();
        searchInput = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Search By Name");

        jTable1.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 51, 51));
        jTable1.getTableHeader().setFont(new java.awt.Font("Lohit Devanagari", Font.BOLD, 19));
        jTable1.getTableHeader().setForeground(new java.awt.Color(0, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "Name", "Account Number", "Balance", "Pin Code"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(35);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        closeButton.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        closeButton.setForeground(new java.awt.Color(0, 51, 204));
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        searchInput.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        searchInput.setForeground(new java.awt.Color(0, 51, 51));
        searchInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchInput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchInput.setMargin(new java.awt.Insets(5, 10, 5, 5));
        searchInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(closeButton)
                                                .addGap(272, 272, 272))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(207, 207, 207))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeButton)
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void searchInputActionPerformed(java.awt.event.ActionEvent evt) {
        String txt = searchInput.getText();
        User_Account.searchByName(txt);
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField searchInput;
    // End of variables declaration
}
class MaximumBalance extends JFrame {

    public MaximumBalance() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();
        searchInput = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Maximum Balances");

        jTable1.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 51, 51));
        jTable1.getTableHeader().setFont(new java.awt.Font("Lohit Devanagari", Font.BOLD, 19));
        jTable1.getTableHeader().setForeground(new java.awt.Color(0, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Name", "Account Number", "Balance", "Pin Code"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(35);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        closeButton.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        closeButton.setForeground(new java.awt.Color(0, 51, 204));
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        searchInput.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        searchInput.setForeground(new java.awt.Color(0, 51, 51));
        searchInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchInput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchInput.setMargin(new java.awt.Insets(5, 10, 5, 5));
        searchInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInputActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Balance: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(closeButton)
                                                .addGap(272, 272, 272))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(11, 11, 11)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeButton)
                                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void searchInputActionPerformed(java.awt.event.ActionEvent evt) {
        String txt = searchInput.getText();
        User_Account.maxBalance(Double.parseDouble(txt));
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField searchInput;
    // End of variables declaration
}
class MinimumBalance extends JFrame {

    public MinimumBalance() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();
        searchInput = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Minimum Balances");

        jTable1.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 51, 51));
        jTable1.getTableHeader().setFont(new java.awt.Font("Lohit Devanagari", Font.BOLD, 19));
        jTable1.getTableHeader().setForeground(new java.awt.Color(0, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Name", "Account Number", "Balance", "Pin Code"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(35);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        closeButton.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        closeButton.setForeground(new java.awt.Color(0, 51, 204));
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        searchInput.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        searchInput.setForeground(new java.awt.Color(0, 51, 51));
        searchInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchInput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchInput.setMargin(new java.awt.Insets(5, 10, 5, 5));
        searchInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInputActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Balance: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(closeButton)
                                                .addGap(272, 272, 272))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(11, 11, 11)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(closeButton)
                                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void searchInputActionPerformed(java.awt.event.ActionEvent evt) {
        String txt = searchInput.getText();
        User_Account.minBalance(Double.parseDouble(txt));
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField searchInput;
    // End of variables declaration
}
class DeleteAccount extends JFrame {
    public DeleteAccount() {
        setTitle("Melloss Bank");
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        header = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        accountInput = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        header.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        header.setForeground(new java.awt.Color(0, 0, 153));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Delete Account");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Account Number: ");

        accountInput.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        accountInput.setForeground(new java.awt.Color(0, 51, 51));
        accountInput.setMargin(new java.awt.Insets(5, 10, 5, 5));

        deleteButton.setBackground(new java.awt.Color(255, 0, 0));
        deleteButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete");
        deleteButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 0, 204));
        cancelButton.setText("Cancel");
        cancelButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(accountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 11, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelButton)
                                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(accountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(deleteButton)
                                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(61, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String txt = accountInput.getText();
        if(User_Account.isThereUserByAccountNumber(Long.parseLong(txt))){
            int choice = JOptionPane.showConfirmDialog(new DeleteAccount(),"Are you Sure you want to delete teh account?","Melloss Bank",JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                User_Account.deleteAccount(Long.parseLong(txt));
            }
            dispose();
        }
        else JOptionPane.showMessageDialog(new DeleteAccount(),"The Account Does Not Exist!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField accountInput;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel header;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration
}
class ShowBalance extends JFrame {

    public ShowBalance() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        balanceLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        long accNumber = UserLoginPanel.getAccountNumber();
        userNameLabel.setText(User_Account.getUserNameByAccountNumber(accNumber).toUpperCase());
        if(User_Account.getBalanceByAccountNumber(accNumber) != -1){
            balanceLabel.setText(Double.toString(User_Account.getBalanceByAccountNumber(accNumber)));
        }

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("Hello,");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Current Balance");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Your Balance is : ");

        balanceLabel.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        balanceLabel.setForeground(new java.awt.Color(0, 51, 0));

        userNameLabel.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        userNameLabel.setForeground(new java.awt.Color(0, 51, 0));

        jButton1.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 153));
        jButton1.setText("Close");
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
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addComponent(jButton1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(77, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addComponent(jButton1)
                                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration
}
class TransferBalance extends JFrame {
    public TransferBalance() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        toAccountNumberInput = new javax.swing.JTextField();
        amountInput = new javax.swing.JTextField();
        transferButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Transfer Balance");

        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("To :");
        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 17));

        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Amount: ");
        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 17));

        toAccountNumberInput.setForeground(new java.awt.Color(0, 51, 51));
        toAccountNumberInput.setMargin(new Insets(5,10,5,5));
        toAccountNumberInput.setFont(new java.awt.Font("Liberation Sans", 1, 17));


        amountInput.setForeground(new java.awt.Color(0, 51, 51));
        amountInput.setMargin(new Insets(5,10,5,5));
        amountInput.setFont(new java.awt.Font("Liberation Sans", 1, 17));

        transferButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        transferButton.setForeground(new java.awt.Color(0, 0, 102));
        transferButton.setText("Transfer");
        transferButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        transferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferButtonActionPerformed(evt);
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
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(51, 51, 51)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel3)
                                                                        .addComponent(jLabel2))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(amountInput)
                                                                        .addComponent(toAccountNumberInput, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(152, 152, 152)
                                                                .addComponent(transferButton)))
                                                .addGap(0, 64, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(toAccountNumberInput, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(amountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                .addComponent(transferButton)
                                .addGap(42, 42, 42))
        );

        pack();
    }// </editor-fold>

    private void transferButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String toAccount = toAccountNumberInput.getText();
        String amount = amountInput.getText();
        if(User_Account.isThereUserByAccountNumber(Long.parseLong(toAccount))){
            User_Account.transferBalance(UserLoginPanel.getAccountNumber(),Long.parseLong(toAccount),Double.parseDouble(amount));
        }
        else JOptionPane.showMessageDialog(new TransferBalance(),"The Account doesn't Exist!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField amountInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField toAccountNumberInput;
    private javax.swing.JButton transferButton;
    // End of variables declaration
}
class ShowTransaction extends JFrame {
    public ShowTransaction() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        header = new javax.swing.JLabel();
        hitoryLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


        header.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        header.setForeground(new java.awt.Color(0, 0, 255));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Transaction History");

        hitoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hitoryLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        hitoryLabel.setText(Transaction.showTransaction(UserLoginPanel.getAccountNumber()));
        hitoryLabel.setFont(new java.awt.Font("Liberation Sans", 1, 18));

        closeButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        closeButton.setForeground(new java.awt.Color(0, 0, 255));
        closeButton.setText("Close");
        closeButton.setMargin(new java.awt.Insets(5, 19, 5, 19));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(hitoryLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(154, 154, 154)
                                .addComponent(closeButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hitoryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(closeButton)
                                .addGap(11, 11, 11))
        );

        pack();
    }

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel header;
    private javax.swing.JLabel hitoryLabel;
}
class BuyAirtime extends JFrame {

    public BuyAirtime() {
        setTitle("Molloss Bank");
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buyButton = new javax.swing.JButton();
        phoneNumberInput = new javax.swing.JTextField();
        amountInput = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buy Airtime");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Phone Number: ");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Amount:");

        buyButton.setFont(new java.awt.Font("Liberation Sans", 1, 17)); // NOI18N
        buyButton.setForeground(new java.awt.Color(0, 0, 255));
        buyButton.setText("Buy");
        buyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyButtonActionPerformed(evt);
            }
        });

        phoneNumberInput.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        phoneNumberInput.setForeground(new java.awt.Color(0, 51, 51));
        phoneNumberInput.setMargin(new java.awt.Insets(5, 10, 5, 5));

        amountInput.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        amountInput.setForeground(new java.awt.Color(0, 51, 51));
        amountInput.setMargin(new java.awt.Insets(5, 10, 5, 5));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(phoneNumberInput, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(amountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 32, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(143, 143, 143)
                                .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(phoneNumberInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(amountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String amount = amountInput.getText();
        User_Account.withdrawBalance(UserLoginPanel.getAccountNumber(),Double.parseDouble(amount));
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField amountInput;
    private javax.swing.JButton buyButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField phoneNumberInput;
    // End of variables declaration
}
class ChangePinCode extends JFrame {

    public ChangePinCode() {
        setTitle("Melloss Bank");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        header = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        currentpinInput = new javax.swing.JTextField();
        newPinInput = new javax.swing.JTextField();
        changeButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        header.setFont(new java.awt.Font("Liberation Sans", 1, 25)); // NOI18N
        header.setForeground(new java.awt.Color(0, 0, 255));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Change Pin Code");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Current Pin Code: ");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("New Pin Code: ");

        currentpinInput.setFont(new java.awt.Font("Liberation Sans", 0, 17));
        currentpinInput.setMargin(new java.awt.Insets(5, 10, 5, 5));
        newPinInput.setFont(new java.awt.Font("Liberation Sans", 0, 17));
        newPinInput.setMargin(new java.awt.Insets(5, 10, 5, 5));

        changeButton.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        changeButton.setForeground(new java.awt.Color(0, 0, 204));
        changeButton.setText("Change");
        changeButton.setMargin(new java.awt.Insets(5, 10, 5, 10));
        changeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 0, 204));
        cancelButton.setText("Cancel");
        cancelButton.setMargin(new java.awt.Insets(5, 10, 5, 5));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
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
                                                .addGap(104, 104, 104)
                                                .addComponent(changeButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cancelButton)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(newPinInput)
                                        .addComponent(currentpinInput, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(currentpinInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(newPinInput, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(changeButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String currentPin = currentpinInput.getText();
        String newPin = newPinInput.getText();
        if(User_Account.isThereUserBy(UserLoginPanel.getAccountNumber(),Integer.parseInt(currentPin))){
            User_Account.changePinCode(UserLoginPanel.getAccountNumber(),Integer.parseInt(newPin));
            JOptionPane.showMessageDialog(new ChangePinCode(),"Changed Successfully!","Melloss Bank",JOptionPane.INFORMATION_MESSAGE);
        }
        else JOptionPane.showMessageDialog(new ChangePinCode(),"Wrong Pin Code!","Melloss Bank",JOptionPane.ERROR_MESSAGE);
        dispose();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton changeButton;
    private javax.swing.JTextField currentpinInput;
    private javax.swing.JLabel header;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField newPinInput;
    // End of variables declaration
}