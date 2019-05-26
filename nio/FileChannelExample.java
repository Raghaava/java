package org.interview.preperation.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//Read a file and print to console using nio package
public class FileChannelExample {
    public static void main(String args[]) throws IOException {
        RandomAccessFile ram = new RandomAccessFile("/Users/raghava.juvvaji/Documents/SelfControl-Killer.log", "r");
        FileChannel channel = ram.getChannel();
        ByteBuffer buff = ByteBuffer.allocate(512);

        //Keep reading till file ends
        while (channel.read(buff) != -1) {
            //set buff pointers for read
            buff.flip();
            System.out.println(new String(buff.array()));
            buff.clear();
        }
        ram.close();
    }


}
