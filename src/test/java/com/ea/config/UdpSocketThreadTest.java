package com.ea.config;

import com.ea.ServerApp;
import com.ea.dto.DatagramSocketData;
import com.ea.steps.DatagramSocketWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {ServerApp.class})
class UdpSocketThreadTest {

    @MockitoBean(answers = Answers.CALLS_REAL_METHODS)
    private DatagramSocketWriter writerSpy;

    private DatagramSocket clientSocket;

    @BeforeEach
    void setUp() throws IOException {
        clientSocket = new DatagramSocket(0);
    }

    @Test // MoHH - #1
    void connectPacketMoHH() throws IOException, InterruptedException {
        byte[] input = HexFormat.of().parseHex("00000001b697dd33");
        byte[] expectedOutput = HexFormat.of().parseHex("00000002b697dd33");

        DatagramPacket packet = new DatagramPacket(input, input.length, InetAddress.getLocalHost(), 3658);
        clientSocket.send(packet);

        Thread.sleep(200);

        ArgumentCaptor<DatagramSocketData> captor = ArgumentCaptor.forClass(DatagramSocketData.class);
        verify(writerSpy, times(1)).write(any(DatagramSocket.class), captor.capture());
        DatagramSocketData sentData = captor.getValue();
        assertArrayEquals(expectedOutput, sentData.getOutputMessage());

        clientSocket.close();
    }
}