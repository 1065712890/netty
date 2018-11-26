package nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @program: netty_hello
 * @description: test buffer
 * @author: dengbin
 * @create: 2018-11-23 15:01
 *
 *
 * put 存数据
 * get 取数据
 *
 * capacity 容量，表示缓冲区中最大存储数据的容量， 一旦声明，不可改变
 * limit 界限，表示缓冲区中可以操作数据的大小， limit后的数据不能进行读写
 * position 位置，表示缓冲区中正在操作数据的位置
 * mark 标记位置，用于还原，和reset结合使用
 *
 * position <= limit <= capacity
 *
 *
 **/

public class TestBuffer {
    @Test
    public void test1(){
        //分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //分配缓冲区后的结果 0 1024 1024
        System.out.println("__________________allocate");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());

        //put后的结果 5, 1024, 1024
        String str = "abcde";
        buf.put(str.getBytes());
        System.out.println("__________________put");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());

        //flip后的结果 0, 5, 1024
        buf.flip();
        System.out.println("__________________flip");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());

        //get后的结果 5, 5, 1024
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("__________________get");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());

        //rewind 还原，复位 mark为-1， position为0
        //0, 5, 1024
        buf.rewind();
        System.out.println("__________________rewind");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());


        //mark标记为0, get后position为1，reset恢复，position为0
        buf.mark();
        buf.get();
        System.out.println("__________________before reset");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());
        buf.reset();
        System.out.println("__________________reset");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());

        //清空， 假清空 数据仍然存在 0, 1024, 1024
        buf.clear();
        System.out.println("__________________clear");
        System.out.println("postition:" + buf.position());
        System.out.println("limit:" + buf.limit());
        System.out.println("capacity:" + buf.capacity());

    }
}
