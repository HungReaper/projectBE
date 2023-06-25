package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import gui.Main;

public class SubjectFuntion {
    static int sizeList;
    public static final String FILESUBJECT = "src\\data\\Subject";
    public static final String FILESUBIDLIST = "src\\data\\Subject\\SubjectId";
    public static final String FILESUBNAME = "src\\data\\Subject\\SubjectName.txt";
    public static final String FILESUBID = "src\\data\\Subject\\SubjectId.txt";
    public static final String FILESUBCREDIT = "src\\data\\Subject\\SubjectCredit.txt";

    public static void subjectFuntion (){
        boolean quit = false;
        while(!quit){
            Main.clearConsole();
            Menu.menuSubject();
            int choice1 = Main.choice(6);
            switch(choice1){
                case 1:
                    Main.clearConsole();
                    int choice2;
                    do {
                        Main.clearConsole();
                        showList();
                        System.out.println("\nExit?\n  1. yes\n  2.no ");
                        choice2 = Main.choice(2);
                    } while(choice2 == 2);
                    break;
                case 2:
                    createNewSubject();
                    break;
                case 3:
                    readAndUpdata();
                    break;
                case 4:
                    deleteSubject();
                    break;
                case 5:
                    break;
                case 6:
                    quit = true;
                    break;
            }
        }
    }

    // hiện danh sách Subjects
    public static void showList() {
        System.out.println("    ---Subject List---\n");
        ArrayList<String> subjectList = GetList.getList(FILESUBNAME);
        int i = 1;
        for (String sub : subjectList) {
            System.out.println(i + ". " + sub);
            i++;
        }
        sizeList = i-1;
        return;
    }

    // tạo mới Subjects
    public static void createNewSubject() {
        Main.clearConsole();
        Subject sub = new Subject();
        Scanner sc = new Scanner(System.in);
        System.out.println("--Create New subject");
        String name = "";
        String id = "";
        String credit = "0";

        boolean check = true;
        do{
            do {
                check = true;
                System.out.println("- Subject Name: ");
                name = sc.nextLine();
                check = CheckValid.checkSubName(name, FILESUBNAME);
            } while(!check);
            sub.setName(name);
            do {
                check = true;
                System.out.println("- Subject id: ");
                id = sc.nextLine();
                id = id.toUpperCase();
                check = CheckValid.checkSubId(id, FILESUBID);
            } while(!check);
            sub.setId(id);

            do {
                check = true;
                System.out.println("- Subject credit");
                credit = sc.nextLine();
                check = CheckValid.checkSubcredit(credit);
            } while(!check);
            sub.setCredit(credit);

            WriteToFile.writtNewToFile(FILESUBIDLIST + "\\" + sub.getId() + ".txt" , sub.getName() + "\n" + sub. getId() + "\n" + sub.getCredit() + "\n" + "Joined student:\n");
            WriteToFile.WriteToFile(FILESUBNAME, sub.getName() + "\n");
            WriteToFile.WriteToFile(FILESUBID, sub.getId() + "\n");
            WriteToFile.WriteToFile(FILESUBCREDIT, sub.getCredit() + "\n");
        } while(!check);
    }

    //
    public static void readAndUpdata() {
        Main.clearConsole();
        System.out.println("chose what subject you want to update\n");
        String subIdFromName = "";
        ArrayList <String> subName = GetList.getList(FILESUBNAME);
        ArrayList <String> idList = GetList.getList(FILESUBID);
        ArrayList <String> creditlist = GetList.getList(FILESUBCREDIT);
        int sizeId = idList.size();
        showList();
        System.out.println("what subject you like to Read?\n");
        int select = Main.choice(sizeId + 1);
        subIdFromName = idList.get(select - 1);
        boolean quit = false;
        ArrayList <String> subInFor = GetList.getList(FILESUBIDLIST + "\\" + subIdFromName + ".txt");
        while(!quit){
            int section = 0;
            do{
               showSubInFor(subInFor);
               System.out.println();
               System.out.println("---Update---\n");
               Menu.menuSubjectUpdate();
               System.out.println("\nWhat would you like to update?\n");
               section = Main.choice(4);
               switch(section){
                    // update name
                    case 1:
                        Main.clearConsole();
                        System.out.println("New Subject Name: ");
                        String newName = updateName();
                        subInFor.set(0, newName);
                        subName.set(section - 1, newName);
                        try {
                            Formatter f = new Formatter(FILESUBIDLIST + "\\" + subIdFromName + ".txt");
                            for(String string : subInFor) {
                                f.format(string + "\n");
                            }
                            f.close();
                        } catch (Exception e) {
                            System.out.println("can't not write to file list");
                        }
                        try {
                            Formatter f = new Formatter(FILESUBNAME);
                            for(String string : subName) {
                                f.format(string + "\n");
                            }
                            f.close();
                        } catch (Exception e) {
                            System.out.println("can't write to file Name");
                        }
                        break;
                    // updata id
                    case 2:
                        Main.clearConsole();
                        System.out.println("New subject ID: ");
                        String newId = updateId();
                        newId.toUpperCase();
                        subInFor.set(1, newId);
                        idList.set(section - 1, newId);
                        try {
                            Formatter f = new Formatter(FILESUBIDLIST + "\\" + newId + ".txt");
                            for(String string : subInFor) {
                                f.format(string + "\n");
                            }
                            f.close();
                        } catch (Exception e) {
                            System.out.println("can't write to file list");                      
                        }
                        try {
                            Formatter f = new  Formatter(FILESUBID);
                            for(String string : idList) {
                                f.format(string + "\n");
                            }
                            f.close();
                        } catch (Exception e) {
                            System.out.println("can't wirte to file txt");
                        }
                        try {
                            File f = new File(FILESUBIDLIST + "\\" + subIdFromName + ".txt");
                            f.delete();
                        } catch (Exception e) {
                            System.out.println("can't delete old file txt");
                        }
                        
                        break;
                    // updata credit
                    case 3:
                        Main.clearConsole();
                        System.out.println("New subject credit: ");
                        String newCredit = updateCredit();
                        subInFor.set(2, newCredit);
                        creditlist.set(section - 1, newCredit);
                        try {
                            Formatter f = new Formatter(FILESUBIDLIST + "\\" + subIdFromName + ".txt");
                            for(String string : subInFor) {
                                f.format(string + "\n");
                            }
                            f.close();
                        } catch (Exception e) {
                            System.out.println("can't wirte to file list");
                        }
                        try {
                            Formatter f = new Formatter(FILESUBCREDIT);
                            for(String string : creditlist) {
                                f.format(string + "\n");
                            }
                            f.close();
                        } catch (Exception e) {
                            System.out.println("can't wirte to file txt");
                        }
                        break;
                    case 4:
                        quit = true;
                        break;
               }

            } while(!quit);
        }
    }

