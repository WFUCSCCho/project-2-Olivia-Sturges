import java.io.*;
import java.util.*;

/***********************************************************************************************************************
 * @file: Proj2.java
 * @description: This program implements the main method of Project 2.
 * @author: Olivia Sturges
 * @date: October 23, 2024
 * Note on changing the data: Took out a comma from the titles in rows 69 and 111.
 **********************************************************************************************************************/

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME
        // Create ArrayList to store PARKSANDRECData
        ArrayList<PARKSANDRECData> parksList = new ArrayList<PARKSANDRECData>();

        // Read the file line by line
        //TODO: Wait for Cho's response
        for (int i = 0; i < numLines; i++) { //
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(","); // split the string into multiple parts
            // Create a new PARKS&RECDATA object
            // season, episode_num_in_season, episode_num_overall, title, directed_by, written_by, original_air_date, us_viewers
            PARKSANDRECData data = new PARKSANDRECData(
                    Integer.parseInt(parts[0]), // Season
                    Integer.parseInt(parts[1]), // Episode number in season
                    Integer.parseInt(parts[2]), // Overall episode number
                    parts[3], // Episode
                    parts[4], // Directed By
                    parts[5], // Written By
                    parts[6], // Original Air Date
                    Double.parseDouble(parts[7]) // Number of US viewers
            );
            parksList.add(data); // add the data onto the ArrayList
        }
        inputFileNameStream.close();

        // Making sorted and randomized copies  of the ArrayList
        // sort based off of number of us_viewers
        ArrayList<PARKSANDRECData> sorted = new ArrayList<PARKSANDRECData>(parksList);
        Collections.sort(sorted);

        //randomized
        ArrayList<PARKSANDRECData> rand = new ArrayList<PARKSANDRECData>(parksList);
        Collections.shuffle(rand);

        // Initializing start time and end time
        long startTime = 0;
        long endTime = 0;

        System.out.print("N:" + numLines + ", ");

        // Inserting sorted data into BST
        startTime = System.nanoTime();
        BST<PARKSANDRECData> sortbst = new BST<>();
        for (int i = 0; i < sorted.size(); i++) {
            sortbst.insert(sorted.get(i));
        }
        endTime = System.nanoTime();
        double insertTimeSortedBST = (endTime - startTime) / 1e9;
        System.out.print("Insert sorted BST time (sec): " + insertTimeSortedBST + ", ");

        // Inserting shuffled data into BST
        startTime = System.nanoTime();
        BST<PARKSANDRECData> randbst = new BST<>();
        for (int i = 0; i < rand.size(); i++) {
            randbst.insert(rand.get(i));
        }
        endTime = System.nanoTime();
        double insertTimeShuffledBST = (endTime - startTime) / 1e9;
        System.out.print("Insert shuffled BST time (sec): " + insertTimeShuffledBST + ", ");

        // Inserting sorted data into Avl
        startTime = System.nanoTime();
        AvlTree<PARKSANDRECData> sortAvlTree = new AvlTree<>();
        for (int i = 0; i < sorted.size(); i++) {
            sortAvlTree.insert(sorted.get(i));
        }
        endTime = System.nanoTime();
        double insertTimeSortedAvl = (endTime - startTime) / 1e9;
        System.out.print("Insert sorted Avl time (sec): " + insertTimeSortedAvl + ", ");

        // Inserting shuffled data into Avl
        startTime = System.nanoTime();
        AvlTree<PARKSANDRECData> randAvlTree = new AvlTree<>();
        for (int i = 0; i < rand.size(); i++) {
            randAvlTree.insert(rand.get(i));
        }
        endTime = System.nanoTime();
        double insertTimeShuffledAvl = (endTime - startTime) / 1e9;
        System.out.print("Insert shuffled AvL time (sec): " + insertTimeShuffledAvl + ", ");

        // Searching for data in sorted BST
        startTime = System.nanoTime();
        for (int i = 0; i < parksList.size(); i++) {
            sortbst.search(parksList.get(i));
        }
        endTime = System.nanoTime();
        double searchTimeSortedBST = (endTime - startTime) / 1e9;
        System.out.print("Search sorted BST time (sec): " + searchTimeSortedBST + ", ");

        // Searching for data in shuffled BST
        startTime = System.nanoTime();
        for (int i = 0; i < parksList.size(); i++) {
            randbst.search(parksList.get(i));
        }
        endTime = System.nanoTime();
        double searchTimeShuffledBST = (endTime - startTime) / 1e9;
        System.out.print("Search shuffled BST time (sec): " + searchTimeShuffledBST + ", ");

        // Searching for data in sorted Avl
        startTime = System.nanoTime();
        for (int i = 0; i < parksList.size(); i++) {
            sortAvlTree.contains(parksList.get(i));
        }
        endTime = System.nanoTime();
        double searchTimeSortedAvl = (endTime - startTime) / 1e9;
        System.out.print("Search sorted AvL time (sec): " + searchTimeSortedAvl + ", ");

        // Searching for data in shuffled Avl
        startTime = System.nanoTime();
        for (int i = 0; i < parksList.size(); i++) {
            randAvlTree.contains(parksList.get(i));
        }
        endTime = System.nanoTime();
        double searchTimeShuffledAvl = (endTime - startTime) / 1e9;
        System.out.println("Search sorted AvL time (sec): " + searchTimeSortedAvl);

        // Writing to output.txt in CSV style format
        // N,insert sorted BST (sec),insert shuffled BST (sec),insert sorted AVL (sec),insert shuffled AVL (sec),search sorted BST (sec),search shuffled BST(sec),search sorted AVL (sec),search shuffled AVL (sec)
        writeToFile(numLines + "," + insertTimeSortedBST + "," + insertTimeShuffledBST + "," + insertTimeSortedAvl + "," + insertTimeShuffledAvl + "," + searchTimeSortedBST + "," + searchTimeShuffledBST + "," + searchTimeSortedAvl + "," + searchTimeShuffledAvl, "./output.txt");

    }

    // Method that generates output file
    public static void writeToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            writer.write(content);
            writer.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
