import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;


public class WordCounter {
    private MapSet<String,Integer> map;
    private int total;
    private boolean chooseMap;

    public WordCounter(int size) {
        if (size == 0) {
            this.map = new BSTMap<String, Integer>(new StringAscending());
            this.chooseMap = true;
        }
        else {
            this.map = new Hashmap<String, Integer>(size);
            this.chooseMap = false;
        }
        this.total = 0;
        
    } 


    //generates the word counts from a file of words
    public void analyze(String filename) {
        map.clear();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
    
            while (line != null) {
                
                String[] words = line.split("[^a-zA-Z0-9']");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim().toLowerCase();
                    if (word.length() != 0) {
                        Integer has = map.get(word);
                        if (has != null) {
                            map.put(word, has + 1);
                        } else {
                            map.put(word, 1);
                        }
                        this.total ++;
                    }
                   
                }
    
                line = bufferReader.readLine();
            }
            bufferReader.close();
            
            return;    
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file " + filename );
        }
        catch(IOException ex) {
            System.out.println("Error reading file " + filename);
        }
        return;
    }

    //returns the total word count
    public int getTotalWordCount() {
        return total;
    }


    //returns the number of unique words
    public int getUniqueWordCount() {
        return map.size();
    }

    //returns the frequency value associated with this word
    public int getCount(String word) {
        return map.get(word); 
    }

    
    public MapSet<String,Integer> getMap() {
        return this.map;
    }

    //returns the value associated with this word divided by the total word count. 
    public double getFrequency( String word ) {
        double frequency = ((double)this.getCount(word)) / this.getTotalWordCount();
        return frequency;
    }

    //writes the contents of the BSTMap to a word count file.
    public void writeWordCounterFile(String filename) throws IOException {
        String report = "totalWordCount: " + this.total + "\n";
        ArrayList<KeyValuePair<String,Integer>> set = map.entrySet();        
        FileWriter writeCount = new FileWriter(filename);
        writeCount.write(report);
        for (KeyValuePair<String,Integer> kv: set) {
            writeCount.write(kv + "\n");
        }
        writeCount.close();
    }

   


    //reads the contents of a word count file and reconstructs the fields of the WordCount object, including the BSTMap
    public void readWordCountFile( String filename ) {
        map.clear();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
            //read first line separately
            String[] firstLine = line.split("[^a-zA-Z0-9']");
            this.total = Integer.parseInt(firstLine[2]);
            line = bufferReader.readLine();
            while (line != null ) { 
                String[] words = line.split("[^a-zA-Z0-9']");
                
                map.put(words[0], Integer.parseInt(words[2]));
                
                
                line = bufferReader.readLine();
            }
            bufferReader.close();
            return;    
        }

        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file " + filename );
        }
        catch(IOException ex) {
            System.out.println("Error reading file " + filename);
        }
        return;
        
    }


    
    public static void main(String[] args) throws IOException {
        //enable taking in multiple files in the command line and analyze them
        for (int i = 0; i < args.length; i ++) {
            ArrayList<Long> times = new ArrayList<Long>();
            int iteration = 0;
            int x = i + 1;
            int wordCount = 0;
            int unique = 0;
            
            System.out.println("STATISTIC FOR FILE " + x);

            //analyze each file 5 times
            while (iteration < 5 ) {
                long timeStart = System.currentTimeMillis();
                WordCounter test = new WordCounter(0);
                test.analyze(args[i]);
                long timeEnd = System.currentTimeMillis();
                long time = timeEnd - timeStart;
                
                //for the first iteration, calculate total, unique wordcount, depth/collision
                if (iteration == 0) {
                    wordCount = test.getTotalWordCount();
                    unique = test.getUniqueWordCount();
                    //if BSTMap is used, calculate the tree's depth
                    if (test.chooseMap == true) {
                        int depth = ((BSTMap<String, Integer>)(test.map)).depth();
                        System.out.println("Depth: " + depth);

                    } //if any type of Hashmap is used, calculate the collision
                    else {
                        int collision = ((Hashmap<String, Integer>)(test.map)).getCollision();
                        
                        System.out.println("Collision: " + collision);

                        System.out.println(((Hashmap<String, Integer>)(test.map)).getMostFrequent(20));
                    }
                    //get the used memory
                    Runtime run = Runtime.getRuntime();
                    run.gc();
                    long usedMemory = run.totalMemory() - run.freeMemory() ;
                    System.out.println("Used memory: " + usedMemory);

                }
                System.out.println("It took: "+ time/1000.0 + "s to read the file");
                times.add(time);
                iteration ++;
                
            } 
            times.remove(Collections.max(times));
            times.remove(Collections.min(times));

            long mean = (times.get(0) + times.get(1) + times.get(2))/3;
            System.out.println("Average processing time: "+ mean/1000.0 + "s");
            System.out.println("Total word count: " + wordCount);
            System.out.println("Unique word count: " + unique);
            
            

            
            
        }

      


    }  

       
    

}