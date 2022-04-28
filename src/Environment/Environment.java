package Environment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Environment {
    ArrayList<BNN> bnnList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    void cycle() {
        System.out.println("\n\n");
        int displayCounter = 0;
        for (BNN bnn : bnnList)
            System.out.println(displayCounter++ + " " + bnn);
        System.out.println("Enter Command");
        System.out.println("[load] [save] [train] [create] [analyze] [create train] [remove] [train experiment] [exit]");
        String userInput = sc.nextLine();
        switch(userInput) {
            case "load":
                loadBNNtoList();
                break;
            case "save":
                saveBNNfromList();
                break;
            case "train":
                trainBNNFromList();
                break;
            case "create":
                createBNNtoList();
                break;
            case "analyze":
                analyzeBNNfromList();
                break;
            case "create train":
                createTrainBNNtoList();
                break;
            case "remove":
                removeBNNsfromList();
                break;
            case "train experiment":
                trainExperiment();
                break;
            case "exit":
                System.exit(0);
        }
        cycle();
    }

    void loadBNNtoList() {
        System.out.println("Enter BNN fileName");
        String fileName = sc.nextLine();

        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new File("/Users/user/Documents/IDEA Projects/BNNEnvironment/src/Environment/BNN/" + fileName + ".txt"));
        }
        catch (Exception e){
            System.out.println("loadBNNtoList " + e);
            return;
        }

        bnnList.add(new BNN(fileScanner));
    }

    void saveBNNfromList(){
        System.out.println("Enter BNN index");
        int bnnIndex = Integer.parseInt(sc.nextLine());
        System.out.println("Enter file name");
        String fileName = sc.nextLine();

        try
        {
            File file = new File("/Users/user/Documents/IDEA Projects/BNNEnvironment/src/Environment/BNN/" + fileName +".txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file.getAbsolutePath());

            bnnList.get(bnnIndex).save(fileWriter);
        }
        catch(Exception e)
        {
            System.out.println("saveBNNfromList " + e);
        }
    }

    void trainBNNFromList(){
        System.out.println("Enter BNN index");
        int bnnIndex = Integer.parseInt(sc.nextLine());
        BNN bnn = bnnList.get(bnnIndex);

        System.out.println("Enter 10 ^ n");
        int epocMag = Integer.parseInt(sc.nextLine());

        System.out.println("Enter learning rate");
        double rate = Double.parseDouble(sc.nextLine());

        bnn.learn(epocMag, rate);
    }

    void createBNNtoList(){
        bnnList.add(new BNN());
    }

    void analyzeBNNfromList() {
        System.out.println("Enter BNN index");
        int bnnIndex = Integer.parseInt(sc.nextLine());
        BNN bnn = bnnList.get(bnnIndex);

        System.out.println("[biases] [error]");
        String userInput = sc.nextLine();
        System.out.println("Enter print count");
        int count = Integer.parseInt(sc.nextLine());
        switch(userInput) {
            case "biases":
                bnn.printBiases();
                break;
            case "error":
                bnn.printError(count);
                break;
        }
    }

    public void createTrainBNNtoList(){
        int startIndex = bnnList.size();
        System.out.println("Enter how many BNN's to create");
        int bnnCount = Integer.parseInt(sc.nextLine());

        System.out.println("Enter 10 ^ n");
        double epocMag = Double.parseDouble(sc.nextLine());

        System.out.println("Enter learning rate");
        double rate = Double.parseDouble(sc.nextLine());

        System.out.println("Enter cutOff value");
        double cutOff = Double.parseDouble(sc.nextLine());

        for (int counter = 0; counter < bnnCount;) {
            createBNNtoList();
            int curIndex = startIndex + counter;
            bnnList.get(curIndex).learn(epocMag, rate);
            if(bnnList.get(curIndex).computeError() > cutOff) {
                System.out.println(bnnList.get(curIndex));
                bnnList.remove(curIndex);

            }
            else
                counter++;
        }
    }

    public void removeBNNsfromList(){
        System.out.println("Enter start index or -1 for clearing all bnn");
        int startIndex = Integer.parseInt(sc.nextLine());
        int endIndex = -2;
        if(startIndex != -1) {
            System.out.println("Enter end index");
            endIndex = Integer.parseInt(sc.nextLine());
        }

        for (int i = startIndex; i <= endIndex; i++)
            bnnList.remove(startIndex);

        if(startIndex == -1)
            bnnList = new ArrayList<>();
    }

    public void trainExperiment() {




    }
}
