package onion.radio;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static InputStream audioOutBytes;
    public static Station stationRecord;
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
    private static int getNumberOfTimesToLoop(){
        System.out.println("How many times do you want the audio to be looped?");
        return Integer.parseInt(sc.nextLine());
    }
    private static File getAudioFile(){
        System.out.println("What is the path to the audio file you want to broadcast?");
        return new File(sc.nextLine());
    }
    private static int queryPort(){
        System.out.println("What port can you use for the onion service? (e.g 62444)");
        return Integer.parseInt(sc.nextLine());
    }
    private static String getTitle(){
        System.out.println("Title your broadcast station");
        return sc.nextLine();
    }
    private static void finalValediction(){
        System.out.println("Good luck marketing this thing!");
        System.out.println("Now you need to put up the onion service in your torrc, so that the address to your server");
        System.out.println("It should look like xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.onion:<port>");
        System.out.println("That allows listeners to add your station to their station.txt files.");
    }
    private static String getDescription(){
        System.out.println("Describe your station, target about 3 sentences, all on the same line.");
        return sc.nextLine();
    }
}