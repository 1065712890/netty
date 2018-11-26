package netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @program: netty
 * @description:
 * @author: dengbin
 * @create: 2018-11-26 17:17
 **/

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress() + "----" + s);
        channelHandlerContext.writeAndFlush("from server: " + UUID.randomUUID().toString());
    }

    /**
    * @description: 异常
    * @author: dengbin
    * @date: 2018/11/26 下午5:28
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        ctx.close();
    }
}
