package com.universe.jdkapi.nio;

import java.nio.CharBuffer;

/**
 * Buffer可以被理解成一个容器，它的本质是一个数组，发送到Channel中的所有对象都必须先放到Buffer中，而从Channel中读取的数据
 * 也必须下先放到Buffer中。<br/>
 * Buffer根据数据类型分类有：ByteBuffer、ShortBuffer、CharBuffer、IntBuffer、LongBuffer、FloatBuffer、DoubleBuffer。
 */
public class BufferTest {

  public static void main(String[] args) {
    // 创建buffer,ByteBuffer有个allocateDirect方法,用来创建直接buffer,直接buufer读取效率更高,但由于直接buffer创建成本高,
    // 所以只适用于长生存期的buffer,而不适用于短生存期、一次用完就丢弃的Buffer
    CharBuffer buff = CharBuffer.allocate(8);
    System.out.println("capacity:" + buff.capacity());
    System.out.println("limit:" + buff.limit());
    System.out.println("position:" + buff.position());

    // 放入元素
    buff.put('a');
    buff.put('b');
    buff.put('c');
    System.out.println("加入三个元素后,position:" + buff.position());

    // 调用flip方法后将position设置为0，将limit设置为position，为从buffer中取出数据做准备
    buff.flip();
    System.out.println("执行flip方法后,limit:" + buff.limit());
    System.out.println("执行flip方法后,position:" + buff.position());

    // 相对读取或写入position会根据处理元素的个数累加
    System.out.println("第一个元素为:" + buff.get());
    System.out.println("取出第一个元素后,position:" + buff.position());

    // 调用clear方法后,为向buffer中装入数据做准备，调用clear方法后并不会清除buffer中的数据,只是position被设置为0，limit被设置为capacity
    buff.clear();
    System.out.println("执行clear后,limit:" + buff.limit());
    System.out.println("执行clear后,position:" + buff.position());
    System.out.println("执行clear后,缓冲区内容并没有被清除,第三个元素为:" + buff.get(2));
    System.out.println("执行绝对读取后position的位置并不会改变,position:" + buff.position());
  }

}
