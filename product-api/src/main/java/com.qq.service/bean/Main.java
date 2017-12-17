package com.qq.service.bean;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Qing on 2017/12/17.
 */
public class Main {
    public static void main(String[] args) {

        IProductService iProductService = (IProductService)rpc(IProductService.class);
        Product product = iProductService.queryById(1);
        System.out.println(product.getName());
    }

    public static Object rpc(final Class clazz){
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},new InvocationHandler(){

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket("127.0.0.1",8888);

//                       我们想远程调用哪个类的哪个方法，并传递给这个方法什么参数
//                       注意我们只知道product api 并不知道product api在product 的实现
                        String apiClassName = clazz.getName();
                        String methodName = method.getName();
                        Class[] parameterTypes = method.getParameterTypes();

                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                        objectOutputStream.writeUTF(apiClassName);
                        objectOutputStream.writeUTF(methodName);
                        objectOutputStream.writeObject(parameterTypes);
                        objectOutputStream.writeObject(args);
                        objectOutputStream.flush();

                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        Object o = objectInputStream.readObject();
                        objectInputStream.close();
                        objectOutputStream.close();
                        socket.close();


                        return o;
                    }
                });
    }
}
