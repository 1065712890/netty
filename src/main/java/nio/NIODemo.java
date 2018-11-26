package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

/**
 * @program: netty_hello
 * @description: test
 * @author: dengbin
 * @create: 2018-11-23 14:28
 **/

public class NIODemo {

    @Test
    public void client() throws IOException {

        //获取通道
        SocketChannel client = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

        //切换为非阻塞模式
        client.configureBlocking(false);

        //分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //发送数据给服务端
        //存入数据
        buf.put(new Date().toString().getBytes());
        //切换模式。进行读取，这个是从buf中进行读取，因为要把buf中的数据拿出来写入到通道
        buf.flip();
        //向通道写入
        client.write(buf);
        //清空缓冲区
        buf.clear();

        //关闭通道
        client.close();

    }

    @Test
    public void server() throws IOException {
        //获取通道
        ServerSocketChannel server = ServerSocketChannel.open();
        //设置非阻塞
        server.configureBlocking(false);
        //绑定端口
        server.bind(new InetSocketAddress(8888));
        //获取选择器
        Selector selector = Selector.open();
        //向选择器注册连接事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        //有事件就绪
        while(selector.select() > 0){
            //获取迭代器。遍历事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey key = it.next();
                //如果是连接就绪。则获取客户端的连接，并注册读就绪事件
                if(key.isAcceptable()){
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    //读就绪事件，进行数据处理
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while((len = client.read(buf)) > 0){
                        //切换为读模式
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                it.remove();
            }
        }
    }


}
