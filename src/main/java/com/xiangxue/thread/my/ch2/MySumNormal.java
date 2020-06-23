package com.xiangxue.thread.my.ch2;

import com.xiangxue.tools.SleepTools;

/**
 * 正常运算求和
 */
public class MySumNormal {
    public static void main(String[] args) {
        int count = 0;
        int[] src = MyMakeArray.makeArray();

        long start = System.currentTimeMillis();
        for(int i= 0;i<src.length;i++){
//            SleepTools.ms(1);
            count = count + src[i];
        }
        System.out.println("The count is "+count
                +" spend time:"+(System.currentTimeMillis()-start)+"ms");
    }
}
