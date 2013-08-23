package net.kingsbery.acm._1991;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CodeGeneration {

    public static class ASTNode{

        private String a;

        public ASTNode(String a){
            this.a=a;
        }
        
        public String toString(){
            return this.a;
        }
        
    }
    
    public static void main(String args[]) throws Exception{
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/net/kingsbery/acm/_1991/code.txt")));
        String line=null;
        while((line=reader.readLine())!=null){
            ASTNode result = parseExpression(line);
        }
    }

    private static ASTNode parseExpression(String line) {
        System.out.println(line);
        List<String> splits = Arrays.asList(line.split(""));
        splits=splits.subList(1, splits.size());
        int i=0;
        Stack<ASTNode> stack = new Stack<ASTNode>();
        for(String item: splits){
            if(isOperand(item)){
                
                ASTNode b = stack.pop();
                System.out.println("L " + stack.pop());
                System.out.println("A " + b);
                String temp = getTemp();
                System.out.println("ST " + temp);
                stack.push(new ASTNode(temp));
            }else{
                stack.push(new ASTNode(item));
            }          
        }
        
        ASTNode result = stack.pop();
        return result;
    }

    private static int tempNum=1;
    
    private static String getTemp() {
        return "$"+tempNum++;
    }

    private static boolean isOperand(String string) {
        return "+".equals(string);
    }
    
}
