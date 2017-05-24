package cn.limbo.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by limbo on 2017/5/24.
 */

@Sharable     //标示一个ChannelHandler可以被多个Channel安全地共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	//对于每个传入地消息都要调用
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);     //将所接收的消息返回给发送者。注意，这还没有冲刷数据
	}

	@Override
	//通知ChannelInboundHandler 最后一次对channelRead()的调用是当前批量读取中的最后一条消息
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//将未决消息冲刷到远程节点，并且关闭该Channel
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
				.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	//异常时抛出
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close(); //关闭通道
	}
}
