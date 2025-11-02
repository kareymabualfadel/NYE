package debugging;
/**
 * A class that analyzes words.
 */
public class WordAnalyzer
{
    /**
     * Constructs an analyzer for a given word.
     * @param aWord the word to be analyzed
     */
    public WordAnalyzer(String aWord)
    {
        word = aWord;
    }

    /**
     * Gets the first repeated character. A character is <i>repeated</i>
     * if it occurs at least twice in adjacent positions. For example,
     * 'l' is repeated in "hollow", but 'o' is not.
     * @return the first repeated character, or 0 if none found
     */
    public char firstRepeatedCharacter()
    {           /* the first bug is here and its critical (i + 1 ) should never exceed the last valid index ( Word.lenth() -1)
                    so instead of this ....(   for (int i = 0; i < word.length(); i++)    )   we write add -1 to word.lenght() */
        for (int i = 0; i < word.length() -1; i++)
        {
            char ch = word.charAt(i);
            if (ch == word.charAt(i + 1))
                return ch;
        }
        return 0;
    }

    /**
     * Gets the first multiply occuring character. A character is <i>multiple</i>
     * if it occurs at least twice in the word, not necessarily in adjacent positions.
     * For example, both 'o' and 'l' are multiple in "hollow", but 'h' is not.
     * @return the first repeated character, or 0 if none found
     */
    public char firstMultipleCharacter()
    {
        for (int i = 0; i < word.length(); i++)
        {
            char ch = word.charAt(i);
            if (find(ch, i + 1) >= 0)
                return ch;
        }
        return 0;
    }
            /*    the other buggy maybe its the orginal ..ao as far as i understand that the firstmultipleChar method uses a helper called find
            *       and here it comes the issue ..so since that find helper maybe finds the same letter because it starts searching at the same index,
            *        not after it.    because the parameter it has int pos ..i think we should change it to i + 1*/
    private int find(char c, int pos)
    {
        for (int i = pos; i < word.length(); i++)
        {
            if (word.charAt(i) == c)
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Counts the groups of repeated characters. For example, "mississippi!!!" has
     * four such groups: ss, ss, pp and !!!.
     * @return the number of repeated character groups
     */
    public int countRepeatedCharacters()
    {
        int c = 0;
        for (int i = 1; i < word.length() - 1; i++)
        {
            if (word.charAt(i) == word.charAt(i + 1)) // found a repetition
            {
                if (word.charAt(i - 1) != word.charAt(i)) // it't the start
                    c++;
            }
        }
        return c;
    }

    private String word;
}
