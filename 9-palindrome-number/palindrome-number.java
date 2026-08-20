class Solution {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int o=x;
        int a=0;
        while(x!=0){
            int b=x%10;
            a = a*10+b;
            x=x/10;

        }
        if (o==a){
            return true;

        }
        else {
           return false;
        }
    }
}