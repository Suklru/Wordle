import java.io.*;
import java.util.*;

public class Wordle{
  public static void main(String[] args){

    ArrayList<String> allWords = new ArrayList<>();//An ArrayList that will be used to store all the 5 letter words
    //Add all the 5 letter words into an ArrayList
    try {
      File myObj = new File("words.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        allWords.add(data);
      }
        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred while reading the file.");
        e.printStackTrace();
    }

    //All object initialize
    Scanner scan = new Scanner(System.in);
    Random rand = new Random();
    
    //All variable initialize
    String correctWord = allWords.get(rand.nextInt(allWords.size()));
    int numGuess = 6;
    String guessedWord = "";
    String tempString = "";

    //All Array/ArrayList initialize
    int [] correctWordArr = new int [26]; //Alphabets for the correct word
    int [] guessWordArr = new int [26]; // Alphabets for the guessed word
    String [] alphabets = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"}; //Alphabets
    List<String> Alphabets = new ArrayList<>(Arrays.asList(alphabets)); //Changed to list to make it modifiable
    
    //All background color
    final String BG_Green = "\u001b[42m";//Color for the letters in the right place
    final String BG_Yellow = "\u001b[43m";//Color for the letters in the word but wrong place
    final String RESET = "\u001b[0m";

    //Puts the amount of iteration that each letter occurs in an array for the correct word
    for (int i = 0; i < 5; i++)
    {
      for (int j = 0; j < alphabets.length; j++)
      {
        if (correctWord.substring(i,i+1).equals(alphabets[j]))
        {
          correctWordArr[j] += 1;
        }
      }
    }

    System.out.println("Welcome to Wordle!");

    while (numGuess > 0)
    {
      System.out.println("Please type a 5 letter word.");//The typed 5 letter word
      guessedWord = scan.nextLine();

      //Close out of terminal
      if (guessedWord.equals("-1"))
      {
        scan.close();
      }

      //Check if the word is a 5 letter word
      if (guessedWord.length() > 5 || guessedWord.length() < 5)
      {
        System.out.println("\nPlease type a 5 letter word.");
        guessedWord = scan.nextLine();
      }

      //Check if it's an actual 5 letter word
      for (int i = 0; i < allWords.size(); i++)
      {
        String s = allWords.get(i);
        tempString = s;
        //Checks to see if the word is in word.txt
        if (!guessedWord.equals(s) && i == allWords.size() - 1)
        {
          System.out.println("\nPlease type an actual 5 letter word");
        }
        else if (guessedWord.equals(s))
        {
          //The loop control variable
          numGuess--;
          break;
        }
      }

      //Loops through the guessed word
      for (int j = 0; j < 5; j++)
      {
        //Loops through the alphabets
        for (int x = 0; x < alphabets.length; x++)
        {
          //Checks to see if the letters are in the right spot and if they are it'll increment the letter in the alphabets array.
          if (correctWord.substring(j, j+1).equals(guessedWord.substring(j, j+1)) && guessedWord.substring(j, j+1).equals(alphabets[x]))
          {
            guessWordArr [x] += 1;
          }
        }
      }

      if (guessedWord.length() == 5 && guessedWord.equals(tempString))
      {
        //Loops through the letters of guessedWord
        for (int i = 0; i < 5; i++)
        {
          //Checks if the letters are in the right position
          if (correctWord.substring(i,i+1).equals(guessedWord.substring(i,i+1)))
          {
            System.out.print(BG_Green + guessedWord.substring(i,i+1) + RESET);
          }

          else
          {
            //Cycles through the guessed word to see the amount of iterations
            for (int k = 0; k < alphabets.length; k++)
            {
              //Increments the count for each letter in guessedWord
              if (guessedWord.substring(i,i+1).equals(alphabets[k]))
              {
                guessWordArr[k] += 1;
                //Checks to see if the letter is in the word and the amount of iterations of a letter to see if it's being repeated to much
                if (correctWord.indexOf(guessedWord.substring(i,i+1)) != -1 && guessWordArr[k] <= correctWordArr[k])
                {
                  System.out.print(BG_Yellow + guessedWord.substring(i,i+1) + RESET);
                }

                else if (guessWordArr[k] > correctWordArr[k] && !correctWord.substring(i,i+1).equals(guessedWord.substring(i,i+1)))
                {
                  System.out.print(guessedWord.substring(i,i+1));
                }
              }
            }
          }
        }
      }

      //Resets all the elements in guessWordArr back to zero
      Arrays.fill(guessWordArr, 0);

      if (guessedWord.length() == 5 && guessedWord.equals(tempString))
      {
        //Removes all used alphabets
        for (int index = 0; index < 5; index++)
        {
          for (int num = 0; num < Alphabets.size(); num++)
          {
            if (guessedWord.substring(index,index+1).equals(Alphabets.get(num)))
            {
              Alphabets.remove(num);
            }
          }
        }
      }
      
      System.out.println();

      //Checks if the word is correct;
      if (guessedWord.equals(correctWord))
      {
        System.out.print("\nYou won with " + numGuess + " guesses left!");
        scan.close();
        numGuess = -1;
      }

      else
      {
        System.out.println("\nYou have " + numGuess + " guesses left.");
        System.out.println("These are the unused letters " + Alphabets);
      }
        
      //Occurs when you lose
      if (numGuess == 0)
      {
        System.out.println("\nYou lost. The word was " + correctWord);
        scan.close();
      }
      
    }
  }  
}
