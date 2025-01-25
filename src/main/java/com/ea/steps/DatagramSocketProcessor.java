package com.ea.steps;

import com.ea.dto.DatagramSocketData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatagramSocketProcessor {

    private final DatagramSocketWriter writer;

    /*public static final int RAW_PACKET_INIT = 1;
    public static final int RAW_PACKET_CONN = 2;
    public static final int RAW_PACKET_DISC = 3;
    public static final int RAW_PACKET_POKE = 5;
    public static final int GAME_PACKET_USER_UNRELIABLE = 7;
    public static final int GAME_PACKET_USER_UNRELIABLE_AND_GAME_PACKET_SYNC = 71; // 7 + 64
    public static final int GAME_PACKET_SYNC = 64;*/
    public static final int RAW_PACKET_DATA = 256;
    public static final int RAW_PACKET_UNREL = 128;

    /**
     * Prepares the output message based on request type,
     * then calls the writer
     * @param socket the socket to give to the writer
     * @param socketData the object to process
     */
    public void process(DatagramSocket socket, DatagramSocketData socketData) {

//        log.info("origin port: {}", socket.getLocalPort());
//        log.info("destination port: {}", socketData.getInputPacket().getPort());

        DatagramPacket inputPacket = socketData.getInputPacket();
        byte[] buf = Arrays.copyOf(inputPacket.getData(), inputPacket.getLength());

        int packetSeq = new BigInteger(1, buf, 0, 4).intValue();

        if(packetSeq == 1) {
            byte[] connAccept = new byte[4];
            connAccept[3] = 0x02;
            System.arraycopy(connAccept, 0, buf, 0, 4);
        } else if (RAW_PACKET_UNREL <= packetSeq && RAW_PACKET_DATA > packetSeq) {
            /*int packetOperation = new BigInteger(1, buf, inputPacket.getLength() - 1, 1).intValue();
            if (0x07 == packetOperation) {
                buf[inputPacket.getLength() - 1] = 0x05;
            } else if (0x47 == packetOperation)  {
                buf[inputPacket.getLength() - 1] = 0x45;
            }*/
        }

        socketData.setOutputMessage(buf);

        writer.write(socket, socketData);

    }

}
