package structures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
 * 		Purpose : 	Makes pronounceable name based on a list of names.
 * 				  	Breaks input names into psuedo syllables of length
 * 					of 2 3 or 4 and then randomly stitches them together.
 * 
 * 					Also makes completely randomly generated names by
 * 					stitching together sequences of vowels and consonants		
 */

public class NameGenerator 
{
	private static Random r;
	private static List<String> vowels;
	private static List<String> consonants;
	
	private static final int MIN_WORD_LENGTH = 3;
	private static final int MAX_WORD_LENGTH = 8;
	
	private List<String> beginningSyllables;
	private List<String> midSyllables;
	private List<String> endSyllables;
	
	Set<String> begSyll;
	Set<String> midSyll;
	Set<String> endSyll;
	
	private String filePath;
	
	public NameGenerator()
	{
		r = new Random();
		beginningSyllables = new ArrayList<>();
		midSyllables = new ArrayList<>();
		endSyllables = new ArrayList<>();
		
		vowels = new ArrayList<String>();
		vowels.add("a");
		vowels.add("e");
		vowels.add("i");
		vowels.add("o");
		vowels.add("u");
		vowels.add("y");
		
		consonants = new ArrayList<String>();
		consonants.add("b");
		consonants.add("c");
		consonants.add("d");
		consonants.add("f");
		consonants.add("g");
		consonants.add("h");
		consonants.add("j");
		consonants.add("k");
		consonants.add("l");
		consonants.add("m");
		consonants.add("n");
		consonants.add("p");
		consonants.add("q");
		consonants.add("r");
		consonants.add("s");
		consonants.add("t");
		consonants.add("v");
		consonants.add("w");
		consonants.add("x");
		consonants.add("z");
	}	
	public String getRandomName()
	{
		// Stitches together beginning, middle and end syllables
		String randomName = beginningSyllables.get(r.nextInt(beginningSyllables.size()));
		
		if(r.nextInt(5) > 4)
			randomName += midSyllables.get(r.nextInt(midSyllables.size()));
		
		randomName += endSyllables.get(r.nextInt(endSyllables.size()));
		
		//System.out.println(midSyllables.size() + " " + endSyllables.size());
		
		return randomName;
	}
	public void setFileToUse(String filePath)
	{
		this.filePath = filePath;
		setUpSyllableBanks();
	}
	public void setUpSyllableBanks()
	{
		String name = "";
		String[] chunks;
		char[] charedName;
		
		ArrayList<String> chunker = new ArrayList<>();
		
		try(Scanner scan = new Scanner(new File(filePath)))
		{
			while(scan.hasNextLine())
			{
				name = scan.nextLine();
				if(name.length() == 3)
				{
					beginningSyllables.add(name);
				}
				else
				{
					for(int i = 0; i < 7; i++)
					{
						chunker = returnSyllablesFromName(name, i);
						// System.out.println(chunker);
						if(i == 0)
							beginningSyllables.addAll(chunker);
						if(i == 2)
							midSyllables.addAll(chunker);
						if(i == 3 || i == 4 || i == 7)
							endSyllables.addAll(chunker);
					}
				}
			}
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		// Removes duplicates
		beginningSyllables = beginningSyllables.stream().distinct().collect(Collectors.toList());
		midSyllables = midSyllables.stream().distinct().collect(Collectors.toList());
		endSyllables = endSyllables.stream().distinct().collect(Collectors.toList());
	}
	public ArrayList<String> returnSyllablesFromName(String name, int offset)
	{
		char[] chars = name.toCharArray();
		int numOfCombos = 4;
		ArrayList<String> syllables = new ArrayList<>();
		
		int range = 1;
		String chunk = "";
		
		for(int i = 1; i <= numOfCombos; i++) // Combos for syll patterns
		{
			for(int j =offset; j < range+offset; j++) // gets varying lengths of substrings
			{
				if(j < name.length())
				{
					Character ch = chars[j];
					chunk += ch.toString();
				}
			}
			if(chunk.length() > 1)
				syllables.add(chunk);
			
			if(range + 1 < name.length())
				range++;
			
			chunk = "";
		}
		return syllables;
	}
	public boolean isConsonant(Character c)
	{
		if(consonants.contains(c))
			return true;
		else
			return false;
	}
	public boolean isVowel(Character c)
	{
		if(vowels.contains(c))
			return true;
		else
			return false;
	}
	public static String getVowel()
	{
		return vowels.get(r.nextInt(vowels.size()));
	}
	public static String getConsonant()
	{
		return consonants.get(r.nextInt(consonants.size()));
	}
	
	public static String getNextLetter(String word)
	{
		int vowelOrConsonant = r.nextInt(2)+ 1;
		String nextLetter = "";
		if(word.length() == 0) // if begining of word
		{
			if(vowelOrConsonant == 1)
				nextLetter = getVowel().toUpperCase();
			else
				nextLetter = getConsonant().toUpperCase();
		}
		else // after first letter
		{
			char[] unfin = word.toCharArray();
			if(word.length() > 2)
			{
				Character c = unfin[word.length() -1];
				if(vowels.contains(c.toString())) // if last char of word is vowel
				{
					nextLetter = getConsonant();
					if(word.length() ==4 || word.length() ==5)
					{
						nextLetter += getConsonant();
					}
				}
				else
				{
					nextLetter = getVowel();
					if(word.length() ==2 || word.length() ==7)
					{
						nextLetter += getVowel();
					}
				}
			}
			else
			{
				if(vowelOrConsonant == 1)
					nextLetter = getVowel();
				else
					nextLetter = getConsonant();
			}
		}
		return nextLetter;
	}
	public static String getFirstName()
	{
		int firstNameLength = r.nextInt(MAX_WORD_LENGTH) + MIN_WORD_LENGTH;
		String name = "";
		for(int i = 0; i < firstNameLength; i++)
		{
			name += getNextLetter(name);
		}
		return name;
	}
}
