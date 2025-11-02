package debugging;
public class WordAnalyzerTester
{
    public static void main(String[] args)
    {
        testRepeated("aardvark");
        testRepeated("roommate");
        testRepeated("mate");
        testRepeated("test");
            /*  just testing the multiplecharchter method   */
//        System.out.println("\n---- Testing multiple characters ----");
//        testMultiple("karemek");
//        testMultiple("hollow");
//        testMultiple("banana");
    }

    public static void testRepeated(String s)
    {
        WordAnalyzer wa = new WordAnalyzer(s);
        char result = wa.firstRepeatedCharacter();
        if (result == 0)
            System.out.println("No repeated character.");
        else
            System.out.println("First repeated character = " + result);
    }

//    public static void testMultiple(String s)
//    {
//        WordAnalyzer wa = new WordAnalyzer(s);
//        char result = wa.firstMultipleCharacter();
//        if (result == 0)
//            System.out.println("No multiple character.");
//        else
//            System.out.println("First multiple character = " + result);
//    }


}
