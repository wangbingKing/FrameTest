package base;

public class StringUtils {
	public static Boolean isNotEmpty(String str)
	{
		if(str == null || str == "")
		{
			return false;
		}
		return true;		
	}
}
