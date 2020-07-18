package com.example.tbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class demo {

    public static void main(String[] args){
        int[] num1={};
        int[] num2={-2,-1};
        String str="";
        System.out.println(longestPalindrome(str));
    }
    public static String longestPalindrome(String s) {
        Stack<Node> stack = new Stack<>();
        int current, next,fMax,fFirst,fLast,cMax=0;
        int charCount=1;
        current=0;
        for(int i=0;i<s.length()-1;i++){
            next = i+1;

            if(s.charAt(next)==s.charAt(current)){
                charCount++;
                break;
            }
            else{

                if(!stack.empty()) {
                    if (stack.peek().ch.equals(s.charAt(next))) {
                        cMax += charCount;
                        charCount += 2 * stack.pop().count;
                        break;
                    }
                }
                    stack.push(new Node(s.charAt(current),charCount));
                   charCount=1;
                   current++;
            }
        }

        return "";
    }


    static class Node{
        Character ch;
        int count;
        public Node(Character chr,int n){
            ch= chr;
            count = n;
        }

        public Character getCh() {
            return ch;
        }

        public int getCount(){
            return count;
        }
    }
}
