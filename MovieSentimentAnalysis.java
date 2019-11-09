import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MovieSentimentAnalysis {

    public static String s;
    public static String s1;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("What would you like to do?");
        System.out.println("1: Get the score of a word.");
        System.out.println("2: Get the overall score of words in a file (one word per line).");
        System.out.println("3: Find the highest/lowest scoring words in a file.");
        System.out.println("4: Sort words from a file into positive.txt and negative.txt.");
        System.out.println("5: Exit the program.");
        System.out.print("Enter a number 1-5: ");
        int option = input.nextInt();
        input.nextLine();
        switch (option) {
            case 1: {
                System.out.print("Enter the file directory for movieReviews: ");
                String filename = input.nextLine();
                System.out.print("Enter a word: ");
                String word = input.nextLine();
                System.out.printf("The average score for reviews containing the word '" + word + "' is " + "%.3f \n", GetWordScore(filename, word));
                break;
            }
            case 2: {
                System.out.print("Enter the file directory for movieReviews: ");
                String movieReviewFile = input.nextLine();
                System.out.print("Enter the file directory: ");
                String filename = input.nextLine();
                double x = OverallWordsScore(filename, movieReviewFile);
                System.out.printf("The average score of words in file directory is " + "%.3f \n", x);
                if (x > 2.01) {
                    System.out.println("The overall sentiment of file is positive.");
                } else {
                    System.out.println("The overall sentiment of the file is negative.");
                }
                break;
            }
            case 3: {
                System.out.print("Enter the file directory for movieReviews: ");
                String movieReviewFile = input.nextLine();
                System.out.print("Enter the file directory: ");
                String filename = input.nextLine();
                double[] x = HighestLowest(filename, movieReviewFile);
                System.out.printf("The most positive word, with a score of " + "%.3f", x[0]);
                System.out.print(" is '" + s1 + "'");
                System.out.println();
                System.out.printf("The most negative word, with a score of " + "%.3f", x[1]);
                System.out.print(" is '" + s + "'");
                System.out.println();
                break;
            }

            case 4: {
                System.out.print("Enter the file directory for movieReviews: ");
                String movieReviewFile = input.nextLine();
                System.out.print("Enter the file directory: ");
                String filename = input.nextLine();
                WriteToFile(filename, movieReviewFile);
                break;
            }

            case 5: {
                System.exit(0);
                break;
            }

            default:
                throw new IllegalArgumentException("Invalid input: " + option);
        }
    }

    public static double GetWordScore(String filename, String word) throws FileNotFoundException {
        File reviewFile = new File(filename);
        Scanner reviewScanner = new Scanner(reviewFile);

        int reviewScore;
        int count = 0;
        int sum = 0;
        String reviewText;

        while (reviewScanner.hasNext()) {
            reviewScore = reviewScanner.nextInt();
            reviewText = reviewScanner.nextLine();

            if (reviewText.contains(word)) {
                count++;
                sum += reviewScore;
            }
        }
        double avg = sum / (1.0 * count);
        System.out.println("The word '" + word + "' appears " + count + " times.");
        return avg;
    }

    public static double OverallWordsScore(String filename, String movieReviewFile) throws FileNotFoundException {
        File reviewFile = new File(movieReviewFile);
        Scanner reviewScanner = new Scanner(reviewFile);

        File reviewFile1 = new File(filename);
        Scanner reviewScanner1 = new Scanner(reviewFile1);

        int reviewScore;
        int sum = 0;
        int count = 0;
        int c = 0;
        double avg = 0.0;
        String reviewText;
        String word;
        while (reviewScanner1.hasNext()) {
            sum = 0;
            count = 0;
            reviewScanner = new Scanner(reviewFile);
            word = reviewScanner1.nextLine();
            while (reviewScanner.hasNext()) {
                reviewScore = reviewScanner.nextInt();
                reviewText = reviewScanner.nextLine();

                if (reviewText.contains(word)) {
                    sum += reviewScore;
                    count++;
                }
            }
            c++;
            avg += (double) sum / count;
        }
        double var = avg / c;
        return var;
    }

    public static double[] HighestLowest(String filename, String movieReviewFile) throws FileNotFoundException {
        File reviewFile = new File(movieReviewFile);
        Scanner reviewScanner = new Scanner(reviewFile);

        File reviewFile1 = new File(filename);
        Scanner reviewScanner1 = new Scanner(reviewFile1);

        int reviewScore;
        int sum = 0;
        int count = 0;
        int c = 0;
        Double max = Double.MIN_VALUE;
        Double min = Double.MAX_VALUE;
        double avg = 0.0;
        String reviewText;
        String word;
        while (reviewScanner1.hasNext()) {
            sum = 0;
            count = 0;
            reviewScanner = new Scanner(reviewFile);
            word = reviewScanner1.nextLine();
            while (reviewScanner.hasNext()) {
                reviewScore = reviewScanner.nextInt();
                reviewText = reviewScanner.nextLine();

                if (reviewText.contains(word)) {
                    sum += reviewScore;
                    count++;
                }
            }
            c++;
            avg = (double) sum / count;
            if (avg < min) {
                min = avg;
                s = word;
            }

            if (avg > max) {
                max = avg;
                s1 = word;
            }
        }

        double[] a = new double[2];
        a[0] = max;
        a[1] = min;
        return a;
    }

    public static void WriteToFile(String filename, String movieReviewFile) throws FileNotFoundException {
        File reviewFile = new File(movieReviewFile);
        Scanner reviewScanner = new Scanner(reviewFile);

        File reviewFile1 = new File(filename);
        Scanner reviewScanner1 = new Scanner(reviewFile1);

        PrintWriter out = new PrintWriter("negative.txt");
        PrintWriter out1 = new PrintWriter("positive.txt");

        int reviewScore;
        int sum = 0;
        int count = 0;
        int c = 0;
        double avg = 0.0;
        String reviewText;
        String word;
        while (reviewScanner1.hasNext()) {
            sum = 0;
            count = 0;
            reviewScanner = new Scanner(reviewFile);
            word = reviewScanner1.nextLine();
            while (reviewScanner.hasNext()) {
                reviewScore = reviewScanner.nextInt();
                reviewText = reviewScanner.nextLine();

                if (reviewText.contains(word)) {
                    sum += reviewScore;
                    count++;
                }
            }
            c++;
            avg = (double) sum / count;
            if (avg < 1.9) {
                out.println(word);
            } else if (avg > 2.1) {
                out1.println(word);
            } else {
            }
        }
        out.close();
        out1.close();
    }
}
