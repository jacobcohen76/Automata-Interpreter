import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Set;

public class StringGenerator
{
	
	/**
	 * Computes the number of possible strings of specified
	 * length given some number of different characters
	 * 
	 * @param k length of string
	 * @param n number of characters in set
	 * @return number of possible strings
	 */
	public static BigInteger numStrings(int k, BigInteger n)
	{
		return n.pow(k);
	}
	
	private static LinkedList<String> generated;
	
	private static void generateAllKLength(Set<Character> charSet, String prefix, int k)
	{
		if(k <= 0)
		{
			generated.add(prefix);
		}
		else
		{
			for(Character c : charSet)
			{
				String newPrefix = prefix + c;
				generateAllKLength(charSet, newPrefix, k - 1);
			}
		}
	}
	
	public static LinkedList<String> generateAllKLength(Set<Character> charSet, int k)
	{
		generated = new LinkedList<String>();
		generateAllKLength(charSet, "", k);
		return generated;
	}
	
	public static LinkedList<String> generateAllUptoKLength(Set<Character> charSet, int k)
	{
		LinkedList<String> generated = new LinkedList<String>();
		for(int i = 0; i <= k; i++)
			generated.addAll(generateAllKLength(charSet, i));
		return generated;
	}
	
	private static void permute(StringBuilder s, int k)
	{
		if(k >= s.length())
		{
			System.out.println(s);
		}
		else
		{
			char temp;
			for(int i = k; i < s.length(); i++)
			{			
				temp = s.charAt(i);
				s.setCharAt(i, s.charAt(k));
				s.setCharAt(k, temp);
				
				permute(s, k + 1);
				
				temp = s.charAt(i);
				s.setCharAt(i, s.charAt(k));
				s.setCharAt(k, temp);
			}
		}
	}
	
	public static void permute(String s)
	{
		permute(new StringBuilder(s), 0);
	}
}
