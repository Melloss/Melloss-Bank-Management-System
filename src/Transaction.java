import javax.swing.*;
import  java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Transaction{

    static ArrayList<String> transactionData = new ArrayList<String>();
    public static void getReady(long accountNumber){
        boolean isFound = false;
        ArrayList<String> transactionList = new ArrayList<String>();
        try{
            File f = new File("Transactions/Account_list.txt");
            if(!f.exists()) {
                f.createNewFile();
            }
            File f2 = new File("Transactions/Account_list.txt");
            Scanner s = new Scanner(f2);
            while(s.hasNext()){
                transactionList.add(s.nextLine());
            }
            for(String t : transactionList){
                if(t.equals(accountNumber+".txt")){
                    isFound = true;
                    File f3 = new File("Transactions/"+t);
                    if(f3.exists()){
                        Scanner cin = new Scanner(f3);
                        while(cin.hasNext()){
                            transactionData.add(cin.nextLine());
                        }
                    }
                    break;
                }
                else{
                    isFound = false;
                }
            }
            if(isFound == false){
                System.out.println("-> The Account does not Found!");
            }
        }
        catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static String showTransaction(long accountNumber){
        getReady(accountNumber);
        String text="",fullText="<html><body>";
        if(transactionData.size() == 0){
            JOptionPane.showMessageDialog(new ShowTransaction(),"There is no transaction history!","Melloss Bank",JOptionPane.WARNING_MESSAGE);
        }
        for(String s: transactionData){
            text=text+s+"<br>";
        }
        fullText=fullText+text+"</body></html>";
        transactionData.clear();
        return fullText;
    }
    public static void saveTransaction(long accountNumber,double amount,double currentBalance,boolean withdrawal){
        getReady(accountNumber);
        try{
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
            DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
            String date = d.format(df);
            String time = t.format(tf);
            FileWriter f = new FileWriter("Transactions/"+accountNumber+".txt",true);
                if(withdrawal == true){
                    f.write(" - "+amount+" AT "+time+" ON "+date+"\n-> Balance: "+currentBalance+"\n");
                }
                else{
                    f.write(" + "+amount+" AT "+time+" ON "+date+"\n-> Balance: "+currentBalance+"\n");
                }
            f.close();
        }
        catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        transactionData.clear();
    }
}