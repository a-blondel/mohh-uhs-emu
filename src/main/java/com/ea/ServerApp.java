package com.ea;

import com.ea.config.UdpSocketThread;
import com.ea.steps.DatagramSocketProcessor;
import com.ea.steps.DatagramSocketReader;
import com.ea.steps.DatagramSocketWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.net.DatagramSocket;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ServerApp implements CommandLineRunner {

    @Value("${mohh-psp.port}")
    private int mohhPspPort;

    @Value("${mohh2-psp.port}")
    private int mohh2PspPort;

    @Value("${mohh2-wii.port}")
    private int mohh2WiiPort;

    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            startUdpSocketThread(mohhPspPort);
            startUdpSocketThread(mohh2PspPort);
            startUdpSocketThread(mohh2WiiPort);
        } catch (Exception e) {
            log.error("Error starting servers", e);
        }
    }

    private void startUdpSocketThread(int port) throws Exception {
        DatagramSocket socket = new DatagramSocket(port);
        UdpSocketThread udpSocketThread = new UdpSocketThread(socket, datagramSocketReader());
        new Thread(udpSocketThread).start();
        log.info("UDP server started on port {}.", port);
    }

    @Bean
    public DatagramSocketReader datagramSocketReader() {
        return new DatagramSocketReader(datagramSocketProcessor());
    }

    @Bean
    public DatagramSocketProcessor datagramSocketProcessor() {
        return new DatagramSocketProcessor(datagramSocketWriter());
    }

    @Bean
    public DatagramSocketWriter datagramSocketWriter() {
        return new DatagramSocketWriter();
    }
}