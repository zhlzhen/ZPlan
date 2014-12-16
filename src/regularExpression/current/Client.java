package regularExpression.current;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

interface ISubject {
	public void showName(String name);
}

class RealSubject implements ISubject {

	@Override
	public void showName(String name) {
		System.out.println(name+"闪亮登场");
	}

}

class LogHandler implements InvocationHandler {

	Object target=null;
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result=null;
		//调用目标对象方法前的逻辑
		System.out.println("下面有一个大人物要出现");
		//调用目标对象的方法，这句代码将代理与目标类联系了起来
		method.invoke(target, args);
		//调用目标对象方法后的逻辑
		System.out.println("大家鼓掌欢迎");
		return result;
				
	}

}

class LogIntercept implements MethodInterceptor {
	Object target=null;
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		
		Object result=null;
		//调用目标对象方法前的逻辑
		System.out.println("下面有一个大人物要出现");
		//调用目标对象的方法，这句代码将代理与目标类联系了起来
		arg3.invoke(target, arg2);
		//调用目标对象方法后的逻辑
		System.out.println("大家鼓掌欢迎");
		return result;
	}

}

public class Client {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		LogHandler logHandler=new LogHandler();
//		logHandler.setTarget(new RealSubject());
//		//创建代理对象
//		ISubject proxySubject=(ISubject)Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), logHandler);
//		System.out.println("-------JDK Proxy-------------");
//		proxySubject.showName("委座");
		
		/////////////////////////////////////////////
		LogIntercept logIntercept=new LogIntercept();  
        logIntercept.setTarget(new RealSubject());  
		
        RealSubject proxySubject1=(RealSubject )Enhancer.create(RealSubject.class, logIntercept); 
        System.out.println("-------CBLIB-------------");  
        proxySubject1.showName("委座");  

	}

}
