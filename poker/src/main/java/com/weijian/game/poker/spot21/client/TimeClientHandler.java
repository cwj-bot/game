package com.weijian.game.poker.spot21.client;

import com.alibaba.fastjson.JSONObject;
import com.weijian.game.poker.spot21.dto.InterFaceMsgReq;
import com.weijian.game.poker.util.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-07-15 19:06
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

//    private byte[] req;
    /** 空闲次数 */
    private int idle_count = 1;

    /** 发送次数 */
    private int count = 1;

    /** 循环次数 */
    private int fcount = 1;


//    public TimeClientHandler() {
//        req = ("hello" + System.getProperty("line.separator")).getBytes();
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //拿到的msg已经是解码成字符串之后的应答消息了。
        String body = (String) msg;
        log.info("channelRead body:{}", body);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        ByteBuf message = null;
//        for (int i = 0; i < 100; i++) {
//            message = Unpooled.buffer(req.length);
//            message.writeBytes(req);
//            ctx.writeAndFlush(message);
//        }

        InterFaceMsgReq interFaceMsgReq = new InterFaceMsgReq();
        // 创建
//        interFaceMsgReq.setInterfaceType(Constant.INTERFACE_21_SPOT_CREATE_TABLE_1);
//        interFaceMsgReq.setPlayerName("小明");
//        interFaceMsgReq.setPlayerNum(4);
//        send(ctx, JSONObject.toJSONString(interFaceMsgReq));

        // 加入
        interFaceMsgReq.setInterfaceType(Constant.INTERFACE_21_SPOT_JOIN_TABLE_2);
        interFaceMsgReq.setPlayerName("小华");
        interFaceMsgReq.setTableId(102);
        send(ctx, JSONObject.toJSONString(interFaceMsgReq));

    }

    private void send(ChannelHandlerContext ctx, String msg) {
        byte[] req = (msg + System.getProperty("line.separator")).getBytes();
        ByteBuf message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);
    }

    /**
     * 心跳请求处理，每4秒发送一次心跳请求;
     *
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        System.out.println("\r\n循环请求的时间：" + date() + "，次数" + fcount);

        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) {// 如果写通道处于空闲状态就发送心跳命令
                // 设置发送次数，允许发送3次心跳包
                if (idle_count <= 3) {
                    idle_count++;
                    ctx.channel().writeAndFlush("ddd");
                } else {
                    System.out.println("心跳包发送结束，不再发送心跳请求！！！");
                }
            }
        }

        fcount++;
    }

    private String date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
