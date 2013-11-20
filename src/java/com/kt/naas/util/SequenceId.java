package com.kt.naas.util;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceId {
	private static final int MAX_SEQ_VALUE = 999999999;
    private static final int MIN_SEQ_VALUE = 1;

    private static AtomicInteger currentSeq = new AtomicInteger(MIN_SEQ_VALUE);
    
    public static int nextValue(){
        int seq = currentSeq.incrementAndGet();

        if(seq == MAX_SEQ_VALUE){
        	currentSeq = new AtomicInteger(MIN_SEQ_VALUE);
        }

        return seq;
    }
}
