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
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatagramSocketReader {

    private final DatagramSocketProcessor processor;

    /**
     * Waits for data to come from the client
     * Calls a processor to handle input messages from the stream
     * @param socket the socket to read
     * @throws IOException
     */
    public void read(DatagramSocket socket) throws IOException {
        while (true) {
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            byte[] dump = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
            log.info("Received from {}:{}:\n{}", address, port, HexUtils.formatHexDump(dump));

            processor.process(socket, new DatagramSocketData(packet, null));
        }
    }

}
