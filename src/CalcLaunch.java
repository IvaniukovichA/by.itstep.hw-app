import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CalcLaunch {

    public static void main(String[] args) {

        System.out.println("Welcome to Calculator-APP");
        boolean flag = true;
        while(flag) {
            System.out.println("Please write your math-expression:");
            Scanner sc = new Scanner(System.in);
            StringBuilder expr = new StringBuilder();
            expr.append(sc.nextLine());

            spaceDel(expr);
            expr = fixNegativeDigit(expr); //add 0 to beginning of String, if it starts from '-'

            while (!checkFunction(expr)) {
                System.out.println("Write correct expression in format like \" 3+6 / 18 - 34 \" ");
                expr = new StringBuilder();
                expr.append(sc.nextLine());
                spaceDel(expr);
            }


            while (true) {
                int lBracketInd = (expr).indexOf("(");
                int rBracketInd = (expr).indexOf(")");
                if (lBracketInd > -1 && rBracketInd > -1 && (lBracketInd < rBracketInd))  { //getting expression from brackets
                    StringBuilder subExpr = new StringBuilder();
                    String lExpr = expr.substring(0,lBracketInd);
                    String rExpr = "";
                    if (rBracketInd < expr.length()) { //checking brackets  at the end of a line
                        rExpr = expr.substring(rBracketInd+1,expr.length());
                    }
                    subExpr.append(expr.substring(lBracketInd+1,rBracketInd));
                    subExpr = fixNegativeDigit(subExpr);
                    expr = new StringBuilder();
                    expr.append(lExpr).append(calc(subExpr)).append(rExpr);

                } else {
                    System.out.println(expr + "=" + calc(expr));
                    break;
                }
            }

            System.out.println("Do you want to repeat? Y/N");
            if (!sc.nextLine().equals("Y")) {
                flag = false;
                System.out.println("Thanks for calculating with me, bye");
            }

        }

    }

    public static StringBuilder fixNegativeDigit(StringBuilder sb) {
        if (sb.charAt(0) == '-') {
            StringBuilder sbTemp = new StringBuilder();
            sbTemp.append('0').append(sb);
            sb = sbTemp;
        }
        if (sb.charAt(0) == '(' && sb.charAt(1) == '-') {
            StringBuilder sbTemp = new StringBuilder();
            sbTemp.append("(0").append(sb.substring(1));
            sb = sbTemp;
        }
        return sb;
    }



    public static boolean checkFunction(StringBuilder sb) {
        char ch, chNext = 127;
        if (sb.charAt(0) < 40 || (sb.charAt(0) > 40 && sb.charAt(0) < 48) || sb.charAt(0) > 57) {
            return false;
        }
        for (int i = 0; i < sb.length() - 1; i++) {
            ch = sb.charAt(i);
            chNext = sb.charAt(i + 1);

            if (ch < 40 || ch == ',' || ch > 57) {
                return false;
            } else if (ch < 48 && ch > 42 && chNext < 48 && chNext > 42) {
                return false;
            }


        }
        if (chNext < 40 || chNext == ',' || chNext > 57) {
            return false;
        }
        return true;
    }

    public static void spaceDel(StringBuilder sb) {
        for (int i = 0; i < sb.length() - 1; i++) {
            if (sb.charAt(i) == ' ') {
                sb = sb.deleteCharAt(i);
            }
        }
    }

    public static Double calc(StringBuilder sb) {
        double rez;
        StringBuffer tempSb = new StringBuffer();
        ArrayList<Double> al = new ArrayList<>();
        ArrayList<Character> op = new ArrayList<>();

        for (int i = 0; i < sb.length(); i++) {
            char curChar = sb.charAt(i);

            if (curChar > 47 && curChar < 58 || curChar == '.' ) {
                tempSb.append(curChar);
            } else {
                al.add(Double.parseDouble(tempSb.toString()));
                tempSb = new StringBuffer();
                op.add(curChar);
            }
            if (i == sb.length() - 1) {
                al.add(Double.parseDouble(tempSb.toString()));
            }
        }

        Iterator<Character> opIterator= op.iterator();
        int i = 0;
        while(opIterator.hasNext()) { //doing mult and dev at first
            Character ch = opIterator.next();
            if (ch.equals('*')) {
                rez = al.get(i)*al.get(i+1);
                al.remove(i+1);
                al.set(i,rez);
                opIterator.remove();
                i-=1;
            } else if (ch.equals('/')) {
                rez = al.get(i)/al.get(i+1);
                al.remove(i+1);
                al.set(i,rez);
                opIterator.remove();
                i-=1;
            }
            i+=1;
        }

        opIterator= op.iterator();
        i = 0;
        while(opIterator.hasNext()) { //doing sum and substr
            Character ch = opIterator.next();
            if (ch.equals('+')) {
                rez = al.get(i)+al.get(i+1);
                al.remove(i+1);
                al.set(i,rez);
                opIterator.remove();
                i-=1;
            } else if (ch.equals('-')) {
                rez = al.get(i)-al.get(i+1);
                al.remove(i+1);
                al.set(i,rez);
                opIterator.remove();
                i-=1;
            }
            i+=1;
        }


        return al.get(0);
    }


}





