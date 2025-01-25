package com.ea.config;

import com.ea.steps.DatagramSocketReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Thread to handle a unique udp socket
 */
@Slf4j
@RequiredArgsConstructor
public class UdpSocketThread implements Runnable {

    private final DatagramSocket clientSocket;
    private final DatagramSocketReader reader;

    public void run() {
        try {
            reader.read(clientSocket);
        } catch (IOException e) {
            log.error("Error reading from socket", e);
        } finally {
            log.info("UDP server closed");
        }
    }

}
