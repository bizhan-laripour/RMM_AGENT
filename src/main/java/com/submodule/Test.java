package com.submodule;

public class Test {


    public static void main(String[] args) {
        int [] ip1 = new int[4];
        int [] ip2 = new int[4];

        String [] parts1 = "168.200.197.3".split("\\.");
        String [] parts2 = "238.199.200.78".split("\\.");

        for (int i = 0; i <4; i++){
            ip1[i] = Integer.parseInt(parts1[i]);
            for (int j = 0; j<4; j ++){
                ip2[j] = Integer.parseInt(parts2[j]);
                for (int k = ip1[i]; k<ip2[j]; k++){
                    System.out.println(k);
                }
            }
        }
    }


}
