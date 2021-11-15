

public class Magpie4
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Ho Ho Ho, I'm Snowbot. Let's chat in lowercase, because uppercase hurts my eyes!";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (findKeyword(statement, "hi") >= 0)
		{
			response = "Hello! What do you want for Christmas";
		}

    else if (findKeyword(statement, "hello") >= 0)
    {
      response = "How are you?";
    }

    else if (findKeyword(statement, "christmas") >= 0)
    {
      response = "What's your favorite  Christmas song?";
    }

    else if (findKeyword(statement, "how are you?") >= 0) {
      response = "Feeling very joyful today, how about you?";
    }

    else if (findKeyword(statement, "bad") >= 0) {
      response = "Sorry about that!";
    }
    
    else if (findKeyword(statement, "good") >= 0) {
      response = "Delightful!";
    }
    
    else if (findKeyword(statement, "jingle bells")>= 0
				|| findKeyword(statement, "deck the halls") >= 0
				|| findKeyword(statement, "all i want for christmas") >= 0|| findKeyword(statement, "santa claus is coming to town") >= 0|| findKeyword(statement, "white christmas") >= 0|| findKeyword(statement, "here comes santa claus") >= 0|| findKeyword(statement, "last christmas") >= 0) 
    {
        statement = statement.toLowerCase();
      
        String[] christmasSongs = {"jingle bells", "deck the halls", "all I want for christmas", "santa claus is coming to town" , "white christmas" , "here comes santa claus" , "last christmas"};
      
        String[] christmasLyrics = {"oh what fun it is to ride in a one horse open sleigh!", "fa la la la la", "....IS YOU!!!", "you better watch out, you better not cry!", "Just like the ones I used to knoww..", "right down Santa Claus Lane!", "I gave you my heart!"};

        for (int i = 0; i < christmasSongs.length; i++)
        {
          if (statement.equals(christmasSongs[i])){
            response = christmasLyrics[i];
          }
        }
        return response;
    }

    else if (findKeyword(statement, "thank you") >= 0)
		{
			response = "Of Course! All for the Christmas spirit!";
		}

		else if (findKeyword(statement, "mother") >= 0
				|| findKeyword(statement, "father") >= 0
				|| findKeyword(statement, "sister") >= 0
				|| findKeyword(statement, "brother") >= 0)
		{
			response = "Have a great Christmas family!";
		}

    else if (findKeyword(statement, "dog") >= 0
        || findKeyword(statement, "dogs") >= 0
        || findKeyword(statement, "cat") >= 0
				|| findKeyword(statement, "cats") >= 0)
		{
			response = "I can buy you one for christmas!";
		}

    else if (findKeyword(statement, "yes") >= 0)
      {
        response = "Christmas brings me joy!";
		  }

    else if (findKeyword(statement, "no") >= 0) 
      {
        response = "Talk about being a grinch";
      }

		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

    else if (findKeyword(statement, "I want a", 0) >= 0)
		{
			response = transformIWantAStatement(statement);
		}

    else if (findKeyword(statement, "name", 0) >= 0){
      response = "My name is Snowbot!";
    }

    else if (statement.length() > 10) 
    {
      response = getRandomResponse(statement);
    }

		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				response = getRandomResponse(statement);
			}
		}
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

  // transforms "i want a" to "i can get you that for Christmas"
  private String transformIWantAStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want a", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "I can get you a " + restOfStatement + " for Christmas!";
	}

	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
  
	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse(String statement)
	{
		final int NUMBER_OF_RESPONSES = 5;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Someone ended up on Santa's naughty list..";
		}
		else if (whichResponse == 1)
		{
			response = "It’s chilly out here";
		}
		else if (whichResponse == 2)
		{
			response = "What did you mean by '" + statement + "'?";
		}
		else if (whichResponse == 3)
		{
			response = "Could you rephrase " + statement + "?";
		}
    else if (whichResponse == 4)
		{
			response = "I don't know what you mean by " + statement + ".";
		}
    else if (whichResponse == 5)
    {
      response = "What's your favorite christmas song?";
    }

		return response;
	}
}
