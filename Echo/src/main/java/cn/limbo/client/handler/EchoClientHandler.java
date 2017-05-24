package cn.limbo.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by limbo on 2017/5/24.
 */

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//当被通知该 channel 是活动的时候就发送信息
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
				CharsetUtil.UTF_8));
	}

	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
		System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
	}

	@Override
	//记录日志错误并关闭 channel
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
