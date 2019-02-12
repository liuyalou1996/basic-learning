package com.iboxpay.jdkapi.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class CharsetTest {

  public static void main(String[] args) throws Exception {
    Charset charset = StandardCharsets.UTF_8;
    CharsetEncoder encoder = charset.newEncoder();
    CharsetDecoder decoder = charset.newDecoder();

    CharBuffer cbuffer = CharBuffer.allocate(8);
    cbuffer.put('a');
    cbuffer.put('b');
    cbuffer.put('c');
    cbuffer.flip();

    ByteBuffer bbuffer = encoder.encode(cbuffer);
    for (int i = 0; i < bbuffer.capacity(); i++) {
      System.out.print(bbuffer.get(i) + " ");
    }
    System.out.println("\n" + decoder.decode(bbuffer).toString());
  }

}
