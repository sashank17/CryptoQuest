public class functions {
    public String RightShift(String x) {
        char[] oldChar = x.toCharArray();
        char[] newChar = new char[oldChar.length];
        newChar[0] = oldChar[oldChar.length-1];
        for(int i = 1; i < oldChar.length; i++)
            newChar[i] = oldChar[i-1];
        x = String.valueOf(newChar);
        return x;
    }

    public String LeftShift(String x) {
        char[] oldChar = x.toCharArray();
        char[] newChar = new char[oldChar.length];
        newChar[oldChar.length-1] = oldChar[0];
        for(int i = 0; i < oldChar.length-1; i++)
            newChar[i] = oldChar[i+1];
        x = String.valueOf(newChar);
        return x;
    }

    public String FullReverse(String x) {
        StringBuilder sb = new StringBuilder(x);
        x = sb.reverse().toString();
        return x;
    }

    public String AltSwap(String x) {
        x = FullReverse(x.charAt(0) + Character.toString(x.charAt(1))) + FullReverse(x.charAt(2) + Character.toString(x.charAt(3))) + FullReverse(x.charAt(4) + Character.toString(x.charAt(5)));
        System.out.println(x);
        return x;
    }
}