    // upadate name
    public static String updateName() {
        boolean check = true;
        String newName = "";
        do {
            check = true;
            Scanner sc = new Scanner(System.in);
            newName = sc.nextLine();
            check = CheckValid.checkSubName(newName, FILESUBNAME);
        } while(!check);
        return newName;
    }
    
    // update Id
    public static String updateId() {
        boolean check = true;
        String newId = "";
        do {
            check = true;
            Scanner sc = new Scanner(System.in);
            newId = sc.nextLine();
            newId.toUpperCase();
            check = CheckValid.checkSubId(newId, FILESUBID);
        } while(!check);
        return newId;
    }

    //update Creadit
    public static String updateCredit() {
        boolean check = true;
        String newCredit = "";
        do {
            check = true;
            Scanner sc = new Scanner(System.in);
            newCredit = sc.nextLine();
            check = CheckValid.checkSubcredit(newCredit);
        } while(!check);
        return newCredit;
    }

    // xem các mục trong file list
    public static void showSubInFor(ArrayList <String> list) {
        Main.clearConsole();
        try {
            System.out.println("---Subject's Information\n");
            System.out.println("    - Name:     " + list.get(0));
            System.out.println("    - ID:       " + list.get(1));
            System.out.println("    - Credit:   " + list.get(2));
            System.out.println("---");
            for(int i = 3; i < list.size(); i++){
                System.out.println(list.get(i));
            }
        } catch (Exception e) {
            System.out.println("can't show information");
            // TODO: handle exception
        }
        
    }
    
    // Funstion xóa File
    public static void deleteSubject() {
        boolean quit = false;
        String subIdFromName = "";
        do{
            boolean quit1 = true;
            Main.clearConsole();
            ArrayList <String> idList = GetList.getList(FILESUBID);
            int sizeId = idList.size();
            System.out.println("\n---Subject Name List---\n");
            showList();
            System.out.println("what subject you like to delete\n");
            int select = Main.choice(sizeId + 1);
            subIdFromName = idList.get(select - 1);
            ArrayList <String> subInFor = GetList.getList(FILESUBIDLIST + "\\" + subIdFromName + ".txt");
            String delName = subInFor.get(0);
            String delId = subInFor.get(1);
            String delCredit = subInFor.get(2);
            do{
                if(subInFor.size() > 4) {
                    System.out.println("There are still students, can't not deleted.\n");
                } else {
                    try {
                        ArrayList <String> newdelNameTxt = GetList.getListDel(FILESUBNAME, delName);
                        Formatter f = new Formatter(FILESUBNAME);
                        for(String string : newdelNameTxt) {
                            f.format(string + '\n');
                        }
                        f.close();
                    } catch (Exception e) {
                        System.out.println("can't delete file name.txt");
                    }
                    try {
                        ArrayList <String> newdelIdTxt = GetList.getListDel(FILESUBID, delId);
                        Formatter f = new Formatter(FILESUBID);
                        for(String string : newdelIdTxt) {
                            f.format(string + "\n");
                        }
                        f.close();
                    } catch (Exception e) {
                         System.out.println("can't delete file id.txt");
                    }
                    try {
                        ArrayList <String> newdelCreditTxt = GetList.getListDel(FILESUBCREDIT, delCredit);
                        Formatter f = new Formatter(FILESUBCREDIT);
                        for(String string : newdelCreditTxt) {
                            f.format(string + "\n");
                        } 
                        f.close();
                    } catch (Exception e) {
                        System.out.println("can't delete file credit.txt");
                    }
                    try {
                        File f = new File(FILESUBIDLIST + "\\" + subIdFromName + ".txt");
                        f.delete();
                    } catch (Exception e) {
                        System.out.println("can't delete file list");
                    }
                }
            } while(!quit1);
            Main.clearConsole();
            System.out.println("what you want to do next?");
            System.out.println("1. go back");
            System.out.println("2. continue delete?");
            if (Main.choice(2) == 1){
                quit = true;
            }
        } while(!quit);

    }


}

