package com.kt.naas.process;

public class Job {
    private final Object message;
    private final Processor processor;

    public Job(Processor processor, Object message){
        this.processor = processor;
        this.message = message;
    }

    public void execute(){
        processor.process(message);
    }
}
