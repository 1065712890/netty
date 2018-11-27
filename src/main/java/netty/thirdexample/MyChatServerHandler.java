package netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @program: netty
 * @description:
 * @author: dengbin
 * @create: 2018-11-27 09:42
 **/

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();

        for(Channel ch : channelGroup){
            if(channel != ch){
                ch.writeAndFlush(channel.remoteAddress() + "：" + s + "\n");
            }else{
                ch.writeAndFlush("我说:" + s + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * @description: 连接建立
     * @author: dengbin
     * @date: 2018/11/27 上午10:00
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //广播
        channelGroup.writeAndFlush("from server: " + channel.remoteAddress() + "加入\n");

        channelGroup.add(channel);
    }

    /**
     * @description: 连接断开
     * @author: dengbin
     * @date: 2018/11/27 上午10:00
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("from server" + channel.remoteAddress() + "离开\n");
    }

    /**
     * @description: 连接属于活动状态
     * @author: dengbin
     * @date: 2018/11/27 上午10:02
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线\n");
    }

    /**
     * @description: 离线
     * @author: dengbin
     * @date: 2018/11/27 上午10:03
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线\n");
    }
}
