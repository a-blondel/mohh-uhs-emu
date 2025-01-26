package com.ea.steps;

import com.ea.dto.DatagramSocketData;
import com.ea.utils.HexUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatagramSocketWriter {

    /**
     * Sends packet
     * @param socket the socket to write into
     * @param socketData the object to use to write the message
     */
    public void write(DatagramSocket socket, DatagramSocketData socketData) {

        try {
            DatagramPacket inputPacket = socketData.getInputPacket();
            byte[] buf = socketData.getOutputMessage();

            InetAddress address = inputPacket.getAddress();
            int port = inputPacket.getPort();

            DatagramPacket outputPacket = new DatagramPacket(
                    buf,
                    buf.length,
                    address,
                    port);

            log.info("Send to {}:{}:\n{}", address, port, HexUtils.formatHexDump(buf));
            socket.send(outputPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
