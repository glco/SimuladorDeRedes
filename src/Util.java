import java.io.IOException;

public class Util {

	private static String[] splitByNumber(String text, int number) {
        int inLength = text.length();
        int arLength = inLength / number;
        int left=inLength%number;
        if(left>0){++arLength;}
        String ar[] = new String[arLength];
            String tempText=text;
            for (int x = 0; x < arLength; ++x) {

                if(tempText.length()>number){
                ar[x]=tempText.substring(0, number);
                tempText=tempText.substring(number);
                }else{
                    ar[x]=tempText;
                }

            }

        return ar;
    }
}
